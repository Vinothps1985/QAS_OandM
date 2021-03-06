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

		steps.web.StepsLibrary.selectSalesforcePicklistOptionforAssets(assetType);

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

	@QAFTestStep(description = "create a truck inspection for the current asset with data {workType} {dueDate} {subject} {description}")
	public void createTruckInspectionForCurrentAssetWithData() {

		$("assets.moreActions.button").waitForPresent();
		$("assets.moreActions.button").waitForEnabled();
		$("assets.moreActions.button").click();

		$("assets.newAdHocTruckInspection.button").waitForEnabled();
		$("assets.newAdHocTruckInspection.button").waitForVisible();
		$("assets.newAdHocTruckInspection.button").click();

		$("assets.newTruckInspection.popup.newAdHocTruckInspection.checkbox").waitForEnabled();
		$("assets.newTruckInspection.popup.newAdHocTruckInspection.checkbox").waitForVisible();
		$("assets.newTruckInspection.popup.newAdHocTruckInspection.checkbox").click();

		$("assets.newTruckInspection.popup.save.button").waitForEnabled();
		$("assets.newTruckInspection.popup.save.button").click();

		$("common.toastContainer").waitForVisible();

	}

	@QAFTestStep(description = "search for asset {assetName}")
	public void searchForAsset(String assetName) {

		//Search for the asset in the search bar
		$("common.searchAssistant.button").waitForEnabled();
		CommonStep.click("common.searchAssistant.button");
		CommonStep.sendKeys(""+String.valueOf(assetName)+"","common.searchAssistant.input");
		$("common.search.showMoreResults").waitForVisible();
		$("common.search.showMoreResults").waitForEnabled();
		$("common.search.showMoreResults").click();

		steps.web.StepsLibrary.waitForPageToFinishLoading();

		if ($("search.results.categories.assets").isPresent()) {
			$("search.results.categories.assets").click();
		} else {
			if ($("search.results.categories.expandList.button").isPresent() && $("search.results.categories.expandList.button").isEnabled()) {
				$("search.results.categories.expandList.button").click();
			}

			$("search.results.categories.showMore.button").waitForPresent();
			$("search.results.categories.showMore.button").waitForEnabled();
			$("search.results.categories.showMore.button").click();
			steps.web.StepsLibrary.waitForPageToFinishLoading();
			if ($("search.results.categories.assets").isPresent()) {
				$("search.results.categories.assets").click();
			}
		}

		$("search.results.panel.assets.title").waitForEnabled();
		$("search.results.panel.assets.title").waitForVisible();

		steps.web.StepsLibrary.waitForPageToFinishLoading();

		$("search.results.panel.assets.table.tr.first").waitForEnabled();
		$("search.results.panel.assets.table.tr.first.link.first").waitForEnabled();

		$("search.results.panel.assets.table.tr.first.link.first").assertText(assetName);
		$("search.results.panel.assets.table.tr.first.link.first").click();
		
		//Assert we're in the correct opportunity before continuing
		$("assets.details.assetName").waitForPresent();
		$("assets.details.assetName").assertText(String.valueOf(assetName));
	}

	@QAFTestStep(description = "search for asset {assetName} or create one with data {recordType} {assetType} {name} {account}")
	public void searchForAssetOrCreateOne(String assetName, String recordType, String assetType, String name, String account) {
		if (assetName != null && assetName.trim().length() > 0) {
			searchForAsset(assetName);
		} else {
			createAssetWithTypeNameAccount(recordType, assetType, name, account);
		}
	}
	
}