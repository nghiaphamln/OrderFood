from flask import jsonify
from database import connect_to_db


# get categories
def get_categories():
    try:
        conn = connect_to_db()
        cur = conn.cursor()
        row = cur.execute("SELECT * FROM categories").fetchall()
        conn.commit()
        data = []
        for i in row:
            data.append({
                'id': i[0],
                'name': i[1],
                'image': i[2]
            })
        return jsonify(data)
    except Exception as ex:
        print(ex)
        resp = jsonify({'message': 'Bad Request'})
        resp.status_code = 400
        return resp


# get products by category
def get_products_by_category(data):
    try:
        category = data['category']
        conn = connect_to_db()
        cur = conn.cursor()
        if str(category) == '0':
            row = cur.execute("SELECT * FROM products LIMIT 7").fetchall()
        else:
            row = cur.execute("SELECT * FROM products WHERE category_id = (?)", (category,)).fetchall()
        conn.commit()
        data = []
        for i in row:
            data.append({
                'id': i[0],
                'name': i[2],
                'image': i[5],
                'price': i[4],
                'description': i[3],
                'category_id': i[1]
            })
        return jsonify(data)
    except Exception as ex:
        print(ex)
        resp = jsonify({'message': 'Bad Request'})
        resp.status_code = 400
        return resp


