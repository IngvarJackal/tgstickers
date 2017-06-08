if [ -z "$1" ]
  then
    COMPOSE_FILE_PATH="etc/dev/docker-compose.yml"
  else
    COMPOSE_FILE_PATH="$1"
fi

cd commons &&
mvn clean install &&
cd .. &&
cd bl_service &&
mvn clean package &&
rm -r src/main/docker/target/
cp -r target/ src/main/docker/ &&
cd .. &&
cd in_service &&
mvn clean package &&
rm -r src/main/docker/target/
cp -r target/ src/main/docker/ &&
cd .. &&
cd out_service &&
mvn clean package &&
rm -r src/main/docker/target/
cp -r target/ src/main/docker/ &&
cd .. &&
docker rmi -f tgstickers_inservice
docker rmi -f tgstickers_blservice
docker rmi -f tgstickers_outservice
echo "COMPOSE_FILE_PATH=$COMPOSE_FILE_PATH"
docker-compose -f "$COMPOSE_FILE_PATH" --project-directory . build
