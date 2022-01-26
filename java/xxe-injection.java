/*
취약점 개요
XXE Injection

XML 표준은 문서의 DOCTYPE에 선언된 엔티티의 사용을 허용하며 내부 또는 외부 일 수 있습니다 .

XML 파일을 파싱할 때 파일 시스템이나 네트워크와 같은 외부 저장소에서 외부 엔터티의 내용을 검색하므로 제한이 없으면 
임의 파일 공개 또는 SSRF(서버 측 요청 위조 )로 이어질 수 있습니다.

다음 솔루션 중 하나를 사용하여 외부 엔터티의 확인을 제한하는 것이 좋습니다.

DOCTYPE이 필요하지 않은 경우 모든 DOCTYPE 선언을 완전히 비활성화합니다.
외부 엔터티가 필요하지 않은 경우 해당 선언을 완전히 비활성화합니다.
외부 엔티티가 필요한 경우:
사용 가능한 경우 XML 프로세서 기능을 사용하여 필요한 프로토콜(예: https)만 승인합니다.
그리고 엔터티 확인자(및 선택적으로 XML 카탈로그)를 사용하여 신뢰할 수 있는 엔터티만 확인합니다. == 비준수 코드 예

*/


// XML 코드
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE person [
  <!ENTITY file SYSTEM "file:///etc/passwd">
  <!ENTITY ssrf SYSTEM "https://internal.network/sensitive_information">
]>

<person>
  <name>&file;</name>
  <city>&ssrf;</city>
  <age>18</age>
</person>
;

// Noncompliant Code Example
// For DocumentBuilder, SAXParser, XMLInput, Transformer and Schema JAPX factories
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); // Noncompliant
SAXParserFactory factory = SAXParserFactory.newInstance(); // Noncompliant
XMLInputFactory factory = XMLInputFactory.newInstance(); // Noncompliant
TransformerFactory factory = javax.xml.transform.TransformerFactory.newInstance();  // Noncompliant
SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);  // Noncompliant

// For Dom4j library
SAXReader xmlReader = new SAXReader(); // Noncompliant

// For Jdom2 library
SAXBuilder builder = new SAXBuilder(); // Noncompliant


// Compliant Solution
// Compliant Solution: For DocumentBuilder, SAXParser, XMLInput, Transformer and Schema JAPX factories
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
// to be compliant, completely disable DOCTYPE declaration:
factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
// or completely disable external entities declarations:
factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
// or prohibit the use of all protocols by external entities:
factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

SAXParserFactory factory = SAXParserFactory.newInstance();
// to be compliant, completely disable DOCTYPE declaration:
factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
// or completely disable external entities declarations:
factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
// or prohibit the use of all protocols by external entities:
SAXParser parser = factory.newSAXParser(); // Noncompliant
parser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
parser.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

XMLInputFactory factory = XMLInputFactory.newInstance();
// to be compliant, completely disable DOCTYPE declaration:
factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
// or completely disable external entities declarations:
factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, Boolean.FALSE);
// or prohibit the use of all protocols by external entities:
factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

TransformerFactory factory = javax.xml.transform.TransformerFactory.newInstance();
// to be compliant, prohibit the use of all protocols by external entities:
factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
// to be compliant, completely disable DOCTYPE declaration:
factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
// or prohibit the use of all protocols by external entities:
factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

// For Dom4j library
SAXReader xmlReader = new SAXReader();
xmlReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);

// For Jdom2 library
SAXBuilder builder = new SAXBuilder();
builder.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
builder.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");


