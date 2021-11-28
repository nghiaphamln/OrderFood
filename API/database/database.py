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
