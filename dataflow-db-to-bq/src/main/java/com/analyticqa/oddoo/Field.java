package com.analyticqa.oddoo;

public class Field {

	private String name;
	private String type;
	private String mode;

	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	public String getType() {
	return type;
	}

	public void setType(String type) {
	this.type = type;
	}

	public String getMode() {
	return mode;
	}

	public void setMode(String mode) {
	this.mode = mode;
	}

	@Override
	public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(Field.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
	sb.append("name");
	sb.append('=');
	sb.append(((this.name == null)?"<null>":this.name));
	sb.append(',');
	sb.append("type");
	sb.append('=');
	sb.append(((this.type == null)?"<null>":this.type));
	sb.append(',');
	sb.append("mode");
	sb.append('=');
	sb.append(((this.mode == null)?"<null>":this.mode));
	sb.append(',');
	if (sb.charAt((sb.length()- 1)) == ',') {
	sb.setCharAt((sb.length()- 1), ']');
	} else {
	sb.append(']');
	}
	return sb.toString();
	}
}
