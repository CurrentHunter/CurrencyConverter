package com.agaev.CurrencyConverter.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name="currency_details")
public class CurrencyDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name="currency_code")
    @OneToOne
    private Currency currency;

    @Column(name="exchange_rate")
    private Double exchangeRate;

    public CurrencyDetails(Currency currency, Double exchangeRate) {
        this.currency = currency;
        this.exchangeRate = exchangeRate;
    }

    @Column(name="last_modified")
    private LocalDateTime lastModified;

    @PrePersist
    public void prePersist(){
        this.lastModified = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.lastModified = LocalDateTime.now();
    }

}
