package com.pers.repository;

import com.pers.dto.filter.*;
import com.pers.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface FilterClientRepository {

    Page<Client> findAllByFilter(ClientFilterDto filterDto, Pageable pageable);

}