package steps.android;

import org.openqa.selenium.Dimension;
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

// define android native application related steps here.
// You can create sub packages to organize the steps within different modules
public class StepsLibrary {
	
	static Log logger = LogFactory.getLog(StepsLibrary.class);
	
	/**
	 * @param data
	 *                 : data which is being passed from bdd
	 */
	@QAFTestStep(description = "sample step with {0}")
	public static void sampleStep(String data) {
	}

	/**
	 * @param data
	 *                 : data which is being passed from bdd
	 */
	@QAFTestStep(description = "print page information to log")
	public static void doSomething() {
		//logger.info("DO SOMETHING");
		if (Util.DEBUG) {
			System.out.println("================================");
			Set<String> contextNames = getDriver().getContextHandles();
			for (String context : contextNames) {
				System.out.println("Available context: " + context);
			}
			System.out.println("================================");

			System.out.println("");

			System.out.println("================================");
			
			//WebElement element = getDriver().findElementByXPath("//android.widget.ImageButton[@content-desc='More options']");
			//element.click();

			WebElement loginButton = getDriver().findElementByXPath("//android.widget.Button[@resource-id='Login']");
			loginButton.click();

			System.out.println("================================");
			String source = getDriver().getPageSource();
			System.out.println(source);
			System.out.println("================================");
		}
	}

	@QAFTestStep(description = "wait for {0} minutes for {1} to be not present")
	public static void waitForLocToBeNotPresentMinutes(int minutes, String loc) {
		logger.info("Waiting for " + minutes + " minutes until " + loc + " is not present...");
		StepsLibrary.waitForLocToBeNotPresent(minutes*60*1000, loc);
	}

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

	@QAFTestStep(description = "scroll until the text {0} is on the screen")
	public static void scrollThingy(String text) {
		logger.info("Scrolling to find the text: " + text);
		boolean found = false;
		try {
			int maxAttempts = 10;
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

	@QAFTestStep(description = "scroll until the text {0} is on the screen with deadzone of {deadzone}")
	public static void scrollThingyAgain(String text, String deadzone) {
		logger.info("Scrolling to find the text: " + text);
		try {
			getDriver().findElement(MobileBy.AndroidUIAutomator(
			"new UiScrollable(new UiSelector().scrollable(true))" +
			".setSwipeDeadZonePercentage(" + deadzone + ").setMaxSearchSwipes(20).scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"));
		} catch (Exception x) {
			//x.printStackTrace();
		}
	}

	/**
	 * Scroll to the end of the page
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

	@QAFTestStep(description = "fling scrollable {resourceId} to beginning")
	public static void scrollThingyBackwards(String resourceId) {
		try {
			getDriver().findElement(MobileBy.AndroidUIAutomator(
			"new UiScrollable(new UiSelector().resourceId(\"" + resourceId + "\"))" +
			".setSwipeDeadZonePercentage(.6).scrollBackward(500)"));
		} catch (Exception x) {
			//x.printStackTrace();
		}
	}

	@QAFTestStep(description = "scroll android scrollable {0} until element {1} is on the screen")
	public static void scrollThingy2(String scrollableResourceId, String elementResourceId) {
		try {
			getDriver().findElement(MobileBy.AndroidUIAutomator(
			"new UiScrollable(new UiSelector().resourceId(\"" + scrollableResourceId + "\"))" +
			".setSwipeDeadZonePercentage(.6).scrollIntoView(new UiSelector().resourceId(\"" + elementResourceId + "\"))"));
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	@QAFTestStep(description = "scroll android scrollable {0} until the end")
	public static void scrollThingy3(String scrollableResourceId) {
		try {
			getDriver().findElement(MobileBy.AndroidUIAutomator(
			"new UiScrollable(new UiSelector().resourceId(\"" + scrollableResourceId + "\"))" +
			".setSwipeDeadZonePercentage(.6).scrollToBeginning(10)")); //10=max swipes
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	@QAFTestStep(description = "enable debug log")
	public static void enableDebugLog() {
		Util.DEBUG=true;
	}

	@SuppressWarnings({"rawtypes"})
	private static AppiumDriver getDriver() {
		return (AppiumDriver) new WebDriverTestBase().getDriver().getUnderLayingDriver();
	}

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

	@QAFTestStep(description = "select option that contains {option} for form input with name {name}")
	public static void selectOptionThatContainsForFormInput(String option, String name) {
		boolean success = false;
		try {
			int maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					logger.info("****1");
					String xpath= "(" +
						"//android.widget.TextView[contains(@text, '" + name + "')]//following::android.widget.TextView[contains(@text,'" + option + "')][1]" +
						")" +
						"//ancestor::*[@clickable='true'][1]";
					WebElement element = getDriver().findElementByXPath(xpath);
					logger.info("****2");
					if (element.isDisplayed() && element.isEnabled()) {
						logger.info("****3");
						element.click();
						success = true;
						logger.info("****4");
						break;
					}
				} catch (Exception x) {logger.info("****5");}
				Thread.sleep(2000);
				logger.info("****6");
			}
		} catch (Exception x) {
			logger.info("****7");
			logger.error(x.getMessage(), x);
		}
		logger.info("****8");
		Validator.assertTrue(success,
			"Could not select option " + option + " for form input with name " + name,
			"Successfully selected option " + option + " for form input with name " + name);
	}

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
}
