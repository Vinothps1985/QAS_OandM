Feature: IncidentReports

@author:Rodrigo Montemayor
@description:Verify Incident report creation from FSL app
@incidentreports @positive @mobile
@dataFile:resources/testdata/IncidentReports/Create incident report.csv

Scenario: LoginTest
	
   When sendKeys "${passcode}" into "login.passcode.input"
   And click on "home.popup.useLocation.notYet.option" if it appears within 10000 milisec
   
   #Sync
   And wait until "home.menu.profile.button" to be enable
   And click on "home.menu.profile.button"
   And wait until "profile.settings.icon" to be enable
   And click on "profile.settings.icon"
   And wait until "settings.dataSync.button" to be enable
   And click on "settings.dataSync.button"
   
   #Do the datasync
   And wait until "dataSync.sync.button" to be enable
   And click on "dataSync.sync.button"
   And wait for 10000 milisec
   And wait for 5 minutes for "dataSync.cancelSync.button" to be not present

   And wait until "dataSync.syncComplete.dismiss.button" to be enable
   And click on "dataSync.syncComplete.dismiss.button"
   And go back until "home.menu.profile.button" is present
   And wait until "home.menu.schedule.button" to be enable
   And click on "home.menu.schedule.button"

   And wait until "schedule.date.icon" to be enable
   And click on "schedule.date.icon"
   And wait until "schedule.datepicker.element.sep30" to be present
   And click on "schedule.datepicker.element.sep30"
   And wait until "serviceAppointments.appointments.first" to be enable
   And click on "serviceAppointments.appointments.first"

   #Create new incident
   And wait until "common.actions.button" to be enable
   And click on "common.actions.button"
   And wait until "serviceAppointments.actions.newIncidentReport" to be enable
   And click on "serviceAppointments.actions.newIncidentReport"
   And wait until "serviceAppointment.newIncidentReport.customerProperty.yes" to be enable
   And click on "serviceAppointment.newIncidentReport.customerProperty.yes"
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"
   And wait for 1500 milisec
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"
   And wait until "serviceAppointment.newIncidentReport.severity.closeCall" to be enable
   And click on "serviceAppointment.newIncidentReport.severity.closeCall"
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"
   And wait until "serviceAppointment.newIncidentReport.closeCall.incidentDate.input" to be enable
   And click on "serviceAppointment.newIncidentReport.closeCall.incidentDate.input"
   And wait until "common.date.popup.ok" to be enable
   And click on "common.date.popup.ok"
   And sendKeys "${locationInfo}" into "serviceAppointment.newIncidentReport.closeCall.location.input"
   And sendKeys "${injuryInfo}" into "serviceAppointment.newIncidentReport.closeCall.injury.input"
   And scroll until the text "sequence" is on the screen
   And sendKeys "${damageInfo}" into "serviceAppointment.newIncidentReport.closeCall.damage.input"
   And sendKeys "${sequenceInfo}" into "serviceAppointment.newIncidentReport.closeCall.sequence.input"
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"
   And wait until "serviceAppointment.newIncidentReport.uploadPictures.no" to be enable
   And click on "serviceAppointment.newIncidentReport.uploadPictures.no"
   And wait until "serviceAppointment.newIncidentReport.next" to be enable
   And click on "serviceAppointment.newIncidentReport.next"
   And wait until "serviceAppointment.newIncidentReport.finish.button" to be enable
   And click on "serviceAppointment.newIncidentReport.finish.button"
   
   And wait until "serviceAppointment.details.related.button" to be enable
   And click on "serviceAppointment.details.related.button"
   And wait until "serviceAppointment.details.related.incidentReports.button" to be enable
   And click on "serviceAppointment.details.related.incidentReports.button"
   And wait until "serviceAppointment.incidentReports.first" to be enable
   #And enable debug log
   And click on "serviceAppointment.incidentReports.first"
   #And wait for 5000 milisec-

   