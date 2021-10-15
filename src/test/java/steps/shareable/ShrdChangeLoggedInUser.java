
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
/**
* @author Rodrigo Montemayor
*/
public class ShrdChangeLoggedInUser extends WebDriverTestCase {

	@QAFTestStep(description = "ShrdChangeLoggedInUser {0}")
	public void customShrdChangeLoggedInUser(Object userToSet) {
		
		$("common.activeTab").waitForPresent();
		$("common.searchAssistant.button").waitForEnabled();
		CommonStep.click("common.searchAssistant.button");
		CommonStep.clear("common.searchAssistant.input");
		CommonStep.sendKeys(""+String.valueOf(userToSet)+"","common.searchAssistant.input");
		$("users.search.firstResult").waitForPresent();
		$("users.search.firstResult").waitForEnabled();
		CommonStep.click("users.search.firstResult");
		$("users.userDetails.button").waitForEnabled();
		CommonStep.click("users.userDetails.button");
		new WebDriverTestBase().getDriver().switchTo().frame(new QAFExtendedWebElement("users.details.iframe"));
		$("users.userDetails.login.button").waitForEnabled();
		CommonStep.click("users.userDetails.login.button");
		new WebDriverTestBase().getDriver().switchTo().defaultContent();
		$("common.logOutAs.link").waitForPresent();
	}
}




