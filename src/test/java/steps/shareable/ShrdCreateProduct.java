
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
import java.util.Date;

/**
* @author Rodrigo Montemayor
*/
public class ShrdCreateProduct extends WebDriverTestCase {

	/**
	 * Creates a new product in salesforce
	 * 
	 * If correctly generated, the product name is saved on a variable called
	 * ${generated_productName}
	 * 
	 * @param productName The product name to create, or (empty|ANY) to create a random name
	 * @param description
	 * @param oemManufacturer
	 * @param activeDataSheet
	 */
	@QAFTestStep(description = "ShrdCreateProduct {productName} {description} {oemManufacturer} {activeDataSheet}")
	public void customShrdCreateProduct(String productName, String description, String oemManufacturer, 
	    String activeDataSheet) {
		
		//Product name sent, or time in millis
		if (productName == null || productName.trim().length() == 0 || productName.equalsIgnoreCase("ANY")) {
			productName = String.valueOf(new Date().getTime());
		}

		ShrdLaunchApp launchApp = new ShrdLaunchApp();
		launchApp.customShrdLaunchApp("products");
		
		$("products.create.new.button").waitForPresent();
		$("products.create.new.button").waitForEnabled();
		CommonStep.click("products.create.new.button");

		$("products.createProduct.popup.recordType.OM.input").waitForVisible();
		try {Thread.sleep(3000);} catch (Exception e) {System.out.println(e);}
		$("products.createProduct.popup.recordType.OM.input").waitForEnabled();
		CommonStep.click("products.createProduct.popup.recordType.OM.input");
		$("products.createProduct.popup.next.button").waitForEnabled();
		CommonStep.click("products.createProduct.popup.next.button");

		$("products.createProduct.popup.name.input").waitForEnabled();
		CommonStep.click("products.createProduct.popup.name.input");
		CommonStep.sendKeys(productName,"products.createProduct.popup.name.input");

		$("products.createProduct.popup.family.input").waitForEnabled();
		CommonStep.click("products.createProduct.popup.family.input");
		$("products.createProduct.popup.family.input.option.Tools").waitForEnabled();
		CommonStep.click("products.createProduct.popup.family.input.option.Tools");

		CommonStep.sendKeys(""+String.valueOf(description)+"","products.createProduct.popup.productDescription.input");

		if (oemManufacturer != null && oemManufacturer.trim().length() > 0) {
			CommonStep.sendKeys(""+String.valueOf(oemManufacturer)+"","products.createProduct.popup.oemManufacturer.input");
		}
		//Non-editable from the create screen
		/*if (productCode != null && productCode.trim().length() > 0) {
			CommonStep.sendKeys(""+String.valueOf(productCode)+"","products.createProduct.popup.productCode.input");
		}
		if (watts != null && watts.trim().length() > 0) {
			CommonStep.sendKeys(""+String.valueOf(watts)+"","products.createProduct.popup.watts.input");
		}*/
		if (activeDataSheet != null && activeDataSheet.trim().length() > 0) {
			CommonStep.sendKeys(""+String.valueOf(activeDataSheet)+"","products.createProduct.popup.activeDataSheet.input");
		}

		$("products.createProduct.popup.save.button").waitForEnabled();
		CommonStep.click("products.createProduct.popup.save.button");
		$("common.toastContainer").waitForPresent();
		$("products.details.name").waitForVisible();

		logger.info("Generated Product Name: " + productName);
		CommonStep.store(productName, "generated_productName");

	}
}




