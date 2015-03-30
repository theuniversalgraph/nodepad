copy .\web\target\storyteller-web-1.0\WEB-INF\lib\storyteller-core-1.0.jar ..\WEB-INF\lib
copy .\web\target\storyteller-web-1.0\WEB-INF\lib\storyteller-common-1.0.jar ..\WEB-INF\lib
java -cp "jp.storyteller.desktop.jar:commons-httpclient-3.0.1.jar:commons-logging.jar:commons-codec-1.3.jar"  net.storyteller.desktop.HttpClientFilePostOfAndromdaCoreJarAddingToJ2eeStory http://localhost:8080/storyteller /Users/toukubo/Dropbox/git/ nodepad
curl -X GET "http://localhost:8080/storyteller/SystemCreatesNonnsAndAttrsFromCoreJarOfJ2eeStorys.do?j2eeStory=nodepad&packageName=enclosing%2Fapplication%2Fnode"

# java -cp "jp.storyteller.desktop.jar:commons-httpclient-3.0.1.jar:commons-logging.jar:commons-codec-1.3.jar"  net.storyteller.desktop.StorytellerCodesDownload http://localhost:8080/storyteller /Users/toukubo/Dropbox/git/ nodepad
