Feature: Cases

@author:Rodrigo Montemayor
@description:Verify the ability to close case using a REACTIVE case having Quotes with status Approved.
@case @positive @smoke
@dataFile:resources/testdata/Cases/Close REACTIVE case with approved quotes.csv
@requirementKey=QTM-RQ-23
Scenario: Close REACTIVE case with approved quotes
	
   Given ShrdLogin "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And ShrdLaunchApp "cases"
   And ShrdCreateCase "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot
   And ShrdCreateWorkOrder "${generated_caseNumber}" "${assetType1}" "${assetType2}"
   And take a screenshot
   
   And wait for the page to finish loading
   And ShrdChangeLoggedInUser "test_o&M_manager"
   And wait for the page to finish loading

   #Create a quote with no lines
   #TODO if we create it with lines, it cannot be accepted :(
   And ShrdCreateAndApproveQuote "${generated_caseNumber}" "${salesRep}" "${primaryContact}" "${specialNotes}"
   And take a screenshot
   And approve quote "${generated_quoteNumber}"
   ##And store "Q-03767" into "generated_quoteNumber"

   And change status of quote "${generated_quoteNumber}" to "Accepted"
   And take a screenshot
   
   And wait for the page to finish loading
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And wait for the page to finish loading

   #And store "00290506" into "generated_caseNumber"
   
   #TODO Should happen automatically after closing al SA, WO and WOLI
   Then change status of case "${generated_caseNumber}" to "Ops Review"
   And take a screenshot

   And set all service appointments of case "${generated_caseNumber}" to status "Completed"
   And take a screenshot

   And set all work orders of case "${generated_caseNumber}" to status "Completed"
   And take a screenshot

   And set all work order line items of case "${generated_caseNumber}" to status "Completed"
   And take a screenshot

   And close case "${generated_caseNumber}" with "Reactive Service"
   And take a screenshot

   ##TEMP
   ##And go to case "${generated_caseNumber}"

   And wait until "cases.caseHistory" to be enable
   And click on "cases.caseHistory"

   And store table "cases.caseHistory.table" column index with title "Field" on "fieldIdx"
   And store table "cases.caseHistory.table" column index with title "New Value" on "newValIdx"

   And assert row exists on table "cases.caseHistory.table" where text on index "${fieldIdx}" is "Status" and text on index "${newValIdx}" is "Closed"
   And assert row exists on table "cases.caseHistory.table" where text on index "${fieldIdx}" is "Labor Billing" and text on index "${newValIdx}" is "Billable"
   #TODO case resolution date (to get) and billing status (appears a 'Not Billable'. Excel file says it should say 'In Progress')