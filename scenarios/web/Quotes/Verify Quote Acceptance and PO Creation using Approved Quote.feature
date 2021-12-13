Feature: Quotes

@author:Rodrigo Montemayor
@description:Verify the Quote Acceptance and Purchase Order creation functionality using an Approved Quote.
@quote @positive @regression
@dataFile:resources/testdata/Quotes/Verify Quote Acceptance and PO Creation using Approved Quote.csv

Scenario: Verify Quote Acceptance and PO Creation using Approved Quote
	
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

   And ShrdCreateQuoteWithLines "${generated_caseNumber}" "${salesRep}" "${primaryContact}" "${specialNotes}" "${typeOfWork}" "${laborBilling}" "${pmBilling}" "${costCode1}" "${costCode2}" "${notes1}" "${notes2}" "${vendor1}" "${vendor2}" "${vendorContact1}" "${vendorContact2}"
   And take a screenshot

   #Now approve the quote
   And approve quote "${generated_quoteNumber}"
   And assert "quotes.details.recordType" text is "Approved"
   And assert "quotes.details.status" text is "Approved"
   And take a screenshot

   #Must check the latest emails to compare an email is received after the test
   Then store the timestamp of the latest received email in "latestEmailTimestamp"

   #Now accept the quote
   Then change status of quote "${generated_quoteNumber}" to "Accepted"
   And assert "quotes.details.status" text is "Accepted"
   And take a screenshot

   #Count the number of quote lines for later assertions
   Then wait until "quotes.quoteLines.link" to be present
   And wait until "quotes.quoteLines.link" to be enable
   And store the current url in "quoteURL"
   And click on "quotes.quoteLines.link"
   And wait until "quotes.quoteLines.table.first" to be present
   And wait until "quotes.quoteLines.table.first" to be enable
   And store number of rows from table into "numOfQuoteRows"
   And get "${quoteURL}"
   And wait until "quotes.details.status" to be present
   And wait until "quotes.details.status" to be enable

   #Ensure email reception
   #TODO not being received
   #And assert an email is received after "${latestEmailTimestamp}" and the subject contains the text "Quote is accepted by client"

   Then scroll until "quotes.conReqGroups.link" is visible
   And click on "quotes.conReqGroups.link"
   And wait until "cases.conReqGroups.firstResultLink" to be present
   And wait until "cases.conReqGroups.firstResultLink" to be enable
   And take a screenshot
   And click on "cases.conReqGroups.firstResultLink"

   And wait until "conReqGroups.iframe" to be present
   And wait until "conReqGroups.iframe" to be enable
   And switch to frame "conReqGroups.iframe" until "conReqGroups.constructionRequisitionLines.table" is present
   Then assert "conReqGroups.details.status" text is "New"
   And take a screenshot

   And scroll until "conReqGroups.constructionRequisitionLines.table" is visible
   And assert "conReqGroups.constructionRequisitionLines.table" is present
   And take a screenshot
   And assert con req group has "${numOfQuoteRows}" lines

   #This approval can take a long time. 1800 secs = 30 min...
   Then refresh the con req group until the status is "Approved" for a max of 1800 seconds
   And take a screenshot

   Then go to case "${generated_caseNumber}"
   And wait until "case.details.status" to be present
   And wait until "case.details.status" to be enable
   And scroll until "cases.purchaseOrders" is visible
   And click on "cases.purchaseOrders"
   And wait until "cases.purchaseOrders.firstResult.link" to be present
   And wait until "cases.purchaseOrders.firstResult.link" to be enable
   And assert "cases.purchaseOrders.firstResult.link" is present
   And take a screenshot

   And assert number of purchase orders created matches "${vendor1}" "${vendor2}"