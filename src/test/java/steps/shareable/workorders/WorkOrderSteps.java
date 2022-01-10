package steps.shareable.workorders;

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

import com.qmetry.qaf.automation.util.Reporter;
import com.qmetry.qaf.automation.util.Validator;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import steps.common.*;
import steps.shareable.ShrdCreateWorkOrder;
import steps.shareable.ShrdLaunchApp;
import steps.shareable.ShrdCreateFollowUpWorkOrder;

public class WorkOrderSteps extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	@QAFTestStep(description = "create a work order with data {0} {1} {2}")
	public void createWorkOrderWithData(String caseNumber, Object assetType1, Object assetType2) {
		ShrdCreateWorkOrder create = new ShrdCreateWorkOrder();
		create.customShrdCreateWorkOrder(caseNumber, assetType1, assetType2);
	}

	@QAFTestStep(description = "prerequisite for follow up work order creation")
	public void createFollowUpWorkOrderWithData() {
		ShrdCreateFollowUpWorkOrder create = new ShrdCreateFollowUpWorkOrder();
		create.customShrdCreatFollowUpWorkOrder();
	}

	@QAFTestStep(description = "store the follow up work order number")
	public void storeFollowUpWorkOrderNumber() {
		//Store the generated work order number
		$("workOrders.titleWONumber").waitForPresent();
		logger.info("Generated Follow Up work order number: " + $("workOrders.titleWONumber").getText());
		CommonStep.store($("workOrders.titleWONumber").getText(), "generated_workOrderNumber_new");
	}

}