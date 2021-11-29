import sqlite3
import bcrypt


def connect_to_db():
    conn = sqlite3.connect('./database/database.db')
    return conn


def create_db_table():
    try:
        conn = connect_to_db()
        conn.execute('''
            CREATE TABLE users (
                id INTEGER PRIMARY KEY NOT NULL,
                email TEXT NOT NULL,
                username TEXT NOT NULL,
                phone TEXT NOT NULL,
                password TEXT NOT NULL,
                otp TEXT
            );
        ''')

        conn.commit()
        print("User table created successfully")
    except Exception as ex:
        print("User table creation failed - {}".format(ex))
    finally:
        conn.close()


# create table categories
def create_categories_table():
    try:
        conn = connect_to_db()
        conn.execute('''
            CREATE TABLE categories (
                id INTEGER PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                image TEXT NOT NULL
            );
        ''')

        conn.commit()
        print("Category table created successfully")

    except Exception as ex:
        print("Category table creation failed - {}".format(ex))
    finally:
        conn.close()


# insert categories
def insert_categories():
    try:
        conn = connect_to_db()
        conn.execute('''
            INSERT INTO categories (name, image)
            VALUES ('Đồ ăn nhanh 1', '/static/category/cat_1.png'),
                   ('Đồ ăn nhanh 2', '/static/category/cat_2.png'),
                   ('Đồ ăn nhanh 3', '/static/category/cat_3.png'),
                   ('Nước', '/static/category/cat_4.png'),
                   ('Bánh ngọt', '/static/category/cat_5.png');
        ''')

        conn.commit()
        print("Categories inserted successfully")
    except Exception as ex:
        print("Categories insertion failed - {}".format(ex))
    finally:
        conn.close()


# create table products
def create_products_table():
    try:
        conn = connect_to_db()
        conn.execute('''
            CREATE TABLE products (
                id INTEGER PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                price REAL NOT NULL,
                image TEXT NOT NULL,
                description TEXT NOT NULL,
                category_id INTEGER NOT NULL,
                foreign key (category_id) references categories(id)
            );
        ''')

        conn.commit()
        print("Products table created successfully")
    except Exception as ex:
        print("Products table creation failed - {}".format(ex))
    finally:
        conn.close()


# insert into products
def insert_products():
    try:
        conn = connect_to_db()
        conn.execute('''
            INSERT INTO products (name, price, image, description, category_id)
            VALUES ('Bánh Piza nhỏ', '50000', '/static/product/pizza1.png', 'Đây là mô tả của Bánh pizza nhỏ', 1),
                   ('Bánh Piza lớn', '100000', '/static/product/pizza2.png', 'Đây là mô tả của Bánh pizza lớn', 1),
                   ('Bánh Burger nhỏ', '50000', '/static/product/burger1.png', 'Đây là mô tả của bánh Burger nhỏ', 1),
                   ('Bánh Burger nhỏ', '100000', '/static/product/burger2.png', 'Đây là mô tả của bánh Burger lớn', 1);
        ''')

        conn.commit()
        print("Categories inserted successfully")
    except Exception as ex:
        print("Categories insertion failed - {}".format(ex))
    finally:
        conn.close()
