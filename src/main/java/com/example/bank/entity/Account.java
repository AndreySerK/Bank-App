package com.example.bank.entity;

import com.example.bank.entity.enums.AccountStatus;
import com.example.bank.entity.enums.AccountType;
import com.example.bank.entity.enums.CurrencyCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(STRING)
    private AccountType type;

    @Column(name = "status")
    @Enumerated(STRING)
    private AccountStatus status;

    @Column(name = "balance")
    private double balance;

    @Column(name = "currency_code")
    @Enumerated(STRING)
    private CurrencyCode currencyCode;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(cascade = ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToMany(mappedBy = "account", cascade = ALL, orphanRemoval = true)
    private List<Agreement> agreements;

    @OneToMany(mappedBy = "debitAccount", cascade = ALL, orphanRemoval = true)
    private Set<Transaction> debitTransactions;

    @OneToMany(mappedBy = "creditAccount", cascade = ALL, orphanRemoval = true)
    private Set<Transaction> creditTransactions;
}
