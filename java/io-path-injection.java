/*
취약점 개요
URL 매개변수, POST 데이터 페이로드 또는 쿠키와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
오염된 데이터에서 직접 파일 시스템 경로를 구성하면 공격자가 '../'초기 경로를 변경하는 특수하게 조작된 값(예: )을 주입할 수 있으며 액세스할 때 사용자가 일반적으로 액세스해서는 안 되는 파일 시스템의 경로를 확인할 수 있습니다.

공격이 성공하면 공격자는 파일 시스템에서 민감한 정보를 읽거나 수정하거나 삭제할 수 있으며 때로는 임의의 운영 체제 명령을 실행할 수도 있습니다. "directory traversal" 공격이라고 합니다.

*/


// Noncompliant Code Example
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String file = request.getParameter("file");

    File fileUnsafe = new File(file);
    try {
      FileUtils.forceDelete(fileUnsafe); // Noncompliant
    }
    catch(IOException ex){
      System.out.println (ex.toString());
    }
}


// Compliant Solution
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String file = request.getParameter("file");

    File fileUnsafe = new File(file);
    File directory = new File("/tmp/");

    try {
      if(FileUtils.directoryContains(directory, fileUnsafe)) {
        FileUtils.forceDelete(fileUnsafe); // Compliant
      }
    }
    catch(IOException ex){
      System.out.println (ex.toString());
    }
}