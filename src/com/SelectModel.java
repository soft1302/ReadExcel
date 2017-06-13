package com;

import java.util.ArrayList;
import java.util.List;

public class SelectModel {
	private String name;
	private List<String> child=new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getChild() {
		return child;
	}

	public void setChild(List<String> child) {
		this.child = child;
	}

}
