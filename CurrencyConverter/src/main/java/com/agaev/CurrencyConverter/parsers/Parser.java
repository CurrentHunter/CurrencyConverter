package com.agaev.CurrencyConverter.parsers;

import com.agaev.CurrencyConverter.entities.CurrencyDetails;

import java.io.IOException;
import java.util.List;

public interface Parser {

    List<CurrencyDetails> parse() throws IOException;

}
