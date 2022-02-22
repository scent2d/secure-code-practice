'''
취약점 개요
URL 매개변수, POST 데이터 페이로드 또는 쿠키와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
사용자가 제공한 데이터를 기반으로 한 역직렬화는 두 가지 유형의 공격을 유발할 수 있습니다.

직렬화되지 않은 개체의 동작을 수정하기 위해 직렬화된 데이터의 구조가 변경되는 원격 코드 실행 공격.
예를 들어 제품의 수량이나 가격과 같은 권한을 높이거나 변경하기 위해 데이터가 수정되는 매개변수 변조 공격.
역직렬화 공격으로부터 보호하는 가장 좋은 방법은 아마도 응용 프로그램에서 역직렬화 메커니즘 사용에 도전하는 것입니다. 
역직렬화 메커니즘의 사용이 정당화되지 않고 위반이 발생한 경우입니다(CVE-2017-9785).

deserialization 메커니즘의 사용이 컨텍스트에서 유효한 경우 다음 방법 중 하나로 문제를 완화할 수 있습니다.

pickle 모듈 은 안전하지 않으므로 신뢰할 수 없는 데이터를 역직렬화하는 데 사용하지 마십시오.
기본 안전 로더와 함께 PyYAML 모듈 만 사용하십시오 .
기본 데이터 교환 형식을 사용하는 대신 형식화되지 않은 JSON과 같은 안전한 표준 형식이나 Google 프로토콜 버퍼와 같은 구조화된 데이터 접근 방식을 사용하세요.
무결성이 손상되지 않도록 하려면 역직렬화 전에 유효성이 검사되는 직렬화된 데이터에 디지털 서명(HMAC)을 추가합니다(클라이언트가 직렬화된 데이터를 수정할 필요가 없는 경우에만 유효함).
최후의 수단으로 역직렬화를 화이트리스트에 있는 특정 클래스로만 제한할 수 있습니다.
'''

# Noncompliant Code Example
from flask import request
import pickle
import yaml

@app.route('/pickle')
def pickle_loads():
    file = request.files['pickle']
    pickle.load(file) # Noncompliant; Never use pickle module to deserialize user inputs

@app.route('/yaml')
def yaml_load():
    data = request.GET.get("data")
    yaml.load(data, Loader=yaml.Loader) # Noncompliant; Avoid using yaml.load with unsafe yaml.Loader

# Compliant Solution
from flask import request
import yaml

@app.route('/yaml')
def yaml_load():
    data = request.GET.get("data")
    yaml.load(data) # Compliant;  Prefer using yaml.load with the default safe loader