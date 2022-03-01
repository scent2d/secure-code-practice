'''
취약점 개요
URL 매개변수와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
오염된 데이터에서 직접 LDAP 이름 또는 검색 필터를 구성하면 공격자가 이름이나 필터 자체의 초기 의미를 변경하는 특수하게 조작된 값을 삽입할 수 있습니다. 
성공적인 LDAP 주입 공격은 디렉터리 서비스에서 민감한 정보를 읽거나 수정하거나 삭제할 수 있습니다.

LDAP 이름 내에서 특수 문자 ' ', '#', '"', '+', ',', ';', '<', '>'는 RFC 4514 '\'에 null따라 이스케이프되어야 합니다
(예: 이스케이프 '\'할 문자의 ASCII 코드에 해당하는 백슬래시 문자 뒤에 오는 두 개의 16진수 숫자 로 대체). . 
마찬가지로, LDAP 검색 필터는 RFC 4515에 따라 다른 특수 문자 집합( '*', '(', ')', '\'및 를 포함하되 이에 국한되지 않음)을 이스케이프해야 합니다.null

'''

# Noncompliant Code Example
from flask import request
import ldap

@app.route("/user")
def user():
    dn =  request.args['dn']
    username =  request.args['username']

    search_filter = "(&(objectClass=*)(uid="+username+"))"
    ldap_connection = ldap.initialize("ldap://127.0.0.1:389")
    user = ldap_connection.search_s(dn, ldap.SCOPE_SUBTREE, search_filter) # Noncompliant
    return user[0]


# Compliant Solution
from flask import request
import ldap
import ldap.filter
import ldap.dn

@app.route("/user")
def user():
    dn = "dc=%s" % ldap.dn.escape_dn_chars(request.args['dc']) # Escape distinguished names special characters
    username = ldap.filter.escape_filter_chars(request.args['username']) # Escape search filters special characters

    search_filter = "(&(objectClass=*)(uid="+username+"))"
    ldap_connection = ldap.initialize("ldap://127.0.0.1:389")
    user = ldap_connection.search_s(dn, ldap.SCOPE_SUBTREE, search_filter) # Compliant
    return user[0]
