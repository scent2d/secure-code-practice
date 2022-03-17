'''
취약점 개요
URL 매개변수, POST 데이터 페이로드 또는 쿠키와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
사용자 제어 데이터에서 요청을 수행하면 공격자가 내부 네트워크에서 임의의 요청을 하거나 원래 의미를 변경하여 민감한 정보를 검색하거나 삭제할 수 있습니다.

다음 방법 중 하나로 문제를 완화할 수 있습니다.

- 요청을 구성하는 데 사용되는 URL 및 헤더와 같은 사용자 제공 데이터의 유효성을 검사합니다.
- 사용자가 제공한 데이터를 기반으로 요청을 보내지 않도록 애플리케이션을 다시 디자인합니다.
'''


# Noncompliant Code Example
from flask import request
import urllib

@app.route('/proxy')
def proxy():
    url = request.args["url"]
    return urllib.request.urlopen(url).read() # Noncompliant


# Compliant Solution
from flask import request
import urllib

DOMAINS_WHITELIST = ['domain1.com', 'domain2.com']

@app.route('/proxy')
def proxy():
    url = request.args["url"]
    if urllib.parse.urlparse(url).hostname in DOMAINS_WHITELIST:
        return urllib.request.urlopen(url).read()