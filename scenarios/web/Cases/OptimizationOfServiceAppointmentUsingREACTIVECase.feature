Feature: Cases

@author:Rodrigo Montemayor
@description:Verify the optimization of service appointment using a REACTIVE case
@case @positive @smoke
@dataFile:resources/testdata/Cases/Optimization of service appointment using REACTIVE case.csv
@requirementKey=QTM-RQ-23
Scenario: Optimization of service appointment using REACTIVE case
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   Then close all open web tabs
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And create a work order with data "${generated_caseNumber}" "${assetType1}" "${assetType2}"

   And take a screenshot

   #Change the status of the case from New case to Deployment Review and click on Save
   And wait until "cases.details.status.edit.button" to be present
   When wait until "cases.details.status.edit.button" to be enable
   And store the current url in "caseURL"
   And click on "cases.details.status.edit.button"
   Then wait until "cases.details.status.edit.input" to be present
   When wait until "cases.details.status.edit.input" to be enable
   And click on "cases.details.status.edit.input"
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
   And store text from "serviceAppointments.table.first.link" into "serviceAppointment"
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

   Then wait until "cases.quickLinks.workOrders" to be enable
   And click on "cases.quickLinks.workOrders"

   Then wait until "workOrders.table.firstResult.link" to be present
   And wait until "workOrders.table.firstResult.link" to be enable
   And click on "workOrders.table.firstResult.link"

   Then wait until "workOrders.details.dueDate.edit.button" to be present
   And wait until "workOrders.details.dueDate.edit.button" to be enable
   And click on "workOrders.details.dueDate.edit.button"

   And store next business day into "nextBusinessDay" in format "M/d/yyyy"

   Then wait until "workOrders.details.dueDate.edit.input" to be enable
   And wait until "workOrders.details.dueDate.edit.input" to be visible
   Then clear "workOrders.details.dueDate.edit.input"
   And sendKeys "${nextBusinessDay}" into "workOrders.details.dueDate.edit.input"

   #And scroll until "workOrders.details.startDate.edit.input" is visible
   #And wait until "workOrders.details.startDate.edit.input" to be enable
   #And clear "workOrders.details.startDate.edit.input"
   #And sendKeys "${nextBusinessDay}" into "workOrders.details.startDate.edit.input"

   #This would be the code to select the 'tomorrow' option
   #Then wait until "workOrders.details.dueDate.datePicker.icon" to be visible
   #And wait until "workOrders.details.dueDate.datePicker.icon" to be enable
   #And click on "workOrders.details.dueDate.datePicker.icon"
   
   #Then wait until "common.openCalendar.tomorrow" to be visible
   #And wait until "common.openCalendar.tomorrow" to be enable
   #And click on "common.openCalendar.tomorrow"

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
   And assert "serviceAppointments.details.earliestStartPermitted" contains the text "${dueDate}"
   And take a screenshot
   And click on "serviceAppointments.details.case.link"

   #Change case status to Ready to Schedule
   Then wait until "cases.details.status.edit.button" to be present
   When wait until "cases.details.status.edit.button" to be enable
   And click on "cases.details.status.edit.button"
   Then wait until "cases.details.status.edit.input" to be present
   When wait until "cases.details.status.edit.input" to be enable
   And click on "cases.details.status.edit.input"
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
   #Return
   And click on "serviceAppointments.details.case.link"

   #Assert case owner is O&M Ops Center
   And wait until "case.details.caseOwner" to be visible
   And wait until "case.details.caseOwner" to be enable
   And assert "case.details.caseOwner" text is "${caseOwnerReadyToSchedule}"
   And take a screenshot

   And launch salesforce app "Field Service"

   And wait until "fieldService.iframe" for a max of 60 and min of 10 seconds to be present
   And wait until "fieldService.iframe" to be enable
   And switch to frame "fieldService.iframe"
   And wait until "fieldService.selectTimeline.button" to be present
   When wait until "fieldService.selectTimeline.button" to be enable
   And click on "fieldService.selectTimeline.button"
   #Then wait until "fieldService.selectTimeline.option.2days" to be visible
   #When wait until "fieldService.selectTimeline.option.2days" to be enable
   #And click on "fieldService.selectTimeline.option.2days"
   Then wait until "fieldService.selectTimeline.option.weekly" to be visible
   When wait until "fieldService.selectTimeline.option.weekly" to be enable
   And click on "fieldService.selectTimeline.option.weekly"
   
   Then wait until "fieldService.matchGanttDates.checkbox" to be enable
   And check angular checkbox "fieldService.matchGanttDates.checkbox" if not checked
   And wait until "fieldService.searchServiceAppointments.input" to be enable
   And sendKeys "${serviceAppointment}" into "fieldService.searchServiceAppointments.input"
   #And wait for 3000 milisec
   And wait until "fieldService.serviceAppointmentsList.firstOption" to be enable
   And wait until "fieldService.serviceAppointmentsList.firstOption.serviceAppointmentID" text "${serviceAppointment}"
   And click on "fieldService.serviceAppointmentsList.firstOption"
   Then wait until "fieldService.serviceAppointmentsList.firstOption.details.account" to be visible
   And assert "fieldService.serviceAppointmentsList.firstOption.details.account" is visible
   And assert "fieldService.serviceAppointmentsList.firstOption.details.dueDate" contains the text "${dueDate}"
   And take a screenshot

   When wait until "fieldService.serviceAppointmentsList.firstOption.checkbox" to be enable
   And click on "fieldService.serviceAppointmentsList.firstOption.checkbox"
   And wait until "fieldService.serviceAppointmentsList.actions.button" to be enable
   And click on "fieldService.serviceAppointmentsList.actions.button"
   Then wait until "fieldService.serviceAppointmentsList.actions.optimize.button" to be present
   When wait until "fieldService.serviceAppointmentsList.actions.optimize.button" to be enable
   And click on "fieldService.serviceAppointmentsList.actions.optimize.button"

   Then wait until "fieldService.optimization.popup.territories.firstOption.checkbox" to be visible
   When wait until "fieldService.optimization.popup.territories.firstOption.checkbox" to be enable
   And click on "fieldService.optimization.popup.territories.firstOption.checkbox"
   And select "label=Ready to Schedule" in "fieldService.optimization.popup.filterServicesBy.select"
   And wait until "fieldService.optimization.popup.optimize.button" to be enable
   And click on "fieldService.optimization.popup.optimize.button"

   Then wait until "fieldService.optimizationRequests.badge" to be visible
   When wait until "fieldService.notifications.button" to be enable
   And click on "fieldService.notifications.button"
   Then wait until "fieldService.notifications.popup.firstOption" to be visible
   When wait until "fieldService.notifications.popup.firstOption" to be enable
   And click on "fieldService.notifications.popup.firstOption"
   Then switch to default window 
   Then wait until "optimizationRequests.details.status" to be visible
   And take a screenshot
   #In Fullcopy it usually takes about 3-5 refreshes (30-50 seconds) to change to "Completed"
   And wait 10000 milisec up to 10 times until "optimizationRequests.details.status" has the text "Completed"
   And assert "optimizationRequests.details.objectsScheduled" text is not "0"
   And assert "optimizationRequests.details.failureReason" text is ""
   And take a screenshot

   #Assign to the user Test Technician
   Then get "${serviceAppointmentURL}"

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

   And launch salesforce app "Field Service"
   And wait until "fieldService.iframe" for a max of 60 seconds to be present
   And wait until "fieldService.iframe" to be enable
   And take a screenshot

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