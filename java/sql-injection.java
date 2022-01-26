/*
취약점 개요
SQL Injection
URL 매개변수와 같은 사용자 제공 데이터는 항상 신뢰할 수 없고 오염된 것으로 간주되어야 합니다. 오염된 데이터에서 직접 SQL 쿼리를 구성하면 공격자가 쿼리 자체의 초기 의미를 변경하는 특수하게 조작된 값을 삽입할 수 있습니다. 성공적인 데이터베이스 쿼리 주입 공격은 데이터베이스에서 민감한 정보를 읽거나 수정하거나 삭제할 수 있으며 때로는 데이터베이스를 종료하거나 임의의 운영 체제 명령을 실행할 수도 있습니다.

일반적으로 솔루션은 준비된 명령문을 사용하고 setString, 와 같은 전용 메서드를 사용하여 변수를 SQL 쿼리 매개변수에 바인딩하여 사용자가 제공한 데이터가 적절하게 이스케이프되도록 하는 것입니다. 또 다른 솔루션은 쿼리를 작성하는 데 사용되는 모든 매개변수의 유효성을 검사하는 것입니다. 이것은 문자열 값을 기본 유형으로 변환하거나 허용된 값의 화이트리스트에 대해 유효성을 검사하여 달성할 수 있습니다.

이 규칙은 JDBC, Java EE Entity Manager, Spring Framework, Hibernate, JDO, Android 데이터베이스, Apache Torque, Apache Turbine, MyBastis, Rapidoid를 지원합니다.
*/


// Noncompliant Code Example
public boolean authenticate(javax.servlet.http.HttpServletRequest request, java.sql.Connection connection) throws SQLException {
  String user = request.getParameter("user");
  String pass = request.getParameter("pass");

  String query = "SELECT * FROM users WHERE user = '" + user + "' AND pass = '" + pass + "'"; // Unsafe

  // If the special value "foo' OR 1=1 --" is passed as either the user or pass, authentication is bypassed
  // Indeed, if it is passed as a user, the query becomes:
  // SELECT * FROM users WHERE user = 'foo' OR 1=1 --' AND pass = '...'
  // As '--' is the comment till end of line syntax in SQL, this is equivalent to:
  // SELECT * FROM users WHERE user = 'foo' OR 1=1
  // which is equivalent to:
  // SELECT * FROM users WHERE 1=1
  // which is equivalent to:
  // SELECT * FROM users

  java.sql.Statement statement = connection.createStatement();
  java.sql.ResultSet resultSet = statement.executeQuery(query); // Noncompliant
  return resultSet.next();
}

// Compliant Solution
public boolean authenticate(javax.servlet.http.HttpServletRequest request, java.sql.Connection connection) throws SQLException {
  String user = request.getParameter("user");
  String pass = request.getParameter("pass");

  // Safe even if authenticate() method is still vulnerable to brute-force attack in this specific case
  String query = "SELECT * FROM users WHERE user = ? AND pass = ?"; 

  // PreparedStatement 방식으로 SQL 쿼리문을 고정한 후, user/pass 파라미터의 값을 setString 하면 변조된 SQL 쿼리문이 실행되지 않는 구조임.
  java.sql.PreparedStatement statement = connection.prepareStatement(query);
  statement.setString(1, user); // Will be properly escaped
  statement.setString(2, pass);
  java.sql.ResultSet resultSet = statement.executeQuery();
  return resultSet.next();
}
