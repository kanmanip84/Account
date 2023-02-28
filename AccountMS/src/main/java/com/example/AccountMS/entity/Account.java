package com.example.AccountMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="accounts")

public class Account {
    @Id
    @GeneratedValue
    private int accountNumber;
    @NotBlank(message = "Account Type is required")
    private String accountType;
    @NotNull(message = "Customer Id is required")
    private int customerId;
    @NotBlank(message = "Customer name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Customer name should contain only alphabets")
    private String customerName;
    @NotNull(message = "Account Balance is required")
    private int accountBalance;
    @NotBlank(message = "Status is required")
    private String status;
    @NotNull(message = "Interest is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Interest rate should be non-negative")
    private int interest;
}