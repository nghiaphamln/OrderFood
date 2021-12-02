from flask import jsonify
from database import connect_to_db
import bcrypt
import random
from flask_mail import Message


# login
def login(data):
    try:
        username = data['username']
        password = data['password']
        if username and password:
            conn = connect_to_db()
            cur = conn.cursor()
            # check if user exists
            row = cur.execute("SELECT * FROM users WHERE username = (?)", (username, )).fetchone()
            conn.commit()
            if row:
                # check password
                if bcrypt.checkpw(str.encode(password), row[4]):
                    return jsonify({'message': 'Login successful'})
                else:
                    resp = jsonify({'message': 'Bad Request - Login failed'})
                    resp.status_code = 400
                    return resp
            else:
                resp = jsonify({'message': 'Bad Request - Login failed'})
                resp.status_code = 400
                return resp
        else:
            resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
            resp.status_code = 400
            return resp
    except Exception as ex:
        print(ex)
        resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
        resp.status_code = 400
        return resp


# register
def register(data):
    try:
        email = data['email']
        username = data['username']
        phone = data['phone']
        password = data['password']
        if email and username and phone and password:
            conn = connect_to_db()
            cur = conn.cursor()
            # check if user exists
            row = cur.execute("SELECT * FROM users WHERE username = (?) OR email = (?) OR phone = (?)", (username, email, phone)).fetchone()
            conn.commit()
            if not row:
                # hash password
                password = bcrypt.hashpw(str.encode(password), bcrypt.gensalt())
                # insert into db
                cur.execute("INSERT INTO users (email, username, phone, password, permission) VALUES (?, ?, ?, ?, ?)", (email, username, phone, password, 'user'))
                conn.commit()
                return jsonify({'message': 'Registration successful'})
            else:
                resp = jsonify({'message': 'Bad Request - Registration failed'})
                resp.status_code = 400
                return resp
        else:
            resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
            resp.status_code = 400
            return resp
    except Exception as ex:
        print(ex)
        resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
        resp.status_code = 400
        return resp


# send otp
def send_otp(data, mail):
    try:
        email = data['email']
        if email:
            conn = connect_to_db()
            cur = conn.cursor()
            # check if user exists
            row = cur.execute("SELECT * FROM users WHERE email = (?)", (email, )).fetchone()
            conn.commit()
            if row:
                # generate otp
                otp = ''.join(str(random.randint(0, 9)) for i in range(6))
                # send otp
                msg = Message('RESET PASSWORD', sender='1824801040118@student.tdmu.edu.vn', recipients=[email])
                msg.body = "Sử dụng OTP: {} để thay đổi mật khẩu!".format(otp)
                mail.send(msg)
                # update otp
                cur.execute("UPDATE users SET otp = (?) WHERE email = (?)", (otp, email))
                conn.commit()
                return jsonify({'message': 'OTP sent'})
            else:
                resp = jsonify({'message': 'Bad Request - Invalid email'})
                resp.status_code = 400
                return resp
        else:
            resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
            resp.status_code = 400
            return resp
    except Exception as ex:
        print(ex)
        resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
        resp.status_code = 400
        return resp


# verify otp
def verify_otp(data):
    try:
        email = data['email']
        otp = data['otp']
        if email and otp:
            conn = connect_to_db()
            cur = conn.cursor()
            # check if user exists
            row = cur.execute("SELECT * FROM users WHERE email = (?)", (email, )).fetchone()
            conn.commit()
            if row:
                # check otp
                if row[6] == otp:
                    return jsonify({'message': 'OTP verified'})
                else:
                    resp = jsonify({'message': 'Bad Request - Invalid OTP'})
                    resp.status_code = 400
                    return resp
            else:
                resp = jsonify({'message': 'Bad Request - Invalid email'})
                resp.status_code = 400
                return resp
        else:
            resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
            resp.status_code = 400
            return resp
    except Exception as ex:
        print(ex)
        resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
        resp.status_code = 400
        return resp


# reset password
def reset_password(data):
    try:
        email = data['email']
        password = data['password']
        otp = data['otp']
        if email and password and otp:
            conn = connect_to_db()
            cur = conn.cursor()
            # check if user exists
            row = cur.execute("SELECT * FROM users WHERE email = (?)", (email, )).fetchone()
            conn.commit()
            if row:
                # check otp
                if row[6] == otp:
                    # hash password
                    password = bcrypt.hashpw(str.encode(password), bcrypt.gensalt())
                    # update password
                    cur.execute("UPDATE users SET password = (?) WHERE email = (?)", (password, email))
                    conn.commit()
                    return jsonify({'message': 'Password reset successful'})
                else:
                    resp = jsonify({'message': 'Bad Request - Invalid OTP'})
                    resp.status_code = 400
                    return resp
            else:
                resp = jsonify({'message': 'Bad Request - Invalid email'})
                resp.status_code = 400
                return resp
        else:
            resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
            resp.status_code = 400
            return resp
    except Exception as ex:
        print(ex)
        resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
        resp.status_code = 400
        return resp


# change password
def change_password(data):
    try:
        username = data['username']
        old_password = data['old_password']
        new_password = data['new_password']
        if username and old_password and new_password:
            conn = connect_to_db()
            cur = conn.cursor()
            # check if user exists
            row = cur.execute("SELECT * FROM users WHERE username = (?)", (username, )).fetchone()
            conn.commit()
            if row:
                # check password
                if bcrypt.checkpw(str.encode(old_password), row[4]):
                    # hash password
                    password = bcrypt.hashpw(str.encode(new_password), bcrypt.gensalt())
                    # update password
                    cur.execute("UPDATE users SET password = (?) WHERE username = (?)", (password, username))
                    conn.commit()
                    return jsonify({'message': 'Password changed successful'})
                else:
                    resp = jsonify({'message': 'Bad Request - Invalid password'})
                    resp.status_code = 400
                    return resp
            else:
                resp = jsonify({'message': 'Bad Request - Invalid email'})
                resp.status_code = 400
                return resp
        else:
            resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
            resp.status_code = 400
            return resp
    except Exception as ex:
        print(ex)
        resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
        resp.status_code = 400
        return resp


# get user details
def get_user_details(data):
    try:
        username = data['username']
        if username:
            conn = connect_to_db()
            cur = conn.cursor()
            # check if user exists
            row = cur.execute("SELECT * FROM users WHERE username = (?)", (username, )).fetchone()
            conn.commit()
            if row:
                return jsonify({
                    'username': row[2],
                    'email': row[1],
                    'phone': row[3],
                    'permission': row[5]
                })
            else:
                resp = jsonify({'message': 'Bad Request - Invalid username'})
                resp.status_code = 400
                return resp
        else:
            resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
            resp.status_code = 400
            return resp
    except Exception as ex:
        print(ex)
        resp = jsonify({'message': 'Bad Request - Invalid credendtials'})
        resp.status_code = 400
        return resp
