package top.simba1949.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author Theodore
 * @Date 2019/10/23 14:44
 */
@Data
@Configuration
@PropertySource(value = "classpath:mail.properties", encoding = "utf-8")
public class MailConfig {

    public static String emailServerHost;
    public static String senderAccount;
    public static String senderPassword;
    public static String senderUsername;

    @Autowired
    public void setEmailServerHost(@Value("${email.server.host}")String host) {
        emailServerHost = host;
    }
    @Autowired
    public void setSenderAccount(@Value("${email.sender.account}") String account){
        senderAccount = account;
    }
    @Autowired
    public void setSenderPassword(@Value("${email.sender.password}")String password){
        senderPassword = password;
    }
    @Autowired
    public void setSenderUsername(@Value("${email.sender.username}")String username){
        senderUsername = username;
    }
}
