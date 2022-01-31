/*
취약점 개요
JDBC 커넥션 객체를 생성할 때, DB 서버와 연결 시 사용하는 비밀번호 값은 하드코딩이나 빈 값으로 지정하면 안된다.

password 값을 System.getProperty() 함수를 이용하여 불러와서 사용한다

*/


// Noncompliant Code Example
Connection conn = DriverManager.getConnection("jdbc:derby:memory:myDB;create=true", "login", "");

// Compliant Solution
String password = System.getProperty("database.password");
Connection conn = DriverManager.getConnection("jdbc:derby:memory:myDB;create=true", "login", password);
