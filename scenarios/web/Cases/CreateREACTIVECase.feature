Feature: Cases

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a case of record type REACTIVE
@case @positive @smoke @first
@dataFile:resources/testdata/Cases/Create REACTIVE case.csv
@requirementKey=QTM-RQ-23
Scenario: Create REACTIVE case
	
   Given ShrdLogin "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   Then ShrdCreateCase "${projectName}" "${subject}" "${caseDescription}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"

   #Assertions
   Then assert "case.entityName" text is "Case"
   And wait until "case.priority" to be present
   And take a screenshot
   And assert "case.priority" text is "${casePriority}"
   And assert "case.caseOrigin" text is "${caseOrigin}"
   And assert "case.reportedIssue" text is "${reportedIssue}"
   And assert "case.caseCause" text is "${caseCause}"
   And scroll until "case.subject" is visible
   And assert "case.subject" text is "${subject}"
   And assert "case.description" text is "${caseDescription}"
   And take a screenshot
   And wait for 2000 milisec