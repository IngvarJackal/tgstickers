[![Unit tests](https://api.travis-ci.org/IngvarJackal/tgstickers.svg?branch=master)](https://travis-ci.org/IngvarJackal/tgstickers)

# tgstickers
It's a telegram bot for custom stickers tags.

The main idea is to write own custom descriptions for telegram stickers and to use them via inline mode like ```@cnambot joy``` and it will show you stickers which you marked as "joy"

## Features
* Inline mode
* Fuzzy search

## How to run in compose
**Requires 1.12.0 docker-compose, to be installed separately**
* ```docker-compose up```
* set BOT_TOKEN env variable for inservice to fetch updates from the bot
* can set LOGGING_LEVEL, default is TRACE
* can set TELEGRAM_API (e.g. for CI), default is https://api.telegram.org/bot

## Architecture
![](https://raw.githubusercontent.com/IngvarJackal/tgstickers/master/doc/tgstickers.jpg)

## CI
DOCKER_USERNAME and DOCKER_PASSWORD env vars should be set