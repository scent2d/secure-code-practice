/*
취약점 개요
CBC 모드 암호화의 IV 값은 예측 불가능한 값이어야 한다.

CBC(Cipher Block Chaining) 모드로 데이터를 암호화할 때 IV(초기화 벡터)는 암호화를 무작위화하는 데 사용됩니다. 
즉, 주어진 키에서 동일한 평문이 항상 동일한 암호문을 생성하지는 않습니다. IV는 비밀일 필요는 없지만 "선택된 일반 텍스트 공격"을 피하기 위해 예측할 수 없어야 합니다.

초기화 벡터를 생성하기 위해 NIST는 보안 난수 생성기를 사용할 것을 권장합니다.

*/

// Noncompliant Code Example
public class MyCbcClass {

    public String applyCBC(String strKey, String plainText) {
    
      // 고정된 IV 바이트 값 할당
      byte[] bytesIV = "7cVgr5cbdCZVw5WY".getBytes("UTF-8");
  
      /* KEY + IV setting */
      IvParameterSpec iv = new IvParameterSpec(bytesIV);
      SecretKeySpec skeySpec = new SecretKeySpec(strKey.getBytes("UTF-8"), "AES");
  
      /* Ciphering */
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  // Noncompliant: the IV is hard coded and thus not generated with a secure random generator
      byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
      return DatatypeConverter.printBase64Binary(bytesIV)
              + ";" + DatatypeConverter.printBase64Binary(encryptedBytes);
    }
}
 
// Compliant Solution
public class MyCbcClass {

    SecureRandom random = new SecureRandom();
  
    public String applyCBC(String strKey, String plainText) {
      
      // 고정적이지 않은 랜덤 IV 바이트 값 할당
      byte[] bytesIV = new byte[16];
      random.nextBytes(bytesIV);
  
      /* KEY + IV setting */
      IvParameterSpec iv = new IvParameterSpec(bytesIV);
      SecretKeySpec skeySpec = new SecretKeySpec(strKey.getBytes("UTF-8"), "AES");
  
      /* Ciphering */
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv); // Compliant
      byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
      return DatatypeConverter.printBase64Binary(bytesIV)
              + ";" + DatatypeConverter.printBase64Binary(encryptedBytes);
    }
}