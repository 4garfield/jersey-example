# deploy to test server
mvn package tomcat7:redeploy -Dhost=localhost -Dusername=admin -Dpassword=admin
