package com.agaev.CurrencyConverter.controllers;

import com.agaev.CurrencyConverter.entities.Exchange;
import com.agaev.CurrencyConverter.entities.User;
import com.agaev.CurrencyConverter.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;

@RequestMapping("/exchange")
@Controller
public class ExchangeController {

    private final String title = "Обмен валюты";

    @Autowired
    ExchangeService exchangeService;

    @GetMapping
    public String exchange(Model model) throws IOException {
        model.addAttribute("currencies", exchangeService.getAllCurrencies());
        model.addAttribute("metaTitle", title);
        return "exchange";
    }

    @PostMapping
    public String addExchange(@AuthenticationPrincipal User user,
            @ModelAttribute Exchange exchange,
                              Model model)
            throws IOException {

        exchangeService.checkAndUpdateRates(exchange);
        exchangeService.setExchangeParams(exchange, user);

        model.addAttribute("currencies", exchangeService.getAllCurrencies());
        model.addAttribute("metaTitle", title);
        model.addAttribute("currentExchange", exchange);
        model.addAttribute("exchanges", exchangeService.getNLastExchangesByUser(user, 5));

        return "exchange";
    }

}
