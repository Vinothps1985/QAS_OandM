package steps.common;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;

import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;
import com.qmetry.qaf.automation.step.CommonStep;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Keys;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.qmetry.qaf.automation.util.PropertyUtil;
import com.qmetry.qaf.automation.util.Validator;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import java.util.NoSuchElementException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Base64;
// define common steps among all the platforms.
// You can create sub packages to organize the steps within different modules
public class StepsLibrary {
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
	 * @param data : data which is being passed from bdd
	 */
	@QAFTestStep(description = "sample step with {0}")
	public static void sampleStep(String data) {
	}

	@QAFTestStep(description = "sendNumericKeys {number} into {loc}")
	public static void sendKeysInt(Integer number, String loc) {
		$(loc).sendKeys(number.toString());
	}

	@QAFTestStep(description = "store value of {0} into system as {1}")
	public static void storeValueToSystem(String localKey, String systemKey) {
		System.setProperty(systemKey, String.valueOf(ConfigurationManager.getBundle().getObject(localKey)));
	}

	@QAFTestStep(description = "store {0} into system as {1}")
	public static void storeToSystem(String localKey, String systemKey) {
		System.setProperty(systemKey, localKey);
	}

	@QAFTestStep(description = "get value from system {0} into {1}")
	public static void getValueFromSystem(String systemKey, String localKey) {
		ConfigurationManager.getBundle().setProperty(localKey, System.getProperty(systemKey));
	}

	@QAFTestStep(description = "switch to {0} platform")
	public static void switchToPlatform(String platform) {
		ConfigurationManager.getBundle().setProperty("env.resources", "resources/testdata;resources/" + platform);
	}
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
	@QAFTestStep(description = "type Enter {loc}")
		public static void typeEnter(String loc) {
			$(loc).sendKeys(Keys.ENTER);
	}

	@QAFTestStep(description = "close {loc}")
	public static void close(String loc) {
		new WebDriverTestBase().getDriver().close();
	}

	@QAFTestStep(description = "switchWindow {0}")
	public static void switchWindow(String str0) {
		Set<String> windowHandles = new WebDriverTestBase().getDriver().getWindowHandles();
		List<String> windowStrings = new ArrayList<>(windowHandles);
		String reqWindow = windowStrings.get(Integer.parseInt(str0));
		new WebDriverTestBase().getDriver().switchTo().window(reqWindow);
	}

	@QAFTestStep(description = "wait for {0} milisec")
	public static void waitForMilliseconds(int time) {
		try {
			Thread.sleep(time);
			System.out.println("Execution waited for "+time+" ms");
	    } catch (Exception e) {
		System.out.println(" Exection occured on implicit wait : "+e);
        }
	}
	
	@QAFTestStep(description = " wait for {0} milisec")
	public static void waitForMillisecondsSpace(int time) {
		waitForMilliseconds(time);
    }
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

	@QAFTestStep(description = "setAfterLambdaTestCapabilities")
	public static void setAfterLambdaTestCapabilities() {
		PropertyUtil prop = new PropertyUtil(
		System.getProperty("application.properties.file", "resources/application.properties"));
		ConfigurationManager.getBundle().setProperty("driver.name", prop.getPropertyValue("driver.name"));
		ConfigurationManager.getBundle().setProperty("remote.server", prop.getPropertyValue("remote.server"));
	}

	@QAFTestStep(description = "drag {source} and drop on {target} perform")
	public static void dragAndDropPerform(String source, String target) {
		((JavascriptExecutor) new WebDriverTestBase().getDriver()).executeScript(getJsDndHelper() + "simulateDragDrop(arguments[0], arguments[1])", $(source),  $(target));
		QAFExtendedWebElement src = (QAFExtendedWebElement) $(source);
		Actions actions = new Actions(src.getWrappedDriver());
		actions.dragAndDrop(src, $(target)).perform();
	}

	@QAFTestStep(description = "drag {0} and drop on {1} and {2} perform")
	public static void dragAndDropOnAndPerform(String source, String Xtarget, String Ytarget) {
			QAFExtendedWebElement src = (QAFExtendedWebElement) $(source);
			Actions actions = new Actions(src.getWrappedDriver());
			actions.dragAndDropBy(src, Integer.parseInt(Xtarget), Integer.parseInt(Ytarget)).build().perform();
	}

