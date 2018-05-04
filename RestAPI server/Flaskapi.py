from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_marshmallow import Marshmallow
import os

app = Flask(__name__)
"""Moved the below code to config.py"""
# basedir = os.path.abspath(os.path.dirname(__file__))

# app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'employee.sqlite')
# app.config.setdefault('SQLALCHEMY_TRACK_MODIFICATIONS', True)

app.config.from_pyfile("config.py")

db = SQLAlchemy(app)
ma = Marshmallow(app)


class Employee(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(80))
    email = db.Column(db.String(120), unique=True)
    role = db.Column(db.String(30))

    def __init__(self, name, email, role):
        self.name = name
        self.email = email
        self.role = role


class UserSchema(ma.Schema):
    class Meta:
        fields = ('name', 'email', 'role')


user_schema = UserSchema()
users_schema = UserSchema(many=True)


@app.route("/employee", methods=["POST"])
def add_user():
    name = request.json['name']
    email = request.json['email']
    role = request.json['role']

    new_user = Employee(name, email, role)

    db.session.add(new_user)
    db.session.commit()

    return jsonify(new_user.name, new_user.email, new_user.role)


@app.route("/employee", methods=["GET"])
def get_user():
    all_users = Employee.query.all()
    result = users_schema.dump(all_users)
    return jsonify(result.data)


@app.route("/employee/<id>", methods=["GET"])
def user_detail(id):
    user = Employee.query.get(id)
    return user_schema.jsonify(user)


@app.route("/employee/<id>", methods=["PUT"])
def user_update(id):
    user = Employee.query.get(id)
    name = request.json['name']
    email = request.json['email']
    role = request.json['role']

    user.email = email
    user.name = name
    user.role = role

    db.session.commit()
    return user_schema.jsonify(user)


@app.route("/employee/<id>", methods=["DELETE"])
def user_delete(id):
    user = Employee.query.get(id)
    db.session.delete(user)
    db.session.commit()

    return user_schema.jsonify(user)


db.create_all()

if __name__ == '__main__':
    app.run(debug=True)
    
