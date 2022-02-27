'''
취약점 개요
SQL 인젝션 공격
일반적으로 솔루션은 준비된 명령문을 사용 params하고 사용자가 제공한 데이터가 적절하게 이스케이프되도록 하는 전용 메서드를 사용하여 변수를 SQL 쿼리 매개변수에 바인딩하는 것입니다. 
또 다른 솔루션은 쿼리를 작성하는 데 사용되는 모든 매개변수의 유효성을 검사하는 것입니다. 
이것은 문자열 값을 기본 유형으로 변환하거나 허용된 값의 화이트리스트에 대해 유효성을 검사하여 달성할 수 있습니다.
'''


# Noncompliant Code Example
# Flask application
from flask import request
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import text
from database.users import User

@app.route('hello')
def hello():
    id = request.args.get("id")
    stmt = text("SELECT * FROM users where id=%s" % id) # Query is constructed based on user inputs
    query = SQLAlchemy().session.query(User).from_statement(stmt) # Noncompliant
    user = query.one()
    return "Hello %s" % user.username

# Django application
from django.http import HttpResponse
from django.db import connection

def hello(request):
    id = request.GET.get("id", "")
    cursor = connection.cursor()
    cursor.execute("SELECT username FROM auth_user WHERE id=%s" % id) # Noncompliant; Query is constructed based on user inputs
    row = cursor.fetchone()
    return HttpResponse("Hello %s" % row[0])



# Compliant Solution
# Flask application
from flask import request
from flask_sqlalchemy import SQLAlchemy
from sqlalchemy import text
from database.users import User

@app.route('hello')
def hello():
    id = request.args.get("id")
    stmt = text("SELECT * FROM users where id=:id")
    query = SQLAlchemy().session.query(User).from_statement(stmt).params(id=id) # Compliant
    user = query.one()
    return "Hello %s" % user.username

# Django application
from django.http import HttpResponse
from django.db import connection

def hello(request):
    id = request.GET.get("id", "")
    cursor = connection.cursor()
    cursor.execute("SELECT username FROM auth_user WHERE id=:id", {"id": id}) # Compliant
    row = cursor.fetchone()
    return HttpResponse("Hello %s" % row[0])