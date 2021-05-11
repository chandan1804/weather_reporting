# weather_reporting
###### **Clone the project from Github**
https://github.com/chandan1804/weather_reporting.git

###############################################################################

###### **Important Files and Folders**
browser drivers can be found in "ExternalTools" folder, update the drivers with new version when required by download and place file in "ExternalTools" folder
Test data can be found in "src/main/resources/TestData.csv", add/delete/update test cases in "TestData.csv"
TestNG xml can be found in "src/main/resources/testng.xml"
Test Result containing city name, Temperature captured from UI, Temperature captured through API, Magnitude and Result can be found in "src/main/resources/TestResult.csv"
Screenshots for failure cases can be found in "logs" folder with subfolder created with date and time
logs can be found logs.txt inside "logs" folder
Emailable html report can be found in "test-output/emailable-report.html"

###############################################################################

###### **maven commands**
#Validates whether project is correct and all necessary information is available to complete the build process.

mvn validate

#Initializes build state, for example set properties.

mvn initialize

#Generate any source code to be included in compilation phase.

mvn generate-sources

#Compile the source code of the project.

mvn compile

#Run test

mvn clean test -P weatherReportTest

#Copies the final package to the remote repository for sharing with other developers and projects.

mvn deploy
