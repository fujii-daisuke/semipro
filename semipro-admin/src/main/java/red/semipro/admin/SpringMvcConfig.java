package red.semipro.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenInterceptor;

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
}
