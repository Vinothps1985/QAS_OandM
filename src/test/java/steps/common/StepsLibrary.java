package steps.common;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;

import com.qmetry.qaf.automation.core.ConfigurationManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import steps.shareable.ShrdChangeLoggedInUser;
import steps.shareable.ShrdLaunchApp;

import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;
import com.qmetry.qaf.automation.step.CommonStep;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Keys;
import support.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.qmetry.qaf.automation.util.PropertyUtil;
import com.qmetry.qaf.automation.util.Reporter;
import com.qmetry.qaf.automation.util.Validator;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

/**
 * Steps Library for android, web or hybrid executions.
 * These steps are shared for all execution types so place them here if needed
 * 
 * Note: When running hybrid executions, even when running a 'web' section, they
 * MUST be here instead of the web.StepsLibrary class. That class is only for
 * web-only tests.
 */
public class StepsLibrary {

	private static Log logger = LogFactory.getLog(StepsLibrary.class);
	
	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	private static String getJsDndHelper() {
		return "function simulateDragDrop(sourceNode, destinationNode) {\r\n" +
				"		    var EVENT_TYPES = {\r\n" +
				"		        DRAG_END: 'dragend',\r\n" +
				"		        DRAG_START: 'dragstart',\r\n" +
				"		        DROP: 'drop'\r\n" +
				"		    }\r\n" +
				"		\r\n" +
				"		    function createCustomEvent(type) {\r\n" +
				"		        var event = new CustomEvent(\"CustomEvent\")\r\n" +
				"		        event.initCustomEvent(type, true, true, null)\r\n" +
				"		        event.dataTransfer = {\r\n" +
				"		            data: {\r\n" +
				"		            },\r\n" +
				"		            setData: function(type, val) {\r\n" +
				"		                this.data[type] = val\r\n" +
				"		            },\r\n" +
				"		            getData: function(type) {\r\n" +
				"		                return this.data[type]\r\n" +
				"		            }\r\n" +
				"		        }\r\n" +
				"		        return event\r\n" +
				"		    }\r\n" +
				"		\r\n" +
				"		    function dispatchEvent(node, type, event) {\r\n" +
				"		        if (node.dispatchEvent) {\r\n" +
				"		            return node.dispatchEvent(event)\r\n" +
				"		        }\r\n" +
				"		        if (node.fireEvent) {\r\n" +
				"		            return node.fireEvent(\"on\" + type, event)\r\n" +
				"		        }\r\n" +
				"		    }\r\n" +
				"		\r\n" +
				"		    var event = createCustomEvent(EVENT_TYPES.DRAG_START)\r\n" +
				"		    dispatchEvent(sourceNode, EVENT_TYPES.DRAG_START, event)\r\n" +
				"		\r\n" +
				"		    var dropEvent = createCustomEvent(EVENT_TYPES.DROP)\r\n" +
				"		    dropEvent.dataTransfer = event.dataTransfer\r\n" +
				"		    dispatchEvent(destinationNode, EVENT_TYPES.DROP, dropEvent)\r\n" +
				"		\r\n" +
				"		    var dragEndEvent = createCustomEvent(EVENT_TYPES.DRAG_END)\r\n" +
				"		    dragEndEvent.dataTransfer = event.dataTransfer\r\n" +
				"		    dispatchEvent(sourceNode, EVENT_TYPES.DRAG_END, dragEndEvent)\r\n" +
				"		}";
	}

	/**
	 * Send a numeric value into a locator
	 * @param number The number to send
	 * @param loc The locator to receive the number
	 */
	@QAFTestStep(description = "sendNumericKeys {number} into {loc}")
	public static void sendKeysInt(Integer number, String loc) {
		$(loc).sendKeys(number.toString());
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "store value of {0} into system as {1}")
	public static void storeValueToSystem(String localKey, String systemKey) {
		System.setProperty(systemKey, String.valueOf(ConfigurationManager.getBundle().getObject(localKey)));
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "store {0} into system as {1}")
	public static void storeToSystem(String localKey, String systemKey) {
		System.setProperty(systemKey, localKey);
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "get value from system {0} into {1}")
	public static void getValueFromSystem(String systemKey, String localKey) {
		ConfigurationManager.getBundle().setProperty(localKey, System.getProperty(systemKey));
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "switch to {0} platform")
	public static void switchToPlatform(String platform) {
		ConfigurationManager.getBundle().setProperty("env.resources", "resources/testdata;resources/" + platform);
	}

