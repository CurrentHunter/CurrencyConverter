package com.agaev.CurrencyConverter.repositories;

import com.agaev.CurrencyConverter.entities.Currency;
import com.agaev.CurrencyConverter.entities.CurrencyDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyDetailsRepo extends JpaRepository<CurrencyDetails, Integer> {

    CurrencyDetails findByCurrency(Currency currency);

}
