Feature: Cases

@author:Anusha MG
@description:Verify whether user is able to create and Delete a case of record type REACTIVE
@case @positive @smoke
@dataFile:resources/testdata/Cases/Create REACTIVE case.csv
Scenario: Create and Delete REACTIVE case
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   Then close all open web tabs
   And launch salesforce app "cases"
   Then create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"

   #Assertions
   Then assert "case.entityName" text is "Case"
   And wait until "case.priority" to be present
   And take a screenshot
   And assert "case.details.caseOwner" text is "${caseOwnerName}"
   And assert "cases.quickLinks.serviceAppointments" text is "Service Appointments (0)"
   And assert "cases.quickLinks.workOrders" text is "Work Orders (0)"
   And assert "cases.quickLinks.workOrderLineItems" text is "Work Order Line Items (0)"
   And assert "case.priority" text is "${casePriority}"
   And assert "case.caseOrigin" text is "${caseOrigin}"
   And assert "case.reportedIssue" text is "${reportedIssue}"
   And assert "case.caseCause" text is "${caseCause}"
   And scroll until "case.details.subject" is visible
   And take a screenshot
   And assert "case.details.subject" text is "${subject}"
   And assert "case.details.description" text is "${caseDescription}"

   #Delete Case
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