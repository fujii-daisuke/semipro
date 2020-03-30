package red.semipro.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;
import red.semipro.share.converter.StringToBusinessTypeConverter;
import red.semipro.share.converter.StringToGenderConverter;
import red.semipro.share.converter.StringToLocalDateTimeConverter;
import red.semipro.share.converter.StringToPlaceArrangementConverter;
import red.semipro.share.converter.StringToSeminarImageTypeConverter;
import red.semipro.share.converter.StringToSeminarTypeConverter;
import red.semipro.share.converter.StringToSuccessConditionConverter;

/**
 * DIコンテナへ登録するBean
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * コンバーターを登録します
     *
     * @param registry FormatterRegistry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSeminarTypeConverter());
        registry.addConverter(new StringToPlaceArrangementConverter());
        registry.addConverter(new StringToSuccessConditionConverter());
        registry.addConverter(new StringToSeminarImageTypeConverter());
        registry.addConverter(new StringToBusinessTypeConverter());
        registry.addConverter(new StringToGenderConverter());
        registry.addConverter(new StringToLocalDateTimeConverter());
    }

    /**
     * 二重送信防止時のトランザクショントークンインターセプターを登録します
     *
     * @return TransactionTokenInterceptor
     */
    @Bean
    public TransactionTokenInterceptor transactionTokenInterceptor() {
        return new TransactionTokenInterceptor(1);
    }

    /**
     * インターセプター除外設定を登録します
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(transactionTokenInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/resources/**")
            .excludePathPatterns("/**/*.html");
    }

    /*
     *  Dispatcher configuration for serving static resources
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        // Register resource handler for CSS and JS
        registry.addResourceHandler("/resources/**")
            .addResourceLocations("classpath:/static/");
    }
}
