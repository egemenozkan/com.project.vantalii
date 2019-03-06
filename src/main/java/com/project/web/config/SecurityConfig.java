package com.project.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    public SecurityConfig() {
        super();
    }

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth.inMemoryAuthentication()
            .withUser("user1").password("{noop}user1Pass").roles("USER")
            .and()
            .withUser("admin1").password("{noop}admin1Pass").roles("ADMIN");
        // @formatter:on
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
    	 http.authorizeRequests()
         .antMatchers("/anonymous*").anonymous()
         .antMatchers("/login*").permitAll()
         .anyRequest().authenticated()
         
//         .and()
//         .formLogin()
//         .loginPage("/logind")
//         .loginProcessingUrl("/login")
//         .successHandler(myAuthenticationSuccessHandler())
//         .failureUrl("/logind?error=true")
         
         .and()
         .logout().deleteCookies("JSESSIONID")
         
         .and()
         .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400)
         
         .and()
         .csrf().disable()
         ;
        // @formatter:on
    }
    
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
}
