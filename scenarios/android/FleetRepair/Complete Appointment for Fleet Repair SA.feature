Feature: FleetMaintenance

@author:Rodrigo Montemayor
@description:Verify whether user is able to COMPLETE the Appointment via FS Mobile for a given Fleet Repair Service Appointment
@fleetrepair @positive @mobile @repairme
@dataFile:resources/testdata/FleetRepair/Complete Appointment for Fleet Repair SA.csv

Scenario: Complete Appointment for a given Fleet Repair Service Appointment

   ##First create the testing data in web
   Given store "resource/testdata;resources/web" into "env.resources"
   And set current platform as "web"
   #Login and change user
   Then login to salesforce with "${username}" and "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"

   #Create a new asset of type truck to use
   Then create a random number with 6 digits and store it in "randomTruckNumber"
   And store "${truckName} - ${randomTruckNumber}" into "newTruckName"
   Then create an asset with data "Truck" "${assetType}" "${newTruckName}" "${account}"
   And take a screenshot

   #Create a work order from the asset page
   Then store next business day into "nextBusinessDay" in format "M/d/yyyy"
   And create a work order for the current asset with data "${workType}" "${nextBusinessDay}" "${woSubject}" "${woDescription}"

   And wait until "assets.workOrders.first.link" to be enable
   And wait until "assets.workOrders.first.link" to be visible
   And store text from "assets.workOrders.first.link" into "generated_workOrder"
   And click on "assets.workOrders.first.link"
   
   Then wait until "workOrders.details.workOrderNumber" to be enable
   And wait until "workOrders.details.workOrderNumber" to be visible
   And store the current url in "generated_workOrderURL"
   And click on "workOrders.serviceAppointments.link"
   
   Then wait until "serviceAppointments.table.first.link" to be enable
   And wait until "serviceAppointments.table.first.link" to be visible
   Then store text from "serviceAppointments.table.first.link" into "generated_serviceAppointment"
   And click on "serviceAppointments.table.first.link"

   Then wait until "serviceAppointments.details.status" to be enable
   And wait until "serviceAppointments.details.status" to be visible
   And store the current url in "generated_serviceAppointmentURL"

   And launch salesforce app "Field Service"
   And wait until "fieldService.iframe" for a max of 60 and min of 10 seconds to be present
   And wait until "fieldService.iframe" to be enable
   And switch to frame "fieldService.iframe"

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

   #Reload
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
   
   Then sendKeys "${passcode}" into "login.passcode.input"

   And click on "home.popup.useLocation.notYet.option" if it appears within 10000 milisec

   And wait until "schedule.date.icon" to be enable
   And click on "schedule.date.icon"

   Then format date "${sa_scheduledStart}" from "M/d/yyyy" to "EEE, MMM dd" into "scheduledDateAndroidFormat"

   And click the date "${scheduledDateAndroidFormat}" in the scheduler datepicker

   And scroll refresh for up to 60 seconds until "serviceAppointments.appointments.first" is present

   And wait until "serviceAppointments.appointments.first" to be enable
   And scroll until the text "${generated_serviceAppointment}" is on the screen
   And click on service appointment "${generated_serviceAppointment}"

   #Check the details
   And wait until "common.DETAILS.link" to be present
   And wait until "common.DETAILS.link" to be enable
   And click on "common.DETAILS.link"

   And scroll until "serviceAppointment.details.subject.text" is visible
   And assert "serviceAppointment.details.subject.text" text is "${woSubject}"

   And scroll until "serviceAppointment.details.description.text" is visible
   And assert "serviceAppointment.details.description.text" text is "${woDescription}"

   And take a screenshot

   And wait until "common.OVERVIEW.link" to be present
   And wait until "common.OVERVIEW.link" to be enable
   And click on "common.OVERVIEW.link"

   And wait until "common.actions.button" to be enable
   And click on "common.actions.button"

   And wait until "serviceAppointments.actions.fleetMaintenanceOrRepairCompleted" to be enable
   And click on "serviceAppointments.actions.fleetMaintenanceOrRepairCompleted"

   #For some reason we have to click twice this status option!
   And wait until "serviceAppointment.fleetMaintenanceComplete.status.select" to be present
   And wait until "serviceAppointment.fleetMaintenanceComplete.status.select" to be enable
   And click on "serviceAppointment.fleetMaintenanceComplete.status.select"
   #This is because this input has to be clicked twice for some reason... check?
   And wait for 2000 milisec 
   And click on "serviceAppointment.fleetMaintenanceComplete.status.select"
   And select option that contains "Completed" for form input with name "Status"
   And wait until "serviceAppointment.fleetMaintenanceComplete.travelStartTime.datepicker" to be enable
   And click on "serviceAppointment.fleetMaintenanceComplete.travelStartTime.datepicker"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And wait until "serviceAppointment.fleetMaintenanceComplete.workStartTime.datepicker" to be enable
   And click on "serviceAppointment.fleetMaintenanceComplete.workStartTime.datepicker"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And scroll to end
   And wait until "serviceAppointment.fleetMaintenanceComplete.workEndTime.datepicker" to be enable
   And click on "serviceAppointment.fleetMaintenanceComplete.workEndTime.datepicker"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And take a screenshot
   And wait until "serviceAppointment.fleetMaintenanceComplete.next.button" to be enable
   And click on "serviceAppointment.fleetMaintenanceComplete.next.button"

   And wait until "serviceAppointment.finish.button" to be present
   And wait until "serviceAppointment.finish.button" to be enable
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

   #Change to web
   Then store "resource/testdata;resources/web" into "env.resources"
   And set current platform as "web"
   Given login to salesforce with "${username}" and "${password}"
   Then ShrdChangeLoggedInUser "test_ops_center_operator"

   Then get "${generated_workOrderURL}"
   Then wait until "workOrders.details.workOrderNumber" to be enable
   And wait until "workOrders.details.workOrderNumber" to be visible

   And scroll until "workOrders.timeSheetEntries.link" is visible
   And take a screenshot
   And click on "workOrders.timeSheetEntries.link"
   And assert "timeSheetEntries.table.firstResult.link" is present
   And take a screenshot