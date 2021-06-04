package com.document.documentTranslator.service.Email;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Service
public class EmailService {

    Logger logger = Logger.getLogger(EmailService.class);

    public void sendPasswordRecoveryMailMessage(String to, String password) {
        String text = "<p>Bonjour,</p>\n" +
                "<p>Vous avez demand&eacute; une r&eacute;initialisation de votre mot de passe. Votre mot de passe provisoire est</p>\n" +
                "<p> %s </p>\n" +
                "<p>Par mesure de s&eacute;curit&eacute;, ce mot de passe n&rsquo;est valable que 48h et doit &ecirc;tre remplac&eacute; par un nouveau, via Votre espace client &gt; Profil.</p>\n" +
                "<p>Cordialement,</p>\n" +
                "<p><a href=\"https://francedoc.fr/\"><em>https://francedoc.fr/</em></a></p>\n" +
                "<p><em>Service de traductions certifi&eacute;es (asserment&eacute;es)</em></p>\n" +
                "<p>----------------------------</p>\n" +
                "<p><em>Vous pouvez voir les mentions l&eacute;gales de vente et de protection des donn&eacute;es &agrave; caract&egrave;re personnel (RGPD) en cliquant&nbsp;</em><a href=\"https://www.sfr.fr/telephonie-mobile/tarifs-conditions.html\"><em>https://francedoc.fr/LegalNotes</em></a>&nbsp;<em>.</em></p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p style=\"text-align: right;\">سلام</p>\n" +
                "<p style=\"text-align: right;\">شما درخواست بازیابی رمزعبور حساب کاربری خود را داده&zwnj;اید. رمز عبور موقتی شما در زیر آورده شده است</p>\n" +
                "<p style=\"text-align: right;\"> %s </p>\n" +
                "<p style=\"text-align: right;\">به دلایل امنیتی، این رمز تنها 48 ساعت اعتبار دارد و باید سریعا از طریق حساب کاربری &gt; پروفایل با یک رمز جدید جایگزین شود</p>\n" +
                "<p style=\"text-align: right;\">،احترام</p>\n" +
                "<p style=\"text-align: right;\"><em>فرانسدک</em></p>\n" +
                "<p style=\"text-align: right;\"><em>خدمات ترجمه رسمی</em></p>\n" +
                "<p style=\"text-align: right;\">----------------------------</p>\n" +
                "<p style=\"text-align: right;\">با کلیک روی لینک زیر می&zwnj;توانید موارد قانونی فروش و حفاظت از داده&zwnj;های شخصی را ببینید</p>\n" +
                "<p style=\"text-align: right;\"><a href=\"https://www.sfr.fr/telephonie-mobile/tarifs-conditions.html\"><em>https://francedoc.fr/LegalNotes</em></a></p>\n" +
                "<p>&nbsp;</p>";
        sendSimpleMessage(to, "FranceDoc - Password Recovery", String.format(text, password, password));
    }

