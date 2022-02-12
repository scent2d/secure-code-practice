/*
취약점 개요
URL 매개변수, POST 데이터 페이로드 또는 쿠키와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
감염된 데이터를 기록하는 응용 프로그램을 통해 공격자는 로그 파일 패턴을 깨는 문자를 삽입할 수 있습니다. 
이는 모니터와 SIEM(보안 정보 및 이벤트 관리) 시스템이 다른 악성 이벤트를 감지하지 못하도록 차단하는 데 사용할 수 있습니다.

*/

// Noncompliant Code Example
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String param1 = req.getParameter("param1");
    Logger.info("Param1: " + param1 + " " + Logger.getName()); // Noncompliant
    // ...
}

// Compliant Solution
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String param1 = req.getParameter("param1");
  
    // Replace pattern-breaking characters
    param1 = param1.replaceAll("[\n\r\t]", "_");
  
    Logger.info("Param1: " + param1 + " " + Logger.getName());
    // ...
}
