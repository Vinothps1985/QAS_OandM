package steps.shareable.cases;

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
import steps.shareable.ShrdCreateCase;
import steps.shareable.ShrdLaunchApp;

public class CaseSteps extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	@QAFTestStep(description = "change status of case {caseNumber} to {newStatus}")
	public void caseChangeStatus(String caseNumber, String newStatus) {
		
		//open case
		if (!inCaseScreen(caseNumber)) {
			goToCase(caseNumber);
		}

		//Change status
		$("cases.details.status.edit.button").waitForPresent();
		$("cases.details.status.edit.button").waitForEnabled();
		$("cases.details.status.edit.button").click();
	 
		$("cases.details.status.edit.input").waitForPresent();
		$("cases.details.status.edit.input").waitForEnabled();
		$("cases.details.status.edit.input").click();

		String xpath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//label[text() = 'Status']//following::span[text()='" + newStatus + "']";
		QAFExtendedWebElement option = new WebDriverTestBase().getDriver().findElementByXPath(xpath);

		Validator.assertTrue(option.isPresent() && option.isEnabled(), 
			"Status " + newStatus + " not found as an option to set for  case " + caseNumber,
			"Status " + newStatus + " found as an option to set for  case " + caseNumber);
		
		option.click();

		$("cases.details.edit.save.button").waitForEnabled();
		$("cases.details.edit.save.button").click();

		$("cases.details.status.edit.button").waitForVisible();
	}

	@QAFTestStep(description = "set all service appointments of case {caseNumber} to status {newStatus}")
	public void caseServiceAppointmentStatus(String caseNumber, String newStatus) {
		
		//open case
		if (!inCaseScreen(caseNumber)) {
			goToCase(caseNumber);
		}

		//Go to service appointments table
		$("cases.quickLinks.serviceAppointments").waitForPresent();
		$("cases.quickLinks.serviceAppointments").click();

		$("serviceAppointments.table.first.link").waitForPresent();

		//Save the list url
		String saListUrl = new WebDriverTestBase().getDriver().getCurrentUrl();

		//All links xpath
		String xpath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr//a[starts-with(@title, 'SA-')]";
		List<WebElement> saLinks = new WebDriverTestBase().getDriver().findElementsByXPath(xpath);
		if (saLinks != null && saLinks.size() > 0) {
			int idx = 1;
			for (WebElement link : saLinks) {
				//Specific xpath, update the link to get the element (avoid the 'stale element' error)
				String linkPath = "(//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr//a[starts-with(@title, 'SA-')])[" + idx + "]";
				QAFExtendedWebElement validLink = new WebDriverTestBase().getDriver().findElementByXPath(linkPath);
				validLink.waitForPresent();
				validLink.click();
				
				$("serviceAppointments.details.status.edit.button").waitForPresent();
				//Now inside the Service Appointment

				//Reload the page to avoid modified errors
				steps.common.StepsLibrary.executeJavaScript("window.location.reload();");
				steps.web.StepsLibrary.waitForPageToFinishLoading();
				$("serviceAppointments.details.status.edit.button").waitForPresent();
				$("serviceAppointments.details.status.edit.button").waitForEnabled();

				//Now inside the Service Appointment again
				$("serviceAppointments.details.status.edit.button").click();

				$("serviceAppointments.details.edit.status.select").waitForEnabled();
				$("serviceAppointments.details.edit.status.select").click();

				String optionXpath = "//div[@class='select-options']//ul//li//a[text()='" + newStatus + "']";
				QAFExtendedWebElement option = new WebDriverTestBase().getDriver().findElementByXPath(optionXpath);
				option.waitForEnabled();
				option.click();

				$("serviceAppointments.details.edit.save.button").waitForEnabled();
				$("serviceAppointments.details.edit.save.button").click();

				$("serviceAppointments.details.status.edit.button").waitForVisible();

				new WebDriverTestBase().getDriver().get(saListUrl);
				$("serviceAppointments.table.first.link").waitForPresent();
				
				idx++;
			}
		}

		logger.info("All Service appointments set to " + newStatus);
	}

	@QAFTestStep(description = "set all work orders of case {caseNumber} to status {newStatus}")
	public void caseWorkOrdersStatus(String caseNumber, String newStatus) {
		
		//open case
		if (!inCaseScreen(caseNumber)) {
			goToCase(caseNumber);
		}

		//Go to service appointments table
		$("cases.quickLinks.workOrders").waitForPresent();
		$("cases.quickLinks.workOrders").click();

		$("workOrders.table.firstResult.link").waitForPresent();

		//Save the list url
		String saListUrl = new WebDriverTestBase().getDriver().getCurrentUrl();

		//All links xpath
		//  ((//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr)[1]//a)[1]
		String xpath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr";
		List<WebElement> woTRs = new WebDriverTestBase().getDriver().findElementsByXPath(xpath);
		if (woTRs != null && woTRs.size() > 0) {
			for (int idx=1; idx <= woTRs.size(); idx++) {
				//Specific link to the WO
				String linkPath = "((//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr)[" + idx + "]//a)[1]";
				QAFExtendedWebElement validLink = new WebDriverTestBase().getDriver().findElementByXPath(linkPath);
				validLink.waitForPresent();
				validLink.click();
				
				$("workOrders.details.status.edit.button").waitForPresent();
				//Now inside the Service Appointment
				$("workOrders.details.status.edit.button").click();

				$("workOrders.details.edit.status.select").waitForEnabled();
				$("workOrders.details.edit.status.select").click();

				String optionXpath = "//div[@class='select-options']//ul//li//a[text()='" + newStatus + "']";
				QAFExtendedWebElement option = new WebDriverTestBase().getDriver().findElementByXPath(optionXpath);
				option.waitForEnabled();
				option.click();

				$("workOrders.details.save.button").waitForEnabled();
				$("workOrders.details.save.button").click();

				$("workOrders.details.status.edit.button").waitForVisible();

				new WebDriverTestBase().getDriver().get(saListUrl);
				$("workOrders.table.firstResult.link").waitForPresent();
			}
		}

		logger.info("All Work Orders set to " + newStatus);
	}

	@QAFTestStep(description = "set all work order line items of case {caseNumber} to status {newStatus}")
	public void caseWorkOrdersLineItemsStatus(String caseNumber, String newStatus) {
		
		//open case
		if (!inCaseScreen(caseNumber)) {
			goToCase(caseNumber);
		}

		//Go to service appointments table
		$("cases.quickLinks.workOrderLineItems").waitForPresent();
		$("cases.quickLinks.workOrderLineItems").click();

		$("woLineItems.table.firstResult.link").waitForPresent();

		//Save the list url
		String saListUrl = new WebDriverTestBase().getDriver().getCurrentUrl();

		//All links xpath
		//  ((//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr)[1]//a)[1]
		String xpath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr";
		List<WebElement> woliTRs = new WebDriverTestBase().getDriver().findElementsByXPath(xpath);
		if (woliTRs != null && woliTRs.size() > 0) {
			for (int idx=1; idx <= woliTRs.size(); idx++) {
				//Specific link to the WO
				String linkPath = "((//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr)[" + idx + "]//a)[1]";
				QAFExtendedWebElement validLink = new WebDriverTestBase().getDriver().findElementByXPath(linkPath);
				validLink.waitForPresent();
				validLink.click();
				
				$("woLineItems.details.status.edit.button").waitForPresent();
				//Now inside the Service Appointment
				$("woLineItems.details.status.edit.button").click();

				$("woLineItems.details.edit.status.select").waitForEnabled();
				$("woLineItems.details.edit.status.select").click();

				String optionXpath = "//div[@class='select-options']//ul//li//a[text()='" + newStatus + "']";
				QAFExtendedWebElement option = new WebDriverTestBase().getDriver().findElementByXPath(optionXpath);
				option.waitForEnabled();
				option.click();

				$("woLineItems.details.edit.save.button").waitForEnabled();
				$("woLineItems.details.edit.save.button").click();

				$("woLineItems.details.status.edit.button").waitForVisible();

				new WebDriverTestBase().getDriver().get(saListUrl);
				$("woLineItems.table.firstResult.link").waitForPresent();
			}
		}

		logger.info("All Work Order Line Items set to " + newStatus);
	}

	@QAFTestStep(description = "add a comment starting with {comment} to all work order line items of case {caseNumber}")
	public void addCommentToWorkOrdersLineItems(String comment, String caseNumber) {
		
		//open case
		if (!inCaseScreen(caseNumber)) {
			goToCase(caseNumber);
		}

		//Go to service appointments table
		$("cases.quickLinks.workOrderLineItems").waitForPresent();
		$("cases.quickLinks.workOrderLineItems").click();

		$("woLineItems.table.firstResult.link").waitForPresent();

		//Save the list url
		String woliUrl = new WebDriverTestBase().getDriver().getCurrentUrl();

		//All links xpath
		//  ((//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr)[1]//a)[1]
		String xpath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr";
		List<WebElement> woliTRs = new WebDriverTestBase().getDriver().findElementsByXPath(xpath);
		if (woliTRs != null && woliTRs.size() > 0) {
			for (int idx=1; idx <= woliTRs.size(); idx++) {
				//Specific link to the WO
				String linkPath = "((//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr)[" + idx + "]//a)[1]";
				QAFExtendedWebElement validLink = new WebDriverTestBase().getDriver().findElementByXPath(linkPath);
				validLink.waitForPresent();
				validLink.click();
				
				//Have to scroll to find the button
				steps.web.StepsLibrary.waitForPageToFinishLoading();
				steps.common.StepsLibrary.scrollUntilVisible("woLineItems.details.comments.edit.button");
				//$("woLineItems.details.comments.edit.button").waitFort();
				//Now inside the Service Appointment
				$("woLineItems.details.comments.edit.button").click();

				$("woLineItems.details.edit.comments.textarea").waitForEnabled();
				$("woLineItems.details.edit.comments.textarea").clear();
				$("woLineItems.details.edit.comments.textarea").sendKeys(comment + " " + idx);

				$("woLineItems.details.edit.save.button").waitForEnabled();
				$("woLineItems.details.edit.save.button").click();

				$("woLineItems.details.comments.edit.button").waitForVisible();

				new WebDriverTestBase().getDriver().get(woliUrl);
				$("woLineItems.table.firstResult.link").waitForPresent();
			}
		}

		logger.info("All Work Order Line Items comments set to " + comment);
	}

	@QAFTestStep(description = "answer all questions of all work order line items of case {caseNumber} alternating pass and fail")
	public void answerAllWOLIQuestionsAlternating(String caseNumber) {
		
		//open case
		if (!inCaseScreen(caseNumber)) {
			goToCase(caseNumber);
		}

		//Go to service appointments table
		$("cases.quickLinks.workOrderLineItems").waitForPresent();
		$("cases.quickLinks.workOrderLineItems").click();

		$("woLineItems.table.firstResult.link").waitForPresent();

		//Save the list url
		String woliUrl = new WebDriverTestBase().getDriver().getCurrentUrl();

		//All links xpath
		String xpath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr";
		List<WebElement> woliTRs = new WebDriverTestBase().getDriver().findElementsByXPath(xpath);
		if (woliTRs != null && woliTRs.size() > 0) {
			for (int idx=1; idx <= woliTRs.size(); idx++) {
				//Specific link to the WO
				String linkPath = "((//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr)[" + idx + "]//a)[1]";
				QAFExtendedWebElement validLink = new WebDriverTestBase().getDriver().findElementByXPath(linkPath);
				validLink.waitForPresent();
				validLink.click();

				$("woLineItems.checklists.link").waitForPresent();
				$("woLineItems.checklists.link").waitForEnabled();
				$("woLineItems.checklists.link").click();

				$("common.table.link.first").waitForPresent();
				$("common.table.link.first").waitForEnabled();

				//Now in the checklists page. Must edit one by one, alternating pass or fail
				String checklistTrsXPath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr";
				List<WebElement> checklistTRs = new WebDriverTestBase().getDriver().findElementsByXPath(checklistTrsXPath);
				if (checklistTRs != null && checklistTRs.size() > 0) {
					for (int clIdx=1; clIdx <= checklistTRs.size(); clIdx++) {
						int answerTdIndex = 5;
						int actionsTdIndex = 8;

						String answerXpath = "(" + checklistTrsXPath + ")[" + clIdx + "]//td[" + answerTdIndex + "]//span//span";
						QAFExtendedWebElement answerSpan = 
							new WebDriverTestBase().getDriver().findElementByXPath(answerXpath);
						//If the answer is N/A, skip
						if (answerSpan.isPresent() && !answerSpan.getText().equals("N/A")) {
							//Can answer this question. Go
							String actionButtonXpath = 
								"(" + checklistTrsXPath + ")[" + clIdx + "]//td[" + actionsTdIndex + "]//a";
							QAFExtendedWebElement actionButton = 
								new WebDriverTestBase().getDriver().findElementByXPath(actionButtonXpath);
							actionButton.click();
							$("checklists.table.action.edit.button").waitForVisible();
							$("checklists.table.action.edit.button").click();
							
							$("checklists.edit.popup.answerPassFail.input").waitForVisible();
							$("checklists.edit.popup.answerPassFail.input").waitForEnabled();
							$("checklists.edit.popup.answerPassFail.input").click();
							//Even numbers are fail. Odd are pass.
							if (clIdx % 2==0) {
								$("checklists.edit.popup.answerPassFail.option.fail").waitForVisible();
								$("checklists.edit.popup.answerPassFail.option.fail").click();
								$("checklists.edit.popup.answerPassFail.option.fail").waitForNotVisible();
							} else {
								$("checklists.edit.popup.answerPassFail.option.pass").waitForVisible();
								$("checklists.edit.popup.answerPassFail.option.pass").click();
								$("checklists.edit.popup.answerPassFail.option.pass").waitForNotVisible();
							}

							$("checklists.edit.popup.save.button").click();
							$("common.toastContainer").waitForVisible();
						}
						
					}

					Reporter.logWithScreenShot("Work order line items questions answered");
				}

				new WebDriverTestBase().getDriver().get(woliUrl);
				$("woLineItems.table.firstResult.link").waitForPresent();
			}
		}

		logger.info("All Work Order Line Items have been answered with pass and fail");
	}

	@QAFTestStep(description = "close case {caseNumber} with {reactiveServiceType}")
	public void closeCase(String caseNumber, String reactiveServiceType) {
		
		//open case
		if (!inCaseScreen(caseNumber)) {
			goToCase(caseNumber);
		}

		//Change status
		$("cases.closeCase.button").waitForPresent();
		$("cases.closeCase.button").waitForEnabled();
		$("cases.closeCase.button").click();

		$("cases.closeCase.caseResolutionDate.datepicker").waitForPresent();
		$("cases.closeCase.caseResolutionDate.datepicker").waitForEnabled();
		$("cases.closeCase.caseResolutionDate.datepicker").click();

		$("common.openCalendar.today").waitForPresent();
		$("common.openCalendar.today").waitForEnabled();
		$("common.openCalendar.today").click();
	 
		$("cases.details.status.edit.input").waitForPresent();
		$("cases.details.status.edit.input").waitForEnabled();
		$("cases.details.status.edit.input").click();

		$("cases.closeCase.reactiveServiceType.select").waitForEnabled();
		$("cases.closeCase.reactiveServiceType.select").click();

		String optionXpath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//label[text()='Reactive Service Type']//following::span[text()='" + reactiveServiceType + "']//ancestor::lightning-base-combobox-item[1]";
		QAFExtendedWebElement option = new WebDriverTestBase().getDriver().findElementByXPath(optionXpath);
		option.waitForEnabled();
		option.click();

		Validator.assertTrue(option.isPresent() && option.isEnabled(), 
			"Reactive Service Type " + reactiveServiceType + " not found as an option to set when closing case " + caseNumber,
			"Reactive Service Type " + reactiveServiceType + " found as an option to set when closing case " + caseNumber);

		$("cases.closeCase.save.button").waitForEnabled();
		$("cases.closeCase.save.button").click();

		$("case.details.status").waitForPresent();
		$("case.details.status").waitForEnabled();

		$("case.details.status").assertText("Closed");
	}

	/**
	 * Navigate to the case screen of case with number caseNumber
	 * 
	 * @param caseNumber The case number to navigate to
	 */
	@QAFTestStep(description = "go to case {caseNumber}")
	public void goToCase(String caseNumber) {
		ShrdLaunchApp launchApp = new ShrdLaunchApp();
		launchApp.customShrdLaunchApp("cases");

		$("common.searchAssistant.button").waitForEnabled();
		CommonStep.click("common.searchAssistant.button");
		CommonStep.sendKeys(""+String.valueOf(caseNumber)+"","common.searchAssistant.input");

		$("common.search.showMoreResults").waitForVisible();
		$("common.search.showMoreResults").waitForEnabled();
		$("common.search.showMoreResults").click();

		steps.web.StepsLibrary.waitForPageToFinishLoading();

		if ($("search.results.categories.cases").isPresent()) {
			$("search.results.categories.cases").click();
		} else {
			if ($("search.results.categories.expandList.button").isPresent() && $("search.results.categories.expandList.button").isEnabled()) {
				$("search.results.categories.expandList.button").click();
			}

			$("search.results.categories.showMore.button").waitForPresent();
			$("search.results.categories.showMore.button").waitForEnabled();
			$("search.results.categories.showMore.button").click();
			steps.web.StepsLibrary.waitForPageToFinishLoading();
			if ($("search.results.categories.cases").isPresent()) {
				$("search.results.categories.cases").click();
			}
		}

		$("search.results.panel.cases.title").waitForEnabled();
		$("search.results.panel.cases.title").waitForVisible();

		steps.web.StepsLibrary.waitForPageToFinishLoading();

		$("search.results.panel.cases.table.tr.first").waitForEnabled();
		$("search.results.panel.cases.table.tr.first.link.first").waitForEnabled();

		$("search.results.panel.cases.table.tr.first.link.first").assertText(caseNumber);
		$("search.results.panel.cases.table.tr.first.link.first").click();

		$("cases.titleCaseNumber").waitForVisible();

		$("cases.titleCaseNumber").waitForPresent();
		$("cases.titleCaseNumber").assertText(String.valueOf(caseNumber));
	}

	public boolean inCaseScreen(String caseNumber) {
		try {
			QAFWebElement title = $("common.entityNameTitle");
			if (!title.isPresent() || !title.isDisplayed() || !title.getText().equalsIgnoreCase("Case")) {
				return false;
			}
	
			QAFWebElement details = $("common.entityNameTitleDetails");
			if (!details.isPresent() || !details.isDisplayed() || !details.getText().equals(caseNumber)) {
				return false;
			}
			return true;
		} catch (Exception x) {
			return false;
		}
	}


	@QAFTestStep(description = "create a case with data {projectName} {subject} {caseDescription} {summary} {recordType} {casePriority} {caseOrigin} {reprtedIssue} {caseCause}")
	public void createCaseWithData(Object projectName, Object subject, Object caseDescription, Object summary,
	    Object recordType, Object casePriority,Object caseOrigin,Object reportedIssue,Object caseCause) {

		ShrdCreateCase create = new ShrdCreateCase();
		create.customShrdCreateCase(projectName, subject, caseDescription, summary,
		    recordType, casePriority, caseOrigin, reportedIssue, caseCause);
	}
}