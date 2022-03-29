/*
취약점 개요
zip 아카이브에 있는 항목의 파일 이름은 신뢰할 수 없고 오염된 것으로 간주되어야 하며 
파일 시스템 작업에 사용하기 전에 유효성을 검사해야 합니다. 
실제로 파일 이름에는 '../'와 같이 특수하게 조작된 값이 포함될 수 있습니다. 
이 값은 초기 경로를 변경하고 액세스할 때 사용자가 일반적으로 액세스할 수 없어야 하는 파일 시스템의 경로로 확인됩니다.

공격이 성공하면 공격자는 파일 시스템에서 중요한 정보를 읽거나 수정하거나 삭제할 수 있으며 
때로는 임의의 운영 체제 명령을 실행할 수도 있습니다. 경로 주입 취약점의 이 특별한 경우를 "zip slip"이라고 합니다.

완화 전략은 허용된 경로 또는 문자의 허용 목록을 기반으로 해야 합니다.
*/


// Noncompliant Code Example
const AdmZip = require('adm-zip');
const fs = require('fs');

const zip = new AdmZip("zip-slip.zip");
const zipEntries = zip.getEntries();
zipEntries.forEach(function (zipEntry) {
  fs.createWriteStream(zipEntry.entryName); // Noncompliant
});

// Compliant Solution
const AdmZip = require('adm-zip');
const pathmodule = require('path');
const fs = require('fs');

const zip = new AdmZip("zip-slip.zip");
const zipEntries = zip.getEntries();
zipEntries.forEach(function (zipEntry) {
  let resolvedPath = pathmodule.join(__dirname + '/archive_tmp', zipEntry.entryName); // join will resolve "../"

  if (resolvedPath.startsWith(__dirname + '/archive_tmp')) {
    // the file cannot be extracted outside of the "archive_tmp" folder
    fs.createWriteStream(resolvedPath); // Compliant
  }
});