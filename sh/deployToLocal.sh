#!/bin/bash

mvn clean package

rm ~/microprograms/osgi-framework-launcher/bundle/module_micro_api_qrcode-*.jar
cp target/module_micro_api_qrcode-*.jar ~/microprograms/osgi-framework-launcher/bundle/

cd ~/microprograms/osgi-framework-launcher/
sh bin/restart.sh