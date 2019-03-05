#!/bin/sh

mvn clean
mvn install


#rsync -Cavuzb -a --stats --progress target/web/ devops@dc.sevais.com:/opt/www/vantalii/apache-tomcat-9.0.16/webapps/app
rsync -acrvz  --delete --stats --progress target/web/ devops@dc.sevais.com:/opt/www/vantalii/apache-tomcat-9.0.16/webapps/app
exit 1;

