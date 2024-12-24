package com.example.egen.orderprocessingbulkasync;

import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
// import org.springframework.http.HttpRequest;
// import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import okhttp3.*;


import com.example.egen.orderprocessingbulkasync.configuration.BatchConfiguration;

public class KafkaSimulation {

  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
  public static boolean MOCK_MODE = false;
  public static void produce(String apiUrl, String jsonPayload) {
    if (MOCK_MODE) {
      System.out.println("Mocked produce called with payload: ");
      return;
    }
    System.out.println("not mocking");
    OkHttpClient client = new OkHttpClient();
    RequestBody body = RequestBody.create(JSON, jsonPayload);
    Request request = new Request.Builder()
            .url(apiUrl)
            .post(body)
            .build();

    try (Response response = client.newCall(request).execute()) {
        System.out.println("Response Code: " + response.code());
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
  // public static void produce(String apiUrl, String jsonPayload) {
  //   System.out.println("Mocked HTTP POST request to: " + apiUrl);
  //   System.out.println("Payload: " + jsonPayload);
  // }
  public static String loadJsonFromFile(String filePath) {
    try (FileReader reader = new FileReader(filePath)) {
      JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
      return jsonObject.toString(); // Convert JSON to String
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void consumeBatchJob() {
    
    try (AnnotationConfigApplicationContext context = 
              new AnnotationConfigApplicationContext(BatchConfiguration.class)) {

      // Retrieve the Step bean
      Step step = context.getBean("start", Step.class);

      // Create a StepExecution object manually
      JobRepository jobRepository = context.getBean(JobRepository.class);
      StepExecution stepExecution = new StepExecution("batchStep", jobRepository.createJobExecution("manualJob", null));

      // Register the StepExecution in the StepSynchronizationManager
      StepSynchronizationManager.register(stepExecution);

      // Execute the Step
      step.execute(stepExecution);
      System.out.println("Batch step executed successfully with status: " + stepExecution.getStatus());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      StepSynchronizationManager.close();
    }
  }
  // public static void consumeBatchJob() {
  //   System.out.println("consume batch job");
  // }
}
