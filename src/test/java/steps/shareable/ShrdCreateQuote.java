
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

import com.qmetry.qaf.automation.util.Reporter;
import com.qmetry.qaf.automation.util.Validator;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import steps.common.*;

/**
 * @author Rodrigo Montemayor
 */
public class ShrdCreateQuote extends WebDriverTestCase {

	/**
	 * Creates a new case in salesforce
	 * 
	 * If correctly generated, the quote number is saved on a variable called
	 * ${generated_quoteNumber}
	 * 
	 * 
	 * @param caseNumber
	 * @param salesRep
	 * @param primaryContact
	 * @param specialNotes
	 * @param laborBilling
	 * @param pmBilling
	 * @param costCode1
	 * @param costCode2
	 * @param notes1
	 * @param notes2
	 * @param vendor1
	 * @param vendor2
	 * @param vendorContact1
	 * @param vendorContact2
	 * @throws InterruptedException
	 */
	@QAFTestStep(description = "ShrdCreateQuoteWithLines {0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13} {14}")
	public void customShrdShrdCreateQuoteWithLines(String caseNumber, Object salesRep, Object primaryContact,
			Object specialNotes, Object typeOfWork, Object laborBilling, Object pmBilling, Object costCode1,
			Object costCode2, Object notes1, Object notes2, Object vendor1, Object vendor2, Object vendorContact1,
			Object vendorContact2) throws InterruptedException {

		customShrdShrdCreateQuote(caseNumber, salesRep, primaryContact, specialNotes, typeOfWork);

		$("quotes.editLines.button").waitForPresent();
		$("quotes.editLines.button").waitForEnabled();
		$("quotes.editLines.button").click();
		
		//$("quotes.edit.iframe").waitForPresent();
		//wait(10000);

		$("quotes.edit.iframe").waitForVisible();
		$("quotes.edit.iframe").waitForPresent(120 * 1000);
		$("quotes.edit.iframe").waitForEnabled();
		//Thread.sleep(3000);
		//$("quotes.edit.iframe").click();
		new WebDriverTestBase().getDriver().switchTo().frame(new QAFExtendedWebElement("quotes.edit.iframe"));
		System.out.println("Switched to frame");
		//steps.web.StepsLibrary.switchToFrameUntilLocIsPresent("quotes.edit.iframe", "quotes.edit.laborBilling.select");

		$("quotes.edit.laborBilling.select").waitForPresent();
		$("quotes.edit.laborBilling.select").waitForVisible();
		$("quotes.edit.laborBilling.select").waitForEnabled();
		StepsLibrary.selectIn("label=" + String.valueOf(laborBilling) + "", "quotes.edit.laborBilling.select");
		StepsLibrary.selectIn("label=" + String.valueOf(pmBilling) + "", "quotes.edit.pmBilling.select");
		$("quotes.edit.addProducts.button").waitForEnabled();
		$("quotes.edit.addProducts.button").click();
		$("quotes.addProducts.paperDrawerPanel").waitForPresent();
		$("quotes.addProducts.categoryRows.first").waitForEnabled();
		$("quotes.addProducts.categoryRows.first").click();
		$("quotes.addProducts.rows.second.checkbox").waitForEnabled();
		$("quotes.addProducts.rows.second.checkbox").click();
		$("quotes.addProducts.rows.third.checkbox").waitForEnabled();
		$("quotes.addProducts.rows.third.checkbox").click();
		$("quotes.addProducts.select.button").waitForEnabled();
		$("quotes.addProducts.select.button").click();

		Reporter.logWithScreenShot("take a screenshot");

		$("quotes.edit.save.button").assertPresent();
		$("quotes.edit.products.row.1").waitForEnabled();
		$("quotes.edit.products.row.1").click();
		$("quotes.edit.products.row.1.costCode.edit.button").waitForEnabled();
		$("quotes.edit.products.row.1.costCode.edit.button").click();
		StepsLibrary.selectIn("label=" + String.valueOf(costCode1) + "", "quotes.edit.products.row.1.costCode.select");
		$("quotes.edit.products.row.2").waitForEnabled();
		$("quotes.edit.products.row.2").click();
		$("quotes.edit.products.row.2.costCode.edit.button").waitForEnabled();
		$("quotes.edit.products.row.2.costCode.edit.button").click();
		StepsLibrary.selectIn("label=" + String.valueOf(costCode2) + "", "quotes.edit.products.row.2.costCode.select");
		$("quotes.edit.products.row.1").waitForEnabled();
		$("quotes.edit.products.row.1").click();
		$("quotes.edit.products.row.1.notes.edit.button").waitForEnabled();
		$("quotes.edit.products.row.1.notes.edit.button").click();
		CommonStep.clear("quotes.edit.products.row.1.notes.textarea");
		CommonStep.sendKeys("" + String.valueOf(notes1) + "", "quotes.edit.products.row.1.notes.textarea");
		$("quotes.edit.products.row.2").waitForEnabled();
		$("quotes.edit.products.row.2").click();
		$("quotes.edit.products.row.2.notes.edit.button").waitForEnabled();
		$("quotes.edit.products.row.2.notes.edit.button").click();
		CommonStep.clear("quotes.edit.products.row.2.notes.textarea");
		CommonStep.sendKeys("" + String.valueOf(notes2) + "", "quotes.edit.products.row.2.notes.textarea");
		$("quotes.edit.products.row.1.vendor.edit.button").waitForEnabled();
		$("quotes.edit.products.row.1.vendor.edit.button").click();
		CommonStep.clear("quotes.edit.products.row.1.vendor.input");
		CommonStep.sendKeys("" + String.valueOf(vendor1) + "", "quotes.edit.products.row.1.vendor.input");
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			System.out.println(e);
		}
		$("quotes.edit.products.row.1.vendor.option.2").waitForPresent();
		$("quotes.edit.products.row.1.vendor.option.2").waitForEnabled();
		$("quotes.edit.products.row.1.vendor.option.2").click();
		$("quotes.edit.products.row.2.vendor.edit.button").waitForEnabled();
		$("quotes.edit.products.row.2.vendor.edit.button").click();
		CommonStep.clear("quotes.edit.products.row.2.vendor.input");
		CommonStep.sendKeys("" + String.valueOf(vendor2) + "", "quotes.edit.products.row.2.vendor.input");
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			System.out.println(e);
		}
		$("quotes.edit.products.row.2.vendor.option.2").waitForPresent();
		$("quotes.edit.products.row.2.vendor.option.2").waitForEnabled();
		$("quotes.edit.products.row.2.vendor.option.2").click();
		$("quotes.edit.products.row.1.vendorContact.edit.button").waitForEnabled();
		$("quotes.edit.products.row.1.vendorContact.edit.button").click();
		CommonStep.clear("quotes.edit.products.row.1.vendorContact.input");
		CommonStep.sendKeys("" + String.valueOf(vendorContact1) + "", "quotes.edit.products.row.1.vendorContact.input");
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			System.out.println(e);
		}
		$("quotes.edit.products.row.1.vendorContact.option.2").waitForPresent();
		$("quotes.edit.products.row.1.vendorContact.option.2").waitForEnabled();
		$("quotes.edit.products.row.1.vendorContact.option.2").click();

		Reporter.logWithScreenShot("take a screenshot");

		$("quotes.edit.products.row.2.vendorContact.edit.button").waitForEnabled();
		$("quotes.edit.products.row.2.vendorContact.edit.button").click();
		CommonStep.clear("quotes.edit.products.row.2.vendorContact.input");
		CommonStep.sendKeys("" + String.valueOf(vendorContact2) + "", "quotes.edit.products.row.2.vendorContact.input");
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			System.out.println(e);
		}
		$("quotes.edit.products.row.2.vendorContact.option.2").waitForPresent();
		$("quotes.edit.products.row.2.vendorContact.option.2").waitForEnabled();
		$("quotes.edit.products.row.2.vendorContact.option.2").click();

		$("quotes.edit.save.button").assertPresent();
		$("quotes.edit.save.button").waitForEnabled();
		CommonStep.click("quotes.edit.save.button");

		new WebDriverTestBase().getDriver().switchTo().defaultContent();

		$("quotes.details.recordType").waitForPresent();
		$("quotes.details.recordType").waitForEnabled();
		$("quotes.details.recordType").waitForText("Draft");
		$("quotes.details.recordType").assertText("Draft");

		// TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot
		Reporter.logWithScreenShot("take a screenshot");
	}

	/**
	 * 
	 * @param quoteNumber
	 */
	@QAFTestStep(description = "approve quote {quoteNumber}")
	public void approveQuote(String quoteNumber) {

		// Confirm in quotes page
		// Confirm in quote page with the quote number

		if (!$("quote.details.quoteNumber").isPresent() || !$("quote.details.quoteNumber").isDisplayed()) {
			// Go to correct quote
		}

		$("quotes.submitForApproval.button").waitForEnabled();
		$("quotes.submitForApproval.button").click();

		$("quotes.submitForApproval.popup.submit.button").waitForEnabled();
		$("quotes.submitForApproval.popup.submit.button").click();

		$("quotes.details.recordType").waitForPresent();
		$("quotes.details.recordType.approved").waitForPresent();
	}

	/**
	 * Creates a new quote in salesforce (without any quote lines)
	 * 
	 * If correctly generated, the quote number is saved on a variable called
	 * ${generated_quoteNumber}
	 * 
	 * 
	 * @param caseNumber
	 * @param salesRep
	 * @param primaryContact
	 * @param specialNotes
	 */
	@QAFTestStep(description = "ShrdCreateQuote {caseNumber} {salesRep} {primaryContact} {specialNotes} {typeOfWork}")
	public void customShrdShrdCreateQuote(String caseNumber, Object salesRep, Object primaryContact,
			Object specialNotes, Object typeOfWork) {

		ShrdLaunchApp launchApp = new ShrdLaunchApp();
		launchApp.customShrdLaunchApp("cases");

		$("common.searchAssistant.button").waitForEnabled();
		$("common.searchAssistant.button").click();
		$("common.searchAssistant.input").clear();
		CommonStep.sendKeys(caseNumber, "common.searchAssistant.input");
		$("cases.search.firstResult").waitForPresent();
		$("cases.search.firstResult").waitForEnabled();
		$("cases.search.firstResult").click();

		$("cases.createNewQuote.button").waitForEnabled();
		$("cases.createNewQuote.button").click();

		$("quotes.createQuote.popup.salesRep.input").waitForEnabled();
		$("quotes.createQuote.popup.salesRep.input").click();
		$("quotes.createQuote.popup.salesRep.input").clear();
		CommonStep.sendKeys("" + String.valueOf(salesRep) + "", "quotes.createQuote.popup.salesRep.input");
		$("quotes.createQuote.popup.salesRep.firstOption").waitForEnabled();
		$("quotes.createQuote.popup.salesRep.firstOption").click();
		$("quotes.createQuote.popup.primaryContact.input").waitForEnabled();
		$("quotes.createQuote.popup.primaryContact.input").click();
		$("quotes.createQuote.popup.primaryContact.input").clear();
		// CommonStep.click("quotes.createQuote.popup.primaryContact.input");
		CommonStep.sendKeys("" + String.valueOf(primaryContact) + "", "quotes.createQuote.popup.primaryContact.input");
		$("quotes.createQuote.popup.primaryContact.firstOption").waitForEnabled();
		//CommonStep.click("quotes.createQuote.popup.primaryContact.firstOption");
		$("quotes.createQuote.popup.primaryContact.firstOption").click();

		//TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot
		Reporter.logWithScreenShot("take a screenshot");
		
		$("quotes.createQuote.popup.estimatedWorkStartDate.calendar.icon").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.estimatedWorkStartDate.calendar.icon");
		$("common.openLightningCalendar.today").waitForEnabled();
		CommonStep.click("common.openLightningCalendar.today");
		$("quotes.createQuote.popup.siteWorkState.select").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.siteWorkState.select");
		$("quotes.createQuote.popup.siteWorkState.option.second").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.siteWorkState.option.second");
		steps.common.StepsLibrary.scrollUntilVisible("quotes.createQuote.popup.typeOfWork.button");
		$("quotes.createQuote.popup.typeOfWork.button").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.typeOfWork.button");
		$("quotes.createQuote.popup.typeOfWork.option.initialVisit").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.typeOfWork.option.initialVisit");
		//steps.web.StepsLibrary.selectSalesforceDropdownOption("*Type of Work", String.valueOf(typeOfWork));
		CommonStep.clear("quotes.createQuote.popup.specialNotes.textarea");
		CommonStep.sendKeys(""+String.valueOf(specialNotes)+"","quotes.createQuote.popup.specialNotes.textarea");

		//TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot
		Reporter.logWithScreenShot("take a screenshot");

		steps.common.StepsLibrary.scrollUntilVisible("quotes.createQuote.popup.useDefaultShippingLocation.checkbox");

		//$("quotes.createQuote.popup.useDefaultShippingLocation.checkbox").waitForEnabled();
		$("quotes.createQuote.popup.useDefaultShippingLocation.checkbox").click();
		$("quotes.createQuote.popup.save.button").waitForEnabled();
		$("quotes.createQuote.popup.save.button").click();

		$("common.toastContainer").waitForPresent();
		$("quotes.details.salesRep.text").waitForPresent();
		
		$("quotes.details.salesRep.text").assertText(""+String.valueOf(salesRep)+"");

		//TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot
		Reporter.logWithScreenShot("take a screenshot");

		logger.info("Generated quote number: " + $("quote.details.quoteNumber").getText());
		CommonStep.store($("quote.details.quoteNumber").getText(), "generated_quoteNumber");

	}
}




