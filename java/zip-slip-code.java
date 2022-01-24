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