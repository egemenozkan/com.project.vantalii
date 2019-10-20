package com.project.web.model;

public class AutocompleteRequest {
	private String query;

	public AutocompleteRequest(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
