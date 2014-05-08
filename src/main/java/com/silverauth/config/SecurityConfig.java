package com.silverauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

@Configuration
@EnableWebSecurity
@Import(DataBaseConfig.class)
@Order(value = 2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataBaseConfig dataBaseConfig;
	
	@Bean 
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	     return super.authenticationManagerBean();
	}
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
        	.jdbcAuthentication()
        		.dataSource(dataBaseConfig.dataSource())
        		.passwordEncoder(encoder());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.httpBasic().authenticationEntryPoint(new SilverAuthBasicAuthenticationEntryPoint());
    	http.authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/users").permitAll();
    	http.authorizeRequests().antMatchers("/users").authenticated().and().userDetailsService(new ClientDetailsUserDetailsService(jdbcClientDetailsService()));
    	

    	http.httpBasic().realmName("123").and().csrf().disable();
    }
    
    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
    	JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataBaseConfig.dataSource());
    	return jdbcClientDetailsService;
    }

	@Bean
	public BCryptPasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
}
