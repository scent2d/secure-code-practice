/*
취약점 개요

zip 아카이브에 있는 파일 이름은 입력 값 및 유효성 검증이 필요하다. 
실제로 파일 이름에 '../' 와 같은 Directory Traversal 공격 페이로드가 포함될 시 사용자가 접근할 수 없는 파일 시스템 경로에 접근할 위험성이 존재한다. 

Directory Traversal 공격에 성공할 시, 공격자는 파일 시스템의 중요한 정보를 Read/Update/Delete 등 가능하며 때로는 RCE로 이어질 수 있다.

*/

// Noncompliant Code Example
public static List<String> zipSlipNoncompliant(ZipFile zipFile) throws IOException {
  Enumeration<? extends ZipEntry> entries = zipFile.entries();
  List<String> filesContent = new ArrayList<>();

  while (entries.hasMoreElements()) {
    ZipEntry entry = entries.nextElement();
    File file = new File(entry.getName());
    String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8); // Noncompliant
    filesContent.add(content);
  }

  return filesContent;
}

// Compliant Solution
// 정확한 targetDirectory를 입력받은 후, 입력받은 값과 Extract된 파일의 경로 값과 동일한지 검증하는 로직을 추가해야 한다.
public static List<String> zipSlipCompliant(ZipFile zipFile, String targetDirectory) throws IOException {
  Enumeration<? extends ZipEntry> entries = zipFile.entries();
  List<String> filesContent = new ArrayList<>();

  while (entries.hasMoreElements()) {
    ZipEntry entry = entries.nextElement();
    File file = new File(entry.getName());
    String canonicalDestinationPath = file.getCanonicalPath();

    // Extract된 파일의 경로 값이 입력받은 targetDirectory 값과 다를 경우 예외처리한다. 
    if (!canonicalDestinationPath.startsWith(targetDirectory)) {
      throw new IOException("Entry is outside of the target directory");
    }

    String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8); // OK
    filesContent.add(content);
  }

  return filesContent;
}