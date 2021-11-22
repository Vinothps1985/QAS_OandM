Feature: WorkOrders

@author:Rodrigo Montemayor
@description:Verify the ability to complete Work Order Line Item flow via FSL using a REACTIVE case
@workorder @positive @mobile
@dataFile:resources/testdata/WorkOrder/Complete Work Order Line Item using REACTIVE case.csv

Scenario: Complete Work Order Line Item using REACTIVE case

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

   And wait until "schedule.date.icon" to be enable
   And click on "schedule.date.icon"

   Then format date "${sa_scheduledStart}" from "M/d/yyyy" to "EEE, MMM dd" into "scheduledDateAndroidFormat"

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

   And wait until "woLineItem.actions.completeWoLineItem.button" to be enable
   And click on "woLineItem.actions.completeWoLineItem.button"

   Then select option "${weatherConditions}" for form input with name "Weather Conditions"
   Then wait until "woLineItem.complete.vegetation.select" to be enable
   And click on "woLineItem.complete.vegetation.select"
   And select option that contains "${vegetation}" for form input with name "Vegetation"
   And scroll to end
   And select option "${soiling}" for form input with name "Soiling"
   And wait until "woLineItem.complete.next.button" to be enable
   And click on "woLineItem.complete.next.button"

   And answer the work order line item checklist until the question "Was the asset offline?" appears

   And select option "${assetOffline}" for form input with name "Was the asset offline?"
   And wait until "woLineItem.complete.next.button" to be enable
   And click on "woLineItem.complete.next.button"

   And select option "${fault}" for form input with name "Fault"
   And wait until "woLineItem.complete.next.button" to be enable
   And click on "woLineItem.complete.next.button"

   And wait until "woLineItem.complete.woLineItemStatus.completed.option" to be enable
   And click on "woLineItem.complete.woLineItemStatus.completed.option"
   And wait until "woLineItem.complete.travelStartTime.datepicker" to be enable
   And click on "woLineItem.complete.travelStartTime.datepicker"

   #Convert the date to the format the datepicker expects (e.g. 23 November 2021)
   Then format date "${sa_scheduledStart}" from "M/d/yyyy" to "dd MMMM yyyy" into "scheduledDateDatepickerFormat"
   And click on android View with content desc "${scheduledDateDatepickerFormat}"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And wait until "woLineItem.complete.workStartTime.datepicker" to be enable
   And click on "woLineItem.complete.workStartTime.datepicker"
   And click on android View with content desc "${scheduledDateDatepickerFormat}"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And wait until "woLineItem.complete.workEndTime.datepicker" to be enable
   And click on "woLineItem.complete.workEndTime.datepicker"
   And click on android View with content desc "${scheduledDateDatepickerFormat}"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And scroll to end
   And wait until "woLineItem.complete.comments.input" to be enable
   And click on "woLineItem.complete.comments.input"
   And sendKeys "${comments}" into "woLineItem.complete.comments.input"
   And scroll to end
   And wait until "woLineItem.complete.next.button" to be enable
   And click on "woLineItem.complete.next.button"

   Then wait until "woLineItem.complete.checklistCompleted.text" to be enable
   And click on "woLineItem.complete.next.button"

   Then wait until "woLineItem.complete.completeWOLICompleted.text" to be enable
   And take a screenshot
   And click on "woLineItem.complete.finish.button"

   And wait until "common.actions.button" to be enable
   And take a screenshot

   #Change to web
   Then store "resource/testdata;resources/web" into "env.resources"
   And set current platform as "web"
   Given login to salesforce with "${username}" and "${password}"
   Then ShrdChangeLoggedInUser "test_ops_center_operator"
   Then go to case "${generated_caseNumber}"

   Then wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"

   Then wait until "woLineItems.table.firstResult.link" to be enable
   And click on "woLineItems.table.firstResult.link"

   Then wait until "woLineItems.details.status" to be enable
   And wait until "woLineItems.details.status" to be visible

   Then assert "woLineItems.details.status" text is "Completed"
   And take a screenshot

   Then click on "woLineItems.details.workOrder.link"
   And wait until "workOrders.details.status" to be enable
   And wait until "workOrders.details.status" to be visible
   And assert "workOrders.details.status" text is "In Progress"
   And take a screenshot

   #Change to Android
   Then store "resource/testdata;resources/android" into "env.resources"
   And set current platform as "android"
   Given sendKeys "${passcode}" into "login.passcode.input"

   And click on "home.popup.useLocation.notYet.option" if it appears within 10000 milisec

   And wait until "schedule.date.icon" to be enable
   And click on "schedule.date.icon"
   
   And click the date "${scheduledDateAndroidFormat}" in the scheduler datepicker
   And scroll refresh for up to 60 seconds until "serviceAppointments.appointments.first" is present
   And wait until "serviceAppointments.appointments.first" to be enable
   And scroll until the text "${generated_serviceAppointment}" is on the screen
   And click on service appointment "${generated_serviceAppointment}"
   
   And wait until "common.RELATED.link" to be enable
   And click on "common.RELATED.link"

   And wait until "workOrder.related.workOrderLineItems.button" to be enable
   And click on "workOrder.related.workOrderLineItems.button"

   And wait until "workOrder.related.workOrderLineItems.second" to be enable
   And click on "workOrder.related.workOrderLineItems.second"

   And wait until "common.actions.button" to be enable
   And click on "common.actions.button"

   And wait until "woLineItem.actions.completeWoLineItem.button" to be enable
   And click on "woLineItem.actions.completeWoLineItem.button"

   And answer the work order line item checklist until the question "Was the asset offline?" appears

   And select option "${assetOffline2}" for form input with name "Was the asset offline?"
   And wait until "woLineItem.complete.next.button" to be enable
   And click on "woLineItem.complete.next.button"

   And select option "${fault2}" for form input with name "Fault"
   And wait until "woLineItem.complete.next.button" to be enable
   And click on "woLineItem.complete.next.button"

   And wait until "woLineItem.complete.woLineItemStatus.completed.option" to be enable
   And click on "woLineItem.complete.woLineItemStatus.completed.option"
   And wait until "woLineItem.complete.travelStartTime.datepicker" to be enable
   And click on "woLineItem.complete.travelStartTime.datepicker"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And wait until "woLineItem.complete.workStartTime.datepicker" to be enable
   And click on "woLineItem.complete.workStartTime.datepicker"
   #Again to re-set the existing time
   And wait until "woLineItem.complete.workStartTime.datepicker" to be enable
   And click on "woLineItem.complete.workStartTime.datepicker"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And wait until "woLineItem.complete.workEndTime.datepicker" to be enable
   And click on "woLineItem.complete.workEndTime.datepicker"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And scroll to end
   And wait until "woLineItem.complete.comments.input" to be enable
   And click on "woLineItem.complete.comments.input"
   And sendKeys "${comments2}" into "woLineItem.complete.comments.input"
   And scroll to end
   And wait until "woLineItem.complete.next.button" to be enable
   And click on "woLineItem.complete.next.button"

   Then wait until "woLineItem.complete.checklistCompleted.text" to be enable
   And click on "woLineItem.complete.next.button"

   Then wait until "woLineItem.complete.completeWOLICompleted.text" to be enable
   And take a screenshot
   And click on "woLineItem.complete.finish.button"

   And wait until "common.actions.button" to be enable
   And take a screenshot

   And click on "common.back.button" if it appears within 10000 milisec
   And take a screenshot

   And click on "common.back.button" if it appears within 10000 milisec
   And take a screenshot

   And click on "common.back.button" if it appears within 10000 milisec
   And take a screenshot

   #Return to web for assertions!
   Then store "resource/testdata;resources/web" into "env.resources"
   And set current platform as "web"
   Given login to salesforce with "${username}" and "${password}"
   Then ShrdChangeLoggedInUser "test_ops_center_operator"
   Then go to case "${generated_caseNumber}"

   Then wait until "case.details.caseOwner" to be enable
   And assert "case.details.caseOwner" text is "O&M Ops Center"
   And assert "case.details.status" text is "Ops Review"
   And take a screenshot

   Then click on "cases.quickLinks.serviceAppointments"
   And wait until "serviceAppointments.table.first.link" to be enable
   And click on "serviceAppointments.table.first.link"

   Then wait until "serviceAppointments.details.status" to be enable
   And wait until "serviceAppointments.details.status" to be visible
   And assert "serviceAppointments.details.status" text is "Completed"
   And take a screenshot
   Then click on "serviceAppointments.details.case.link"

   Then wait until "cases.quickLinks.workOrders" to be enable
   And click on "cases.quickLinks.workOrders"
   And wait until "workOrders.table.firstResult.link" to be enable
   And click on "workOrders.table.firstResult.link"
   And wait until "workOrders.details.status" to be enable
   And wait until "workOrders.details.status" to be visible
   And assert "workOrders.details.status" text is "Completed"
   And take a screenshot
   And click on "workOrders.details.case"

   Then wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"
   And wait until "woLineItems.table.firstResult.link" to be enable
   And click on "woLineItems.table.firstResult.link"
   And wait until "woLineItems.details.status" to be enable
   And wait until "woLineItems.details.status" to be visible
   And assert "woLineItems.details.status" text is "Completed"
   And take a screenshot
   And click on "woLineItems.details.case.link"
   Then wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"
   And wait until "woLineItems.table.secondResult.link" to be enable
   And click on "woLineItems.table.secondResult.link"
   And wait until "woLineItems.details.status" to be enable
   And wait until "woLineItems.details.status" to be visible
   And assert "woLineItems.details.status" text is "Completed"
   And take a screenshot
   And click on "woLineItems.details.case.link"

   #Time sheet entries check
   Then wait until "cases.quickLinks.workOrders" to be enable
   And click on "cases.quickLinks.workOrders"
   And wait until "workOrders.table.firstResult.link" to be enable
   And click on "workOrders.table.firstResult.link"
   And wait until "workOrders.details.status" to be enable
   And wait until "workOrders.details.status" to be visible
   And scroll until "workOrders.timeSheetEntries.link" is visible
   And click on "workOrders.timeSheetEntries.link"
   And wait until "timeSheetEntries.table.firstResult.link" to be enable
   And assert "timeSheetEntries.table.firstResult.link" is present
   And assert "timeSheetEntries.table.secondResult.link" is present
   And take a screenshot

   #Change to Android
   Then store "resource/testdata;resources/android" into "env.resources"
   And set current platform as "android"
   Given sendKeys "${passcode}" into "login.passcode.input"

   And click on "home.popup.useLocation.notYet.option" if it appears within 10000 milisec

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

   And wait until "schedule.date.icon" to be enable
   And click on "schedule.date.icon"
   And click the date "${scheduledDateAndroidFormat}" in the scheduler datepicker
   And scroll refresh for up to 60 seconds until "serviceAppointments.appointments.first" is present
   And wait until "serviceAppointments.appointments.first" to be enable
   And scroll until the text "${generated_serviceAppointment}" is on the screen
   And click on service appointment "${generated_serviceAppointment}"
   
   And wait until "common.RELATED.link" to be enable
   And click on "common.RELATED.link"

   Then wait until "workOrder.related.timeSheetEntries.button" to be enable
   And assert "workOrder.related.timeSheetEntries.text" text is "Time Sheet Entries - (2)"
   And take a screenshot
   Then click on "workOrder.related.timeSheetEntries.button"
   And take a screenshot