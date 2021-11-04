Feature: Projects

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a new Contact and add contact into Project.
@project @contact @positive @smoke
@dataFile:resources/testdata/Projects/Create contact and add to project.csv
@requirementKey=QTM-RQ-23
Scenario: Create contact and add to project
	
   Given ShrdLogin "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   #Project name will be 'ProjectName-1234' where 1234 is a random number
   And create a random number with 6 digits and store it in "randomNumber"
   And ShrdCreateProject "${opportunityName}" "ProjectName-${randomNumber}" "${epcSite}" "${watts}"
   
   Then assert "projects.details.projectName" text is "${generated_projectName}"   
   And take a screenshot
   And store the current url in "projectsURL"
   
   Then wait until "projects.details.account.link" to be enable
   And click on "projects.details.account.link"

   Then wait until "accounts.quickLinks.contacts" to be enable
   And click on "accounts.quickLinks.contacts"
   And wait until "contacts.create.new.button" to be visible
   And click on "contacts.create.new.button"

   Then wait until "contacts.createContact.popup.firstName.input" to be enable
   And sendKeys "${firstName}" into "contacts.createContact.popup.firstName.input"
   And wait until "contacts.createContact.popup.lastName.input" to be enable
   And create a random number with 5 digits and store it in "randomNumLastName"
   #This randomized info is necessary to later search for this specific contact avoiding duplications
   #and ensuring we're selecting this contact when searched by name
   And sendKeys "Last ${randomNumLastName} Name" into "contacts.createContact.popup.lastName.input"
   And wait until "contacts.createContact.popup.save.button" to be enable
   And click on "contacts.createContact.popup.save.button"
   #If duplicate error appears, then click on save again
   And click on "contacts.createContact.popup.save.button" if "contacts.createContact.popup.similarRecordsDialog.close.button" appears within 10 seconds

   Then wait until "common.toastContainer.link" to be present
   And wait until "common.toastContainer.link" to be enable
   And click on "common.toastContainer.link"

   Then wait until "contacts.details.name" to be present
   And wait until "contacts.details.name" to be visible
   And wait until "contacts.details.name" to be enable
   And store "${firstName} Last ${randomNumLastName} Name" into "fullName"
   And assert "contacts.details.name" text is "${fullName}"
   And take a screenshot

   Then get "${projectsURL}"

   Then wait until "projects.siteInformation.onSiteContact.edit.button" to be present
   And wait until "projects.siteInformation.onSiteContact.edit.button" to be enable
   And click on "projects.siteInformation.onSiteContact.edit.button"
   Then wait until "projects.siteInformation.onSiteContact.edit.input" to be visible
   And wait until "projects.siteInformation.onSiteContact.edit.input" to be enable
   And sendKeys "${fullName}" into "projects.siteInformation.onSiteContact.edit.input"
   Then wait until "projects.siteInformation.onSiteContact.edit.input.li.firstOption" to be visible
   And wait until "projects.siteInformation.onSiteContact.edit.input.li.firstOption" to be enable
   And click on "projects.siteInformation.onSiteContact.edit.input.li.firstOption"
   And wait until "projects.siteInformation.onSiteContact.edit.input.li.firstOption" not to be visible

   And wait until "projects.siteInformation.primaryOMContact.edit.input" to be visible
   And wait until "projects.siteInformation.primaryOMContact.edit.input" to be enable
   And sendKeys "${fullName}" into "projects.siteInformation.primaryOMContact.edit.input"
   Then wait until "projects.siteInformation.primaryOMContact.edit.input.li.firstOption" to be visible
   And wait until "projects.siteInformation.primaryOMContact.edit.input.li.firstOption" to be enable
   And click on "projects.siteInformation.primaryOMContact.edit.input.li.firstOption"
   And wait until "projects.siteInformation.primaryOMContact.edit.input.li.firstOption" not to be visible
   Then take a screenshot
   Then wait until "projects.save.button" to be present
   And wait until "projects.save.button" to be enable
   And click on "projects.save.button"
   
   Then wait until "projects.siteInformation.onSiteContact.link.span" to be present
   And assert "projects.siteInformation.onSiteContact.link.span" text is "${fullName}"
   And assert "projects.siteInformation.primaryOMContact.link.span" text is "${fullName}"
   And take a screenshot
