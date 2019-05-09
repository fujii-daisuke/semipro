package red.semipro.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    private static final String[] authenticateActionArray = 
        {
                "/holds"
        };
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        // セキュリティ設定を無視するリクエスト設定
        // 静的リソース(images、css、javascript)に対するアクセスはセキュリティ設定を無視する
        web.ignoring().antMatchers(
                            "/css/**",
                            "/font/**",
                            "/img/**",
                            "/js/**");
    }
    
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
            .failureForwardUrl("/login/failure?error")
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
    
    @Autowired
    public void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        //ＤＢによる独自認証を行う
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
