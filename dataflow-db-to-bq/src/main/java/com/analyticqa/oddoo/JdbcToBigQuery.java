package com.analyticqa.oddoo;

import com.google.api.services.bigquery.model.TableRow;
import com.google.auto.service.AutoService;
//import com.google.cloud.teleport.io.DynamicJdbcIO;
//import com.google.cloud.teleport.templates.common.JdbcConverters;
//import com.google.cloud.teleport.util.KMSEncryptedNestedValueProvider;
import java.security.Security;
import javax.net.ssl.SSLServerSocketFactory;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineResult;
import org.apache.beam.sdk.harness.JvmInitializer;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.bigquery.TableRowJsonCoder;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.transforms.DoFn.ProcessContext;
import org.apache.beam.sdk.transforms.DoFn.ProcessElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcToBigQuery {
	
	private static final Logger LOG = LoggerFactory.getLogger(JdbcToBigQuery.class);
	
	//private static final Logger LOG = LoggerFactory.getLogger(JdbcToBigQuery.class);

	  /**
	   * Custom JvmInitializer to override jdk.tls.disabledAlgorithms through the template parameters.
	   */
	  @AutoService(JvmInitializer.class)
	  public static class CustomJvmInitializer implements JvmInitializer {
	    @Override
	    public void onStartup() {}

	    @Override
	    public void beforeProcessing(PipelineOptions options) {
	      JdbcConverters.JdbcToBigQueryOptions pipelineOptions =
	          options.as(JdbcConverters.JdbcToBigQueryOptions.class);
	      if (pipelineOptions.getDisabledAlgorithms() != null
	          && pipelineOptions.getDisabledAlgorithms().get() != null) {
	        String value = pipelineOptions.getDisabledAlgorithms().get();
	        // if the user sets disabledAlgorithms to "none" then set "jdk.tls.disabledAlgorithms" to ""
	        if (value.equals("none")) {
	          value = "";
	        }
	        LOG.info("disabledAlgorithms is set to {}.", value);
	        Security.setProperty("jdk.tls.disabledAlgorithms", value);
	        SSLServerSocketFactory fact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
	        LOG.info("Supported Ciper Suites: " + String.join("\n", fact.getSupportedCipherSuites()));
	      }
	    }
	  }

	  

	  public static void main(String[] args) {
		// Parse the user options passed from the command-line
		    JdbcConverters.JdbcToBigQueryOptions options =
		        PipelineOptionsFactory.fromArgs(args)
		            .withValidation()
		            .as(JdbcConverters.JdbcToBigQueryOptions.class);

		    //run(options);
		    Pipeline pipeline = Pipeline.create(options);
	    
	    pipeline.apply(
	            "Read from JdbcIO",
	            DynamicJdbcIO.<TableRow>read()
	                .withDataSourceConfiguration(
	                    DynamicJdbcIO.DynamicDataSourceConfiguration.create(
	                            options.getDriverClassName(),
	                            options.getConnectionURL())
	                        .withUsername(
	                            options.getUsername())
	                        .withPassword(
	                            options.getPassword())
	                        .withDriverJars(options.getDriverJars())
	                        .withConnectionProperties(options.getConnectionProperties()))
	                .withQuery(options.getQuery())
	                .withCoder(TableRowJsonCoder.of())
	                .withRowMapper(JdbcConverters.getResultSetToTableRow()))

	  
	           .apply(
	            "Write to BigQuery",
	            BigQueryIO.writeTableRows()
	                .withoutValidation()
	                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
	                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND)
	                .withCustomGcsTempLocation(options.getBigQueryLoadingTemporaryDirectory())
	                .to(options.getOutputTable()));

	    pipeline.run().waitUntilFinish();
	  }
}
