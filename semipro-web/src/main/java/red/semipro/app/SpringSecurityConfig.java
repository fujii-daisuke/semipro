package red.semipro.app;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import red.semipro.domain.service.userdetails.AccountUserDetailsService;

/**
 * Spring Security - configuration
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountUserDetailsService userDetailsService;

    /**
     * 認証が必要なURLパス
     */
    private static final String[] authenticateActionArray =
        {
            "/seminars/edit/**",
            "/seminars/**/preview",
            "/seminars/**/apply",
            "/seminars/created/**",
            "/seminars/**/entry/**",
            "/upload/image/**",
        };

    /**
     * セキュリティ設定を除外するリクエストを設定します
     * <p>静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視します
     *
     * @param web WebSecurity
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
            "/css/**",
            "/font/**",
            "/img/**",
            "/js/**");
    }

    /**
     * 認証の設定を行います
     *
     * @param http HttpSecurity
     * @throws Exception 認証失敗時に発生します
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(authenticateActionArray).authenticated()
            .anyRequest().permitAll()
            .and()
            .exceptionHandling();

        http.formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .usernameParameter("email")
            .passwordParameter("password")
            .defaultSuccessUrl("/")
            .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
            .failureUrl("/login?error")
            .permitAll()
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/")
            .deleteCookies("JSESSIONID")
            .invalidateHttpSession(true)
            .permitAll();

        http.sessionManagement()
            .maximumSessions(1)
            .expiredUrl("/login");
    }

    /**
     * 認証設定を行います
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception 認証失敗時に発生します
     */
    @Autowired
    public void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * パスワードエンコーダーをDIコンテナに登録します
     *
     * @return PasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
