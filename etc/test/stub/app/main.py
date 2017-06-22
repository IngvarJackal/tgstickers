from flask import Flask
from flask import request
import time
import threading
from flask.json import jsonify

app = Flask(__name__)

UPDATES_OFFSETS_LIST = []
UPDATES = [
    (
    {
        "update_id": 587894148,
        "message": {
            "message_id": 12,
            "from": {
                    "id": 175875879,
                    "first_name": "Werwr",
                    "last_name": "Ururu",
                    "username": "Werwer",
                    "language_code": "lv"
            },
            "chat": {
                "id": 175875879,
                "first_name": "Werwr",
                "last_name": "Ururu",
                "username": "Werwer",
                "type": "private"
            },
            "date": 1498035942,
            "sticker": {
                "width": 512,
                "height": 512,
                "emoji": ":)",
                "thumb": {
                    "file_id": "BvcBVCbvcxbc<BVC7-fPdmIttpAjAAIC",
                    "file_size": 4996,
                    "width": 128,
                    "height": 128
                },
                "file_id": "CAADAgADZbuvbuCBVHXBnhbvyxcuhvI",
                "file_size": 29930
            }
        }
    }
    ,),
    (
    {
        "update_id": 587894158,
        "message": {
            "message_id": 13,
            "from": {
                "id": 175875879,
                "first_name": "Werwr",
                "last_name": "Ururu",
                "username": "Werwer",
                "language_code": "lv"
            },
            "chat": {
                "id": 175875879,
                "first_name": "Werwr",
                "last_name": "Ururu",
                "username": "Werwer",
                "type": "private"
            },
            "date": 1498035942,
            "text": "testtagAAAA testtagBBBB"
        }
    }
    ,),
    (
    {
        "update_id": 587894168,
        "message": {
            "message_id": 14,
            "from": {
                "id": 175875879,
                "first_name": "Werwr",
                "last_name": "Ururu",
                "username": "Werwer",
                "language_code": "lv"
            },
            "chat": {
                "id": 175875879,
                "first_name": "Werwr",
                "last_name": "Ururu",
                "username": "Werwer",
                "type": "private"
            },
            "date": 1498035942,
            "text": "testtagCCCC"
        }
    }
    ,),
    (
    {
        "update_id": 587894178,
        "inline_query": {
            "id": "1875189181121120845",
            "from": {
                "id": 175875879,
                "first_name": "Werwr",
                "last_name": "Ururu",
                "username": "Werwer",
                "language_code": "lv"
            },
            "query": "hellow",
            "offset": ""
        }
    }
    ,),
    (
    {
        "update_id": 587894188,
        "inline_query": {
            "id": "1875189181121120845",
            "from": {
                "id": 175875879,
                "first_name": "Werwr",
                "last_name": "Ururu",
                "username": "Werwer",
                "language_code": "lv"
            },
            "query": "testtagAAA",
            "offset": ""
        }
    }
    ,),
    (
    {
        "update_id": 587894198,
        "inline_query": {
            "id": "1875189181121120845",
            "from": {
                "id": 175875879,
                "first_name": "Werwr",
                "last_name": "Ururu",
                "username": "Werwer",
                "language_code": "lv"
            },
            "query": "testtag",
            "offset": ""
        }
    }
    ,)
]



MESSAGE_RESPONSES = []
INLINE_RESPONSES = []

DELAYS = [0, 10, 2]
DELAY = 0
def getDelay():
    global DELAY, DELAYS
    if len(DELAYS) > 0:
        DELAY = DELAYS.pop(0)
    return DELAY


@app.route("/testbot/getUpdates", methods=['POST'])
def incomingMessages():
    global DELAY
    print("/getUpdates", request.values)
    time.sleep(getDelay())
    UPDATES_OFFSETS_LIST.append(int(getFromRequest("offset")))
    return jsonify({"ok":True, "result":getUpdate()})

@app.route("/testbot/answerInlineQuery", methods=['POST'])
def inlineQuery():
    print("/answerInlineQuery", request.values)
    INLINE_RESPONSES.append(request.values)
    return jsonify({"ok":True })

@app.route("/testbot/sendMessage", methods=['POST'])
def sendMessage():
    print("/sendMessage", request.values)
    MESSAGE_RESPONSES.append(request.values)
    return jsonify({"ok":True})

def stopperFunction():
    import time
    import os
    while len(UPDATES) > 0: time.sleep(2)
    time.sleep(10)
    print("++++++++++++++++++++++++++++++++++++++++++++ ENDING SIMULATION ++++++++++++++++++++++++++++++++++++++++++++")
    os.makedirs(os.path.dirname("/tmp/stubres/"), exist_ok=True)
    if os.path.isfile("/tmp/stubres/result.txt"):
        os.chmod("/tmp/stubres/result.txt", stat.S_IWRITE)
    with open("/tmp/stubres/result.txt", "w") as out:
        out.write("OK")


if __name__ == "__main__":
    stopperThread = threading.Thread(target=stopperFunction)
    stopperThread.start()
    app.run(host='0.0.0.0', debug=False, port=80)

def getUpdate():
    results = []
    while len(UPDATES) > 0:
        results = UPDATES.pop()
    return results

def getFromRequest(tag):
    result = None
    if result is None:
        result = request.args.get(tag)
    if result is None:
        result = request.form.get(tag)
    if result is None:
        result = request.get_json(force=True).get(tag)
    return result