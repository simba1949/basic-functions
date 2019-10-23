package top.simba1949.config;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

/**
 * @Author Theodore
 * @Date 2019/10/23 14:43
 */
@Data
@Configuration
public class FreemarkerConfig {
    /**
     * 初始化 FreeMarker 相关配置
     * @param path
     * @return
     * @throws IOException
     */
    private static freemarker.template.Configuration init(String path) throws IOException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.getVersion());
        configuration.setEncoding(Locale.CHINESE, "utf-8");
        configuration.setDirectoryForTemplateLoading(new File(path));
        return configuration;
    }

    /**
     * 获取模板对象
     * @param path
     * @param templateName
     * @return
     * @throws IOException
     */
    public static Template getTemplate(String path, String templateName) throws IOException {
        freemarker.template.Configuration configuration = init(path);
        Template template = configuration.getTemplate(templateName);
        return template;
    }

    /**
     * 将模板转出字符串
     * @param path
     * @param templateName
     * @param dataMap
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String templateToString(String path, String templateName, Map<String, Object> dataMap) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Template template = getTemplate(path, templateName);
        template.process(dataMap, stringWriter);;
        return stringWriter.toString();
    }
}
