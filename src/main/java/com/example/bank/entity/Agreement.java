package com.example.bank.entity;

import com.example.bank.entity.enums.AgreementStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.sql.Timestamp;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "agreements")
public class Agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Enumerated(STRING)
    @Column(name = "status")
    private AgreementStatus status;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
}
