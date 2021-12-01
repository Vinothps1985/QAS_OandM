Feature: IncidentReports

@author:Rodrigo Montemayor
@description:Verify Incident report creation from FSL app
@incidentreports @positive @mobile
@dataFile:resources/testdata/IncidentReports/Create incident report.csv

Scenario: Create Incident Report
	
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
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"
   And wait for 1500 milisec
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"
   And wait until "serviceAppointment.newIncidentReport.severity.closeCall" to be enable
   And click on "serviceAppointment.newIncidentReport.severity.closeCall"
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"
   And wait until "serviceAppointment.newIncidentReport.closeCall.incidentDate.input" to be enable
   And click on "serviceAppointment.newIncidentReport.closeCall.incidentDate.input"

   Then format date "${sa_scheduledStart}" from "M/d/yyyy" to "dd MMMM yyyy" into "scheduledDateDatepickerFormat"
   And click on android View with content desc "${scheduledDateDatepickerFormat}"

   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"

   And sendKeys "${locationInfo}" into "serviceAppointment.newIncidentReport.closeCall.location.input"
   And sendKeys "${injuryInfo}" into "serviceAppointment.newIncidentReport.closeCall.injury.input"
   And scroll until the text "Describe the Close Call sequence of events. *" is on the screen
   And sendKeys "${damageInfo}" into "serviceAppointment.newIncidentReport.closeCall.damage.input"
   And sendKeys "${sequenceInfo}" into "serviceAppointment.newIncidentReport.closeCall.sequence.input"
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"

   Then assert android TextView is present with the text contains "Add Pictures"
   Then select option "Yes" for form input with name "Add Pictures"
   And click on "serviceAppointment.newIncidentReport.next"
   Then assert android TextView is present with the text contains "Take Pictures"
   And click on unclickable TextView with text "Take Pictures"

   Then wait until "sharinPix.takePhoto.button" to be present
   And wait until "sharinPix.takePhoto.button" to be enable
   And click on "sharinPix.takePhoto.button"

   Then wait until "sharinPix.uploadPhoto.button" to be present
   And wait until "sharinPix.uploadPhoto.button" to be enable
   And click on "sharinPix.uploadPhoto.button"

   Then assert android TextView is present with the text contains "Take Pictures"
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"
   And wait until "serviceAppointment.finish.button" to be enable
   And click on "serviceAppointment.finish.button"
   
   And wait until "serviceAppointment.details.related.button" to be enable
   And click on "serviceAppointment.details.related.button"
   And scroll to end
   And wait until "serviceAppointment.details.related.incidentReports.button" to be enable
   And take a screenshot
   And click on "serviceAppointment.details.related.incidentReports.button"
   And wait until "serviceAppointment.incidentReports.first" to be enable
   And assert "serviceAppointment.incidentReports.first" is present
   And take a screenshot
   And click on "serviceAppointment.incidentReports.first"

   And wait until "incidentReport.details.incidentReportNumber.text" to be enable
   And assert "incidentReport.details.incidentReportNumber.text" text is not ""
   And store text from "incidentReport.details.incidentReportNumber.text" into "incidentReportNumber"
   And take a screenshot

   And scroll until "incidentReport.details.incidentType.text" is visible
   And assert "incidentReport.details.incidentType.text" text is not ""
   And scroll until "incidentReport.details.workOrder.text" is visible
   And assert "incidentReport.details.workOrder.text" text is not ""
   And scroll until "incidentReport.details.account.text" is visible
   And assert "incidentReport.details.account.text" text is not ""
   And take a screenshot
   And scroll until "incidentReport.details.case.text" is visible
   And assert "incidentReport.details.case.text" text is not ""
   And scroll until "incidentReport.details.serviceAppointment.text" is visible
   And assert "incidentReport.details.serviceAppointment.text" text is not ""
   And take a screenshot
   And scroll until "incidentReport.details.incidentLocation.text" is visible
   And assert "incidentReport.details.incidentLocation.text" text is not ""
   And scroll until "incidentReport.details.incidentDate.text" is visible
   And assert "incidentReport.details.incidentDate.text" text is not ""
   And take a screenshot
   And scroll until "incidentReport.details.employeeInjury.text" is visible
   And assert "incidentReport.details.employeeInjury.text" text is not ""
   And scroll until "incidentReport.details.vehiclePropertyDamage.text" is visible
   And assert "incidentReport.details.vehiclePropertyDamage.text" text is not ""
   And take a screenshot
   And scroll until "incidentReport.details.incidentTimeline.text" is visible for up to 30 scrolls
   And assert "incidentReport.details.incidentTimeline.text" text is not ""
   And take a screenshot

   #Change to web
   Then store "resource/testdata;resources/web" into "env.resources"
   And set current platform as "web"
   Given login to salesforce with "${username}" and "${password}"
   Then ShrdChangeLoggedInUser "test_ops_center_operator"
   Then close all open web tabs

   Then go to case "${generated_caseNumber}"

   Then wait until "cases.quickLinks.serviceAppointments" to be present
   And wait until "cases.quickLinks.serviceAppointments" to be enable
   And click on "cases.quickLinks.serviceAppointments"

   Then wait until "serviceAppointments.table.first.link" to be present
   And wait until "serviceAppointments.table.first.link" to be enable
   And click on "serviceAppointments.table.first.link"

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

   Then scroll until "incidentReports.details.incidentReportNumber" is visible
   Then assert "incidentReports.details.incidentReportNumber" text is "${incidentReportNumber}"
   And assert "incidentReports.details.incidentType" text is "Close Call"

   Then scroll until "incidentReports.details.serviceAppointment.link.text" is visible
   And assert "incidentReports.details.workOrder.link.text" text is "${generated_workOrder}"
   And assert "incidentReports.details.account.link.text" text is "${accountName}"
   And assert "incidentReports.details.case.link.text" text is "${generated_caseNumber}"
   And assert "incidentReports.details.serviceAppointment.link.text" text is "${generated_serviceAppointment}"

   Then scroll until "incidentReports.details.incidentLocation" is visible
   And assert "incidentReports.details.incidentLocation" text is "${locationInfo}"
   And assert "incidentReports.details.incidentDate" contains the text "${sa_scheduledStart}"
   And take a screenshot

   Then scroll until "incidentReports.details.employeeInjury1" is visible
   And assert "incidentReports.details.employeeInjury1" text is "${injuryInfo}"
   And take a screenshot

   Then scroll until "incidentReports.details.vehiclePropertyDamage" is visible
   And assert "incidentReports.details.vehiclePropertyDamage" text is "${damageInfo}"
   And take a screenshot

   Then scroll until "incidentReports.details.incidentTimeline" is visible
   And assert "incidentReports.details.incidentTimeline" text is "${sequenceInfo}"
   And take a screenshot
