package com.example.AccountMS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="accounts")

public class Account {
    @Id
    @GeneratedValue
    private int accountNumber;
    private String accountType;
    private int customerId;
    private String customerName;
    private int accountBalance;
    private String status;
    private int interest;

}