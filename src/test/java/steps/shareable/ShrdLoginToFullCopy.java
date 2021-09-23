
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
public class ShrdLoginToFullCopy extends WebDriverTestCase {

	@QAFTestStep(description = "ShrdLoginToFullCopy {0} {1}")
	public void customShrdLoginToFullCopy(Object username,Object password) {
		
		getDriver().get("https://bssi--fullcopy.lightning.force.com/");
		StepsLibrary.maximizeWindow();
		CommonStep.clear("login.username");
		CommonStep.sendKeys(""+String.valueOf(username)+"","login.username");
		CommonStep.clear("login.password");
		CommonStep.sendKeys(""+String.valueOf(password)+"","login.password");
		$("login.submit").waitForEnabled();
		CommonStep.click("login.submit");
	}
}




