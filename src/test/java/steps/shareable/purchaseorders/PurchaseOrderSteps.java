package steps.shareable.purchaseorders;

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


public class PurchaseOrderSteps extends WebDriverTestCase {

	static Log logger = LogFactory.getLog(PurchaseOrderSteps.class);

	@QAFTestStep(description = "assert number of purchase orders created matches {vendor1} {vendor2}")
	public void assertNumOfPOsMatchesVendors(String vendor1, String vendor2) {
        boolean success = false;
        //We expect 1 PO if vendor1 equals vendor2. If they're different, we expect 2 POs created.
        int expectedPOs = vendor1.trim().equals(vendor2.trim()) ? 1 : 2;
		int numRows = 0;
		try {
			String xpath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr";
			
			List lstTRs = new WebDriverTestBase().getDriver().findElements(By.xpath(xpath));
			if (lstTRs != null && lstTRs.size() > 0) {
				numRows = lstTRs.size();
            }
			success = true;
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.assertTrue(success && numRows == expectedPOs,
			"Incorrect amount of POs created. " + numRows + " POs found when " + expectedPOs + " were expected",
			"Correct amount of POs created: " + numRows);
		
	}
}