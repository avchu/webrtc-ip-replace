#!/bin/bash
source /root/.sdkman/bin/sdkman-init.sh
sdk install java 8.0.422-amzn
sdk install maven
cd webrtc-ip-replace
sdk use java 8.0.422-amzn
#mvn clean install
