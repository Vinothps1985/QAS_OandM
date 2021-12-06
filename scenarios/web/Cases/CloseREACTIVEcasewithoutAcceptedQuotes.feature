Feature: Cases

@author:Anusha MG
@description:Verify that user is unable to close case using a REACTIVE case having Quotes with status other than Accepted.
@case @positive @regression
@dataFile:resources/testdata/Cases/Close REACTIVE case without accepted quotes.csv
@requirementKey:QTM-RQ-23
Scenario: Close REACTIVE case without accepted quotes
	
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
   And close case error "${generated_caseNumber}" with "Reactive Service"
   And take a screenshot