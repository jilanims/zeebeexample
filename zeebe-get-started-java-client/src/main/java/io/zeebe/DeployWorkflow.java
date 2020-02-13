package io.zeebe;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;

public class DeployWorkflow {

	public void deployWorkflow(ZeebeClient client, String processWFName) {
		// after the client is connected

		final DeploymentEvent deployment = client.newDeployCommand().addResourceFromClasspath(processWFName).send()
				.join();

		final int version = deployment.getWorkflows().get(0).getVersion();
		System.out.println("Workflow deployed. Version: " + version);

		// ...
	}
}
