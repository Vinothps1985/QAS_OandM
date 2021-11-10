package steps.android;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import support.Util;

import com.qmetry.qaf.automation.step.CommonStep;
import com.qmetry.qaf.automation.step.QAFTestStep;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;

import com.qmetry.qaf.automation.ui.WebDriverTestBase;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;

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
		try {
			getDriver().findElement(MobileBy.AndroidUIAutomator(
			"new UiScrollable(new UiSelector().scrollable(true))" +
			".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"));
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
}
