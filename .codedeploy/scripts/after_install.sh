#!/bin/bash
export RELEASE_DIR=/tmp/semipro-release/

export WEB_EXECUTE_DIR=/usr/local/bin/semipro-web/
export WEB_JAR_NAME=semipro-web.jar
export WEB_EXECUTE_PATH=${WEB_EXECUTE_DIR}${WEB_JAR_NAME}

export ADMIN_EXECUTE_DIR=/usr/local/bin/semipro-admin/
export ADMIN_JAR_NAME=semipro-admin.jar
export ADMIN_EXECUTE_PATH=${ADMIN_EXECUTE_DIR}${ADMIN_JAR_NAME}

export BATCH_EXECUTE_DIR=/usr/local/bin/semipro-batch/
export BATCH_JAR_NAME=semipro-batch.jar
export BATCH_EXECUTE_PATH=${BATCH_EXECUTE_DIR}${BATCH_JAR_NAME}

systemctl stop httpd.service
systemctl stop semipro-web.servicee
systemctl stop semipro-admin.service
systemctl stop semipro-batch.service

rm ${WEB_EXECUTE_PATH}
cp ${RELEASE_DIR}${WEB_JAR_NAME} ${WEB_EXECUTE_DIR}
chown semipro.semipro ${WEB_EXECUTE_PATH}
chmod 500 ${WEB_EXECUTE_PATH}

rm ${ADMIN_EXECUTE_DIR}${ADMIN_JAR_NAME}
cp ${RELEASE_DIR}${ADMIN_JAR_NAME} ${ADMIN_EXECUTE_DIR}
chown semipro.semipro ${ADMIN_EXECUTE_PATH}
chmod 500 ${ADMIN_EXECUTE_PATH}

rm ${BATCH_EXECUTE_DIR}${BATCH_JAR_NAME}
cp ${RELEASE_DIR}${BATCH_JAR_NAME} ${BATCH_EXECUTE_DIR}
chown semipro.semipro ${BATCH_EXECUTE_PATH}
chmod 500 ${BATCH_EXECUTE_PATH}

systemctl start semipro-web.service
systemctl start semipro-admin.service
systemctl start semipro-batch.service
systemctl start httpd.service
