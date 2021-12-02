import json

from flask import Flask, request, jsonify
import user
import index
import admin
from flask_mail import Mail

app = Flask(__name__)
# config mail
app.config['MAIL_SERVER'] = 'smtp.gmail.com'
app.config['MAIL_PORT'] = 465
app.config['MAIL_USERNAME'] = '1824801040118@student.tdmu.edu.vn'
app.config['MAIL_PASSWORD'] = 'Nghiadz1!'
app.config['MAIL_USE_TLS'] = False
app.config['MAIL_USE_SSL'] = True
mail = Mail(app)


@app.route('/')
def hello_world():
    return 'Chào mừng các bạn đã đến với API do bạn Nghĩa code!!!'


# login
@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    return user.login(data)


# register
@app.route('/register', methods=['POST'])
def register():
    data = request.get_json()
    return user.register(data)


# send otp
@app.route('/send-otp', methods=['POST'])
def send_otp():
    data = request.get_json()
    return user.send_otp(data, mail)


# verify otp
@app.route('/verify-otp', methods=['POST'])
def verify_otp():
    data = request.get_json()
    return user.verify_otp(data)


# reset password
@app.route('/reset-password', methods=['POST'])
def reset_password():
    data = request.get_json()
    return user.reset_password(data)


# change password
@app.route('/change-password', methods=['POST'])
def change_password():
    data = request.get_json()
    return user.change_password(data)


# get user info
@app.route('/get-info', methods=['POST'])
def get_user_info():
    data = request.get_json()
    return user.get_user_details(data)


# get all categories
@app.route('/get-categories', methods=['GET'])
def get_categories():
    return index.get_categories()


# get products by category
@app.route('/get-products', methods=['POST'])
def get_products():
    data = request.get_json()
    return index.get_products_by_category(data)


# add product
@app.route('/add-product', methods=['POST'])
def add_product():
    image = request.files['image']
    data = json.loads(request.form.get("data"))
    return admin.add_product(data, image)


if __name__ == '__main__':
    app.run()
