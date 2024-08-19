#!/bin/bash
source /root/.sdkman/bin/sdkman-init.sh
sdk install java 8.0.422-amzn
sdk install maven
git clone https://github.com/avchu/webrtc-ip-replace
cd webrtc-ip-replace && mvn clean install
