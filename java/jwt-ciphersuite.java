/*
취약점 개요
JSON 웹 토큰(JWT)이 강력한 암호 알고리즘으로 서명되지 않은 경우(또는 전혀 서명되지 않은 경우) 공격자는 이를 위조하고 사용자 ID를 가장할 수 있습니다.

none토큰의 유효성을 서명하거나 확인하기 위해 알고리즘을 사용하지 마십시오 .
사전에 서명을 확인하지 않고 토큰을 사용하지 마십시오.

*/


// Noncompliant Code Example
// Using jwtk/Java JWT library (to verify a signed token (containing a JWS) don’t use the parse method as it doesn’t throw an exception if an unsigned token is provided)
// Signing:
io.jsonwebtoken.Jwts.builder() // Noncompliant, token is not signed.
  .setSubject(USER_LOGIN)
  .compact();
// Verifying:
io.jsonwebtoken.Jwts.parser().setSigningKey(SECRET_KEY).parse(token).getBody(); // Noncompliant

// Using auth0/Java JWT library
// Signing:
com.auth0.jwt.JWT.create()
  .withSubject(SUBJECT)
  .sign(Algorithm.none()); // Noncompliant, use only strong cipher algorithms when signing this JWT.
// Verifying:
JWTVerifier nonCompliantVerifier = com.auth0.jwt.JWT.require(Algorithm.none()) // Noncompliant
  .withSubject(LOGIN)
  .build();


// Compliant Solution
// Using Java JWT library (to verify a signed token (containing a JWS) use the parseClaimsJws method that will throw an exception if an unsigned token is provided)
// Signing:
Jwts.builder() // Compliant
  .setSubject(USER_LOGIN)
  .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
  .compact();
// Verifying:
Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody(); // Compliant

// Using auth0/Java JWT library
// Signing:
JWT.create()
  .withSubject(SUBJECT)
  .sign(Algorithm.HMAC256(SECRET_KEY)); // Compliant
// Verifying:
JWTVerifier nonCompliantVerifier = JWT.require(Algorithm.HMAC256(SECRET_KEY)) // Compliant
  .withSubject(LOGIN)
  .build();