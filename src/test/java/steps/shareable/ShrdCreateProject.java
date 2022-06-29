
package steps.shareable;
import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.step.QAFTestStep;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.qmetry.qaf.automation.step.*;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;
import com.qmetry.qaf.automation.util.Validator;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import steps.common.*;
/**
* @author Rodrigo Montemayor
*/
public class ShrdCreateProject extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	/**
	 * Creates a new project in salesforce
	 * 
	 * Code currently creates a project for any OM opportunity (does not select a specific OM opportunity).
	 * 
	 * If correctly generated, the project name is saved on a variable called
	 * ${generated_projectName}
	 * 
	 * @param projectName The project name to create, or (empty|ANY) to create a random name
	 * @param subject
	 * @param caseDescription
	 * @param recordType
	 * @param casePriority
	 * @param caseOrigin
	 * @param reportedIssue
	 * @param caseCause
	 */
	@QAFTestStep(description = "ShrdCreateProject {opportunityName} {projectName} {epcSite} {watts}")
	public void customShrdCreateProject(String opportunityName, String projectName, String epcSite, String watts) {

		//Project name sent, or time in millis
		if (projectName == null || projectName.trim().length() == 0 || projectName.equalsIgnoreCase("ANY")) {
			projectName = String.valueOf(new Date().getTime());
		}

		ShrdLaunchApp launchApp = new ShrdLaunchApp();
		launchApp.customShrdLaunchApp("opportunities");
		
		//Select any OM opportunity
		if (opportunityName == null || opportunityName.equalsIgnoreCase("ANY")) {
			$("opportunities.selectListView").waitForPresent();
			$("opportunities.selectListView").waitForEnabled();
			CommonStep.click("opportunities.selectListView");
			$("opportunities.selectListView.allOpportunities").waitForPresent();
			$("opportunities.selectListView.allOpportunities").waitForEnabled();
			CommonStep.click("opportunities.selectListView.allOpportunities");
			$("opportunities.search.input").waitForPresent();
			CommonStep.clear("opportunities.search.input");
			try {Thread.sleep(3000);} catch (Exception e) {System.out.println(e);}
			CommonStep.sendKeys("O&M","opportunities.search.input");
			$("opportunities.search.input").waitForEnabled();
			$("opportunities.search.input").sendKeys(Keys.ENTER);
			try {Thread.sleep(3000);} catch (Exception e) {System.out.println(e);}
			$("opportunities.search.results.first").waitForPresent();
			$("opportunities.search.results.first").waitForEnabled();
			CommonStep.click("opportunities.search.results.first");
		} else {
			//Search for the opportuntiy in the search bar
			$("common.searchAssistant.button").waitForEnabled();
			CommonStep.click("common.searchAssistant.button");
			CommonStep.sendKeys(""+String.valueOf(opportunityName)+"","common.searchAssistant.input");
			$("common.search.showMoreResults").waitForVisible();
			$("common.search.showMoreResults").waitForEnabled();
			$("common.search.showMoreResults").click();

			steps.web.StepsLibrary.waitForPageToFinishLoading();

			if ($("search.results.categories.opportunities").isPresent()) {
				$("search.results.categories.opportunities").click();
			} else {
				if ($("search.results.categories.expandList.button").isPresent() && $("search.results.categories.expandList.button").isEnabled()) {
					$("search.results.categories.expandList.button").click();
				}

				$("search.results.categories.showMore.button").waitForPresent();
				$("search.results.categories.showMore.button").waitForEnabled();
				$("search.results.categories.showMore.button").click();
				steps.web.StepsLibrary.waitForPageToFinishLoading();
				if ($("search.results.categories.opportunities").isPresent()) {
					$("search.results.categories.opportunities").click();
				}
			}

			$("search.results.panel.opportunities.title").waitForEnabled();
			$("search.results.panel.opportunities.title").waitForVisible();

			steps.web.StepsLibrary.waitForPageToFinishLoading();

			$("search.results.panel.opportunities.table.tr.first").waitForEnabled();
			$("search.results.panel.opportunities.table.tr.first.link.first").waitForEnabled();

			$("search.results.panel.opportunities.table.tr.first.link.first").assertText(opportunityName);
			$("search.results.panel.opportunities.table.tr.first.link.first").click();
		}
		
		//Assert we're in the correct opportunity before continuing
		$("opportunities.opportunityName").waitForPresent();
		$("opportunities.opportunityName").assertText(String.valueOf(opportunityName));

		$("opportunities.createProject.button").waitForVisible();
		$("opportunities.createProject.button").waitForEnabled();
		CommonStep.click("opportunities.createProject.button");
		$("opportunities.createProject.iframe").waitForVisible();
		new WebDriverTestBase().getDriver().switchTo().frame(new QAFExtendedWebElement("opportunities.createProject.iframe"));

		$("opportunities.createProject.template.select").waitForPresent();
		$("opportunities.createProject.template.select.option.OM").waitForEnabled();
		CommonStep.click("opportunities.createProject.template.select.option.OM");
		$("opportunities.createProject.template.createProject.button").waitForEnabled();
		CommonStep.click("opportunities.createProject.template.createProject.button");

		new WebDriverTestBase().getDriver().switchTo().defaultContent();

		try {Thread.sleep(3000);} catch (Exception e) {System.out.println(e);}
		
		$("projects.editProject.button").waitForVisible();
		$("projects.editProject.button").waitForEnabled();
		CommonStep.click("projects.editProject.button");

		$("projects.editProject.popup.name.input").waitForVisible();
		$("projects.editProject.popup.name.input").waitForEnabled();
		CommonStep.clear("projects.editProject.popup.name.input");
		CommonStep.clear("projects.editProject.popup.name.input");
		CommonStep.sendKeys(""+String.valueOf(projectName)+"","projects.editProject.popup.name.input");

		if (epcSite != null && epcSite.trim().length() > 0) {
			CommonStep.clear("projects.editProject.popup.epcSite.input");
			CommonStep.sendKeys(""+String.valueOf(epcSite)+"","projects.editProject.popup.epcSite.input");
			$("projects.editProject.popup.epcSite.input").waitForEnabled();
			CommonStep.click("projects.editProject.popup.epcSite.input");
			$("projects.editProject.popup.epcSite.results.first").waitForPresent();
			$("projects.editProject.popup.epcSite.results.first").waitForEnabled();
			CommonStep.click("projects.editProject.popup.epcSite.results.first");
		}
		if (watts != null && watts.trim().length() > 0) {
			CommonStep.clear("projects.editProject.popup.watts.input");
			CommonStep.sendKeys(""+String.valueOf(watts)+"","projects.editProject.popup.watts.input");
		}

		$("projects.editProject.popup.available.firstOption.li").waitForEnabled();
		CommonStep.click("projects.editProject.popup.available.firstOption.li");
		$("projects.editProject.popup.available.moveToChosen.button").waitForEnabled();
		CommonStep.click("projects.editProject.popup.available.moveToChosen.button");
		$("projects.editProject.popup.available.firstOption.li").waitForEnabled();
		CommonStep.click("projects.editProject.popup.available.firstOption.li");
		$("projects.editProject.popup.available.moveToChosen.button").waitForEnabled();
		CommonStep.click("projects.editProject.popup.available.moveToChosen.button");
		$("projects.editProject.popup.save.button").waitForEnabled();
		CommonStep.click("projects.editProject.popup.save.button");

		$("common.toastContainer").waitForPresent();
		$("projects.details.projectName").waitForPresent();

		logger.info("Generated Project Name: " + projectName);
		CommonStep.store(projectName, "generated_projectName");
	}
}




