import bcrypt

password = "phamminhnghianehihi"
password = str.encode(password)
password_hashed = bcrypt.hashpw(password, bcrypt.gensalt())
print(password_hashed)

if bcrypt.checkpw(password, password_hashed):
    print("True")
else:
    print("False")
