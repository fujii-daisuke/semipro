#!/bin/bash
export FOLDER=/home/ec2-user/tmp/

if [ -d $FOLDER ]
then
 rm -rf $FOLDER
fi

mkdir -p $FOLDER