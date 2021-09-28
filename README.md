#  QAS_OandM
Automated Framework for O&amp;M project

##  Web automation
### File changes
- Go to resources/application.properties
	- Uncomment the 2 lines for web testing (platform/scenario.file.loc)
	- Ensure the 2 lines for mobile testing are commented
- Go to pom.xml
	- Find `testSuiteFile`
	- Change the value to `config/web_config.xml`
### Execution
- Execute all test cases
	- `mvn clean test`
- Execute test cases with a specific tag
	- `mvn clean test -Dgroups=tag1,tag2,tag3`

##  Mobile automation
### File changes
- Go to resources/application.properties
	- Uncomment the 2 lines for mobile testing (platform/scenario.file.loc)
	- Ensure the 2 lines for web testing are commented
- Go to pom.xml
	- Find `testSuiteFile`
	- Change the value to `config/android_config.xml`
### Execution
- Execute all test cases
	- `mvn clean test`
- Execute test cases with a specific tag
	- `mvn clean test -Dgroups=tag1,tag2,tag3`