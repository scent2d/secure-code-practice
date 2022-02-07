/*
취약점 개요
XML bomb / billion laughs attack 은 반복적으로 반복되는 동일한 큰 엔터티를 포함하는 악성 XML 문서입니다. 
엔터티 확장 수에 대한 제한과 같은 제한이 없는 경우 XML 프로세서는 이러한 문서를 구문 분석하는 동안 많은 메모리와 시간을 소비하여 서비스 거부로 이어질 수 있습니다.
*/

// Noncompliant Code Example
// For DocumentBuilder, SAXParser and Schema and Transformer JAPX factories
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false); // Noncompliant

SAXParserFactory factory = SAXParserFactory.newInstance();
factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false); // Noncompliant

SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false); // Noncompliant

TransformerFactory factory = javax.xml.transform.TransformerFactory.newInstance();
factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false); // Noncompliant

// For Dom4j library
SAXReader xmlReader = new SAXReader();
xmlReader.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false); // Noncompliant

// For Jdom2 library
SAXBuilder builder = new SAXBuilder();
builder.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);  // Noncompliant

// Compliant Solution
// For DocumentBuilder, SAXParser and Schema and Transformer JAPX factories
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

SAXParserFactory factory = SAXParserFactory.newInstance();
factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

TransformerFactory factory = javax.xml.transform.TransformerFactory.newInstance();
factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

// For Dom4j library
SAXReader xmlReader = new SAXReader();
xmlReader.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

// For Jdom2 library
SAXBuilder builder = new SAXBuilder();
builder.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
