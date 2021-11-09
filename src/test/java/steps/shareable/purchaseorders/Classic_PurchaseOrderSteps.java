package steps.shareable.purchaseorders;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.qmetry.qaf.automation.step.*;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;

import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;
import com.qmetry.qaf.automation.util.Validator;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import steps.common.*;
import steps.shareable.ShrdLaunchApp;

public class Classic_PurchaseOrderSteps extends WebDriverTestCase {
    
    Log logger = LogFactory.getLog(getClass());

	@QAFTestStep(description = "attempt to create a PO from available construction requisitions")
	public void attemptCreatePOFromConReqs() {

        String checkboxesXpath = "(//div[contains(@class, 'listViewport')]//table//tbody//tr//input[@type='checkbox'])";

        int cb = 1;
        boolean found = false;
        while (true) {

            $("classic.conReqs.table.checkbox.first").waitForPresent();
            try {Thread.sleep(1500);} catch (Exception x) {} //Wait a bit to avoid immediate action

            String xpath = checkboxesXpath + "[" + cb + "]";
            QAFExtendedWebElement element = new WebDriverTestBase().getDriver().findElementByXPath(xpath);
            if (!element.isPresent()) {
                break;
            }

            //Click this checkbox
            int maxAttempts = 5;
            for (int attempt = 0; attempt < maxAttempts; attempt++) {
                if (element == null || !element.isPresent() || !element.isEnabled()) {
                    try {Thread.sleep(1000);} catch (Exception x) {}
                    element = new WebDriverTestBase().getDriver().findElementByXPath(xpath);
                } else {
                    break;
                }
            }

            if (!element.isEnabled()) {
                throw new AssertionError("Checkbox element to select a con req to check for available POs is not enabled");
            }

            element.click();
            //Try to create the PO
            $("classic.conReqs.table.createPurchaseOrders.button").click();

            //After loading, check if the 'Create Purchase Orders' button exists
            QAFWebElement createButton = null;
            QAFWebElement backButton = null;
            maxAttempts = 20;
            for (int attempt = 0; attempt < maxAttempts; attempt++) {
                createButton = $("classic.purchaseOrders.draftPurchaseOrders.createPOs.button");
                backButton = $("classic.purchaseOrders.draftPurchaseOrders.back.button");
                if (createButton.isPresent() || backButton.isPresent()) {
                    //One of the buttons found. Exit the iteration
                    break;
                }
                try {Thread.sleep(1500);} catch (Exception x) {} //Wait for page load
            }

            if ((createButton == null && backButton == null) || (!createButton.isPresent() && !backButton.isPresent())) {
                //If neither was found, raise an error
                throw new AssertionError("No buttons to Create Purchase Order or Back Button were found where expected");
            }

            if (createButton != null && createButton.isPresent()) {
                //Found a suitable construction requisition to create a PO from!
                String soLine = $("classic.purchaseOrders.draftPurchaseOrders.table.soLine.first").getText();
                logger.info("Found a suitable Con Req to create a PO from! SalesOrderLine: " + soLine);
                createButton.click();
                found = true;
                break;
            } else if (backButton != null && backButton.isPresent()) {
                logger.info("Not a suitable Con Req to create a PO. Attempting the next one...");
                backButton.click();
            }

            cb++;
        }

        Validator.assertTrue(found,
            "A suitable line was not found in Con Reqs page to create a PO from",
            "A suitable line was found in Con Reqs page to create a PO from");
    }

}