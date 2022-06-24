Feature: Quotes

@author:Rodrigo Montemayor
@description:Verify that user is able to CREATE and APPROVE the Quote for a REACTIVE case.
@quote @positive @smoke
@dataFile:resources/testdata/Quotes/Create and approve quote for REACTIVE case.csv

Scenario: Create and approve quote for REACTIVE case
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   And close all open web tabs
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot
   And create a work order with data "${generated_caseNumber}" "${assetType1}" "${assetType2}"
   And take a screenshot
   
   And wait for the page to finish loading
   And change logged in user to "test_o&M_manager"
   And wait for the page to finish loading
   And close all open web tabs

   And ShrdCreateQuoteWithLines "${generated_caseNumber}" "${salesRep}" "${primaryContact}" "${specialNotes}" "${typeOfWork}" "${laborBilling}" "${pmBilling}" "${costCode1}" "${costCode2}" "${notes1}" "${notes2}" "${vendor1}" "${vendor2}" "${vendorContact1}" "${vendorContact2}"
   #And ShrdCreateQuoteWithLines "00298958" "${salesRep}" "${primaryContact}" "${specialNotes}" "${typeOfWork}" "${laborBilling}" "${pmBilling}" "${costCode1}" "${costCode2}" "${notes1}" "${notes2}" "${vendor1}" "${vendor2}" "${vendorContact1}" "${vendorContact2}"
   And take a screenshot

   #Assertions
   And wait until "quotes.details.salesRep.text" to be present
   And wait until "quotes.details.salesRep.text" to be enable
   And assert "quotes.details.salesRep.text" text is "${salesRep}"
   And assert "quotes.details.primaryContact.link.text" text is "${primaryContact}"
   And take a screenshot
   And scroll until "quotes.details.estimatedWorkStartDate" is visible
   And store the current date in format "M/d/yyyy" into "currentDate"
   And assert "quote.details.typeOfWork" text is "${typeOfWork}"
   And take a screenshot
   And scroll until "quotes.details.specialNotesAndInstructions" is visible
   And assert "quotes.details.specialNotesAndInstructions" text is "${specialNotes}"
   And take a screenshot
   And scroll until "quotes.details.caseDetail" is visible
   And assert "quotes.details.project.link.text" text is "${projectName}"
   And assert "quotes.details.case.link.text" text is "${generated_caseNumber}"
   And assert "quotes.details.caseDetail" text is "${subject}"
   And take a screenshot

   And store the current url in "quote_URL"
   And click on "quotes.details.account.link"

   And wait until "accounts.quickLinks.contacts" to be present
   And wait until "accounts.quickLinks.contacts" to be enable
   And scroll until "accounts.details.billing" is visible
   #Edit clicked to be able to see each address field individually. We don't change anything
   And click on "accounts.details.billingAddress.edit.button"
   And wait until "accounts.details.billingStreet.edit.textarea" to be present
   And wait until "accounts.details.billingStreet.edit.textarea" to be visible
   And store value from "accounts.details.billingStreet.edit.textarea" into "account_billingStreet"
   And store value from "accounts.details.billingCity.edit.input" into "account_billingCity"
   And store value from "accounts.details.billingState.edit.input" into "account_billingState"
   And store value from "accounts.details.billingZip.edit.input" into "account_billingZip"
   And click on "accounts.details.edit.cancel.button"
   And wait until "accounts.details.billingAddress.edit.button" to be visible

   Then get "${quote_URL}"
   And wait until "quotes.details.salesRep.text" to be present
   And wait until "quotes.details.salesRep.text" to be enable
   And scroll until "quote.details.billToStreet" is visible
   And assert "quote.details.billToStreet" text is "${account_billingStreet}"
   And assert "quote.details.billToCity" text is "${account_billingCity}"
   And assert "quote.details.billToState" text is "${account_billingState}"
   And assert "quote.details.billToPostalCode" text is "${account_billingZip}"

   #Must check the latest emails to compare an email is received after the test
   Then store the timestamp of the latest received email in "latestEmailTimestamp"

   #Now approve the quote
   And approve quote "${generated_quoteNumber}"
   And assert "quotes.details.recordType" text is "Approved"
   And assert "quotes.details.status" text is "Approved"
   And take a screenshot
   
   #Ensure email reception
   And assert an email is received after "${latestEmailTimestamp}" and the subject contains the text "Quote has been Approved and Ready to be Sent"
