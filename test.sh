export BOT_TOKEN=testtoken
cd commons &&
mvn clean install &&
cd .. &&
cd bl_service &&
mvn clean test &&
cd .. &&
cd in_service &&
mvn clean test &&
cd .. &&
cd out_service &&
mvn clean test
