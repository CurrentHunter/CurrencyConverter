package com.agaev.CurrencyConverter.services;

import com.agaev.CurrencyConverter.entities.Currency;
import com.agaev.CurrencyConverter.entities.CurrencyDetails;
import com.agaev.CurrencyConverter.entities.Exchange;
import com.agaev.CurrencyConverter.entities.User;
import com.agaev.CurrencyConverter.repositories.CurrencyDetailsRepo;
import com.agaev.CurrencyConverter.repositories.CurrencyRepo;
import com.agaev.CurrencyConverter.repositories.ExchangeRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ExchangeService {

    private final CurrencyRepo currencyRepo;
    private final CurrencyDetailsRepo currencyDetailsRepo;
    private final ParserService parserService;
    private final ExchangeRepo exchangeRepo;

    @Autowired
    public ExchangeService(CurrencyRepo currencyRepo, CurrencyDetailsRepo currencyDetailsRepo, ParserService parserService, ExchangeRepo exchangeRepo) {
        this.currencyRepo = currencyRepo;
        this.currencyDetailsRepo = currencyDetailsRepo;
        this.parserService = parserService;
        this.exchangeRepo = exchangeRepo;
    }

    public List<Currency> getAllCurrencies() throws IOException {
        if(currencyRepo.count() == 0 || currencyDetailsRepo.count() == 0)
            parserService.getAllCurrenciesAndCurrencyDetails();
        return currencyRepo.findAll();
    }

    public void checkAndUpdateRates(Exchange exchange) {
        Stream.of(exchange.getSourceCurrency(), exchange.getTargetCurrency())
                .map(currencyDetailsRepo::findByCurrency)
                .filter(cd -> cd.getLastModified().isBefore(LocalDateTime.now().minusHours(1)))
                .forEach(cd -> {
                    cd.setExchangeRate(parserService.getUpdatedExchangeRate(cd.getCurrency()));
                    cd.setLastModified(LocalDateTime.now());
                    currencyDetailsRepo.save(cd);
                });
    }

    public List<Exchange> getAllExchanges(){
        return exchangeRepo.findAll();
    }

    public List<Exchange> getNLastExchangesByUser(User user, int n){
        return exchangeRepo.findNLastExchangesByUser(user, PageRequest.of(0, n));
    }

    public void setExchangeParams(Exchange exchange, User user) {
        exchange.setConversionRate(getThisConversionRate(exchange));
        exchange.setResult(convert(exchange));
        exchange.setUser(user);
        exchangeRepo.saveAndFlush(exchange);
    }

    public BigDecimal getThisConversionRate(Exchange exchange) {
        return Stream.of(exchange.getSourceCurrency(), exchange.getTargetCurrency())
                .map(currencyDetailsRepo::findByCurrency)
                .map(CurrencyDetails::getExchangeRate)
                .map(BigDecimal::valueOf)
                .reduce((source, target) -> source.divide(target, 4, RoundingMode.HALF_UP))
                .orElse(null);
    }

    @SneakyThrows
    public BigDecimal convert(Exchange exchange) {
        checkAndUpdateRates(exchange);
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("###.####", dfs);
        df.setParseBigDecimal(true);

        BigDecimal sourceAmount = (BigDecimal) df.parse(exchange.getAmount());
        BigDecimal conversionRate = getThisConversionRate(exchange);
        BigDecimal result = sourceAmount.multiply(conversionRate).setScale(2, RoundingMode.HALF_UP);
        System.out.println(result);
        return result;
    }
}
