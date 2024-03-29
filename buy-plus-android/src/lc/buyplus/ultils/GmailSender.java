package lc.buyplus.ultils;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.util.Log;

class Mail extends Authenticator {
	private String mUser;
	private String mPass;
	private String[] mTo;
	private String mFrom;
	private String mPort;
	private String mSport;
	private String mHost;
	private String mSubject;
	private String mBody;
	private boolean mAuth;
	private boolean mDebuggable;
	private Multipart mMultipart;
	public Mail() {
		mHost = "smtp.gmail.com"; 
		mPort = "465"; 
		mSport = "465"; 
		mUser = ""; 
		mPass = ""; 
		mFrom = ""; 
		mSubject = ""; 
		mBody = ""; 
		mDebuggable = false; // debug mode on or off - default off
		mAuth = true; // smtp authentication - default on
		mMultipart = new MimeMultipart();
		// There is something wrong with MailCap, javamail can not find a
		// handler for the multipart/mixed part, so this bit needs to be added.
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
		mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
		mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
		mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
		mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
		mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
		CommandMap.setDefaultCommandMap(mc);
	}
	public Mail(String user, String pass) {
		this();
		mUser = user;
		mPass = pass;
	}
	public void setTo(String[] toArr){
		mTo = toArr;
	}
    public void setFrom(String from){
    	mFrom = from;
    }
    public void setSubject(String subject){
    	mSubject = subject;
    }
	public boolean send() throws Exception {
		Properties props = setProperties();
		if (!mUser.equals("") && !mPass.equals("") && mTo.length > 0
				&& !mFrom.equals("") && !mSubject.equals("")
				&& !mBody.equals("")) {
			Session session = Session.getInstance(props, this);
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(mFrom));
			InternetAddress[] addressTo = new InternetAddress[mTo.length];
			for (int i = 0; i < mTo.length; i++) {
				addressTo[i] = new InternetAddress(mTo[i]);
			}
			msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);
			msg.setSubject(mSubject);
			msg.setSentDate(new Date());
			// setup message body
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(mBody);
			mMultipart.addBodyPart(messageBodyPart);
			// Put parts in message
			msg.setContent(mMultipart);
			// send email
			Transport.send(msg);
			return true;
		} else {
			return false;
		}
	}

	public void addAttachment(String filename) throws Exception {
		BodyPart messageBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(filename);
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filename);
		mMultipart.addBodyPart(messageBodyPart);
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(mUser, mPass);
	}

	private Properties setProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.host", mHost);
		if (mDebuggable) {
			props.put("mail.debug", "true");
		}
		if (mAuth) {
			props.put("mail.smtp.auth", "true");
		}
		props.put("mail.smtp.port", mPort);
		props.put("mail.smtp.socketFactory.port", mSport);
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		return props;
	}
	// the getters and setters
	public String getBody() {
		return mBody;
	}
	public void setBody(String mBody) {
		this.mBody = mBody;
	}
}
public class GmailSender {
	public static void sendEmail(final String userName, final String password, 
			final String name, final String[] receiveList, final String subject, 
			final String content, final String[] attachLinks) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Mail m = new Mail(userName, password);
				m.setTo(receiveList);
				m.setFrom(name);
				m.setSubject(subject);
				m.setBody(content);
				try {
					if(attachLinks != null) {
						for(int i=0; i<attachLinks.length; i++) {
							m.addAttachment(attachLinks[i]);
						}
					}
					if(m.send()) {
						Log.e("Viadroid Emailsender", "success");
					} else {
						Log.e("Viadroid Emailsender", "failed");
					}
				} catch(Exception e) {
					Log.e("Darkmoon Emailsender", "Could not send email", e);
				}
			}
		}).start();
	}
}
