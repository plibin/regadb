#! /bin/sh

cd `echo $0 | sed 's/\/[^\/]*$/\//g'`

mkdir -p /soft/wts

mkdir /soft/wts/sessions
mkdir /soft/wts/users
cp wts-users.pwd /soft/wts/users.pwd

cp -R ../../../regadb-wts-services/ services

chown -R tomcat:tomcat /soft/wts