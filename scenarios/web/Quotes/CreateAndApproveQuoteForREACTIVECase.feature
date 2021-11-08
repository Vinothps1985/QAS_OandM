Feature: Quotes

@author:Rodrigo Montemayor
@description:Verify that user is able to CREATE and APPROVE the Quote for a REACTIVE case.
@quote @positive @smoke
@dataFile:resources/testdata/Quotes/Create and approve quote for REACTIVE case.csv
@requirementKey=QTM-RQ-23
Scenario: Create and approve quote for REACTIVE case
	
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

   And ShrdCreateQuoteWithLines "${generated_caseNumber}" "${salesRep}" "${primaryContact}" "${specialNotes}" "${laborBilling}" "${pmBilling}" "${costCode1}" "${costCode2}" "${notes1}" "${notes2}" "${vendor1}" "${vendor2}" "${vendorContact1}" "${vendorContact2}"
   And take a screenshot

   #Must check the latest emails to compare an email is received after the test
   Then store the timestamp of the latest received email in "latestEmailTimestamp"

   #Now approve the quote
   And approve quote "${generated_quoteNumber}"
   And assert "quotes.details.recordType" text is "Approved"
   And assert "quotes.details.status" text is "Approved"
   And take a screenshot
   
   #Ensure email reception
   And assert an email is received after "${latestEmailTimestamp}" and the subject contains the text "Quote has been Approved and Ready to be Sent"
