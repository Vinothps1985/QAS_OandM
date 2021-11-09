package steps.shareable;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.step.QAFTestStep;
import org.openqa.selenium.Keys;
import com.qmetry.qaf.automation.step.*;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import static com.qmetry.qaf.automation.core.ConfigurationManager.getBundle;
import com.qmetry.qaf.automation.util.Validator;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import steps.common.*;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;

/**
* @author Rodrigo Montemayor
*/
public class ShrdChangeLoggedInUser extends WebDriverTestCase{
	@QAFTestStep(description = "ShrdChangeLoggedInUser {0}")
	public void customShrdChangeLoggedInUser(Object userToSet) {
		
		//If already logged in as another user, log out from that user first
		if ($("common.logOutAs.link").isPresent() && $("common.logOutAs.link").isDisplayed() 
		    && $("common.logOutAs.link").isEnabled()) {

			$("common.logOutAs.link").click();

			steps.web.StepsLibrary.waitForPageToFinishLoading();
		}

		//Maybe in classic Mmode! Log out in that case
		if ($("classic.common.header.user.dropdown.link").isPresent() && $("classic.common.header.user.dropdown.link").isDisplayed()) {
			$("classic.common.header.user.dropdown.link").click();
			$("classic.common.logout.link").waitForVisible();
			$("classic.common.logout.link").click();
		}

		//Go first to the 'Cases' section to have the regular top search assistant
		//(Avoid having the top search from the 'Admin' section)
		ShrdLaunchApp launchApp = new ShrdLaunchApp();
		launchApp.customShrdLaunchApp("cases");

		int maxAttempts = 5;
		boolean success = true;
		for (int attempt = 0; attempt < maxAttempts; attempt++) {
			try {
				$("common.activeTab").waitForPresent();
				$("common.searchAssistant.button").waitForEnabled();
				$("common.searchAssistant.button").click();
				CommonStep.clear("common.searchAssistant.input");
				CommonStep.sendKeys(""+String.valueOf(userToSet)+"","common.searchAssistant.input");
				$("users.search.firstResult").waitForPresent();
				$("users.search.firstResult").waitForEnabled();
				$("users.search.firstResult").click();
				steps.web.StepsLibrary.waitForPageToFinishLoading();
				success = true;
				break;
			} catch (Exception x) {
				try { Thread.sleep(2000); } catch (Exception tx) {}
			}
		}
		
		if (!success) {
			throw new AssertionError("Could not change the logged in user to: " + userToSet + " after " + maxAttempts + " attempts");
		}
		
		if (!$("users.userDetails.button").isPresent()) {
			//Check to see if parent frame has it
			new WebDriverTestBase().getDriver().switchTo().defaultContent();
		}

		$("users.userDetails.button").waitForVisible();
		$("users.userDetails.button").waitForEnabled();	
		$("users.userDetails.button").click();
		new WebDriverTestBase().getDriver().switchTo().frame(new QAFExtendedWebElement("users.details.iframe"));
		$("users.userDetails.login.button").waitForEnabled();
		$("users.userDetails.login.button").click();
		new WebDriverTestBase().getDriver().switchTo().defaultContent();
		
		//This piece of code checks if the 'logged in as' section appears
		//and works both for lightning and classic mode
		boolean found = false;
		try {
			maxAttempts = 10;
			for (int attempt = 0; attempt < maxAttempts; attempt++) {
				if ($("common.logOutAs.link").isPresent() || $("classic.common.loggedInAs.span").isPresent()) {
					found = true;
					break;
				}
				Thread.sleep(2500);
			}
		} catch (Exception x) {
			x.printStackTrace();
		}

		Validator.assertTrue(found, 
			"Failed while waiting for the user to be logged in correctly as the user " + userToSet,
			"Logged in correctly as the user " + userToSet);		
	}
}




