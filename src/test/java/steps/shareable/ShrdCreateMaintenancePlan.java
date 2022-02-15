package steps.shareable;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.step.QAFTestStep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Keys;
import com.qmetry.qaf.automation.step.*;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;

import com.qmetry.qaf.automation.util.Reporter;
import com.qmetry.qaf.automation.util.Validator;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import steps.common.*;

public class ShrdCreateMaintenancePlan extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	/**
	 * Creates a new Maintenance Plan in salesforce
	 * 
	 * If correctly generated, the Maintenance Plan number is saved on a variable
	 * called ${generated_maintenancePlanNumber}
	 * 
	 * @param projectName
	 * @param startDate
	 * @param type
	 * @param frequency
	 * @param generationTimeframe
	 * @param dateofFirstWOinNextBatch
	 * @param maintenancePlanTitle
	 * @param maintenancePlanDescription
	 */
	@QAFTestStep(description = "ShrdCreateMaintenancePlan {projectName} {startDate} {type} {frequency} {generationTimeframe} {dateofFirstWOinNextBatch} {maintenancePlanTitle} {maintenancePlanDescription}")
	public void customShrdCreateMaintenancePlan(Object projectName, Object startDate, Object type, Object frequency,
			Object generationTimeframe, Object dateofFirstWOinNextBatch, Object maintenancePlanTitle,
			Object maintenancePlanDescription) {

		// Search for the project in the top search bar and click on the result
		$("common.searchAssistant.button").waitForEnabled();
		$("common.searchAssistant.button").click();
		CommonStep.sendKeys("" + String.valueOf(projectName) + "", "common.searchAssistant.input");
		$("projects.search.firstResult").waitForVisible();
		$("projects.search.firstResult").waitForEnabled();
		$("projects.search.firstResult").click();
		new WebDriverTestBase().getDriver().switchTo().defaultContent();

		$("projects.details.projectName").waitForPresent();
		$("projects.details.projectName").assertText(String.valueOf(projectName));
		Reporter.logWithScreenShot("Screenshot of project");

		// Verify that Service Contract status is Active
		$("projects.details.contract").waitForPresent();
		$("projects.details.contract").waitForEnabled();
		$("projects.details.contract").click();
		$("serviceContracts.details.contractStatus").waitForPresent();
		$("serviceContracts.details.contractStatus").assertText("Active");
		Reporter.logWithScreenShot("Screenshot of project's Active Contract");

		// Create Maintenance Plan from the Service Contract
		$("serviceContracts.createMaintenancePlan.button").waitForEnabled();
		$("serviceContracts.createMaintenancePlan.button").click();
		$("serviceContracts.newMP.popup.title").waitForEnabled();

		$("serviceContracts.newMP.popup.startDate.input").waitForEnabled();
		CommonStep.sendKeys("" + String.valueOf(startDate) + "", "serviceContracts.newMP.popup.startDate.input");
		Reporter.logWithScreenShot("Screenshot taken for testing purpose");

		$("serviceContracts.newMP.popup.type.select").waitForEnabled();
		CommonStep.click("serviceContracts.newMP.popup.type.select");
		steps.web.StepsLibrary.selectSalesforcePicklistOption(type.toString());

		$("serviceContracts.newMP.popup.frequency.input").waitForEnabled();
		CommonStep.sendKeys(""+String.valueOf(frequency)+"","serviceContracts.newMP.popup.frequency.input");

		$("serviceContracts.newMP.popup.generationTimeframe.input").waitForEnabled();
		CommonStep.sendKeys(""+String.valueOf(generationTimeframe)+"","serviceContracts.newMP.popup.generationTimeframe.input");

		$("serviceContracts.newMP.popup.dateofFirstWOinNextBatch.input").waitForEnabled();
		CommonStep.sendKeys(""+String.valueOf(dateofFirstWOinNextBatch)+"","serviceContracts.newMP.popup.dateofFirstWOinNextBatch.input");
		Reporter.logWithScreenShot("Screenshot taken for testing purpose");

		$("serviceContracts.newMP.popup.maintenancePlanTitle.input").waitForEnabled();
		CommonStep.sendKeys(""+String.valueOf(maintenancePlanTitle)+"","serviceContracts.newMP.popup.maintenancePlanTitle.input");

		$("serviceContracts.newMP.popup.maintenancePlanDescription.input").waitForEnabled();
		CommonStep.sendKeys(""+String.valueOf(maintenancePlanDescription)+"","serviceContracts.newMP.popup.maintenancePlanDescription.input");

		$("serviceContracts.newMP.popup.save.button").waitForEnabled();
		CommonStep.click("serviceContracts.newMP.popup.save.button");
		Reporter.logWithScreenShot("Screenshot taken for testing purpose");

		$("maintenancePlan.maintenancePlanNumber.text").waitForPresent();
		logger.info("Generated Maintenance Plan Number: " + $("maintenancePlan.maintenancePlanNumber.text").getText());
		CommonStep.store($("maintenancePlan.maintenancePlanNumber.text").getText(), "generated_maintenancePlanNumber");
	}
}




