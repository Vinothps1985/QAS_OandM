package steps.shareable.truckinspections;

import static com.qmetry.qaf.automation.ui.webdriver.ElementFactory.$;
import com.qmetry.qaf.automation.step.QAFTestStep;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.qmetry.qaf.automation.ui.WebDriverTestCase;

public class TruckInspectionSteps extends WebDriverTestCase {

	Log logger = LogFactory.getLog(getClass());

	@QAFTestStep(description = "perform truck inspection steps with data {answer1} {answer2} {answer3} {answer4} {answer5}")
	public void performTruckInspectionStepsWithData(String answer1, String answer2,
	String answer3, String answer4, String answer5) {
		
		int component = 1;
		for (component = 1; component <= 1; component++) {
			//1 = Driver Side Front Quarter Panel
			//Separated to test the saving-continuing functionality in component 2
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForPresent();
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForEnabled();
			$("serviceAppointment.truckInspection.component" + component + ".title").assertPresent();
			//steps.common.StepsLibrary.takeAScreenshot();
			answerDentsQuestionsAndClickOnNext(answer1, answer2, answer3, answer4, answer5);
		}

		//2 = Driver Side Front Door
		//Separated to test the saving-continuing functionality in component 2
		$("serviceAppointment.truckInspection.component" + component + ".title").waitForPresent();
		$("serviceAppointment.truckInspection.component" + component + ".title").waitForEnabled();
		$("serviceAppointment.truckInspection.component" + component + ".title").assertPresent();
		
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

		//Save and exit!
		steps.android.StepsLibrary.selectOptionForFormInput("Save and Exit", "Would you like to Continue or Save");
		steps.common.StepsLibrary.takeAScreenshot();
		$("serviceAppointment.truckInspection.next.button").click();

		steps.android.StepsLibrary.assertAndroidTextViewPresentWithText("Not Completed");
		$("serviceAppointment.truckInspection.next.button").click();
		$("serviceAppointment.truckInspection.truckInspectionCompleted.title").waitForPresent();
		$("serviceAppointment.truckInspection.truckInspectionCompleted.title").waitForEnabled();
		$("serviceAppointment.finish.button").click();

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

		for (component = 3; component <= 4; component++) {
			//3 = Driver Side Rear Door, 4 = Driver Side Rear Quarter Panel
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForPresent();
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForEnabled();
			$("serviceAppointment.truckInspection.component" + component + ".title").assertPresent();
			//steps.common.StepsLibrary.takeAScreenshot();
			answerDentsQuestionsAndClickOnNext(answer1, answer2, answer3, answer4, answer5);
		}

		//Exterior picture required
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").waitForPresent();
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").waitForEnabled();
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").assertPresent();
		$("serviceAppointment.truckInspection.next.button").click();

		for (component = 5; component <= 6; component++) {
			//5 = Tailgate, 6 = Rear Bumper
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForPresent();
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForEnabled();
			$("serviceAppointment.truckInspection.component" + component + ".title").assertPresent();
			//steps.common.StepsLibrary.takeAScreenshot();
			answerDentsQuestionsAndClickOnNext(answer1, answer2, answer3, answer4, answer5);
		}

		//Exterior picture required
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").waitForPresent();
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").waitForEnabled();
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").assertPresent();
		$("serviceAppointment.truckInspection.next.button").click();

		for (component = 7; component <= 8; component++) {
			//7 = Front Bumper, 8 = Hood
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForPresent();
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForEnabled();
			$("serviceAppointment.truckInspection.component" + component + ".title").assertPresent();
			//steps.common.StepsLibrary.takeAScreenshot();
			answerDentsQuestionsAndClickOnNext(answer1, answer2, answer3, answer4, answer5);
		}

		//Exterior picture required
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").waitForPresent();
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").waitForEnabled();
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").assertPresent();
		$("serviceAppointment.truckInspection.next.button").click();

		for (component = 9; component <= 12; component++) {
			//9 = Passenger Side Front Quarter Panel, 10 = Passenger Side Front Door
			//11 = Passenger Side Rear Door, 12 = Side Rear Quarter Panel
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForPresent();
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForEnabled();
			$("serviceAppointment.truckInspection.component" + component + ".title").assertPresent();
			//steps.common.StepsLibrary.takeAScreenshot();
			answerDentsQuestionsAndClickOnNext(answer1, answer2, answer3, answer4, answer5);
		}

		//Exterior picture required
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").waitForPresent();
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").waitForEnabled();
		$("serviceAppointment.truckInspection.exteriorPictureRequired.title").assertPresent();
		$("serviceAppointment.truckInspection.next.button").click();

		for (component = 13; component <= 20; component++) {
			//13 = Front Carpet/Flooring, 14 = Rear Carpet/Flooring
			//15 = Front Seats, 16 = Rear Seats, 17 = Head Liner, 18 = Dash
			//19 = Internal Front Door Panels, 20 = Internal Rear Door Panels
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForPresent();
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForEnabled();
			$("serviceAppointment.truckInspection.component" + component + ".title").assertPresent();
			//steps.common.StepsLibrary.takeAScreenshot();
			answerStainsQuestionsAndClickOnNext(answer1, answer2, answer3, answer4);

		}

		//Interior picture required
		$("serviceAppointment.truckInspection.interiorPictureRequired.title").waitForPresent();
		$("serviceAppointment.truckInspection.interiorPictureRequired.title").waitForEnabled();
		$("serviceAppointment.truckInspection.interiorPictureRequired.title").assertPresent();
		$("serviceAppointment.truckInspection.next.button").click();

		for (component = 21; component <= 22; component++) {
			//21 = Cab Top
			//22 = Ladder Rack
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForPresent();
			$("serviceAppointment.truckInspection.component" + component + ".title").waitForEnabled();
			$("serviceAppointment.truckInspection.component" + component + ".title").assertPresent();
			//steps.common.StepsLibrary.takeAScreenshot();
			answerDentsQuestionsAndClickOnNext(answer1, answer2, answer3, answer4, answer5);
		}
			
	}
	
	public void answerDentsQuestionsAndClickOnNext(String answer1, String answer2,
	String answer3, String answer4, String answer5) {

		/*steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Major Dents");
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

		steps.android.StepsLibrary.selectOptionForFormInput("Continue", "Would you like to Continue or Save");*/
		$("serviceAppointment.truckInspection.next.button").click();
	}

	public void answerStainsQuestionsAndClickOnNext(String answer1, String answer2,
	String answer3, String answer4) {

		/*steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Major Stains");
		steps.android.StepsLibrary.selectOptionForFormInput(answer1, "Qty of Major Stains");
		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Minor Stains");
		steps.android.StepsLibrary.selectOptionForFormInput(answer2, "Qty of Minor Stains");
		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Major Tears");
		steps.android.StepsLibrary.selectOptionForFormInput(answer3, "Qty of Major Tears");
		steps.android.StepsLibrary.scrollAndroidToEnd();
		steps.android.StepsLibrary.clickOnSelectInputForFormInput("Qty of Minor Tears");
		steps.android.StepsLibrary.selectOptionForFormInput(answer4, "Qty of Minor Tears");

		steps.android.StepsLibrary.selectOptionForFormInput("Continue", "Would you like to Continue or Save");*/
		$("serviceAppointment.truckInspection.next.button").click();
	}

}