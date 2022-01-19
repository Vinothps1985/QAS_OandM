Feature: LiveWorkPermit

@author:Rodrigo Montemayor
@description:Verify whether a user is able to Add Live Work Permit to the Work Order Line item using FSL app
@liveworkpermit @positive @mobile
@requirementKey:QTM-RQ-23
@dataFile:resources/testdata/LiveWorkPermit/Add Live Work Permit to Work Order Line Item.csv

Scenario: Add Live Work Permit to Work Order Line Item
	
   #First create the testing data in web
   Given store "resource/testdata;resources/web" into "env.resources"
   And set current platform as "web"
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   And close all open web tabs
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot
   And create a work order with data "${generated_caseNumber}" "${assetType1}" "${assetType2}"
   And take a screenshot

   Then wait until "cases.quickLinks.serviceAppointments" to be enable
   And store text from "case.details.accountName" into "accountName"
   And click on "cases.quickLinks.serviceAppointments"
   And wait until "serviceAppointments.table.first.link" to be enable
   Then store text from "serviceAppointments.table.first.link" into "generated_serviceAppointment"
   And click on "serviceAppointments.table.first.link"

   Then wait until "serviceAppointments.details.status" to be present
   And wait until "serviceAppointments.details.status" to be enable
   And store the current url in "generated_serviceAppointmentURL"

   Then go to case "${generated_caseNumber}"

   Then wait until "cases.quickLinks.workOrders" to be enable
   And click on "cases.quickLinks.workOrders"

   Then wait until "workOrders.table.firstResult.link" to be enable
   Then store text from "workOrders.table.firstResult.link" into "generated_workOrder"

   And launch salesforce app "Field Service"
   And wait until "fieldService.iframe" for a max of 60 and min of 10 seconds to be present
   And wait until "fieldService.iframe" to be enable
   And switch to frame "fieldService.iframe"

   And wait until "fieldService.predefinedFilterSelector.select" to be present
   And wait for 10000 milisec
   And select "label=All Service Appointments" in "fieldService.predefinedFilterSelector.select"
   And wait until "fieldService.searchServiceAppointments.input" to be enable
   And sendKeys "${generated_serviceAppointment}" into "fieldService.searchServiceAppointments.input"
   And wait until "fieldService.serviceAppointmentsList.firstOption" to be enable
   And wait until "fieldService.serviceAppointmentsList.firstOption.serviceAppointmentID" text "${generated_serviceAppointment}"
   And click on "fieldService.serviceAppointmentsList.firstOption"
   Then wait until "fieldService.serviceAppointment.firstOption.schedule.button" to be visible
   And click on "fieldService.serviceAppointment.firstOption.schedule.button"
   And wait until "fieldService.loadingIndicator" to be visible
   Then wait until "fieldService.loadingIndicator" not to be visible

   Then get "${generated_serviceAppointmentURL}"
   Then wait until "serviceAppointments.details.status" to be enable
   And wait until "serviceAppointments.details.status" to be visible

   #Reload and assign to mobile user (e.g. Test Technician)
   Then Execute Java Script with data "window.location.reload();"
   Then wait until "serviceAppointments.assignedResources.first.dropDown.button" to be enable
   And wait until "serviceAppointments.assignedResources.first.dropDown.button" to be visible
   And click on "serviceAppointments.assignedResources.first.dropDown.button"
   Then wait until "serviceAppointments.assignedResources.first.dropDown.edit.option" to be present
   And click on "serviceAppointments.assignedResources.first.dropDown.edit.option"
   Then wait until "serviceAppointment.editResource.popup.serviceResource.delete.link" to be enable
   And wait until "serviceAppointment.editResource.popup.serviceResource.delete.link" to be visible
   And click on "serviceAppointment.editResource.popup.serviceResource.delete.link"
   Then wait until "serviceAppointment.editResource.popup.serviceResource.input" to be enable
   And wait until "serviceAppointment.editResource.popup.serviceResource.input" to be visible
   And sendKeys "${serviceAppointmentAssignee}" into "serviceAppointment.editResource.popup.serviceResource.input"
   Then wait until "serviceAppointment.editResource.popup.serviceResource.option.first" to be present
   And wait until "serviceAppointment.editResource.popup.serviceResource.option.first" to be visible
   And click on "serviceAppointment.editResource.popup.serviceResource.option.first"
   And take a screenshot
   And click on "serviceAppointment.editResource.popup.save.button"
   Then wait until "common.toastContainer" to be present
   And wait until "common.toastContainer" to be enable
   And take a screenshot
   #Save the scheduled date
   Then scroll until "serviceAppointments.details.scheduledStart" is visible
   And store text from "serviceAppointments.details.scheduledStart" into "sa_scheduledStart"
   #Remove the time and take just the date (e.g. 11/16/2021 9:18 AM = 11/16/2021)
   And extract the date component from "${sa_scheduledStart}" into "sa_scheduledStart"

   #Change to Android
   Then store "resource/testdata;resources/android" into "env.resources"
   And set current platform as "android"

   When sendKeys "${passcode}" into "login.passcode.input"

   And click on "home.popup.useLocation.notYet.option" if it appears within 10000 milisec

   And go back until "home.menu.profile.button" is present
   And wait until "home.menu.schedule.button" to be enable
   And click on "home.menu.schedule.button"

   And wait until "schedule.date.icon" to be enable
   And click on "schedule.date.icon"

   Then format date "${sa_scheduledStart}" from "M/d/yyyy" to "EEE, MMM d" into "scheduledDateAndroidFormat"
   Then store the current date in format "MMM d, yyyy" into "currentDateAndroidFormat"

   And click the date "${scheduledDateAndroidFormat}" in the scheduler datepicker

   And scroll refresh for up to 60 seconds until "serviceAppointments.appointments.first" is present

   And wait until "serviceAppointments.appointments.first" to be enable
   And scroll until the text "${generated_serviceAppointment}" is on the screen
   And click on service appointment "${generated_serviceAppointment}"
   
   And wait until "common.RELATED.link" to be enable
   And click on "common.RELATED.link"

   And wait until "workOrder.related.workOrderLineItems.button" to be enable
   And click on "workOrder.related.workOrderLineItems.button"

   And wait until "workOrder.related.workOrderLineItems.first" to be enable
   And click on "workOrder.related.workOrderLineItems.first"

   And wait until "common.actions.button" to be enable
   And click on "common.actions.button"

   And wait until "woLineItem.actions.newLiveWorkPermit.button" to be enable
   And click on "woLineItem.actions.newLiveWorkPermit.button"

   Then assert android TextView is present with the text "PROJECT INFORMATION"
   And toggle the switch for form input with name "Does Everything Above Look Correct?"
   
   And assert android TextView is present with the text contains "Date:  ${currentDateAndroidFormat}"
   And assert android TextView is present with the text contains "Work Order:  ${generated_workOrder}"
   And assert android TextView is present with the text contains "Account:  ${accountName}"
   And assert android TextView is present with the text contains "Requestor:  ${serviceAppointmentAssignee}"
   
   Then click on "common.next.button"

   Then assert android TextView is present with the text "Work Explanation"
   Then sendKeys "${workToBePerformed}" into form input group with name "Describe Work to be Performed"
   Then sendKeys "${explanation}" into form input group with name "Explain Why"
   Then click on "common.next.button"

   Then assert android TextView is present with the text "Job Description"
   Then sendKeys "${detailedJobDescription}" into form input group with name "Detailed job description"
   Then sendKeys "${safeWorkPractices}" into form input group with name "Description of safe work practices"
   Then click on "common.next.button"

   Then assert android TextView is present with the text contains "Shock"
   Then sendKeys "${voltage}" into form input group with name "Voltage to which personnel will be exposed"
   Then sendKeys "${availableIncidentEnergy}" into form input group with name "Available Incident Energy"
   And scroll to end
   And hide the android keyboard
   And scroll to end
   Then sendKeys "${shockPPERequired}" into form input group with name "Shock PPE Required"
   Then sendKeys "${arcFlashPPERequired}" into form input group with name "Arc Flash PPE Required"
   Then click on "common.next.button"

   Then assert android TextView is present with the text contains "Enter Voltage Range"
   And click on select button for form input with name "Nominal System Voltage Range Phase to Phase"
   And select option "${nominalSystemVoltageRange}" for form input with name "Nominal System Voltage Range Phase to Phase"
   Then sendKeys "${workToBePerformed2}" into form input group with name "Work To Be Performed"
   Then click on "common.next.button"

   Then assert android TextView is present with the text contains "Preliminary Checks"
   Then sendKeys "${restrictedBy}" into form input group with name "Access to area restricted by"
   Then sendKeys "${qualifiedPersons}" into form input group with name "Qualified Persons"
   Then click on "common.next.button"

   Then assert android TextView is present with the text contains "Job Location"
   Then select option "${jsaCompleted}" for form input with name "JSA Completed"
   Then select option "${hasEquipmentBeenLockedOut}" for form input with name "Has Equipment been locked out"
   
   Then scroll until the text containing "Have barriers been established" is on the screen
   #Then scroll until the text containing "Have rubber gloves and other protective equipment and tools been Visually inspected" is on the screen
   Then select option "${rubberGloves}" for form input with name "Have rubber gloves and other protective equipment and tools been Visually inspected"
   Then select option "${barriers}" for form input with name "Have barriers been established"
   
   Then scroll until the text containing "Is there adequate Lighting" is on the screen
   Then select option "${listPersonalGrounds}" for form input with name "Has a list of where personal grounds will be applied been created"

   Then scroll until the text containing "Workers insulated from ground" is on the screen
   Then select option "${adequateLighting}" for form input with name "Is there adequate Lighting"

   Then scroll to end
   Then select option "${workersInsulated}" for form input with name "Workers insulated from ground"
   Then select option "${personTrained}" for form input with name "Is a person trained and certified in CPR standing by"
   Then click on "common.next.button"

   Then assert android TextView is present with the text contains "Job Location 02"
   Then select option "${metalHandHeldTools}" for form input with name "Metal hand-held tools to be used"
   Then select option "${personCloseBy}" for form input with name "Person standing by close to the breakers"
   
   Then scroll until the text containing "Proper PPE identified and worn" is on the screen
   Then select option "${safetyLine}" for form input with name "Safety line is securely attached to worker"
   Then select option "${properPPE}" for form input with name "Proper PPE identified and worn"

   Then scroll to end
   Then select option "${testMeter}" for form input with name "Is test meter in calibration and has been checked"
   Then click on "common.next.button"

   Then assert android TextView is present with the text contains "Submit Live Work Permit Request"
   Then select option "Yes" for form input with name "Submit Live Work Request for"
   Then click on "common.next.button"

   Then assert android TextView is present with the text contains "New Live Work Permit Completed"
   Then click on "common.finish.button"

   Then assert android TextView is present with the text contains "WORK ORDER LINE ITEM"
   Then click on "common.RELATED.link"

   Then assert android TextView is present with the text contains "Live Work Permits"
   And click on "woLineItem.related.liveWorkPermits.button"

   Then assert android TextView is present with the text contains "Live Work Permits"

   #This steps verifies, doesn't assert, so if it doesn't appear after 30 seconds, it continues...
   And scroll refresh for up to 30 seconds until "woLineItem.related.liveWorkPermits.first" is present

   And assert "woLineItem.related.liveWorkPermits.first" is present
   And take a screenshot

   

   #Change to web
   Then store "resource/testdata;resources/web" into "env.resources"
   And set current platform as "web"
   Given login to salesforce with "${username}" and "${password}"
   Then change logged in user to "test_ops_center_operator"
   And close all open web tabs
   
   Then go to case "${generated_caseNumber}"
   And wait until "cases.quickLinks.workOrderLineItems" to be present
   And wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"
   
   Then wait until "woLineItems.table.firstResult.link" to be present
   And wait until "woLineItems.table.firstResult.link" to be enable
   And click on "woLineItems.table.firstResult.link"

   Then wait until "woLineItems.details.case.link" to be present
   Then wait until "woLineItems.details.case.link" to be enable
   And scroll until "woLineItems.liveWorkPermits.link" is visible
   And click on "woLineItems.liveWorkPermits.link"

   Then wait until "liveWorkPermits.table.firstResult.link" to be present
   Then wait until "liveWorkPermits.table.firstResult.link" to be enable
   And take a screenshot
   And click on "liveWorkPermits.table.firstResult.link"

   And wait until "liveWorkPermits.details.status" to be present
   And wait until "liveWorkPermits.details.status" to be enable
   And take a screenshot