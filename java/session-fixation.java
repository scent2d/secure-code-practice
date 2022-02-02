/*
취약점 개요
세션 고정 공격은 공격자가 합법적인 사용자가 알고 있는 세션 ID를 사용하도록 할 때 발생합니다. 
고정 공격을 피하려면 사용자가 인증할 때마다 새 세션을 생성하고 기존 세션(공격자가 알고 있는 세션)을 삭제/무효화하는 것이 좋습니다.

*/

// Noncompliant Code Example
// In a Spring Security’s context, session fixation protection is enabled by default but can be disabled with sessionFixation().none() method
@Override
protected void configure(HttpSecurity http) throws Exception {
   http.sessionManagement()
     .sessionFixation().none(); // Noncompliant: the existing session will continue
}

// Compliant Solution
// In a Spring Security’s context, session fixation protection can be enabled as follows
@Override
protected void configure(HttpSecurity http) throws Exception {
  http.sessionManagement()
     .sessionFixation().newSession(); // Compliant: a new session is created without any of the attributes from the old session being copied over

  // or

  http.sessionManagement()
     .sessionFixation().migrateSession(); // Compliant: a new session is created, the old one is invalidated and the attributes from the old session are copied over.
}
