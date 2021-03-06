'''
취약점 개요
XSS
'''


# Noncompliant Code Example
templates/xss_shared.html

<!doctype html>
<title>Hello from Flask</title>
{% if name %}
  <h1>Hello {{ name }}!</h1>
{% else %}
  <h1>Hello, World!</h1>
{% endif %}

xss.py

@xss.route('/insecure/no_template_engine_replace', methods =['GET'])
def no_template_engine_replace():
    param = request.args.get('param', 'not set')

    html = open('templates/xss_shared.html').read()
    response = make_response(html.replace('{{ name }}', param)) # Noncompliant: param is not sanitized
    return response

# Compliant Solution
templates/xss_shared.html

<!doctype html>
<title>Hello from Flask</title>
{% if name %}
  <h1>Hello {{ name }}!</h1>
{% else %}
  <h1>Hello, World!</h1>
{% endif %}

xss.py

@xss.route('/secure/no_template_engine_sanitized_Markup_escape', methods =['GET'])
def no_template_engine_sanitized_Markup_escape():
    param = request.args.get('param', 'not set')

    param = Markup.escape(param) # escape 처리 (XSS)

    html = open('templates/xss_shared.html').read()
    response = make_response(html.replace('{{ name }}', param )) # Compliant: 'param' is sanitized by Markup.escape
    return response