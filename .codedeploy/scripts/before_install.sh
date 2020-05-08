#!/bin/bash

systemctl stop httpd.service

export FOLDER=/tmp/semipro-release/

if [ -d $FOLDER ]
then
 rm -rf $FOLDER
fi

mkdir -p $FOLDER