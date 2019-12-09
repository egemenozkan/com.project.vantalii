package com.project.web.component;

import java.net.URI;

import javax.annotation.PostConstruct;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.stereotype.Component;

@Component
public class RequestFactory {
	
    public RequestFactory(OAuth2ProtectedResourceDetails resourceDetails, OAuth2ClientContext clientContext) {
		this.restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext) ;
	}

	private OAuth2RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestWithBodyFactory());
    }

    private static final class HttpComponentsClientHttpRequestWithBodyFactory extends HttpComponentsClientHttpRequestFactory {
        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            if (httpMethod == HttpMethod.GET) {
                return new HttpGetRequestWithEntity(uri);
            }
            return super.createHttpUriRequest(httpMethod, uri);
        }
    }

    private static final class HttpGetRequestWithEntity extends HttpEntityEnclosingRequestBase {
        public HttpGetRequestWithEntity(final URI uri) {
            super.setURI(uri);
        }

        @Override
        public String getMethod() {
            return HttpMethod.GET.name();
        }
    }

    public OAuth2RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
