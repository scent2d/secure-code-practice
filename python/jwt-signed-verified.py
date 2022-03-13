'''
취약점 개요

JSON 웹 토큰(JWT)이 강력한 암호 알고리즘으로 서명되지 않은 경우(또는 전혀 서명되지 않은 경우) 공격자는 이를 위조하고 사용자 ID를 가장할 수 있습니다.
- none토큰의 유효성을 서명하거나 확인하기 위해 알고리즘을 사용하지 마십시오 .
- 사전에 서명을 확인하지 않고 토큰을 사용하지 마십시오
'''

# Noncompliant Code Example
# For pyjwt module:
jwt.decode(token, verify = False)  # Noncompliant
jwt.decode(token, key, options={"verify_signature": False})  # Noncompliant

# For python_jwt module:
jwt.process_jwt(token)  # Noncompliant


# Compliant Solution
# For pyjwt module:
jwt.decode(token, key, algo)

# For python_jwt module:
jwt.process_jwt(token)  #  Compliant because followed by verify_jwt()
jwt.verify_jwt(token, key, algo)