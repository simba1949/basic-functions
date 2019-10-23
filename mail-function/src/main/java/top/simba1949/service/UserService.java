package top.simba1949.service;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;
import top.simba1949.common.MailInfo;
import top.simba1949.config.FreemarkerConfig;
import top.simba1949.util.EmailUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author Theodore
 * @Date 2019/10/23 14:41
 */
@Service
public class UserService {

    public void getEmailCode(String email) {
        // 获取邮箱验证码
        String verificationCode = UUID.randomUUID().toString().replaceAll("-", "");

        // 邮件普通html内容
        String subject = "用户密码找回";
        StringBuilder sb = new StringBuilder("亲爱的伙伴，您好！<br/>\n");
        sb.append("您收到这封这封电子邮件是因为您 (也可能是某人冒充您的名义) 提交了找回密码请求<br/>\n");
        sb.append("<b>邀请码：").append(verificationCode).append("</b><br/>\n");
        sb.append("为了保证您帐号的安全性，该验证码有效期为15分钟，并且使用一次后将失效!<br/>\n");
        sb.append("如果这不是您本人提出的资金密码修改申请, 则无需进行任何操作，并可以放心地忽略此电子邮件。<br/>\n");
        sb.append("YUE-SHE PAN-GU");
        String content = sb.toString();

        // 邮件发送html模板内容
        // 模板根目录
        String path = this.getClass().getResource("/").getFile();
        Map dataMap = new HashMap<>(16);
        dataMap.put("code", verificationCode);
        // 生成的模板转换成字符串输出
        String htmlStr = "";
        try {
            htmlStr = FreemarkerConfig.templateToString(path + "/mail-template", "VerificationCodeTmp.ftl", dataMap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        // 构建邮件信息
        MailInfo mailInfo = new MailInfo();
        mailInfo.setToAddress(Arrays.asList(email));
        mailInfo.setSubject(subject);
        // mailInfo.setContent(content);
        mailInfo.setContent(htmlStr);

        EmailUtils.sendEmail(mailInfo);
    }
}
