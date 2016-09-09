# nodepad project
an open source / open data General AI project with webapis, GUI, commercial batch interface.

## build
### dependencies
we use maven for the dependency solutions. most of the pom dependencies are public projects,but we have 2 private ones.
1. [clouddb](https://github.com/toukubo/clouddb)
2. [todoist](https://github.com/toukubo/todoist)

git clone https://github.com/toukubo/todoist
cd todoist
mvn install 

git clone https://github.com/toukubo/clouddb
cd clouddb
mvn install

git clone https://github.com/theuniversalgraph/core
mvn install




### how to build with those dependencies
those are all maven java project. you need to run 
mvn install 
on those project. then maven will install jars into local repo.

## RUN
Those shell for mac and windows are just wrappers of mvn command. we are building mac / windows exe and .app packages, but now those below only are available. 

### mac os
use either ways below.
1. modify nodepad.sh to fit your installation dir ( where this readme.md exists ), and run nodepad.sh 
2. mvn exec:java -Dexec.mainClass="enclosing.application.node.Main" -Dexec.args=$1
### windows
use either ways below.
1. double click nodepad.exe
2. mvn exec:java -Dexec.mainClass="enclosing.application.node.Main" -Dexec.args=$1

### linux, or from shell
mvn exec:java -Dexec.mainClass="enclosing.application.node.Main" -Dexec.args=$1

