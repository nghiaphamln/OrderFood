import sqlite3
import bcrypt


# connect to database
def connect_to_db():
    conn = sqlite3.connect('./database/database.db')
    return conn


# create users table
def create_users_table():
    conn = connect_to_db()
    conn.execute('''
                CREATE TABLE users (
                    id INTEGER PRIMARY KEY NOT NULL,
                    email TEXT NOT NULL,
                    username TEXT NOT NULL,
                    phone TEXT NOT NULL,
                    password TEXT NOT NULL,
                    permission TEXT NOT NULL,
                    otp TEXT
                );
            ''')

    conn.commit()
    print("User table created successfully")


# create categories table
def create_categories_table():
    conn = connect_to_db()
    conn.execute('''
                CREATE TABLE categories (
                    id INTEGER PRIMARY KEY NOT NULL,
                    category TEXT NOT NULL,
                    image TEXT NOT NULL
                );
            ''')

    conn.commit()
    print("Category table created successfully")


# create products table
def create_products_table():
    conn = connect_to_db()
    conn.execute('''
                CREATE TABLE products (
                    id INTEGER PRIMARY KEY NOT NULL,
                    category_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    description TEXT NOT NULL,
                    price INTEGER NOT NULL,
                    image TEXT NOT NULL,
                    FOREIGN KEY (category_id) REFERENCES categories(id)
                );
            ''')

    conn.commit()
    print("Product table created successfully")


# create orders table
def create_orders_table():
    conn = connect_to_db()
    conn.execute('''
                CREATE TABLE orders (
                    id INTEGER PRIMARY KEY NOT NULL,
                    user_id INTEGER NOT NULL,
                    product_id INTEGER NOT NULL,
                    total_price INTEGER NOT NULL,
                    FOREIGN KEY (user_id) REFERENCES users(id),
                    FOREIGN KEY (product_id) REFERENCES products(id)
                );
            ''')

    conn.commit()
    print("Order table created successfully")


# create orders_details table
def create_orders_details_table():
    conn = connect_to_db()
    conn.execute('''
                CREATE TABLE orders_details (
                    id INTEGER PRIMARY KEY NOT NULL,
                    order_id INTEGER NOT NULL,
                    product_id INTEGER NOT NULL,
                    quantity INTEGER NOT NULL,
                    FOREIGN KEY (order_id) REFERENCES orders(id),
                    FOREIGN KEY (product_id) REFERENCES products(id)
                );
            ''')

    conn.commit()
    print("Order details table created successfully")


# insert categories
def insert_categories():
    try:
        conn = connect_to_db()
        conn.execute('''
            INSERT INTO categories (category, image)
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


# insert into products
def insert_products():
    try:
        conn = connect_to_db()
        conn.execute('''
            INSERT INTO products (name, price, image, description, category_id)
            VALUES ('Bánh Piza nhỏ', '50000', '/static/product/pizza1.png', 'Đây là mô tả của Bánh pizza nhỏ', 1),
                   ('Bánh Piza lớn', '100000', '/static/product/pizza2.png', 'Đây là mô tả của Bánh pizza lớn', 1),
                   ('Bánh Burger nhỏ', '50000', '/static/product/burger1.png', 'Đây là mô tả của bánh Burger nhỏ', 2),
                   ('Bánh Burger nhỏ', '100000', '/static/product/burger2.png', 'Đây là mô tả của bánh Burger lớn', 2);
        ''')

        conn.commit()
        print("Categories inserted successfully")
    except Exception as ex:
        print("Categories insertion failed - {}".format(ex))
    finally:
        conn.close()


# add product
def add_product(category_id, name, description, price, image):
    try:
        conn = connect_to_db()
        conn.execute('''
            INSERT INTO products (category_id, name, description, price, image)
            VALUES (?, ?, ?, ?, ?)
        ''', (category_id, name, description, price, image))
        conn.commit()
        print("Product inserted successfully")
    except Exception as ex:
        print("Product insertion failed - {}".format(ex))
    finally:
        conn.close()


"""
create_users_table()
create_categories_table()
create_products_table()
create_orders_table()
create_orders_details_table()"""
# insert_categories()
# insert_products()
