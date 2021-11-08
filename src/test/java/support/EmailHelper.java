package support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import com.sun.mail.util.MailSSLSocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class EmailHelper {

   private static Log logger = LogFactory.getLog(EmailHelper.class);

   public List<EmailObject> getLatestMessages(int max) {
      try {
         logger.info("getLatestMessages(" + max + ")");
         // create properties field
         Properties properties = new Properties();

         MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
         socketFactory.setTrustAllHosts(true);
         properties.put("mail.pop3s.ssl.socketFactory", socketFactory);

         properties.put("mail.pop3.host", Util.EMAIL_HOST);
         properties.put("mail.pop3.port", "995");
         properties.put("mail.pop3.starttls.enable", "true");
         properties.put("mail.pop3.ssl.checkserveridentity", "false");
         properties.put("mail.pop3.ssl.trust", "*");
         properties.put("mail.pop3s.ssl.checkserveridentity", "false");
         properties.put("mail.pop3s.ssl.trust", "*");
         Session emailSession = Session.getDefaultInstance(properties);

         // create the POP3 store object and connect with the pop server
         Store store = emailSession.getStore("pop3s");
         
         logger.info("Connecting to " + Util.EMAIL_HOST);
         store.connect(Util.EMAIL_HOST, Util.EMAIL_USERNAME, Util.EMAIL_PASSWORD);

         // create the folder object and open it
         Folder emailFolder = store.getFolder("INBOX");
         logger.info("Opening folder...");
         emailFolder.open(Folder.READ_ONLY);
         logger.info("Folder opened");

         int end = emailFolder.getMessageCount();
         int start = end - max + 1;
         logger.info("Message count: " + end);
         Message[] messages = null;
         if (emailFolder.getMessageCount() < max) {
            messages = emailFolder.getMessages();
         } else {
            messages = emailFolder.getMessages(start, end);
         }
         logger.info("Messages: " + messages.length);

         List<EmailObject> lstMessages = new ArrayList<EmailObject>();

         for (int n = messages.length - 1, i = 0; n >= 0; n--, i++) {
            Message message = messages[n];
            EmailObject eo = new EmailObject();
            //Call the functions to have the email metadata
            eo.setSubject(message.getSubject());
            if (message.getFrom() != null) {
               for (Address a : message.getFrom()) {
                  eo.getFrom().add(a.toString());
               }
            }
            //logger.info("Received Date: " + message.getReceivedDate());
            eo.setSentDate(message.getSentDate());

            //logger.info("Sent Date: " + message.getSentDate());
            /*Enumeration<Header> headers = message.getAllHeaders();
            for (Header h : Collections.list(headers)) {
               logger.info("Header: Name = " + h.getName() + ", Value = " + h.getValue());
            }*/


            lstMessages.add(eo);
         }

         // close the store and folder objects
         emailFolder.close(false);
         store.close();

         return lstMessages;

      } catch (NoSuchProviderException e) {
         e.printStackTrace();
      } catch (MessagingException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return null;
   }
}