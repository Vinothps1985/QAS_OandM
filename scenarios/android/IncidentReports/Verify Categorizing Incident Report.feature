Feature: IncidentReports

@author:Rodrigo Montemayor
@description:Verify Categorizing an Incident report from FSL app
@incidentreports @positive @mobile
@dataFile:resources/testdata/IncidentReports/Verify Categorizing Incident Report.csv

Scenario: Verify Categorizing an Incident Report
	
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
   And click on "cases.quickLinks.serviceAppointments"
   And wait until "serviceAppointments.table.first.link" to be enable
   Then store text from "serviceAppointments.table.first.link" into "generated_serviceAppointment"
   And click on "serviceAppointments.table.first.link"

   Then wait until "serviceAppointments.details.status" to be present
   And wait until "serviceAppointments.details.status" to be enable
   And store the current url in "generated_serviceAppointmentURL"

   And launch salesforce app "Field Service"
   And wait until "fieldService.iframe" for a max of 60 and min of 10 seconds to be present
   And wait until "fieldService.iframe" to be enable
   And switch to frame "fieldService.iframe"

   And wait until "fieldService.predefinedFilterSelector.select" to be present
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

   Then format date "${sa_scheduledStart}" from "M/d/yyyy" to "EEE, MMM dd" into "scheduledDateAndroidFormat"

   And click the date "${scheduledDateAndroidFormat}" in the scheduler datepicker

   And scroll refresh for up to 60 seconds until "serviceAppointments.appointments.first" is present

   And wait until "serviceAppointments.appointments.first" to be enable
   And scroll until the text "${generated_serviceAppointment}" is on the screen
   And click on service appointment "${generated_serviceAppointment}"

   #Create new incident
   And wait until "common.actions.button" to be enable
   And click on "common.actions.button"
   And wait until "serviceAppointments.actions.newIncidentReport" to be enable
   And click on "serviceAppointments.actions.newIncidentReport"
   
   And wait until "serviceAppointment.newIncidentReport.customerProperty.yes" to be enable
   And click on "serviceAppointment.newIncidentReport.customerProperty.yes"
   And click on "serviceAppointment.newIncidentReport.next"
   
   And assert android TextView is present with the text "Confirm Project"
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"

   And assert android TextView is present with the text "Major Incident or Close Call"
   And select option "Major Incident" for form input with name "Are you reporting a"
   And click on "serviceAppointment.newIncidentReport.next"

   And assert android TextView is present with the text "Major Incident To Report"
   And click on "serviceAppointment.newIncidentReport.next"

   And assert android TextView is present with the text contains "General Incident Info"
   And click on select button for form input with name "Incident Type"
   And select option that contains "${incidentType}" for form input with name "Incident Type"
   And open the date picker for form input with name "Incident Date and Time"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And wait until "common.date.popup.ok" is not present
   Then sendKeys "${namesPeoplePresent}" into form input group with name "Names of People Present"
   And scroll to end
   And select option "${knowAddressOfIncident}" for form input with name "Do you know the address of the incident"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Confirm Address Abb"
   And scroll to end
   And select option "${addressCorrectAsShown}" for form input with name "Incident Address correct as shown"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Duties"
   And select option "${normalJobDuties}" for form input with name "Were you performing normal job duties"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Any Witnesses"
   And select option "${anyWitnesses}" for form input with name "Were there any witnesses"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Property Damage"
   And select option "${propertyDamage}" for form input with name "Was There Employee Injury or Company Property Damage"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Employee Injury"
   And sendKeys "${injuryDetails}" into form input group with name "What was the injury"
   And click on select button for form input with name "Employee Injury Detail"
   And select option "${employeeInjury}" for form input with name "Employee Injury Detail"
   And click on "common.CONFIRM.button"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Company Property Damage"
   And sendKeys "${specificDamage}" into form input group with name "What is the specific Vehicle or Property damage"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Medical Attention"
   And select option "${medicalAttention}" for form input with name "Medical Attention"
   And sendKeys "${medProviderName}" into form input group with name "Medical Provider Name"
   And scroll to end
   And sendKeys "${medProviderPhone}" into form input group with name "Medical Provider Phone"
   And sendKeys "${medProviderAddress}" into form input group with name "Medical Provider Address"
   And select option "${employeeReturned}" for form input with name "Did the Employee return to work"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Sequence Of Events"
   And sendKeys "${whatHappened}" into form input group with name "Describe in detail what happened"
   And sendKeys "${trackIncident}" into form input group with name "Track incident from significant events"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Company Property"
   And select option "${companyVehicleInvolved}" for form input with name "Was this company Vehicle involved"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Choose A Company Asset"
   And click on select button for form input with name "What Truck if any was involved"
   And select option "${whatTruckInvolved}" for form input with name "What Truck if any was involved"
   And select option "${noTruckInvolved}" for form input with name "There was no truck involved in damage"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Describe Company Asset"
   And sendKeys "${companyAssets}" into form input group with name "Describe the Company Asset"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Any Third Party Injuries"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Local Authorities"
   And select option "${reportFiled}" for form input with name "Was a report filed with local authorities"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Other Driver"
   And select option "${otherDrivers}" for form input with name "Were other Drivers or Property Owners involved"
   And click on "serviceAppointment.newIncidentReport.next"
   
   Then assert android TextView is present with the text contains "Vehicles"
   And select option "${otherVehicles}" for form input with name "Were other vehicles involved"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Pictures needed"
   And select option "${addPictures}" for form input with name "Add Pictures"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Launch SharinPix"
   And wait until "serviceAppointment.newIncidentReport.takePictures.link" to be present
   And wait until "serviceAppointment.newIncidentReport.takePictures.link" to be enable
   And click on unclickable TextView with text "Take Pictures"

   Then wait until "sharinPix.takePhoto.button" to be present
   And wait until "sharinPix.takePhoto.button" to be enable
   And click on "sharinPix.takePhoto.button"

   Then wait until "sharinPix.uploadPhoto.button" to be present
   And wait until "sharinPix.uploadPhoto.button" to be enable
   And click on "sharinPix.uploadPhoto.button"

   Then assert android TextView is present with the text contains "Launch SharinPix"
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "New Incident Report Completed"
   And take a screenshot
   And click on "serviceAppointment.finish.button"

   And wait until "common.RELATED.link" to be present
   And wait until "common.RELATED.link" to be enable
   And click on "common.RELATED.link"

   Then wait until "serviceAppointment.related.timeSheetEntries.button" to be present
   And wait until "serviceAppointment.related.timeSheetEntries.button" to be enable
   And scroll to end
   And wait until "serviceAppointment.details.related.incidentReports.button" to be present
   And wait until "serviceAppointment.details.related.incidentReports.button" to be enable
   And click on "serviceAppointment.details.related.incidentReports.button"
   And assert android TextView is present with the text "Incident Reports"

   And scroll refresh for up to 120 seconds until "serviceAppointment.related.incidentReports.first" is present

   And assert "serviceAppointment.related.incidentReports.first" is present
   And take a screenshot

   #Change to web
   Then store "resource/testdata;resources/web" into "env.resources"
   And set current platform as "web"
   Given login to salesforce with "${username}" and "${password}"
   Then ShrdChangeLoggedInUser "test_ops_center_operator"

   Then get "${generated_serviceAppointmentURL}"
   Then wait until "serviceAppointments.details.status" to be enable
   And wait until "serviceAppointments.details.status" to be visible

   And scroll until "serviceAppointments.incidentReports.all.link" is visible
   And click on "serviceAppointments.incidentReports.all.link"

   And wait until "incidentReports.table.first.link" to be present
   And wait until "incidentReports.table.first.link" to be enable
   And click on "incidentReports.table.first.link"

   And wait until "incidentReports.details.incidentReportNumber" to be present
   And wait until "incidentReports.details.incidentReportNumber" to be enable
   And take a screenshot

   Then scroll until "incidentReports.details.sharinPix.iframe" is visible
   And wait until "incidentReports.details.sharinPix.iframe" to be enable
   And switch to frame "incidentReports.details.sharinPix.iframe"
   And wait until "incidentReports.details.sharinPix.image.first" to be present
   And wait until "incidentReports.details.sharinPix.image.first" to be enable
   And assert "incidentReports.details.sharinPix.image.first" is present
   And take a screenshot
   And switch to parent frame
   