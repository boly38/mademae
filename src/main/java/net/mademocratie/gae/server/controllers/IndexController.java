package net.mademocratie.gae.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
        @RequestMapping("/index")
        protected ModelAndView index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
            ModelAndView modelAndView = new ModelAndView("jsp/index.jsp");
            // modelAndView.addObject("url", "/about.do");
            return modelAndView;
        }
    }
