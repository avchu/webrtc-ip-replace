#!/bin/bash
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JAVA_HOME/lib/server
source /root/.sdkman/bin/sdkman-init.sh
sdk install java 17.0.12-tem
sdk install maven
cd webrtc-ip-replace && mvn clean install
