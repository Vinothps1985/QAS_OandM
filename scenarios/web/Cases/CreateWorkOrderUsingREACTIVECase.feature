Feature: Cases

@author:Rodrigo Montemayor
@description:Verify user is able to create Work Order Hybrid using a REACTIVE case
@case @positive @workorder @smoke
@dataFile:resources/testdata/Cases/Create Work Order using REACTIVE case.csv
@requirementKey:OPERA-RQ-520
Scenario: Create Work Order using REACTIVE case
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   Then close all open web tabs
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot
   And create a work order with data "${generated_caseNumber}" "${assetType1}" "${assetType2}"

   #Confirm and screenshot work order lines created
   Then wait until "cases.quickLinks.workOrderLineItems" to be enable
   And take a screenshot
   And click on "cases.quickLinks.workOrderLineItems"
   Then wait until "woLineItems.table.firstResult.link" to be present
   And take a screenshot
   Then click on "breadcrumbs.second"

   Then wait until "cases.quickLinks.serviceAppointments" to be enable
   And click on "cases.quickLinks.serviceAppointments"
   And wait until "serviceAppointments.table.first.link" to be enable
   And click on "serviceAppointments.table.first.link"
   
   And wait until "serviceAppointments.details.status" to be enable
   And assert "serviceAppointments.details.status" text is "None"
   And take a screenshot
   And click on "serviceAppointments.details.case.link"

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
   Then click on "workOrders.details.case"

   #Assertions about the first work order line item
   Then wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"

   Then wait until "woLineItems.table.firstResult.link" to be enable
   And click on "woLineItems.table.firstResult.link"

   Then wait until "woLineItems.details.workOrder.link" to be enable
   And assert "woLineItems.details.workOrder.link" text is "${generated_workOrder}"
   And assert "woLineItems.details.status" text is "New"
   And take a screenshot
   Then wait until "woLineItems.details.assetType" to be enable
   And scroll until "woLineItems.details.assetType" is visible
   And assert "woLineItems.details.assetType" text is "${assetType1_realName}"
   And take a screenshot
   Then click on "woLineItems.details.case.link"

   #Assertions about the second work order line item
   Then wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"

   Then wait until "woLineItems.table.secondResult.link" to be enable
   And click on "woLineItems.table.secondResult.link"

   Then wait until "woLineItems.details.workOrder.link" to be enable
   And assert "woLineItems.details.workOrder.link" text is "${generated_workOrder}"
   And assert "woLineItems.details.status" text is "New"
   And take a screenshot
   Then wait until "woLineItems.details.assetType" to be enable
   And scroll until "woLineItems.details.assetType" is visible
   And assert "woLineItems.details.assetType" text is "${assetType2_realName}"
   And take a screenshot
