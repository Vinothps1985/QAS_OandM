Feature: Cases

@author:Rodrigo Montemayor
@description:Verify user is able to create Work Order Hybrid using a REACTIVE case
@case @positive @workorder @smoke @second
@dataFile:resources/testdata/Cases/Create Work Order using REACTIVE case.csv
@requirementKey=QTM-RQ-23
Scenario: Create Work Order using REACTIVE case
	
   Given ShrdLogin "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And ShrdLaunchApp "cases"
   And ShrdCreateCase "${projectName}" "${subject}" "${caseDescription}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And ShrdCreateWorkOrder "${generated_caseNumber}" "${assetType1}" "${assetType2}"

   And take a screenshot

   #Confirm and screenshot work order lines created
   Then wait until "cases.quickLinks.workOrderLineItems" to be enable
   And click on "cases.quickLinks.workOrderLineItems"
   Then wait until "woLineItems.table.firstResult.link" to be present
   And take a screenshot
   And wait for 2000 milisec
   



