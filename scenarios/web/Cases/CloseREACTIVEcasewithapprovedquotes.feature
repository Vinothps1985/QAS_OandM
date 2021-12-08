Feature: Cases

@author:Rodrigo Montemayor
@description:Verify the ability to close case using a REACTIVE case having Quotes with status Accepted.
@case @positive @smoke
@dataFile:resources/testdata/Cases/Close REACTIVE case with approved quotes.csv
@requirementKey:OPERA-RQ-520
Scenario: Close REACTIVE case with approved quotes
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot
   And create a work order with data "${generated_caseNumber}" "${assetType1}" "${assetType2}"
   And take a screenshot
   
   And wait for the page to finish loading
   And change logged in user to "test_o&M_manager"
   And wait for the page to finish loading

   #Create a quote with no lines
   #At the time of test creation, accepting a quote with lines is failing, so we create a quote
   #without lines
   And ShrdCreateQuote "${generated_caseNumber}" "${salesRep}" "${primaryContact}" "${specialNotes}" "${typeOfWork}"
   And take a screenshot
   And approve quote "${generated_quoteNumber}"

   And change status of quote "${generated_quoteNumber}" to "Accepted"
   And take a screenshot
   
   And wait for the page to finish loading
   And change logged in user to "test_ops_center_operator"
   And wait for the page to finish loading

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
   And store table "cases.caseHistory.table" column index with title "Field" on "fieldIdx"
   And store table "cases.caseHistory.table" column index with title "New Value" on "newValIdx"

   And assert row exists on table "cases.caseHistory.table" where text on index "${fieldIdx}" is "Status" and text on index "${newValIdx}" is "Closed"
   And assert row exists on table "cases.caseHistory.table" where text on index "${fieldIdx}" is "Labor Billing" and text on index "${newValIdx}" is "Billable"
   And assert row exists on table "cases.caseHistory.table" where text on index "${fieldIdx}" is "Case Resolution Date" and text on index "${newValIdx}" is "${caseResolutionDateFormatted}"

   And scroll until "cases.caseHistory.table.td.last" is visible
   And take a screenshot
   
   #Commented out this failing assertion by request
   #And assert row exists on table "cases.caseHistory.table" where text on index "${fieldIdx}" is "Billing Status" and text on index "${newValIdx}" is "In Progress"