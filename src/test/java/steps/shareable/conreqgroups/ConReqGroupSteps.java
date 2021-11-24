package steps.shareable.conreqgroups;

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


public class ConReqGroupSteps extends WebDriverTestCase {

	static Log logger = LogFactory.getLog(ConReqGroupSteps.class);

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
	@QAFTestStep(description="refresh the con req group until the status is {status} for a max of {seconds} seconds")
	public static void refreshConReqGroupUntilStatusIs(String status, int seconds) {
		boolean success = false;
		try {
			int currentRefreshes=0;
			int refreshes = (seconds/10)+1;
			while (currentRefreshes < refreshes) {
				try {
					//Exit all iframes
					new WebDriverTestBase().getDriver().switchTo().defaultContent();
					$("conReqGroups.iframe").waitForPresent();
					//Get into the con req group iframe
					steps.web.StepsLibrary.switchToFrameUntilLocIsPresent("conReqGroups.iframe", 
						"conReqGroups.constructionRequisitionLines.table");
					//Check the status
					String text = $("conReqGroups.details.status").getText();
					if (text != null && text.equalsIgnoreCase(status)) {
						success = true;
						new WebDriverTestBase().getDriver().switchTo().defaultContent();
						break;
					} else {
						currentRefreshes++;
						Thread.sleep(10000);
						new WebDriverTestBase().getDriver().switchTo().defaultContent();
						steps.common.StepsLibrary.executeJavaScript("window.location.reload();");
					}
				} catch (Exception x) {
					currentRefreshes++;
					Thread.sleep(10000);
					new WebDriverTestBase().getDriver().switchTo().defaultContent();
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