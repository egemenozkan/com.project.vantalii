rsync -acrvz  --delete --stats --progress target/classes/static/bundle webuser@192.168.0.20:/var/depo/vantalii/static/
rsync -acrvz  --delete --stats --progress target/classes/static/assets webuser@192.168.0.20:/var/depo/vantalii/static/
rsync -acrvz  --delete --stats --progress target/web-alfa.jar webuser@192.168.0.20:/var/www/vantalii/web