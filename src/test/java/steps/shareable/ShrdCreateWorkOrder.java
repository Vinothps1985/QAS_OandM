package steps.shareable;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.step.QAFTestStep;
import org.openqa.selenium.Keys;
import com.qmetry.qaf.automation.step.*;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;
import com.qmetry.qaf.automation.util.Validator;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import steps.common.*;
/**
* @author Rodrigo Montemayor
*/
public class ShrdCreateWorkOrder extends WebDriverTestCase {

	@QAFTestStep(description = "ShrdCreateWorkOrder {0} {1} {2}")
	public void customShrdCreateWorkOrder(Object caseNumber, Object assetType1, Object assetType2) {
		
		//Look for the case
		$("common.searchAssistant.button").waitForEnabled();
		CommonStep.click("common.searchAssistant.button");
		CommonStep.sendKeys(""+String.valueOf(caseNumber)+"","common.searchAssistant.input");
		$("cases.search.firstResult").waitForVisible();
		$("cases.search.firstResult").waitForEnabled();
		CommonStep.click("cases.search.firstResult");
		$("cases.caseNumber").waitForVisible();

		$("cases.caseNumber").waitForPresent();
		$("cases.caseNumber").assertText(String.valueOf(caseNumber));
		TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot

		//Click on create work order button
		$("cases.createWorkOrderHybrid.button").waitForEnabled();
		CommonStep.click("cases.createWorkOrderHybrid.button");

		//Change into iframe and start creating the work order
		$("workOrders.iframe").waitForPresent();
		new WebDriverTestBase().getDriver().switchTo().frame(new QAFExtendedWebElement("workOrders.iframe"));

		$("workOrders.create.next.button").waitForPresent();
		$("workOrders.create.next.button").waitForEnabled();
		CommonStep.click("workOrders.create.next.button");

		$("workOrders.create.assetType.select").waitForPresent();
		StepsLibrary.selectIn("label="+String.valueOf(assetType1)+"","workOrders.create.assetType.select");

		$("workOrders.create.next.button").waitForEnabled();
		CommonStep.click("workOrders.create.next.button");

		$("workOrders.create.assetTable.checkbox.first").waitForPresent();
		$("workOrders.create.assetTable.checkbox.first").waitForEnabled();
		CommonStep.click("workOrders.create.assetTable.checkbox.first");

		$("workOrders.create.next.button").waitForEnabled();
		CommonStep.click("workOrders.create.next.button");
		
		$("workOrders.create.addAdditionalAssets.yes.option").waitForEnabled();
		CommonStep.click("workOrders.create.addAdditionalAssets.yes.option");

		$("workOrders.create.next.button").waitForEnabled();
		CommonStep.click("workOrders.create.next.button");

		$("workOrders.create.assetType.select").waitForPresent();
		StepsLibrary.selectIn("label="+String.valueOf(assetType2)+"","workOrders.create.assetType.select");

		$("workOrders.create.next.button").waitForEnabled();
		CommonStep.click("workOrders.create.next.button");

		$("workOrders.create.assetTable.checkbox.first").waitForPresent();
		$("workOrders.create.assetTable.checkbox.first").waitForEnabled();
		CommonStep.click("workOrders.create.assetTable.checkbox.first");

		$("workOrders.create.next.button").waitForEnabled();
		CommonStep.click("workOrders.create.next.button");

		$("workOrders.create.addAdditionalAssets.no.option").waitForEnabled();
		CommonStep.click("workOrders.create.addAdditionalAssets.no.option");

		$("workOrders.create.next.button").waitForEnabled();
		CommonStep.click("workOrders.create.next.button");

		$("workOrders.create.selectAssets.label").waitForPresent();

		$("workOrders.create.next.button").waitForEnabled();
		CommonStep.click("workOrders.create.next.button");

		$("workOrders.create.updateDuration.label").waitForPresent();

		$("workOrders.create.next.button").waitForEnabled();
		CommonStep.click("workOrders.create.next.button");

		$("workOrders.create.multipleTechsNeeded.label").waitForPresent();

		$("workOrders.create.next.button").waitForEnabled();
		CommonStep.click("workOrders.create.next.button");

		//Return to regular frame
		new WebDriverTestBase().getDriver().switchTo().defaultContent();

		//Reload the case page
		$("cases.caseNumber").waitForVisible();
		StepsLibrary.executeJavaScript("window.location.reload();");

		//Wait until the cases page is updated
		$("cases.caseNumber").waitForPresent();
	}
}



