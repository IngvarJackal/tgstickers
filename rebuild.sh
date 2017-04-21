cd commons
mvn clean install
cd ..
cd web_root
mvn clean install
cd ..
cd bl_service
mvn clean package
rm -r src/main/docker/target/
cp -r target/ src/main/docker/
cd ..
cd in_service
mvn clean package
rm -r src/main/docker/target/
cp -r target/ src/main/docker/
cd ..
cd out_service
mvn clean package
rm -r src/main/docker/target/
cp -r target/ src/main/docker/
cd ..
docker rmi -f tgstickers_inservice
docker rmi -f tgstickers_blservice
docker rmi -f tgstickers_outservice
docker-compose build
