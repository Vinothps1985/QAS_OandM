package steps.android;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;

import java.time.Duration;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import java.util.List;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import support.Util;

import com.qmetry.qaf.automation.step.CommonStep;
import com.qmetry.qaf.automation.step.QAFTestStep;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;

import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.util.Validator;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import steps.common.*;

/**
 * Steps Library for Android executions.
 * It's important to note that this steps will be available for Android and hybrid
 * executions.
 * 
 * If you want steps to be visible in web and Android (both), code them in the StepsLibrary
 * class inside the 'common' package.
 */
public class StepsLibrary {
	
	static Log logger = LogFactory.getLog(StepsLibrary.class);

	/**
	 * Waits a certain amount of minutes for something to be not-present.
	 * This is useful in Android for example when waiting for the 'Sync' process
	 * to finish, because it takes several minutes
	 * 
	 * @param minutes Maximum minutes to wait
	 * @param loc The locator that we're waiting to be not-present anymore
	 */
	@QAFTestStep(description = "wait for {0} minutes for {1} to be not present")
	public static void waitForLocToBeNotPresentMinutes(int minutes, String loc) {
		logger.info("Waiting for " + minutes + " minutes until " + loc + " is not present...");
		StepsLibrary.waitForLocToBeNotPresent(minutes*60*1000, loc);
	}

	/**
	 * Waits a certain amount of miliseconds for something to be not-present.
	 * 
	 * @param minutes Maximum miliseconds to wait
	 * @param loc The locator that we're waiting to be not-present anymore
	 */
	@QAFTestStep(description = "wait for {0} milisec for {1} to be not present")
	public static void waitForLocToBeNotPresent(int milisec, String loc) {

		try {
			logger.info("Waiting for " + milisec + " milis until " + loc + " is not present...");
			$(loc).waitForNotPresent(milisec);
			logger.info(loc + " is not present anymore!");
		} catch (Exception ex) {
			logger.info(loc + " didn't disappear after " + milisec + " milisecs...");
			ex.printStackTrace();
		}
	}

	/**
	 * Click the 'back' button in android until a certain locator is found on screen
	 * 
	 * Useful when we have to go back several pages
	 * 
	 * @param loc The locator that, when found, will stop the process from going back anymore
	 */
	@QAFTestStep(description = "go back until {0} is present")
	public static void goBackUntilIsPresent(String loc) {
		try {
			int maxIterations = 10;
			int timeout = 5000;
			for (int i=0; i < maxIterations; i++) {
				if ($(loc).isPresent()) {
					break;
				} else {
					logger.info("Element " + loc + " not found yet. Going back and waiting...");
					getDriver().navigate().back();
					//getDriver().wait(timeout);
					getDriver().manage().timeouts().implicitlyWait(timeout, TimeUnit.MILLISECONDS);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Scrolls the Android scroller down, starting from the current position, until
	 * a text containing the sent text is found
	 * 
	 * @param text The text that we're looking for (partial text is ok)
	 */
	@QAFTestStep(description = "scroll until the text containing {0} is on the screen")
	public static void scrollUntilTextContainingOnScreen(String text) {
		logger.info("Scrolling to find the text containing: " + text);
		boolean found = false;
		try {
			int maxAttempts = 10;
			int attempts = 0;
			while (attempts < maxAttempts) {
				try {
					String xpath = "(//*[contains(@text,'" + text + "')])[1]";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element.isDisplayed()) {
						found = true;
						break;
					}
				} catch (Exception x) {}
		
				attempts++;
				Thread.sleep(500);
				
				getDriver().findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true))" +
				".setSwipeDeadZonePercentage(.3).scrollForward(50)"));
			}
		} catch (Exception x) {
			x.printStackTrace();
			throw new AssertionError("Could not find element with text " + text);
		}

