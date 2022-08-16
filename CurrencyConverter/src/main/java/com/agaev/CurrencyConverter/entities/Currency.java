package com.agaev.CurrencyConverter.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name="currencies")
public class Currency {

    @Id
    private Integer code;

    private String name;

    private String charCode;

    public Currency(Integer code, String name, String charCode) {
        this.code = code;
        this.name = name;
        this.charCode = charCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return code.equals(currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
