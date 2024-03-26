package com.pers.http.greeting;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GreetingController {

    public ModelAndView hello() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting/hello");
        return modelAndView;
    }

    public ModelAndView bye() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting/bye");
        return modelAndView;
    }



}
