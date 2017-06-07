from flask import Flask
app = Flask(__name__)

@app.route("/bottestbot/getUpdates")
def incomingMessages():
    print("BOT GETTING UPDATES")
    return "[]"

if __name__ == "__main__":
    app.run(host='0.0.0.0', debug=False, port=8080)