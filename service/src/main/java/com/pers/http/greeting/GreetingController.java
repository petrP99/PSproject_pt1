package com.pers.http.greeting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("api/v1")
public class GreetingController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView hello() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting/hello");
        return modelAndView;
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
