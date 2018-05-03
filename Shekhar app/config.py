import os

basedir = os.path.abspath(os.path.dirname(__file__))
# app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'employee.sqlite')
# app.config.setdefault('SQLALCHEMY_TRACK_MODIFICATIONS', True)

SQLALCHEMY_DATABASE_URI = 'sqlite:///' + os.path.join(basedir, 'employee.sqlite')
SQLALCHEMY_TRACK_MODIFICATIONS = True