package com.analyticqa.dbapp;

import org.apache.beam.sdk.options.ValueProvider;

public class CustomTableValueProvider implements ValueProvider<String> {

	public CustomTableValueProvider(String outputTableName, ValueProvider<String> outputDataset) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String get() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccessible() {
		// TODO Auto-generated method stub
		return false;
	}

}
