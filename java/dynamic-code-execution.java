/*
취약점 개요
사용자 입력 값을 통해 eval 함수와 같이 스크립트 코드를 동적으로 생성하는 함수를 사용할 시, 입력 값 검증 필터링을 추가해야 한다. 

*/


// Noncompliant Code Example
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    // 사용자 입력값 input 파라미터
    String input = req.getParameter("input");
  
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("JavaScript");

    // 취약함
    engine.eval(input); // Noncompliant
}


// Compliant Solution
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    // 사용자 입력값 input 파라미터
    String input = req.getParameter("input");
  
    // input 파라미터 화이트리스트 검증 로직 추가함
    if (!whiteList.contains(input))
      throw new IOException();
  
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("JavaScript");

    // 검증된 입력 값을 기반으로 자바스크립트 동적 코딩 처리
    engine.eval(input);
}