package com.vantalii.web.misc.service.impl;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.vantalii.web.misc.service.IGoogleApiService;

@Service
public class GoogleApiService implements IGoogleApiService {

	/**
	 * Be sure to specify the name of your application. If the application name is
	 * {@code null} or blank, the application will log a warning. Suggested format
	 * is "MyCompany-ProductName/1.0".
	 */
	private static final String APPLICATION_NAME = "Vantalii International";
	private static final String CLIENT_ID = "897437363481-oustapsredu1innp4ih4e82k77quq841.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "TwqIOvHqG64PzheU8DA1_fcl";

	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static HttpTransport HTTP_TRANSPORT;

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isValid(String token, String accessToken) {

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(HTTP_TRANSPORT, JSON_FACTORY)
				// Specify the CLIENT_ID of the app that accesses the backend:
				.setAudience(Collections.singletonList(CLIENT_ID))
				// Or, if multiple clients access the backend:
				// .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
				.build();

		// (Receive idTokenString by HTTPS POST)

		GoogleIdToken idToken = null;
		boolean isValid = false;
		try {
			idToken = verifier.verify(token);
			if (idToken != null) {
				Payload payload = idToken.getPayload();

				// Print user identifier
				String userId = payload.getSubject();
				System.out.println("User ID: " + userId);

				// Get profile information from payload
				String email = payload.getEmail();
				boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
				String name = (String) payload.get("name");
				String pictureUrl = (String) payload.get("picture");
				String locale = (String) payload.get("locale");
				String familyName = (String) payload.get("family_name");
				String givenName = (String) payload.get("given_name");

				isValid = true;
				
				// Use or store profile information
				// ...

			} else {
				System.out.println("Invalid ID token.");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return isValid;
	}

}
