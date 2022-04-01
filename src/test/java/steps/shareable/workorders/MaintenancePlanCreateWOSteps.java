package steps.shareable.workorders;

import java.util.List;
import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;

import com.qmetry.qaf.automation.step.CommonStep;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;

import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import com.qmetry.qaf.automation.util.Validator;


public class MaintenancePlanCreateWOSteps extends WebDriverTestCase {

	static Log logger = LogFactory.getLog(MaintenancePlanCreateWOSteps.class);

	@QAFTestStep(description = "assert con req group has {num} lines")
	public void assertConReqGroupHasNumLines(int num) {
		boolean success = false;
		int numRows = 0;
		try {
			String xpath = "(//h2[text()='Construction Requisition Lines']//following::div[@class='pbBody'])[1]//table//tbody//tr//td[1]";
			
			List lstTRs = new WebDriverTestBase().getDriver().findElements(By.xpath(xpath));
			if (lstTRs != null && lstTRs.size() > 0) {
				numRows = lstTRs.size();
			}
			success = true;
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.assertTrue(success && numRows == num,
			"Amount of rows do not match. Con req group has " + numRows + " lines, instead of the expected " + num,
			"Con req group has the correct amount of lines: " + num);
		
	}

	/**
	 * 
	 * @param milisec
	 * @param refreshes
	 * @param loc
	 * @param text
	 */
	@QAFTestStep(description="refresh the maintenance plan page until work order generation status is {status} for a max of {seconds} seconds")
	public static void refreshMaintenancePlanUntilStatusIs(String status, int seconds) {
		boolean success = false;
		try {
			int currentRefreshes=0;
			int refreshes = (seconds/10)+1;
			while (currentRefreshes < refreshes) {
				try {
					//Check the status
					String text = $("maintenancePlan.workOrderGenerationStatus.text").getText();
					if (text != null && text.equalsIgnoreCase(status)) {
						success = true;
						break;
					} else {
						currentRefreshes++;
						Thread.sleep(10000);
						steps.common.StepsLibrary.executeJavaScript("window.location.reload();");
					}
				} catch (Exception x) {
					currentRefreshes++;
					Thread.sleep(10000);
					steps.common.StepsLibrary.executeJavaScript("window.location.reload();");
				}
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.assertTrue(success,
			"Con Req group status did not change to " + status + " after " + seconds + "seconds",
			"Con Req group status changed successfully to status " + status);
	}
}