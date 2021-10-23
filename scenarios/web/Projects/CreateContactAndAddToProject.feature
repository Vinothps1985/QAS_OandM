Feature: Projects

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a new Contact and add contact into Project.
@project @contact @positive @smoke
@dataFile:resources/testdata/Projects/Create contact and add to project.csv
@requirementKey=QTM-RQ-23
Scenario: Create contact and add to project
	
   Given ShrdLogin "${username}" "${password}"
   And ShrdChangeLoggedInUser "test_ops_center_operator"
   And ShrdCreateProject "ANY" "${projectName}" "" ""
   Then assert "projects.details.projectName" text is "${projectName}"
   
   And take a screenshot
   And store the current url in "projectsURL"
   And store the current time in "currentTime"
   
   When wait until "projects.details.account.link" to be enable
   And click on "projects.details.account.link"
   Then wait until "accounts.quickLinks.contacts" to be present
   When wait until "accounts.quickLinks.contacts" to be enable
   And click on "accounts.quickLinks.contacts"
   And wait until "contacts.create.new.button" to be enable
   And click on "contacts.create.new.button"
   Then wait until "contacts.createContact.popup.firstName.input" to be visible
   When wait until "contacts.createContact.popup.firstName.input" to be enable
   And sendKeys "Auto First Name" into "contacts.createContact.popup.firstName.input"
   And wait until "contacts.createContact.popup.lastName.input" to be enable
   And sendKeys "${currentTime}" into "contacts.createContact.popup.lastName.input"
   And wait until "contacts.createContact.popup.save.button" to be enable
   And click on "contacts.createContact.popup.save.button"

   Then wait until "common.toastContainer.link" to be present
   When wait until "common.toastContainer.link" to be enable
   And click on "common.toastContainer.link"
   Then wait until "contacts.details.name" to be present
   And Execute Java Script with data "var input = document.createElement(\"input\");input.id=\"testing-name\";document.body.insertAdjacentElement(\"beforeend\", input);var input = document.createElement(\"input\");input.id=\"testing-last-name\";document.body.insertAdjacentElement(\"beforeend\", input);var input = document.createElement(\"input\");input.id=\"testing-full-name\";document.body.insertAdjacentElement(\"beforeend\", input);"
   When wait until "common.var.testing-name" to be enable
   And sendKeys "Auto First Name" into "common.var.testing-name"
   And wait until "common.var.testing-last-name" to be enable
   And sendKeys "${currentTime}" into "common.var.testing-last-name"
   Then Execute Java Script with data "document.getElementById(\"testing-full-name\").value = document.getElementById(\"testing-name\").value + \" \" + document.getElementById(\"testing-last-name\").value;"
   And store value from "common.var.testing-full-name" into "fullName"
   And wait until "contacts.details.name" to be present
   And wait for 2000 milisec
   And assert "contacts.details.name" text is "${fullName}"
   And take a screenshot
   And get "${projectsURL}"

   And wait until "projects.siteInformation.onSiteContact.edit.button" to be present
   When wait until "projects.siteInformation.onSiteContact.edit.button" to be enable
   And click on "projects.siteInformation.onSiteContact.edit.button"
   And wait for 3000 milisec
   Then wait until "projects.siteInformation.onSiteContact.edit.input" to be present
   When wait until "projects.siteInformation.onSiteContact.edit.input" to be enable
   And sendKeys "${fullName}" into "projects.siteInformation.onSiteContact.edit.input"
   And wait for 3000 milisec
   Then wait until "projects.siteInformation.onSiteContact.edit.input.li.firstOption" to be present
   When wait until "projects.siteInformation.onSiteContact.edit.input.li.firstOption" to be enable
   And click on "projects.siteInformation.onSiteContact.edit.input.li.firstOption"
   And wait until "projects.siteInformation.primaryOMContact.edit.input" to be enable
   And sendKeys "${fullName}" into "projects.siteInformation.primaryOMContact.edit.input"
   And wait for 3000 milisec
   Then wait until "projects.siteInformation.primaryOMContact.edit.input.li.firstOption" to be present
   When wait until "projects.siteInformation.primaryOMContact.edit.input.li.firstOption" to be enable
   And click on "projects.siteInformation.primaryOMContact.edit.input.li.firstOption"
   Then assert "projects.save.button" is present
   When wait until "projects.save.button" to be enable
   And click on "projects.save.button"
   Then wait until "projects.siteInformation.onSiteContact.link.span" to be present
   And assert "projects.siteInformation.onSiteContact.link.span" text is "${fullName}"
   And take a screenshot
   And wait for 2000 milisec