cd commons && mvn clean install && cd ..
cd web_root && mvn clean install && cd ..
cd bl_service && mvn clean package && mvn docker:build && cd ..
cd in_service && mvn clean package && mvn docker:build && cd ..
cd out_service && mvn clean package && mvn docker:build && cd ..
docker push ingvarjackal/tgstickers-blservice
docker push ingvarjackal/tgstickers-inservice
docker push ingvarjackal/tgstickers-outservice
