/*

URL 매개변수, POST 데이터 페이로드 또는 쿠키와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
사용자 제어 데이터에서 요청을 수행하면 공격자가 내부 네트워크에서 임의의 요청을 하거나 원래 의미를 변경하여 민감한 정보를 검색하거나 삭제할 수 있습니다.

다음 방법 중 하나로 문제를 완화할 수 있습니다.

요청을 구성하는 데 사용되는 URL 및 헤더와 같은 사용자 제공 데이터의 유효성을 검사합니다.
사용자가 제공한 데이터를 기반으로 요청을 보내지 않도록 애플리케이션을 다시 디자인합니다.
*/


// Noncompliant Code Example
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    URL url = new URL(req.getParameter("url"));
    HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Noncompliant
}


// Compliant Solution
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String[] urlWhiteList = { "example.com", "www.example.com" };
  
    String inputUrl = req.getParameter("url");
  
    URI uri           = new URI(inputUrl);
    String remoteHost = uri.getHost();
  
    if (!urlWhiteList.contains(remoteHost))
      throw new IOException();
  
    URL url = uri.toURL();
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
}
