package steps.web;

import static com.qmetry.qaf.automation.step.CommonStep.click;
import static com.qmetry.qaf.automation.step.CommonStep.sendKeys;

import com.qmetry.qaf.automation.step.QAFTestStep;

public class StepsLibrary {
	/**
	 * @param data
	 *                       : data which is being passed from bdd
	 */
	@QAFTestStep(description = "sample step with {0}")
	public static void sampleStep(String data) {
	}
}
