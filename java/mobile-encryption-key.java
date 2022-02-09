/*
취약점 개요
암호화 키는 비밀로 유지되어야 하며 다음을 의미하므로 애플리케이션에 하드 코딩되어서는 안 됩니다.
- 모든 사용자는 동일한 암호화 키를 사용합니다.
- 암호화 키는 소스 코드 또는 애플리케이션 바이너리 코드에 대한 액세스 권한이 있는 사람이라면 누구나 알 수 있습니다.
- 데이터베이스에 암호화되어 저장된 데이터는 보호되지 않습니다.

데이터베이스를 암호화하고 해독하기 위해 키를 제공하는 방법에는 다양한 접근 방식이 있습니다. 
가장 편리한 방법 중 하나는 EncryptedSharedPreferences암호화 키를 저장하는 것입니다. 또한 애플리케이션 사용자가 동적으로 제공하거나 원격 서버에서 가져올 수 있습니다.

*/


// Noncompliant Code Example
// SQLCipher
String key = "gb09ym9ydoolp3w886d0tciczj6ve9kszqd65u7d126040gwy86xqimjpuuc788g";
SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("test.db", key, null); // Noncompliant

// Realm
String key = "gb09ym9ydoolp3w886d0tciczj6ve9kszqd65u7d126040gwy86xqimjpuuc788g";
RealmConfiguration config = new RealmConfiguration.Builder();
    .encryptionKey(key.toByteArray()) // Noncompliant
    .build();
Realm realm = Realm.getInstance(config);

// Compliant Solution
// SQLCipher
SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("test.db", getKey(), null);

// Realm
RealmConfiguration config = new RealmConfiguration.Builder()
    .encryptionKey(getKey())
    .build();
Realm realm = Realm.getInstance(config);