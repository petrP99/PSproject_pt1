package com.pers.http.greeting;

import com.pers.service.ClientService;
import com.pers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final UserService userService;
    private final ClientService clientService;


}
