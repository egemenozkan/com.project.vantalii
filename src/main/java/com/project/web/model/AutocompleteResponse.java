package com.project.web.model;

import java.util.List;

public class AutocompleteResponse {
	private boolean success;
	private List<AutocompleteItem> items;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<AutocompleteItem> getItems() {
		return items;
	}

	public void setItems(List<AutocompleteItem> items) {
		this.items = items;
	}
}
