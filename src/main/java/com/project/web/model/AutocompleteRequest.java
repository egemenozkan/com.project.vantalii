package com.project.web.model;

import com.project.api.data.enums.Language;

public class AutocompleteRequest {
	private String query;
	private Language language;

	public AutocompleteRequest() {
	}

	public AutocompleteRequest(String query) {
		this.query = query;
	}

	public AutocompleteRequest(String query, Language language) {
		this.query = query;
		this.language = language;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
