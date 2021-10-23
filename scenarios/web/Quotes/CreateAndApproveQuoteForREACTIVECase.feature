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
   And ShrdCreateCase "${projectName}" "${subject}" "${caseDescription}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot
   And ShrdCreateWorkOrder "${generated_caseNumber}" "${assetType1}" "${assetType2}"
   And take a screenshot
   
   And wait for the page to finish loading
   And ShrdChangeLoggedInUser "test_o&M_manager"

   And wait for the page to finish loading
   And ShrdLaunchApp "cases"
   When wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And clear "common.searchAssistant.input"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${generated_caseNumber}" into "common.searchAssistant.input"

   Then wait until "cases.search.firstResult" to be present
   When wait until "cases.search.firstResult" to be enable
   And click on "cases.search.firstResult"
   And wait until "cases.createNewQuote.button" to be enable
   And click on "cases.createNewQuote.button"
   And wait until "quotes.createQuote.popup.salesRep.input" to be enable
   And click on "quotes.createQuote.popup.salesRep.input"
   And wait until "quotes.createQuote.popup.salesRep.input" to be enable
   And clear "quotes.createQuote.popup.salesRep.input"
   And wait until "quotes.createQuote.popup.salesRep.input" to be enable
   And sendKeys "${salesRep}" into "quotes.createQuote.popup.salesRep.input"
   And wait until "quotes.createQuote.popup.salesRep.firstOption" to be enable
   And click on "quotes.createQuote.popup.salesRep.firstOption"
   And wait until "quotes.createQuote.popup.primaryContact.input" to be enable
   And click on "quotes.createQuote.popup.primaryContact.input"
   And wait until "quotes.createQuote.popup.primaryContact.input" to be enable
   And sendKeys "${primaryContact}" into "quotes.createQuote.popup.primaryContact.input"
   And take a screenshot
   And wait until "quotes.createQuote.popup.primaryContact.firstOption" to be enable
   And click on "quotes.createQuote.popup.primaryContact.firstOption"
   And wait until "quotes.createQuote.popup.estimatedWorkStartDate.calendar.icon" to be enable
   And click on "quotes.createQuote.popup.estimatedWorkStartDate.calendar.icon"
   And wait until "common.openLightningCalendar.today" to be enable
   And click on "common.openLightningCalendar.today"
   And wait until "quotes.createQuote.popup.siteWorkState.select" to be enable
   And click on "quotes.createQuote.popup.siteWorkState.select"
   And wait until "quotes.createQuote.popup.siteWorkState.option.second" to be enable
   And click on "quotes.createQuote.popup.siteWorkState.option.second"
   And wait until "quotes.createQuote.popup.typeOfWork.select" to be enable
   And click on "quotes.createQuote.popup.typeOfWork.select"
   And wait until "quotes.createQuote.popup.typeOfWork.option.initialVisit" to be enable
   And click on "quotes.createQuote.popup.typeOfWork.option.initialVisit"
   And wait until "quotes.createQuote.popup.specialNotes.textarea" to be enable
   And clear "quotes.createQuote.popup.specialNotes.textarea"
   And wait until "quotes.createQuote.popup.specialNotes.textarea" to be enable
   And sendKeys "${specialNotes}" into "quotes.createQuote.popup.specialNotes.textarea"
   And take a screenshot
   And scroll until "quotes.createQuote.popup.useDefaultShippingLocation.checkbox" is visible
   And click on "quotes.createQuote.popup.useDefaultShippingLocation.checkbox"
   And wait until "quotes.createQuote.popup.save.button" to be enable
   And click on "quotes.createQuote.popup.save.button"
   Then wait until "common.toastContainer" to be present
   And wait until "quotes.details.salesRep.text" to be present
   And assert "quotes.details.salesRep.text" text is "${salesRep}"
   And take a screenshot

   And wait until "quotes.editLines.button" to be present
   When wait until "quotes.editLines.button" to be enable
   And click on "quotes.editLines.button"
   And wait until "quotes.edit.iframe" to be enable
   And click on "quotes.edit.iframe"
   Then switch to frame "quotes.edit.iframe"
   And wait until "quotes.edit.laborBilling.select" to be present
   When select "label=${laborBilling}" in "quotes.edit.laborBilling.select"
   And select "label=${pmBilling}" in "quotes.edit.pmBilling.select"
   And wait until "quotes.edit.addProducts.button" to be enable
   And click on "quotes.edit.addProducts.button"
   Then wait until "quotes.addProducts.paperDrawerPanel" to be present
   When wait until "quotes.addProducts.categoryRows.first" to be enable
   And click on "quotes.addProducts.categoryRows.first"
   And wait until "quotes.addProducts.rows.second.checkbox" to be enable
   And click on "quotes.addProducts.rows.second.checkbox"
   And wait until "quotes.addProducts.rows.third.checkbox" to be enable
   And click on "quotes.addProducts.rows.third.checkbox"
   And wait until "quotes.addProducts.select.button" to be enable
   And click on "quotes.addProducts.select.button"
   And take a screenshot
   When wait until "quotes.edit.products.row.1" to be enable
   And click on "quotes.edit.products.row.1"
   And wait until "quotes.edit.products.row.1.costCode.edit.button" to be enable
   And click on "quotes.edit.products.row.1.costCode.edit.button"
   And select "label=${costCode1}" in "quotes.edit.products.row.1.costCode.select"
   And wait until "quotes.edit.products.row.2" to be enable
   And click on "quotes.edit.products.row.2"
   And wait until "quotes.edit.products.row.2.costCode.edit.button" to be enable
   And click on "quotes.edit.products.row.2.costCode.edit.button"
   And select "label=${costCode2}" in "quotes.edit.products.row.2.costCode.select"
   And wait until "quotes.edit.products.row.1" to be enable
   And click on "quotes.edit.products.row.1"
   And wait until "quotes.edit.products.row.1.notes.edit.button" to be enable
   And click on "quotes.edit.products.row.1.notes.edit.button"
   And wait until "quotes.edit.products.row.1.notes.textarea" to be enable
   And clear "quotes.edit.products.row.1.notes.textarea"
   And wait until "quotes.edit.products.row.1.notes.textarea" to be enable
   And sendKeys "${notes1}" into "quotes.edit.products.row.1.notes.textarea"
   And wait until "quotes.edit.products.row.2" to be enable
   And click on "quotes.edit.products.row.2"
   And wait until "quotes.edit.products.row.2.notes.edit.button" to be enable
   And click on "quotes.edit.products.row.2.notes.edit.button"
   And wait until "quotes.edit.products.row.2.notes.textarea" to be enable
   And clear "quotes.edit.products.row.2.notes.textarea"
   And wait until "quotes.edit.products.row.2.notes.textarea" to be enable
   And sendKeys "${notes2}" into "quotes.edit.products.row.2.notes.textarea"
   And wait until "quotes.edit.products.row.1.vendor.edit.button" to be enable
   And click on "quotes.edit.products.row.1.vendor.edit.button"
   And wait until "quotes.edit.products.row.1.vendor.input" to be enable
   And clear "quotes.edit.products.row.1.vendor.input"
   And wait until "quotes.edit.products.row.1.vendor.input" to be enable
   And sendKeys "${vendor1}" into "quotes.edit.products.row.1.vendor.input"
   And wait for 3000 milisec
   Then wait until "quotes.edit.products.row.1.vendor.option.2" to be present
   When wait until "quotes.edit.products.row.1.vendor.option.2" to be enable
   And click on "quotes.edit.products.row.1.vendor.option.2"
   And wait until "quotes.edit.products.row.2.vendor.edit.button" to be enable
   And click on "quotes.edit.products.row.2.vendor.edit.button"
   And wait until "quotes.edit.products.row.2.vendor.input" to be enable
   And clear "quotes.edit.products.row.2.vendor.input"
   And wait until "quotes.edit.products.row.2.vendor.input" to be enable
   And sendKeys "${vendor2}" into "quotes.edit.products.row.2.vendor.input"
   And wait for 3000 milisec
   Then wait until "quotes.edit.products.row.2.vendor.option.2" to be present
   When wait until "quotes.edit.products.row.2.vendor.option.2" to be enable
   And click on "quotes.edit.products.row.2.vendor.option.2"
   And wait until "quotes.edit.products.row.1.vendorContact.edit.button" to be enable
   And click on "quotes.edit.products.row.1.vendorContact.edit.button"
   And wait until "quotes.edit.products.row.1.vendorContact.input" to be enable
   And clear "quotes.edit.products.row.1.vendorContact.input"
   And wait until "quotes.edit.products.row.1.vendorContact.input" to be enable
   And sendKeys "${vendorContact1}" into "quotes.edit.products.row.1.vendorContact.input"
   And wait for 3000 milisec
   Then wait until "quotes.edit.products.row.1.vendorContact.option.2" to be present
   When wait until "quotes.edit.products.row.1.vendorContact.option.2" to be enable
   And click on "quotes.edit.products.row.1.vendorContact.option.2"
   And wait until "quotes.edit.products.row.2.vendorContact.edit.button" to be enable
   And click on "quotes.edit.products.row.2.vendorContact.edit.button"
   And wait until "quotes.edit.products.row.2.vendorContact.input" to be enable
   And clear "quotes.edit.products.row.2.vendorContact.input"
   And wait until "quotes.edit.products.row.2.vendorContact.input" to be enable
   And sendKeys "${vendorContact2}" into "quotes.edit.products.row.2.vendorContact.input"
   And wait for 3000 milisec
   Then wait until "quotes.edit.products.row.2.vendorContact.option.2" to be present
   When wait until "quotes.edit.products.row.2.vendorContact.option.2" to be enable
   And click on "quotes.edit.products.row.2.vendorContact.option.2"
   And take a screenshot
   When wait until "quotes.edit.save.button" to be enable
   And click on "quotes.edit.save.button"
   Then switch to default window 
   And wait until "quotes.details.recordType" to be present
   And assert "quotes.details.recordType" text is "Draft"
   And take a screenshot
   When wait until "quotes.submitForApproval.button" to be enable
   And click on "quotes.submitForApproval.button"
   And wait until "quotes.submitForApproval.popup.submit.button" to be enable
   And click on "quotes.submitForApproval.popup.submit.button"
   Then wait until "quotes.details.recordType" to be present
   And wait until "quotes.details.recordType.approved" to be present
   And assert "quotes.details.recordType.approved" text is "Approved"
   And take a screenshot
   And wait for 2000 milisec