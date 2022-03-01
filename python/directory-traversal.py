'''
취약점 개요
URL 매개변수, POST 데이터 페이로드 또는 쿠키와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
오염된 데이터에서 직접 파일 시스템 경로를 구성하면 공격자가 특수하게 조작된 값(예: '../'초기 경로를 변경하고 액세스 시 사용자가 일반적으로 액세스해서는 안 되는 파일 시스템 경로로 확인)을 주입할 수 있습니다.

공격이 성공하면 공격자는 파일 시스템에서 중요한 정보를 읽거나 수정하거나 삭제할 수 있으며 때로는 임의의 운영 체제 명령을 실행할 수도 있습니다.

완화 전략은 허용된 경로 또는 문자의 허용 목록을 기반으로 해야 합니다.
'''

# Noncompliant Code Example
from flask import request, send_file

@app.route('/download')
def download():
    file = request.args['file']
    return send_file("static/%s" % file, as_attachment=True) # Noncompliant

# Compliant Solution
from flask import request, send_from_directory

@app.route('/download')
def download():
    file = request.args['file']
    return send_from_directory('static', file) # Compliant
