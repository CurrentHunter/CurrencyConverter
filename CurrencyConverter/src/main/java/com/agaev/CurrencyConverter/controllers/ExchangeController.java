package com.agaev.CurrencyConverter.controllers;

import com.agaev.CurrencyConverter.entities.Currency;
import com.agaev.CurrencyConverter.entities.Exchange;
import com.agaev.CurrencyConverter.entities.User;
import com.agaev.CurrencyConverter.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

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
    public String addExchange(@ModelAttribute Exchange exchange,
                              Model model)
            throws ParseException, IOException, SAXException, ParserConfigurationException {

        User user = new User(2, "Mary");
        exchangeService.checkAndUpdateRates(exchange);
        exchangeService.setExchangeParams(exchange, user);

        model.addAttribute("currencies", exchangeService.getAllCurrencies());
        model.addAttribute("metaTitle", title);
        model.addAttribute("currentExchange", exchange);
        model.addAttribute("exchanges", exchangeService.getNLastExchangesByUser(user, 5));

        return "exchange";
    }

}
