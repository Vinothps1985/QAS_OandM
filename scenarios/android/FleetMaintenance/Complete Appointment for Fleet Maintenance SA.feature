Feature: FleetMaintenance

@author:Rodrigo Montemayor
@description:Verify whether user is able to COMPLETE the Appointment via FS Mobile for a given Fleet Maintenance Service Appointment
@fleetmaintenance @positive @mobile 
@dataFile:resources/testdata/FleetMaintenance/Complete Appointment for Fleet Maintenance SA.csv

Scenario: Complete Appointment for a given Fleet Maintenance Service Appointment

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
   And type Enter "serviceAppointments.details.scheduledEnd.edit.input"

   Then click on "serviceAppointments.details.edit.save.button"
   And wait until "serviceAppointments.details.scheduledStart.edit.button" to be visible

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

   #### START DATASYNC
   And wait until "home.menu.profile.button" to be enable
   And click on "home.menu.profile.button"
   And wait until "profile.settings.icon" to be enable
   And click on "profile.settings.icon"
   And wait until "settings.dataSync.button" to be enable
   And click on "settings.dataSync.button"
   
   #Do the datasync
   And wait until "dataSync.sync.button" to be enable
   And click on "dataSync.sync.button"
   And wait for 10000 milisec
   And wait for 5 minutes for "dataSync.cancelSync.button" to be not present

   And wait until "dataSync.syncComplete.dismiss.button" to be enable
   And click on "dataSync.syncComplete.dismiss.button"
   And go back until "home.menu.profile.button" is present
   And wait until "home.menu.schedule.button" to be enable
   And click on "home.menu.schedule.button"
   #### END DATASYNC

   And wait until "schedule.date.icon" to be enable
   And click on "schedule.date.icon"

   Then format date "${nextBusinessDay}" from "M/d/yyyy" to "EEE, MMM dd" into "nextBusinessDayAndroidFormat"

   And click the date "${nextBusinessDayAndroidFormat}" in the scheduler datepicker

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
   Then change logged in user to "test_ops_center_operator"

   Then get "${generated_workOrderURL}"
   Then wait until "workOrders.details.workOrderNumber" to be enable
   And wait until "workOrders.details.workOrderNumber" to be visible

   And scroll until "workOrders.timeSheetEntries.link" is visible
   And take a screenshot
   And click on "workOrders.timeSheetEntries.link"
   And assert "timeSheetEntries.table.firstResult.link" is present
   And take a screenshot