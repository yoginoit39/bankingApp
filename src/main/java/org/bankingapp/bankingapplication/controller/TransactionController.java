package org.bankingapp.bankingapplication.controller;


import com.itextpdf.text.DocumentException;
import org.bankingapp.bankingapplication.entity.Transaction;
import org.bankingapp.bankingapplication.service.impl.BankStatementService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/bankStatement")
public class TransactionController {

    private final BankStatementService bankStatement;

    public TransactionController(BankStatementService bankStatement) {
        this.bankStatement = bankStatement;
    }


    @GetMapping
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate) throws DocumentException, FileNotFoundException {

        return bankStatement.generateStatement(accountNumber, startDate, endDate);

    }


}
