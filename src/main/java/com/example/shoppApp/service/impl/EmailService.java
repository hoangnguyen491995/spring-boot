package com.example.shoppApp.service.impl;

import com.example.shoppApp.entity.User;
import com.example.shoppApp.service.IEmailService;
import com.example.shoppApp.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailService implements IEmailService {
    final JavaMailSender mailSender;

    final IUserService userService;

    @Value("spring.mail.username")
    String myEmail;

    public void sendEmail() throws MessagingException {

        var birthdayUsers = userService.getUserByBirthday();
        if (birthdayUsers.isEmpty())
            return;
        var sender = this.myEmail;
        var recipients = birthdayUsers.stream().map(User::getEmail).toArray(String[]::new);
        var subject = "Happy birthday";
        var htmlContent = this.createTemplate();

        MimeMessage message = mailSender.createMimeMessage();
        InternetAddress[] addresses = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addresses[i] = new InternetAddress(recipients[i]);
        }
        message.setRecipients(MimeMessage.RecipientType.TO, addresses);
        message.setSubject(subject);
        message.setContent(htmlContent, "text/html; charset=utf-8");
        mailSender.send(message);

    }

    private String createTemplate() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<meta charset=\"UTF-8\">\n" +
                "\t<title>Chúc Mừng Sinh Nhật Từ Ngân Hàng OCB!</title>\n" +
                "\t<style>\n" +
                "\t\tbody {\n" +
                "\t\t\tfont-family: Arial, sans-serif;\n" +
                "\t\t\tfont-size: 16px;\n" +
                "\t\t\tline-height: 1.5;\n" +
                "\t\t\tcolor: #333;\n" +
                "\t\t\tbackground-color: #f7f7f7;\n" +
                "\t\t\tmargin: 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t}\n" +
                "\t\t.container {\n" +
                "\t\t\tmax-width: 600px;\n" +
                "\t\t\tmargin: 0 auto;\n" +
                "\t\t\tpadding: 20px;\n" +
                "\t\t\tbackground-color: #fff;\n" +
                "\t\t\tborder-radius: 5px;\n" +
                "\t\t\tbox-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);\n" +
                "\t\t}\n" +
                "\t\th1 {\n" +
                "\t\t\tfont-size: 32px;\n" +
                "\t\t\tfont-weight: bold;\n" +
                "\t\t\tmargin: 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tcolor: #002b5c;\n" +
                "\t\t}\n" +
                "\t\tp {\n" +
                "\t\t\tfont-size: 18px;\n" +
                "\t\t\tmargin: 20px 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t\ttext-align: justify;\n" +
                "\t\t\tcolor: #333;\n" +
                "\t\t}\n" +
                "\t\t.button {\n" +
                "\t\t\tdisplay: inline-block;\n" +
                "\t\t\tpadding: 10px 20px;\n" +
                "\t\t\tbackground-color: #fbcb00;\n" +
                "\t\t\tcolor: #002b5c;\n" +
                "\t\t\ttext-decoration: none;\n" +
                "\t\t\tborder-radius: 5px;\n" +
                "\t\t\tbox-shadow: 0px 0px 5px rgba(0, 0, 0, 0.1);\n" +
                "\t\t\ttransition: background-color 0.2s ease;\n" +
                "\t\t}\n" +
                "\t\t.button:hover {\n" +
                "\t\t\tbackground-color: #f5a200;\n" +
                "\t\t}\n" +
                "\t\t.footer {\n" +
                "\t\t\tfont-size: 14px;\n" +
                "\t\t\tmargin-top: 20px;\n" +
                "\t\t\tpadding-top: 20px;\n" +
                "\t\t\tborder-top: 1px solid #ccc;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tcolor: #999;\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div class=\"container\">\n" +
                "\t\t<h1>Chúc Mừng Sinh Nhật Từ Ngân Hàng OCB!</h1>\n" +
                "\t\t<p>Kính gửi anh/chị,</p>\n" +
                "\t\t<p>Chúc anh/chị sinh nhật vui vẻ và hạnh phúc! Ngân hàng OCB cảm ơn anh/chị đã đóng góp và cống hiến cho sự phát triển của chúng tôi.</p>\n" +
                "\t\t<p>Hãy tiếp tục nỗ lực và làm việc chăm chỉ để cùng nhau đạt được những thành công lớn hơn trong tương lai.</p>\n" +
                "\t\t<p>Chúc anh/chị một ngày sinh nhật thật ý nghĩa và đáng nhớ!</p>\n" +
                "\t\t<p>Trân trọng,</p>\n" +
                "\t\t<p>Ngân hàng OCB</p>\n" +
                "\t\t<a href=\"https://ocb.com.vn\" class=\"button\">Nhận quà tại đây</a>\n" +
                "\t\t<div class=\"footer\">\n" +
                "\t\t\t<p>Email này được gửi đến anh/chị vì anh/chị là nhân viên quý giá của Ngân hàng OCB. Nếu anh/chị không muốn nhận các email này nữa, anh/chị có thể <a href=\"#\">hủy đăng ký</a>.</p>\n" +
                "\t\t\t<p>Ngân hàng OCB</p>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>";
    }

}
