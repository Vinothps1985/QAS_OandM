package steps.shareable.assets;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

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

public class AssetSteps extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	@QAFTestStep(description = "create an asset with data {recordType} {assetType} {name} {account}")
	public void createAssetWithTypeNameAccount(String recordType, String assetType,  String name, String account) {
		
		ShrdLaunchApp launchApp = new ShrdLaunchApp();
		launchApp.customShrdLaunchApp("assets");

		$("assets.new.button").waitForPresent();
		$("assets.new.button").waitForEnabled();
		$("assets.new.button").click();

		String recordTypeXPath = "//div[contains(@class, 'Mode-normal') or contains(@class, 'Mode-maximized')]" +
			"//span[text()='" + recordType + "']//following::div[1]";

		int maxAttempts = 10;
		boolean found = false;
		for (int attempt = 0; attempt < maxAttempts; attempt++) {
			try {
				QAFExtendedWebElement recordTypeElement = new WebDriverTestBase().getDriver().findElementByXPath(recordTypeXPath);
				recordTypeElement.waitForPresent();
				recordTypeElement.waitForEnabled();
				recordTypeElement.click();
				found = true;
				break;
			} catch (Exception x) {}
			try {Thread.sleep(2000);} catch (Exception x) {}
		}
		if (!found) {
			throw new AssertionError("Could not find the record type with name " + recordType + " to click");
		}

		$("assets.new.popup.next.button").click();

		$("assets.new.popup.assetName.input").waitForPresent();
		$("assets.new.popup.assetName.input").waitForEnabled();
		$("assets.new.popup.assetName.input").sendKeys(name);

		$("assets.new.popup.assetType.input").click();

		steps.web.StepsLibrary.selectSalesforcePicklistOption(assetType);

		steps.common.StepsLibrary.scrollUntilVisible("assets.new.popup.account.input");

		$("assets.new.popup.account.input").waitForVisible();
		$("assets.new.popup.account.input").click();
		$("assets.new.popup.account.input").sendKeys(account);

		$("assets.new.popup.account.option.first").waitForEnabled();
		$("assets.new.popup.account.option.first").waitForVisible();
		$("assets.new.popup.account.option.first").click();
		$("assets.new.popup.account.option.first").waitForNotVisible();

		$("assets.new.popup.save.button").click();

		$("assets.details.assetName").waitForEnabled();
		$("assets.details.assetName").waitForVisible();
		$("assets.details.assetName").assertText(name);

		CommonStep.store(new WebDriverTestBase().getDriver(), "generatedAsset_URL");

	}

	@QAFTestStep(description = "create a work order for the current asset with data {workType} {dueDate} {subject} {description}")
	public void createWOForCurrentAssetWithData(String workType, String dueDate, String subject, String description) {

		$("assets.moreActions.button").waitForPresent();
		$("assets.moreActions.button").waitForEnabled();
		$("assets.moreActions.button").click();

		$("assets.truckRepairOrMaintWO.button").waitForEnabled();
		$("assets.truckRepairOrMaintWO.button").waitForVisible();
		$("assets.truckRepairOrMaintWO.button").click();

		$("assets.createWO.popup.workType.input").waitForEnabled();
		$("assets.createWO.popup.workType.input").waitForVisible();
		$("assets.createWO.popup.workType.input").sendKeys(workType);

		$("assets.createWO.popup.workType.option.first").waitForEnabled();
		$("assets.createWO.popup.workType.option.first").waitForVisible();
		$("assets.createWO.popup.workType.option.first").click();
		$("assets.createWO.popup.workType.option.first").waitForNotVisible();

		$("assets.createWO.popup.dueDate.input").sendKeys(dueDate);

		$("assets.createWO.popup.subject.input").sendKeys(subject);
		$("assets.createWO.popup.description.textarea").sendKeys(description);

		$("assets.createWO.popup.save.button").click();

		$("common.toastContainer").waitForVisible();

	}
	
}