package com.analyticqa.oddoo;

import java.util.List;

import com.google.api.services.bigquery.model.TableSchema;

public class BigQuerySchema {
	
	private String query;
	private String outputTableName;
	private List<Field> fields = null;

	public String getQuery() {
	return query;
	}

	public void setQuery(String query) {
	this.query = query;
	}

	public String getOutputTableName() {
	return outputTableName;
	}

	public void setOutputTableName(String outputTableName) {
	this.outputTableName = outputTableName;
	}

	public List<Field> getFields() {
	return fields;
	}

	public void setFields(List<Field> fields) {
	this.fields = fields;
	}

	@Override
	public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(BigQuerySchema.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
	sb.append("query");
	sb.append('=');
	sb.append(((this.query == null)?"<null>":this.query));
	sb.append(',');
	sb.append("outputTableName");
	sb.append('=');
	sb.append(((this.outputTableName == null)?"<null>":this.outputTableName));
	sb.append(',');
	sb.append("fields");
	sb.append('=');
	sb.append(((this.fields == null)?"<null>":this.fields));
	sb.append(',');
	if (sb.charAt((sb.length()- 1)) == ',') {
	sb.setCharAt((sb.length()- 1), ']');
	} else {
	sb.append(']');
	}
	return sb.toString();
	}

	
	private TableSchema tableSchema;
	public TableSchema getTableSchema() {
		return tableSchema;
	}
	public void setTableSchema(TableSchema tableSchema) {
		this.tableSchema = tableSchema;
	}
	

}
