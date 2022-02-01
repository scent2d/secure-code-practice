/*

*/

// Noncompliant Code Example
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

public void runUnsafe(HttpServletRequest request) throws IOException {
  String cmd = request.getParameter("command");
  String arg = request.getParameter("arg");

  Runtime.getRuntime().exec(cmd+" "+arg); // Noncompliant
}


// Compliant Solution 1 - each time the command to execute is user-controlled
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

public void runUnsafe(HttpServletRequest request) throws IOException {
  String cmd = request.getParameter("command");
  String arg = request.getParameter("arg");

  if(cmd.equals("/usr/bin/ls") || cmd.equals("/usr/bin/cat"))
  {
       // only ls or cat command are authorized
       String cmdarray[] =  new String[] { cmd, arg };
       Runtime.getRuntime().exec(cmdarray); // Compliant
  }
}

// Compliant Solution 2 - globally with the creation of a SecurityManager overriding checkExec() method
class MySecurityManager extends SecurityManager {
    MySecurityManager() {
      super();
    }
  
    public void checkExec(String cmd) {
      if(!(cmd.equals("/usr/bin/ls") || cmd.equals("/usr/bin/cat"))) {
        throw new SecurityException("Unauthorized command: "+cmd);
      }
    }
}

MySecurityManager sm = new MySecurityManager();
System.setSecurityManager(sm);

