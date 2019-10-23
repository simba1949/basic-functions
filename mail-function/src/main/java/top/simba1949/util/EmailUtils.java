package top.simba1949.util;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import top.simba1949.common.MailInfo;
import top.simba1949.config.MailConfig;

import java.util.List;

/**
 * @Author Theodore
 * @Date 2019/10/23 14:45
 */
public class EmailUtils {
    /**
     * 发送邮件
     * @param mailInfo
     */
    public static void sendEmail(MailInfo mailInfo){

        try {
            HtmlEmail email = new HtmlEmail();
            // mail.properties 配置信息
            email.setHostName(MailConfig.emailServerHost);
            email.setFrom(MailConfig.senderAccount, MailConfig.senderUsername);
            email.setAuthentication(MailConfig.senderAccount, MailConfig.senderPassword);
            email.setCharset("UTF-8");

            // 邮件主题
            email.setSubject(mailInfo.getSubject());
            // 邮件内容
            email.setHtmlMsg(mailInfo.getContent());
            // 收件人
            List<String> toAddress = mailInfo.getToAddress();
            if (null != toAddress && toAddress.size() > 0) {
                for (int i = 0; i < toAddress.size(); i++) {
                    email.addTo(toAddress.get(i));
                }
            }
            // 抄送人
            List<String> ccAddress = mailInfo.getCcAddress();
            if (null != ccAddress && ccAddress.size() > 0) {
                for (int i = 0; i < ccAddress.size(); i++) {
                    email.addCc(ccAddress.get(i));
                }
            }
            // 密送人
            List<String> bccAddress = mailInfo.getBccAddress();
            if (null != bccAddress && bccAddress.size() > 0) {
                for (int i = 0; i < bccAddress.size(); i++) {
                    email.addBcc(ccAddress.get(i));
                }
            }
            // 添加附件
            List<EmailAttachment> attachments = mailInfo.getAttachments();
            if (null != attachments && attachments.size() > 0) {
                for (int i = 0; i < attachments.size(); i++) {
                    email.attach(attachments.get(i));
                }
            }

            // 发送邮件
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
