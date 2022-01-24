package uk.ac.cardiff.team5.graphium.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import uk.ac.cardiff.team5.graphium.service.UserDetailsService;

@Configuration
@EnableWebSecurity
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {



    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/admin/**").access("hasRole('2')")
                .antMatchers("/api/admin/**").access("hasRole('2')")
                .antMatchers("/api/user/**").access("hasAnyRole('1','2')")
                .antMatchers("/upload").access("hasAnyRole('1','2')")
                .antMatchers("/file").access("hasAnyRole('1','2')")
                .antMatchers("/files").access("hasAnyRole('1','2')")
                .antMatchers("/myFiles").access("hasAnyRole('1','2')")
                .antMatchers("/file/view/**").access("hasAnyRole('1','2')")
                .antMatchers("/file/modify/**").access("hasAnyRole('1','2')")
                .antMatchers("/file/reupload").access("hasAnyRole('1','2')")
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .successHandler(myAuthenticationSuccessHandler())
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
    }

    // ignore register URL for non-authenticated users
    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().antMatchers("/register/**");
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

}
