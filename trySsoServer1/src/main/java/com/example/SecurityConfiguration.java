package com.example;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// @formatter:off
//		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll().anyRequest()
//				.authenticated().and().exceptionHandling()
//				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().logout()
//				.logoutSuccessUrl("/").permitAll().and().csrf()
//				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
//				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
//		// @formatter:on
//	}
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin().loginPage("/login").permitAll().successHandler(loginSuccessHandler())
//                .and().authorizeRequests()
//                .antMatchers("/images/**", "/checkcode", "/scripts/**", "/styles/**").permitAll()
//                .anyRequest().authenticated()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
//                .and().exceptionHandling().accessDeniedPage("/deny")
//                .and().rememberMe().tokenValiditySeconds(86400).tokenRepository(tokenRepository());
//    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.antMatcher("/**").authorizeRequests().antMatchers("/logout","/login**", "/webjars/**").permitAll()
			.anyRequest().authenticated()
			.and().exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
			.and().logout().logoutSuccessUrl("/logout2").permitAll()
			.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.and().formLogin().loginPage("/login")
				;
		// @formatter:on
	}
	
	
//	Parameter 0 of method oauth2ClientFilterRegistration in com.example.SecurityConfiguration required a bean of type 'org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter' that could not be found.
//	Action:
//	Consider defining a bean of type 'org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter' in your configuration.
//	//~~~~~~~
//	@Bean
//	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(filter);
//		registration.setOrder(-100);
//		return registration;
//	}

	
	
}
