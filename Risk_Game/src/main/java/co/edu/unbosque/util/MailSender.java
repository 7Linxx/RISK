 package co.edu.unbosque.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

	private static String emailFrom = "mjpu117@gmail.com";
	private static String passwordFrom = "vwabzakgmbqcleri";

	private static Properties prop = new Properties();
	private static Session session;
	private static MimeMessage mimeMessage;

	public static void sendEamil(String emailTo,String body)
			throws AddressException, MessagingException {
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.setProperty("mail.smtp.port", "587");
		prop.setProperty("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		session = Session.getDefaultInstance(prop ,new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailFrom, passwordFrom);
            }});

		String content = body;

		String subject = "Risk Game | Game Summary";

		mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(emailFrom));
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
		mimeMessage.setSubject(subject);
		
		mimeMessage.setSubject(subject);
		mimeMessage.setText(content);
		
		Transport.send(mimeMessage);

	}

	public static String getEmailFrom() {
		return emailFrom;
	}

	public static void setEmailFrom(String emailFrom) {
		MailSender.emailFrom = emailFrom;
	}

	public static String getPasswordFrom() {
		return passwordFrom;
	}

	public static void setPasswordFrom(String passwordFrom) {
		MailSender.passwordFrom = passwordFrom;
	}

	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		MailSender.prop = prop;
	}

	public static Session getSession() {
		return session;
	}

	public static void setSession(Session session) {
		MailSender.session = session;
	}

	public static MimeMessage getMimeMessage() {
		return mimeMessage;
	}

	public static void setMimeMessage(MimeMessage mimeMessage) {
		MailSender.mimeMessage = mimeMessage;
	}

}
