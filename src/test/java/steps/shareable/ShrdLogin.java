
package steps.shareable;
import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.step.QAFTestStep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import support.Util;
/**
* @author Rodrigo Montemayor
*/
public class ShrdLogin extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	@QAFTestStep(description = "ShrdLogin {0} {1}")
	public void customShrdLogin(Object username, Object password) {
		
		logger.info("Logging in to PATH: " + Util.LOGIN_PATH);
		getDriver().get(Util.LOGIN_PATH);
		StepsLibrary.maximizeWindow();
		CommonStep.clear("login.username");
		CommonStep.sendKeys(""+String.valueOf(username)+"","login.username");
		CommonStep.clear("login.password");
		CommonStep.sendKeys(""+String.valueOf(password)+"","login.password");
		$("login.submit").waitForEnabled();
		CommonStep.click("login.submit");
	}
}




