cd bl_service && mvn clean package && mvn docker:build && cd ..
cd in_service && mvn clean package && mvn docker:build && cd ..
cd out_service && mvn clean package && mvn docker:build && cd ..
