'''
취약점 개요
URL 매개변수, POST 데이터 페이로드 또는 쿠키와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
오염된 데이터를 기반으로 HTTP 리디렉션을 수행하는 응용 프로그램을 통해 공격자는 사용자를 악성 사이트로 리디렉션하여 로그인 자격 증명을 훔치는 등의 작업을 수행할 수 있습니다.

이 문제는 다음 방법 중 하나로 완화할 수 있습니다.
- 허용 목록을 기반으로 사용자가 제공한 데이터의 유효성을 검사하고 일치하지 않는 입력을 거부합니다.
- 사용자가 제공한 데이터를 기반으로 리디렉션을 수행하지 않도록 애플리케이션을 재설계합니다.

'''


# Noncompliant Code Example
# Flask
from flask import request, redirect, Response

@app.route('flask_redirect')
def flask_redirect():
    url = request.args["next"]
    return redirect(url)  # Noncompliant

@app.route('set_location_header')
def set_location_header():
    url = request.args["next"]
    response = Response("redirecting...", 302)
    response.headers['Location'] = url  # Noncompliant
    return response

# Django
from django.http import HttpResponseRedirect

def http_responser_redirect(request):
    url = request.GET.get("next", "/")
    return HttpResponseRedirect(url)  # Noncompliant

def set_location_header(request):
    url = request.GET.get("next", "/")
    response = HttpResponse(status=302)
    response['Location'] = url  # Noncompliant
    return response


# Compliant Solution
# Flask
from flask import request, redirect, Response, url_for

@app.route('flask_redirect')
def flask_redirect():
    endpoint = request.args["next"]
    return redirect(url_for(endpoint))  # Compliant


# Django
from django.http import HttpResponseRedirect
from urllib.parse import urlparse

DOMAINS_WHITELIST = ['www.example.com', 'example.com']

def http_responser_redirect(request):
    url = request.GET.get("next", "/")
    parsed_uri = urlparse(url)
    if parsed_uri.netloc in DOMAINS_WHITELIST:
        return HttpResponseRedirect(url)  # Compliant
    return HttpResponseRedirect("/")
