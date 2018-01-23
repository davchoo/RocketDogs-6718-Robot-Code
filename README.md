# RocketDogs Robot Path Visualizer
This branch is for testing the library pathfinder and visualizing the
paths created. This means DO NOT MERGE THIS BRANCH!

## Create IDE project
- ```gradlew.bat idea``` Create IDEA project files
- ```gradlew.bat cleanIdea``` Remove IDEA project files
- ```gradlew.bat eclipse``` Create Eclipse project files
- ```gradlew.bat cleanEclipse``` Remove Eclipse project files

# Setup python
- Install python3
- `pip install pygame` to install the necessary library

# Visualizing a path
- Edit the waypoints list in frc.team6718.Test
- `gradlew.bat run` to create the path.csv in the visualization folder
- `cd visualization` to access the visualization folder
- `python Main.py` to run the python script that draws the path that will be taken
- Close out of the python window before rerunning the java program