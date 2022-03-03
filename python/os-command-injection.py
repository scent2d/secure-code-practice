'''
취약점 개요
운영 체제 명령을 실행하거나 기본 시스템과 상호 작용하는 명령을 실행하는 응용 프로그램은 해당 명령에 사용된 외부 제공 값을 무효화해야 합니다. 
그렇게 하지 않으면 공격자가 의도하지 않은 명령을 실행하거나 민감한 데이터를 노출하는 입력을 포함할 수 있습니다.

다음 방법 중 하나로 문제를 완화할 수 있습니다.

- Using subprocess module without the shell=true. In this case subprocess expects an array where command and arguments are clearly separated.
- Escaping shell argument with shlex.quote

'''


# Noncompliant Code Example
# os
from flask import request
import os

@app.route('/ping')
def ping():
    address = request.args.get("address")
    cmd = "ping -c 1 %s" % address
    os.popen(cmd) # Noncompliant

# subprocess
from flask import request
import subprocess

@app.route('/ping')
def ping():
    address = request.args.get("address")
    cmd = "ping -c 1 %s" % address
    subprocess.Popen(cmd, shell=True) # Noncompliant; using shell=true is unsafe

# Compliant Solution
# os
from flask import request
import os

@app.route('/ping')
def ping():
    address = shlex.quote(request.args.get("address")) # address argument is shell-escaped
    cmd = "ping -c 1 %s" % address
    os.popen(cmd ) # Compliant

# subprocess
from flask import request
import subprocess

@app.route('/ping')
def ping():
    address = request.args.get("address")
    args = ["ping", "-c1", address]
    subprocess.Popen(args) # Compliant
