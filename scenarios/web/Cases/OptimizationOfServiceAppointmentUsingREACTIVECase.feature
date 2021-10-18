Feature: Cases

@author:Rodrigo Montemayor
@description:Verify the optimization of service appointment using a REACTIVE case
@case @positive @smoke
@dataFile:resources/testdata/Cases/Optimization of service appointment using REACTIVE case.csv
@requirementKey=QTM-RQ-23
Scenario: Optimization of service appointment using REACTIVE case
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And ShrdLaunchApp "cases"
   When wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${reactiveCaseNumber}" into "common.searchAssistant.input"
   Then wait until "cases.search.firstResult" to be visible
   When wait until "cases.search.firstResult" to be enable
   And click on "cases.search.firstResult"
   Then wait until "cases.caseNumber" to be visible
   Then wait for 2000 milisec
   And wait until "cases.details.status.edit.button" to be present
   When wait until "cases.details.status.edit.button" to be enable
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
   When wait until "cases.quickLinks.workOrders" to be enable
   And click on "cases.quickLinks.workOrders"
   Then wait until "workOrders.table.firstResult.link" to be present
   When wait until "workOrders.table.firstResult.link" to be enable
   And click on "workOrders.table.firstResult.link"
   Then wait until "workOrders.details.dueDate.edit.button" to be present
   When wait until "workOrders.details.dueDate.edit.button" to be enable
   And click on "workOrders.details.dueDate.edit.button"
   Then wait until "workOrders.details.dueDate.datePicker.icon" to be visible
   When wait until "workOrders.details.dueDate.datePicker.icon" to be enable
   And click on "workOrders.details.dueDate.datePicker.icon"
   Then wait until "common.openCalendar.tomorrow" to be visible
   When wait until "common.openCalendar.tomorrow" to be enable
   And click on "common.openCalendar.tomorrow"
   And wait until "workOrders.details.save.button" to be enable
   And click on "workOrders.details.save.button"
   Then wait until "workOrders.details.dueDate.edit.button" to be visible
   When wait until "workOrders.details.case" to be enable
   And click on "workOrders.details.case"
   Then wait until "cases.details.status.edit.button" to be present
   When wait until "cases.details.status.edit.button" to be enable
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
   And ShrdLaunchApp "Field Service"
   And  wait for 8000 milisec
   And wait until "fieldService.iframe" to be present
   And switch to frame "fieldService.iframe"
   And wait until "fieldService.selectTimeline.button" to be present
   When wait until "fieldService.selectTimeline.button" to be enable
   And click on "fieldService.selectTimeline.button"
   Then wait until "fieldService.selectTimeline.option.2days" to be visible
   When wait until "fieldService.selectTimeline.option.2days" to be enable
   And click on "fieldService.selectTimeline.option.2days"
   And wait for 2000 milisec
   And wait until "fieldService.searchServiceAppointments.input" to be enable
   And sendKeys "SA-38086" into "fieldService.searchServiceAppointments.input"
   And wait for 3000 milisec
   And wait until "fieldService.serviceAppointmentsList.firstOption" to be enable
   And click on "fieldService.serviceAppointmentsList.firstOption"
   Then wait until "fieldService.serviceAppointmentsList.firstOption.details.account" to be visible
   And assert "fieldService.serviceAppointmentsList.firstOption.details.account" is visible
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
   When switchWindow "1"
   Then wait until "optimizationRequests.details.status" to be visible
   And assert "optimizationRequests.details.status" text is "Completed"
   



