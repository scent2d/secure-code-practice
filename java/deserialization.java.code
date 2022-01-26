/*
취약점 개요
역직렬화 취약점

권한상승 및 RCE로 이루어질 수 있는 취약점

*/


// Noncompliant Code Example
public class RequestProcessor {
    protected void processRequest(HttpServletRequest request) {
      ServletInputStream sis = request.getInputStream();
      ObjectInputStream ois = new ObjectInputStream(sis);
      Object obj = ois.readObject(); // Noncompliant
    }
}


// Compliant Solution
public class SecureObjectInputStream extends ObjectInputStream {
    // Constructor here
  
    @Override
    protected Class<?> resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
      // Only deserialize instances of AllowedClass

      // 직렬화된 코드 감사 시, 허용된 클래스의 코드만 사용되도록 검증하는 로직
      if (!osc.getName().equals(AllowedClass.class.getName())) {
        throw new InvalidClassException("Unauthorized deserialization", osc.getName());
      }
      return super.resolveClass(osc);
    }
}
  
public class RequestProcessor {
    protected void processRequest(HttpServletRequest request) {
        ServletInputStream sis = request.getInputStream();
        SecureObjectInputStream sois = new SecureObjectInputStream(sis);
        Object obj = sois.readObject();
    }
}
