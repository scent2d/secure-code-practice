/*
취약점 개요
URL 매개변수와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
오염된 데이터에서 직접 쿠키를 구성하면 공격자가 세션 식별자를 알려진 값으로 설정할 수 있으므로 공격자가 피해자와 세션을 공유할 수 있습니다. 

공격이 성공하면 예를 들어 피해자가 인증할 때 세션 식별자가 다시 생성되지 않는 경우 민감한 정보에 대한 무단 액세스가 발생할 수 있습니다.

https://owasp.org/www-community/attacks/Session_fixation
*/

// Noncompliant Code Example
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;

// value: 사용자 입력 값
// 사용자 입력 값인 value를 직접 Set-Cookie 처리
@RequestMapping(value = "/")
public void index(HttpServletResponse res, String value) {
    res.setHeader("Set-Cookie", value);  // Noncompliant
    Cookie cookie = new Cookie("jsessionid", value);  // Noncompliant
    res.addCookie(cookie);
}


// Compliant Solution
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;


// value: 사용자 입력 값
@RequestMapping(value = "/")
public void index(HttpServletResponse res, String value) {
    res.setHeader("X-Data", value);
    Cookie cookie = new Cookie("data", value);
    res.addCookie(cookie);
}
