Feature: Projects

@author:Rodrigo Montemayor
@description:Verify whether user is able to create a new Contact and add contact into Project.
@project @contact @positive

Scenario: Create contact and add to project
	
   Given get "https://bssi--fullcopy.lightning.force.com/"
   And maximizeWindow 
   When wait until "login.username" to be enable
   And clear "login.username"
   And wait until "login.username" to be enable
   And sendKeys "rmontemayor@borregosolar.com.fullcopy" into "login.username"
   And wait until "login.password" to be enable
   And clear "login.password"
   And wait until "login.password" to be enable
   And sendEncryptedKeys "RkNwYXNzd29yZDEyMyE=" into "login.password"
   And wait until "login.submit" to be enable
   And click on "login.submit"
   Then wait until "applauncher.div" to be present
   When wait until "applauncher.div" to be enable
   And click on "applauncher.div"
   Then wait until "applauncher.input.text" to be present
   When wait until "applauncher.input.text" to be enable
   And clear "applauncher.input.text"
   And wait until "applauncher.input.text" to be enable
   And sendKeys "opportunities" into "applauncher.input.text"
   Then wait until "applauncher.link.opportunities" to be present
   When wait until "applauncher.link.opportunities" to be enable
   And click on "applauncher.link.opportunities"
   Then wait until "opportunities.selectListView" to be present
   When wait until "opportunities.selectListView" to be enable
   And click on "opportunities.selectListView"
   Then wait until "opportunities.selectListView.allOpportunities" to be present
   When wait until "opportunities.selectListView.allOpportunities" to be enable
   And click on "opportunities.selectListView.allOpportunities"
   And wait for 3000 milisec
   And wait until "opportunities.search.input" to be enable
   And clear "opportunities.search.input"
   And wait until "opportunities.search.input" to be enable
   And sendKeys "O&M" into "opportunities.search.input"
   And wait until "opportunities.search.input" to be enable
   And type Enter "opportunities.search.input"
   And wait for 2000 milisec
   Then wait until "opportunities.search.results.first" to be present
   When wait until "opportunities.search.results.first" to be enable
   And click on "opportunities.search.results.first"
   Then wait until "opportunities.createProject.button" to be present
   When wait until "opportunities.createProject.button" to be enable
   And click on "opportunities.createProject.button"
   Then switch to frame "opportunities.createProject.iframe"
   And wait until "opportunities.createProject.template.select" to be present
   When wait until "opportunities.createProject.template.select.option.OM" to be enable
   And click on "opportunities.createProject.template.select.option.OM"
   And wait until "opportunities.createProject.template.createProject.button" to be enable
   And click on "opportunities.createProject.template.createProject.button"
   Then switch to default window 
   And wait until "projects.editProject.button" to be visible
   When wait until "projects.editProject.button" to be enable
   And click on "projects.editProject.button"
   And wait until "projects.editProject.popup.name.input" to be enable
   And clear "projects.editProject.popup.name.input"
   And wait until "projects.editProject.popup.name.input" to be enable
   And sendKeys "Project Name - Creating Contact" into "projects.editProject.popup.name.input"
   And wait until "projects.editProject.popup.save.button" to be enable
   And click on "projects.editProject.popup.save.button"
   Then wait until "common.toastContainer" to be present
   And assert "projects.details.projectName" text is "Project Name - Creating Contact"
   And Execute Java Script with data "var elem = document.createElement(\"div\");elem.id=\"the-url\";elem.innerHTML=window.location.href;document.body.insertAdjacentElement(\"beforeend\", elem);"
   And store text from "common.var.the-url" into "projectsURL"
   And Execute Java Script with data "var elem = document.createElement(\"div\");elem.id=\"the-time\";elem.innerHTML=new Date().getTime();document.body.insertAdjacentElement(\"beforeend\", elem);"
   And store text from "common.var.the-time" into "currentTime"
   When wait until "projects.details.account.link" to be enable
   And click on "projects.details.account.link"
   Then wait until "accounts.contacts.all.link" to be present
   When wait until "accounts.contacts.all.link" to be enable
   And click on "accounts.contacts.all.link"
   And wait until "contacts.create.new.button" to be enable
   And click on "contacts.create.new.button"
   And wait until "contacts.createContact.popup.salutation.input" to be enable
   And click on "contacts.createContact.popup.salutation.input"
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
   And assert "contacts.details.name" text is "${fullName}"
   And Execute Java Script with data "var input = document.createElement(\"input\");input.id=\"input-url\";document.body.insertAdjacentElement(\"beforeend\", input);"
   When wait until "common.var.input-url" to be enable
   And sendKeys "${projectsURL}" into "common.var.input-url"
   Then Execute Java Script with data "window.location.href=document.getElementById(\"input-url\").value"
   When wait until "projects.siteInformation.onSiteContact.edit.button" to be enable
   And click on "projects.siteInformation.onSiteContact.edit.button"
   And wait for 3000 milisec
   Then wait until "projects.siteInformation.onSiteContact.edit.input" to be visible
   Then wait for 3000 milisec
   When wait until "projects.siteInformation.onSiteContact.edit.input" to be enable
   And sendKeys "${fullName}" into "projects.siteInformation.onSiteContact.edit.input"
   And wait until "projects.siteInformation.onSiteContact.edit.input.li.firstOption" to be enable
   And click on "projects.siteInformation.onSiteContact.edit.input.li.firstOption"
   And wait until "projects.save.button" to be enable
   And click on "projects.save.button"
   Then wait until "projects.siteInformation.onSiteContact.link.span" to be present
   And assert "projects.siteInformation.onSiteContact.link.span" text is "${fullName}"
   



