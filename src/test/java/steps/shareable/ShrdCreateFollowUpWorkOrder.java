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
* @author Anusha MG
*/
public class ShrdCreateFollowUpWorkOrder extends WebDriverTestCase {

	@QAFTestStep(description = "ShrdCreateFollowUpWorkOrder")
	public void customShrdCreatFollowUpWorkOrder() {
		
		//Click on Edit link
		$("workOrders.edit.link").waitForEnabled();
		CommonStep.click("workOrders.edit.link");

		//Select "Cannot Complete" picklist option and Save the WO
		$("workOrders.edit.status.select").waitForEnabled();
		CommonStep.click("workOrders.edit.status.select");

		steps.web.StepsLibrary.selectSalesforcePicklistOption("Cannot Complete");

		$("workOrders.edit.save.button").waitForEnabled();
		CommonStep.click("workOrders.edit.save.button");

		//Store the generated work order number
		$("workOrders.titleWONumber").waitForPresent();
		logger.info("Generated work order number: " + $("workOrders.titleWONumber").getText());
		CommonStep.store($("workOrders.titleWONumber").getText(), "generated_workOrderNumber_old");

	}
}




