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
docker-compose build
