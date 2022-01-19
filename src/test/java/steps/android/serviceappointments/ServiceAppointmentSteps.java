package steps.android.serviceappointments;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;

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
import steps.shareable.ShrdLaunchApp;

public class ServiceAppointmentSteps extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	@QAFTestStep(description = "click on service appointment {saNumber}")
	public void goToServiceAppointment(String saNumber) {
        String xpath = "//android.widget.TextView[@text='" + saNumber + "']//ancestor::*[@clickable='true'][1]";
        WebElement element = getDriver().findElementByXPath(xpath);
        element.click();
	}

	@QAFTestStep(description = "click the date {date} in the scheduler datepicker")
	public void selectDateInSchedulerDatepicker(String date) {

		boolean success = false;
		try {
			String xpath = "//androidx.recyclerview.widget.RecyclerView" +
				"[@resource-id='com.salesforce.fieldservice.app:id/date_picker_recyclerview']" + 
				"//android.widget.LinearLayout[contains(@content-desc, '" + date + "')]";

			WebElement element = getDriver().findElementByXPath(xpath);
			element.click();
			success = true;
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.assertTrue(success,
			"Could not find the date " + date + " in the scheduler datepicker",
			"Date " + date + " found successfully in the scheduler datepicker");
	}

	@QAFTestStep(description = "answer the work order line item checklist until the question {question} appears")
	public void answerWoliChecklistUntil(String question) {
		boolean success = false;
		try {
			int max = 50;
			for (int i=0; i < max; i++) {
				//Should we exit?
				try {
					String questionToExit = "(//android.widget.TextView[contains(@text, '" + question + "')])[1]";
					WebElement elementExit = getDriver().findElementByXPath(questionToExit);
					if (elementExit != null && elementExit.isDisplayed() && elementExit.isEnabled()) {
						success = true;
						break;
					}
				} catch (Exception x){}

				//Is this a 'Result' question?
				boolean answered = false;
				try {
					String resultXpath= "(//android.widget.TextView[contains(@text, 'Result')])[1]";
					WebElement resultElement = getDriver().findElementByXPath(resultXpath);
					if (resultElement != null && resultElement.isDisplayed() && resultElement.isEnabled()) {
						String option = "Pass";
						String xpath= "(" +
							"//android.widget.TextView[contains(@text, 'Result')]//following::android.widget.TextView[@text='" + option + "'][1]" +
							")" +
							"//ancestor::*[@clickable='true'][1]";

						WebElement optionElement = getDriver().findElementByXPath(xpath);
						if (optionElement != null && optionElement.isDisplayed() && optionElement.isEnabled()) {
							optionElement.click();
							//Click the next button also...
							$("woLineItem.complete.next.button").click();
							answered = true;
						}
					}
				} catch (Exception x){}

				if (!answered) {
					//Is this a 'photo' section to just click continue?
					try {
						QAFWebElement photoElement = $("woLineItem.complete.takePhotos.text");
						if (photoElement != null && photoElement.isDisplayed() && photoElement.isEnabled()) {
							//Just click the next button
							$("woLineItem.complete.next.button").click();
							answered = true;
						}
					} catch (Exception x) {}
				}

				if (!answered) {
					//Is this a 'Service Contract' Question?
					try {
						QAFWebElement serviceContractElement = $("woLineItem.serviceContractQuestion.input");
						if (serviceContractElement.getText().equals("") && serviceContractElement.isDisplayed() && serviceContractElement.isEnabled()) {
							//Input the Service Contract test data
							$("woLineItem.serviceContractQuestion.input").click();
							$("woLineItem.serviceContractQuestion.input").sendKeys("5");
							$("woLineItem.customChecklistNumeric.text").click();
							//Click the next button
                            $("woLineItem.complete.next.button").waitForEnabled();
							$("woLineItem.complete.next.button").click();
							answered = true;
						}
					} catch (Exception x) {}
				}

				Thread.sleep(2000);
			}

		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.assertTrue(success,
			"Could not finish answering the checklist",
			"Checklist answered sucessfully and the question " + question + " appeared");
	}

	public void scrollAndroidToEnd() {
		try {
			int maxAttempts = 2;
			int attempt = 0;
			while (attempt < maxAttempts) {
				getDriver().findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true))" +
				".setSwipeDeadZonePercentage(.3).setMaxSearchSwipes(20).scrollToEnd(20, 20)"));

				attempt++;
				Thread.sleep(100);
			}
		} catch (Exception x) {
			//x.printStackTrace();
			logger.error(x);
		}
	}

}