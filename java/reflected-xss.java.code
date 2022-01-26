/*
취약점 개요
Reflected XSS 

URL 매개변수, POST 데이터 페이로드 또는 쿠키와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 또한 HTTP 요청을 처리할 때 웹 서버는 사용자가 제공한 데이터를 사용자에게 다시 전송되는 HTTP 응답의 본문에 복사할 수 있습니다. 이 동작을 "반사"라고 합니다. 오염된 데이터를 반영하는 끝점을 통해 공격자는 결국 사용자의 브라우저에서 실행될 코드를 삽입할 수 있습니다. 이것은 민감한 정보에 접근/수정하거나 다른 사용자를 사칭하는 것과 같은 광범위한 심각한 공격을 가능하게 할 수 있습니다.

일반적으로 솔루션은 다음 중 하나입니다.

화이트리스트를 기반으로 사용자가 제공한 데이터의 유효성을 검사하고 허용되지 않는 입력을 거부합니다.
악의적인 목적으로 사용될 수 있는 모든 문자에서 사용자 제공 데이터를 삭제합니다.
HTTP 응답에 다시 반영될 때 사용자가 제공한 데이터를 인코딩합니다. 예를 들어 HTML 콘텐츠에 HTML 인코딩이 사용되고, 속성 값에 HTML 속성 인코딩이, 서버 생성 JavaScript에 JavaScript 인코딩이 사용되도록 인코딩을 출력 컨텍스트로 조정합니다.
데이터를 삭제하거나 인코딩할 때 보안 목적으로 특별히 설계된 라이브러리만 사용하는 것이 좋습니다. 또한 사용 중인 라이브러리가 적극적으로 유지 관리되고 있고 가장 최근에 발견된 취약점으로 최신 상태를 유지하고 있는지 확인하십시오.
*/

// Noncompliant Code Example
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String name = req.getParameter("name");
    PrintWriter out = resp.getWriter();

    // name 파라미터로부터 입력 받은 사용자 입력 값을 그대로 HTTP Response의 값으로 출력할 시 취약함
    out.write("Hello " + name); // Noncompliant
  }

// Compliant Solution
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String name = req.getParameter("name");

    // name 파라미터로부터 입력 받은 사용자 입력 값을 HTML 인코딩 처리하여 XSS 필터 로직 적용
    String encodedName = org.owasp.encoder.Encode.forHtml(name);
    PrintWriter out = resp.getWriter();

    // HTML 인코딩 처리된 사용자 입력 값이 HTTP Response의 값으로 출력되므로 양호함
    out.write("Hello " + encodedName);
  }
