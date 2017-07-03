export BOT_TOKEN="$1"
export DATASTORE_APP_ID="$2"
if [ -e docker-compose ]
then
    ./docker-compose -f docker-compose-prod.yml down
    docker rm $(docker ps -a -q)
    docker rmi $(docker images -q)
else
    curl -L https://github.com/docker/compose/releases/download/1.14.0/docker-compose-Linux-x86_64 > docker-compose &&
    chmod +x docker-compose
fi
wget -q https://raw.githubusercontent.com/IngvarJackal/tgstickers/master/etc/prod/docker-compose-prod.yml -O docker-compose-prod.yml
./docker-compose -f docker-compose-prod.yml pull
nohup ./docker-compose -f docker-compose-prod.yml up > logs 2>&1 < /dev/null &