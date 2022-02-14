"""
취약점 개요
URL 매개 변수와 같은 사용자가 제공한 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주해야 합니다. 
오염된 데이터에서 직접 쿠키를 구성하면 공격자가 세션 식별자를 알려진 값으로 설정하여 공격 대상자와 세션을 공유할 수 있습니다. 
공격이 성공하면 예를 들어 공격 대상자가 인증할 때 세션 식별자가 재생성되지 않은 경우 중요한 정보에 무단으로 액세스할 수 있습니다.
일반적으로 이러한 유형의 공격을 방지하기 위한 해결책은 허용 목록의 영향을 받을 수 있는 쿠키를 제한하는 것입니다.
"""

# Noncompliant Code Example
from django.http import HttpResponse

def index(request):
    value = request.GET.get("value")
    response = HttpResponse("")
    response["Set-Cookie"] = value  # Noncompliant
    response.set_cookie("sessionid", value)  # Noncompliant
    return response

# Compliant Solution
from django.http import HttpResponse

def index(request):
    value = request.GET.get("value")
    response = HttpResponse("")
    response["X-Data"] = value
    response.set_cookie("data", value)
    return response