#!/bin/bash
export RELEASE_FOLDER=/tmp/semipro-release/
export EXECUTE_FOLDER=/usr/local/bin/semipro-web/
export JAR_NAME=semipro-web.jar

systemctl stop httpd.service
systemctl stop semipro-web.service

rm ${EXECUTE_FOLDER}${JAR_NAME}
cp ${RELEASE_FOLDER}${JAR_NAME} ${EXECUTE_FOLDER}
chown semipro.semipro ${EXECUTE_FOLDER}${JAR_NAME}
chmod 500 ${EXECUTE_FOLDER}${JAR_NAME}

systemctl start semipro-web.service
systemctl start httpd.service
