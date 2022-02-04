/*
취약점 개요
이 java.security.SecureRandom클래스는 암호화에 적합한 강력한 RNG(난수 생성기)를 제공합니다. 그러나 상수 또는 다른 예측 가능한 값으로 시딩하면 크게 약화됩니다.
일반적으로 SecureRandom에서 제공하는 시드에 의존하는 것이 훨씬 안전합니다 .

이 규칙은 다음 중 하나인 시드로 호출 SecureRandom.setSeed()되거나 .SecureRandom(byte[])가 호출 될 때 문제를 발생시킵니다 
- 상수
- 시스템 시간

*/


// Noncompliant Code Example
SecureRandom sr = new SecureRandom();
sr.setSeed(123456L); // Noncompliant
int v = sr.next(32);

// Noncompliant Code Example
sr = new SecureRandom("abcdefghijklmnop".getBytes("us-ascii")); // Noncompliant
v = sr.next(32);



// Compliant Solution
SecureRandom sr = new SecureRandom();
int v = sr.next(32);
