Feature: Cases

@author:Rodrigo Montemayor
@description:Verify user is able to create Work Order Hybrid using a REACTIVE case
@case @positive @workorder
@dataFile:resources/testdata/Cases/Create Work Order using REACTIVE case.csv
@requirementKey=QTM-RQ-23

Scenario: Create Work Order using REACTIVE case
	
   Given ShrdLoginToFullCopy "${username}" "${password}"
   When wait until "applauncher.div" to be enable
   And click on "applauncher.div"
   Then wait until "applauncher.input.text" to be present
   When wait until "applauncher.input.text" to be enable
   And clear "applauncher.input.text"
   And wait until "applauncher.input.text" to be enable
   And sendKeys "cases" into "applauncher.input.text"
   Then wait until "applauncher.link.cases" to be visible
   When wait until "applauncher.link.cases" to be enable
   And click on "applauncher.link.cases"
   And wait until "common.searchAssistant.button" to be enable
   And click on "common.searchAssistant.button"
   And wait until "common.searchAssistant.input" to be enable
   And sendKeys "${reactiveCaseNumber}" into "common.searchAssistant.input"
   Then wait until "cases.search.firstResult" to be visible
   When wait until "cases.search.firstResult" to be enable
   And click on "cases.search.firstResult"
   And wait for 2000 milisec
   Then assert "cases.caseNumber" text is "${reactiveCaseNumber}"
   When wait until "cases.createWorkOrderHybrid.button" to be enable
   And click on "cases.createWorkOrderHybrid.button"
   And wait for 5000 milisec
   



