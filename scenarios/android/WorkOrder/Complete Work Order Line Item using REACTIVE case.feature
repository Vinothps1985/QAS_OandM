Feature: WorkOrders

@author:Rodrigo Montemayor
@description:Verify the ability to complete Work Order Line Item flow via FSL using a REACTIVE case
@workorder @positive @mobile
@dataFile:resources/testdata/WorkOrder/Complete Work Order Line Item using REACTIVE case.csv

Scenario: Complete Work Order Line Item using REACTIVE case
	
   Given sendKeys "${passcode}" into "login.passcode.input"

   #Temp data - to reuse from service optimization process
   And store "00290773" into "generated_caseNumber"
   And store "SA-38508" into "generated_serviceAppointment"
   And store "11/15/2021" into "nextBusinessDay"

   And click on "home.popup.useLocation.notYet.option" if it appears within 10000 milisec

   And wait until "schedule.date.icon" to be enable
   And click on "schedule.date.icon"

   Then format date "${nextBusinessDay}" from "M/d/yyyy" to "EEE, MMM dd" into "nextBusinessDayAndroidFormat"

   And click the date "${nextBusinessDayAndroidFormat}" in the scheduler datepicker

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

   Then select option "Sunny" for form input with name "Weather Conditions"
   Then wait until "woLineItem.complete.vegetation.select" to be enable
   And click on "woLineItem.complete.vegetation.select"
   And select option that contains "24" for form input with name "Vegetation"
   And scroll to end
   And select option "Light" for form input with name "Soiling"
   And wait until "woLineItem.complete.next.button" to be enable
   And click on "woLineItem.complete.next.button"

   And answer the work order line item checklist until the question "Was the asset offline?" appears

   And select option "No" for form input with name "Was the asset offline?"
   And wait until "woLineItem.complete.next.button" to be enable
   And click on "woLineItem.complete.next.button"

   And select option "Failed DC Wire" for form input with name "Fault"
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
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And wait until "woLineItem.complete.workEndTime.datepicker" to be enable
   And click on "woLineItem.complete.workEndTime.datepicker"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And scroll to end
   And wait until "woLineItem.complete.comments.input" to be enable
   And click on "woLineItem.complete.comments.input"
   And sendKeys "The comments" into "woLineItem.complete.comments.input"
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
   
   And click the date "${nextBusinessDayAndroidFormat}" in the scheduler datepicker
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

   And select option "No" for form input with name "Was the asset offline?"
   And wait until "woLineItem.complete.next.button" to be enable
   And click on "woLineItem.complete.next.button"

   And select option "External Damage" for form input with name "Fault"
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
   And sendKeys "The comments 2" into "woLineItem.complete.comments.input"
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
   And click the date "${nextBusinessDayAndroidFormat}" in the scheduler datepicker
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