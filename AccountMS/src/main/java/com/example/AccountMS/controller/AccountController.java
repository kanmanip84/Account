package com.example.AccountMS.controller;

import com.example.AccountMS.entity.Account;
import com.example.AccountMS.repository.AccountRepository;
import com.example.AccountMS.service.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountServices service;
    @Autowired
    private AccountRepository repository;

    @PostMapping("/addAccount")
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
        String msg = "This method is used to create a account for particular customer and " +
                     "without customer id we cannot create a account";
        createFile(msg);
        if (account.getCustomerId() == 0) //Each account should have a customer id
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("customer id is required");
        } else
            return ResponseEntity.ok(repository.save(account));
    }

    @PostMapping("/addAccounts")
    public List<Account> addAccounts(@RequestBody List<Account> accounts) {
        String msg = "This method is used to create multiple accounts ";
        createFile(msg);
        return service.saveAccounts(accounts);
    }

    @GetMapping("/accounts")
    public List<Account> findAllAccounts() {
        String msg = "This method is used to getting all the accounts";
        createFile(msg);
        return service.getAccounts();
    }

    @GetMapping("/accountById/{accountNumber}")
    public Account findById(@PathVariable int accountNumber) {
        String msg = "This method is used to get the account by accountNumber";
        createFile(msg);
        return service.getAccountById(accountNumber);
    }

    @DeleteMapping("/delete/{accountNumber}")
    public String deleteAccount(@PathVariable int accountNumber) {
        String msg = "This method is used to delete the account by accountNumber";
        createFile(msg);
        return service.deleteAccount(accountNumber);
    }

    @DeleteMapping("/deleteByCustomerId/{customerId}")
    public String deleteAccountByCustomerId(@PathVariable int customerId) {
        String msg = "This method is used to get the account by customer id";
        createFile(msg);
        service.deleteAccountsByCustomerId(customerId);
        return ("Account deleted for customer id  " + customerId);
    }

    @GetMapping("/balance/{accountNumber}")
    public String getBalance(@PathVariable int accountNumber) {
        String msg = "This method is used to get the balance by accountNumber";
        createFile(msg);
        return service.getBalanceById(accountNumber);
    }

    @PutMapping("/update")
    public Account updateAccount(@RequestBody Account account) {
        String msg = "This method is used to update the account";
        createFile(msg);
        return service.updateAccount(account);
    }
    private void createFile(String msg) {
        {
            try {
                File myObj = new File("C:\\Users\\admin\\IdeaProjects\\kanmani\\CreateFile1.txt");
                myObj.createNewFile();
                System.out.println("File created: " + myObj.getName());

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            try {
                FileWriter myWriter = new FileWriter("C:\\Users\\admin\\IdeaProjects\\kanmani\\CreateFile1.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(myWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                printWriter.println(msg + "\n");
                printWriter.flush();
                bufferedWriter.flush();
                myWriter.flush();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/log")
    private StringBuilder ReadFile() {
        StringBuilder fileRead = new StringBuilder();
        String line = " ";
        try {
            File myObj = new File("C:\\Users\\admin\\IdeaProjects\\kanmani\\CreateFile1.txt");
            FileReader filereader = new FileReader(myObj);
            BufferedReader reader = new BufferedReader(filereader);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                fileRead.append(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }
        return (fileRead);
    }
}
