#!/bin/sh
mvn -f $HOME/devel/workspace/projects/com.project.contract/ clean install
mvn clean install -DskipTests -Pprod

#StaticFiles
rsync -acrvz  --delete --stats --progress target/web/WEB-INF/classes/static/ root@178.62.32.47:/opt/www/vantalii/static

#rsync -Cavuzb -a --stats --progress target/web/ devops@dc.sevais.com:/home/devops/www/sevais/opetra/core/webapps/app
rsync -acrvz  --delete --stats --progress target/web-alfa/ root@94.237.94.131:/var/www/jee_projects/vantalii/apache-tomcat-9.0.34/webapps/ROOT
exit 1;

