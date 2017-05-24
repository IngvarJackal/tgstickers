export BOT_TOKEN=testtoken
cd commons &&
mvn clean install &&
cd .. &&
cd bl_service &&
mvn clean test &&
rm -r src/main/docker/target/ &&
cp -r target/ src/main/docker/ &&
cd .. &&
cd in_service &&
mvn clean test &&
rm -r src/main/docker/target/ &&
cp -r target/ src/main/docker/ &&
cd .. &&
cd out_service &&
mvn clean test