		Validator.assertTrue(found,
				"No element was found with the text " + text,
				"The element with text " + text + " was found");
	}

	/**
	 * Scrolls the Android scroller down, starting from the current position, until
	 * a text with the exact sent text is found
	 * 
	 * @param text The text that we're looking for (exact text needed)
	 */
	@QAFTestStep(description = "scroll until the text {0} is on the screen")
	public static void scrollUntilTextOnScreen(String text) {
		logger.info("Scrolling to find the text: " + text);
		boolean found = false;
		try {
			int maxAttempts = 20;
			int attempts = 0;
			while (attempts < maxAttempts) {
				try {
					String xpath = "(//*[@text='" + text + "'])[1]";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element.isDisplayed()) {
						found = true;
						break;
					}
				} catch (Exception x) {}
		
				attempts++;
				Thread.sleep(500);
				
				getDriver().findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true))" +
				".setSwipeDeadZonePercentage(.3).scrollForward(50)"));
			}
		} catch (Exception x) {
			x.printStackTrace();
			throw new AssertionError("Could not find element with text " + text);
		}

		Validator.assertTrue(found,
				"No element was found with the text " + text,
				"The element with text " + text + " was found");
	}

	/**
	 * Scroll the Android scroller to the end of the page, starting from the current position
	 * 
	 * There is a concept in scrolling call 'Deadzone', that basically creates an area all around
	 * the screen that will not be touched. Important for example to avoid the scrolling action
	 * to start from the very bottom of the screen, which sometimes clicks the 'Actions' button
	 * when it shouldn't
	 * 
	 * DeadZone: .3
	 * Max Swipes: 20
	 * 
	 */
	@QAFTestStep(description = "scroll to end")
	public static void scrollAndroidToEnd() {
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

	/**
	 * Enables the DEBUG option.
	 * When DEBUG is enabled, for each time a page is displayed on Android
	 * (or the page changes), the XML of the page is saved in a local
	 * directory (configurable in application.properties with the key
	 * 'mobile.helper.xml.directory').
	 * 
	 * This is important when automating existing applications and we don't
	 * know the structure, ids, etc of the page. That XML can help us find
	 * the correct xpaths that we need
	 */
	@QAFTestStep(description = "enable debug log")
	public static void enableDebugLog() {
		Util.DEBUG=true;
	}

	/**
	 * Get the android driver
	 * @return The AppiumDriver
	 */
	@SuppressWarnings({"rawtypes"})
	private static AppiumDriver getDriver() {
		return (AppiumDriver) new WebDriverTestBase().getDriver().getUnderLayingDriver();
	}

	/**
	 * In the current Android page, searches for a 'label' (any Text) containing
	 * the text sent in 'name'. Once found, it looks for the next TextView which
	 * has the text 'option', and from it, gets its first clickable parent.
	 * 
	 * Useful for forms for example that have a label/question and several options
	 * e.g. 'Do you want to continue?' with options Yes/No.
	 * 
	 * @param option The specific option to click/select in the 'form group'
	 * @param name The text to be searched for, representing the 'label' of the 'form group'
	 */
	@QAFTestStep(description = "select option {option} for form input with name {name}")
	public static void selectOptionForFormInput(String option, String name) {
		boolean success = false;
		try {
			int maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String xpath= "(" +
						"//android.widget.TextView[contains(@text, '" + name + "')]//following::android.widget.TextView[@text='" + option + "'][1]" +
						")" +
						"//ancestor::*[@clickable='true'][1]";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element.isDisplayed() && element.isEnabled()) {
						element.click();
						success = true;
						break;
					}
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not select option " + option + " for form input with name " + name,
			"Successfully selected option " + option + " for form input with name " + name);
	}

	/**
	 * Opens the timepicker component for a datepicker form group
	 * It first searches for a text view containing the text sent in 'name'
	 * (the label of the form group). When found, it looks for its next
	 * timepicker and clicks it to open it and select a time
	 * 
	 * @param name The text to be searched for, representing the 'label' of the 'form group'
	 */
	@QAFTestStep(description = "open timepicker for form input with name {name}")
	public static void openTimepickerForFormInput(String name) {
		boolean success = false;
		try {
			int maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					Thread.sleep(2000);
					String xpath= "//android.widget.TextView[contains(@text, '" + name + "')]//following::android.view.ViewGroup[@clickable='true'][3]";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element.isDisplayed() && element.isEnabled()) {
						element.click();
						success = true;
						break;
					}
				} catch (Exception x) {}
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not open timepicker for form input with name " + name,
			"Successfully opened timepicker for form input with name " + name);
	}

	/**
	 * Considering a datepicker popup is open, just select the current date
	 * by clicking 'Ok'
	 */
	@QAFTestStep(description = "select current selected date on datepicker")
	public static void selectCurrentSelectedDateOnDatePicker() {
		boolean success = false;
		try {
			int maxAttempts = 3;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					$("common.date.popup.ok").waitForPresent();
					$("common.date.popup.ok").waitForEnabled();
					$("common.date.popup.ok").click();
					$("common.date.popup.ok").waitForNotPresent();
					Thread.sleep(2000);
					success = true;
					break;
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not select current selected date on datepicker",
			"Successfully selected current date on datepicker");
	}
	
	/**
	 * Considering a timepicker is open (the radial/circular one),
	 * select a specific hour and minute
	 * 
	 * @param hour The hour to select
	 * @param minute needs to be either 0, 5, 10, 15... up to 55
	 */
	@QAFTestStep(description = "select {hour} {minute} on timepicker")
	public static void selectTimeOnTimepicker(String hour, String minute) {
		boolean success = false;
		try {
			int maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String xpath= "//android.widget.RadialTimePickerView.RadialPickerTouchHelper[@content-desc='" + hour + "']";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element.isDisplayed() && element.isEnabled()) {
						element.click();
						Thread.sleep(2000);
						//If minute is 05, it should be 5
						minute = minute.length() == 2 && minute.startsWith("0") ? minute.substring(1, 1) : minute;
						xpath= "//android.widget.RadialTimePickerView.RadialPickerTouchHelper[@content-desc='" + minute + "']";
						WebElement elementMinute = getDriver().findElementByXPath(xpath);
						if (elementMinute.isDisplayed() && elementMinute.isEnabled()) {
							elementMinute.click();
							success = true;
							break;
						}
					}
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not select " + hour + " " + minute + " on timepicker",
			"Successfully selected " + hour + " " + minute + " on timepicker");
	}

	/**
	 * Look for the form group which label contains the text 'name', and then click on it's slider/toggle
	 * 
	 * @param name The text to be searched for that represents the 'label' of the 'form group'
	 */
	@QAFTestStep(description = "toggle the switch for form input with name {name}")
	public static void toggleswitchForFormInput(String name) {
		boolean success = false;
		try {
			int maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String xpath= "(//android.widget.TextView[contains(@text, '" + name + "')]//following::android.widget.Switch)[1]";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element.isDisplayed() && element.isEnabled()) {
						element.click();
						success = true;
						break;
					}
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not toggle switch for form input with name " + name,
			"Successfully toggled switch for form input with name " + name);
	}

	/**
	 * In the current Android page, searches for a 'label' (any Text) containing
	 * the text sent in 'name'. Once found, it looks for the next TextView which
	 * contains the text 'option', and from it, gets its first clickable parent.
	 * 
	 * Useful for forms for example that have a label/question and several options
	 * e.g. 'Do you want to continue?' with options Yes/No.
	 * 
	 * @param option The text/partial text of the specific option to click/select in the 'form group'
	 * @param name The text to be searched for, representing the 'label' of the 'form group'
	 */
	@QAFTestStep(description = "select option that contains {option} for form input with name {name}")
	public static void selectOptionThatContainsForFormInput(String option, String name) {
		boolean success = false;
		try {
			int maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String xpath= "(" +
						"//android.widget.TextView[contains(@text, '" + name + "')]//following::android.widget.TextView[contains(@text,'" + option + "')][1]" +
						")" +
						"//ancestor::*[@clickable='true'][1]";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element.isDisplayed() && element.isEnabled()) {
						element.click();
						success = true;
						break;
					}
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not select option " + option + " for form input with name " + name,
			"Successfully selected option " + option + " for form input with name " + name);
	}

	/**
	 * Performs a 'pull to refresh' action several times (until the 'secs' have passed), doing
	 * it until a specific locator is found
	 * 
	 * It works e.g. when we create a new Service Appointment in web and go to mobile to search for it.
	 * When selecting the date, the SA does not appear, so we 'pull to refresh' or 'scroll refresh' until
	 * it appears
	 * 
	 * @param secs Maximum number of seconds to do this process until we fail if locator is not present
	 * @param loc Locator we're waiting for to appear
	 */
	@QAFTestStep(description = "scroll refresh for up to {secs} seconds until {locator} is present")
	@SuppressWarnings({"rawtypes"})
	public static void scrollRefreshForSecondsUntilPresent(int secs, String loc) {
		boolean success = false;
		try {
			int maxAttempts = secs/10; //we scroll each 10 secs
			int attempts = 0;
			Thread.sleep(1500);
			while (attempts < maxAttempts) {
				try {
					Dimension dims = getDriver().manage().window().getSize();
					//init start point = center of screen
					PointOption pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
					//scroll down (pull to refresh)
					PointOption pointOptionEnd = PointOption.point(dims.width / 2, (dims.height / 2) + (dims.height / 2));

					new TouchAction(getDriver())
						.press(pointOptionStart)
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
						.moveTo(pointOptionEnd)
						.release().perform();

					if ($(loc).isPresent() && $(loc).isDisplayed() && $(loc).isEnabled()) {
						success = true;
						break;
					}
				} catch (Exception x) {}
		
				attempts++;
				Thread.sleep(10000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.verifyTrue(success,
				"Could not find element " + loc + " after refreshing for " + secs + " seconds",
				"Element " + loc + " found successfully after refreshing");
	}

	/**
	 * Asserts that a TextView is present on the screen, with a specific text
	 * 
	 * @param text The specific text to be searched for
	 */
	@QAFTestStep(description = "assert android TextView is present with the text {text}")
	public static void assertAndroidTextViewPresentWithText(String text) {
		boolean success = false;
		try {
			int maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String xpath= "//android.widget.TextView[@text='" + text + "']";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element != null && element.isDisplayed() && element.isEnabled()) {
						success = true;
						break;
					}
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not find TextView with text " + text,
			text + " found successfully on screen");
	}

	/**
	 * Asserts that a TextView is present on the screen, containing the sent text
	 * 
	 * @param text The text/partial text to be searched for
	 */
	@QAFTestStep(description = "assert android TextView is present with the text contains {text}")
	public static void assertAndroidTextViewPresentWithTextContains(String text) {
		boolean success = false;
		try {
			int maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String xpath= "//android.widget.TextView[contains(@text,'" + text + "')]";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element != null && element.isDisplayed() && element.isEnabled()) {
						success = true;
						break;
					}
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not find TextView with text contains " + text,
			text + " found successfully on screen");
	}

	/**
	 * If open, hide the Android Keyboard
	 */
	@QAFTestStep(description = "hide the android keyboard")
	public static void hideAndroidKeyboard() {
		boolean success = false;
		try {
			getDriver().hideKeyboard();
			success = true;
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Keyboard could not be hidden",
			"Keyboard hidden successfully");
	}

	/**
	 * Click on the select field of a 'form group', which has a label that contains the text 'Name'
	 * 
	 * For example if there's a form similar to:
	 * 
	 * - Vegetation Type
	 * - | Select Vegetation |
	 * 
	 * If you call this function with 'Vegetation Type' as the name, it will click the
	 * 'Select Vegetation' section to open up the possible options to click later
	 * @param name The text that the 'form label' must have to look for the select box to click
	 */
	@QAFTestStep(description = "click on select button for form input with name {name}")
	public static void clickOnSelectInputForFormInput(String name) {
		boolean success = false;
		try {
			int maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String xpath= "(//android.widget.TextView[contains(@text, '" + name + "')]//ancestor::*[@clickable='true'])[1]";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element.isDisplayed() && element.isEnabled()) {
						element.click();
						success = true;
						break;
					}
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not click on select button for form input with name " + name,
			"Successfully clicked on select button for form input with name " + name);
	}

	/**
	 * Click on a generic Android View whose content-desc is equal to the one sent
	 * 
	 * @param contentDesc Content desc the component must have for it to be clicked
	 */
	@QAFTestStep(description = "click on android View with content desc {contentDesc}")
	public static void clickOnAndroidViewWithContentDesc(String contentDesc) {
		boolean success = false;
		try {
			int maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String xpath= "(//android.view.View[@content-desc='" + contentDesc + "'])[1]";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element.isDisplayed() && element.isEnabled()) {
						element.click();
						success = true;
						break;
					}
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not click on android View with content desc " + contentDesc,
			"Successfully clicked on android View with content desc " + contentDesc);
	}

	/**
	 * Opens the datepicker component of a datepicker form group
	 * It first searches for a text view containing the text sent in 'name'
	 * (the label of the form group). When found, it looks for its next
	 * datepicker and clicks it to open it and select a time
	 * 
	 * @param name The text to be searched for, representing the 'label' of the 'form group'
	 */
	@QAFTestStep(description = "open the date picker for form input with name {name}")
	public static void openDatepickerForFormInput(String name) {
		boolean success = false;
		try {
			int maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					String xpath = "//android.widget.TextView[contains(@text, '" + name + "')]" +
					    "//following::android.view.ViewGroup[@clickable='true'][1]";
					WebElement element = getDriver().findElementByXPath(xpath);
					if (element.isDisplayed() && element.isEnabled()) {
						element.click();
						success = true;
						break;
					}
				} catch (Exception x) {}
				Thread.sleep(2000);
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not open the datepickerfor form input with name " + name,
			"Successfully opened the datepicker for form input with name " + name);
	}

	/**
	 * Sends input information to a special 'form input group' found in several
	 * Salesforce forms. They can be identified because instead of a regular input text,
	 * the input has an icon to the right similar to a barcode.
	 * 
	 * Basically it clicks on the input, types the text, hides the keyboard, and clicks
	 * a little above the 'form label' to ensure the focus is lost in the input text,
	 * or else any future texts we type as part of the tests will be typed here instead
	 * of the correct fields
	 * 
	 * @param data Text to type in the input
	 * @param name Text/Partial text of the 'form label' to look for the form group
	 */
	@QAFTestStep(description = "sendKeys {data} into form input group with name {name}")
	public static void sendKeysIntoFormInputGroup(String data, String name) {
		boolean success = false;
		try {

			String xpathInputGroup = "(" +
				"//android.widget.TextView[contains(@text,'" + name + "')]//following::android.view.ViewGroup" +
			")[1]";

			WebElement inputGroup = waitAndReturnWhenPresentByXpath(xpathInputGroup);
			inputGroup.click();

			String xpathEditText = "//android.widget.TextView[contains(@text,'" + name + "')]" +
				"//following::android.widget.EditText[1]";
			WebElement editText = waitAndReturnWhenPresentByXpath(xpathEditText);
			editText.sendKeys(data);

			String xpathEditTextPrecedingVG = xpathEditText + "//preceding::android.view.ViewGroup[1]";
			WebElement precedingVG = waitAndReturnWhenPresentByXpath(xpathEditTextPrecedingVG);
			
			Dimension dims = getDriver().manage().window().getSize();
			PointOption pointOption = PointOption.point(precedingVG.getRect().getX(), precedingVG.getRect().getY());
			PointOption pointOption2 = PointOption.point(precedingVG.getRect().getX(), precedingVG.getRect().getY()-2);
			new TouchAction(getDriver())
				.tap(pointOption)
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(100)))
				.tap(pointOption2)
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(100)))
				.perform();
			
			hideAndroidKeyboard();
			success = true;
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not sendKeys " + data + " to into form input group with name " + name,
			"Successfuly sent sendKeys " + data + " to into form input group with name " + name);
	}

	/**
	 * Click on a TextView that has a clickable attribute of false.
	 * This happens for some reason when wanting to take pictures with SharinPix.
	 * We select that we want pictures, and a 'link' appears displaying something like
	 * 'Take pictures'. But the text is not clickable, so the test fails.
	 * 
	 * This bypasses that problem by using a direct tap on the location where the text
	 * appears, avoiding the error
	 * 
	 * @param text Text or partial text that the unclickable text/link has
	 */
	@SuppressWarnings({"rawtypes"})
	@QAFTestStep(description = "click on unclickable TextView with text {text}")
	public static void clickUnclickableTextView(String text) {
		boolean success = false;
		try {

			String xpath = "//android.widget.TextView[contains(@text,'" + text + "')]";

			WebElement element = waitAndReturnWhenPresentByXpath(xpath);
			PointOption pointOption = PointOption.point(element.getRect().getX(), element.getRect().getY());
			new TouchAction(getDriver())
				.tap(pointOption)
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(100)))
				.perform();
			success = true;
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"Could not click unclickable TextView with text: " + text,
			"Successfully clicked unclickable TextView with text: " + text);
	}

	/**
	 * Returns a webelement to be present, and when it is, it returns it.
	 * Waits for aproximately 20 seconds for it to appear, or else
	 * fails the test
	 * 
	 * @param xpath The xpath string to look for. Must have the xpath=//abc format
	 * @return The webelement when found
	 */
	public static WebElement waitAndReturnWhenPresentByXpath(String xpath) {
		int maxAttempts = 10;
		for (int attempt = 0; attempt < maxAttempts; attempt++) {
			try {Thread.sleep(2000);} catch (Exception x) {}
			try {
				WebElement element = getDriver().findElementByXPath(xpath);
				if (element.isDisplayed() && element.isEnabled()) {
					return element;
				}
			} catch (Exception x) {}
		}
		throw new AssertionError("Could not find the element with xpath: " + xpath);
	}
}