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
import steps.shareable.cases.CaseSteps;
/**
* @author Rodrigo Montemayor
*/
public class ShrdCreateWorkOrder extends WebDriverTestCase {

	@QAFTestStep(description = "ShrdCreateWorkOrder {0} {1} {2}")
	public void customShrdCreateWorkOrder(String caseNumber, Object assetType1, Object assetType2) {
		
		//Look for the case
		CaseSteps cs = new CaseSteps();
		if (!cs.inCaseScreen(caseNumber)) {
			cs.goToCase(caseNumber);
		}

		//Click on create work order button
		$("cases.createWorkOrderHybrid.button").waitForEnabled();
		CommonStep.click("cases.createWorkOrderHybrid.button");

		//Change into iframe and start creating the work order
		$("workOrders.iframe").waitForPresent(120 * 1000); //Can take a while
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

		//Save the asset type name that appears on the table here. It may be used for assertions later
		//Sometimes it's different (e.g. Asset Type 'Combiner Box' has name 'Combiner' only)
		CommonStep.store($("workOrders.create.assetTable.assetType.first").getText(), "assetType1_realName");

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

		//Save the asset type name that appears on the table here. It may be used for assertions later
		//Sometimes it's different (e.g. Asset Type 'Combiner Box' has name 'Combiner' only)
		CommonStep.store($("workOrders.create.assetTable.assetType.first").getText(), "assetType2_realName");

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




