package com.agaev.CurrencyConverter.parsers;

import com.agaev.CurrencyConverter.entities.Currency;
import com.agaev.CurrencyConverter.entities.CurrencyDetails;
import com.agaev.CurrencyConverter.data.JSONData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class JSONParserNBU implements Parser {

    @Autowired
    JSONData jsonData;

    @Override
    public List<CurrencyDetails> parse() throws IOException {
        List<CurrencyDetails> currencyDetails = new ArrayList<>();

        //manually added UAH
        currencyDetails.add(new CurrencyDetails(new Currency(980, "Гривня", "UAH"), 1.0));

        JSONArray jsonArray = new JSONArray(jsonData.getData());
        Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            JSONObject obj = (JSONObject) iterator.next();
            currencyDetails.add(new CurrencyDetails(new Currency(obj.getInt("r030"), obj.getString("txt"), obj.getString("cc")), obj.getDouble("rate")));
        }
        return currencyDetails;
    }
}
