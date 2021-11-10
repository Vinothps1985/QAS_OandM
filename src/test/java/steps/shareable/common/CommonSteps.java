package steps.shareable.common;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.core.ConfigurationManager;
import com.qmetry.qaf.automation.core.TestBaseProvider;
import com.qmetry.qaf.automation.step.QAFTestStep;
import com.qmetry.qaf.automation.ui.webdriver.QAFWebElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qmetry.qaf.automation.step.*;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import steps.common.StepsLibrary;
import steps.shareable.ShrdLogin;
import support.Util;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import java.util.Base64;

public class CommonSteps extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

    @QAFTestStep(description = "login to salesforce with {username} and {password}")
	public void loginToSalesforce(String username, String password) {
        ShrdLogin shrdLogin = new ShrdLogin();
        byte[] passwordBytes = Base64.getDecoder().decode(password);
        String realPassword = new String(passwordBytes);
        shrdLogin.customShrdLogin(username, realPassword);
	}
}