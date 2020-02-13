package io.zeebe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.worker.JobWorker;

public class OrderWorkflow {
	public void collectMoney(ZeebeClient client, String jobType) {
		// after the workflow instance is created
		jobType = "payment-service";
		final JobWorker jobWorker = client.newWorker().jobType(jobType).handler((jobClient, job) -> {
			System.out.println("Collect money");

			// ...

			jobClient.newCompleteCommand(job.getKey()).send().join();
		}).open();

		// waiting for the jobs

		// Don't close, we need to keep polling to get work
		// jobWorker.close();

		// ...
	}

	public void createOrderInstance(ZeebeClient client, String bpmnProcessId) {

		// after the workflow is deployed
		final Map<String, Object> data = new HashMap<>();
		data.put("orderId", 31243);
		data.put("orderItems", Arrays.asList(435, 182, 376));

		CreateInstance createInstance = new CreateInstance();
		createInstance.createInstance(client, bpmnProcessId, data);
		
		// ...

		final JobWorker jobWorker = client.newWorker().jobType("payment-service").handler((jobClient, job) -> {
			final Map<String, Object> variables = job.getVariablesAsMap();
			
			double price = 46.50;
			System.out.println("Process order: " + variables.get("orderId"));
			System.out.println("Collect money: $" + price);

			final Map<String, Object> result = new HashMap<>();
			result.put("totalPrice", price);

			jobClient.newCompleteCommand(job.getKey()).variables(result).send().join();
		}).fetchVariables("orderId").open();
		
		
		final JobWorker fetchJobWorker = client.newWorker().jobType("fetch-items").handler((jobClient, job) -> {
			final Map<String, Object> variables = job.getVariablesAsMap();

			System.out.println("Process order: " + variables.get("orderId"));
			System.out.println("Fetch Item: " + variables.get("orderItems"));

			jobClient.newCompleteCommand(job.getKey()).send().join();
		}).open();

		
		
		final JobWorker shipJobWorker = client.newWorker().jobType("ship-parcel").handler((jobClient, job) -> {
			final Map<String, Object> variables = job.getVariablesAsMap();

			System.out.println("Process order: " + variables.get("orderId"));
			System.out.println("Ship item: " + variables.get("orderItems"));

			jobClient.newCompleteCommand(job.getKey()).send().join();
		}).open();


		// ...
	}
}
