copy .\web\target\storyteller-web-1.0\WEB-INF\lib\storyteller-core-1.0.jar ..\WEB-INF\lib
copy .\web\target\storyteller-web-1.0\WEB-INF\lib\storyteller-common-1.0.jar ..\WEB-INF\lib
java -cp "jp.storyteller.desktop.jar;../WEB-INF/lib/commons-httpclient-3.0.1.jar;../WEB-INF/lib/commons-logging.jar;../WEB-INF/lib/commons-codec-1.3.jar"  net.storyteller.desktop.HttpClientFilePostOfAndromdaCoreJarAddingToJ2eeStory http://localhost:8080/storyteller /Users/toukubo/Dropbox/git/storyteller/ storyteller
wget http://localhost:8080/st/SystemCreatesNonnsAndAttrsFromCoreJarOfJ2eeStorys.do
java -cp "jp.storyteller.desktop.jar;../WEB-INF/lib/commons-httpclient-3.0.1.jar;../WEB-INF/lib/commons-logging.jar;../WEB-INF/lib/commons-codec-1.3.jar"  net.storyteller.desktop.StorytellerCodesDownload http://localhost:8080/st c:/current/eclipse/workspace/ storyteller
