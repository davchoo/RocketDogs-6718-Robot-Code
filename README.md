# RocketDogs 6718 Robot Code
Robot code for team 6718 or Rocket Dogs!

## Create IDE project
- ```gradlew.bat idea``` Create IDEA project files
- ```gradlew.bat cleanIdea``` Remove IDEA project files
- ```gradlew.bat eclipse``` Create Eclipse project files
- ```gradlew.bat cleanEclipse``` Remove Eclipse project files

## Deploy Commands
- ```gradlew.bat deploy``` will build and deploy your code.
- ```gradlew.bat riolog``` will display the RoboRIO console output on your computer (run with `-Pfakeds` if you don't have a driverstation connected).
- ```gradlew.bat shuffleboard``` will start the shuffleboard app

## Usage
1. Connect to robot
   - Connect to the wifi ```6718```
   - Plug in through USB B
1. ```cd *cloned repo directory*```
1. ```git checkout master``` Changes branch to master
1. ```git pull origin master``` Updates the files to the version in origin/master
1. ```gradlew.bat deploy``` Deploy code to robot
   - ```gradlew.bat deploy --offline``` Use this when connected to robot wifi or at competition
