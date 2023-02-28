package com.example.AccountMS.controller;

import com.example.AccountMS.entity.Account;
import com.example.AccountMS.repository.AccountRepository;
import com.example.AccountMS.service.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.*;
import java.util.*;
import java.lang.Integer;

@RestController
public class AccountController {
    @Autowired
    private AccountServices service;
    @Autowired
    private AccountRepository repository;
    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/addAccount")
    public ResponseEntity<Object> addAccount(@Valid @RequestBody Account account,BindingResult result) {
        String msg = "This method is used to create a account for particular customer and added validation constraints and" +
                     " if captured any input validation errors,send a response with the error messages in the response body and" +
                     " We create a account only for existing customer";
        createFile(msg);
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String customer = restTemplate.exchange("http://localhost:9293/template/customerById/" + account.getCustomerId(), HttpMethod.GET, entity, String.class).getBody();
        if (customer.equals("yes")) {//if customer id is present in customerMS,adding account in AccountMS
            service.saveAccount(account);
            repository.save(account);
            return ResponseEntity.ok(account);
        } else {//if customer id does not exist in customerMS,not allowing to add account in AccountMS
            return ResponseEntity.badRequest().body("Customer ID does not exist.");
        }
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
    public ResponseEntity<Object> findByAccountNo(@PathVariable("accountNumber") String idString) {
        String msg = "This method is used to get the account by accountNumber";
        createFile(msg);
        Account account = new Account();
        try {
            int ids = Integer.parseInt(idString);//check format of accountNumber
            Optional<Account> optionalAccount =repository.findById(ids);
            if (!optionalAccount.isPresent()) {//check given accountNumber availability
                return ResponseEntity.badRequest().body("AccountNo does not exist.");
            } else {
                account = repository.findById(ids).orElse(null);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid AccountNumber format...AccountNumber must be Numeric");
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping("/accountByCustomerId/{customerId}")
    public List<Account> findByCustomerId(@PathVariable int customerId) {
        String msg = "This method is used to get the accounts of particular customer id";
        createFile(msg);
        return service.getAccountByCustomerId(customerId);
    }

    @DeleteMapping("/delete/{accountNumber}")
    public String deleteAccount(@PathVariable int accountNumber) {
        String msg = "This method is used to delete the account by accountNumber";
        createFile(msg);
        return service.deleteAccount(accountNumber);
    }

    @DeleteMapping("/deleteByCustomerId/{customerId}")
    public String deleteAccountByCustomerId(@PathVariable int customerId) {
        String msg = "This method is used to delete the account by customer id";
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
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody Account account, BindingResult result){
        String msg = "This method is used to Update the existing customer" +
                     "Before updating the customer it will Validate the request body " +
                     "If captured any input validation errors,send a response with the error messages in the response body";
        createFile(msg);
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        Account updatedAccount =service.updateAccount(account);
        return ResponseEntity.ok(updatedAccount);
    }

    @RequestMapping("/template/customers")
    public String getCustomers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        String msg = "This method is used to get the customers from CustomerMs using restTemplate";
        createFile(msg);
        return restTemplate.exchange("http://localhost:9293/customers", HttpMethod.GET, entity, String.class).getBody();
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
