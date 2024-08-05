package org.bankingapp.bankingapplication.service.impl;

import org.bankingapp.bankingapplication.dto.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
    void sendEmailWithAttachment(EmailDetails emailDetails);

}



