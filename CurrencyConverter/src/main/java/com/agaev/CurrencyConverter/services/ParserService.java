package com.agaev.CurrencyConverter.services;

import com.agaev.CurrencyConverter.entities.Currency;
import com.agaev.CurrencyConverter.entities.CurrencyDetails;
import com.agaev.CurrencyConverter.parsers.Parser;
import com.agaev.CurrencyConverter.repositories.CurrencyDetailsRepo;
import com.agaev.CurrencyConverter.repositories.CurrencyRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParserService {

    private final CurrencyRepo currencyRepo;
    private final CurrencyDetailsRepo currencyDetailsRepo;
    private final Parser parser;

    @Autowired
    public ParserService(CurrencyRepo currencyRepo, CurrencyDetailsRepo currencyDetailsRepo, Parser parser) {
        this.currencyRepo = currencyRepo;
        this.currencyDetailsRepo = currencyDetailsRepo;
        this.parser = parser;
    }

    public void getAllCurrenciesAndCurrencyDetails() throws IOException {
        List<CurrencyDetails> currencyDetails = parser.parse();
        List<Currency> currencies = new ArrayList<>();
        currencyDetails.forEach(detail -> currencies.add(detail.getCurrency()));
        currencyRepo.saveAll(currencies);
        currencyDetailsRepo.saveAll(currencyDetails);
    }

    @SneakyThrows
    public double getUpdatedExchangeRate(Currency currency) {
        List<CurrencyDetails> currencyDetails = parser.parse();
        CurrencyDetails neededCurrencyDetails = currencyDetails
                .stream()
                .filter(detail -> detail.getCurrency().equals(currency))
                .findAny().get();
        return neededCurrencyDetails.getExchangeRate();
    }
}
