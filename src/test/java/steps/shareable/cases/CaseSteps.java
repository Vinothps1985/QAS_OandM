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
import com.qmetry.qaf.automation.util.Validator;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import steps.common.*;
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
		$("cases.search.firstResult").waitForVisible();
		$("cases.search.firstResult").waitForEnabled();
		CommonStep.click("cases.search.firstResult");
		$("cases.caseNumber").waitForVisible();

		$("cases.caseNumber").waitForPresent();
		$("cases.caseNumber").assertText(String.valueOf(caseNumber));
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
}