package com.vantalii.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (
   prePostEnabled = true,
   securedEnabled = true,
   jsr250Enabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private DataSource dataSource;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //.antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/login*").permitAll()
                .antMatchers("/accountConfirm").permitAll()
                .antMatchers("/account").permitAll()
                .antMatchers("/password*").permitAll()
                .antMatchers("/assets/css/**", "assets/js/**", "/images/**").permitAll()
                .antMatchers("/**").permitAll()
             //   .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .failureUrl("/login?error=true")
                .permitAll()
                .defaultSuccessUrl("/", true)
                
//                .and()
//                .rememberMe()
//                .key("superSecretKey")
//                .tokenRepository(tokenRepository())
                .and()
                .logout()
                .logoutSuccessUrl("/?logout=true")
                .logoutRequestMatcher(new AntPathRequestMatcher("/perform_logout", "GET"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
         //       .and()
               // .addFilterAfter(new SameSiteFilter(), BasicAuthenticationFilter.class);     

    }
    
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 auth.inMemoryAuthentication()
                .withUser("egemen").password(passwordEncoder().encode("pass")).roles("USER");
		// Setting Service to find User in the database.
		// And Setting PassswordEncoder
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());     
	}

//    @Bean
//    public PersistentTokenRepository tokenRepository () {
//        JdbcTokenRepositoryImpl token = new JdbcTokenRepositoryImpl();
//        token.setDataSource(dataSource);
//        return token;
//    }
//
//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder());
//
//
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
