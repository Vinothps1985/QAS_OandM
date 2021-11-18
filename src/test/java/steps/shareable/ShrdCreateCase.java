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

public class ShrdCreateCase extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	/**
	 * Creates a new case in salesforce
	 * 
	 * If correctly generated, the case number is saved on a variable called
	 * ${generated_caseNumber}
	 * 
	 * @param projectName
	 * @param subject
	 * @param caseDescription
	 * @param recordType
	 * @param casePriority
	 * @param caseOrigin
	 * @param reportedIssue
	 * @param caseCause
	 */
	@QAFTestStep(description = "ShrdCreateCase {projectName} {subject} {caseDescription} {summary} {recordType} {casePriority} {caseOrigin} {reprtedIssue} {caseCause}")
	public void customShrdCreateCase(Object projectName, Object subject, Object caseDescription, Object summary, Object recordType, Object casePriority,Object caseOrigin,Object reportedIssue,Object caseCause) {
		
		//Search for the project in the top search bar and click on the result
		$("common.searchAssistant.button").waitForEnabled();
		$("common.searchAssistant.button").click();
		CommonStep.sendKeys(""+String.valueOf(projectName)+"","common.searchAssistant.input");
		$("projects.search.firstResult").waitForVisible();
		$("projects.search.firstResult").waitForEnabled();
		$("projects.search.firstResult").click();
		new WebDriverTestBase().getDriver().switchTo().defaultContent();

		$("projects.details.projectName").waitForPresent();
		$("projects.details.projectName").assertText(String.valueOf(projectName));
		//TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot
		Reporter.logWithScreenShot("Screenshot of project");

		//Verify the project has active contracts
		$("projects.quicklink.contracts").waitForPresent();
		$("projects.quicklink.contracts").waitForEnabled();
		$("projects.quicklink.contracts").click();
		$("projects.contracts.firstContract.status").waitForPresent();
		$("projects.contracts.firstContract.status").assertPresent();
		//TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot
		Reporter.logWithScreenShot("Screenshot of project's contracts");

		//Return to cases
		$("breadcrumbs.second").waitForEnabled();
		$("breadcrumbs.second").click();
		$("projects.createCase.button").waitForEnabled();

		//Create new case
		$("projects.createCase.button").click();

		$("projects.createCase.popup.OnM.first").verifyPresent();
		$("projects.createCase.popup.OnM.first.createCase.button").waitForEnabled();
		$("projects.createCase.popup.OnM.first.createCase.button").click();

		$("case.createCase.recordType.select").waitForPresent();
		$("case.createCase.recordType.select").waitForEnabled();
		$("case.createCase.recordType.select").click();

		StepsLibrary.selectIn("label=" + recordType,"case.createCase.recordType.select");

		$("case.createCase.recordType.next.button").waitForEnabled();
		CommonStep.click("case.createCase.recordType.next.button");

		$("case.createCase.popup.priority.select").waitForEnabled();
		CommonStep.click("case.createCase.popup.priority.select");

		steps.web.StepsLibrary.selectSalesforcePicklistOption(casePriority.toString());

		//$("case.createCase.popup.priority.low.option").waitForEnabled();
		//CommonStep.click("case.createCase.popup.priority.low.option");

		$("case.createCase.popup.caseOrigin.select").waitForEnabled();
		CommonStep.click("case.createCase.popup.caseOrigin.select");

		steps.web.StepsLibrary.selectSalesforcePicklistOption(caseOrigin.toString());

		//$("case.createCase.popup.caseOrigin.managerCreated.option").waitForEnabled();
		//CommonStep.click("case.createCase.popup.caseOrigin.managerCreated.option");

		$("case.createCase.popup.reportedIssue.select").waitForEnabled();
		CommonStep.click("case.createCase.popup.reportedIssue.select");

		steps.web.StepsLibrary.selectSalesforcePicklistOption(reportedIssue.toString());
		//$("case.createCase.popup.reportedIssue.systemDown.option").waitForEnabled();
		//CommonStep.click("case.createCase.popup.reportedIssue.systemDown.option");

		$("case.createCase.popup.branch.select").waitForEnabled();
		CommonStep.click("case.createCase.popup.branch.select");
		$("case.createCase.popup.branch.midAtlantic.option").waitForEnabled();
		CommonStep.click("case.createCase.popup.branch.midAtlantic.option");
		
		$("case.createCase.popup.caseCause.select").waitForEnabled();
		CommonStep.click("case.createCase.popup.caseCause.select");

		steps.web.StepsLibrary.selectSalesforcePicklistOption(caseCause.toString());
		//$("case.createCase.popup.caseCause.other.option").waitForEnabled();
		//CommonStep.click("case.createCase.popup.caseCause.other.option");

		CommonStep.sendKeys(""+String.valueOf(subject)+"","case.createCase.popup.subject.input");
		CommonStep.sendKeys(""+String.valueOf(caseDescription)+"","case.createCase.popup.description.textarea");
		CommonStep.sendKeys(""+String.valueOf(summary)+"","case.createCase.popup.summary.textarea");

		$("case.createCase.popup.save.button").waitForEnabled();
		CommonStep.click("case.createCase.popup.save.button");

		$("case.entityName").verifyText("Case");
		$("cases.caseNumber").waitForPresent();
		logger.info("Generated case number: " + $("cases.caseNumber").getText());
		CommonStep.store($("cases.caseNumber").getText(), "generated_caseNumber");
	}
}




