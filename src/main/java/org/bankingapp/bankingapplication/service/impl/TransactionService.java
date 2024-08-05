package org.bankingapp.bankingapplication.service.impl;

import org.bankingapp.bankingapplication.dto.TransactionDto;
import org.bankingapp.bankingapplication.entity.Transaction;

public interface TransactionService {

    void saveTransaction(TransactionDto transactionDto);

}
