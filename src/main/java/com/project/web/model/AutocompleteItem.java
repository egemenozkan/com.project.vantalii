package com.project.web.model;

import java.io.Serializable;

public class AutocompleteItem implements Serializable {

	private static final long serialVersionUID = -1004453840935332588L;
	
	private String label;
	private String name;
	private String code;


	private String url;

	public AutocompleteItem(String label, String name, String code, String url) {
		this.label = label;
		this.name = name;
		this.code = code;
		this.url = url;
	}
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
