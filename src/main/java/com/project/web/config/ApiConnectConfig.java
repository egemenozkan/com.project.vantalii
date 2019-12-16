package com.project.web.config;

import static java.util.Arrays.asList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class ApiConnectConfig {

    
    @Value("${security.api.auth-server-url}")
   	private String authServerUrl;
    
    @Value("${security.api.token-path}")
   	private String tokenPath;
    
    @Value("${security.api.authorize-path}")
   	private String authorizePath;
    
    @Value("${security.api.client-id}")
	private String clientId;

	@Value("${security.api.client-secret}")
	private String clientSecret;

	@Value("${security.api.grant-type}")
	private String grantType;

	@Value("${security.api.scope}")
	private String scope;

	
    
	@Bean
	public OAuth2ProtectedResourceDetails apiServerId() {
		ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		resourceDetails.setAccessTokenUri(authServerUrl + tokenPath);
		resourceDetails.setClientId(clientId);
		resourceDetails.setClientSecret(clientSecret);
		resourceDetails.setGrantType(grantType);
		resourceDetails.setScope(asList(scope));
		return resourceDetails;
	}

	@Bean
	public OAuth2RestTemplate restTemplate(OAuth2ClientContext clientContext) {
		return new OAuth2RestTemplate(apiServerId(), clientContext);
	}
}