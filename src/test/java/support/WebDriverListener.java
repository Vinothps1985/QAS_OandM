package support;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.Response;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.ui.webdriver.CommandTracker;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebDriverCommandAdapter;

import com.qmetry.qaf.automation.step.QAFTestStep;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumDriver;

/**
 * com.basis.listener.ErrorOverrideListener.java
 *
 * @author
 * 		shalin.s
 */

public class WebDriverListener extends QAFWebDriverCommandAdapter {
	Log logger = LogFactory.getLog(getClass());

	@Override
	public void beforeCommand(QAFExtendedWebDriver driver,
			CommandTracker commandTracker) {

		super.beforeCommand(driver, commandTracker);
		//logger.info("BEFORE COMMAND!");

		String command = commandTracker.getCommand();
		if (command.equalsIgnoreCase(DriverCommand.GET_CURRENT_WINDOW_HANDLE)
				&& (ConfigurationManager.getBundle().getString("platform")
						.equalsIgnoreCase("android")
						|| ConfigurationManager.getBundle().getString("platform")
								.equalsIgnoreCase("ios")
						|| ConfigurationManager.getBundle().getString("platform")
								.equalsIgnoreCase("windows"))) {
			commandTracker.setResponce(new Response());
		}

		if (Util.DEBUG) {
			String source = getDriver().getPageSource();
			System.out.println("SOURCEX");
			System.out.println(source);
		}
	}

	@Override
	public void onFailure(QAFExtendedWebDriver driver, CommandTracker commandTracker) {
		super.onFailure(driver, commandTracker);
		commandTracker.getException().printStackTrace();
		commandTracker.setException(null);
	}

	@Override
	public void onInitialize(QAFExtendedWebDriver driver) {
		super.onInitialize(driver);
	}

	@Override
	public void beforeInitialize(Capabilities desiredCapabilities) {
		// if (ConfigurationManager.getBundle().getString("platform")
		// 		.equalsIgnoreCase("mobileweb")) {
		// 	DesiredCapabilities capabilities = (DesiredCapabilities) desiredCapabilities;
		// 	Map<String, String> mobileEmulation = new HashMap<>();

		// 	mobileEmulation.put("deviceName", "Nexus 5");

		// 	ChromeOptions chromeOptions = new ChromeOptions();
		// 	chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
		// 	capabilities.setCapability("chromeOptions", chromeOptions);
		// }

		/*DesiredCapabilities capabilities = (DesiredCapabilities) desiredCapabilities;
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");//setExperimentalOption("mobileEmulation", mobileEmulation);
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--window-size=1920x1080");
        capabilities.setCapability("chromeOptions", chromeOptions);*/

		super.beforeInitialize(desiredCapabilities);

	}

	@SuppressWarnings({"rawtypes"})
	private static AppiumDriver getDriver() {
		return (AppiumDriver) new WebDriverTestBase().getDriver().getUnderLayingDriver();
	}
}