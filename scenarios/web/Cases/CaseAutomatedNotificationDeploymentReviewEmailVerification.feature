Feature: Cases

@author:Anusha MG
@description:Verify the email sent on case status update to Deployment Review using a REACTIVE case
@case @positive @regression @caseautomatednotification
@dataFile:resources/testdata/Cases/Email notification verification for case status update to Deployment Review using a REACTIVE case.csv

Scenario: Email notification verification for case status update to Deployment Review using a REACTIVE case
	
   # Below are the Pre-requisites for this test
   # Test User Contact on the Account should include Contact Type (Multi-Select):  O&M New Case Contact
   # Email Opt Out set to FALSE on Test User Contact

   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   Then close all open web tabs
   And launch salesforce app "cases"
   And create a case with data "${projectName}" "${subject}" "${caseDescription}" "${summary}" "${recordType}" "${casePriority}" "${caseOrigin}" "${reportedIssue}" "${caseCause}"
   And create a work order with data "${generated_caseNumber}" "${assetType1}" "${assetType2}"
   Then wait until "cases.quickLinks.workOrderLineItems" to be enable

   #Assertions
   And wait until "case.priority" to be present
   Then assert "case.entityName" text is "Case"
   And assert "cases.details.caseRecordType.text" text is "${recordType}"
   And assert "cases.details.status.text" text is "New Case"

   And take a screenshot

   #Must check the latest emails to compare an email is received after the test
   Then store the timestamp of the latest received email in "latestEmailTimestamp"

   #Change the status of the case from New case to Deployment Review and click on Save
   And wait until "cases.details.status.edit.button" to be present
   When wait until "cases.details.status.edit.button" to be enable
   And store the current url in "caseURL"
   And click on "cases.details.status.edit.button"
   Then wait until "cases.details.status.edit.input" to be present
   When wait until "cases.details.status.edit.input" to be enable
   And wait for 2000 milisec
   And scroll until "cases.details.status.edit.input" is visible
   And click on "cases.details.status.edit.input"
   And wait for 2000 milisec
   Then wait until "cases.details.status.edit.deploymentReview.option" to be present
   When wait until "cases.details.status.edit.deploymentReview.option" to be enable
   And click on "cases.details.status.edit.deploymentReview.option"
   And wait until "cases.details.edit.save.button" to be enable
   And click on "cases.details.edit.save.button"
   Then wait until "cases.details.status.edit.button" to be visible

   #Assert case owner is area supervisor
   And wait until "case.details.caseOwner.link.value" to be visible
   And wait until "case.details.caseOwner.link.value" to be enable
   And assert "case.details.caseOwner.link.value" text is "${caseOwnerDeploymentReview}"
   And take a screenshot

   #Assert case status is Deployment Review
   And scroll until "cases.details.status.text" is visible
   And assert "cases.details.status.text" text is "Deployment Review"
   And take a screenshot

   #Ensure email reception
   And assert an email is received after "${latestEmailTimestamp}" and the subject contains the text "Sandbox: Borrego O & M Reactive Case"
   And assert an email is received after "${latestEmailTimestamp}" and the subject contains the text "-- Opened"