import os.path

import database
from flask import jsonify
from werkzeug.utils import secure_filename
from os.path import join, dirname, realpath

UPLOADS_PATH = join(dirname(realpath(__file__)), 'static/product')


def add_product(data, image):
    try:
        category = data['category']
        name = data['name']
        price = data['price']
        description = data['description']
        if category and name and price and description and image:
            image_name = secure_filename(image.filename)
            image_path = f'/static/product/{image_name}'
            image.save(os.path.join(UPLOADS_PATH, image_name))
            database.add_product(category, name, price, description, image_path)
            return jsonify({'message': 'Product added successfully'}), 200
        else:
            return jsonify({'message': 'Bad Request - Invalid credendtials'}), 400
    except Exception as e:
        print(e)
        return jsonify({'message': 'Bad Request - Invalid credendtials'}), 400


