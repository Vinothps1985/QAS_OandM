Feature: Cases

@author:Anusha MG
@description:Verify related list quick links on work order page using a REACTIVE case
@case @positive @regression
@dataFile:resources/testdata/Cases/Verify work order quick links using REACTIVE case.csv
Scenario: Verify work order quick links using REACTIVE case
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   Then close all open web tabs
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot
   And create a work order with data "${generated_caseNumber}" "${assetType1}" "${assetType2}"

   #Confirm and screenshot work order lines created
   Then wait until "cases.quickLinks.workOrders" to be enable
   And click on "cases.quickLinks.workOrders"

   Then wait until "workOrders.table.firstResult.link" to be enable
   And click on "workOrders.table.firstResult.link"
   
   Then wait until "workOrders.details.case" to be enable
   And store text from "workOrders.details.workOrderNumber" into "generated_workOrder"
   And assert "workOrders.details.case" text is "${generated_caseNumber}"
   And assert "workOrders.details.status" text is "New"
   And assert "workOrders.details.workType.link" text is "Reactive"
   And take a screenshot

   #Assertions on quick links on work order page
   And wait until "workOrders.relatedList.serviceAppointments.title" to be enable
   And scroll until "workOrders.relatedList.serviceAppointments.title" is visible
   And assert "workOrders.relatedList.serviceAppointments.title" text is "Service Appointments"
   And take a screenshot

   And wait until "workOrders.relatedList.workOrderLineItems.title" to be enable
   And scroll until "workOrders.relatedList.workOrderLineItems.title" is visible
   And assert "workOrders.relatedList.workOrderLineItems.title" text is "Work Order Line Items"
   And take a screenshot

   And wait until "workOrders.relatedList.timeSheetEntries.title" to be enable
   And scroll until "workOrders.relatedList.timeSheetEntries.title" is visible
   And assert "workOrders.relatedList.timeSheetEntries.title" text is "Time Sheet Entries"
   And take a screenshot

   And wait until "workOrders.relatedList.productRequests.title" to be enable
   And scroll until "workOrders.relatedList.productRequests.title" is visible
   And assert "workOrders.relatedList.productRequests.title" text is "Product Requests"
   And take a screenshot

   And wait until "workOrders.relatedList.productsConsumed.title" to be enable
   And scroll until "workOrders.relatedList.productsConsumed.title" is visible
   And assert "workOrders.relatedList.productsConsumed.title" text is "Products Consumed"
   And take a screenshot

   And wait until "workOrders.relatedList.resourcePreferences.title" to be enable
   And scroll until "workOrders.relatedList.resourcePreferences.title" is visible
   And assert "workOrders.relatedList.resourcePreferences.title" text is "Resource Preferences"
   And take a screenshot

   And wait until "workOrders.relatedList.incidentReports.title" to be enable
   And scroll until "workOrders.relatedList.incidentReports.title" is visible
   And assert "workOrders.relatedList.incidentReports.title" text is "Incident Reports"
   And take a screenshot

   And wait until "workOrders.relatedList.jhas.title" to be enable
   And scroll until "workOrders.relatedList.jhas.title" is visible
   And assert "workOrders.relatedList.jhas.title" text is "JHAs"
   And take a screenshot

   And wait until "workOrders.relatedList.liveWorkPermits.title" to be enable
   And scroll until "workOrders.relatedList.liveWorkPermits.title" is visible
   And assert "workOrders.relatedList.liveWorkPermits.title" text is "Live Work Permits"
   And take a screenshot

   And wait until "workOrders.relatedList.truckInspections.title" to be enable
   And scroll until "workOrders.relatedList.truckInspections.title" is visible
   And assert "workOrders.relatedList.truckInspections.title" text is "Truck Inspections"
   And take a screenshot

   And wait until "workOrders.relatedList.activityHistory.title" to be enable
   And scroll until "workOrders.relatedList.activityHistory.title" is visible
   And assert "workOrders.relatedList.activityHistory.title" text is "Activity History"
   And take a screenshot

   And wait until "workOrders.relatedList.workOrderHistory.title" to be enable
   And scroll until "workOrders.relatedList.workOrderHistory.title" is visible
   And assert "workOrders.relatedList.workOrderHistory.title" text is "Work Order History"
   And take a screenshot
