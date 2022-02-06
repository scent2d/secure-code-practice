/*
취약점 개요

File.createTempFile 임시 디렉토리를 만드는 첫 번째 단계로 사용 하면 경쟁 조건이 발생하고 본질적으로 신뢰할 수 없고 안전하지 않습니다. 
대신 Files.createTempDirectory(Java 7+)를 사용해야 합니다.

This rule raises an issue when the following steps are taken in immediate sequence:
* call to File.createTempFile
* delete resulting file
* call mkdir on the File object

*/

// Noncompliant Code Example
File tempDir;
tempDir = File.createTempFile("", ".");
tempDir.delete();
tempDir.mkdir();  // Noncompliant


// Compliant Solution
Path tempPath = Files.createTempDirectory("");
File tempDir = tempPath.toFile();