Feature: Quotes

@author:Rodrigo Montemayor
@description:Verify that user is able to CREATE and APPROVE the Quote for a REACTIVE case.
@quote @positive @smoke
@dataFile:resources/testdata/Quotes/Create and approve quote for REACTIVE case.csv
@requirementKey=QTM-RQ-23
Scenario: Create and approve quote for REACTIVE case
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_o&M_manager"
   When wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And clear "common.searchAssistant.input"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${number}" into "common.searchAssistant.input"
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
   And sendKeys "test_o&m_manager" into "quotes.createQuote.popup.salesRep.input"
   And wait until "quotes.createQuote.popup.salesRep.firstOption" to be enable
   And click on "quotes.createQuote.popup.salesRep.firstOption"
   And wait until "quotes.createQuote.popup.primaryContact.input" to be enable
   And click on "quotes.createQuote.popup.primaryContact.input"
   And wait until "quotes.createQuote.popup.primaryContact.input" to be enable
   And sendKeys "test_o&m_manager" into "quotes.createQuote.popup.primaryContact.input"
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
   And sendKeys "Special notes for the quotes" into "quotes.createQuote.popup.specialNotes.textarea"
   And wait until "quotes.createQuote.popup.useDefaultShippingLocation.checkbox" to be enable
   And click on "quotes.createQuote.popup.useDefaultShippingLocation.checkbox"
   And wait until "quotes.createQuote.popup.save.button" to be enable
   And click on "quotes.createQuote.popup.save.button"
   Then wait until "common.toastContainer" to be present
   And wait until "quotes.details.salesRep.text" to be present
   And assert "quotes.details.salesRep.text" text is "test_o&m_manager"
   And assert "quotes.details.account" text is "CleanCapital"
   And wait until "quotes.editLines.button" to be present
   When wait until "quotes.editLines.button" to be enable
   And click on "quotes.editLines.button"
   Then switch to frame "quotes.edit.iframe"
   And wait until "quotes.edit.laborBilling.select" to be present
   When select "label=Billable" in "quotes.edit.laborBilling.select"
   And select "label=Billable" in "quotes.edit.pmBilling.select"
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
   Then assert "quotes.edit.save.button" is present
   When wait until "quotes.edit.products.row.1" to be enable
   And click on "quotes.edit.products.row.1"
   And wait until "quotes.edit.products.row.1.costCode.edit.button" to be enable
   And click on "quotes.edit.products.row.1.costCode.edit.button"
   And select "label=Parts - CM" in "quotes.edit.products.row.1.costCode.select"
   And wait until "quotes.edit.products.row.2" to be enable
   And click on "quotes.edit.products.row.2"
   And wait until "quotes.edit.products.row.2.costCode.edit.button" to be enable
   And click on "quotes.edit.products.row.2.costCode.edit.button"
   And select "label=Parts - CM" in "quotes.edit.products.row.2.costCode.select"
   And wait until "quotes.edit.products.row.1" to be enable
   And click on "quotes.edit.products.row.1"
   And wait until "quotes.edit.products.row.1.notes.edit.button" to be enable
   And click on "quotes.edit.products.row.1.notes.edit.button"
   And wait until "quotes.edit.products.row.1.notes.textarea" to be enable
   And clear "quotes.edit.products.row.1.notes.textarea"
   And wait until "quotes.edit.products.row.1.notes.textarea" to be enable
   And sendKeys "Notes 1" into "quotes.edit.products.row.1.notes.textarea"
   And wait until "quotes.edit.products.row.2" to be enable
   And click on "quotes.edit.products.row.2"
   And wait until "quotes.edit.products.row.2.notes.edit.button" to be enable
   And click on "quotes.edit.products.row.2.notes.edit.button"
   And wait until "quotes.edit.products.row.2.notes.textarea" to be enable
   And clear "quotes.edit.products.row.2.notes.textarea"
   And wait until "quotes.edit.products.row.2.notes.textarea" to be enable
   And sendKeys "Notes 2" into "quotes.edit.products.row.2.notes.textarea"
   And wait until "quotes.edit.products.row.1.vendor.edit.button" to be enable
   And click on "quotes.edit.products.row.1.vendor.edit.button"
   And wait until "quotes.edit.products.row.1.vendor.input" to be enable
   And clear "quotes.edit.products.row.1.vendor.input"
   And wait until "quotes.edit.products.row.1.vendor.input" to be enable
   And sendKeys "Amazon.com for purchasing" into "quotes.edit.products.row.1.vendor.input"
   Then wait until "quotes.edit.products.row.1.vendor.option.2" to be present
   When wait until "quotes.edit.products.row.1.vendor.option.2" to be enable
   And click on "quotes.edit.products.row.1.vendor.option.2"
   And wait until "quotes.edit.products.row.2.vendor.edit.button" to be enable
   And click on "quotes.edit.products.row.2.vendor.edit.button"
   And wait until "quotes.edit.products.row.2.vendor.input" to be enable
   And clear "quotes.edit.products.row.2.vendor.input"
   And wait until "quotes.edit.products.row.2.vendor.input" to be enable
   And sendKeys "Amazon.com for purchasing" into "quotes.edit.products.row.2.vendor.input"
   Then wait until "quotes.edit.products.row.2.vendor.option.2" to be present
   When wait until "quotes.edit.products.row.2.vendor.option.2" to be enable
   And click on "quotes.edit.products.row.2.vendor.option.2"
   And wait until "quotes.edit.products.row.1.vendorContact.edit.button" to be enable
   And click on "quotes.edit.products.row.1.vendorContact.edit.button"
   And wait until "quotes.edit.products.row.1.vendorContact.input" to be enable
   And clear "quotes.edit.products.row.1.vendorContact.input"
   And wait until "quotes.edit.products.row.1.vendorContact.input" to be enable
   And sendKeys "null null" into "quotes.edit.products.row.1.vendorContact.input"
   Then wait until "quotes.edit.products.row.1.vendorContact.option.2" to be present
   When wait until "quotes.edit.products.row.1.vendorContact.option.2" to be enable
   And click on "quotes.edit.products.row.1.vendorContact.option.2"
   And wait until "quotes.edit.products.row.2.vendorContact.edit.button" to be enable
   And click on "quotes.edit.products.row.2.vendorContact.edit.button"
   And wait until "quotes.edit.products.row.2.vendorContact.input" to be enable
   And clear "quotes.edit.products.row.2.vendorContact.input"
   And wait until "quotes.edit.products.row.2.vendorContact.input" to be enable
   And sendKeys "null null" into "quotes.edit.products.row.2.vendorContact.input"
   Then wait until "quotes.edit.products.row.2.vendorContact.option.2" to be present
   When wait until "quotes.edit.products.row.2.vendorContact.option.2" to be enable
   And click on "quotes.edit.products.row.2.vendorContact.option.2"
   Then assert "quotes.edit.save.button" is present
   When wait until "quotes.edit.save.button" to be enable
   And click on "quotes.edit.save.button"
   Then wait until "quotes.details.recordType" to be present
   And assert "quotes.details.recordType" text is "Draft"
   When wait until "quotes.submitForApproval.button" to be enable
   And click on "quotes.submitForApproval.button"
   And wait until "quotes.submitForApproval.popup.submit.button" to be enable
   And click on "quotes.submitForApproval.popup.submit.button"
   Then wait until "quotes.details.recordType" to be present
   And wait until "quotes.details.recordType.approved" to be present
   And assert "quotes.details.recordType.approved" text is "Approved"