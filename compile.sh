#!/bin/bash
source /home/chrome/.sdkman/bin/sdkman-init.sh
sdk install java 17.0.12-tem
sdk install maven
sdk use java 17.0.12-tem
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JAVA_HOME/lib/server
cd /tmp/webrtc-ip-replace && mvn clean install
