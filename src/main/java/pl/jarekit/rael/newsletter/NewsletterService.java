package pl.jarekit.rael.newsletter;

import com.github.alexanderwe.bananaj.connection.MailChimpConnection;
import com.github.alexanderwe.bananaj.model.list.MailChimpList;
import com.github.alexanderwe.bananaj.model.list.member.EmailType;
import com.github.alexanderwe.bananaj.model.list.member.Member;
import com.github.alexanderwe.bananaj.model.list.member.MemberStatus;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.jarekit.rael.logs.Level;
import pl.jarekit.rael.logs.LogUtils;

import java.time.LocalDateTime;

@Service
public class NewsletterService {

    @Value("${newsletter.mailchimp.api-key}")
    private String apiKey;

    @Value("${newsletter.mailchimp.list-id}")
    private String listId;

    @Value("${newsletter.mailchimp.ip-address}")
    private String ipAddress;

    @SneakyThrows
    void addEmailToNewsletter(String email) {
        MailChimpConnection con = new MailChimpConnection(apiKey);
        MailChimpList yourList = con.getList(listId);

        LocalDateTime timeStamp = LocalDateTime.now();

        Member member = new Member.Builder()
                .emailAddress(email)
                .list(yourList)
                .emailType(EmailType.HTML)
                .status(MemberStatus.SUBSCRIBED)
                .statusIfNew(MemberStatus.SUBSCRIBED)
                .ipSignup(ipAddress)
                .timestampSignup(timeStamp)
                .ipOpt(ipAddress)
                .timestampOpt(timeStamp)
                .build();
        yourList.addOrUpdateMember(member);
        LogUtils.saveLogStatic("Added new subscriber to newsletter: " + email , Level.INFO);
    }
}
