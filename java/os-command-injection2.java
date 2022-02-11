/*

사용자 제어 데이터에서 운영 체제 명령의 실행을 허용하는 응용 프로그램은 명령에 전달된 인수를 제어해야 합니다. 
그렇지 않으면 공격자가 명령의 동작을 변경할 수 있는 추가 임의 인수를 삽입할 수 있습니다.

사용자 제어 인수는 인수 구분 기호(예: ', 공백, -)를 무효화하여 원치 않는 추가 인수의 삽입을 방지하여 삭제해야 합니다. 
단일 사용자 제어 인수가 find-exec 와 함께 사용 가능한 것과 같이 명령에서 지원하는 위험한 옵션에 해당하는 경우 여전히 취약점으로 이어질 수 있습니다. 
이 경우 --(이중 대시) 또는 제한 을 사용하여 명령줄에서 옵션 처리 종료를 표시하십시오. 신뢰할 수 있는 값에만 옵션을 제공합니다.


*/


// Noncompliant Code Example
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

public void runUnsafe(HttpServletRequest request) throws IOException {
  String folder = request.getParameter("folder");

  String cmd = "mkdir " + folder;

  Runtime.getRuntime().exec(cmd); // Noncompliant
}


// Compliant Solution
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

public void runSafe(HttpServletRequest request) throws IOException {
  String folderarg1 = request.getParameter("folder");

  String cmd[] =  new String[] { "mkdir", folderarg1 };

  Runtime.getRuntime().exec(cmd); // Compliant
}