package red.semipro.business.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/mail/MailMessages.properties")
public class EmailMessageProperty {

    @Autowired
    private Environment environment;
    
    public String get(String key){
        return environment.getProperty(key);
    }
}
