#!/bin/sh
mvn -f /Users/Egemen/Devel/workspace/com.project.contract/ clean install
mvn clean install -DskipTests -Pprod

#StaticFiles
rsync -acrvz  --delete --stats --progress target/web/WEB-INF/classes/static/ root@dc.sevais.com:/opt/www/vantalii/static

#rsync -Cavuzb -a --stats --progress target/web/ devops@dc.sevais.com:/home/devops/www/sevais/opetra/core/webapps/app
rsync -acrvz  --delete --stats --progress target/web-alfa/ root@dc.sevais.com:/opt/www/vantalii/apache-tomcat-9.0.16/webapps/ROOT
exit 1;

