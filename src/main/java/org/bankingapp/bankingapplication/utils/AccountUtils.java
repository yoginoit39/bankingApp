package org.bankingapp.bankingapplication.utils;

import jakarta.servlet.http.PushBuilder;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";
    public static final String ACCOUNT_EXISTS_MESSAGE = "This user already has an account";
    public static final String ACCOUNT_CREATION_SUCCESS = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account has been successfully created";
    public static final String ACCOUNT_NOT_EXISTS_CODE = "003";
    public static final String ACCOUNT_NOT_EXISTS_MESSAGE = "User with the provided account number does not exist";
    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_SUCCESS = "User Account Found";
    public static final String ACCOUNT_CREDITED_SUCCESS = "005";
    public static final String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User Account Credited Successfully";
    public static final String INSUFFICIENT_BALANCE_CODE = "006";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";
    public static final String ACCOUNT_DEBIT_SUCCESS_CODE = "007";
    public static final String ACCOUNT_DEBIT_SUCCESS_MESSAGE = "Account Successfully Debited";
    public static final String TRANSFER_SUCCESS_CODE = "008";
    public static final String TRANSFER_SUCCESS_MESSAGE = "Transfer Successful";





    public static String generateAccountNumber(){
        //    acct num to have a current year + random 6 digits
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

//    generate a random number between min and max
        int randomNumber = (int) Math.floor(Math.random() * (max-min + 1) + min);

//    converting the year and randomNumber of string and concatinating them together
        String year = String.valueOf(currentYear);
        String randomNumStr = String.valueOf(randomNumber);

//    appending them
        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(randomNumStr).toString();
    }

}
