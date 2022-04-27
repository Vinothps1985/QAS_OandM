Feature: Cases

@author:Anusha MG
@description:Verify that Maintenance plans generate Work Orders and Maintenance Cases
@case @positive @regression
@dataFile:resources/testdata/Cases/Create Work Order using Maintenance Plan.csv
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
   And wait until "maintenancePlan.startDate.text" to be present
   Then assert "maintenancePlan.startDate.text" text is "${startDate}"
   And assert "maintenancePlan.workType.link" text is "Maintenance"
   And take a screenshot
   And scroll until "maintenancePlan.workOrderGenerationStatus.text" is visible
   And assert "maintenancePlan.type.text" text is "${type}"
   And assert "maintenancePlan.frequency.text" text is "${frequency}"
   And assert "maintenancePlan.generationTimeframe.text" text is "${generationTimeframe}"
   And assert "maintenancePlan.frequencyType.text" text is "Months"
   And assert "maintenancePlan.generationTimeframeType.text" text is "Months"
   And assert "maintenancePlan.dateofFirstWOinNextBatch.text" text is "${dateofFirstWOinNextBatch}"
   And assert "maintenancePlan.workOrderGenerationStatus.text" text is "Not Started"
   And take a screenshot
   And scroll until "maintenancePlan.maintenancePlanDescription.text" is visible
   And assert "maintenancePlan.maintenancePlanTitle.text" text is "${maintenancePlanTitle}"
   And assert "maintenancePlan.maintenancePlanDescription.text" text is "${maintenancePlanDescription}"
   And take a screenshot
   
   #Generate Maintenance Work Orders
   Then store the current url in "MaintenancePlanURL"
   And click on "maintenancePlan.generateWorkOrders.link"
   And wait until "maintenancePlan.generateWorkOrders.popup.title" to be visible
   And assert "maintenancePlan.generateWorkOrders.popup.woGenerationProgress.text" contains the text "Your work order generation is in progress"
   And take a screenshot
   And click on "maintenancePlan.generateWorkOrders.popup.close.button"
   And wait until "maintenancePlan.startDate.text" to be visible

   #Verify the Work Orders created
   And wait for 5000 milisec
   Then get "${MaintenancePlanURL}"
   Then refresh the maintenance plan page until work order generation status is "In Progress" for a max of 60 seconds
   And assert "maintenancePlan.workOrderGenerationStatus.text" text is "In Progress"
   And scroll until "maintenancePlan.workOrderGenerationStatus.text" is visible
   And take a screenshot

   #This process can take a long time. 1800 secs = 30 min...
   Then refresh the maintenance plan page until work order generation status is "Complete" for a max of 1800 seconds
   And scroll until "maintenancePlan.workOrderGenerationStatus.text" is visible
   And take a screenshot

   #Verify the Work Order - 1 created
   Then scroll until "maintenancePlan.workOrders.viewAll.link" is visible
   And wait until "maintenancePlan.workOrders.viewAll.link" to be enable
   And click on "maintenancePlan.workOrders.viewAll.link"
   And wait until "maintenancePlan.workOrders.table.firstResult.link" to be enable
   And take a screenshot
   And click on "maintenancePlan.workOrders.table.firstResult.link"
   Then wait until "workOrders.details.case" to be enable
   And store text from "workOrders.details.workOrderNumber" into "generated_workOrder_1"
   And store text from "workOrders.details.case" into "generated_caseNumber_1"
   And assert "workOrders.details.case" text is not ""
   And assert "workOrders.details.status" text is "New"
   And assert "workOrders.details.workType.link" text is "${workType}"
   And take a screenshot
   Then click on "workOrders.details.case"

   #Verify the Maintenance Case - 1 created
   Then wait until "maintenanceCase.details.status" to be enable
   And take a screenshot
   And assert "maintenanceCase.details.status" text is "New Case"
   And assert "maintenanceCase.details.caseOwner.link.value" text is "${caseOwnerName}"
   And assert "maintenanceCase.details.caseRecordType" text is "Maintenance Case"
   And assert "maintenanceCase.details.MaintenanceType" text is "${type}"
   And assert "case.caseNumber.text" text is "${generated_caseNumber_1}"

   Then wait until "cases.quickLinks.serviceAppointments" to be enable
   And click on "cases.quickLinks.serviceAppointments"
   And wait until "serviceAppointments.table.first.link" to be enable
   And click on "serviceAppointments.table.first.link"
   And wait until "serviceAppointments.details.status" to be enable
   And assert "serviceAppointments.details.status" text is "None"
   And take a screenshot

   #Verify the Work Order - 2 created
   Then get "${MaintenancePlanURL}"
   And scroll until "maintenancePlan.workOrders.viewAll.link" is visible
   And wait until "maintenancePlan.workOrders.viewAll.link" to be enable
   And click on "maintenancePlan.workOrders.viewAll.link"
   And wait until "maintenancePlan.workOrders.table.secondResult.link" to be enable
   And click on "maintenancePlan.workOrders.table.secondResult.link"
   Then wait until "workOrders.details.case" to be enable
   And store text from "workOrders.details.workOrderNumber" into "generated_workOrder_2"
   And store text from "workOrders.details.case" into "generated_caseNumber_2"
   And assert "workOrders.details.case" text is not ""
   And assert "workOrders.details.status" text is "New"
   And assert "workOrders.details.workType.link" text is "${workType}"
   And take a screenshot
   Then click on "workOrders.details.case"

   #Verify the Maintenance Case - 2 created
   Then wait until "maintenanceCase.details.status" to be enable
   And take a screenshot
   And assert "maintenanceCase.details.status" text is "New Case"
   And assert "maintenanceCase.details.caseOwner.link.value" text is "${caseOwnerName}"
   And assert "maintenanceCase.details.caseRecordType" text is "Maintenance Case"
   And assert "maintenanceCase.details.MaintenanceType" text is "${type}"
   And assert "case.caseNumber.text" text is "${generated_caseNumber_2}"

   Then wait until "cases.quickLinks.serviceAppointments" to be enable
   And click on "cases.quickLinks.serviceAppointments"
   And wait until "serviceAppointments.table.first.link" to be enable
   And click on "serviceAppointments.table.first.link"
   And wait until "serviceAppointments.details.status" to be enable
   And assert "serviceAppointments.details.status" text is "None"
   And take a screenshot