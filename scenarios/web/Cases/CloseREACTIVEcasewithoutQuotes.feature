Feature: Cases

@author:Anusha MG
@description:Verify the ability to close a Case without Quotes using a REACTIVE case
@case @positive @regression
@dataFile:resources/testdata/Cases/Close REACTIVE case without quotes.csv
@requirementKey:QTM-RQ-23
Scenario: Close REACTIVE case without quotes
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot
   And create a work order with data "${generated_caseNumber}" "${assetType1}" "${assetType2}"
   And take a screenshot

   And set all service appointments of case "${generated_caseNumber}" to status "Completed"
   And take a screenshot

   And set all work orders of case "${generated_caseNumber}" to status "Completed"
   And take a screenshot

   And set all work order line items of case "${generated_caseNumber}" to status "Completed"
   And take a screenshot

   #Now close the case
   And close case "${generated_caseNumber}" with "Reactive Service"
   And take a screenshot

   And store text from "case.details.caseResolutionDate" into "caseResolutionDate"
   And format date "${caseResolutionDate}" from "M/d/yyyy" to "yyyy-MM-dd" into "caseResolutionDateFormatted"
   
   Then scroll until "cases.caseHistory" is visible
   And click on "cases.caseHistory"

   And take a screenshot
   Then scroll until "cases.caseHistory.newValue" is visible
   And take a screenshot
   And store table "cases.caseHistory.table" column index with title "Field" on "fieldIdx"
   And store table "cases.caseHistory.table" column index with title "New Value" on "newValIdx"

   And assert row exists on table "cases.caseHistory.table" where text on index "${fieldIdx}" is "Status" and text on index "${newValIdx}" is "Closed"
   And assert row exists on table "cases.caseHistory.table" where text on index "${fieldIdx}" is "Labor Billing" and text on index "${newValIdx}" is "Not Billable"
   And assert row exists on table "cases.caseHistory.table" where text on index "${fieldIdx}" is "Case Resolution Date" and text on index "${newValIdx}" is "${caseResolutionDateFormatted}"
   And assert row exists on table "cases.caseHistory.table" where text on index "${fieldIdx}" is "Billing Status" and text on index "${newValIdx}" is "Not Billable"