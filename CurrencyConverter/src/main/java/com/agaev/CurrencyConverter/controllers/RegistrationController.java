package com.agaev.CurrencyConverter.controllers;

import com.agaev.CurrencyConverter.entities.Role;
import com.agaev.CurrencyConverter.entities.User;
import com.agaev.CurrencyConverter.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Collections;


@RequiredArgsConstructor
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserRepo userRepo;
    private final String title = "Регистрация";

    @GetMapping
    public String showRegistrationPage(Model model) {
        model.addAttribute("metaTitle", title);
        return "registration";
    }

    @PostMapping
    public String addUser(User user, Model model) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("message", "Такой пользователь уже есть");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        model.addAttribute("metaTitle", title);
        return "redirect:login";
    }
}
