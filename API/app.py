from flask import Flask, jsonify, request, session
import bcrypt
from database import database
from flask_mail import Mail, Message
from random import randint

app = Flask(__name__)
app.secret_key = 'xin_chao_moi_nguoi_day_la_secret_key_do_may_ban_doan_ra_duoc_do'
# config mail
app.config['MAIL_SERVER'] = 'smtp.gmail.com'
app.config['MAIL_PORT'] = 465
app.config['MAIL_USERNAME'] = '1824801040118@student.tdmu.edu.vn'
app.config['MAIL_PASSWORD'] = 'Nghiadz1!'
app.config['MAIL_USE_TLS'] = False
app.config['MAIL_USE_SSL'] = True
mail = Mail(app)


@app.route('/')
def home():
    if 'username' in session:
        username = session['username']
        return jsonify({
            'message': 'You are already logged in!',
            'username': username
        })
    else:
        resp = jsonify({'message': 'Unauthorized'})
        resp.status = 401
        return resp


@app.route('/send-otp', methods=['POST'])
def send_email():
    data = request.get_json()
    try:
        email = data['email']
        if email:
            # check email exist
            conn = database.connect_to_db()
            cur = conn.cursor()
            row = cur.execute("SELECT * FROM users WHERE email=(?)", (email, )).fetchone()
            conn.commit()
            if row:
                # create otp
                otp = randint(100000, 999999)
                # insert otp to database
                cur.execute("UPDATE users SET otp=(?) WHERE email=(?)", (otp, email))
                conn.commit()
                # send email
                msg = Message('RESET PASSWORD', sender='1824801040118@student.tdmu.edu.vn', recipients=[email])
                msg.body = "Sử dụng OTP: {} để thay đổi mật khẩu!".format(otp)
                mail.send(msg)
                return jsonify({
                    'message': 'Sent'
                })
            else:
                resp = jsonify({'message': 'Bad Request - invalid email'})
                resp.status_code = 400
                return resp
    except Exception as e:
        print(e)
        resp = jsonify({'message': 'Bad Request - invalid credendtials'})
        resp.status_code = 400
        return resp


# check otp
@app.route('/check-otp', methods=['POST'])
def check_otp():
    data = request.get_json()
    try:
        email = data['email']
        otp = data['otp']
        if email and otp:
            conn = database.connect_to_db()
            cur = conn.cursor()
            row = cur.execute("SELECT * FROM users WHERE email=(?)", (email, )).fetchone()
            conn.commit()
            if row:
                if row[5] == otp:
                    return jsonify({
                        'message': 'Valid otp'
                    })
                else:
                    resp = jsonify({'message': 'Bad Request - invalid otp'})
                    resp.status_code = 400
                    return resp
            else:
                resp = jsonify({'message': 'Bad Request - invalid email'})
                resp.status_code = 400
                return resp
    except Exception as e:
        print(e)
        resp = jsonify({'message': 'Bad Request - invalid credendtials'})
        resp.status_code = 400
        return resp


@app.route('/reset-password', methods=['POST'])
def reset_password():
    data = request.get_json()
    try:
        email = data['email']
        otp = data['otp']
        password = data['password']
        if email and otp and password:
            conn = database.connect_to_db()
            cur = conn.cursor()
            row = cur.execute("SELECT * FROM users WHERE email=(?)", (email, )).fetchone()
            conn.commit()
            if row:
                if row[5] == otp:
                    # hash password
                    hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt())
                    # update password and otp to database
                    cur.execute("UPDATE users SET password=(?), otp=(?) WHERE email=(?)", (hashed_password, 0, email))
                    conn.commit()
                    return jsonify({
                        'message': 'Password updated'
                    })
                else:
                    resp = jsonify({'message': 'Bad Request - invalid otp'})
                    resp.status_code = 400
                    return resp
            else:
                resp = jsonify({'message': 'Bad Request - invalid email'})
                resp.status_code = 400
                return resp
    except Exception as e:
        print(e)
        resp = jsonify({'message': 'Bad Request - invalid credendtials'})
        resp.status_code = 400
        return resp


