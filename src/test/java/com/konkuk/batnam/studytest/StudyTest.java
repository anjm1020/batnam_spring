package com.konkuk.batnam.studytest;


import org.apache.logging.log4j.message.SimpleMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class StudyTest {

    private final String appPw = "mqphbqrsxuhzepqr";
    private final String imgStr = "https://cdn.aitimes.com/news/photo/202204/143854_149285_5324.jpg";

    @Autowired
    private JavaMailSender mailSender;

    @Test
    void mailTest() throws Exception {

        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject("TEST");
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(toHTMLTag("h1", "BATMAN - ALERT"));
        sb.append(toHTMLTag("span", "Too many objects detected"));
        sb.append("<ul>");
        sb.append(toHTMLTag("li","date", "2022-01-01"));
        sb.append(toHTMLTag("li","airStrip", "StripA"));
        sb.append(toHTMLTag("li","Sector", "SectorG"));
        sb.append(toHTMLTag("li","object", "BIRD"));
        sb.append(toHTMLTag("h2", "Captured Image"));
        sb.append("</ul>");
        sb.append("<img src=\"" + imgStr + "\"/>");
        sb.append("</html>");
        message.setContent(sb.toString(),"text/html");
        message.setContent(template(), "text/html");
        message.addRecipients(Message.RecipientType.TO,"ehdtndla123@gmail.com");
        message.addRecipients(Message.RecipientType.TO,"annie526289@gmail.com");
        message.addRecipients(Message.RecipientType.TO,"anjm1020@gmail.com");
        mailSender.send(message);

    }

    String template() {
        return "<h1>BATMAN-ALERT</h1>\n" +
                "<span>Too many objects detected</span>\n" +
                "<ul>\n" +
                "    <li>date : 2022-01-01</li>\n" +
                "    <li>airStrip : 2022-01-01</li>\n" +
                "    <li>sector : 2022-01-01</li>\n" +
                "    <li>object : 2022-01-01</li>\n" +
                "</ul>\n" +
                "<h2>Captured Image</h2>\n" +
                "<img src=\"https://cdn.aitimes.com/news/photo/202204/143854_149285_5324.jpg\"/>";
    }

    String toHTMLTag(String tag, String attr, String value) {
        return "<" + tag + ">" + attr + " : " + value + "</" + tag + ">";
    }

    String toHTMLTag(String tag, String value) {
        return "<" + tag + ">" + value + "</" + tag + ">";
    }

    void setMessage(MimeMessage message,String imgSrc) throws Exception {
        message.setContent("<h1>Message</h1>","text/html");
        message.setContent("<img src=\""+imgSrc+"\"/>","text/html");
    }
}
