package steps.shareable.quotes;

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

public class QuoteSteps extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	@QAFTestStep(description = "change status of quote {quoteNumber} to {newStatus}")
	public void quoteChangeStatus(String quoteNumber, String newStatus) {
		
		//open quote
		if (!inQuoteScreen(quoteNumber)) {
			goToQuote(quoteNumber);
		}

		//Change status
		$("quotes.details.status.edit.button").waitForPresent();
		$("quotes.details.status.edit.button").waitForEnabled();
		$("quotes.details.status.edit.button").click();
	 
		$("quotes.details.status.edit.input").waitForPresent();
		$("quotes.details.status.edit.input").waitForEnabled();
		$("quotes.details.status.edit.input").click();

		String xpath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//label[text() = 'Status']//following::span[text()='" + newStatus + "']";
		QAFExtendedWebElement option = new WebDriverTestBase().getDriver().findElementByXPath(xpath);

		Validator.assertTrue(option.isPresent() && option.isEnabled(), 
			"Status " + newStatus + " not found as an option to set for quote " + quoteNumber,
			"Status " + newStatus + " found as an option to set for quote " + quoteNumber);
		
		option.click();

		$("quotes.details.edit.save.button").waitForEnabled();
		$("quotes.details.edit.save.button").click();

		$("quotes.details.status.edit.button").waitForVisible();
	}

	public void goToQuote(String quoteNumber) {
		ShrdLaunchApp launchApp = new ShrdLaunchApp();
		launchApp.customShrdLaunchApp("quotes");

		$("common.searchAssistant.button").waitForEnabled();
		CommonStep.click("common.searchAssistant.button");
		CommonStep.sendKeys(""+String.valueOf(quoteNumber)+"","common.searchAssistant.input");
		$("quotes.search.firstResult").waitForVisible();
		$("quotes.search.firstResult").waitForEnabled();
		CommonStep.click("quotes.search.firstResult");
		$("quote.details.quoteNumber").waitForVisible();

		$("quote.details.quoteNumber").waitForPresent();
		$("quote.details.quoteNumber").assertText(String.valueOf(quoteNumber));
	}

	public boolean inQuoteScreen(String quoteNumber) {
		try {
			QAFWebElement title = $("common.entityNameTitle");
			if (!title.isPresent() || !title.isDisplayed() || !title.getText().equalsIgnoreCase("Quote")) {
				return false;
			}
	
			QAFWebElement details = $("common.entityNameTitleDetails");
			if (!details.isPresent() || !details.isDisplayed() || !details.getText().equals(quoteNumber)) {
				return false;
			}
			return true;
		} catch (Exception x) {
			return false;
		}
	}
}