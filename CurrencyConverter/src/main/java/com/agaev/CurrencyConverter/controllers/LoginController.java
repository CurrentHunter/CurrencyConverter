package com.agaev.CurrencyConverter.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/login")
public class LoginController {

    private final String title = "Вход";

    @GetMapping
    public String auth(Model model) {
        model.addAttribute("metaTitle", title);
        return "login";
    }

    @PostMapping
    public String login(Model model) {
        model.addAttribute("metaTitle", title);
        return "redirect:exchange";
    }
}
