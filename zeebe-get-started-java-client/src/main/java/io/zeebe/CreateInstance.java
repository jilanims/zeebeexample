package io.zeebe;

import java.util.Map;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.WorkflowInstanceEvent;

public class CreateInstance {
	public long createInstance(ZeebeClient client, String bpmnProcessId, Map<String, Object> data) {
		// after the workflow is deployed

		final WorkflowInstanceEvent wfInstance = client.newCreateInstanceCommand().bpmnProcessId(bpmnProcessId)
				.latestVersion().variables(data).send().join();

		final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();

		System.out.println("Workflow instance created. Key: " + workflowInstanceKey);

		return workflowInstanceKey;
		// //...
	}

}
