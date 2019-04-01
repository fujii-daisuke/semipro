package red.semipro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Autowired
//    private MessageSource messageSource;
//
//    @Bean
//    public LocalValidatorFactoryBean validator() {
//        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
//        localValidatorFactoryBean.setValidationMessageSource(messageSource);
//        return localValidatorFactoryBean;
//    }
//
//    @Override
//    public Validator getValidator() {
//        return validator();
//    }
    
    @Bean
    public TransactionTokenInterceptor transactionTokenInterceptor() {
        return new TransactionTokenInterceptor(1);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(transactionTokenInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/resources/**")
            .excludePathPatterns("/**/*.html");
    }
    
//    @Bean
//    public TemplateEngine textTemplateEngine() {
//        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.addTemplateResolver(textTemplateResolver());
//        templateEngine.setTemplateEngineMessageSource(textMessageSource());
//        return templateEngine;
//    }
//
//    private ITemplateResolver textTemplateResolver() {
//        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setOrder(Integer.valueOf(1));
//        templateResolver.setPrefix("/mail/");
//        templateResolver.setSuffix(".txt");
//        templateResolver.setTemplateMode(TemplateMode.TEXT);
//        templateResolver.setCharacterEncoding("utf-8");
//        templateResolver.setCacheable(false);
//        return templateResolver;
//    }
//
//    @Bean
//    public ResourceBundleMessageSource textMessageSource() {
//        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("mail/Messages");
//        return messageSource;
//    }
    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(); //single threaded by default
    }
}
