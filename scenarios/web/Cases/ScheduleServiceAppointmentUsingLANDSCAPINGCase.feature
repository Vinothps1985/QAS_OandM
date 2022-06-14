Feature: Cases

@author:Anusha MG
@description:Schedule a service appointment using a Landscaping case
@case @positive @regression @schedulesa
@dataFile:resources/testdata/Cases/Schedule a service appointment using Landscaping case.csv
Scenario: Schedule a service appointment using Landscaping case
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   Then close all open web tabs
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And create a work order with data "${generated_caseNumber}" "${assetType1}" "${assetType2}"

   #Verify Service Appointment status
   Then wait until "cases.quickLinks.serviceAppointments" to be enable
   And take a screenshot

   And click on "cases.quickLinks.serviceAppointments"
   And wait until "serviceAppointments.table.first.link" to be enable
   And click on "serviceAppointments.table.first.link"
   
   And wait until "serviceAppointments.details.status" to be enable
   And assert "serviceAppointments.details.status" text is "None"
   And take a screenshot
   And click on "serviceAppointments.details.case.link"
   Then wait until "cases.quickLinks.workOrders" to be enable

   #Change the status of the case from New case to Deployment Review and click on Save
   And wait until "cases.details.status.edit.button" to be present
   When wait until "cases.details.status.edit.button" to be enable
   And store the current url in "caseURL"
   And click on "cases.details.status.edit.button"
   Then wait until "cases.details.status.edit.input" to be present
   When wait until "cases.details.status.edit.input" to be enable
   And wait for 2000 milisec
   And scroll until "cases.details.status.edit.input" is visible
   And click on "cases.details.status.edit.input"
   And wait for 2000 milisec
   Then wait until "cases.details.status.edit.deploymentReview.option" to be present
   When wait until "cases.details.status.edit.deploymentReview.option" to be enable
   And click on "cases.details.status.edit.deploymentReview.option"
   And wait until "cases.details.edit.save.button" to be enable
   And click on "cases.details.edit.save.button"
   Then wait until "cases.details.status.edit.button" to be visible

   #Refresh and verify status of service appointment
   Then Execute Java Script with data "window.location.reload();"
   
   ##Go to service appointments to get the SA number and check status is None
   And wait until "cases.quickLinks.serviceAppointments" to be present
   And click on "cases.quickLinks.serviceAppointments"
   And wait until "serviceAppointments.table.first.link" to be present
   And store text from "serviceAppointments.table.first.link" into "generated_serviceAppointment"
   And click on "serviceAppointments.table.first.link"
   And wait until "serviceAppointments.details.status" to be visible
   And wait until "serviceAppointments.details.status" to be enable
   And assert "serviceAppointments.details.status" text is "None"
   And store the current url in "serviceAppointmentURL"
   And take a screenshot
   #Return
   And click on "serviceAppointments.details.case.link"

   #Assert case owner is area supervisor
   And wait until "case.details.caseOwner.link.value" to be visible
   And wait until "case.details.caseOwner.link.value" to be enable
   And assert "case.details.caseOwner.link.value" text is "${caseOwnerDeploymentReview}"
   And take a screenshot

   #Update case owner, wait for few seconds and verify if it's updated
   And update case owner "${caseOwnerUpdate}"
   And wait for 20000 milisec
   Then get "${caseURL}"
   And wait until "case.details.caseOwner.link.value" to be visible
   And wait until "case.details.caseOwner.link.value" to be enable
   And scroll until "case.details.caseOwner.link.value" is visible
   And assert "case.details.caseOwner.link.value" text is "${caseOwnerUpdate}"
   And take a screenshot

   Then wait until "cases.quickLinks.workOrders" to be enable
   And click on "cases.quickLinks.workOrders"

   Then wait until "workOrders.table.firstResult.link" to be present
   And wait until "workOrders.table.firstResult.link" to be enable
   And click on "workOrders.table.firstResult.link"

   And scroll until "workOrders.details.dueDate" is visible
   Then wait until "workOrders.details.dueDate.edit.button" to be present
   And wait until "workOrders.details.dueDate.edit.button" to be enable
   And click on "workOrders.details.dueDate.edit.button"

   #And store next business day into "nextBusinessDay" in format "M/d/yyyy"
   And add 1 business days to current date and store it into "nextBusinessDay" in format "M/d/yyyy"

   Then wait until "workOrders.details.dueDate.edit.input" to be enable
   And wait until "workOrders.details.dueDate.edit.input" to be visible
   Then clear "workOrders.details.dueDate.edit.input"
   And sendKeys "${nextBusinessDay}" into "workOrders.details.dueDate.edit.input"

   And wait until "workOrders.details.save.button" to be enable
   And click on "workOrders.details.save.button"
   Then wait until "workOrders.details.dueDate.edit.button" to be visible
   And take a screenshot
   And store text from "workOrders.details.dueDate" into "dueDate"
   And click on "workOrders.details.case"

   #Check service appointment for due date and earliest start permitted date to be changed
   Then get "${serviceAppointmentURL}"
   And wait until "serviceAppointments.details.dueDate" to be visible
   And wait until "serviceAppointments.details.dueDate" to be enable
   And scroll until "serviceAppointments.details.dueDate" is visible
   #Need to refresh several times because this dueDate (In Service Appointment) takes a while to get updated...
   And wait 10000 milisec up to 10 times until "serviceAppointments.details.dueDate" contains the text "${dueDate}"
   And scroll until "serviceAppointments.details.dueDate" is visible
   And take a screenshot
   And click on "serviceAppointments.details.case.link"

   #Change case status to Ready to Schedule
   Then wait until "cases.details.status.edit.button" to be present
   When wait until "cases.details.status.edit.button" to be enable
   And click on "cases.details.status.edit.button"
   Then wait until "cases.details.status.edit.input" to be present
   When wait until "cases.details.status.edit.input" to be enable
   And wait for 2000 milisec
   And scroll until "cases.details.status.edit.input" is visible
   And click on "cases.details.status.edit.input"
   And wait for 2000 milisec
   Then wait until "cases.details.status.edit.readyToSchedule.option" to be present
   When wait until "cases.details.status.edit.readyToSchedule.option" to be enable
   And click on "cases.details.status.edit.readyToSchedule.option"
   And wait until "cases.details.edit.save.button" to be enable
   And click on "cases.details.edit.save.button"
   Then wait until "cases.details.status.edit.button" to be visible

   #Refresh and verify status of service appointment
   Then Execute Java Script with data "window.location.reload();"
   
   #Go to service appointments to get the SA number and check status is None again
   And get "${serviceAppointmentURL}"
   And wait until "serviceAppointments.details.status" to be visible
   And wait until "serviceAppointments.details.status" to be enable
   And assert "serviceAppointments.details.status" text is "None"
   And take a screenshot

   #Verify that JHA is not created when the SA status is Not 'Scheduled'
   Then scroll until "serviceAppointment.jhas.icon.image" is visible
   And wait until "serviceAppointment.jhas.link" to be visible
   And wait until "serviceAppointment.jhas.link" to be enable
   And wait until "serviceAppointment.jhas.verifyJhaCreated" to be visible
   And wait until "serviceAppointment.jhas.verifyJhaCreated" to be enable
   And assert "serviceAppointment.jhas.verifyJhaCreated" text is "(0)"
   And take a screenshot

   #Return
   And click on "serviceAppointments.details.case.link"

   #Assert case owner is O&M Ops Center
   And wait until "case.details.caseOwner" to be visible
   And wait until "case.details.caseOwner" to be enable
   And assert "case.details.caseOwner" text is "${caseOwnerReadyToSchedule}"
   And take a screenshot

   And close all open web tabs
   
   And launch salesforce app "Field Service"
   And wait until "fieldService.iframe" for a max of 60 and min of 10 seconds to be present
   And wait until "fieldService.iframe" to be enable
   And switch to frame "fieldService.iframe"

   And wait for 10000 milisec
   And wait until "fieldService.predefinedFilterSelector.select" to be present
   #And wait until "fieldService.searchServiceAppointments.input" to be present
   #And wait until "fieldService.searchServiceAppointments.input" to be enable
   And select "label=All Service Appointments" in "fieldService.predefinedFilterSelector.select"
   And wait until "fieldService.searchServiceAppointments.input" to be enable
   And sendKeys "${generated_serviceAppointment}" into "fieldService.searchServiceAppointments.input"
   And wait for 3000 milisec
   And wait until "fieldService.searchServiceAppointments.searchAllRecords" to be present
   And click on "fieldService.searchServiceAppointments.searchAllRecords"
   And wait until "fieldService.serviceAppointmentsList.firstOption" to be enable
   And wait until "fieldService.serviceAppointmentsList.firstOption.serviceAppointmentID" text "${generated_serviceAppointment}"
   And click on "fieldService.serviceAppointmentsList.firstOption"
   Then wait until "fieldService.serviceAppointment.firstOption.schedule.button" to be visible
   And click on "fieldService.serviceAppointment.firstOption.schedule.button"
   And wait until "fieldService.loadingIndicator" to be visible
   Then wait until "fieldService.loadingIndicator" not to be visible

   #Validate case status to be Scheduled
   Then get "${caseURL}"
   And wait until "case.details.status" to be present
   And wait until "case.details.status" to be enable
   Then Execute Java Script with data "window.location.reload();"
   And wait until "case.details.status" to be present
   And wait until "case.details.status" to be enable
   And assert "case.details.status" text is "Scheduled"
   And take a screenshot

   #Validate service appointment to be scheduled
   And get "${serviceAppointmentURL}"
   And wait until "serviceAppointments.details.status" to be visible
   And wait until "serviceAppointments.details.status" to be enable
   And assert "serviceAppointments.details.status" text is "Scheduled"
   And take a screenshot

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

   #Verify that JHA is created when SA status is 'Scheduled'
   And wait for 3000 milisec
   Then scroll until "serviceAppointment.jhas.link" is visible
   And wait until "serviceAppointment.jhas.link" to be present
   And wait until "serviceAppointment.jhas.link" to be enable
   And assert "serviceAppointment.jhas.verifyJhaCreated" text is "(1)"
   Then scroll until "serviceAppointment.jhas.jhaNumber" is visible
   Then wait until "serviceAppointment.jhas.jhaNumber" to be present
   And wait until "serviceAppointment.jhas.jhaNumber" to be enable
   And take a screenshot
   And store text from "serviceAppointment.jhas.jhaNumber" into "jha_number"
   And click on "serviceAppointment.jhas.jhaNumber"
   And wait until "serviceAppointment.jha.details.status" to be enable
   And wait until "serviceAppointment.jha.details.status" to be visible
   And assert "serviceAppointment.jha.details.jhaNumber" text is "${jha_number}"
   And assert "serviceAppointment.jha.details.status" text is "Open"
   And assert "serviceAppointment.jha.details.owner" text is "${serviceAppointmentAssignee}"
   And assert "serviceAppointment.jha.jhaQuestions" text is "(3+)"
   And take a screenshot