from flask import Flask,jsonify

app = Flask(__name__)




@app.route('/')
def index():
    return "Hello"

if __name__ == '__main__':
    app.run(host='127.0.0.1',port=8877)