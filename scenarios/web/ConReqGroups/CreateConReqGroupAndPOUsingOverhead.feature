Feature: Con Req Groups

@author:Rodrigo Montemayor
@description:Verify that user is able to create Con Req Group and PO manually using an Overhead account
@conreqgroup @regression @positive
@dataFile:resources/testdata/Con Req Groups/Create Con Req Group and PO using Overhead.csv
@requirementKey=QTM-RQ-23
Scenario: Create Con Req Group and PO using Overhead
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_o&M_manager"
   And ShrdLaunchApp "cases"
   Then wait until "common.activeTab" to be present
   When wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And clear "common.searchAssistant.input"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${caseNumber}" into "common.searchAssistant.input"
   Then wait until "cases.search.firstResult" to be present
   When wait until "cases.search.firstResult" to be enable
   And click on "cases.search.firstResult"
   And wait until "cases.createConReqGroup.button" to be enable
   And click on "cases.createConReqGroup.button"
   And wait until "cases.createConReqGroup.popup.vendor.input" to be enable
   And click on "cases.createConReqGroup.popup.vendor.input"
   And wait until "cases.createConReqGroup.popup.vendor.input" to be enable
   And clear "cases.createConReqGroup.popup.vendor.input"
   And wait until "cases.createConReqGroup.popup.vendor.input" to be enable
   And sendKeys "${vendor}" into "cases.createConReqGroup.popup.vendor.input"
   And wait until "cases.createConReqGroup.popup.vendor.firstOption" to be enable
   And click on "cases.createConReqGroup.popup.vendor.firstOption"
   And wait until "cases.createConReqGroup.popup.vendorContact.input" to be enable
   And click on "cases.createConReqGroup.popup.vendorContact.input"
   And wait until "cases.createConReqGroup.popup.vendorContact.input" to be enable
   And clear "cases.createConReqGroup.popup.vendorContact.input"
   And wait until "cases.createConReqGroup.popup.vendorContact.input" to be enable
   And sendKeys "${vendorContact}" into "cases.createConReqGroup.popup.vendorContact.input"
   And wait until "cases.createConReqGroup.popup.vendorContact.firstOption" to be enable
   And click on "cases.createConReqGroup.popup.vendorContact.firstOption"
   And wait until "cases.createConReqGroup.popup.requestedDeliveryDate.calendar.icon" to be enable
   And click on "cases.createConReqGroup.popup.requestedDeliveryDate.calendar.icon"
   And wait until "common.openLightningCalendar.today" to be enable
   And click on "common.openLightningCalendar.today"
   And wait until "cases.createConReqGroup.popup.useProjectShipTo.checkbox" to be enable
   And click on "cases.createConReqGroup.popup.useProjectShipTo.checkbox"
   And wait until "cases.createConReqGroup.popup.save.button" to be enable
   And click on "cases.createConReqGroup.popup.save.button"
   Then switch to frame "conReqGroups.iframe"
   And assert "conReqGroups.details.status" text is "New"
   And store text from "conReqGroups.details.name" into "createdConReqGroup"
   When wait until "conReqGroups.constructionRequisitionLines.new.button" to be enable
   And click on "conReqGroups.constructionRequisitionLines.new.button"
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
   When select "label=${taxCategory}" in "conReqGroups.constructionRequisitionLines.add.lineItem.taxCategory.select"
   And wait until "conReqGroups.constructionRequisitionLines.add.qtyOrdered.input" to be enable
   And click on "conReqGroups.constructionRequisitionLines.add.qtyOrdered.input"
   And wait until "conReqGroups.constructionRequisitionLines.add.qtyOrdered.input" to be enable
   And clear "conReqGroups.constructionRequisitionLines.add.qtyOrdered.input"
   And wait until "conReqGroups.constructionRequisitionLines.add.qtyOrdered.input" to be enable
   And sendKeys "${qtyOrdered}" into "conReqGroups.constructionRequisitionLines.add.qtyOrdered.input"
   And wait until "conReqGroups.constructionRequisitionLines.add.unitCost.input" to be enable
   And click on "conReqGroups.constructionRequisitionLines.add.unitCost.input"
   And wait until "conReqGroups.constructionRequisitionLines.add.unitCost.input" to be enable
   And clear "conReqGroups.constructionRequisitionLines.add.unitCost.input"
   And wait until "conReqGroups.constructionRequisitionLines.add.unitCost.input" to be enable
   And sendKeys "${unitCost}" into "conReqGroups.constructionRequisitionLines.add.unitCost.input"
   And select "label=${notToExceed}" in "conReqGroups.constructionRequisitionLines.add.lineItem.notToExceed.select"
   And wait until "conReqGroups.constructionRequisitionLines.add.lineItem.save.button" to be enable
   And click on "conReqGroups.constructionRequisitionLines.add.lineItem.save.button"
   Then wait until "conReqGroups.iframe" to be present
   And switch to frame "conReqGroups.iframe"
   And assert "conReqGroups.constructionRequisitionLines.table" is present
   And wait until "conReqGroups.details.submitForApproval.button" to be present
   When wait until "conReqGroups.details.submitForApproval.button" to be enable
   And click on "conReqGroups.details.submitForApproval.button"
   Then assert "conReqGroups.details.status" text is "Pending Approval"
   And switch to default window 
   When wait until "common.logOutAs.link" to be enable
   And click on "common.logOutAs.link"
   And ShrdLaunchApp "cases"
   And ShrdChangeLoggedInUser "test_approver"
   And ShrdLaunchApp "cases"
   Then wait until "common.activeTab" to be present
   When wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And clear "common.searchAssistant.input"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${caseNumber}" into "common.searchAssistant.input"
   Then wait until "cases.search.firstResult" to be present
   When wait until "cases.search.firstResult" to be enable
   And click on "cases.search.firstResult"
   Then wait until "cases.quickLinks.conReqGroups" to be present
   When wait until "cases.quickLinks.conReqGroups" to be enable
   And click on "cases.quickLinks.conReqGroups"
   Then wait until "cases.conReqGroups.quickFilter.button" to be present
   When wait until "cases.conReqGroups.quickFilter.button" to be enable
   And click on "cases.conReqGroups.quickFilter.button"
   Then wait until "cases.conReqGroups.quickFilter.name.input" to be visible
   When wait until "cases.conReqGroups.quickFilter.name.input" to be enable
   And click on "cases.conReqGroups.quickFilter.name.input"
   And wait until "cases.conReqGroups.quickFilter.name.input" to be enable
   And clear "cases.conReqGroups.quickFilter.name.input"
   And wait until "cases.conReqGroups.quickFilter.name.input" to be enable
   And sendKeys "${createdConReqGroup}" into "cases.conReqGroups.quickFilter.name.input"
   And wait until "cases.conReqGroups.quickFilter.apply.button" to be enable
   And click on "cases.conReqGroups.quickFilter.apply.button"
   And wait for 5000 milisec
   Then wait until "cases.conReqGroups.firstResultLink" to be present
   When wait until "cases.conReqGroups.firstResultLink" to be enable
   And click on "cases.conReqGroups.firstResultLink"
   Then wait until "conReqGroups.iframe" to be present
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
   And wait for 8000 milisec
   And wait until "conReqGroups.approvalRequest.details.conReqGroup.link" to be enable
   And click on "conReqGroups.approvalRequest.details.conReqGroup.link"
   Then wait until "conReqGroups.iframe" to be present
   And Execute Java Script with data "window.location.reload();"
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
   And wait for 18000 milisec
   Then wait until "conReqGroups.approvalRequest.status.approved" to be present
   And assert "conReqGroups.approvalRequest.status" text is "Approved"
   When wait until "common.logOutAs.link" to be enable
   And click on "common.logOutAs.link"
   And ShrdLaunchApp "cases"
   And ShrdChangeLoggedInUser "test_o&M_manager"
   And ShrdLaunchApp "cases"
   Then wait until "common.activeTab" to be present
   When wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And clear "common.searchAssistant.input"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${caseNumber}" into "common.searchAssistant.input"
   Then wait until "cases.search.firstResult" to be present
   When wait until "cases.search.firstResult" to be enable
   And click on "cases.search.firstResult"
   Then wait until "cases.quickLinks.conReqGroups" to be present
   When wait until "cases.quickLinks.conReqGroups" to be enable
   And click on "cases.quickLinks.conReqGroups"
   Then wait until "cases.conReqGroups.quickFilter.button" to be present
   When wait until "cases.conReqGroups.quickFilter.button" to be enable
   And click on "cases.conReqGroups.quickFilter.button"
   Then wait until "cases.conReqGroups.quickFilter.name.input" to be visible
   When wait until "cases.conReqGroups.quickFilter.name.input" to be enable
   And click on "cases.conReqGroups.quickFilter.name.input"
   And wait until "cases.conReqGroups.quickFilter.name.input" to be enable
   And clear "cases.conReqGroups.quickFilter.name.input"
   And wait until "cases.conReqGroups.quickFilter.name.input" to be enable
   And sendKeys "${createdConReqGroup}" into "cases.conReqGroups.quickFilter.name.input"
   And wait until "cases.conReqGroups.quickFilter.apply.button" to be enable
   And click on "cases.conReqGroups.quickFilter.apply.button"
   And wait for 5000 milisec
   Then wait until "cases.conReqGroups.firstResultLink" to be present
   When wait until "cases.conReqGroups.firstResultLink" to be enable
   And click on "cases.conReqGroups.firstResultLink"
   Then wait until "conReqGroups.iframe" to be present
   And switch to frame "conReqGroups.iframe"
   And wait until "conReqGroups.contstructionRequisitionLines.firstPOLine.link" to be present
   When wait until "conReqGroups.contstructionRequisitionLines.firstPOLine.link" to be enable
   And click on "conReqGroups.contstructionRequisitionLines.firstPOLine.link"
   Then switch to default window 
   And wait until "purchaseOrderLines.details.purchaseOrder.link" to be present
   When wait until "purchaseOrderLines.details.purchaseOrder.link" to be enable
   And click on "purchaseOrderLines.details.purchaseOrder.link"
   Then wait until "purchaseOrder.details.name" to be present
   And assert "purchaseOrder.details.name" is present
   And wait for 12000 milisec
   



