package com.agaev.CurrencyConverter.repositories;

import com.agaev.CurrencyConverter.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepo extends JpaRepository<Currency, Integer> {

}
