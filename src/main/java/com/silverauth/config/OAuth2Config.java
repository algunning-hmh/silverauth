package com.silverauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
@Import({SecurityConfig.class, DataBaseConfig.class})
@Order(value = 3)
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private SecurityConfig security;
	
	@Autowired
	private DataBaseConfig dataBaseConfig;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore)
				.authenticationManager(security.authenticationManagerBean());
		//endpoints.clientDetailsService(silverJdbcClientDetailsService());
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	 	clients.jdbc(dataBaseConfig.dataSource());
	}
	
	@Bean
	public TokenStore tokenStore() {
	    return new JdbcTokenStore(dataBaseConfig.dataSource());
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.realm("silverauth");
	}
	
	@Bean
	public DefaultTokenServices defaultTokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
	    return defaultTokenServices;
	}
}
