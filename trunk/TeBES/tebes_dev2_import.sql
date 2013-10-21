-- MySQL dump 10.13  Distrib 5.5.25a, for Win64 (x86)
--
-- Host: localhost    Database: tebes
-- ------------------------------------------------------
-- Server version	5.5.25a

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actionworkflow`
--

DROP TABLE IF EXISTS `actionworkflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actionworkflow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `actionMark` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actionworkflow`
--

LOCK TABLES `actionworkflow` WRITE;
/*!40000 ALTER TABLE `actionworkflow` DISABLE KEYS */;
INSERT INTO `actionworkflow` VALUES (1,NULL,1),(2,NULL,1);
/*!40000 ALTER TABLE `actionworkflow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `choreography`
--

DROP TABLE IF EXISTS `choreography`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `choreography` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choreography`
--

LOCK TABLES `choreography` WRITE;
/*!40000 ALTER TABLE `choreography` DISABLE KEYS */;
/*!40000 ALTER TABLE `choreography` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filestore`
--

DROP TABLE IF EXISTS `filestore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `filestore` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fileRefId` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `source` text,
  `creationDatetime` varchar(255) DEFAULT NULL,
  `lastUpdateDatetime` varchar(255) DEFAULT NULL,
  `session_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK285440A5C9C715D1` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filestore`
--

LOCK TABLES `filestore` WRITE;
/*!40000 ALTER TABLE `filestore` DISABLE KEYS */;
INSERT INTO `filestore` VALUES (1,'FI-1_document-xml-website-IN-1_TA-1_TP-1','20131021T140133Z_FI-1_document-xml-website-IN-1_TA-1_TP-1_UID-2_ubl-invoice.xml','document','ï»¿<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<?xml-stylesheet type=\"text/xsl\" href=\"stylesheets/CraneUBL2UN380Invoice-html-IT.xsl\"?>\r\n\r\n<!-- \r\n    Descrizione documento:     Esempio fattura emessa dall\'Azienda Unita\' Sanitaria Locale di Reggio Emilia \r\n    Prodotto il :              Febbraio 2012      \r\n    Prodotto da :              A. Brutti\r\n    Ambito:                    Intercent-ER - Piloti Peppol\r\n-->\r\n\r\n\r\n<in:Invoice xmlns:in=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\"\r\n    xmlns:cac=\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\"\r\n    xmlns:cbc=\"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\"\r\n    xmlns:ccts=\"urn:un:unece:uncefact:documentation:2\"\r\n    xmlns:ext=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\"\r\n    xmlns:qdt=\"urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2\"\r\n    xmlns:udt=\"urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2\"\r\n    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\r\n    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n    xsi:schemaLocation=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2 http://docs.oasis-open.org/ubl/os-UBL-2.0/xsd/maindoc/UBL-Invoice-2.0.xsd\">\r\n    <cbc:UBLVersionID>2.0</cbc:UBLVersionID>\r\n    <cbc:CustomizationID schemeID=\"PEPPOL\">urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0</cbc:CustomizationID>\r\n    <cbc:ProfileID>urn:www.cenbii.eu:profile:bii04:ver1.0</cbc:ProfileID>    \r\n	<!-- cbc:ID>2/ 28</cbc:ID -->\r\n    <cbc:IssueDate>2012-01-31</cbc:IssueDate>\r\n    <cbc:InvoiceTypeCode listID=\"UN/ECE 1001\" listAgencyID=\"6\">380</cbc:InvoiceTypeCode>\r\n    <cbc:DocumentCurrencyCode listID=\"ISO 4217 Alpha\" listAgencyID=\"5\">EUR</cbc:DocumentCurrencyCode>\r\n     <cac:AccountingSupplierParty>\r\n        <cac:Party>\r\n            <cac:PartyName>\r\n                <cbc:Name>Azienda Unita\' Sanitaria Locale di Reggio Emilia</cbc:Name>\r\n            </cac:PartyName>\r\n            <cac:PostalAddress>\r\n                <cbc:StreetName>Via Amendola</cbc:StreetName>\r\n                <cbc:BuildingNumber>2</cbc:BuildingNumber>\r\n                <cbc:CityName>Reggio nell\'Emilia</cbc:CityName>\r\n                <cbc:PostalZone>42100</cbc:PostalZone>\r\n                <cbc:CountrySubentity>Reggio nell\'Emilia</cbc:CountrySubentity>\r\n                <cac:Country>\r\n                    <cbc:IdentificationCode listID=\"ISO3166-1\" listAgencyID=\"5\">IT</cbc:IdentificationCode>\r\n                </cac:Country>\r\n            </cac:PostalAddress>\r\n            <cac:PartyTaxScheme>\r\n                <cbc:CompanyID schemeID=\"IT:VAT\"\r\n                    schemeURI=\"urn:oasis:names:tc:ebcore:partyid-type:unregistered:bii:it:vat\">IT01598570354</cbc:CompanyID>\r\n                <cac:TaxScheme>\r\n                    <cbc:ID schemeID=\"UN/ECE 5153\" schemeAgencyID=\"6\">VAT</cbc:ID>\r\n                </cac:TaxScheme>\r\n            </cac:PartyTaxScheme>   \r\n        </cac:Party>\r\n    </cac:AccountingSupplierParty>\r\n    <cac:AccountingCustomerParty>\r\n        <cac:Party>\r\n            <cac:PartyName>\r\n                <cbc:Name>ARCISPEDALE SANTA MARIA NUOVA</cbc:Name>\r\n            </cac:PartyName>\r\n            <cac:PostalAddress>\r\n                <cbc:StreetName>VIALE  RISORGIMENTO</cbc:StreetName>\r\n                <cbc:BuildingNumber>80</cbc:BuildingNumber>\r\n                <cbc:CityName>Reggio nell\'Emilia</cbc:CityName>\r\n                <cbc:PostalZone>42100</cbc:PostalZone>\r\n                <cbc:CountrySubentity>Reggio nell\'Emilia</cbc:CountrySubentity>\r\n                <cac:Country>\r\n                    <cbc:IdentificationCode listID=\"ISO3166-1\" listAgencyID=\"5\">IT</cbc:IdentificationCode>\r\n                </cac:Country>\r\n            </cac:PostalAddress>\r\n            <cac:PartyTaxScheme>\r\n                <cbc:CompanyID schemeID=\"IT:VAT\"\r\n                    schemeURI=\"urn:oasis:names:tc:ebcore:partyid-type:unregistered:bii:it:vat\">IT01614660353</cbc:CompanyID>\r\n                <cac:TaxScheme>\r\n                    <cbc:ID schemeID=\"UN/ECE 5153\" schemeAgencyID=\"6\">VAT</cbc:ID>\r\n                </cac:TaxScheme>\r\n            </cac:PartyTaxScheme>\r\n        </cac:Party>\r\n    </cac:AccountingCustomerParty>\r\n    <cac:TaxTotal>\r\n        <cbc:TaxAmount currencyID=\"EUR\">0.37</cbc:TaxAmount>\r\n        <cac:TaxSubtotal>\r\n            <cbc:TaxableAmount currencyID=\"EUR\">3.63</cbc:TaxableAmount>\r\n            <cbc:TaxAmount currencyID=\"EUR\">0.37</cbc:TaxAmount>\r\n            <cac:TaxCategory>\r\n                <cbc:ID schemeID=\"UN/ECE 5305\" schemeAgencyID=\"6\">S</cbc:ID>\r\n                <cbc:Percent>10</cbc:Percent>\r\n                <cac:TaxScheme >\r\n                    <cbc:ID schemeID=\"UN/ECE 5153\" schemeAgencyID=\"6\">VAT</cbc:ID>\r\n                </cac:TaxScheme>\r\n            </cac:TaxCategory>\r\n        </cac:TaxSubtotal>        \r\n    </cac:TaxTotal>\r\n    <cac:LegalMonetaryTotal>\r\n        <cbc:LineExtensionAmount currencyID=\"EUR\">3.63</cbc:LineExtensionAmount>\r\n        <cbc:TaxExclusiveAmount currencyID=\"EUR\">3.63</cbc:TaxExclusiveAmount>\r\n        <cbc:TaxInclusiveAmount currencyID=\"EUR\">4</cbc:TaxInclusiveAmount>\r\n        <cbc:PayableRoundingAmount currencyID=\"EUR\">0.00162</cbc:PayableRoundingAmount>\r\n        <cbc:PayableAmount currencyID=\"EUR\">4</cbc:PayableAmount>\r\n    </cac:LegalMonetaryTotal>\r\n    <cac:InvoiceLine>\r\n        <cbc:ID>1</cbc:ID>\r\n        <cbc:InvoicedQuantity unitCode=\"AV\">16</cbc:InvoicedQuantity>\r\n        <cbc:LineExtensionAmount currencyID=\"EUR\">3.05</cbc:LineExtensionAmount>\r\n        <cac:DespatchLineReference>\r\n            <cbc:LineID>1</cbc:LineID>\r\n            <cac:DocumentReference>\r\n                <cbc:ID>28/ B</cbc:ID>\r\n                <cbc:IssueDate>2012-01-02</cbc:IssueDate>\r\n                <cbc:DocumentType>Legge ... </cbc:DocumentType>\r\n            </cac:DocumentReference>            \r\n        </cac:DespatchLineReference> \r\n        <cac:TaxTotal>\r\n            <cbc:TaxAmount currencyID=\"EUR\">0.31</cbc:TaxAmount>\r\n            <cac:TaxSubtotal>\r\n                <cbc:TaxAmount currencyID=\"EUR\">0.304736</cbc:TaxAmount>                \r\n                <cac:TaxCategory>\r\n                    <!-- da verificare se e\' effettivamente S -->\r\n                    <cbc:ID schemeID=\"UN/ECE 5305\" schemeAgencyID=\"6\">S</cbc:ID>\r\n                    <cbc:Percent>10</cbc:Percent>\r\n                    <cac:TaxScheme>\r\n                        <cbc:ID schemeID=\"UN/ECE 5153\" schemeAgencyID=\"6\">VAT</cbc:ID>\r\n                    </cac:TaxScheme>\r\n                </cac:TaxCategory>\r\n            </cac:TaxSubtotal>            \r\n        </cac:TaxTotal>\r\n        <cac:Item>            \r\n            <cbc:Name>AMBRAMICINA MG.250</cbc:Name>\r\n        </cac:Item>\r\n        <cac:Price>\r\n            <cbc:PriceAmount currencyID=\"EUR\">0.19046</cbc:PriceAmount>\r\n        </cac:Price>\r\n    </cac:InvoiceLine>\r\n    <cac:InvoiceLine>\r\n        <cbc:ID>2</cbc:ID>\r\n        <cbc:InvoicedQuantity unitCode=\"BX\">1</cbc:InvoicedQuantity>\r\n        <cbc:LineExtensionAmount currencyID=\"EUR\">0.58</cbc:LineExtensionAmount>\r\n        <cac:DespatchLineReference>\r\n            <cbc:LineID>1</cbc:LineID>\r\n            <cac:DocumentReference>\r\n                <cbc:ID>31/B</cbc:ID>\r\n                <cbc:IssueDate>2012-01-02</cbc:IssueDate>\r\n                <cbc:DocumentType>Legge ... </cbc:DocumentType>\r\n            </cac:DocumentReference>            \r\n        </cac:DespatchLineReference>        \r\n        <cac:TaxTotal>\r\n            <cbc:TaxAmount currencyID=\"EUR\">0.06</cbc:TaxAmount>\r\n            <cac:TaxSubtotal>\r\n                <cbc:TaxAmount currencyID=\"EUR\">0.058102</cbc:TaxAmount>\r\n                <cac:TaxCategory>\r\n                    <!-- da verificare se e\' effettivamente S -->\r\n                    <cbc:ID schemeID=\"UN/ECE 5305\" schemeAgencyID=\"6\">S</cbc:ID>\r\n                    <cbc:Percent>10</cbc:Percent>\r\n                    <cac:TaxScheme>\r\n                        <cbc:ID schemeID=\"UN/ECE 5153\" schemeAgencyID=\"6\">VAT</cbc:ID>\r\n                    </cac:TaxScheme>\r\n                </cac:TaxCategory>\r\n            </cac:TaxSubtotal>            \r\n        </cac:TaxTotal>\r\n        <cac:Item>            \r\n            <cbc:Name>FITOSTIMOLINE 10 GARZE</cbc:Name>\r\n        </cac:Item>\r\n        <cac:Price>\r\n            <cbc:PriceAmount currencyID=\"EUR\">0.58102</cbc:PriceAmount>\r\n        </cac:Price>\r\n    </cac:InvoiceLine>\r\n</in:Invoice>\r\n','2013-10-21T14:01:33Z','2013-10-21T14:01:33Z',NULL),(2,'FI-2_document-xml-website-IN-2_TA-1_TP-1','20131021T140133Z_FI-2_document-xml-website-IN-2_TA-1_TP-1_UID-2_ubl-invoice.xml','document','ï»¿<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<?xml-stylesheet type=\"text/xsl\" href=\"stylesheets/CraneUBL2UN380Invoice-html-IT.xsl\"?>\r\n\r\n<!-- \r\n    Descrizione documento:     Esempio fattura emessa dall\'Azienda Unita\' Sanitaria Locale di Reggio Emilia \r\n    Prodotto il :              Febbraio 2012      \r\n    Prodotto da :              A. Brutti\r\n    Ambito:                    Intercent-ER - Piloti Peppol\r\n-->\r\n\r\n\r\n<in:Invoice xmlns:in=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\"\r\n    xmlns:cac=\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\"\r\n    xmlns:cbc=\"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\"\r\n    xmlns:ccts=\"urn:un:unece:uncefact:documentation:2\"\r\n    xmlns:ext=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\"\r\n    xmlns:qdt=\"urn:oasis:names:specification:ubl:schema:xsd:QualifiedDatatypes-2\"\r\n    xmlns:udt=\"urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2\"\r\n    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\r\n    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\r\n    xsi:schemaLocation=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2 http://docs.oasis-open.org/ubl/os-UBL-2.0/xsd/maindoc/UBL-Invoice-2.0.xsd\">\r\n    <cbc:UBLVersionID>2.0</cbc:UBLVersionID>\r\n    <cbc:CustomizationID schemeID=\"PEPPOL\">urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0</cbc:CustomizationID>\r\n    <cbc:ProfileID>urn:www.cenbii.eu:profile:bii04:ver1.0</cbc:ProfileID>    \r\n	<!-- cbc:ID>2/ 28</cbc:ID -->\r\n    <cbc:IssueDate>2012-01-31</cbc:IssueDate>\r\n    <cbc:InvoiceTypeCode listID=\"UN/ECE 1001\" listAgencyID=\"6\">380</cbc:InvoiceTypeCode>\r\n    <cbc:DocumentCurrencyCode listID=\"ISO 4217 Alpha\" listAgencyID=\"5\">EUR</cbc:DocumentCurrencyCode>\r\n     <cac:AccountingSupplierParty>\r\n        <cac:Party>\r\n            <cac:PartyName>\r\n                <cbc:Name>Azienda Unita\' Sanitaria Locale di Reggio Emilia</cbc:Name>\r\n            </cac:PartyName>\r\n            <cac:PostalAddress>\r\n                <cbc:StreetName>Via Amendola</cbc:StreetName>\r\n                <cbc:BuildingNumber>2</cbc:BuildingNumber>\r\n                <cbc:CityName>Reggio nell\'Emilia</cbc:CityName>\r\n                <cbc:PostalZone>42100</cbc:PostalZone>\r\n                <cbc:CountrySubentity>Reggio nell\'Emilia</cbc:CountrySubentity>\r\n                <cac:Country>\r\n                    <cbc:IdentificationCode listID=\"ISO3166-1\" listAgencyID=\"5\">IT</cbc:IdentificationCode>\r\n                </cac:Country>\r\n            </cac:PostalAddress>\r\n            <cac:PartyTaxScheme>\r\n                <cbc:CompanyID schemeID=\"IT:VAT\"\r\n                    schemeURI=\"urn:oasis:names:tc:ebcore:partyid-type:unregistered:bii:it:vat\">IT01598570354</cbc:CompanyID>\r\n                <cac:TaxScheme>\r\n                    <cbc:ID schemeID=\"UN/ECE 5153\" schemeAgencyID=\"6\">VAT</cbc:ID>\r\n                </cac:TaxScheme>\r\n            </cac:PartyTaxScheme>   \r\n        </cac:Party>\r\n    </cac:AccountingSupplierParty>\r\n    <cac:AccountingCustomerParty>\r\n        <cac:Party>\r\n            <cac:PartyName>\r\n                <cbc:Name>ARCISPEDALE SANTA MARIA NUOVA</cbc:Name>\r\n            </cac:PartyName>\r\n            <cac:PostalAddress>\r\n                <cbc:StreetName>VIALE  RISORGIMENTO</cbc:StreetName>\r\n                <cbc:BuildingNumber>80</cbc:BuildingNumber>\r\n                <cbc:CityName>Reggio nell\'Emilia</cbc:CityName>\r\n                <cbc:PostalZone>42100</cbc:PostalZone>\r\n                <cbc:CountrySubentity>Reggio nell\'Emilia</cbc:CountrySubentity>\r\n                <cac:Country>\r\n                    <cbc:IdentificationCode listID=\"ISO3166-1\" listAgencyID=\"5\">IT</cbc:IdentificationCode>\r\n                </cac:Country>\r\n            </cac:PostalAddress>\r\n            <cac:PartyTaxScheme>\r\n                <cbc:CompanyID schemeID=\"IT:VAT\"\r\n                    schemeURI=\"urn:oasis:names:tc:ebcore:partyid-type:unregistered:bii:it:vat\">IT01614660353</cbc:CompanyID>\r\n                <cac:TaxScheme>\r\n                    <cbc:ID schemeID=\"UN/ECE 5153\" schemeAgencyID=\"6\">VAT</cbc:ID>\r\n                </cac:TaxScheme>\r\n            </cac:PartyTaxScheme>\r\n        </cac:Party>\r\n    </cac:AccountingCustomerParty>\r\n    <cac:TaxTotal>\r\n        <cbc:TaxAmount currencyID=\"EUR\">0.37</cbc:TaxAmount>\r\n        <cac:TaxSubtotal>\r\n            <cbc:TaxableAmount currencyID=\"EUR\">3.63</cbc:TaxableAmount>\r\n            <cbc:TaxAmount currencyID=\"EUR\">0.37</cbc:TaxAmount>\r\n            <cac:TaxCategory>\r\n                <cbc:ID schemeID=\"UN/ECE 5305\" schemeAgencyID=\"6\">S</cbc:ID>\r\n                <cbc:Percent>10</cbc:Percent>\r\n                <cac:TaxScheme >\r\n                    <cbc:ID schemeID=\"UN/ECE 5153\" schemeAgencyID=\"6\">VAT</cbc:ID>\r\n                </cac:TaxScheme>\r\n            </cac:TaxCategory>\r\n        </cac:TaxSubtotal>        \r\n    </cac:TaxTotal>\r\n    <cac:LegalMonetaryTotal>\r\n        <cbc:LineExtensionAmount currencyID=\"EUR\">3.63</cbc:LineExtensionAmount>\r\n        <cbc:TaxExclusiveAmount currencyID=\"EUR\">3.63</cbc:TaxExclusiveAmount>\r\n        <cbc:TaxInclusiveAmount currencyID=\"EUR\">4</cbc:TaxInclusiveAmount>\r\n        <cbc:PayableRoundingAmount currencyID=\"EUR\">0.00162</cbc:PayableRoundingAmount>\r\n        <cbc:PayableAmount currencyID=\"EUR\">4</cbc:PayableAmount>\r\n    </cac:LegalMonetaryTotal>\r\n    <cac:InvoiceLine>\r\n        <cbc:ID>1</cbc:ID>\r\n        <cbc:InvoicedQuantity unitCode=\"AV\">16</cbc:InvoicedQuantity>\r\n        <cbc:LineExtensionAmount currencyID=\"EUR\">3.05</cbc:LineExtensionAmount>\r\n        <cac:DespatchLineReference>\r\n            <cbc:LineID>1</cbc:LineID>\r\n            <cac:DocumentReference>\r\n                <cbc:ID>28/ B</cbc:ID>\r\n                <cbc:IssueDate>2012-01-02</cbc:IssueDate>\r\n                <cbc:DocumentType>Legge ... </cbc:DocumentType>\r\n            </cac:DocumentReference>            \r\n        </cac:DespatchLineReference> \r\n        <cac:TaxTotal>\r\n            <cbc:TaxAmount currencyID=\"EUR\">0.31</cbc:TaxAmount>\r\n            <cac:TaxSubtotal>\r\n                <cbc:TaxAmount currencyID=\"EUR\">0.304736</cbc:TaxAmount>                \r\n                <cac:TaxCategory>\r\n                    <!-- da verificare se e\' effettivamente S -->\r\n                    <cbc:ID schemeID=\"UN/ECE 5305\" schemeAgencyID=\"6\">S</cbc:ID>\r\n                    <cbc:Percent>10</cbc:Percent>\r\n                    <cac:TaxScheme>\r\n                        <cbc:ID schemeID=\"UN/ECE 5153\" schemeAgencyID=\"6\">VAT</cbc:ID>\r\n                    </cac:TaxScheme>\r\n                </cac:TaxCategory>\r\n            </cac:TaxSubtotal>            \r\n        </cac:TaxTotal>\r\n        <cac:Item>            \r\n            <cbc:Name>AMBRAMICINA MG.250</cbc:Name>\r\n        </cac:Item>\r\n        <cac:Price>\r\n            <cbc:PriceAmount currencyID=\"EUR\">0.19046</cbc:PriceAmount>\r\n        </cac:Price>\r\n    </cac:InvoiceLine>\r\n    <cac:InvoiceLine>\r\n        <cbc:ID>2</cbc:ID>\r\n        <cbc:InvoicedQuantity unitCode=\"BX\">1</cbc:InvoicedQuantity>\r\n        <cbc:LineExtensionAmount currencyID=\"EUR\">0.58</cbc:LineExtensionAmount>\r\n        <cac:DespatchLineReference>\r\n            <cbc:LineID>1</cbc:LineID>\r\n            <cac:DocumentReference>\r\n                <cbc:ID>31/B</cbc:ID>\r\n                <cbc:IssueDate>2012-01-02</cbc:IssueDate>\r\n                <cbc:DocumentType>Legge ... </cbc:DocumentType>\r\n            </cac:DocumentReference>            \r\n        </cac:DespatchLineReference>        \r\n        <cac:TaxTotal>\r\n            <cbc:TaxAmount currencyID=\"EUR\">0.06</cbc:TaxAmount>\r\n            <cac:TaxSubtotal>\r\n                <cbc:TaxAmount currencyID=\"EUR\">0.058102</cbc:TaxAmount>\r\n                <cac:TaxCategory>\r\n                    <!-- da verificare se e\' effettivamente S -->\r\n                    <cbc:ID schemeID=\"UN/ECE 5305\" schemeAgencyID=\"6\">S</cbc:ID>\r\n                    <cbc:Percent>10</cbc:Percent>\r\n                    <cac:TaxScheme>\r\n                        <cbc:ID schemeID=\"UN/ECE 5153\" schemeAgencyID=\"6\">VAT</cbc:ID>\r\n                    </cac:TaxScheme>\r\n                </cac:TaxCategory>\r\n            </cac:TaxSubtotal>            \r\n        </cac:TaxTotal>\r\n        <cac:Item>            \r\n            <cbc:Name>FITOSTIMOLINE 10 GARZE</cbc:Name>\r\n        </cac:Item>\r\n        <cac:Price>\r\n            <cbc:PriceAmount currencyID=\"EUR\">0.58102</cbc:PriceAmount>\r\n        </cac:Price>\r\n    </cac:InvoiceLine>\r\n</in:Invoice>\r\n','2013-10-21T14:01:33Z','2013-10-21T14:01:33Z',NULL);
/*!40000 ALTER TABLE `filestore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `sessionID` bigint(20) DEFAULT NULL,
  `datetime` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `fullDescription` mediumtext,
  `xml` mediumtext,
  `partialResultSuccessfully` bit(1) NOT NULL,
  `finalResultSuccessfully` bit(1) NOT NULL,
  `tempResult_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK91B1415424E8F41` (`tempResult_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'standard_user','Standard User Role: he can execute a test plan but he can\'t create/edit it',1),(2,'advanced_user','Advanced User Role: he can create/edit/execute a test plan',2),(3,'admin_user','System Administrator Role: he is an advanced user and can add/modify test suites',3),(4,'super_user','Super User Role: he has whole power and permissions on test bed platform',4);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` varchar(255) DEFAULT NULL,
  `creationDateTime` varchar(255) DEFAULT NULL,
  `lastUpdateDateTime` varchar(255) DEFAULT NULL,
  `messageStore` text,
  `sut_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL,
  `testPlan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKD9891A76D92EA83` (`report_id`),
  KEY `FKD9891A76AC268D63` (`user_id`),
  KEY `FKD9891A764041EF03` (`testPlan_id`),
  KEY `FKD9891A76C8782BD1` (`sut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sut`
--

DROP TABLE IF EXISTS `sut`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sut` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK14232AC268D63` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sut`
--

LOCK TABLES `sut` WRITE;
/*!40000 ALTER TABLE `sut` DISABLE KEYS */;
INSERT INTO `sut` VALUES (1,'SystemSUT1','System SUT 1: Document - WebSite','document',1),(2,'SystemSUT2','System SUT 2: Document - email','document',1),(3,'SystemSUT3','System SUT 3: Document - Web Service Client','document',1),(4,'SystemSUT4','System SUT 4: Document - Web Service Server','document',1),(5,'SystemSUT5','System SUT 5: Transport - email','transport',1),(6,'SystemSUT6','System SUT 6: Transport - Web Service Client','transport',1),(7,'SystemSUT7','System SUT 7: Transport - Web Service Server','transport',1),(8,'SystemSUT8','System SUT 8: Process - WebSite','process',1),(9,'SystemSUT9','System SUT 9: Process - email','process',1),(10,'SystemSUT10','System SUT 10: Process - Web Service Client','process',1),(11,'SystemSUT11','System SUT 11: Process - Web Service Server','process',1);
/*!40000 ALTER TABLE `sut` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sutinteraction`
--

DROP TABLE IF EXISTS `sutinteraction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sutinteraction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `endpoint` varchar(255) DEFAULT NULL,
  `sut_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC4084080C8782BD1` (`sut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sutinteraction`
--

LOCK TABLES `sutinteraction` WRITE;
/*!40000 ALTER TABLE `sutinteraction` DISABLE KEYS */;
INSERT INTO `sutinteraction` VALUES (1,'website',NULL,1),(2,'email',NULL,2),(3,'webservice-client',NULL,3),(4,'webservice-server',NULL,4),(5,'email',NULL,5),(6,'webservice-client',NULL,6),(7,'webservice-server',NULL,7),(8,'website',NULL,8),(9,'email',NULL,9),(10,'webservice-client',NULL,10),(11,'webservice-server',NULL,11);
/*!40000 ALTER TABLE `sutinteraction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testaction`
--

DROP TABLE IF EXISTS `testaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` varchar(255) DEFAULT NULL,
  `prerequisite` bit(1) NOT NULL,
  `actionNumber` int(11) NOT NULL,
  `actionName` varchar(255) DEFAULT NULL,
  `actionId` varchar(255) DEFAULT NULL,
  `testLanguage` varchar(255) DEFAULT NULL,
  `testType` varchar(255) DEFAULT NULL,
  `testLocation` varchar(255) DEFAULT NULL,
  `testValue` varchar(255) DEFAULT NULL,
  `skipTurnedON` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `workflow_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBE2755886864CDF9` (`workflow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testaction`
--

LOCK TABLES `testaction` WRITE;
/*!40000 ALTER TABLE `testaction` DISABLE KEYS */;
INSERT INTO `testaction` VALUES (1,'new','\0',1,'XMLSchema-UBL-T10','TA-1_TP-1','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml','TA-001_TC-001_TS-001','','UBL Invoice XML Schema Validation.',1),(2,'new','\0',2,'SDI-UBL','TA-2_TP-1','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml','TA-001_TC-001_TS-005','\0','Italian INTERCENT-ER UBL Invoice Schematron Validation.',1),(3,'new','\0',1,'XMLSchema-UBL-T10','TA-1_TP-2','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml','TA-001_TC-001_TS-001','','UBL Invoice XML Schema Validation.',2),(4,'new','\0',2,'SDI-UBL','TA-2_TP-2','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml','TA-001_TC-001_TS-005','\0','Italian INTERCENT-ER UBL Invoice Schematron Validation.',2);
/*!40000 ALTER TABLE `testaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testplan`
--

DROP TABLE IF EXISTS `testplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testplan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creationDatetime` varchar(255) DEFAULT NULL,
  `lastUpdateDatetime` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `workflow_id` bigint(20) DEFAULT NULL,
  `testplanxml_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBBB236BBE3ACFC51` (`testplanxml_id`),
  KEY `FKBBB236BBAC268D63` (`user_id`),
  KEY `FKBBB236BB6864CDF9` (`workflow_id`),
  CONSTRAINT `FKBBB236BB6864CDF9` FOREIGN KEY (`workflow_id`) REFERENCES `actionworkflow` (`id`),
  CONSTRAINT `FKBBB236BBAC268D63` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKBBB236BBE3ACFC51` FOREIGN KEY (`testplanxml_id`) REFERENCES `testplanxml` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testplan`
--

LOCK TABLES `testplan` WRITE;
/*!40000 ALTER TABLE `testplan` DISABLE KEYS */;
INSERT INTO `testplan` VALUES (1,'TP-1','Test Plan TP-1 defined for the TeBES development','2013-10-21T14:01:32Z','2013-10-21T14:01:32Z','draft','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-1.xml',1,1,1),(2,'TP-2','Test Plan TP-2 defined for the TeBES development','2013-10-21T14:01:32Z','2013-10-21T14:01:32Z','draft','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-2.xml',1,2,2);
/*!40000 ALTER TABLE `testplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testplanxml`
--

DROP TABLE IF EXISTS `testplanxml`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testplanxml` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `absFileName` varchar(255) DEFAULT NULL,
  `xml` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testplanxml`
--

LOCK TABLES `testplanxml` WRITE;
/*!40000 ALTER TABLE `testplanxml` DISABLE KEYS */;
INSERT INTO `testplanxml` VALUES (1,'C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-1.xml','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!-- \n    Document Description:		Test Plan created for the TeBES Platform (X-Lab, Cross-TEC, ENEA)\n    Creation Date:             	2013, 1 February\n    Last Update Date:			2013, 28 July  \n    Author:              		Cristiano Novelli\n    Project:                    Tecnopolo\n-->\r\n<tebes:TestPlan xmlns:tebes=\"http://www.ubl-italia.org/TeBES\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" creationDatetime=\"2013-06-27T18:43:00Z\" description=\"Test Plan TP-1 defined for the TeBES development\" id=\"1\" lastUpdateDatetime=\"2013-06-27T18:44:00Z\" name=\"TP-1\" state=\"draft\" userID=\"0\">\r\n	<!-- \n		Every \"id\" attribute takes the unique value of the identifier generated by DBMS for the related entity. \n		The \"datetime\" attribute is related to the export file datetime.\n		The state attribute can take 2 values: \"draft\" or \"final\" \n	-->\r\n	\r\n	<tebes:TestActionList>\r\n	\r\n		<tebes:TestAction id=\"TA-1_TP-1\" number=\"1\">\r\n			\r\n			<tebes:ActionName>XMLSchema-UBL-T10</tebes:ActionName>\r\n			<tebes:ActionDescription>UBL Invoice XML Schema Validation.</tebes:ActionDescription>\r\n			<tebes:Test lg=\"taml\" location=\"http://www.ubl-italia.org/TeBES/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-001_TC-001_TS-001</tebes:Test>\r\n			<tebes:Input description=\"Send UBL Invoice\" fileIdRef=\"FI-1_document-xml-website-IN-1_TA-1_TP-1\" guiMessage=\"Message for Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1\" guiReaction=\"upload\" id=\"IN-1_TA-1_TP-1\" lg=\"xml\" name=\"UBLInvoice\" sutInteraction=\"website\" type=\"document\">\r\n			</tebes:Input>\r\n			<!-- Questo secondo input ha lo stesso fileIdRef, quindi indica che richiederà lo stesso file -->\r\n			<tebes:Input description=\"Send UBL Invoice2\" fileIdRef=\"FI-2_document-xml-website-IN-2_TA-1_TP-1\" guiMessage=\"Message for Upload of Input with fileIdRef = FI-2_document-xml-website-IN-2_TA-1_TP-1\" guiReaction=\"upload\" id=\"IN-2_TA-1_TP-1\" lg=\"xml\" name=\"UBLInvoice2\" sutInteraction=\"website\" type=\"document\">\r\n			</tebes:Input>\r\n		</tebes:TestAction>\r\n	\r\n	\r\n		<!-- \n			Each tebes:TestAction MUST have a @number attribute used to enumerate every action.\n			The N actions will be execute from 1 to N, sequentially, \n			excepted different specification expressed through the tebes:TestPlan/tebes:Choreography element\n			\n			Each tebes:Test element MUST have a @type attribute used with one and only one of these values:\n				- \"TestAssertion\" \n				- \"TestCase\" \n				- \"TestSuite\" \n		-->\r\n		<tebes:TestAction id=\"TA-2_TP-1\" number=\"2\">\r\n			\r\n			<!-- \n				The following \"tebes:ActionName\" element can take different values \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then tebes:ActionName element takes taml:testAssertion/@name from Test Assertion\n					(f.e. \"SDI-UBL\")\n				- \"TestCase\" \n					then tebes:ActionName element takes taml:testAssertionSet/@setname from Test Case\n					(f.e. \"Test Case for UBL Invoice Italian INTERCENT-ER Schematron validation\")\n				- \"TestSuite\" \n					then tebes:ActionName element takes Test Suite name folder\n					(f.e. \"TS-005_sdi\")\n			-->\r\n			<tebes:ActionName>SDI-UBL</tebes:ActionName>\r\n			\r\n			<!-- \n				The following \"tebes:ActionDescription\" element can take different values \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then Name element takes taml:testAssertion/taml:description\n				- \"TestCase\" \n					then Name element takes taml:testAssertionSet/@setname (or user input)\n				- \"TestSuite\" then Name element takes Test Suite name folder  (or user input)\n			-->\r\n			<tebes:ActionDescription>Italian INTERCENT-ER UBL Invoice Schematron Validation.</tebes:ActionDescription>\r\n			\r\n				\r\n			<!-- \n				The tebes:Test element contains the reference to test to execute, \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then tebes:Test element takes taml:testAssertion/@id from Test Assertion\n					(f.e. \"TA-001_TC-001_TS-005\")\n				- \"TestCase\" \n					then tebes:Test element takes taml:testAssertionSet/@setid from Test Case\n					(f.e. \"TC-001_TS-005\")\n				- \"TestSuite\" \n					then tebes:Test element takes Test Suite name folder prefix\n					(f.e. \"TS-005\")\n					\n				The tebes:Test/@location specifies the location of the test:\n				- an XML file absolute path, in the cases of \"TestAssertion\" or \"TestCase\" types;\n				- a folder absolute path, in the case of \"TestSuite\" type\n			-->\r\n			<tebes:Test lg=\"taml\" location=\"http://www.ubl-italia.org/TeBES/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml\" skipPrerequisites=\"false\" type=\"TestAssertion\">TA-001_TC-001_TS-005</tebes:Test>\r\n				\r\n			<!-- \n				tebes:Input è un elemento che specifica l\'input da parte del SUT \n				(o il SUT stesso se l\'elemento è uno)\n				su cui sarà eseguita quest\'action.\n				Nell\'action corrente, per esempio, il SUT è la fattura UBL.\n				I seguenti attributi possono assumere i seguenti valori:\n				- type: \"document\", \"transport\", \"process\";			\n				- description: descrizione testuale\n			-->\r\n			<tebes:Input description=\"Send UBL Invoice\" fileIdRef=\"FI-1_document-xml-website-IN-1_TA-1_TP-1\" guiMessage=\"Message for Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1\" guiReaction=\"upload\" id=\"IN-1_TA-2_TP-1\" lg=\"xml\" name=\"UBLInvoice\" sutInteraction=\"website\" type=\"document\">\r\n			</tebes:Input>\r\n		</tebes:TestAction>\r\n		\r\n	</tebes:TestActionList>\r\n\r\n	<!-- \n		The \"tebes:Choreography\" element specifies a choreography among the defined test actions. \n		If this element is empty, the N actions MUST be executed from number 1 to N, sequentially.\n		\n		The choreography XML element is work in progress:\n		a solution to express choreography may be an ebBP-like XML syntax. \n	-->	\r\n	<tebes:Choreography/>\r\n	\r\n</tebes:TestPlan>\r\n'),(2,'C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-2.xml','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!-- \n    Document Description:		Test Plan created for the TeBES Platform (X-Lab, Cross-TEC, ENEA)\n    Creation Date:             	2013, 1 February\n    Last Update Date:			2013, 28 July  \n    Author:              		Cristiano Novelli\n    Project:                    Tecnopolo\n-->\r\n<tebes:TestPlan xmlns:tebes=\"http://www.ubl-italia.org/TeBES\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" creationDatetime=\"2013-06-28T18:43:00Z\" description=\"Test Plan TP-2 defined for the TeBES development\" id=\"2\" lastUpdateDatetime=\"2013-06-28T18:44:00Z\" name=\"TP-2\" state=\"draft\" userID=\"0\">\r\n	<!-- \n		Every \"id\" attribute takes the unique value of the identifier generated by DBMS for the related entity. \n		The \"datetime\" attribute is related to the export file datetime.\n		The state attribute can take 2 values: \"draft\" or \"final\" \n	-->\r\n			\r\n	<tebes:TestActionList>\r\n	\r\n		<tebes:TestAction id=\"TA-1_TP-2\" number=\"1\">\r\n			\r\n			<tebes:ActionName>XMLSchema-UBL-T10</tebes:ActionName>\r\n			<tebes:ActionDescription>UBL Invoice XML Schema Validation.</tebes:ActionDescription>\r\n			<tebes:Test lg=\"taml\" location=\"http://www.ubl-italia.org/TeBES/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-001_TC-001_TS-001</tebes:Test>\r\n			<tebes:Input description=\"Send UBL Invoice\" fileIdRef=\"FI-1_document-xml-website-IN-1_TA-1_TP-2\" guiMessage=\"Message for Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-2\" guiReaction=\"upload\" id=\"IN-1_TA-1_TP-2\" lg=\"xml\" name=\"UBLInvoice\" sutInteraction=\"website\" type=\"document\">\r\n			</tebes:Input>		\r\n		</tebes:TestAction>\r\n	\r\n	\r\n		<!-- \n			Each tebes:TestAction MUST have a @number attribute used to enumerate every action.\n			The N actions will be execute from 1 to N, sequentially, \n			excepted different specification expressed through the tebes:TestPlan/tebes:Choreography element\n			\n			Each tebes:Test element MUST have a @type attribute used with one and only one of these values:\n				- \"TestAssertion\" \n				- \"TestCase\" \n				- \"TestSuite\" \n		-->\r\n		<tebes:TestAction id=\"TA-2_TP-2\" number=\"2\">\r\n			\r\n			<!-- \n				The following \"tebes:ActionName\" element can take different values \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then tebes:ActionName element takes taml:testAssertion/@name from Test Assertion\n					(f.e. \"SDI-UBL\")\n				- \"TestCase\" \n					then tebes:ActionName element takes taml:testAssertionSet/@setname from Test Case\n					(f.e. \"Test Case for UBL Invoice Italian INTERCENT-ER Schematron validation\")\n				- \"TestSuite\" \n					then tebes:ActionName element takes Test Suite name folder\n					(f.e. \"TS-005_sdi\")\n			-->\r\n			<tebes:ActionName>SDI-UBL</tebes:ActionName>\r\n			\r\n			<!-- \n				The following \"tebes:ActionDescription\" element can take different values \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then Name element takes taml:testAssertion/taml:description\n				- \"TestCase\" \n					then Name element takes taml:testAssertionSet/@setname (or user input)\n				- \"TestSuite\" then Name element takes Test Suite name folder  (or user input)\n			-->\r\n			<tebes:ActionDescription>Italian INTERCENT-ER UBL Invoice Schematron Validation.</tebes:ActionDescription>\r\n			\r\n\r\n			<!-- \n				The tebes:ActionTarget is the SUT type \n			-->\r\n			<!-- tebes:ActionTarget type=\"xml\" retrieve=\"upload\">document</tebes:ActionTarget -->			\r\n						\r\n			<!-- \n				The tebes:Test element contains the reference to test to execute, \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then tebes:Test element takes taml:testAssertion/@id from Test Assertion\n					(f.e. \"TA-001_TC-001_TS-005\")\n				- \"TestCase\" \n					then tebes:Test element takes taml:testAssertionSet/@setid from Test Case\n					(f.e. \"TC-001_TS-005\")\n				- \"TestSuite\" \n					then tebes:Test element takes Test Suite name folder prefix\n					(f.e. \"TS-005\")\n					\n				The tebes:Test/@location specifies the location of the test:\n				- an XML file absolute path, in the cases of \"TestAssertion\" or \"TestCase\" types;\n				- a folder absolute path, in the case of \"TestSuite\" type\n			-->\r\n			<tebes:Test lg=\"taml\" location=\"http://www.ubl-italia.org/TeBES/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml\" skipPrerequisites=\"false\" type=\"TestAssertion\">TA-001_TC-001_TS-005</tebes:Test>\r\n\r\n			<!-- \n				tebes:Input è un elemento che specifica l\'input da parte del SUT \n				(o il SUT stesso se l\'elemento è uno)\n				su cui sarà eseguita quest\'action.\n				Nell\'action corrente, per esempio, il SUT è la fattura UBL.\n				I seguenti attributi possono assumere i seguenti valori:\n				- type: \"document\", \"transport\", \"process\";		\n				- description: descrizione testuale\n			-->\r\n			<tebes:Input description=\"Send UBL Invoice\" fileIdRef=\"FI-2_document-xml-website-IN-1_TA-2_TP-2\" guiMessage=\"Message for Upload of Input with fileIdRef = FI-2_document-xml-website-IN-1_TA-2_TP-2\" guiReaction=\"upload\" id=\"IN-1_TA-2_TP-2\" lg=\"xml\" name=\"UBLInvoice\" sutInteraction=\"website\" type=\"document\">\r\n			</tebes:Input>		\r\n		</tebes:TestAction>\r\n		\r\n	</tebes:TestActionList>\r\n\r\n	<!-- \n		The \"tebes:Choreography\" element specifies a choreography among the defined test actions. \n		If this element is empty, the N actions MUST be executed from number 1 to N, sequentially.\n		\n		The choreography XML element is work in progress:\n		a solution to express choreography may be an ebBP-like XML syntax. \n	-->	\r\n	<tebes:Choreography/>\r\n	\r\n</tebes:TestPlan>\r\n');
/*!40000 ALTER TABLE `testplanxml` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testresult`
--

DROP TABLE IF EXISTS `testresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testresult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `globalResult` varchar(255) DEFAULT NULL,
  `line` int(11) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testresult`
--

LOCK TABLES `testresult` WRITE;
/*!40000 ALTER TABLE `testresult` DISABLE KEYS */;
/*!40000 ALTER TABLE `testresult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `eMail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK285FEB6FBC983` (`role_id`),
  KEY `FK285FEB5F12AF1` (`group_id`),
  CONSTRAINT `FK285FEB5F12AF1` FOREIGN KEY (`group_id`) REFERENCES `usergroup` (`id`),
  CONSTRAINT `FK285FEB6FBC983` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Cristiano','Novelli','cristiano.novelli@gmail.com','xcristiano',4,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usergroup`
--

DROP TABLE IF EXISTS `usergroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usergroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usergroup`
--

LOCK TABLES `usergroup` WRITE;
/*!40000 ALTER TABLE `usergroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `usergroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinput`
--

DROP TABLE IF EXISTS `userinput`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userinput` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `lg` varchar(255) DEFAULT NULL,
  `interaction` varchar(255) DEFAULT NULL,
  `isInteractionOK` bit(1) NOT NULL,
  `fileIdRef` varchar(255) DEFAULT NULL,
  `isInputSolved` bit(1) NOT NULL,
  `guiReaction` varchar(255) DEFAULT NULL,
  `guiMessage` varchar(255) DEFAULT NULL,
  `testaction_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8A76429FC1CFF2F1` (`testaction_id`),
  CONSTRAINT `FK8A76429FC1CFF2F1` FOREIGN KEY (`testaction_id`) REFERENCES `testaction` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinput`
--

LOCK TABLES `userinput` WRITE;
/*!40000 ALTER TABLE `userinput` DISABLE KEYS */;
INSERT INTO `userinput` VALUES (1,'UBLInvoice','Send UBL Invoice','document','xml','website','\0','FI-1_document-xml-website-IN-1_TA-1_TP-1','\0','upload','Message for Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1',1),(2,'UBLInvoice2','Send UBL Invoice2','document','xml','website','\0','FI-2_document-xml-website-IN-2_TA-1_TP-1','\0','upload','Message for Upload of Input with fileIdRef = FI-2_document-xml-website-IN-2_TA-1_TP-1',1),(3,'UBLInvoice','Send UBL Invoice','document','xml','website','\0','FI-1_document-xml-website-IN-1_TA-1_TP-1','\0','upload','Message for Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1',2),(4,'UBLInvoice','Send UBL Invoice','document','xml','website','\0','FI-1_document-xml-website-IN-1_TA-1_TP-2','\0','upload','Message for Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-2',3),(5,'UBLInvoice','Send UBL Invoice','document','xml','website','\0','FI-2_document-xml-website-IN-1_TA-2_TP-2','\0','upload','Message for Upload of Input with fileIdRef = FI-2_document-xml-website-IN-1_TA-2_TP-2',4);
/*!40000 ALTER TABLE `userinput` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinteraction`
--

DROP TABLE IF EXISTS `userinteraction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userinteraction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) DEFAULT NULL,
  `inputInterfaceType` varchar(255) DEFAULT NULL,
  `session_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA49D8527C9C715D1` (`session_id`),
  CONSTRAINT `FKA49D8527C9C715D1` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinteraction`
--

LOCK TABLES `userinteraction` WRITE;
/*!40000 ALTER TABLE `userinteraction` DISABLE KEYS */;
/*!40000 ALTER TABLE `userinteraction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-10-21 14:05:07
