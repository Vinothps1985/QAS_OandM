package steps.shareable.serviceappointments;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.step.QAFTestStep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;

public class ServiceAppointmentSteps extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	@QAFTestStep(description = "assign {assignee} to service appointment if it is not autoassigned after {secs} seconds")
	public void assignToSAIfNotAutoassignedAFter(String assignee, long seconds) {
		boolean success = false;

		try {
			$("serviceAppointments.assignedResources.all.link").waitForPresent();
			$("serviceAppointments.assignedResources.all.link").waitForEnabled();
			$("serviceAppointments.assignedResources.all.link").click();

			long maxAttempts = (seconds / 10)+1;
			boolean assigned = false;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					if (attempt > 0) {
						Thread.sleep(10000);
						steps.common.StepsLibrary.executeJavaScript("window.location.reload();");
					}

					if ($("common.table.link.first").isPresent()) {
						assigned = true;
						break;
					}
				} catch (Exception x) {}
			}

			if (!assigned) {
				logger.info("Not assigned. Assigning...");
				$("serviceAppointments.assignedResources.new.button").waitForPresent();
				$("serviceAppointments.assignedResources.new.button").waitForEnabled();
				$("serviceAppointments.assignedResources.new.button").click();

				$("serviceAppointment.editResource.popup.serviceResource.input").waitForEnabled();
				$("serviceAppointment.editResource.popup.serviceResource.input").waitForVisible();
				$("serviceAppointment.editResource.popup.serviceResource.input").sendKeys(assignee);
				$("serviceAppointment.editResource.popup.serviceResource.option.first").waitForPresent();
				$("serviceAppointment.editResource.popup.serviceResource.option.first").waitForVisible();
				$("serviceAppointment.editResource.popup.serviceResource.option.first").click();
				steps.common.StepsLibrary.takeAScreenshot();

				$("common.save.button").click();
				$("common.toastContainer").waitForPresent();
				$("common.toastContainer").waitForEnabled();
			} else {
				logger.info("Already Assigned!");
			}

		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
	}

}