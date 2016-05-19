package org.course.project.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"/","/Home", "/wcsm"})
    public String showHomePage(final Model model) {

        return "home";

    }

}
