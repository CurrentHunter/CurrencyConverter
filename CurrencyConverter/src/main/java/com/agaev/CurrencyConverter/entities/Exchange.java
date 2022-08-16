package com.agaev.CurrencyConverter.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="exchanges_log")
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="source_currency")
    private Currency sourceCurrency;

    @ManyToOne
    @JoinColumn(name="target_currency")
    private Currency targetCurrency;

    private String amount;

    public Exchange(Currency sourceCurrency, Currency targetCurrency, String amount) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
    }

    private BigDecimal result;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="date_time")
    private LocalDateTime dateTime;

    @Column(name="conversion_rate")
    private BigDecimal conversionRate;

    @PrePersist
    public void prePersist(){
        this.dateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.dateTime = LocalDateTime.now();
    }

    public String getFormattedDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         return dateTime.format(formatter);
    }

}
