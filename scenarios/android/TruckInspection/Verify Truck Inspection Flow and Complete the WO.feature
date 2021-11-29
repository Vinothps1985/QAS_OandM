Feature: TruckInspection

@author:Rodrigo Montemayor
@description:Verify Truck Inspection flow and COMPLETE the Truck Inspection work order
@truckinspection @positive @mobile
@dataFile:resources/testdata/TruckInspection/Verify Truck Inspection Flow and Complete the WO.csv

Scenario: Verify Truck Inspection flow and COMPLETE the Truck Inspection work order

   #First create the testing data in web
   Given store "resource/testdata;resources/web" into "env.resources"
   And set current platform as "web"
   #Login and change user
   Then login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"

   Then search for asset "Truck Test Asset - 1"

   #Create a truck inspection from the asset page
   Then store next business day into "nextBusinessDay" in format "M/d/yyyy"
   And create a truck inspection for the current asset with data "${workType}" "${nextBusinessDay}" "${woSubject}" "${woDescription}"

   And wait until "assets.workOrders.first.link" to be enable
   And wait until "assets.workOrders.first.link" to be visible
   And store text from "assets.workOrders.first.link" into "generated_workOrder"
   And click on "assets.workOrders.first.link"
   
   Then wait until "workOrders.details.workOrderNumber" to be enable
   And wait until "workOrders.details.workOrderNumber" to be visible
   And store the current url in "generated_workOrderURL"
   And take a screenshot
   And click on "workOrders.serviceAppointments.link"
   
   Then wait until "serviceAppointments.table.first.link" to be enable
   And wait until "serviceAppointments.table.first.link" to be visible
   Then store text from "serviceAppointments.table.first.link" into "generated_serviceAppointment"
   And click on "serviceAppointments.table.first.link"

   Then wait until "serviceAppointments.details.status" to be enable
   And wait until "serviceAppointments.details.status" to be visible

   #Reload
   Then Execute Java Script with data "window.location.reload();"

   Then wait until "serviceAppointments.details.status" to be enable
   And wait until "serviceAppointments.details.status" to be visible

   Then scroll until "serviceAppointments.details.scheduledStart.edit.button" is visible
   And click on "serviceAppointments.details.scheduledStart.edit.button"
   And wait until "serviceAppointments.details.scheduledStart.edit.input" to be enable
   And wait until "serviceAppointments.details.scheduledStart.edit.input" to be visible
   And sendKeys "${nextBusinessDay}" into "serviceAppointments.details.scheduledStart.edit.input"
   And type Enter "serviceAppointments.details.scheduledStart.edit.input"
   Then sendKeys "${nextBusinessDay}" into "serviceAppointments.details.scheduledEnd.edit.input"

   Then click on "serviceAppointments.details.edit.save.button"
   And wait until "serviceAppointments.details.scheduledStart.edit.button" to be visible
   And take a screenshot

   Then click on "serviceAppointments.assignedResources.all.link"

   Then wait until "serviceAppointments.assignedResources.new.button" to be enable
   Then wait until "serviceAppointments.assignedResources.new.button" to be visible
   And click on "serviceAppointments.assignedResources.new.button"

   Then wait until "serviceAppointment.editResource.popup.serviceResource.input" to be enable
   And wait until "serviceAppointment.editResource.popup.serviceResource.input" to be visible
   And sendKeys "${serviceAppointmentAssignee}" into "serviceAppointment.editResource.popup.serviceResource.input"
   Then wait until "serviceAppointment.editResource.popup.serviceResource.option.first" to be present
   And wait until "serviceAppointment.editResource.popup.serviceResource.option.first" to be visible
   And click on "serviceAppointment.editResource.popup.serviceResource.option.first"
   And take a screenshot
   And click on "common.save.button"
   Then wait until "common.toastContainer" to be present
   And wait until "common.toastContainer" to be enable
   And take a screenshot
   
   #Change to Android
   Then store "resource/testdata;resources/android" into "env.resources"
   And set current platform as "android"
   
   Then sendKeys "${passcode}" into "login.passcode.input"

   And click on "home.popup.useLocation.notYet.option" if it appears within 10000 milisec

   And wait until "schedule.date.icon" to be enable
   And click on "schedule.date.icon"

   Then format date "${nextBusinessDay}" from "M/d/yyyy" to "EEE, MMM dd" into "nextBusinessDayAndroidFormat"

   And click the date "${nextBusinessDayAndroidFormat}" in the scheduler datepicker

   And scroll refresh for up to 60 seconds until "serviceAppointments.appointments.first" is present

   And wait until "serviceAppointments.appointments.first" to be enable
   And scroll until the text "${generated_serviceAppointment}" is on the screen
   And click on service appointment "${generated_serviceAppointment}"

   And wait until "common.actions.button" to be enable
   And take a screenshot
   And click on "common.actions.button"

   And wait until "serviceAppointment.actions.truckInspection2" to be enable
   And click on "serviceAppointment.actions.truckInspection2"

   Then wait until "serviceAppointment.truckInspection.adminSafety.title" to be present
   And wait until "serviceAppointment.truckInspection.adminSafety.title" to be enable
   And assert "serviceAppointment.truckInspection.adminSafety.title" is enable

   Then assert android TextView is present with the text "Valid Vehicle Registration? *"
   And select option "Yes" for form input with name "Valid Vehicle Registration"
   Then assert android TextView is present with the text "Valid Proof of Insurance? *"
   And select option "No" for form input with name "Valid Proof of Insurance"
   Then assert android TextView is present with the text "Fire Extinguisher? (Charged) *"
   And select option "Yes" for form input with name "Fire Extinguisher"
   And scroll to end
   Then assert android TextView is present with the text "First Aid Kit? *"
   And select option "No" for form input with name "First Aid Kit"
   Then assert android TextView is present with the text "Spill Kit? *"
   And select option "Yes" for form input with name "Spill Kit"
   And take a screenshot

   Then click on "serviceAppointment.truckInspection.next.button"

   Then wait until "serviceAppointment.truckInspection.glass.title" to be present
   And wait until "serviceAppointment.truckInspection.glass.title" to be enable
   And assert "serviceAppointment.truckInspection.glass.title" is enable

   Then assert android TextView is present with the text "Windshield is Cracked *"
   And select option "Yes" for form input with name "Windshield is Cracked"
   Then assert android TextView is present with the text "Windshield is Chipped *"
   And select option "No" for form input with name "Windshield is Chipped"
   And take a screenshot

   Then click on "serviceAppointment.truckInspection.next.button"

   Then wait until "serviceAppointment.truckInspection.tires.title" to be present
   And wait until "serviceAppointment.truckInspection.tires.title" to be enable
   And assert "serviceAppointment.truckInspection.tires.title" is enable

   And scroll to end

   #Ensure all options appear
   Then assert android TextView is present with the text "Damaged Tires *"
   Then assert android TextView is present with the text "Front Driver"
   Then assert android TextView is present with the text "Rear Driver"
   Then assert android TextView is present with the text "Front Passenger"
   Then assert android TextView is present with the text "Rear Passenger"
   Then assert android TextView is present with the text "No Damage"

   #Select one option plus No Damage
   Then select option "Rear Passenger" for form input with name "Damaged Tires"
   And select option "No Damage" for form input with name "Damaged Tires"
   And take a screenshot
   And click on "serviceAppointment.truckInspection.next.button"

   Then wait until "serviceAppointment.truckInspection.tireSelectionError.title" to be present
   And wait until "serviceAppointment.truckInspection.tireSelectionError.title" to be enable
   And assert "serviceAppointment.truckInspection.tireSelectionError.title" is enable
   And take a screenshot

   Then click on "serviceAppointment.truckInspection.back.button"

   Then wait until "serviceAppointment.truckInspection.tires.title" to be present
   And wait until "serviceAppointment.truckInspection.tires.title" to be enable
   And assert "serviceAppointment.truckInspection.tires.title" is enable

   And scroll to end

   #Unselect 'No Damage'
   Then select option "No Damage" for form input with name "Damaged Tires"
   And take a screenshot
   And click on "serviceAppointment.truckInspection.next.button"

   Then wait until "serviceAppointment.truckInspection.drivetrainElectrical.title" to be present
   And wait until "serviceAppointment.truckInspection.drivetrainElectrical.title" to be enable
   And assert "serviceAppointment.truckInspection.drivetrainElectrical.title" is enable

   Then assert android TextView is present with the text "Odometer Reading *"
   And click on "serviceAppointment.truckInspection.odometerReading.inputGroup"
   And click on "serviceAppointment.truckInspection.odometerReading.input"
   And sendKeys "10" into "serviceAppointment.truckInspection.odometerReading.input"
   And hide the android keyboard

   Then assert android TextView is present with the text "Engine Condition *"
   #Click it twice to ensure we're out of the odometer reading input-group edit mode
   And select option "Not OK" for form input with name "Engine Condition"
   And select option "Not OK" for form input with name "Engine Condition"

   Then assert android TextView is present with the text "Transmission Condition *"
   And select option "Not OK" for form input with name "Transmission Condition"
   And take a screenshot
   And scroll to end 

   Then assert android TextView is present with the text "Electrical Condition *"
   And select option "OK" for form input with name "Electrical Condition"

   Then assert android TextView is present with the text "Brakes Condition *"
   And select option "OK" for form input with name "Brakes Condition"

   Then assert android TextView is present with the text "Exhaust Condition *"
   And select option "OK" for form input with name "Exhaust Condition"
   
   And click on "serviceAppointment.truckInspection.next.button"

   Then wait until "serviceAppointment.truckInspection.notOKReason.title" to be present
   And wait until "serviceAppointment.truckInspection.notOKReason.title" to be enable
   And assert "serviceAppointment.truckInspection.notOKReason.title" is enable

   Then click on "serviceAppointment.truckInspection.notOKReason.input"
   And sendKeys "Not OK Reason" into "serviceAppointment.truckInspection.notOKReason.input"
   And hide the android keyboard
   And take a screenshot

   And click on "serviceAppointment.truckInspection.next.button"

   Then wait until "serviceAppointment.truckInspection.component1.title" to be present
   And wait until "serviceAppointment.truckInspection.component1.title" to be enable
   And assert "serviceAppointment.truckInspection.component1.title" is enable
   And take a screenshot

   Then click on "serviceAppointment.truckInspection.back.button"
   And wait until "serviceAppointment.truckInspection.notOKReason.title" to be present
   And wait until "serviceAppointment.truckInspection.notOKReason.title" to be enable
   And click on "serviceAppointment.truckInspection.back.button"
   Then wait until "serviceAppointment.truckInspection.drivetrainElectrical.title" to be present
   And wait until "serviceAppointment.truckInspection.drivetrainElectrical.title" to be enable

   And select option "OK" for form input with name "Engine Condition"
   And select option "OK" for form input with name "Transmission Condition"
   And take a screenshot
   And scroll to end
   And select option "OK" for form input with name "Electrical Condition"
   And select option "OK" for form input with name "Brakes Condition"
   And select option "OK" for form input with name "Exhaust Condition"
   And click on "serviceAppointment.truckInspection.next.button"

   Then perform truck inspection steps with data "${answer1}" "${answer2}" "${answer3}" "${answer4}" "${answer5}"

   And wait until "serviceAppointment.truckInspection.confirmCompletion.title" to be present
   And wait until "serviceAppointment.truckInspection.confirmCompletion.title" to be enable
   And assert "serviceAppointment.truckInspection.confirmCompletion.title" is enable
   And select option "Yes" for form input with name "Is Truck Inspection Complete"

   And click on "serviceAppointment.truckInspection.next.button"

   Then wait until "serviceAppointment.truckInspection.travelStartTime.datepicker" to be present
   And wait until "serviceAppointment.truckInspection.travelStartTime.datepicker" to be enable
   And click on "serviceAppointment.truckInspection.travelStartTime.datepicker"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And wait until "serviceAppointment.truckInspection.workStartTime.datepicker" to be enable
   And click on "serviceAppointment.truckInspection.workStartTime.datepicker"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And wait until "serviceAppointment.truckInspection.workEndTime.datepicker" to be enable
   And click on "serviceAppointment.truckInspection.workEndTime.datepicker"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And take a screenshot

   And click on "serviceAppointment.truckInspection.next.button"

   Then wait until "serviceAppointment.truckInspection.uploadAdditionalPictures.title" to be present
   And wait until "serviceAppointment.truckInspection.uploadAdditionalPictures.title" to be enable

   And click on "serviceAppointment.truckInspection.next.button"

   Then wait until "serviceAppointment.truckInspection.inspectionComplete.title" to be present
   And wait until "serviceAppointment.truckInspection.inspectionComplete.title" to be enable

   And click on "serviceAppointment.truckInspection.next.button"

   Then wait until "serviceAppointment.truckInspection.truckInspectionCompleted.title" to be present
   And wait until "serviceAppointment.truckInspection.truckInspectionCompleted.title" to be enable

   Then take a screenshot
   And click on "serviceAppointment.finish.button"

   And wait until "common.RELATED.link" to be present
   And wait until "common.RELATED.link" to be enable
   And click on "common.RELATED.link"

   Then wait until "serviceAppointment.related.timeSheetEntries.button" to be present
   Then wait until "serviceAppointment.related.timeSheetEntries.button" to be enable
   Then click on "serviceAppointment.related.timeSheetEntries.button"

   Then wait until "serviceAppointment.related.timeSheetEntries.title" to be present
   Then wait until "serviceAppointment.related.timeSheetEntries.title" to be enable

   #This steps verifies, doesn't assert, so if it doesn't appear after 80 seconds, it continues...
   And scroll refresh for up to 120 seconds until "serviceAppointment.related.timeSheetEntries.first" is present

   And assert "serviceAppointment.related.timeSheetEntries.first" is present
   And take a screenshot