
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
public class ShrdCreateAndApproveQuote extends WebDriverTestCase {

	@QAFTestStep(description = "ShrdCreateAndApproveQuote {0} {1} {2} {3} {4} {5} {6} {7} {8} {9} {10} {11} {12} {13}")
	public void customShrdShrdCreateAndApproveQuote(String caseNumber,Object salesRep,Object primaryContact,Object specialNotes,Object laborBilling,Object pmBilling,Object costCode1,Object costCode2,Object notes1,Object notes2,Object vendor1,Object vendor2,Object vendorContact1,Object vendorContact2) {
		
		ShrdLaunchApp launchApp = new ShrdLaunchApp();
		launchApp.customShrdLaunchApp("cases");

		$("common.searchAssistant.button").waitForEnabled();
		CommonStep.click("common.searchAssistant.button");
		CommonStep.clear("common.searchAssistant.input");
		CommonStep.sendKeys(caseNumber,"common.searchAssistant.input");
		$("cases.search.firstResult").waitForPresent();
		$("cases.search.firstResult").waitForEnabled();
		CommonStep.click("cases.search.firstResult");

		$("cases.createNewQuote.button").waitForEnabled();
		CommonStep.click("cases.createNewQuote.button");

		$("quotes.createQuote.popup.salesRep.input").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.salesRep.input");
		CommonStep.clear("quotes.createQuote.popup.salesRep.input");
		CommonStep.sendKeys(""+String.valueOf(salesRep)+"","quotes.createQuote.popup.salesRep.input");
		$("quotes.createQuote.popup.salesRep.firstOption").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.salesRep.firstOption");
		$("quotes.createQuote.popup.primaryContact.input").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.primaryContact.input");
		CommonStep.sendKeys(""+String.valueOf(primaryContact)+"","quotes.createQuote.popup.primaryContact.input");
		$("quotes.createQuote.popup.primaryContact.firstOption").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.primaryContact.firstOption");

		TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot
		
		$("quotes.createQuote.popup.estimatedWorkStartDate.calendar.icon").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.estimatedWorkStartDate.calendar.icon");
		$("common.openLightningCalendar.today").waitForEnabled();
		CommonStep.click("common.openLightningCalendar.today");
		$("quotes.createQuote.popup.siteWorkState.select").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.siteWorkState.select");
		$("quotes.createQuote.popup.siteWorkState.option.second").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.siteWorkState.option.second");
		$("quotes.createQuote.popup.typeOfWork.select").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.typeOfWork.select");
		$("quotes.createQuote.popup.typeOfWork.option.initialVisit").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.typeOfWork.option.initialVisit");
		CommonStep.clear("quotes.createQuote.popup.specialNotes.textarea");
		CommonStep.sendKeys(""+String.valueOf(specialNotes)+"","quotes.createQuote.popup.specialNotes.textarea");

		TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot

		steps.web.StepsLibrary.scrollUntilVisible("quotes.createQuote.popup.useDefaultShippingLocation.checkbox");

		$("quotes.createQuote.popup.useDefaultShippingLocation.checkbox").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.useDefaultShippingLocation.checkbox");
		$("quotes.createQuote.popup.save.button").waitForEnabled();
		CommonStep.click("quotes.createQuote.popup.save.button");

		$("common.toastContainer").waitForPresent();
		$("quotes.details.salesRep.text").waitForPresent();
		
		$("quotes.details.salesRep.text").assertText(""+String.valueOf(salesRep)+"");

		TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot

		$("quotes.editLines.button").waitForPresent();
		$("quotes.editLines.button").waitForEnabled();
		CommonStep.click("quotes.editLines.button");
		$("quotes.edit.iframe").waitForEnabled();
		CommonStep.click("quotes.edit.iframe");
		new WebDriverTestBase().getDriver().switchTo().frame(new QAFExtendedWebElement("quotes.edit.iframe"));
		$("quotes.edit.laborBilling.select").waitForPresent();
		StepsLibrary.selectIn("label="+String.valueOf(laborBilling)+"","quotes.edit.laborBilling.select");
		StepsLibrary.selectIn("label="+String.valueOf(pmBilling)+"","quotes.edit.pmBilling.select");
		$("quotes.edit.addProducts.button").waitForEnabled();
		CommonStep.click("quotes.edit.addProducts.button");
		$("quotes.addProducts.paperDrawerPanel").waitForPresent();
		$("quotes.addProducts.categoryRows.first").waitForEnabled();
		CommonStep.click("quotes.addProducts.categoryRows.first");
		$("quotes.addProducts.rows.second.checkbox").waitForEnabled();
		CommonStep.click("quotes.addProducts.rows.second.checkbox");
		$("quotes.addProducts.rows.third.checkbox").waitForEnabled();
		CommonStep.click("quotes.addProducts.rows.third.checkbox");
		$("quotes.addProducts.select.button").waitForEnabled();
		CommonStep.click("quotes.addProducts.select.button");

		TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot

		$("quotes.edit.save.button").assertPresent();
		$("quotes.edit.products.row.1").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.1");
		$("quotes.edit.products.row.1.costCode.edit.button").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.1.costCode.edit.button");
		StepsLibrary.selectIn("label="+String.valueOf(costCode1)+"","quotes.edit.products.row.1.costCode.select");
		$("quotes.edit.products.row.2").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.2");
		$("quotes.edit.products.row.2.costCode.edit.button").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.2.costCode.edit.button");
		StepsLibrary.selectIn("label="+String.valueOf(costCode2)+"","quotes.edit.products.row.2.costCode.select");
		$("quotes.edit.products.row.1").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.1");
		$("quotes.edit.products.row.1.notes.edit.button").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.1.notes.edit.button");
		CommonStep.clear("quotes.edit.products.row.1.notes.textarea");
		CommonStep.sendKeys(""+String.valueOf(notes1)+"","quotes.edit.products.row.1.notes.textarea");
		$("quotes.edit.products.row.2").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.2");
		$("quotes.edit.products.row.2.notes.edit.button").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.2.notes.edit.button");
		CommonStep.clear("quotes.edit.products.row.2.notes.textarea");
		CommonStep.sendKeys(""+String.valueOf(notes2)+"","quotes.edit.products.row.2.notes.textarea");
		$("quotes.edit.products.row.1.vendor.edit.button").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.1.vendor.edit.button");
		CommonStep.clear("quotes.edit.products.row.1.vendor.input");
		CommonStep.sendKeys(""+String.valueOf(vendor1)+"","quotes.edit.products.row.1.vendor.input");
		try {Thread.sleep(3000);} catch (Exception e) {System.out.println(e);}
		$("quotes.edit.products.row.1.vendor.option.2").waitForPresent();
		$("quotes.edit.products.row.1.vendor.option.2").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.1.vendor.option.2");
		$("quotes.edit.products.row.2.vendor.edit.button").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.2.vendor.edit.button");
		CommonStep.clear("quotes.edit.products.row.2.vendor.input");
		CommonStep.sendKeys(""+String.valueOf(vendor2)+"","quotes.edit.products.row.2.vendor.input");
		try {Thread.sleep(3000);} catch (Exception e) {System.out.println(e);}
		$("quotes.edit.products.row.2.vendor.option.2").waitForPresent();
		$("quotes.edit.products.row.2.vendor.option.2").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.2.vendor.option.2");
		$("quotes.edit.products.row.1.vendorContact.edit.button").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.1.vendorContact.edit.button");
		CommonStep.clear("quotes.edit.products.row.1.vendorContact.input");
		CommonStep.sendKeys(""+String.valueOf(vendorContact1)+"","quotes.edit.products.row.1.vendorContact.input");
		try {Thread.sleep(3000);} catch (Exception e) {System.out.println(e);}
		$("quotes.edit.products.row.1.vendorContact.option.2").waitForPresent();
		$("quotes.edit.products.row.1.vendorContact.option.2").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.1.vendorContact.option.2");

		TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot

		$("quotes.edit.products.row.2.vendorContact.edit.button").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.2.vendorContact.edit.button");
		CommonStep.clear("quotes.edit.products.row.2.vendorContact.input");
		CommonStep.sendKeys(""+String.valueOf(vendorContact2)+"","quotes.edit.products.row.2.vendorContact.input");
		try {Thread.sleep(3000);} catch (Exception e) {System.out.println(e);}
		$("quotes.edit.products.row.2.vendorContact.option.2").waitForPresent();
		$("quotes.edit.products.row.2.vendorContact.option.2").waitForEnabled();
		CommonStep.click("quotes.edit.products.row.2.vendorContact.option.2");

		$("quotes.edit.save.button").assertPresent();
		$("quotes.edit.save.button").waitForEnabled();
		CommonStep.click("quotes.edit.save.button");

		new WebDriverTestBase().getDriver().switchTo().defaultContent();

		$("quotes.details.recordType").waitForPresent();
		$("quotes.details.recordType").assertText("Draft");

		TestBaseProvider.instance().get().takeScreenShot(); //Take a screenshot

		$("quotes.submitForApproval.button").waitForEnabled();
		CommonStep.click("quotes.submitForApproval.button");

		$("quotes.submitForApproval.popup.submit.button").waitForEnabled();
		CommonStep.click("quotes.submitForApproval.popup.submit.button");

		$("quotes.details.recordType").waitForPresent();
		$("quotes.details.recordType.approved").waitForPresent();
	}
}




