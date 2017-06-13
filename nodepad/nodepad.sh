cd $NODEPADDIR/

mvn exec:java -Dexec.mainClass="com.theuniversalgraph.application.nodepad.Main" -Dexec.args=$1

