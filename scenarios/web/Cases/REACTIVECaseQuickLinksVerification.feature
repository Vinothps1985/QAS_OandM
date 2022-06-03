Feature: Cases

@author:Anusha MG
@description:Verify related list quick links on case page using a REACTIVE case
@case @positive @regression
@dataFile:resources/testdata/Cases/Verify case quick links using REACTIVE case.csv
Scenario: Verify case quick links using REACTIVE case
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   Then close all open web tabs
   And launch salesforce app "cases"
   Then create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"

   Then assert "case.entityName" text is "Case"
   And wait until "case.priority" to be present
   And take a screenshot
   
   #Assertions on related list quick links on case page
   And wait until "cases.relatedListQuickLinks.serviceAppointments.title" to be enable
   And scroll until "cases.relatedListQuickLinks.serviceAppointments.title" is visible
   And assert "cases.relatedListQuickLinks.serviceAppointments.title" text is "Service Appointments"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.workOrders.title" to be enable
   And scroll until "cases.relatedListQuickLinks.workOrders.title" is visible
   And assert "cases.relatedListQuickLinks.workOrders.title" text is "Work Orders"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.workOrderLineItems.title" to be enable
   And scroll until "cases.relatedListQuickLinks.workOrderLineItems.title" is visible
   And assert "cases.relatedListQuickLinks.workOrderLineItems.title" text is "Work Order Line Items"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.conReqGroups.title" to be enable
   And scroll until "cases.relatedListQuickLinks.conReqGroups.title" is visible
   And assert "cases.relatedListQuickLinks.conReqGroups.title" text is "Con Req Groups"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.purchaseOrders.title" to be enable
   And scroll until "cases.relatedListQuickLinks.purchaseOrders.title" is visible
   And assert "cases.relatedListQuickLinks.purchaseOrders.title" text is "Purchase Orders"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.billingLines.title" to be enable
   And scroll until "cases.relatedListQuickLinks.billingLines.title" is visible
   And assert "cases.relatedListQuickLinks.billingLines.title" text is "Billing Lines"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.productRequests.title" to be enable
   And scroll until "cases.relatedListQuickLinks.productRequests.title" is visible
   And assert "cases.relatedListQuickLinks.productRequests.title" text is "Product Requests"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.files.title" to be enable
   And scroll until "cases.relatedListQuickLinks.files.title" is visible
   And assert "cases.relatedListQuickLinks.files.title" text is "Files"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.notes.title" to be enable
   And scroll until "cases.relatedListQuickLinks.notes.title" is visible
   And assert "cases.relatedListQuickLinks.notes.title" text is "Notes"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.tasks.title" to be enable
   And scroll until "cases.relatedListQuickLinks.tasks.title" is visible
   And assert "cases.relatedListQuickLinks.tasks.title" text is "Tasks"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.activityHistory.title" to be enable
   And scroll until "cases.relatedListQuickLinks.activityHistory.title" is visible
   And assert "cases.relatedListQuickLinks.activityHistory.title" text is "Activity History"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.caseHistory.title" to be enable
   And scroll until "cases.relatedListQuickLinks.caseHistory.title" is visible
   And assert "cases.relatedListQuickLinks.caseHistory.title" text is "Case History"
   And take a screenshot

   And wait until "cases.relatedListQuickLinks.payableLines.title" to be enable
   And scroll until "cases.relatedListQuickLinks.payableLines.title" is visible
   And assert "cases.relatedListQuickLinks.payableLines.title" text is "Payable Lines"
   And take a screenshot 
   