	/**
	 * Select a specific option in an HTML select field.
	 * 
	 * @param value The value to look for
	 * @param loc The locator of the select field
	 */
	@QAFTestStep(description="select {0} in {1}")
	public static void selectIn(String value,String loc) {
		WebElement sel = new WebDriverTestBase().getDriver().findElement(loc);
		Select selectDropDown = new Select(sel);
		try {
			selectDropDown.selectByValue(value.contains("=")?value.split("=")[1]:value);
		} catch (Exception e) {
			selectDropDown.selectByVisibleText(value.contains("=")?value.split("=")[1]:value);
		}
	}

	/**
	 * Send the ENTER key to a locator (e.g. input)
	 * 
	 * @param loc The locator to receive th entery key
	 */
	@QAFTestStep(description = "type Enter {loc}")
		public static void typeEnter(String loc) {
			$(loc).sendKeys(Keys.ENTER);
	}

	/**
	 * Close the driver
	 * @param loc
	 */
	@QAFTestStep(description = "close {loc}")
	public static void close(String loc) {
		new WebDriverTestBase().getDriver().close();
	}

	/**
	 * Switch to a window based on a 0-based index (e.g. for when a window pops up)
	 * 
	 * @param str0 The 0-index number of the window to focus
	 */
	@QAFTestStep(description = "switchWindow {0}")
	public static void switchWindow(String str0) {
		Set<String> windowHandles = new WebDriverTestBase().getDriver().getWindowHandles();
		List<String> windowStrings = new ArrayList<>(windowHandles);
		String reqWindow = windowStrings.get(Integer.parseInt(str0));
		new WebDriverTestBase().getDriver().switchTo().window(reqWindow);
	}

	/**
	 * Wait for a certain amount of miliseconds, doing nothing
	 * 
	 * @param time Time in milliseconds to wait
	 */
	@QAFTestStep(description = "wait for {0} milisec")
	public static void waitForMilliseconds(int time) {
		try {
			Thread.sleep(time);
			System.out.println("Execution waited for "+time+" ms");
	    } catch (Exception e) {
		System.out.println(" Exection occured on implicit wait : "+e);
        }
	}
	
