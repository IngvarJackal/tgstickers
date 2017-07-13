export BOT_TOKEN="$1"
export DATASTORE_APP_ID="$2"
export SENTRY_DSN="$3"
export HOSTNAME=$(hostname)
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
kill `ps -aux | grep inservice_keeper | grep -v grep | awk '{ print $2 }'` 2> /dev/null
kill `ps -aux | grep blservice_keeper | grep -v grep | awk '{ print $2 }'` 2> /dev/null
kill `ps -aux | grep outservice_keeper | grep -v grep | awk '{ print $2 }'` 2> /dev/null
nohup ./docker-compose -f docker-compose-prod.yml up > logs 2>&1 < /dev/null &
echo 'while true; do sleep 60; if [[ "OK" == $(curl localhost:10000 2> /dev/null) ]] ; then echo $(date) "OK"; else echo $(date) "NOK"; ./docker-compose -f docker-compose-prod.yml restart inservice; done; fi' > inservice_keeper.sh
echo 'while true; do sleep 60; if [[ "OK" == $(curl localhost:10001 2> /dev/null) ]] ; then echo $(date) "OK"; else echo $(date) "NOK"; ./docker-compose -f docker-compose-prod.yml restart blservice; done; fi' > blservice_keeper.sh
echo 'while true; do sleep 60; if [[ "OK" == $(curl localhost:10002 2> /dev/null) ]] ; then echo $(date) "OK"; else echo $(date) "NOK"; ./docker-compose -f docker-compose-prod.yml restart outservice; done; fi' > outservice_keeper.sh
nohup sh inservice_keeper.sh > inservice_keeper_logs 2>&1 < /dev/null &
nohup sh blservice_keeper.sh > blservice_keeper_logs 2>&1 < /dev/null &
nohup sh outservice_keeper.sh > outservice_keeper_logs 2>&1 < /dev/null &