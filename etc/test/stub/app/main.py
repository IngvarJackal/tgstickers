from flask import Flask
from flask import request
import time
app = Flask(__name__)

UPDATES_OFFSETS_LIST = []
UPDATES = []
MESSAGE_RESPONSES = []
INLINE_RESPONSES = []



@app.route("/testbot/getUpdates", methods=['POST'])
def incomingMessages():
    print("/getUpdates", request.values)
    time.sleep(1)
    return """ {"ok":true, "result":""" + getUpdate(int(get("offset"))) + """} """

@app.route("/testbot/answerInlineQuery", methods=['POST'])
def incomingMessages():
    print("/answerInlineQuery", request.values)
    INLINE_RESPONSES.append(request.get_json(force=True))
    return """ {"ok":true } """

@app.route("/testbot/sendMessage", methods=['POST'])
def incomingMessages():
    print("/sendMessage", request.values)
    MESSAGE_RESPONSES.append(request.get_json(force=True))
    return """ {"ok":true } """

if __name__ == "__main__":
    app.run(host='0.0.0.0', debug=False, port=80)

def getUpdate(offset):
    results = []
    while len(UPDATES) > 0 and UPDATES[0][0] <= offset:
        results.append(UPDATES.pop(0)[1])
    return "[" + ", ".join(results) + "]"

def getFromRequest(tag):
    result = None
    if result is None:
        result = request.args.get(tag)
    if result is None:
        result = request.form.get(tag)
    if result is None:
        result = request.get_json(force=True).get(tag)
    return result