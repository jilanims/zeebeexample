package io.zeebe;

import io.zeebe.client.ZeebeClient;

public class StartBroker {

	public ZeebeClient StartZeebeBroker() {
		final ZeebeClient client = ZeebeClient.newClientBuilder()
				// change the contact point if needed
				.brokerContactPoint("127.0.0.1:26500").usePlaintext().build();
		System.out.println("Connected.");
		return client;
	}
	
	public void closeZeebeBroker(ZeebeClient client) {
		client.close();
		System.out.println("Closed.");
	}
}
