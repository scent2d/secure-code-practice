/*
취약점 개요



*/

// Noncompliant Code Example
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String location = req.getParameter("url");
    resp.sendRedirect(location); // Noncompliant
}

protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String location = req.getParameter("url");
    resp.setStatus(302);
    resp.setHeader("Location", location); // Noncompliant
}


// Compliant Solution
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String location = req.getParameter("url");
  
    // URL 화이트리스트 설정
    List<String> allowedHosts = new ArrayList<String>();
    allowedUrls.add("https://www.domain1.com/");
    allowedUrls.add("https://www.domain2.com/");
  
    // url 파라미터 입력 값 검증
    if (allowedUrls.contains(location))
      resp.sendRedirect(location); // Compliant
}
