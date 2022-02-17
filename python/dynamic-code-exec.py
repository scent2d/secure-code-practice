'''
취약점 개요
코드를 동적으로 실행하는 응용 프로그램은 코드를 구성하는 데 사용되는 모든 외부 제공 값을 무효화해야 합니다. 
그렇게 하지 않으면 공격자가 임의의 코드를 실행할 수 있습니다. 
이는 민감한 정보에 대한 액세스/수정 또는 전체 시스템 액세스 권한 획득과 같은 광범위한 심각한 공격을 가능하게 할 수 있습니다.

완화 전략은 허용된 값의 화이트리스트 지정 또는 안전한 유형으로의 캐스팅을 기반으로 해야 합니다.
'''

# Noncompliant Code Example
from flask import request

@app.route('/')
def index():
    module = request.args.get("module")
    exec("import urllib%s as urllib" % module) # Noncompliant

# Compliant Solution
from flask import request

@app.route('/')
def index():
    module = request.args.get("module")
    exec("import urllib%d as urllib" % int(module)) # Compliant; module is safely cast to an integer