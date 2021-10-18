Feature: Cases

@author:Rodrigo Montemayor
@description:Verify user is able to create Work Order Hybrid using a REACTIVE case
@case @positive @workorder @smoke
@dataFile:resources/testdata/Cases/Create Work Order using REACTIVE case.csv
@requirementKey=QTM-RQ-23
Scenario: Create Work Order using REACTIVE case
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And ShrdLaunchApp "cases"
   When wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${reactiveCaseNumber}" into "common.searchAssistant.input"
   Then wait until "cases.search.firstResult" to be visible
   When wait until "cases.search.firstResult" to be enable
   And click on "cases.search.firstResult"
   Then wait until "cases.caseNumber" to be visible
   Then wait for 2000 milisec
   When wait until "cases.createWorkOrderHybrid.button" to be enable
   And click on "cases.createWorkOrderHybrid.button"
   Then wait until "workOrders.iframe" to be present
   And switch to frame "workOrders.iframe"
   And wait until "workOrders.create.next.button" to be present
   When wait until "workOrders.create.next.button" to be enable
   And click on "workOrders.create.next.button"
   Then wait until "workOrders.create.assetType.select" to be present
   When select "label=${assetType1}" in "workOrders.create.assetType.select"
   And wait until "workOrders.create.next.button" to be enable
   And click on "workOrders.create.next.button"
   Then wait until "workOrders.create.assetTable.checkbox.first" to be present
   When wait until "workOrders.create.assetTable.checkbox.first" to be enable
   And click on "workOrders.create.assetTable.checkbox.first"
   And wait until "workOrders.create.next.button" to be enable
   And click on "workOrders.create.next.button"
   And wait until "workOrders.create.addAdditionalAssets.yes.option" to be enable
   And click on "workOrders.create.addAdditionalAssets.yes.option"
   And wait until "workOrders.create.next.button" to be enable
   And click on "workOrders.create.next.button"
   Then wait until "workOrders.create.assetType.select" to be present
   When select "label=${assetType2}" in "workOrders.create.assetType.select"
   And wait until "workOrders.create.next.button" to be enable
   And click on "workOrders.create.next.button"
   Then wait until "workOrders.create.assetTable.checkbox.first" to be present
   When wait until "workOrders.create.assetTable.checkbox.first" to be enable
   And click on "workOrders.create.assetTable.checkbox.first"
   And wait until "workOrders.create.next.button" to be enable
   And click on "workOrders.create.next.button"
   And wait until "workOrders.create.addAdditionalAssets.no.option" to be enable
   And click on "workOrders.create.addAdditionalAssets.no.option"
   And wait until "workOrders.create.next.button" to be enable
   And click on "workOrders.create.next.button"
   Then wait until "workOrders.create.selectAssets.label" to be present
   When wait until "workOrders.create.next.button" to be enable
   And click on "workOrders.create.next.button"
   Then wait until "workOrders.create.updateDuration.label" to be present
   When wait until "workOrders.create.next.button" to be enable
   And click on "workOrders.create.next.button"
   Then wait until "workOrders.create.multipleTechsNeeded.label" to be present
   When wait until "workOrders.create.next.button" to be enable
   And click on "workOrders.create.next.button"
   Then switch to default window 
   And wait until "cases.caseNumber" to be visible
   And Execute Java Script with data "window.location.reload();"
   And wait until "cases.caseNumber" to be present
   And assert "cases.caseNumber" is present
   When wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"
   Then wait until "woLineItems.table.firstResult.link" to be present
   And assert "woLineItems.table.firstResult.link" is present
   



