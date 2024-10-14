package com.pers.service;

import com.pers.dto.TransferCreateDto;
import com.pers.dto.TransferReadDto;
import com.pers.dto.filter.TransferFilterDto;
import com.pers.entity.Status;
import com.pers.mapper.TransferCreateMapper;
import com.pers.mapper.TransferReadMapper;
import com.pers.repository.CardRepository;
import com.pers.repository.TransferRepository;
import com.pers.util.CheckOfOperationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.pers.entity.Status.FAILED;


@Service
@Transactional
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final TransferReadMapper transferReadMapper;
    private final TransferCreateMapper transferCreateMapper;
    private final ClientService clientService;
    private final CardService cardService;
    private final CardRepository cardRepository;
    private final SmsService smsService;

    @Transactional
    public boolean checkAndCreateTransfer(TransferCreateDto transfer) {
        var cardFrom = cardService.findById(transfer.cardIdFrom()).orElseThrow();
        var cardTo = cardService.findById(transfer.cardIdTo()).orElseThrow();
        var clientFrom = clientService.findById(cardFrom.clientId()).orElseThrow();
        var clientTo = clientService.findById(cardTo.clientId()).orElseThrow();

        if (clientTo.getStatus() == Status.ACTIVE
                && cardTo.status() == Status.ACTIVE
                && cardFrom.status() == Status.ACTIVE
                && (transfer.amount()).compareTo(cardFrom.balance()) <= 0) {

            create(transfer);

            var cardFromUpdateBalance = CheckOfOperationUtil.createDtoCardUpdateBalanceSubtract(cardFrom, transfer.amount());
            var cardToUpdateBalance = CheckOfOperationUtil.createDtoCardUpdateBalanceAdd(cardTo, transfer.amount());
            cardService.updateCardBalance(cardFromUpdateBalance);
            cardService.updateCardBalance(cardToUpdateBalance);

            var clientFromNewBalance = CheckOfOperationUtil.calculateClientBalance(cardRepository.findByClientId(clientFrom.getId()));
            var clientToNewBalance = CheckOfOperationUtil.calculateClientBalance(cardRepository.findByClientId(clientTo.getId()));
            var clientFromUpdateDto = CheckOfOperationUtil.createClientUpdateBalanceDto(clientFrom, clientFromNewBalance);
            var clientToUpdateDto = CheckOfOperationUtil.createClientUpdateBalanceDto(clientTo, clientToNewBalance);

            clientService.updateBalance(clientFromUpdateDto);
            clientService.updateBalance(clientToUpdateDto);
//            smsService.sendInfoTransfer(transfer, clientFrom, clientFromNewBalance);
        } else {
            var transferFail = new TransferCreateDto(
                    transfer.clientId(),
                    transfer.cardIdFrom(),
                    transfer.cardIdTo(),
                    transfer.amount(),
                    transfer.timeOfTransfer(),
                    transfer.recipient(),
                    transfer.message(),
                    FAILED);
            create(transferFail);

            return false;
        }
        return true;
    }


    @Transactional
    public TransferReadDto create(TransferCreateDto transferDto) {
        return Optional.of(transferDto)
                .map(transferCreateMapper::mapFrom)
                .map(transferRepository::save)
                .map(transferReadMapper::mapFrom)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Long id) {
        return transferRepository.findById(id)
                .map(entity -> {
                    transferRepository.delete(entity);
                    transferRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    public Page<TransferReadDto> findAllByClientByFilter(TransferFilterDto filter, Pageable pageable, Long clientId) {
        return transferRepository.findAllByClientByFilter(filter, pageable, clientId)
                .map(transferReadMapper::mapFrom);
    }

    public Page<TransferReadDto> findAllByFilter(TransferFilterDto filter, Pageable pageable) {
        return transferRepository.findAllByFilter(filter, pageable)
                .map(transferReadMapper::mapFrom);
    }

    public Optional<TransferReadDto> findById(Long id) {
        return transferRepository.findById(id)
                .map(transferReadMapper::mapFrom);
    }

//    public File downloadHistory(Long id) {
//        List<PaymentReadDto> allByClientId = findAllByClientId(id);
//        File file = new File("Payments history.txt");
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
//            writer.write("time\t\t\t\t object name\t\t\t\t amount\t\t\tstatus\n");
//            for (int i = 0; i < allByClientId.size(); i++) {
//                writer.write(dateTimeParser(allByClientId.get(i).timeOfPay().toString()) + "\t" +
//                        allByClientId.get(i).shopName() + "\t\t\t" +
//                        allByClientId.get(i).amount().toString() + "\t\t" +
//                        allByClientId.get(i).status() + "\n");
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Ну удалось записать файл");
//        }
//        return file;
//    }

}


