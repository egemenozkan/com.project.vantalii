#!/bin/sh
mvn -f $HOME/Devel/workspace/projects/com.project.contract/ clean install
mvn clean package -DskipTests -Pprod

#StaticFiles
rsync -acrvz  --delete --stats --progress target/web-alfa/WEB-INF/classes/static/ webuser@192.168.0.20:/var/depo/vantalii/static

#rsync -Cavuzb -a --stats --progress target/web/ devops@dc.sevais.com:/home/devops/www/sevais/opetra/core/webapps/app
rsync -acrvz  --delete --stats --progress target/web-alfa/ webuser@192.168.0.20:/var/www/vantalii/web/webapps/ROOT
exit 1;

