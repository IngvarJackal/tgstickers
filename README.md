[![Unit tests](https://api.travis-ci.org/IngvarJackal/tgstickers.svg?branch=master)](https://travis-ci.org/IngvarJackal/tgstickers)
[<img src="https://haydenjames.io/wp-content/uploads/2016/09/uptimerobot_logo-300x92.png" alt="Prod status" height="24">](https://stats.uptimerobot.com/x6BEMhZG7)

# tgstickers
It's a telegram bot for custom stickers tags.

The main idea is to write own custom descriptions for telegram stickers and to use them via inline mode like ```@cnamebot joy``` and it will show you stickers which you marked as "joy". If added ```.``` before tags, it will search stickers with all tags, otherwise -- stickers with any specified tag.

## Features
* Inline mode
* Fuzzy search

## Architecture
![](https://raw.githubusercontent.com/IngvarJackal/tgstickers/master/doc/tgstickers.png)

## How to run in compose
**Requires 1.12.0 docker-compose, to be installed separately**
* ```docker-compose -f etc/test/docker-compose-test.yml --project-directory . up```
* set BOT_TOKEN env variable for inservice to fetch updates from the bot
* can set LOGGING_LEVEL, default is TRACE but with no HTTP logging
* can set TELEGRAM_API (e.g. for CI), default is https://api.telegram.org/bot

## Travis setup
Env vars:
* DOCKER_USERNAME (for docker hub)
* DOCKER_PASSWORD (for docker hub)
* APP_ID (for delivery phase)
* BOT_TOKEN (for delivery phase)
* SSH_CREDENTIALS (for delivery phase)
* SENTRY_DSN (for delivery phase)

There's SSH private key for delivery phase: [deployment_key.enc](https://github.com/IngvarJackal/tgstickers/blob/master/deployment_key.enc). It's deciphered by travis.

See [etc/test/test.py](https://github.com/IngvarJackal/tgstickers/blob/master/etc/test/test.py) for continious delivery details.

All scripts are designed to be ran from root dir.

## Server setup (Linux)
* Docker
* Open ports 10000 (inservice), 10001 (blservice) and 10002 (outservice) for healthchecks
