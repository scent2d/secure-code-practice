/*
취약점 개요
LDAP 클라이언트는 무엇보다도 간단한 인증 방법 을 제공하는 "바인드 요청"으로 LDAP 서버에 인증합니다 .

LDAP의 단순 인증은 세 가지 다른 메커니즘과 함께 사용할 수 있습니다.

길이가 0인 사용자 이름과 비밀번호 값으로 바인드 요청을 수행하여 익명 인증 메커니즘 .
길이가 0인 암호 값으로 바인드 요청을 수행하여 인증되지 않은 인증 메커니즘 입니다.
길이가 0이 아닌 비밀번호 값으로 바인드 요청을 수행하여 이름/비밀번호 인증 메커니즘 .
익명 바인드와 인증되지 않은 바인드를 사용하면 비밀번호를 제공하지 않고도 LDAP 디렉토리의 정보에 액세스할 수 있으므로 사용을 권장하지 않습니다.

*/

// Noncompliant Code Example
// Set up the environment for creating the initial context
Hashtable<String, Object> env = new Hashtable<String, Object>();
env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=JNDITutorial");

// Use anonymous authentication
// "none" 인증 사용하지 않기 - anonymous 인증임
env.put(Context.SECURITY_AUTHENTICATION, "none"); // Noncompliant

// Create the initial context
DirContext ctx = new InitialDirContext(env);



// Compliant Solution
// Set up the environment for creating the initial context
Hashtable<String, Object> env = new Hashtable<String, Object>();
env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=JNDITutorial");

// Use simple authentication
env.put(Context.SECURITY_AUTHENTICATION, "simple");
env.put(Context.SECURITY_PRINCIPAL, "cn=S. User, ou=NewHires, o=JNDITutorial");
env.put(Context.SECURITY_CREDENTIALS, getLDAPPassword());

// Create the initial context
DirContext ctx = new InitialDirContext(env);
