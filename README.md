# tgstickers
It's a telegram bot for custom stickers tags.

The main idea is to write own custom descriptions for telegram stickers and to use them via inline mode like ```@cnambot joy``` and it will show you stickers which you marked as "joy"

## Features
* Inline mode
* Word stemmatization for selected languages (if selected)
* Fuzzy search (if selected)
* Global search (if selected)

## How to run
**Requires docker v1.12.0 or later fow swarm mode**
* ```docker swarm init --advertise-addr 127.0.0.1``` to init dev swarm on localhost
* ```docker swarm join --token <INSERT TOKEN HERE> 127.0.0.1:2377``` to join swarm as master
* ```docker stack deploy --compose-file docker-stack.yml tgstickers``` to deploy in master **tgstickers** app

## Architecture and technologies
![alt text](https://github.com/IngvarJackal/tgstickers/blob/master/doc/diagram.png)
