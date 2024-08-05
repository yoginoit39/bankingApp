package org.bankingapp.bankingapplication.service.impl;


import org.bankingapp.bankingapplication.dto.*;

public interface UserService {

   BankResponse createAccount(UserRequest userRequest);
   BankResponse balanceEnquiry(EnquiryRequest request);
   String nameEnquiry(EnquiryRequest request);

   BankResponse creditAccount(CreditDebitRequest request);

   BankResponse debitAccount(CreditDebitRequest request);
   BankResponse transfer(TransferRequest request);

}
