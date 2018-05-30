nodepad
=======

## a minimum setup
```
vi $bash.d
## gr nodepaddir and fix the dir,
cd $gitdir
git clone https://github.com/theuniversalgraph/nodepad
brew install java
brew install maven
git clone https://github.com/toukubo/clouddb
cd clouddb/clouddb
mvn install
cd $gitdir
git clone https://github.com/toukubo/todoist
cd todoist 
mvn install


git clone https://github.com/toukubo/todoist
cd todoist 
mvn install
git clone https://github.com/theuniversalgraph/core
cd core
mvn install


cd $NODEPADDIR
mvn install

mkdir -p data/sentences

```
to take the data 
git pull....
