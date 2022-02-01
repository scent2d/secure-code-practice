/*
취약점 개요
URL 매개변수와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 
오염된 데이터에서 직접 LDAP 이름 또는 검색 필터를 구성하면 공격자가 이름이나 필터 자체의 초기 의미를 변경하는 특수하게 조작된 값을 삽입할 수 있습니다. 
성공적인 LDAP 주입 공격은 디렉터리 서비스에서 민감한 정보를 읽거나 수정하거나 삭제할 수 있습니다.

LDAP 이름 내에서 특수 문자 ' ', '#', '"', '+', ',', ';', '<', 및 는 RFC 4514에 따라 이스케이프되어야 합니다
(예: 이스케이프할 문자의 ASCII 코드에 해당하는 백슬래시 문자 뒤에 오는 두 개의 16진수 숫자로 대체 '>') . . 
마찬가지로, LDAP 검색 필터는 RFC 4515에 따라 다른 특수 문자 집합( , , , 및 를 포함하되 이에 국한되지 않음)을 이스케이프해야 합니다.

Within LDAP names, the special characters ' ', '#', '"', '+', ',', ';', '<', '>', '\' and null must be escaped according to RFC 4514, 
for example by replacing them with the backslash character '\' followed by the two hex digits corresponding to the ASCII code of the character to be escaped. 
Similarly, LDAP search filters must escape a different set of special characters (including but not limited to '*', '(', ')', '\' and null) according to RFC 4515.
*/


// Noncompliant Code Example
public boolean authenticate(javax.servlet.http.HttpServletRequest request, DirContext ctx) throws NamingException {
    String user = request.getParameter("user");
    String pass = request.getParameter("pass");
  
    String filter = "(&(uid=" + user + ")(userPassword=" + pass + "))"; // Unsafe
  
    // If the special value "*)(uid=*))(|(uid=*" is passed as user, authentication is bypassed
    // Indeed, if it is passed as a user, the filter becomes:
    // (&(uid=*)(uid=*))(|(uid=*)(userPassword=...))
    // as uid=* match all users, it is equivalent to:
    // (|(uid=*)(userPassword=...))
    // again, as uid=* match all users, the filter becomes useless
  
    NamingEnumeration<SearchResult> results = ctx.search("ou=system", filter, new SearchControls()); // Noncompliant
    return results.hasMore();
  }


// Compliant Solution
public boolean authenticate(javax.servlet.http.HttpServletRequest request, DirContext ctx) throws NamingException {
    String user = request.getParameter("user");
    String pass = request.getParameter("pass");
  
    String filter = "(&(uid={0})(userPassword={1}))"; // Safe
  
    NamingEnumeration<SearchResult> results = ctx.search("ou=system", filter, new String[]{user, pass}, new SearchControls());
    return results.hasMore();
  }