# get information
@app.route('/get-info', methods=['POST'])
def get_info():
    data = request.get_json()
    try:
        username = data['username']
        if username:
            conn = database.connect_to_db()
            cur = conn.cursor()
            row = cur.execute("SELECT * FROM users WHERE username=(?)", (username, )).fetchone()
            conn.commit()
            if row:
                return jsonify({
                    'username': row[2],
                    'email': row[1],
                    'phone': row[3]
                })
            else:
                resp = jsonify({'message': 'Bad Request - invalid username'})
                resp.status_code = 400
                return resp
    except Exception as e:
        print(e)
        resp = jsonify({'message': 'Bad Request - invalid credendtials'})
        resp.status_code = 400
        return resp


@app.route('/login', methods=['POST'])
def login():
    try:
        data = request.get_json()
        username = data['username']
        password = data['password']

        # validate the received values
        if username and password:
            conn = database.connect_to_db()
            cur = conn.cursor()
            # query
            row = cur.execute("SELECT * FROM users WHERE username=(?)", (username, )).fetchone()
            conn.commit()
            if row:
                if bcrypt.checkpw(str.encode(password), row[4]):
                    session['username'] = row[2]
                    return jsonify({
                        'message': 'You are logged in successfully'
                    })
                else:
                    resp = jsonify({'message': 'Bad Request - invalid password'})
                    resp.status_code = 400
                    return resp
            else:
                resp = jsonify({'message': 'Bad Request - invalid username'})
                resp.status_code = 400
                return resp

        else:
            resp = jsonify({'message': 'Bad Request - invalid credendtials'})
            resp.status_code = 400
            return resp

    except Exception as ex:
        print(ex)


@app.route('/register', methods=['POST'])
def register():
    try:
        data = request.get_json()
        email = data['email']
        username = data['username']
        phone = data['phone']
        password = data['password']

        if username and password and phone and email:
            password = bcrypt.hashpw(str.encode(password), bcrypt.gensalt())
            conn = database.connect_to_db()
            cur = conn.cursor()

            row = cur.execute("SELECT * FROM users WHERE username=(?)", (username, )).fetchall()
            conn.commit()
            if row:
                resp = jsonify({
                    'message': 'Bad Request - account already exist'
                })
                resp.status_code = 400
                return resp
            else:
                cur.execute("INSERT INTO users (email, username, phone, password) VALUES (?, ?, ?, ?)",
                            (email, username, phone, password))
                conn.commit()
                return jsonify({
                    'message': 'Account successfully created'
                })
        else:
            resp = jsonify({'message': 'Bad Request - invalid credendtials'})
            resp.status_code = 400
            return resp
    except Exception as ex:
        print(ex)
        resp = jsonify({'message': 'Bad Request - invalid credendtials'})
        resp.status_code = 400
        return resp


@app.route('/logout')
def logout():
    if 'username' in session:
        session.pop('username', None)
    return jsonify({'message': 'You successfully logged out'})


# change password
@app.route('/change-password', methods=['POST'])
def change_password():
    try:
        data = request.get_json()
        username = data['username']
        old_password = data['old_password']
        new_password = data['new_password']

        if username and old_password and new_password:
            conn = database.connect_to_db()
            cur = conn.cursor()
            row = cur.execute("SELECT * FROM users WHERE username=(?)", (username, )).fetchone()
            conn.commit()
            if row:
                if bcrypt.checkpw(str.encode(old_password), row[4]):
                    new_password = bcrypt.hashpw(str.encode(new_password), bcrypt.gensalt())
                    cur.execute("UPDATE users SET password=(?) WHERE username=(?)", (new_password, username))
                    conn.commit()
                    return jsonify({
                        'message': 'Password successfully changed'
                    })
                else:
                    resp = jsonify({'message': 'Bad Request - invalid password'})
                    resp.status_code = 400
                    return resp
            else:
                resp = jsonify({'message': 'Bad Request - invalid username'})
                resp.status_code = 400
                return resp
        else:
            resp = jsonify({'message': 'Bad Request - invalid credendtials'})
            resp.status_code = 400
            return resp
    except Exception as ex:
        print(ex)
        resp = jsonify({'message': 'Bad Request - invalid credendtials'})
        resp.status_code = 400
        return resp


if __name__ == '__main__':
    app.run()
