package com.agaev.CurrencyConverter.repositories;

import com.agaev.CurrencyConverter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
