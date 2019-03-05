package com.project.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import com.project.web.social.SocialUsersDetailService;
import com.project.web.social.security.CustomLoginFailureHandler;
import com.project.web.social.security.UserDetailsService;

//@EnableEmbeddedRedis
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
// @EnableRedisHttpSession(maxInactiveIntervalInSeconds = 864000)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().anonymous().and().formLogin().loginPage("/login").defaultSuccessUrl("/", true)
				.loginProcessingUrl("/login/authenticate").failureHandler(new CustomLoginFailureHandler()).and().logout()
				.deleteCookies("SESSION").logoutUrl("/logout").logoutSuccessUrl("/login").and().authorizeRequests()
				.antMatchers("/auth/**", "/login", "/error", "/signup", "/css/**", "/js/**").permitAll().antMatchers("/**").hasRole("USER")
				.and().apply(new SpringSocialConfigurer());
	}

	@Bean
	public SocialUserDetailsService socialUsersDetailService() {
		return new SocialUsersDetailService(userDetailsService);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
		return daoAuthenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}


	@Override
	protected UserDetailsService userDetailsService() {
		return userDetailsService;
	}

	// @Bean
	// public JedisConnectionFactory connectionFactory(@RedisServerPort int port) {
	// JedisConnectionFactory connection = new JedisConnectionFactory();
	// connection.setPort(port);
	// return connection;
	// }
}
