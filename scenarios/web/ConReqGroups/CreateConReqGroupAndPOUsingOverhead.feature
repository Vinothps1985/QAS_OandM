Feature: Con Req Groups

@author:Rodrigo Montemayor
@description:Verify that user is able to create Con Req Group and PO manually using an O&M Project
@conreqgroup @regression @positive
@dataFile:resources/testdata/Con Req Groups/Create Con Req Group and PO using Overhead.csv

Scenario: Create Con Req Group and PO using OM Project

   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_o&M_manager"
   And close all open web tabs
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And take a screenshot

   Then wait until "cases.createConReqGroup.button" to be enable
   And click on "cases.createConReqGroup.button"

   #Vendor
   And wait until "cases.createConReqGroup.popup.vendor.input" to be enable
   And click on "cases.createConReqGroup.popup.vendor.input"
   And wait until "cases.createConReqGroup.popup.vendor.input" to be enable
   And clear "cases.createConReqGroup.popup.vendor.input"
   And wait until "cases.createConReqGroup.popup.vendor.input" to be enable
   And sendKeys "${vendor}" into "cases.createConReqGroup.popup.vendor.input"
   And wait until "cases.createConReqGroup.popup.vendor.firstOption" to be visible
   And wait until "cases.createConReqGroup.popup.vendor.firstOption" to be enable
   And click on "cases.createConReqGroup.popup.vendor.firstOption"
   And wait until "cases.createConReqGroup.popup.vendor.firstOption" not to be visible

   #Vendor contact
   And wait until "cases.createConReqGroup.popup.vendorContact.input" to be enable
   And click on "cases.createConReqGroup.popup.vendorContact.input"
   And wait until "cases.createConReqGroup.popup.vendorContact.input" to be enable
   And clear "cases.createConReqGroup.popup.vendorContact.input"
   And wait until "cases.createConReqGroup.popup.vendorContact.input" to be enable
   And sendKeys "${vendorContact}" into "cases.createConReqGroup.popup.vendorContact.input"
   And wait until "cases.createConReqGroup.popup.vendorContact.firstOption" to be visible
   And wait until "cases.createConReqGroup.popup.vendorContact.firstOption" to be enable
   And click on "cases.createConReqGroup.popup.vendorContact.firstOption"
   And wait until "cases.createConReqGroup.popup.vendorContact.firstOption" not to be visible

   #Requested Delivery Date
   And wait until "cases.createConReqGroup.popup.requestedDeliveryDate.calendar.icon" to be enable
   And click on "cases.createConReqGroup.popup.requestedDeliveryDate.calendar.icon"
   And wait until "common.openLightningCalendar.today" to be enable
   And click on "common.openLightningCalendar.today"

   And wait until "cases.createConReqGroup.popup.shipToContact.input" to be enable
   And click on "cases.createConReqGroup.popup.shipToContact.input"
   And clear "cases.createConReqGroup.popup.shipToContact.input"
   And sendKeys "${shipToContact}" into "cases.createConReqGroup.popup.shipToContact.input"
   And wait until "cases.createConReqGroup.popup.shipToContact.firstOption" to be visible
   And wait until "cases.createConReqGroup.popup.shipToContact.firstOption" to be enable
   And click on "cases.createConReqGroup.popup.shipToContact.firstOption"
   And wait until "cases.createConReqGroup.popup.shipToContact.firstOption" not to be visible

   Then sendKeys "${shipToStreet}" into "cases.createConReqGroup.popup.shipToStreet.input"
   Then sendKeys "${shipToCity}" into "cases.createConReqGroup.popup.shipToCity.input"
   Then sendKeys "${shipToState}" into "cases.createConReqGroup.popup.shipToState.input"
   Then sendKeys "${shipToZip}" into "cases.createConReqGroup.popup.shipToZip.input"

   #Use project ship to
   #And wait until "cases.createConReqGroup.popup.useProjectShipTo.checkbox" to be enable
   #And click on "cases.createConReqGroup.popup.useProjectShipTo.checkbox"
   #And take a screenshot

   #Save
   And wait until "cases.createConReqGroup.popup.save.button" to be enable
   And click on "cases.createConReqGroup.popup.save.button"

   Then switch to frame "conReqGroups.iframe"

   And assert "conReqGroups.details.status" text is "New"
   And store text from "conReqGroups.details.name" into "createdConReqGroup"
   And assert "conReqGroups.details.vendor.link" text is "${vendor}"
   And assert "conReqGroups.details.vendorContact.link" text is "${vendorContact}"
   And take a screenshot

   #Click to create a new line
   When wait until "conReqGroups.constructionRequisitionLines.new.button" to be enable
   And click on "conReqGroups.constructionRequisitionLines.new.button"

   #Line item (e.g. Parts - CM)
   And wait until "conReqGroups.constructionRequisitionLines.add.lineItem.input" to be enable
   And click on "conReqGroups.constructionRequisitionLines.add.lineItem.input"
   And wait until "conReqGroups.constructionRequisitionLines.add.lineItem.input" to be enable
   And clear "conReqGroups.constructionRequisitionLines.add.lineItem.input"
   And wait until "conReqGroups.constructionRequisitionLines.add.lineItem.input" to be enable
   And sendKeys "${lineItem}" into "conReqGroups.constructionRequisitionLines.add.lineItem.input"
   And wait until "conReqGroups.constructionRequisitionLines.add.lineItem.search.button" to be enable
   And click on "conReqGroups.constructionRequisitionLines.add.lineItem.search.button"
   And switchWindow "1"
   Then switch to frame "conReqGroups.constructionRequisitionLines.add.lineItem.popup.search.frame"
   When wait until "conReqGroups.constructionRequisitionLines.add.lineItem.popup.search.firstResult" to be enable
   And click on "conReqGroups.constructionRequisitionLines.add.lineItem.popup.search.firstResult"
   And switchWindow "0"
   Then switch to default window 
   And switch to frame "conReqGroups.iframe"

   #Tax category
   Then select "label=${taxCategory}" in "conReqGroups.constructionRequisitionLines.add.lineItem.taxCategory.select"

   #Qty ordered
   And wait until "conReqGroups.constructionRequisitionLines.add.qtyOrdered.input" to be enable
   And click on "conReqGroups.constructionRequisitionLines.add.qtyOrdered.input"
   And clear "conReqGroups.constructionRequisitionLines.add.qtyOrdered.input"
   And sendKeys "${qtyOrdered}" into "conReqGroups.constructionRequisitionLines.add.qtyOrdered.input"

   #Unit cost
   And wait until "conReqGroups.constructionRequisitionLines.add.unitCost.input" to be enable
   And click on "conReqGroups.constructionRequisitionLines.add.unitCost.input"
   And clear "conReqGroups.constructionRequisitionLines.add.unitCost.input"
   And sendKeys "${unitCost}" into "conReqGroups.constructionRequisitionLines.add.unitCost.input"

   #Not to Exceed
   And select "label=${notToExceed}" in "conReqGroups.constructionRequisitionLines.add.lineItem.notToExceed.select"

   And wait until "conReqGroups.constructionRequisitionLines.add.lineItem.save.button" to be enable
   And click on "conReqGroups.constructionRequisitionLines.add.lineItem.save.button"

   #Then wait for the page to finish loading
   #And wait until "conReqGroups.iframe" to be present
   #And wait until "conReqGroups.iframe" to be enable
   Then switch to default window
   And store the current url in "conReqGroupURL"
   And switch to frame "conReqGroups.iframe" until "conReqGroups.constructionRequisitionLines.table" is present
   And scroll until "conReqGroups.constructionRequisitionLines.table" is visible
   And assert "conReqGroups.constructionRequisitionLines.table" is present
   And take a screenshot

   #Must check the latest emails to compare an email is received after the test
   Then store the timestamp of the latest received email in "latestEmailTimestamp"

   And wait until "conReqGroups.details.submitForApproval.button" to be present
   When wait until "conReqGroups.details.submitForApproval.button" to be enable
   And click on "conReqGroups.details.submitForApproval.button"
   Then assert "conReqGroups.details.status" text is "Pending Approval"

   #Ensure email reception
   And assert an email is received after "${latestEmailTimestamp}" and the subject contains the text "A Service Construction Requisition is pending your approval"

   And switch to default window

   And wait for the page to finish loading
   And change logged in user to "test_approver"
   And wait for the page to finish loading
   And close all open web tabs

   Then get "${conReqGroupURL}"

   Then wait until "conReqGroups.iframe" to be present
   And switch to frame "conReqGroups.iframe"
   #Cookies to use later in PDF testing. Necessary because that PDF sections
   #uses OMVF
   And store cookies into "cookies"

   #Approve the con req group
   And wait until "conReqGroups.approve.button.first" to be present
   When wait until "conReqGroups.approve.button.first" to be enable
   And click on "conReqGroups.approve.button.first"
   Then switch to default window 
   And wait until "conReqGroups.approvalRequest.approve.button" to be present
   When wait until "conReqGroups.approvalRequest.approve.button" to be enable
   And click on "conReqGroups.approvalRequest.approve.button"
   Then wait until "conReqGroups.approvalRequest.approvalPopup.approve.button" to be visible
   When wait until "conReqGroups.approvalRequest.approvalPopup.approve.button" to be enable
   And click on "conReqGroups.approvalRequest.approvalPopup.approve.button"

   And wait for the page to finish loading

   And wait until "conReqGroups.approvalRequest.details.conReqGroup.link" for a max of 60 seconds to be enable
   And click on "conReqGroups.approvalRequest.details.conReqGroup.link"
   Then wait until "conReqGroups.iframe" to be present
   And Execute Java Script with data "window.location.reload();"

   #TODO We have to approve again, for now.
   And switch to frame "conReqGroups.iframe"
   And wait until "conReqGroups.approve.button.first" to be present
   When wait until "conReqGroups.approve.button.first" to be enable
   And click on "conReqGroups.approve.button.first"
   Then switch to default window 
   And wait until "conReqGroups.approvalRequest.approve.button" to be present
   When wait until "conReqGroups.approvalRequest.approve.button" to be enable
   And click on "conReqGroups.approvalRequest.approve.button"
   Then wait until "conReqGroups.approvalRequest.approvalPopup.approve.button" to be visible
   When wait until "conReqGroups.approvalRequest.approvalPopup.approve.button" to be enable
   And click on "conReqGroups.approvalRequest.approvalPopup.approve.button"
   And wait for the page to finish loading
   Then wait until "conReqGroups.approvalRequest.status.approved" for a max of 60 seconds to be present
   And assert "conReqGroups.approvalRequest.status" text is "Approved"
   And take a screenshot

   And click on "conReqGroups.approvalRequest.conReqGroupName.link"
   Then wait until "conReqGroups.iframe" to be present
   And Execute Java Script with data "window.location.reload();"
   And switch to frame "conReqGroups.iframe"

   #Get the info to assert later
   And store text from "conReqGroups.details.requisitionTotalRollup" into "poTotal"
   And scroll until "conReqGroups.contstructionRequisitionLines.firstPOLine.link" is visible
   And store text from "conReqGroups.contstructionRequisitionLines.firstPOLine.link" into "poLineNumber"
   And switch to default window

   And go to case "${generated_caseNumber}"
   And wait until "case.details.status" to be present
   And wait until "case.details.status" to be enable
   
   And scroll until "cases.purchaseOrders" is visible
   And click on "cases.purchaseOrders"
   And wait until "purchaseOrders.table.first.link" to be present
   And wait until "purchaseOrders.table.first.link" to be enable
   And click on "purchaseOrders.table.first.link"

   Then wait until "purchaseOrder.details.name" to be present
   Then wait until "purchaseOrder.details.name" to be enable
   And wait for the page to finish loading
   And take a screenshot

   And scroll until "purchaseOrders.details.conReqGroup.link.text" is visible
   And assert "purchaseOrders.details.conReqGroup.link.text" text is "${createdConReqGroup}"
   And scroll until "purchaseOrders.details.vendorContact.link.text" is visible
   And assert "purchaseOrders.details.vendor.link.text" text is "${vendor}"
   And assert "purchaseOrders.details.vendorContact.link.text" text is "${vendorContact}"
   And take a screenshot

   #Get the PDF File
   Then store the current url in "purchaseOrderURL"

   #Set the special notes to assert later
   Then wait until "purchaseOrder.details.status.edit.button" to be present
   Then wait until "purchaseOrder.details.status.edit.button" to be enable
   And click on "purchaseOrder.details.status.edit.button"
   Then scroll until "purchaseOrder.details.specialNotes.edit.input" is visible
   And clear "purchaseOrder.details.specialNotes.edit.input"
   And sendKeys "${specialNotes}" into "purchaseOrder.details.specialNotes.edit.input"
   And click on "purchaseOrder.edit.save.button"
   And wait for the page to finish loading
   And wait until "purchaseOrder.details.status.edit.button" to be present
   And wait until "purchaseOrder.details.status.edit.button" to be enable
   
   #Print and test PDF
   Then click on "purchaseOrders.printPOOnM.button"
   Then switch to frame "purchaseOrders.printPOOnM.iframe" until "purchaseOrders.printPOOnM.download.errordiv" is present
   And get embedded PDF URL into "pdfURL"
   And get "${pdfURL}"
   And switch to default window
   And get "chrome://downloads"
   And get latest download url from chrome downloads
   And download file locally with cookies "${cookies}"
   Then load latest pdf in downloads directory
   And assert text "To: ${vendor}" appears in the pdf with screenshot
   And assert text "${vendorContact}" appears in the pdf
   And assert text "Delivery ${shipToStreet}" appears in the pdf
   And assert text "To: ${shipToCity}" appears in the pdf
   And assert text "${poLineNumber}" appears in the pdf
   And assert text "Purchase Order Total: ${poTotal}" appears in the pdf
   And assert text "${specialNotes}" appears in the pdf

   Then get "${purchaseOrderURL}"
   Then wait until "purchaseOrder.details.name" to be present
   Then wait until "purchaseOrder.details.name" to be enable

   Then wait until "purchaseOrder.details.status.edit.button" to be present
   Then wait until "purchaseOrder.details.status.edit.button" to be enable
   And click on "purchaseOrder.details.status.edit.button"

   Then wait until "purchaseOrder.details.status.edit.input" to be present
   And wait until "purchaseOrder.details.status.edit.input" to be enable
   And click on "purchaseOrder.details.status.edit.input"
   And wait until "purchaseOrder.details.status.edit.submittedToVendor.option" to be visible
   And click on "purchaseOrder.details.status.edit.submittedToVendor.option"
   And wait until "purchaseOrder.details.status.edit.submittedToVendor.option" not to be visible

   And click on "purchaseOrder.edit.save.button"
   And wait for the page to finish loading
   And wait until "purchaseOrder.details.status.edit.button" to be present
   And wait until "purchaseOrder.details.status.edit.button" to be enable
   And assert "purchaseOrder.details.status" text is "Submitted to Vendor"
   And take a screenshot