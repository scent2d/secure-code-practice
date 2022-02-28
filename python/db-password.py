'''
취약점 개요
DB 연결 시 null 비밀번호를 사용하거나, 하드코딩처리 해선 안된다.

- 환경변수에 저장된 값을 들고오는 방식으로 처리한다.

'''

# Noncompliant Code Example
# Flask-SQLAlchemy
def configure_app(app):
    app.config['SQLALCHEMY_DATABASE_URI'] = "postgresql://user:@domain.com" # Noncompliant

# Django - settings.py
DATABASES = {
    'postgresql_db': {
        'ENGINE': 'django.db.backends.postgresql',
        'NAME': 'quickdb',
        'USER': 'sonarsource',
        'PASSWORD': '', # Noncompliant
        'HOST': 'localhost',
        'PORT': '5432'
    }
}

# mysql/mysql-connector-python
from mysql.connector import connection

connection.MySQLConnection(host='localhost', user='sonarsource', password='')  # Noncompliant


# Compliant Solution
# Flask-SQLAlchemy
def configure_app(app, pwd):
    app.config['SQLALCHEMY_DATABASE_URI'] = f"postgresql://user:{pwd}@domain.com" # Compliant

# Django - settings.py
import os

DATABASES = {
    'postgresql_db': {
        'ENGINE': 'django.db.backends.postgresql',
        'NAME': 'quickdb',
        'USER': 'sonarsource',
        'PASSWORD': os.getenv('DB_PASSWORD'),      # Compliant
        'HOST': 'localhost',
        'PORT': '5432'
    }
}

# mysql/mysql-connector-python
from mysql.connector import connection
import os

db_password = os.getenv('DB_PASSWORD')
connection.MySQLConnection(host='localhost', user='sonarsource', password=db_password)  # Compliant