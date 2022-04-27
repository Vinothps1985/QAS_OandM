package steps.shareable.truckinspections;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.step.QAFTestStep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebElement;

import com.qmetry.qaf.automation.ui.WebDriverTestCase;
import com.qmetry.qaf.automation.util.Validator;

public class TruckInspectionSteps extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	@QAFTestStep(description = "perform truck inspection steps with data {answer1} {answer2} {answer3} {answer4} {answer5}")
	public void performTruckInspectionStepsWithData(String answer1, String answer2,
		String answer3, String answer4, String answer5) {

		//Components (Driver Side Front Quarter Panel, Front Door, etc) are actually dynamic
		//the screens currently have two options: They either ask about exterior (dents & scratches)
		//or interior (stains & tears). So we check which is the current screen and answer based
		//on that

		//Exterior questions have the text: Qty of Major Dents
		//Interior questions have the text: Qty of Major Stains

		//We may also be asked to take pictures, with texts:
		//    Exterior Picture Required
		//    Interior Picture Required

		//We will continue until we find the last screen with the text: Confirm Completion

		int iteration = 0;
		int maxChecksWithoutPage = 30; //Max rounds waiting 1.5 secs without getting one of the expected pages
		int checksWithoutPage = 0;
		int picturesTaken = 0;
		while (true) {
			boolean exteriorQuestions = isTextOnScreen("Qty of Major Dents");
			boolean interiorQuestions = isTextOnScreen("Qty of Major Stains");
			boolean exteriorPicture = isTextOnScreen("Exterior Picture Required");
			boolean interiorPicture = isTextOnScreen("Interior Picture Required");
			boolean confirmCompletion = isTextOnScreen("Confirm Completion");

			if (confirmCompletion) {
				//we're out
				break;
			}

			boolean clickNext = true;
			//If it's the second page, we want to test the 'save and exit' functionality!
			if (iteration == 1) {
				clickNext = false;
			}

			if (exteriorQuestions) {
				answerDentsQuestions(answer1, answer2, answer3, answer4, answer5, clickNext);
			} else if (interiorQuestions) {
				answerStainsQuestions(answer1, answer2, answer3, answer4, clickNext);
			} else if (exteriorPicture) {
				if (picturesTaken == 0) {
					logger.info("Taking a picture");
					takePicture();
					picturesTaken++;
				}
				$("serviceAppointment.truckInspection.next.button").click();
			} else if (interiorPicture) {
				if (picturesTaken == 0) {
					logger.info("Taking a picture");
					takePicture();
					picturesTaken++;
				}
				$("serviceAppointment.truckInspection.next.button").click();
			} else {
				//No page, wait and continue
				checksWithoutPage++;
				if (checksWithoutPage > maxChecksWithoutPage) {
					throw new AssertionError("No expected page found in the truck inspection flow");
				}
				try {Thread.sleep(1500);} catch (Exception x) {}
				continue;
			}

			//We got a page
			checksWithoutPage = 0;

			//If we didn't click on next, text the Save and Exit functionality
			if (!clickNext) {
				saveAndExitAndRestart();
			}

			iteration++;
		}
			
	}

	private void takePicture() {
		steps.android.StepsLibrary.clickUnclickableTextView("Take Pictures using SharinPix app");
		$("sharinPix.takePhoto.button").waitForPresent();
		$("sharinPix.takePhoto.button").waitForEnabled();
		$("sharinPix.takePhoto.button").click();

		$("sharinPix.uploadPhoto.button").waitForPresent();
		$("sharinPix.uploadPhoto.button").waitForEnabled();
		$("sharinPix.uploadPhoto.button").click();
		steps.android.StepsLibrary.assertAndroidTextViewPresentWithText("Take Pictures using SharinPix app");
	}

	private boolean isTextOnScreen(String text) {
		try {
			String xpath= "//android.widget.TextView[contains(@text, '" + text + "')]";
			WebElement element = getDriver().findElementByXPath(xpath);
			if (element != null && element.isDisplayed() && element.isEnabled()) {
				return true;
			}
		} catch (Exception x) {
			return false;
		}
		return false;
	}
	
	public void answerDentsQuestions(String answer1, String answer2, String answer3, String answer4, String answer5, boolean clickNext) {

		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Major Dents");
		steps.android.StepsLibrary.selectOptionForFormInput(answer1, "Qty of Major Dents");
		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Minor Dents");
		steps.android.StepsLibrary.selectOptionForFormInput(answer2, "Qty of Minor Dents");
		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Major Scratches");
		steps.android.StepsLibrary.selectOptionForFormInput(answer3, "Qty of Major Scratches");
		steps.android.StepsLibrary.scrollAndroidToEnd();
		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Minor Scratches");
		steps.android.StepsLibrary.selectOptionForFormInput(answer4, "Qty of Minor Scratches");
		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Paint Chips");
		steps.android.StepsLibrary.selectOptionForFormInput(answer5, "Qty of Paint Chips");

		if (clickNext) {
			steps.android.StepsLibrary.selectOptionForFormInput("Continue", "Would you like to Continue or Save");
			$("serviceAppointment.truckInspection.next.button").click();
		}
	}

	public void answerStainsQuestions(String answer1, String answer2, String answer3, String answer4, boolean clickNext) {

		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Major Stains");
		steps.android.StepsLibrary.selectOptionForFormInput(answer1, "Qty of Major Stains");
		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Minor Stains");
		steps.android.StepsLibrary.selectOptionForFormInput(answer2, "Qty of Minor Stains");
		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Major Tears");
		steps.android.StepsLibrary.selectOptionForFormInput(answer3, "Qty of Major Tears");
		steps.android.StepsLibrary.scrollAndroidToEnd();
		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Minor Tears");
		steps.android.StepsLibrary.selectOptionForFormInput(answer4, "Qty of Minor Tears");

		if (clickNext) {
		    steps.android.StepsLibrary.selectOptionForFormInput("Continue", "Would you like to Continue or Save");
			$("serviceAppointment.truckInspection.next.button").click();
		}
	}

	public void saveAndExitAndRestart() {
		steps.android.StepsLibrary.selectOptionForFormInput("Save and Exit", "Would you like to Continue or Save");
		steps.common.StepsLibrary.takeAScreenshot();
		$("serviceAppointment.truckInspection.next.button").click();

		steps.android.StepsLibrary.assertAndroidTextViewPresentWithText("Not Completed");
		$("serviceAppointment.truckInspection.next.button").click();
		$("serviceAppointment.truckInspection.truckInspectionCompleted.title").waitForPresent();
		$("serviceAppointment.truckInspection.truckInspectionCompleted.title").waitForEnabled();
		//$("serviceAppointment.finish.button").click();

		$("common.actions.button").waitForPresent();
		$("common.actions.button").waitForEnabled();
		$("common.actions.button").click();
		
		$("serviceAppointment.actions.truckInspection2").waitForPresent();
		$("serviceAppointment.actions.truckInspection2").waitForEnabled();
		$("serviceAppointment.actions.truckInspection2").click();

		steps.android.StepsLibrary.assertAndroidTextViewPresentWithText("Resume or Restart?");
		steps.android.StepsLibrary.selectOptionForFormInput("Resume Inspection", "This Truck Inspection is In Progress");
		steps.common.StepsLibrary.takeAScreenshot();
		$("serviceAppointment.truckInspection.next.button").click();
	}

}