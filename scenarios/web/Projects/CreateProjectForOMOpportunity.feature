Feature: Projects

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a project for the opportunity with record type O&M Opportunity
@project @positive @smoke
@dataFile:resources/testdata/Projects/Create project for OM Opportunity.csv

Scenario: Create project for OM Opportunity
	
   Given login to salesforce with "${username}" and "${password}"
   And change logged in user to "test_ops_center_operator"
   Then close all open web tabs
   #Append a random 6 digit number to the project name
   #Final project name will be 'ProjectName-123456' where 123456 is the random number
   And create a random number with 6 digits and store it in "randomNumber"
   And create a project with data "${opportunityName}" "ProjectName-${randomNumber}" "${epcSite}" "${watts}"
   
   Then assert "projects.details.projectName" text is "${generated_projectName}"
   And take a screenshot

   When wait until "projects.siteInformation.siteSubstantialDate.notEmpty" to be enable
   And scroll until "projects.siteInformation.siteSubstantialDate.notEmpty" is visible
   Then assert "projects.details.epcSite" text is "${epcSite}"
   And assert "projects.siteInformation.overallProjectSizeWatts" text is "${watts}"
   And assert "projects.siteInformation.siteAddress" text is "${siteAddress}"
   And assert "projects.siteInformation.siteCity" text is "${siteCity}"
   And assert "projects.details.projectState" text is "${projectState}"
   And assert "projects.siteInformation.siteZip" text is "${siteZip}"
   And assert "projects.siteInformation.sitePTODate.notEmpty" is present
   And assert "projects.siteInformation.siteSubstantialDate.notEmpty" is present
   And take a screenshot
