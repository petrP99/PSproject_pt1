package com.pers.http.greeting;

import com.pers.dto.UserCreateDto;
import com.pers.dto.UserDto;
import com.pers.entity.Role;
import java.net.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes({"user"})
//@RequestMapping("api/v1")
public class GreetingController {

    @GetMapping("/hello")
    public String hello(Model model, HttpRequest request, UserDto userDto) {
        model.addAttribute("user", new UserDto("user1", "123"));
        return "greeting/hello";
    }

    @RequestMapping(value = "/bye", method = RequestMethod.GET)
    public ModelAndView bye() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting/bye");
        return modelAndView;
    }

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    public ModelAndView first() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting/first");
        return modelAndView;
    }

}
