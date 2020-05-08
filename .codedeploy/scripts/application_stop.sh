#!/bin/bash

systemctl stop httpd.service
systemctl stop semipro-web.service
systemctl stop semipro-admin.service
systemctl stop semipro-batch.service
