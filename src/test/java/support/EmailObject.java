package support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represent an email.
 * 
 * It was created to simplify handling of email messages. If we get the emails
 * from the inbox, close the inbox and then try to read the email's metadata,
 * we get an exception.
 * 
 * With this object we can keep the metadata alive for use later
 */
public class EmailObject {

    private String subject;
    private List<String> from = new ArrayList<String>();
    private Date sentDate;

    public String getSubject() {
        return this.subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getFrom() {
        return this.from;
    }
    public void setFrom(List<String> from) {
        this.from = from;
    }

    public Date getSentDate() {
        return this.sentDate;
    }
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

}