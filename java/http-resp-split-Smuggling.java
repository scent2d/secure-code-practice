/*
취약점 개요

URL 매개변수, POST 데이터 페이로드 또는 쿠키와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
오염된 데이터를 기반으로 HTTP 응답 헤더를 구성하는 애플리케이션을 통해 공격자는 Cross-Origin Resource Sharing 헤더와 같은 보안에 민감한 헤더를 변경할 수 있습니다.

웹 애플리케이션 프레임워크와 서버는 공격자가 헤더에 줄 바꿈 문자를 삽입하여 기형 HTTP 응답을 만드는 것을 허용할 수도 있습니다. 
이 경우 애플리케이션은 HTTP 응답 분할/밀수와 같은 광범위한 공격에 취약합니다. 
대부분의 경우 이러한 유형의 공격은 기본적으로 최신 웹 애플리케이션 프레임워크에 의해 완화되지만 이전 버전이 여전히 취약한 드문 경우가 있을 수 있습니다.

모범 사례로서 사용자 제공 데이터를 사용하여 응답 헤더를 구성하는 애플리케이션은 항상 데이터를 먼저 검증해야 합니다. 검증은 화이트리스트를 기반으로 해야 합니다.
*/


// Noncompliant Code Example
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String value = req.getParameter("value");
    resp.addHeader("X-Header", value); // Noncompliant
}


// Compliant Solution
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String value = req.getParameter("value");

    String whitelist = "safevalue1 safevalue2";
    if (!whitelist.contains(value))
      throw new IOException();

    resp.addHeader("X-Header", value); // Compliant
}
