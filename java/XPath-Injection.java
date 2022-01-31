/*
취약점 개요
XPath Injection
URL 매개변수와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
오염된 데이터에서 직접 XPath 표현식을 구성하면 공격자가 표현식 자체의 초기 의미를 변경하는 특수하게 조작된 값을 주입할 수 있습니다. 성공적인 XPath 주입 공격은 XML 문서에서 중요한 정보를 읽을 수 있습니다.

*/

// Noncompliant Code Example
public boolean authenticate(javax.servlet.http.HttpServletRequest request, javax.xml.xpath.XPath xpath, org.w3c.dom.Document doc) throws XPathExpressionException {
    String user = request.getParameter("user");
    String pass = request.getParameter("pass");
  
    String expression = "/users/user[@name='" + user + "' and @pass='" + pass + "']"; // Unsafe
  
    // An attacker can bypass authentication by setting user to this special value
    user = "' or 1=1 or ''='";
  
    return (boolean)xpath.evaluate(expression, doc, XPathConstants.BOOLEAN); // Noncompliant
}


// Compliant Solution
public boolean authenticate(javax.servlet.http.HttpServletRequest request, javax.xml.xpath.XPath xpath, org.w3c.dom.Document doc) throws XPathExpressionException {
    String user = request.getParameter("user");
    String pass = request.getParameter("pass");
  
    String expression = "/users/user[@name=$user and @pass=$pass]";
  
    xpath.setXPathVariableResolver(v -> {
      switch (v.getLocalPart()) {
        case "user":
          return user;
        case "pass":
          return pass;
        default:
          throw new IllegalArgumentException();
      }
    });
  
    return (boolean)xpath.evaluate(expression, doc, XPathConstants.BOOLEAN);
}