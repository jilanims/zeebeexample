package io.zeebe;

import io.zeebe.client.ZeebeClient;

public class App {
	public static void main(final String[] args) {
		StartBroker broker = new StartBroker();
		ZeebeClient client = broker.StartZeebeBroker();

		// Deploy the bpm workflow
		String bpmnworkflow = "order-process.bpmn";
		String bpmnProcessId = "orderProcessId";
		DeployWorkflow deployWF = new DeployWorkflow();
		deployWF.deployWorkflow(client, bpmnworkflow);

		// CreateInstance createInstance = new CreateInstance();
		// createInstance.createInstance(client, "orderProcessId", null);

		OrderWorkflow order = new OrderWorkflow();
		order.createOrderInstance(client, bpmnProcessId);

		//close broker
		//broker.closeZeebeBroker(client);
		
	}
}
