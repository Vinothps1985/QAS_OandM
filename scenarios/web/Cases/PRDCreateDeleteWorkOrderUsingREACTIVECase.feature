Feature: Cases

@author:Anusha MG
@description:Verify user is able to create and delete Work Order Hybrid using a REACTIVE case
@case @positive @workorder @smoke
@dataFile:resources/testdata/Cases/Create Work Order using REACTIVE case.csv
Scenario: Create and Delete Work Order using REACTIVE case
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   #Then close all open web tabs
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot
   And create a work order with data "${generated_caseNumber}" "${assetType1}" "${assetType2}"

   #Confirm and screenshot work order lines created
   Then wait until "cases.quickLinks.workOrderLineItems" to be enable
   And store the current url in "caseURL"
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
   Then click on "woLineItems.details.case.link"

   # Delete WO - Line Items, WO and SA
   Then wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"
   Then wait until "woLineItems.table.firstResult.link" to be enable
   And click on "woLineItems.table.firstResult.link"

   Then wait until "woLineItems.details.workOrder.link" to be enable
   And wait until "woLineItems.deleteWOLine.delete.button" to be visible
   And click on "woLineItems.deleteWOLine.delete.button"
   And wait until "woLineItems.deleteWOLine.popup.title.text" to be visible
   Then assert "woLineItems.deleteWOLine.popup.title.text" text is "Delete Work Order Line Item"
   And wait until "woLineItems.deleteWOLine.popup.delete.button" to be visible
   And click on "woLineItems.deleteWOLine.popup.delete.button"
   And wait until "woLineItems.deleteWOLine.delete.text" to be visible
   Then assert "woLineItems.deleteWOLine.delete.text" text is "Work Order Line Item \"00000001\" was deleted. Undo"
   And take a screenshot
   
   Then get "${caseURL}"
   And wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"
   Then wait until "woLineItems.table.firstResult.link" to be enable
   And click on "woLineItems.table.firstResult.link"

   Then wait until "woLineItems.details.workOrder.link" to be enable
   And wait until "woLineItems.deleteWOLine.delete.button" to be visible
   And click on "woLineItems.deleteWOLine.delete.button"
   And wait until "woLineItems.deleteWOLine.popup.title.text" to be visible
   Then assert "woLineItems.deleteWOLine.popup.title.text" text is "Delete Work Order Line Item"
   And wait until "woLineItems.deleteWOLine.popup.delete.button" to be visible
   And click on "woLineItems.deleteWOLine.popup.delete.button"
   And wait until "woLineItems.deleteWOLine.delete.text" to be visible
   Then assert "woLineItems.deleteWOLine.delete.text" text is "Work Order Line Item \"00000002\" was deleted. Undo"
   And take a screenshot

   Then get "${caseURL}"
   And wait until "cases.quickLinks.workOrders" to be enable
   And click on "cases.quickLinks.workOrders"
   Then wait until "workOrders.table.firstResult.link" to be enable
   And click on "workOrders.table.firstResult.link"
   
   Then wait until "workOrders.details.case" to be enable
   And wait until "workOrders.deleteWorkOrder.showMoreActions.link" to be visible
   And click on "workOrders.deleteWorkOrder.showMoreActions.link"
   And wait until "workOrders.deleteWorkOrder.delete.link" to be visible
   And click on "workOrders.deleteWorkOrder.delete.link"
   And wait until "workOrders.deleteWorkOrder.popup.title.text" to be visible
   Then assert "workOrders.deleteWorkOrder.popup.title.text" text is "Delete Work Order"
   And wait until "workOrders.deleteWorkOrder.popup.delete.button" to be visible
   And click on "workOrders.deleteWorkOrder.popup.delete.button"
   And wait until "workOrders.deleteWorkOrder.delete.text" to be visible
   Then assert "workOrders.deleteWorkOrder.delete.text" text is "Work Order \"${generated_workOrder}\" was deleted. Undo"
   And take a screenshot

   Then get "${caseURL}"
   And wait until "cases.quickLinks.serviceAppointments" to be enable
   And assert "cases.quickLinks.serviceAppointments" text is "Service Appointments (0)"
   And assert "cases.quickLinks.workOrders" text is "Work Orders (0)"
   And assert "cases.quickLinks.workOrderLineItems" text is "Work Order Line Items (0)"
   And take a screenshot

   And wait until "case.deleteCase.showMoreActions.button" to be visible
   And click on "case.deleteCase.showMoreActions.button"
   And wait until "case.deleteCase.delete.link" to be visible
   And click on "case.deleteCase.delete.link"
   And wait until "case.deleteCase.popup.title.text" to be visible
   Then assert "case.deleteCase.popup.title.text" text is "Delete Case"
   And wait until "case.deleteCase.popup.delete.button" to be visible
   And click on "case.deleteCase.popup.delete.button"
   And wait until "case.deleteCase.delete.text" to be visible
   Then assert "case.deleteCase.delete.text" text is "Case \"${generated_caseNumber}\" was deleted. Undo"
   And take a screenshot
   