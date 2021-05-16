package com.document.documentTranslator.service.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    @Autowired
    private JavaMailSender emailSender;

    public void sendPasswordRecoveryMailMessage(String to, String password) {
        String text = "Bonjour,\n" +
                "Vous avez demandé une réinitialisation de votre mot de passe. Votre mot de passe est %s . Par mesure de sécurité, il est fortement conseillé de remplacer ce mot de passe par un nouveau, via Votre espace client > Profil.\n" +
                "Cordialement,\n" +
                "https://francedoc.fr/\n" +
                "Service de traductions certifiées (assermentées)\n";
        text += "سلام \n" +
                "شما درخواست بازیابی رمز عبور حساب کاربری خود را دارید. رمز عبور شما موقتی %s است. این رمز ۴۸ ساعت اعتبار دارد. به دلایل امنیتی، اکیداً به شما توصیه می شود که این رمزعبور را از طریق حساب کاربری > پروفایل، با یک رمز جدید جایگزین کنید.\n" +
                "احترام،\n" +
                "فرانسدک\n" +
                "خدمات ترجمه رسمی\n";
        sendSimpleMessage(to, "FranceDoc - Password Recovery", String.format(text, password, password));
    }

    public void sendSimpleMessage(
            String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kiarash.mci.4@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
