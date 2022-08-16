package com.agaev.CurrencyConverter.repositories;

import com.agaev.CurrencyConverter.entities.Exchange;
import com.agaev.CurrencyConverter.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExchangeRepo extends JpaRepository<Exchange, Integer> {

    @Query("SELECT exch FROM Exchange exch WHERE exch.user = :user ORDER BY exch.id DESC")
    List<Exchange> findNLastExchangesByUser(@Param("user") User user, Pageable pageable);

}
