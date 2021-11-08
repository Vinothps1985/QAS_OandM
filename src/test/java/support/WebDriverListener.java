package support;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.QAFListenerAdapter;
import com.qmetry.qaf.automation.ui.webdriver.CommandTracker;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebDriver;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebDriverCommandAdapter;
import com.qmetry.qaf.automation.util.PropertyUtil;
import com.qmetry.qaf.automation.step.QAFTestStep;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumDriver;

/**
 * com.basis.listener.ErrorOverrideListener.java
 *
 * @author shalin.s
 */

//public class WebDriverListener extends QAFWebDriverCommandAdapter {
  public class WebDriverListener extends QAFListenerAdapter {
	Log logger = LogFactory.getLog(getClass());

	@Override
	public void beforeCommand(QAFExtendedWebDriver driver, CommandTracker commandTracker) {

		super.beforeCommand(driver, commandTracker);
		//logger.info("BEFORE COMMAND! " + commandTracker.getCommand());

		String command = commandTracker.getCommand();
		if (command.equalsIgnoreCase(DriverCommand.GET_CURRENT_WINDOW_HANDLE)
				&& (ConfigurationManager.getBundle().getString("platform").equalsIgnoreCase("android")
						|| ConfigurationManager.getBundle().getString("platform").equalsIgnoreCase("ios")
						|| ConfigurationManager.getBundle().getString("platform").equalsIgnoreCase("windows"))) {
			commandTracker.setResponce(new Response());
		}

		if (ConfigurationManager.getBundle().getString("platform").equals("web")) {
			if (command.equalsIgnoreCase(DriverCommand.CLICK_ELEMENT)) {
				System.out.println("BEFORE CLICK!");
				// NO CLICK
				commandTracker.setResponce(new Response());
			}
		}
		
		if (Util.DEBUG) {
			String source = getDriver().getPageSource();
			System.out.println("SOURCEX");
			System.out.println(source);
		}
	}

	private String lastClick = "";

	public void waitForPageToLoad(WebDriver driver, String command) {
        try {

			if (command.equalsIgnoreCase(DriverCommand.SEND_KEYS_TO_ACTIVE_ELEMENT) ||
			command.equalsIgnoreCase(DriverCommand.SEND_KEYS_TO_ELEMENT) ||
			command.equalsIgnoreCase(DriverCommand.CLICK_ELEMENT)) {

				WebDriverWait wait1 = new WebDriverWait(driver, 30);
			
				//Page should have finished loading
				ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
								.equals("complete");
					}
				};
				
				//Salesforce objects should finish loading (in case $A is defined)
				ExpectedCondition<Boolean> aurascriptLoad = new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver driver) {
						String A_IS_UNDEFINED = "return typeof $A === 'undefined' || !$A";
						Boolean aIsUndefined = (Boolean) ((JavascriptExecutor) driver).executeScript((A_IS_UNDEFINED));

						//If $A is undefined (e.g. login screen) just continue
						if (aIsUndefined) {
							logger.info("$A is undefined");
							return true;
						}

						String NO_AURA_CONFIG = " return typeof $A.metricsService !== 'undefined' && " +
							" typeof $A.metricsService.getCurrentPageTransaction !== 'undefined' && " +
							" typeof $A.metricsService.getCurrentPageTransaction() === 'undefined' ";

						Boolean noAuraConfig = (Boolean) ((JavascriptExecutor) driver).executeScript((NO_AURA_CONFIG));
						//If no aura config, we may be inside an iframe. Ignore then...{}
						if (noAuraConfig) {
							logger.info("No aura config");
							return true;
						}

						/*String INSIDE_IFRAME = "return window === window.parent;";
						try {
							Boolean insideIframe = (Boolean) ((JavascriptExecutor) driver).executeScript((INSIDE_IFRAME));
							//If we're inside an iframe, we should not check the loading process
							if (insideIframe) {
								logger.info("Inside an iframe");
								return true;
							}
						} catch (Exception x) {
							logger.info("Exception. Inside Iframe.");
							return true;
						}*/

						//$A object is defined. This must be a salesforce page so we should wait
						//until the metricsService returns a number (only done when page finished loading)
						//all sections and tabs open
						//This prevents things like clicking the search assistant to type, only to have
						//it be closed automatically when some tabs continue loading...
						String WAIT_FOR_AURA_SCRIPT = "return (typeof $A !== 'undefined' && $A && " +
						" typeof $A.metricsService !== 'undefined' && typeof $A.metricsService.getCurrentPageTransaction !== 'undefined' && " +
						" $A.metricsService.getCurrentPageTransaction() != null && typeof $A.metricsService.getCurrentPageTransaction().config !== 'undefined' && " +
						" typeof $A.metricsService.getCurrentPageTransaction().config.context !== 'undefined' && " +
						" $A.metricsService.getCurrentPageTransaction().config.context.ept > 0)";
						//String EPT_COUNTER_SCRIPT = "return ($A.metricsService.getCurrentPageTransaction().config.context.ept)";
						Boolean result = (Boolean) ((JavascriptExecutor) driver).executeScript((WAIT_FOR_AURA_SCRIPT));

						return result;
						/*if (result) {
							logger.info("EPT on the current page is : " + ((JavascriptExecutor) driver).executeScript(EPT_COUNTER_SCRIPT));
							return true;
						} else {
							return false;
						}*/

					}
				};
				
				//logger.info("Will wait...");
				if (wait1.until(jsLoad) && wait1.until(aurascriptLoad)) {
					//logger.info("Page load complete");
				} else {
					Thread.sleep(1000);
				}
				
			}

			
        }

        catch (Exception e) {
            logger.error("Exception happened in waiting for page to load");
			logger.error("Exception is " + e.getMessage(), e);
            //Thread.sleep(1000);
        }
    }

	@Override
	public void beforeCommand(QAFExtendedWebElement element, CommandTracker commandTracker) {
		if (ConfigurationManager.getBundle().getString("platform").equals("web")) {
		    
			waitForPageToLoad(element.getWrappedDriver(), commandTracker.getCommand());
			if (commandTracker.getCommand().equalsIgnoreCase(DriverCommand.CLICK_ELEMENT)) {
				/*System.out.println("BEFORE CLICK!");
				try {
					//element.getWrappedDriver().
					Map<String, Object> params = commandTracker.getParameters();
					for (Map.Entry<String,Object> entry : params.entrySet()) {
						System.out.println("Key = " + entry.getKey() +
										", Value = " + entry.getValue());
						}
				} catch (Exception ex) {

				}*/
				
				// NO CLICK
				boolean success = false;
				for (int i=0; i < 3; i++) {
					try {
						success = true;
						//if (!element.isEnabled()) {
							//return;
						//}
						if (!element.getId().equals(lastClick)) {
							lastClick = element.getId();
							commandTracker.setResponce(new Response());
							System.out.println("Click intercepted (" + i + ")");
							element.click();
						}
						break;
					} catch (Exception ex) {
						success = false;
						System.out.println("Oops " + i + ": " + ex.getMessage());
						//Wait 2 seconds and try again
						//String alignToTop = i%2==0 ? "false" : "true";
						//if (!element.isEnabled()) {
							//return;
						//}
						//((JavascriptExecutor)element.getWrappedDriver()).executeScript("arguments[0].scrollIntoView(" + alignToTop + ");", element);
						((JavascriptExecutor)element.getWrappedDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
						//element.getWrappedDriver().executeScript("document.querySelector('div[data-message-id=\"loginAsSystemMessage\"]').style.display='none'");
						try {Thread.sleep(500);} catch (Exception x) {}
						lastClick = "";
					}
				}
				
				//Could not find. Attempt one last time with Javascript
				if (!success) {
					try {
						if (!element.isEnabled()) {
							return;
						}
						System.out.println("Oops final: Javascript");
						((JavascriptExecutor)element.getWrappedDriver()).executeScript("arguments[0].click();", element);
						Thread.sleep(5000);
					} catch (Exception ex) {
						System.out.println("Oops en catch final: " + ex.getMessage());
					}
				}
			}
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

		PropertyUtil prop = new PropertyUtil(
			System.getProperty("application.properties.file", "resources/application.properties"));
		String downloadsFolderBase = prop.getPropertyValue("downloads.folder.base");
		String userDataDir = prop.getPropertyValue("user.data.dir");
		String loginPath = prop.getPropertyValue("login.path");

		logger.info("Downloads folder base: " + downloadsFolderBase);
		logger.info("User data dir: " + userDataDir);
		logger.info("Login path: " + loginPath);

		Util.EMAIL_HOST = prop.getPropertyValue("email.host");
		Util.EMAIL_USERNAME = prop.getPropertyValue("email.username");
		Util.EMAIL_PASSWORD = prop.getPropertyValue("email.password");

		logger.info("Email host: " + Util.EMAIL_HOST);
		logger.info("Email username: " + Util.EMAIL_USERNAME);

		logger.info("support.WebDriverListener.beforeInitialize(): Configuring...");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd_HHmmss");
		Util.DOWNLOADS_FOLDER = downloadsFolderBase + sdf.format(new java.util.Date());
		File dir = new File(Util.DOWNLOADS_FOLDER);
		if (!dir.exists()) {
			dir.mkdir();
		}
		Util.LOGIN_PATH = loginPath != null ? loginPath : "https://bssi--fullcopy.lightning.force.com/";

		DesiredCapabilities capabilities = (DesiredCapabilities) desiredCapabilities;
        ChromeOptions chromeOptions = new ChromeOptions();

		// Arguments for testing Conga Composer (remove issues with cross-origin
		// iframes)
		chromeOptions.addArguments("--disable-web-security");
		chromeOptions.addArguments("--user-data-dir=" + userDataDir);
		chromeOptions.addArguments("--disable-site-isolation-trials");
		chromeOptions.addArguments("--disable-notifications");
		//chromeOptions.addArguments("--incognito");
		// chromeOptions.addArguments("--allow-running-insecure-content");
		// chromeOptions.addArguments("--allow-file-access-from-files");

		// Arguments for downloading and testing PDF contents
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", Util.DOWNLOADS_FOLDER);
		chromePrefs.put("download.prompt_for_download", false);
		chromePrefs.put("download.directory_upgrade", true);
		chromePrefs.put("plugins.always_open_pdf_externally", true);

		chromeOptions.setExperimentalOption("prefs", chromePrefs);

		/*
		 * chromeOptions.addArguments("--no-sandbox");//setExperimentalOption(
		 * "mobileEmulation", mobileEmulation);
		 * chromeOptions.addArguments("--disable-dev-shm-usage");
		 * chromeOptions.addArguments("--headless");
		 * chromeOptions.addArguments("--window-size=1920x1080");
		 */

		// chromeOptions.addArguments("--disable-features=IsolateOrigins");
		// chromeOptions.addArguments("--allow-running-insecure-content");
		// chromeOptions.addArguments("--force-fieldtrials=SiteIsolationExtensions/Control");
		// chromeOptions.addArguments("--ignore-certificate-errors");
		if (ConfigurationManager.getBundle().getString("platform").equals("web")) {
		    capabilities.setCapability("chromeOptions", chromeOptions);
		}

		super.beforeInitialize(desiredCapabilities);

	}

	@SuppressWarnings({"rawtypes"})
	private static AppiumDriver getDriver() {
		return (AppiumDriver) new WebDriverTestBase().getDriver().getUnderLayingDriver();
	}
}