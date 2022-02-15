Feature: Cases

@author:Anusha MG
@description:Verify that Maintenance plans generate Work Orders and Maintenance Cases
@case @positive @regression
@dataFile:resources/testdata/Cases/Create Work Order using Maintenance case.csv
Scenario: Create Work Order using Maintenance Plan
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   Then close all open web tabs
   And launch salesforce app "cases"
   And store the current date in format "M/d/yyyy" into "startDate"
   And add 1 business days to current date and store it into "dateofFirstWOinNextBatch" in format "M/d/yyyy"
   Then create a maintenance plan with data "${projectName}" "${startDate}" "${type}" "${frequency}" "${generationTimeframe}" "${dateofFirstWOinNextBatch}" "${maintenancePlanTitle}" "${maintenancePlanDescription}"
   And take a screenshot
   
   #Assertions