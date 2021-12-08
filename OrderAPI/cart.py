from flask import jsonify
from database import connect_to_db


def add_to_order(data):
    try:
        username = data['username']
        name = data['name']
        phone = data['phone']
        total_price = data['total_price']
        address = data['address']
        detail = data['detail']

        if username and name and phone and total_price and address and detail:
            db = connect_to_db()
            cursor = db.cursor()
            row = cursor.execute("SELECT * FROM users WHERE username = (?)", (username,)).fetchone()
            print(row)
            cursor.execute("INSERT INTO orders (user_id, total_price, name, phone, address) VALUES (?, ?, ?, ?, ?)", (row[0], total_price, name, phone, address))
            order_id = cursor.lastrowid
            for item in detail:
                cursor.execute("INSERT INTO orders_details (order_id, product_id, quantity) VALUES (?, ?, ?)", (order_id, item['product_id'], item['quantity']))
            db.commit()
            return jsonify({'status': 'success'})
        else:
            return jsonify({'status': 'error', 'message': 'Missing data'}), 400
    except Exception as e:
        print(e)
        return jsonify({'status': 'error', 'message': str(e)}), 400