	@QAFTestStep(description = "drag {0} and drop on value {1} perform")
	public static void dragAndDropOnValuePerform(String source, String value) {
		String executScriptValue ="arguments[0].setAttribute('value',"+Integer.parseInt(value)+");if(typeof(arguments[0].onchange) === 'function'){arguments[0].onchange('');}";
			((JavascriptExecutor) new WebDriverTestBase().getDriver()).executeScript(executScriptValue, $(source));
	}

	@QAFTestStep(description = "maximise window")
	public static void maximiseWindow() {
		new WebDriverTestBase().getDriver().manage().window().maximize();
	}

	@QAFTestStep(description = "verifyTitle {0}")
	public static void verifyTitle(String input) {
		Validator.verifyTrue(new WebDriverTestBase().getDriver().getTitle().equalsIgnoreCase(input),"Actual Title: \""+ new WebDriverTestBase().getDriver().getTitle() +"\" does not match with Expected: \"" +input+"\"" , "Actual Title: \""+ new WebDriverTestBase().getDriver().getTitle()+"\" matches with Expected: \"" +input+"\"");
	}

	@QAFTestStep(description = "assertTitle {0}")
	public static void assertTitle(String input) {
		Validator.assertTrue(new WebDriverTestBase().getDriver().getTitle().equalsIgnoreCase(input),"Actual Title: \""+ new WebDriverTestBase().getDriver().getTitle() +"\" does not match with Expected: \"" +input+"\"" , "Actual Title: \""+ new WebDriverTestBase().getDriver().getTitle()+"\" matches with Expected: \"" +input+"\"");
	}

	@QAFTestStep(description = "maximizeWindow")
	public static void maximizeWindow() {
		new WebDriverTestBase().getDriver().manage().window().maximize();
	}

	@QAFTestStep(description = "Execute Java Script with data {0}")
	public static void executeJavaScript(String dataScript) {
			new WebDriverTestBase().getDriver().executeScript(dataScript);;
	}

	@QAFTestStep(description = "Execute Async Java Script with data {0}")
	public static void executeAsyncJavaScript(String dataScript) {
			new WebDriverTestBase().getDriver().executeAsyncScript(dataScript);;
	}

	@QAFTestStep(description = "acceptAlert")
	public static void acceptAlert() {
		if (checkAlert(0)) {
			new WebDriverTestBase().getDriver().switchTo().alert().accept();
		}
	}

	@QAFTestStep(description = "dismissAlert")
	public static void dismissAlert() {
		if (checkAlert(0)) {
			new WebDriverTestBase().getDriver().switchTo().alert().dismiss();
		}
	}

	@QAFTestStep(description = "getAlertText {0}")
	public static void getAlertText(String input) {
		if (checkAlert(0)) {
			Alert alert= new WebDriverTestBase().getDriver().switchTo().alert();
			CommonStep.store(alert.getText(), input);
		}else{
			Validator.verifyFalse(true, "Alert is present.", "Alert is not present.");
		}
	}

	@QAFTestStep(description = "setAlertText {0}")
	public static void setAlertText(String input) {
		if (checkAlert(0)) {
			new WebDriverTestBase().getDriver().switchTo().alert().sendKeys(input);
		}
	}
	@QAFTestStep(description = "verifyAlertPresent {0} millisec")
	public static void verifyAlertPresent(String timeout) {
		if (!checkAlert(Long.valueOf(timeout))){
			Validator.verifyFalse(true, "Alert is not present.", "Alert is present.");
		}
	}
	@QAFTestStep(description = "verifyAlertNotPresent {0} millisec")
	public static void verifyAlertNotPresent(String timeout) {
		if (checkAlert(Long.valueOf(timeout))){
			Validator.verifyFalse(true, "Alert is present.", "Alert is not present.");
		}
	}

	@QAFTestStep(description = "waitForAlert {0} millisec")
	public static void waitForAlert(String timeout) {
		try{
		WebDriverWait wait = new WebDriverWait(new WebDriverTestBase().getDriver(), Long.valueOf(timeout));
		wait.until(ExpectedConditions.alertIsPresent());
		}catch(Exception e){
			System.out.println("Exception Occured during waitforAlert : "+e);
		}
	}

	@QAFTestStep(description = "store value from {0} into {1}")
	public static void storeValueIntoVariable(String loc , String varName) {
		CommonStep.store($(loc).getAttribute("value"), varName);
	}

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

}
