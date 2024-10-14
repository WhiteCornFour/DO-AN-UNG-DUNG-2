package com.example.doanungdung2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doanungdung2.R;

public class User_ForgotPassword extends AppCompatActivity {

    EditText edtMailForgotPass;
    TextView tvSignInForgotPass;
    Button btnSendForgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgotpassword);
        addControl();

        addEvent();
    }

    void addControl()
    {
        edtMailForgotPass = findViewById(R.id.edtMailForgotPass);
        tvSignInForgotPass = findViewById(R.id.tvSignInForgotPass);
        btnSendForgotPass = findViewById(R.id.btnSendForgotPass);
    }

    void addEvent()
    {
//        btnSendForgotPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//
//                    String stringSenderEmail = "bapnhn1810@gmail.com";
//                    String stringReceiverEmail = "hoangnam18102004@gmail.com";
//                    String stringPasswordSenderEmail = "MatKhau123";
//
//                    String stringHost = "smtp.gmail.com";
//                    Properties properties = System.getProperties();
//
//                    properties.put("mail.smtp.host", stringHost);
//                    properties.put("mail.smtp.port", "465");
//                    properties.put("mail.smtp.ssl.enable", "true");
//                    properties.put("mail.smtp.auth", "true");
//
//                    javax.mail.Session session = Session.getDefaultInstance(properties, new Authenticator() {
//                        @Override
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
//                        }
//                    });
//
//                    MimeMessage mimeMessage = new MimeMessage(session);
//
//                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));
//
//                    mimeMessage.setSubject("Subject: Android App Email");
//                    mimeMessage.setText("programmer send u this email \n\n hi");
//
//                    Thread thread = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Transport.send(mimeMessage);
//                            } catch (MessagingException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                    });
//
//                    thread.start();
//
//                } catch (AddressException e) {
//                    throw new RuntimeException(e);
//                } catch (MessagingException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
    }
}