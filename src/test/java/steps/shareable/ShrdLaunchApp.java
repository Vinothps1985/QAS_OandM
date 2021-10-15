
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
public class ShrdLaunchApp extends WebDriverTestCase {

	@QAFTestStep(description = "ShrdLaunchApp {0}")
	public void customShrdLaunchApp(Object appName) {
		
		$("common.activeTab").waitForPresent();
		$("applauncher.div").waitForPresent();
		$("applauncher.div").waitForEnabled();
		CommonStep.click("applauncher.div");
		$("applauncher.input.text").waitForPresent();
		CommonStep.clear("applauncher.input.text");
		CommonStep.sendKeys(""+String.valueOf(appName)+"","applauncher.input.text");
		$("applauncher.link.main").waitForPresent();
		$("applauncher.link.main").waitForEnabled();
		CommonStep.click("applauncher.link.main");
		$("common.activeTab").waitForPresent();
	}
}




