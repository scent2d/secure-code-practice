/*
취약점 개요

암호화에서 "salt"은 암호를 해시할 때 포함되는 추가 데이터 조각입니다. 이것은 rainbow-table attacks을 더 어렵게 만듭니다. 
솔트 없이 암호화 해시 함수를 사용하면 공격자가 미리 계산된 해시(이라고 함 rainbow-tables) 데이터베이스에서 해시 값을 성공적으로 찾을 가능성이 높아집니다.

권장되는 보안 코딩 방법
자체 보안 솔트를 생성하는 해싱 함수를 사용하거나 최소 16바이트의 보안 임의 값을 생성합니다.
솔트는 사용자 암호별로 고유해야 합니다.

솔트(salt) 값 지정 시 예측 불가능한 값으로 지정해야 한다.
*/

// Noncompliant Code Example
// Below, the hashed password use a predictable salt
byte[] salt = "notrandom".getBytes();

PBEParameterSpec cipherSpec = new PBEParameterSpec(salt, 10000); // Noncompliant, predictable salt
PBEKeySpec spec = new PBEKeySpec(chars, salt, 10000, 256); // Noncompliant, predictable salt


// Compliant Solution
// Use java.security.SecureRandom to generate an unpredictable salt
SecureRandom random = new SecureRandom();
byte[] salt = new byte[16];
random.nextBytes(salt);

PBEParameterSpec cipherSpec = new PBEParameterSpec(salt, 10000); // Compliant
PBEKeySpec spec = new PBEKeySpec(chars, salt, 10000, 256); // Compliant

