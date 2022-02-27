package com.userForCredit.getLoan.core.utilities.sms;

import com.userForCredit.getLoan.business.abstracts.CreditService;
import com.userForCredit.getLoan.business.abstracts.UserService;
import com.userForCredit.getLoan.entities.Credit;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SmsService {

    //Sadece kredi onaylandığı durumda sms gönderir
    public void sendSmsIfCreditApproved(Credit smsSuccess) {
            log.info("Sayın " + smsSuccess.getUser().getFirstName() + " " + smsSuccess.getUser().getLastName()
            + " krediniz onaylanmıştır."
            + "\nKredi tutarınız : " + smsSuccess.getCreditAmount()
            + "\nKredi onaylanma tarihi : " + smsSuccess.getCreatedDate());
    }
}
