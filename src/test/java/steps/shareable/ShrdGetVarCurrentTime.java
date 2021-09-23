
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
public class ShrdGetVarCurrentTime extends WebDriverTestCase {

	@QAFTestStep(description = "ShrdGetVarCurrentTime")
	public void customShrdGetVarCurrentTime() {
		
		StepsLibrary.executeJavaScript("var value = new Date().getTime();var elem = document.createElement('div');elem.id='the-time';elem.innerHTML=value;document.body.insertAdjacentElement('beforeend', elem);/*fix shrd export*/;var elem = document.createElement('div');elem.id=value;elem.innerHTML=value;document.body.insertAdjacentElement('beforeend', elem);");
		CommonStep.store(CommonStep.getText($("common.var.the-time").getText()), "currentTime");
	}
}




