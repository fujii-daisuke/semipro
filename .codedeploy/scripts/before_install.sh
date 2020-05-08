#!/bin/bash

systemctl stop httpd.service
systemctl stop semipro-web.servicee
systemctl stop semipro-admin.service
systemctl stop semipro-batch.service

export FOLDER=/tmp/semipro-release/

if [ -d $FOLDER ]
then
 rm -rf $FOLDER
fi

mkdir -p $FOLDER