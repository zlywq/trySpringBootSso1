package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import java.security.KeyPair;


@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig
extends AuthorizationServerConfigurerAdapter
{
	/*
下面这个configure不能省，不然client browser在登录时有如下错误
from client: Authentication Failed: Could not obtain access token
	 */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("serverKeystore.jks"), "123456".toCharArray())
        		.getKeyPair("alias1", "123456".toCharArray());
        converter.setKeyPair(keyPair);
        
//        converter.setSigningKey("123");
        return converter;
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtAccessTokenConverter())
        //.authenticationManager(getAuthenticationManager())
        ;
    }
    
	/*
下面的2个configure不能省，不然client browser在登录时有如下错误
http://localhost:8080/oauth/authorize?client_id=acme&redirect_uri=http://localhost:9999/login&response_type=code&state=bd3gPM
There was an unexpected error (type=Internal Server Error, status=500).
Error creating bean with name 'scopedTarget.clientDetailsService' defined in class path resource [org/springframework/security/oauth2/config/annotation/configuration/ClientDetailsServiceConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.security.oauth2.provider.ClientDetailsService]: Factory method 'clientDetailsService' threw exception; nested exception is java.lang.UnsupportedOperationException: Cannot build client services (maybe use inMemory() or jdbc()).
	 */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("acme").secret("acmesecret")
                //.autoApprove(true)//注意这个如果为true，则/oauth/confirm_access页面不会显示
                .authorizedGrantTypes("authorization_code", "refresh_token","password").scopes("openid","read","write");
                //.scopes("openid","read","write");
                //.authorizedGrantTypes("password","authorization_code", "refresh_token","implicit","client_credentials");//.scopes("openid");
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess(
                "isAuthenticated()").allowFormAuthenticationForClients()
        	//.sslOnly();//...
        ;
        //TokenEndpoint te;
    }

    

}