	/**
	 * Wait for a certain amount of miliseconds, doing nothing
	 * 
	 * (Helper used for when Test Recorder has issues exporting the waiting command and
	 * includes an un-needed space before the 'wait' word)
	 * 
	 * @param time Time in milliseconds to wait
	 */
	@QAFTestStep(description = " wait for {0} milisec")
	public static void waitForMillisecondsSpace(int time) {
		waitForMilliseconds(time);
	}
	
	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "setBeforeLambdaTestCapabilities {data}")
	public static void setBeforeLambdaTestCapabilities(String data) {
		String jsonText = data;
		String cap = null;
		JSONParser parser = new JSONParser();
		JSONObject newJObject = new JSONObject();
		try {
			newJObject = (JSONObject) parser.parse(jsonText);
			cap = newJObject.get("cap").toString().replaceAll("\"", "'");
		} catch (Exception e) {
			e.printStackTrace();
		}
		ConfigurationManager.getBundle().setProperty("driver.name", "lambdaRemoteDriver");
		ConfigurationManager.getBundle().setProperty("remote.server", newJObject.get("remote.server"));
		ConfigurationManager.getBundle().setProperty("lambda.additional.capabilities", cap);

	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "setAfterLambdaTestCapabilities")
	public static void setAfterLambdaTestCapabilities() {
		PropertyUtil prop = new PropertyUtil(
		System.getProperty("application.properties.file", "resources/application.properties"));
		ConfigurationManager.getBundle().setProperty("driver.name", prop.getPropertyValue("driver.name"));
		ConfigurationManager.getBundle().setProperty("remote.server", prop.getPropertyValue("remote.server"));
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "drag {source} and drop on {target} perform")
	public static void dragAndDropPerform(String source, String target) {
		((JavascriptExecutor) new WebDriverTestBase().getDriver()).executeScript(getJsDndHelper() + "simulateDragDrop(arguments[0], arguments[1])", $(source),  $(target));
		QAFExtendedWebElement src = (QAFExtendedWebElement) $(source);
		Actions actions = new Actions(src.getWrappedDriver());
		actions.dragAndDrop(src, $(target)).perform();
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "drag {0} and drop on {1} and {2} perform")
	public static void dragAndDropOnAndPerform(String source, String Xtarget, String Ytarget) {
			QAFExtendedWebElement src = (QAFExtendedWebElement) $(source);
			Actions actions = new Actions(src.getWrappedDriver());
			actions.dragAndDropBy(src, Integer.parseInt(Xtarget), Integer.parseInt(Ytarget)).build().perform();
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "drag {0} and drop on value {1} perform")
	public static void dragAndDropOnValuePerform(String source, String value) {
		String executScriptValue ="arguments[0].setAttribute('value',"+Integer.parseInt(value)+");if(typeof(arguments[0].onchange) === 'function'){arguments[0].onchange('');}";
			((JavascriptExecutor) new WebDriverTestBase().getDriver()).executeScript(executScriptValue, $(source));
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "maximise window")
	public static void maximiseWindow() {
		new WebDriverTestBase().getDriver().manage().window().maximize();
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "verifyTitle {0}")
	public static void verifyTitle(String input) {
		Validator.verifyTrue(new WebDriverTestBase().getDriver().getTitle().equalsIgnoreCase(input),"Actual Title: \""+ new WebDriverTestBase().getDriver().getTitle() +"\" does not match with Expected: \"" +input+"\"" , "Actual Title: \""+ new WebDriverTestBase().getDriver().getTitle()+"\" matches with Expected: \"" +input+"\"");
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "assertTitle {0}")
	public static void assertTitle(String input) {
		Validator.assertTrue(new WebDriverTestBase().getDriver().getTitle().equalsIgnoreCase(input),"Actual Title: \""+ new WebDriverTestBase().getDriver().getTitle() +"\" does not match with Expected: \"" +input+"\"" , "Actual Title: \""+ new WebDriverTestBase().getDriver().getTitle()+"\" matches with Expected: \"" +input+"\"");
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "maximizeWindow")
	public static void maximizeWindow() {
		new WebDriverTestBase().getDriver().manage().window().maximize();
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "Execute Java Script with data {0}")
	public static void executeJavaScript(String dataScript) {
			new WebDriverTestBase().getDriver().executeScript(dataScript);;
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "Execute Async Java Script with data {0}")
	public static void executeAsyncJavaScript(String dataScript) {
			new WebDriverTestBase().getDriver().executeAsyncScript(dataScript);;
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "acceptAlert")
	public static void acceptAlert() {
		if (checkAlert(0)) {
			new WebDriverTestBase().getDriver().switchTo().alert().accept();
		}
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "dismissAlert")
	public static void dismissAlert() {
		if (checkAlert(0)) {
			new WebDriverTestBase().getDriver().switchTo().alert().dismiss();
		}
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "getAlertText {0}")
	public static void getAlertText(String input) {
		if (checkAlert(0)) {
			Alert alert= new WebDriverTestBase().getDriver().switchTo().alert();
			CommonStep.store(alert.getText(), input);
		}else{
			Validator.verifyFalse(true, "Alert is present.", "Alert is not present.");
		}
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "setAlertText {0}")
	public static void setAlertText(String input) {
		if (checkAlert(0)) {
			new WebDriverTestBase().getDriver().switchTo().alert().sendKeys(input);
		}
	}
	
	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "verifyAlertPresent {0} millisec")
	public static void verifyAlertPresent(String timeout) {
		if (!checkAlert(Long.valueOf(timeout))){
			Validator.verifyFalse(true, "Alert is not present.", "Alert is present.");
		}
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "verifyAlertNotPresent {0} millisec")
	public static void verifyAlertNotPresent(String timeout) {
		if (checkAlert(Long.valueOf(timeout))){
			Validator.verifyFalse(true, "Alert is present.", "Alert is not present.");
		}
	}

	/**
	 * Internal QAS step
	 * 
	 * @return
	 */
	@QAFTestStep(description = "waitForAlert {0} millisec")
	public static void waitForAlert(String timeout) {
		try{
		WebDriverWait wait = new WebDriverWait(new WebDriverTestBase().getDriver(), Long.valueOf(timeout));
		wait.until(ExpectedConditions.alertIsPresent());
		}catch(Exception e){
			System.out.println("Exception Occured during waitforAlert : "+e);
		}
	}

	/**
	 * Store the value from a specific locator inside a variable
	 * @param loc Locator to look the value in (e.g. an input)
	 * @param varName The variable to save the text
	 */
	@QAFTestStep(description = "store value from {0} into {1}")
	public static void storeValueIntoVariable(String loc , String varName) {
		CommonStep.store($(loc).getAttribute("value"), varName);
	}

	/**
	 * Store the text from a specific locator inside a variable
	 * @param loc The locator to look the text from
	 * @param varName The variable to save the text
	 */
	@QAFTestStep(description = "store text from {0} into {1}")
	public static void storeTextIntoVariable(String loc , String varName) {
		CommonStep.store(CommonStep.getText(loc), varName);
	}

	@QAFTestStep(description = "store title into {0}")
	public static void storeTitleIntoVariable(String varName){
			CommonStep.store(new WebDriverTestBase().getDriver().getTitle(), varName);
	}

	public static boolean checkAlert(long timeout) {
		boolean returnvalue = false;
		WebDriverWait wait = new WebDriverWait(new WebDriverTestBase().getDriver(), timeout);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			returnvalue = true;
		} catch (NoSuchElementException e) {
			returnvalue = false;
		} catch (TimeoutException te) {
			returnvalue = false;
		} catch(NoAlertPresentException ex){
			returnvalue = false;
		}catch (Exception ex) {
			returnvalue = false;
		}
		return returnvalue;
	}
	
	@QAFTestStep(description = "sendEncryptedKeys {text} into {loc}")
	public static void sendEncryptedKeys(String text, String loc) {
		byte[] decoded = Base64.getDecoder().decode(text);
		CommonStep.sendKeys(new String(decoded), loc);
	}

	@QAFTestStep(description = "click on {0} if it appears within {1} milisec")
	public static void clickElementIfAppearsWithin(String loc, int milisec) {
		try {
			$(loc).waitForEnabled(milisec);
			$(loc).click();
		} catch (Exception ex) {}
	}

	@QAFTestStep(description = "click on {0} if {1} appears within {2} milisec")
	public static void clickElementIfAnotherAppearsWithin(String clickLoc, String findLoc, int milisec) {
		try {
			$(findLoc).waitForPresent(milisec);
			if ($(findLoc).isEnabled()) {
				$(clickLoc).click();
			}
		} catch (Exception ex) {}
	}
	
	/**
	 * Takes a screenshot. It makes sure to wait for the page to finish loading if this is
	 * a web environment
	 */
	@QAFTestStep(description="take a screenshot")
	public static void takeAScreenshot() {
		if (ConfigurationManager.getBundle().getString("platform").equals("web") || "web".equals(Util.CURRENT_PLATFORM)) {
			steps.web.StepsLibrary.waitForPageToFinishLoading();
		}
		Reporter.logWithScreenShot("take a screenshot");
	}

	/**
	 * Change the current platform in the Util class so it can be checked
	 * in other places
	 * @param platform web/mobile
	 */
	@QAFTestStep(description="set current platform as {platform}")
	public static void setCurrentPlatformAs(String platform) {
		Util.CURRENT_PLATFORM = platform;
	}

	/**
	 * Stores the next business day (mon-fri) inside a variable with a specific format
	 * 
	 * @param varName Name of the variable to save the date into
	 * @param format The format the date will be saved in (see java SimpleDateFormat formats for options)
	 */
	@QAFTestStep(description="store next business day into {varName} in format {format}")
	public static void storeNextBusinessDayInto(String varName, String format) {
		boolean success = false;
		String value = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			
			Calendar c = Calendar.getInstance();
			//c.setTime(d);
			c.add(Calendar.DATE, 1);
			while (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				c.add(Calendar.DATE, 1);
			}

			value = sdf.format(c.getTime());

			CommonStep.store(value, varName);
			success = true;
		} catch (Exception x) {
			x.printStackTrace();
		}
		Validator.assertTrue(success,
			"Could not store next business day into " + varName + " with format " + format,
			"Next business day was stored in " + varName + " with value " + value);
	}

	/**
	 * Add a certain amount of business days to the current day (mon-fri) and save it into a variable
	 * in a specific format
	 * 
	 * @param daysToAdd Number of business days to add to the current date (e.g. 5 for a week)
	 * @param varName Name of the variable to save the date in
	 * @param format Format of the date. see SimpleDateFormat class for format options
	 */
	@QAFTestStep(description="add {daysToAdd} business days to current date and store it into {varName} in format {format}")
	public static void addBusinessDaysToCurrentDateAndStoreInto(long daysToAdd, String varName, String format) {
		boolean success = false;
		String value = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			
			Calendar c = Calendar.getInstance();
			long daysAdded = 0;
			while (daysAdded < daysToAdd) {
				c.add(Calendar.DATE, 1);
				//Skip saturdays and sundays
				while (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
					c.add(Calendar.DATE, 1);
				}
				daysAdded++;
			}

			value = sdf.format(c.getTime());

			CommonStep.store(value, varName);
			success = true;
		} catch (Exception x) {
			x.printStackTrace();
		}
		Validator.assertTrue(success,
			"Could not add " + daysToAdd + " business days to current date and store into " + varName + " with format " + format,
			daysToAdd + " business days added to current date and stored in " + varName + " with value " + value);
	}

	/**
	 * Take a date in one format, change it to another format and save it in a variable
	 * 
	 * @param originDate Variable name where the original date resides
	 * @param originFormat Format in which the original date is
	 * @param destFormat Format in which we want the date to be formatted to
	 * @param destVar Variable that will get the date in the destination format
	 */
	@QAFTestStep(description = "format date {originDate} from {originFormat} to {destFormat} into {destVar}")
	public static void formatDateFromVarToVarWithFormats(String originDate, String originFormat, String destFormat, String destVar) {
		boolean success = false;
		try {
			Date date = new java.text.SimpleDateFormat(originFormat, Locale.ENGLISH).parse(originDate);
			CommonStep.store(new java.text.SimpleDateFormat(destFormat, Locale.ENGLISH).format(date), destVar);
			success = true;
		} catch (Exception x) {}

		Validator.assertTrue(success,
			"Could not format date " + originDate + " with format " + originFormat + " to convert to format " + destFormat,
			"Date " + originDate + " with format " + originFormat + " converted successfully to format " + destFormat);
	}

	/**
	 * Defaults to 15 scrolls. Use "for up to {maxScrolls} scrolls" to specify max scroll number
	 * @param locator
	 */
	@QAFTestStep(description="scroll until {0} is visible")
	public static void scrollUntilVisible(String locator) {
		scrollUntilVisibleWithMaxScrolls(locator, 15);
	}

	/**
	 * Scrolls the web or Android page until a locator exists and is visible.
	 * 
	 * In the case of web, it starts scrolling from the top of the page always. In 
	 * the case of android it 'swipes' from the current position only.
	 * 
	 * @param locator the locator that must be present
	 * @param maxScrolls the maximum number of scrolls to try to find the element
	 */
	@SuppressWarnings({"rawtypes"})
	@QAFTestStep(description="scroll until {0} is visible for up to {maxScrolls} scrolls")
	public static void scrollUntilVisibleWithMaxScrolls(String locator, long maxScrolls) {
		try {
			if (ConfigurationManager.getBundle().getString("platform").equals("web") || "web".equals(Util.CURRENT_PLATFORM)) {
				if ($(locator).isPresent()) {
					((JavascriptExecutor)new WebDriverTestBase().getDriver())
								.executeScript("arguments[0].scrollIntoView({block: 'center'});", $(locator));
				} else {					
					int scrolls = 0;
					while (scrolls <= maxScrolls) {
						StringBuilder javascript = new StringBuilder();
						javascript.append("var automation_scrollingElement = (document.scrollingElement || document.body);");
						javascript.append("var automation_scrollHeight = automation_scrollingElement.scrollHeight;");
						javascript.append("automation_scrollingElement.scrollTop = (300*"+scrolls + ");");
						((JavascriptExecutor)new WebDriverTestBase().getDriver()).executeScript(javascript.toString());
						Thread.sleep(500);
						if ($(locator).isPresent()) {
							((JavascriptExecutor)new WebDriverTestBase().getDriver())
										.executeScript("arguments[0].scrollIntoView({block: 'center'});", $(locator));
							break;
						}
						scrolls++;
					}
				}
				Thread.sleep(500);
			} else {
				//Mobile!
				int scrolls = 0;
				boolean found = false;
				while (scrolls < maxScrolls) {
					AppiumDriver driver = (AppiumDriver) new WebDriverTestBase().getDriver().getUnderLayingDriver();

					try {
						if ($(locator) != null && $(locator).isPresent() && $(locator).isEnabled()) {
							found = true;
							break;
						}
					} catch (Exception x) {}

					//Not found. Scroll
					driver.findElement(MobileBy.AndroidUIAutomator(
					"new UiScrollable(new UiSelector().scrollable(true))" +
					".setSwipeDeadZonePercentage(.3).scrollForward(50)"));
			
					scrolls++;
				}

				Validator.assertTrue(found,
				    "Element with locator " + locator + " was not found while scrolling",
				    "Element with locator " + locator + " found while scrolling");
			}
		} catch (Exception x) {
			//?
			x.printStackTrace();
		}
	}

	/**
	 * Create a random number with a certain amount of digits, saving it in a variable.
	 * 
	 * @param digits Number of digits the random number must have
	 * @param varName Variable to save the generated random number into 
	 */
	@QAFTestStep(description = "create a random number with {digits} digits and store it in {varName}")
	public static void createRandomNumber(int digits, String varName) {

		int num = (int) Math.pow(10, digits - 1);
		String randomNumber = String.valueOf(num + new Random().nextInt(9 * num));
	
		CommonStep.store(randomNumber, varName);
	}

	/**
	 * Store the URL of the current frame into a variable
	 * 
	 * @param variableName Variable name that will hold the URL
	 */
	@QAFTestStep(description="store the current url in {variableName}")
	public static void saveTheCurrentUrl(String variableName) {
		String url = new WebDriverTestBase().getDriver().getCurrentUrl();
		//logger.info("Storing URL " + url + " in variable named " + variableName);
		CommonStep.store(url, variableName);
	}

	/**
	 * Salesforce-specific function to close all open web tasks, one by one
	 */
	@QAFTestStep(description="close all open web tabs")
	public static void closeAllOpenWebTabs() {
		boolean success = false;
		try {
			int maxAttempts = 50;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				try {
					steps.web.StepsLibrary.waitForPageToFinishLoading();

					//Close all top tabs, if any exist
					String closeButtonXPath = "//div[contains(@class, 'tabsetHeader ')]" +
						"//button[contains(@class, 'slds-button_icon-x-small') and contains(@title, 'Close')]";
						
					QAFExtendedWebElement element = null;
					try {
						element = new WebDriverTestBase().getDriver().findElementByXPath(closeButtonXPath);
					} catch (Exception x) {
						success = true; //Not found anymore :)
						break;
					}

					if (element != null && element.isPresent() && element.isEnabled()) {
						element.click();
					} else {
						success = true;
						break;
					}
					Thread.sleep(500);
				} catch (Exception x) {}
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.assertTrue(success,
			"Could not close all open web tabs",
			"Open web tabs have been closed");
	}

	/**
	 * Launch a salesforce-lightning app using the AppLauncher
	 * 
	 * @param appName The name of the app to launch (e.g. Cases, Projects, Field Service)
	 */
	@QAFTestStep(description = "launch salesforce app {0}")
	public void launchSalesforceApp(Object appName) {
		ShrdLaunchApp launch = new ShrdLaunchApp();
		launch.customShrdLaunchApp(appName);
	}

	/**
	 * Change the current logged in user. This depends on the logged-in user to have the
	 * permissions to search for a user and impersonate it.
	 * 
	 * @param userToSet The user we want to change to by using impersonation
	 */
	@QAFTestStep(description = "change logged in user to {0}")
	public void changeLoggedInUserTo(Object userToSet) {
		ShrdChangeLoggedInUser change = new ShrdChangeLoggedInUser();
		change.customShrdChangeLoggedInUser(userToSet);
	}

	/**
	 * Wait for a locator to be present, with a minimum/maximum amount of seconds to wait for
	 * This is relevant in cases like FieldService that usually takes several seconds to load, so
	 * we wait a bit first, and then start looking for it constantly
	 * 
	 * @param loc The locator to search for
	 * @param max Maximum number of seconds to wait for
	 * @param min Minimum number of seconds to wait for
	 */
	@QAFTestStep(description = "wait until {loc} for a max of {max} and min of {min} seconds to be present")
	public static void waitForPresentFoxMaxMinSeconds(String loc, long max, long min) {
		try {
			Thread.sleep(min*1000);
			$(loc).waitForPresent((max-min) * 1000);
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
	}

	/**
	 * Extract the date component from a String, and save it into a variable.
	 * It basically splits the string by its first space, and takes the first element
	 * 
	 * e.g. 2021-12-30 15:30:25 = 2021-12-30
	 * 
	 * @param data The string to take only the first part (date) from
	 * @param varName The variable to save the date into
	 */
	@QAFTestStep(description = "extract the date component from {data} into {varName}")
	public static void extractDateComponent(String data, String varName) {
		String result = null;
		try {
			String[] components = data.split(" ");
			result = components[0].trim();
			CommonStep.store(result, varName);
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.assertTrue(result != null && result.length() > 0,
			"Could not extract the date component from " + data + " into " + varName,
			"Successfully extracted the date component from " + data + " into " + varName + ": " + result);
	}

	/**
	 * Assert that a locator contains a certain text
	 * 
	 * @param loc Locator to look for
	 * @param text Text that must be contained inside the locator's text
	 */
	@QAFTestStep(description = "assert {loc} contains the text {text}")
	public static void assertLocContainsText(String loc, String text) {
		$(loc).waitForEnabled();
		String locText = $(loc).getText();
		boolean contained = locText.contains(text);
		Validator.assertTrue(contained,
			"The text " + text + " was not contained in the text " + locText + " as expected",
			"The text " + text + " was found in the text " + locText + " as expected");
	}

	/**
	 * Assert that a locator has a numeric value that matches the sent text.
	 * This is important in cases where these are the same:
	 * - 2
	 * - 2.00
	 * - $2.00
	 * - $2.0000
	 * 
	 * @param loc The locator to look for
	 * @param text The text that must appear 'numerically' inside the locator
	 */
	@QAFTestStep(description = "assert {loc} numeric value matches {text}")
	public static void assertLocNumericValueMatches(String loc, String text) {
		$(loc).waitForEnabled();
		String locText = $(loc).getText();
		boolean success = false;
		try {
			locText = locText.replace("$", "").replace(",", "");
			double dLocText = Double.parseDouble(locText);
			text = text.replace("$", "").replace(",", "");
			double dText = Double.parseDouble(text);
			if (dLocText == dText) {
				success = true;
			}
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}
		Validator.assertTrue(success,
			"The numeric value of " + text + " does not match " + locText + " as expected",
			"The numeric value of " + text + " matches " + locText + " as expected");
	}

	/**
	 * Count the number of rows a table has, and save it in a variable
	 * 
	 * @param varName The variable name to save the number of rows into
	 */
	@QAFTestStep(description = "store number of rows from table into {varName}")
	public static void storeNumberOfRowsFromTableInto(String varName) {
		boolean success = false;
		int numRows = 0;
		try {
			String xpath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]//table//tbody//tr";
			
			List lstTRs = new WebDriverTestBase().getDriver().findElements(By.xpath(xpath));
			if (lstTRs != null && lstTRs.size() > 0) {
				numRows = lstTRs.size();
			}
			CommonStep.store(numRows, varName);
			success = true;
		} catch (Exception x) {
			logger.error(x.getMessage(), x);
		}

		Validator.assertTrue(success,
			"Could not store the number of rows from table into " + varName,
			"Stored the number of rows " + numRows + " from table into " + varName);
	}

	/**
	 * Store the current date in a specific format into a variable
	 * 
	 * @param format The format to be saved (for formats look for java SimpleDateFormat documentation)
	 * @param varName The variable where the formatted date will be saved to
	 */
	@QAFTestStep(description = "store the current date in format {format} into {varName}")
	public static void storeCurrentDateInFormatInto(String format, String varName) {
		boolean success = false;
		try {
			Date date = new Date();
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format, Locale.ENGLISH);
			String result = sdf.format(date);
			CommonStep.store(result, varName);
			success = true;
		} catch (Exception x) {}

		Validator.assertTrue(success,
			"Could not format current date into format " + format + " to store it into " + varName,
			"Stored the date in format " + format + " into " + varName + " successfully");
	}
}