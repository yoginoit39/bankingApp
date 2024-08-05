//package org.bankingapp.bankingapplication.service.impl;
//
//
//import lombok.AllArgsConstructor;
//import org.bankingapp.bankingapplication.entity.Transaction;
//import org.bankingapp.bankingapplication.repository.TransactionRepository;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class BankStatementService {
//
//    //retrieve list of transactions within a date range given an account number
//    //generate a pdf file of the transactions
//    //send the file via email
//
//    private TransactionRepository transactionRepository;
//
//    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate){
////        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
////        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
//        LocalDateTime start = LocalDate.parse(startDate.trim(), DateTimeFormatter.ISO_DATE).atStartOfDay();
//        LocalDateTime end = LocalDate.parse(endDate.trim(), DateTimeFormatter.ISO_DATE).atTime(LocalTime.MAX);
//
//
//        List<Transaction> transactionList = transactionRepository.findAll().stream()
//                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
////                .filter(transaction -> transaction.getCreatedAt().isEqual(start))
////                .filter(transaction -> transaction.getCreatedAt().isEqual(end))
//                .filter(transaction -> !transaction.getCreatedAt().isBefore(start) && !transaction.getCreatedAt().isAfter(end))
//                .toList();
//
//        return transactionList;
//    }
//
//}
//


package org.bankingapp.bankingapplication.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bankingapp.bankingapplication.dto.EmailDetails;
import org.bankingapp.bankingapplication.entity.Transaction;
import org.bankingapp.bankingapplication.entity.User;
import org.bankingapp.bankingapplication.repository.TransactionRepository;
import org.bankingapp.bankingapplication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BankStatementService {

    private final TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private EmailService emailService;

    private  static final String FILE = "/Users/cdmstudent/Desktop/banking application/bankingApplication/bankStatements/MyStatement.pdf";

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws FileNotFoundException, DocumentException {
        LocalDate start = LocalDate.parse(startDate.trim(), DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate.trim(), DateTimeFormatter.ISO_DATE);

        List<Transaction> transactionList = transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedAt() != null)
                .filter(transaction -> !transaction.getCreatedAt().toLocalDate().isBefore(start) && !transaction.getCreatedAt().toLocalDate().isAfter(end))
                .collect(Collectors.toList());

        User user = userRepository.findByAccountNumber(accountNumber);
        String customerName = user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName();
//            generating statement pdf
        Rectangle statementSize = new Rectangle(PageSize.A4);
        Document document = new Document(statementSize);
        log.info("setting size of document");
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("Yogi's Bank"));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.BLUE);
        bankName.setPadding(20f);

        PdfPCell bankAddress = new PdfPCell(new Phrase("Chicago, IL USA"));
        bankAddress.setBorder(0);

        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);

        PdfPTable statementInfo = new PdfPTable(2);
        PdfPCell customerInfo = new PdfPCell(new Phrase("Start Date: " + startDate));
        customerInfo.setBorder(0);

        PdfPCell statement = new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
        statement.setBorder(0);
        PdfPCell stopDate = new PdfPCell(new Phrase("End Date: " + endDate));
        stopDate.setBorder(0);

        PdfPCell name = new PdfPCell(new Phrase("Customer Name: " + " " + customerName));
        name.setBorder(0);
        PdfPCell space = new PdfPCell();
        PdfPCell address = new PdfPCell(new Phrase("Customer Address:" + " " + user.getAddress()));
        address.setBorder(0);

        PdfPTable transactionsTable = new PdfPTable(4);
        PdfPCell date = new PdfPCell(new Phrase("Date"));
        date.setBackgroundColor(BaseColor.BLUE);
        date.setBorder(0);

        PdfPCell transactionType = new PdfPCell(new Phrase("Transaction Type"));
        transactionType.setBackgroundColor(BaseColor.BLUE);
        transactionType.setBorder(0);

        PdfPCell transactionAmount = new PdfPCell(new Phrase("Transaction Amount"));
        transactionAmount.setBackgroundColor(BaseColor.BLUE);
        transactionAmount.setBorder(0);

        PdfPCell status = new PdfPCell(new Phrase("Status"));
        status.setBackgroundColor(BaseColor.BLUE);
        status.setBorder(0);

        transactionsTable.addCell(date);
        transactionsTable.addCell(transactionType);
        transactionsTable.addCell(transactionAmount);
        transactionsTable.addCell(status);

//        populating the cells
        transactionList.forEach(transaction -> {
            transactionsTable.addCell(new Phrase(transaction.getCreatedAt().toString()));
            transactionsTable.addCell(new Phrase(transaction.getTransactionType()));
            transactionsTable.addCell(new Phrase(transaction.getAmount().toString()));
            transactionsTable.addCell(new Phrase(transaction.getStatus()));
        });

        statementInfo.addCell(customerInfo);
        statementInfo.addCell(statement);
        statementInfo.addCell(endDate);
        statementInfo.addCell(name);
        statementInfo.addCell(space);
        statementInfo.addCell(address);


        document.add(bankInfoTable);
        document.add(statementInfo);
        document.add(transactionsTable);

        document.close();

        EmailDetails emailDetails =  EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("STATEMENT OF ACCOUNT")
                .messageBody("Kindly find your requested account statement attached!")
                .attachment(FILE)
                .build();

        emailService.sendEmailWithAttachment(emailDetails);


        return transactionList;
    }




}