    public void sendOrderDoneMailMessage(String to, String id) {
        String text = "<p>Bonjour,</p>\n" +
                "<p>Votre commande n&deg; %s est livr&eacute;e et t&eacute;l&eacute;chargeable via votre espace client &gt; liste commandes.</p>\n" +
                "<p>Une version papier vous sera &eacute;galement livr&eacute;e par la poste &agrave; l&rsquo;adresse demand&eacute;e.</p>\n" +
                "<p>Votre facture est &eacute;galement disponible dans votre espace client.</p>\n" +
                "<p>Nous vous remercions de votre confiance.</p>\n" +
                "<p>A bient&ocirc;t <br /><a href=\"https://francedoc.fr/\"><em>https://francedoc.fr/</em></a></p>\n" +
                "<p><em>Service de traductions certifi&eacute;es (asserment&eacute;es)</em></p>\n" +
                "<p>----------------------------</p>\n" +
                "<p><em>Vous pouvez voir les mentions l&eacute;gales de vente et de protection des donn&eacute;es &agrave; caract&egrave;re personnel (RGPD) en cliquant <a href=\"https://francedoc.fr/\">https://francedoc.fr/LegalNotes</a> </em><em>.<br /><br /></em></p>\n" +
                "<p style=\"text-align: right;\">،سلام</p>\n" +
                "<p style=\"text-align: right;\">سفارش شماره</p>\n" +
                "<p style=\"text-align: right;\">%s</p>\n" +
                "<p style=\"text-align: right;\">آماده و از طریق حساب کاربری &gt; لیست سفارش ها قابل دانلود است</p>\n" +
                "<p style=\"text-align: right;\">&nbsp; فاکتور مربوطه نیز در حساب کاربری موجود است. نسخه کاغذی نیزبه آدرس شما پست خواهد شد</p>\n" +
                "<p style=\"text-align: right;\">.ما از اعتماد شما سپاسگزاریم</p>\n" +
                "<p style=\"text-align: right;\">،احترام</p>\n" +
                "<p style=\"text-align: right;\"><em>فرانسدک</em></p>\n" +
                "<p style=\"text-align: right;\"><em>خدمات ترجمه رسمی</em></p>\n" +
                "<p style=\"text-align: right;\">----------------------------</p>\n" +
                "<p style=\"text-align: right;\"><em>با کلیک بر روی لینک زیر می توانید موارد قانونی فروش و حفاظت از اطلاعات شخصی را مشاهده کنید</em></p>\n" +
                "<p><em><a href=\"https://francedoc.fr/\">https://francedoc.fr/LegalNotes</a></em></p>";
        sendSimpleMessage(to, "FranceDoc - Your order is ready", String.format(text, id, id));
    }

    public void sendWarningMail(String to) {
        String text = "<p>Bonjour</p>\n" +
                "<p>Nous remarquons que vous n'avez pas utilis&eacute; votre compte client depuis 6 mois.<br />Sans connexion de votre part d'ici 30 jours, votre compte sera radi&eacute; et vous ne pourrez malheureusement plus vous connecter et vous perdez les informations saisies.<br />Alors n'attendez plus et connectez-vous pour profiter de tous nos services de traduction.</p>\n" +
                "<p>Cordialement,</p>\n" +
                "<p><a href=\"https://francedoc.fr/\"><em>https://francedoc.fr/</em></a></p>\n" +
                "<p><em>Service de traductions certifi&eacute;es (asserment&eacute;es)</em></p>\n" +
                "<p>----------------------------</p>\n" +
                "<p><em>Vous pouvez voir les mentions l&eacute;gales de vente et de protection des donn&eacute;es &agrave; caract&egrave;re personnel (RGPD) en cliquant <a href=\"https://francedoc.fr/\">https://francedoc.fr/LegalNotes</a> </em><em>.</em></p>\n" +
                "<p style=\"text-align: right;\">،سلام</p>\n" +
                "<p style=\"text-align: right;\">.شما از 6 ماه گذشته از حساب کاربری خود استفاده نکرده اید</p>\n" +
                "<p style=\"text-align: right;\">بدون اتصال از طرف شما طی 30 روز آینده، حساب شما لغو می شود و متأسفانه دیگر قادر به اتصال نخواهید بود و اطلاعات ثبت شده حذف می شود</p>\n" +
                "<p style=\"text-align: right;\">.بنابراین منتظر نمانید و وارد حساب کاربری شوید تا از خدمات ترجمه ما بهره مند شوید</p>\n" +
                "<p style=\"text-align: right;\">،با تشکر</p>\n" +
                "<p style=\"text-align: right;\">فرانسدک</p>\n" +
                "<p style=\"text-align: right;\">----------------------------</p>\n" +
                "<p style=\"text-align: right;\"><em>با کلیک بر روی لینک زیر می توانید موارد قانونی فروش و حفاظت از اطلاعات شخصی را مشاهده کنید</em></p>\n" +
                "<p style=\"text-align: right;\"><em><a href=\"https://francedoc.fr/\">https://francedoc.fr/LegalNotes</a></em></p>";
        sendSimpleMessage(to, "FranceDoc - Warning", text);
    }

    public void sendSimpleMessage(
            String to, String subject, String text) {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("kiarash.mci.4@gmail.com", "13579kiarash");
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("kiarash.mci.4@gmail.com"));
            msg.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(text, "text/html; charset=UTF-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            msg.setContent(multipart);
            Transport.send(msg);
        } catch (Exception e) {
            logger.info("Error sending email:", e);
        }
    }
}
