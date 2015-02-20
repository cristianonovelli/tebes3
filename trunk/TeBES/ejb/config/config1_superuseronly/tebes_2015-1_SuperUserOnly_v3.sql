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
-- Table structure for table `actiondescription`
--

DROP TABLE IF EXISTS `actiondescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actiondescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `testaction_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK79842806C1CFF2F1` (`testaction_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actiondescription`
--

LOCK TABLES `actiondescription` WRITE;
/*!40000 ALTER TABLE `actiondescription` DISABLE KEYS */;
INSERT INTO `actiondescription` VALUES (1,'en','UBL 2.0 Invoice XML Schema Validation.',1),(2,'it','Validazione UBL con XML Schema.',1),(3,'en','Italian INTERCENT-ER UBL Invoice Schematron Validation.',2),(4,'it','Validazione Fattura UBL italiana INTERCENT-ER con Schematron.',2),(5,'en','UBL Invoice XML Schema Validation.',3),(6,'it','Validazione UBL con XML Schema.',3),(7,'en','Italian INTERCENT-ER UBL Invoice Schematron Validation.',4),(8,'it','Validazione Fattura UBL italiana INTERCENT-ER con Schematron.',4),(9,'en','Test of Server for the Global Weather Web Service.',5),(10,'en','Test of Client for the Global Weather Web Service.',6),(11,'it','Test Client per il Web Service Global Weather.',6),(12,'en','CORRECT UBL Invoice XML Schema Validation.',7),(13,'it','Validazione di una CORRETTA Fattura UBL con XML Schema.',7),(14,'en','WRONG UBL Invoice XML Schema Validation.',8),(15,'it','Validazione di una Fattura UBL SCORRETTA con XML Schema.',8),(16,'en','Check if the document is valid against the Textile\n				Purchase Order MODA-ML XML Schema.',9),(17,'it','Verifica se il documento è valido rispetto allo Schema\n				Moda-ML Ordine di Acquisto Tessuti.',9),(18,'en','Check if the document is valid against the Textile\n				Purchase Order Response MODA-ML XML Schema.',10),(19,'it','Verifica se il documento è valido rispetto allo Schema\n				Moda-ML Risposta all\'Ordine di Acquisto Tessuti.',10),(20,'en','Check if the document is valid against the Textile\n				Purchase Order against MODA-ML XML Schema.',11),(21,'it','Verifica se il documento è valido rispetto allo Schema\n				Moda-ML Ordine di Acquisto Tessuti.',11),(22,'en','Check if the document is valid against the Textile\n				Purchase Order Status against MODA-ML XML Schema.',12),(23,'it','Verifica se il documento è valido rispetto allo Schema\n				Moda-ML Stato di Avanzamento Ordine di Acquisto Tessuti.',12);
/*!40000 ALTER TABLE `actiondescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actionworkflow`
--

DROP TABLE IF EXISTS `actionworkflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actionworkflow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actionworkflow`
--

LOCK TABLES `actionworkflow` WRITE;
/*!40000 ALTER TABLE `actionworkflow` DISABLE KEYS */;
INSERT INTO `actionworkflow` VALUES (1,NULL),(2,NULL),(3,NULL),(4,NULL),(5,NULL),(6,NULL);
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
-- Table structure for table `description`
--

DROP TABLE IF EXISTS `description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `description` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `testplan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK993583FC4041EF03` (`testplan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `description`
--

LOCK TABLES `description` WRITE;
/*!40000 ALTER TABLE `description` DISABLE KEYS */;
/*!40000 ALTER TABLE `description` ENABLE KEYS */;
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
  KEY `FKB2A510E5C9C715D1` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filestore`
--

LOCK TABLES `filestore` WRITE;
/*!40000 ALTER TABLE `filestore` DISABLE KEYS */;
/*!40000 ALTER TABLE `filestore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guidescription`
--

DROP TABLE IF EXISTS `guidescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guidescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `userinput_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA3306FE1D24B93DC` (`userinput_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guidescription`
--

LOCK TABLES `guidescription` WRITE;
/*!40000 ALTER TABLE `guidescription` DISABLE KEYS */;
INSERT INTO `guidescription` VALUES (1,'en','Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.',1),(2,'it','Upload dell\'Input con fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.',1),(3,'en','Upload of Input with fileIdRef = FI-2_document-xml-website-IN-2_TA-1_TP-1.',1),(4,'it','Upload dell\'Input con fileIdRef = FI-2_document-xml-website-IN-2_TA-1_TP-1.',1),(5,'en','Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.',2),(6,'it','Upload dell\'Input con fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.',2),(7,'en','Upload of Input with fileIdRef = FI-2_document-xml-website-IN-2_TA-1_TP-1.',2),(8,'it','Upload dell\'Input con fileIdRef = FI-2_document-xml-website-IN-2_TA-1_TP-1.',2),(9,'en','Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.',3),(10,'it','Upload dell\'Input con fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.',3),(11,'en','Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-2.',4),(12,'it','Upload dell\'Input con fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-2.',4),(13,'en','Upload of Input with fileIdRef = FI-2_document-xml-website-IN-1_TA-2_TP-2.',5),(14,'it','Upload dell\'Input con fileIdRef = FI-2_document-xml-website-IN-1_TA-2_TP-2.',5),(15,'en','Insert City (i.g. Bologna) for the WS Request to the TeBES WS Global Weather.',6),(16,'it','Inserire Città (p.es. Bologna) per la Richiesta al TeBES WS Global Weather.',6),(17,'en','Insert Country (i.g. Italy) for the WS Request to the TeBES WS Global Weather.',6),(18,'it','Inserire Nazione (p.es. Italy) per la Richiesta al TeBES WS Global Weather.',6),(19,'en','Insert City (i.g. Bologna) for the WS Request to the TeBES WS Global Weather.',7),(20,'it','Inserire Città (p.es. Bologna) per la Richiesta al TeBES WS Global Weather.',7),(21,'en','Insert Country (i.g. Italy) for the WS Request to the TeBES WS Global Weather.',7),(22,'it','Inserire Nazione (p.es. Italy) per la Richiesta al TeBES WS Global Weather.',7),(23,'en','Upload of CORRECT UBL Invoice.',8),(24,'it','Upload della Fattura UBL CORRETTA.',8),(25,'en','Upload of WRONG UBL Invoice.',9),(26,'it','Upload della Fattura UBL SCORRETTA.',9),(27,'en','Upload of Moda-ML Textile Purchase\n							Order.',10),(28,'it','Upload dell\'Ordine di Acquisto Tessuti\n							Moda-ML.',10),(29,'en','Upload of Moda-ML Textile Purchase\n							Order Response.',11),(30,'it','Upload della Risposta all\'Ordine di Acquisto Tessuti\n							Moda-ML.',11),(31,'en','Upload of Moda-ML Textile Purchase\n							Order Change.',12),(32,'it','Upload della Modifica Ordine di Acquisto Tessuti\n							Moda-ML.',12),(33,'en','Upload of Moda-ML Textile Purchase\n							Order Status.',13),(34,'it','Upload dello  Stato di Avanzamento Ordine di Acquisto Tessuti\n							Moda-ML.',13);
/*!40000 ALTER TABLE `guidescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inputdescription`
--

DROP TABLE IF EXISTS `inputdescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inputdescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `userinput_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9BA49F2D24B93DC` (`userinput_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inputdescription`
--

LOCK TABLES `inputdescription` WRITE;
/*!40000 ALTER TABLE `inputdescription` DISABLE KEYS */;
INSERT INTO `inputdescription` VALUES (1,'en','Send UBL Invoice.',1),(2,'it','Invia Fattura UBL.',1),(3,'en','Send UBL Invoice.',1),(4,'it','Invia Fattura UBL.',1),(5,'en','Send UBL Invoice.',2),(6,'it','Invia Fattura UBL.',2),(7,'en','Send UBL Invoice.',2),(8,'it','Invia Fattura UBL.',2),(9,'en','Send UBL Invoice.',3),(10,'it','Invia Fattura UBL.',3),(11,'en','Send UBL Invoice.',4),(12,'it','Invia Fattura UBL.',4),(13,'en','Send UBL Invoice.',5),(14,'it','Invia Fattura UBL.',5),(15,'en','Text field 2 of 2: City.',6),(16,'it','Campo Testuale 2 di 2: Città.',6),(17,'en','Text field 1 of 2: Contry.',6),(18,'it','Campo Testuale 1 di 2: Nazione.',6),(19,'en','Text field 2 of 2: City.',7),(20,'it','Campo Testuale 2 di 2: Città.',7),(21,'en','Text field 1 of 2: Contry.',7),(22,'it','Campo Testuale 1 di 2: Nazione.',7),(23,'en','Sending of CORRECT UBL Invoice.',8),(24,'it','Invio della Fattura UBL CORRETTA.',8),(25,'en','Sending of WRONG UBL Invoice.',9),(26,'it','Invio della Fattura UBL SCORRETTA.',9),(27,'en','Sending of Moda-ML Textile Purchase\n						Order.',10),(28,'it','Invio di un Ordine di Acquisto Tessuti\n						Moda-ML.',10),(29,'en','Sending of Moda-ML Textile Purchase\n						Order Response.',11),(30,'it','Invio di una Risposta all\'Ordine di Acquisto Tessuti\n						Moda-ML.',11),(31,'en','Sending of Moda-ML Textile Purchase\n						Order Change.',12),(32,'it','Invio di una Modifica Ordine di Acquisto Tessuti\n						Moda-ML.',12),(33,'en','Sending of Moda-ML Textile Purchase\n						Order Status.',13),(34,'it','Invio di uno  Stato di Avanzamento Ordine di Acquisto Tessuti\n						Moda-ML.',13);
/*!40000 ALTER TABLE `inputdescription` ENABLE KEYS */;
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
  `sessionID` bigint(20) DEFAULT NULL,
  `datetime` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `publication` varchar(255) DEFAULT NULL,
  `logLocation` varchar(255) DEFAULT NULL,
  `fullDescription` mediumtext,
  `xml` mediumtext,
  `partialResultSuccessfully` bit(1) NOT NULL,
  `finalResultSuccessfully` bit(1) NOT NULL,
  `globalResult` varchar(255) DEFAULT NULL,
  `atomicResult` varchar(255) DEFAULT NULL,
  `request4Interaction` varchar(255) DEFAULT NULL,
  `tempResult_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC84C553424E8F41` (`tempResult_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportdescription`
--

DROP TABLE IF EXISTS `reportdescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reportdescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7567A648D92EA83` (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reportdescription`
--

LOCK TABLES `reportdescription` WRITE;
/*!40000 ALTER TABLE `reportdescription` DISABLE KEYS */;
/*!40000 ALTER TABLE `reportdescription` ENABLE KEYS */;
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
  KEY `FK1BE52AC268D63` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sut`
--

LOCK TABLES `sut` WRITE;
/*!40000 ALTER TABLE `sut` DISABLE KEYS */;
INSERT INTO `sut` VALUES (1,'DefaultSUT','Default SUT Document-WebSite for User Novelli','document',1),(2,'SystemSUT2','System SUT 2: Document - email','document',1),(3,'SystemSUT3','System SUT 3: Document - Web Service Client','document',1),(4,'SystemSUT4','System SUT 4: Document - Web Service Server','document',1),(5,'SystemSUT5','System SUT 5: Transport - email','transport',1),(6,'SystemSUT6','System SUT 6: Transport - Web Service Client','transport',1),(7,'SystemSUT7','System SUT 7: Transport - Web Service Server','transport',1),(8,'SystemSUT8','System SUT 8: Process - WebSite','process',1),(9,'SystemSUT9','System SUT 9: Process - email','process',1),(10,'SystemSUT10','System SUT 10: Process - Web Service Client','process',1),(11,'SystemSUT11','System SUT 11: Process - Web Service Server','process',1);
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
  `operation` varchar(255) DEFAULT NULL,
  `port` varchar(255) DEFAULT NULL,
  `timeout` int(11) NOT NULL,
  `sut_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK62ED4880C8782BD1` (`sut_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sutinteraction`
--

LOCK TABLES `sutinteraction` WRITE;
/*!40000 ALTER TABLE `sutinteraction` DISABLE KEYS */;
INSERT INTO `sutinteraction` VALUES (1,'website',NULL,NULL,NULL,0,1),(2,'email',NULL,NULL,NULL,0,2),(3,'webservice-client',NULL,NULL,NULL,0,3),(4,'webservice-server','http://www.webservicex.net/globalweather.asmx?WSDL',NULL,NULL,0,4),(5,'email',NULL,NULL,NULL,0,5),(6,'webservice-client',NULL,NULL,NULL,0,6),(7,'webservice-server','http://www.webservicex.net/globalweather.asmx?WSDL',NULL,NULL,0,7),(8,'website',NULL,NULL,NULL,0,8),(9,'email',NULL,NULL,NULL,0,9),(10,'webservice-client',NULL,NULL,NULL,0,10),(11,'webservice-server','http://www.webservicex.net/globalweather.asmx?WSDL',NULL,NULL,0,11);
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
  `workflow_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7DF08D486864CDF9` (`workflow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testaction`
--

LOCK TABLES `testaction` WRITE;
/*!40000 ALTER TABLE `testaction` DISABLE KEYS */;
INSERT INTO `testaction` VALUES (1,'new','\0',1,'XMLSchema-UBL-T10','TA-1_TP-1','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml','TA-001_TC-001_TS-001','',1),(2,'new','\0',2,'SDI-UBL','TA-2_TP-1','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml','TA-001_TC-001_TS-005','\0',1),(3,'new','\0',1,'XMLSchema-UBL-T10','TA-1_TP-2','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml','TA-001_TC-001_TS-001','',2),(4,'new','\0',2,'SDI-UBL','TA-2_TP-2','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml','TA-001_TC-001_TS-005','\0',2),(5,'new','\0',1,'Web Service Server','TA-1_TP-80','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/WS/TS-006_WS/TC-001_SOAPEnvelope-WS.xml','TA-001_TC-001_TS-006','',3),(6,'new','\0',1,'Web Service Client','TA-1_TP-81','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/WS/TS-006_WS/TC-001_SOAPEnvelope-WS.xml','TA-002_TC-001_TS-006','',4),(7,'new','\0',1,'Correct XMLSchema-UBL-T10','TA-1_TP-91','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml','TA-001_TC-001_TS-001','',5),(8,'new','\0',2,'Wrong XMLSchema-UBL-T10','TA-2_TP-91','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml','TA-001_TC-001_TS-001','',5),(9,'new','\0',1,'Check TEXOrder-MML','TA-1_TP-MML','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/MODAML_2013-1/TS-009_MML_BusinessDocBR/TC-001_TEXOrder-MML-BR.xml','TA-001_TC-001_TS-001','',6),(10,'new','\0',2,'Check TEXOrdResponse-MML','TA-2_TP-MML','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/MODAML_2013-1/TS-009_MML_BusinessDocBR/TC-002_TEXOrdResponse-MML-BR.xml','TA-001_TC-001_TS-001','',6),(11,'new','\0',3,'Check TEXOrder-MML','TA-3_TP-MML','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/MODAML_2013-1/TS-009_MML_BusinessDocBR/TC-003_TEXOrdChange-MML-BR.xml','TA-001_TC-001_TS-001','',6),(12,'new','\0',4,'Check TEXOrder-MML','TA-4_TP-MML','taml','TestAssertion','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/testsuites/MODAML_2013-1/TS-009_MML_BusinessDocBR/TC-004_TEXOrdStatus-MML-BR.xml','TA-001_TC-001_TS-001','',6);
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
  `creationDatetime` varchar(255) DEFAULT NULL,
  `lastUpdateDatetime` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `publication` varchar(255) DEFAULT NULL,
  `testplanxml_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `workflow_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKBBB236BBAC268D63` (`user_id`),
  KEY `FKBBB236BBE3ACFC51` (`testplanxml_id`),
  KEY `FKBBB236BB6864CDF9` (`workflow_id`),
  CONSTRAINT `FKBBB236BB6864CDF9` FOREIGN KEY (`workflow_id`) REFERENCES `actionworkflow` (`id`),
  CONSTRAINT `FKBBB236BBAC268D63` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKBBB236BBE3ACFC51` FOREIGN KEY (`testplanxml_id`) REFERENCES `testplanxml` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testplan`
--

LOCK TABLES `testplan` WRITE;
/*!40000 ALTER TABLE `testplan` DISABLE KEYS */;
INSERT INTO `testplan` VALUES (1,'TP-1','2015-02-20T14:50:33Z','2015-02-20T14:50:33Z','draft','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-1.xml','http://winter.bologna.enea.it/tebes-artifacts/users/0/testplans/TP-1.xml',1,1,1),(2,'TP-2','2015-02-20T14:50:33Z','2015-02-20T14:50:33Z','draft','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-2.xml','http://winter.bologna.enea.it/tebes-artifacts/users/0/testplans/TP-2.xml',2,1,2),(3,'TP-80','2015-02-20T14:50:33Z','2015-02-20T14:50:33Z','draft','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-80.xml','http://winter.bologna.enea.it/tebes-artifacts/users/0/testplans/TP-80.xml',3,1,3),(4,'TP-81','2015-02-20T14:50:33Z','2015-02-20T14:50:33Z','draft','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-81.xml','http://winter.bologna.enea.it/tebes-artifacts/users/0/testplans/TP-81.xml',4,1,4),(5,'TP-91','2015-02-20T14:50:33Z','2015-02-20T14:50:33Z','draft','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-91.xml','http://winter.bologna.enea.it/tebes-artifacts/users/0/testplans/TP-91.xml',5,1,5),(6,'TP-MML','2015-02-20T14:50:33Z','2015-02-20T14:50:33Z','draft','C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-MML_001.xml','http://winter.bologna.enea.it/tebes-artifacts/users/0/testplans/TP-MML_001.xml',6,1,6);
/*!40000 ALTER TABLE `testplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testplandescription`
--

DROP TABLE IF EXISTS `testplandescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testplandescription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `language` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `testplan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK11E95A614041EF03` (`testplan_id`),
  CONSTRAINT `FK11E95A614041EF03` FOREIGN KEY (`testplan_id`) REFERENCES `testplan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testplandescription`
--

LOCK TABLES `testplandescription` WRITE;
/*!40000 ALTER TABLE `testplandescription` DISABLE KEYS */;
INSERT INTO `testplandescription` VALUES (1,'en','TP-1 Test Plan defined and used for the TeBES platform development.',1),(2,'it','Test Plan TP-1 definito e utilizzato per lo sviluppo della piattaforma TeBES.',1),(3,'en','TP-2 Test Plan defined and used for the TeBES platform development.',2),(4,'it','Test Plan TP-2 definito e utilizzato per lo sviluppo della piattaforma TeBES.',2),(5,'en','TP-80 Test Plan is used to test a Web Service Server for the Global Weather Web Service case.',3),(6,'it','Il Test Plan TP-80 è usato per il test del trasporto Web Service Server per il caso Global Weather Web Service.',3),(7,'en','TP-81 Test Plan is used to test a Web Service Client for the Global Weather Web Service case.',4),(8,'it','Il Test Plan TP-81 è usato per il test del trasporto Web Service Client per il caso Global Weather Web Service.',4),(9,'en','TP-91 Test Plan is used to test a correct UBL invoice and a wrong UBL invoice against XML Schema.',5),(10,'it','Test Plan TP-91 è definito e utilizzato per testare una Fattura UBL corretta e una sbagliata con XML Schema.',5),(11,'en','TP-MML Test Plan is used to test the conformance of the business\n			documents exchanged in Moda-ML \'Purchase of fabrics\' activity.',6),(12,'it','Test Plan TP-MML è utilizzato per testare la conformità dei\n			documenti di business usati nell\'attivita\' Moda-ML \'Acquisto\n			tessuti\'.',6);
/*!40000 ALTER TABLE `testplandescription` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testplanxml`
--

LOCK TABLES `testplanxml` WRITE;
/*!40000 ALTER TABLE `testplanxml` DISABLE KEYS */;
INSERT INTO `testplanxml` VALUES (1,'C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-1.xml','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<?xml-stylesheet type=\"text/xsl\" href=\"../../../xsl/TeBES_TestPlan.xsl\" ?>\r\n\r\n<!-- \n    Document Description:		Test Plan created for the TeBES Platform\n    Author:              		Cristiano Novelli (X-Lab, Cross-TEC, ENEA)\n    Project:                    Tecnopolo\n-->\r\n<tebes:TestPlan xmlns:tebes=\"http://www.ubl-italia.org/TeBES\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" lg=\"en\">\r\n	\r\n	<tebes:TestPlanHeader>\r\n	<!-- \n		The \"datetime\" elements are related to the creation and last modify datetime of file.\n		The state element can take 2 values: \"draft\" or \"final\" \n	-->\r\n	    <tebes:Id>1</tebes:Id>\r\n	    <tebes:Name>TP-1</tebes:Name>\r\n	    <tebes:State>draft</tebes:State>\r\n	    <tebes:Description lg=\"en\">TP-1 Test Plan defined and used for the TeBES platform development.</tebes:Description>\r\n	    <tebes:Description lg=\"it\">Test Plan TP-1 definito e utilizzato per lo sviluppo della piattaforma TeBES.</tebes:Description>\r\n	    <tebes:UserId>0</tebes:UserId>\r\n	    <tebes:CreationDatetime>2013-06-27T18:43:00Z</tebes:CreationDatetime>\r\n	    <tebes:LastUpdateDatetime>2014-02-04T17:15:00Z</tebes:LastUpdateDatetime>\r\n	</tebes:TestPlanHeader>\r\n    \r\n	<tebes:TestActionList>\r\n	\r\n		<tebes:TestAction id=\"TA-1_TP-1\" number=\"1\">\r\n			\r\n			<tebes:ActionName>XMLSchema-UBL-T10</tebes:ActionName>\r\n			<tebes:ActionDescription lg=\"en\">UBL 2.0 Invoice XML Schema Validation.</tebes:ActionDescription>\r\n			<tebes:ActionDescription lg=\"it\">Validazione UBL con XML Schema.</tebes:ActionDescription>\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-001_TC-001_TS-001</tebes:Test>\r\n			\r\n			<tebes:Inputs>\r\n				<tebes:Input>\r\n					\r\n				    <tebes:Name>IN-1_TA-1_TP-1</tebes:Name>\r\n				    \r\n					<tebes:InputDescription lg=\"en\">Send UBL Invoice.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Invia Fattura UBL.</tebes:InputDescription>	\r\n					\r\n					<tebes:SUT fileIdRef=\"FI-1_document-xml-website-IN-1_TA-1_TP-1\" interaction=\"website\" lg=\"xml\" type=\"document\">		    \r\n					</tebes:SUT>\r\n					\r\n					<tebes:GUI reaction=\"upload\">\r\n						<tebes:GUIDescription lg=\"en\">Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.</tebes:GUIDescription>				\r\n						<tebes:GUIDescription lg=\"it\">Upload dell\'Input con fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.</tebes:GUIDescription>				    				    \r\n					</tebes:GUI>\r\n				</tebes:Input>\r\n				<!-- Questo secondo input ha lo stesso fileIdRef, quindi indica che richiederà lo stesso file -->\r\n				<tebes:Input>\r\n					\r\n				    <tebes:Name>IN-2_TA-1_TP-1</tebes:Name>\r\n				    \r\n					<tebes:InputDescription lg=\"en\">Send UBL Invoice.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Invia Fattura UBL.</tebes:InputDescription>	\r\n					\r\n					<tebes:SUT fileIdRef=\"FI-2_document-xml-website-IN-2_TA-1_TP-1\" interaction=\"website\" lg=\"xml\" type=\"document\">		    \r\n					</tebes:SUT>\r\n					\r\n					<tebes:GUI reaction=\"upload\">\r\n						<tebes:GUIDescription lg=\"en\">Upload of Input with fileIdRef = FI-2_document-xml-website-IN-2_TA-1_TP-1.</tebes:GUIDescription>				\r\n						<tebes:GUIDescription lg=\"it\">Upload dell\'Input con fileIdRef = FI-2_document-xml-website-IN-2_TA-1_TP-1.</tebes:GUIDescription>				    				    \r\n					</tebes:GUI>\r\n				</tebes:Input>\r\n			</tebes:Inputs>\r\n		</tebes:TestAction>\r\n	\r\n	\r\n		<!-- \n			Each tebes:TestAction MUST have a @number attribute used to enumerate every action.\n			The N actions will be execute from 1 to N, sequentially, \n			excepted different specification expressed through the tebes:TestPlan/tebes:Choreography element\n			\n			Each tebes:Test element MUST have a @type attribute used with one and only one of these values:\n				- \"TestAssertion\" \n				- \"TestCase\" \n				- \"TestSuite\" \n		-->\r\n		<tebes:TestAction id=\"TA-2_TP-1\" number=\"2\">\r\n			\r\n			<!-- \n				The following \"tebes:ActionName\" element can take different values \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then tebes:ActionName element takes taml:testAssertion/@name from Test Assertion\n					(f.e. \"SDI-UBL\")\n				- \"TestCase\" \n					then tebes:ActionName element takes taml:testAssertionSet/@setname from Test Case\n					(f.e. \"Test Case for UBL Invoice Italian INTERCENT-ER Schematron validation\")\n				- \"TestSuite\" \n					then tebes:ActionName element takes Test Suite name folder\n					(f.e. \"TS-005_sdi\")\n			-->\r\n			<tebes:ActionName>SDI-UBL</tebes:ActionName>\r\n			\r\n			<!-- \n				The following \"tebes:ActionDescription\" element can take different values \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then Name element takes taml:testAssertion/taml:description\n				- \"TestCase\" \n					then Name element takes taml:testAssertionSet/@setname (or user input)\n				- \"TestSuite\" then Name element takes Test Suite name folder  (or user input)\n			-->\r\n			<tebes:ActionDescription lg=\"en\">Italian INTERCENT-ER UBL Invoice Schematron Validation.</tebes:ActionDescription>\r\n			<tebes:ActionDescription lg=\"it\">Validazione Fattura UBL italiana INTERCENT-ER con Schematron.</tebes:ActionDescription>\r\n				\r\n			<!-- \n				The tebes:Test element contains the reference to test to execute, \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then tebes:Test element takes taml:testAssertion/@id from Test Assertion\n					(f.e. \"TA-001_TC-001_TS-005\")\n				- \"TestCase\" \n					then tebes:Test element takes taml:testAssertionSet/@setid from Test Case\n					(f.e. \"TC-001_TS-005\")\n				- \"TestSuite\" \n					then tebes:Test element takes Test Suite name folder prefix\n					(f.e. \"TS-005\")\n					\n				The tebes:Test/@location specifies the location of the test:\n				- an XML file absolute path, in the cases of \"TestAssertion\" or \"TestCase\" types;\n				- a folder absolute path, in the case of \"TestSuite\" type\n			-->\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml\" skipPrerequisites=\"false\" type=\"TestAssertion\">TA-001_TC-001_TS-005</tebes:Test>\r\n				\r\n			<!-- \n				tebes:Input è un elemento che specifica l\'input da parte del SUT \n				(o il SUT stesso se l\'elemento è uno)\n				su cui sarà eseguita quest\'action.\n				Nell\'action corrente, per esempio, il SUT è la fattura UBL.\n				I seguenti attributi possono assumere i seguenti valori:\n				- type: \"document\", \"transport\", \"process\";			\n				- description: descrizione testuale\n			-->\r\n			<tebes:Inputs>\r\n				<tebes:Input>\r\n					\r\n				    <tebes:Name>IN-1_TA-2_TP-1</tebes:Name>\r\n				    \r\n					<tebes:InputDescription lg=\"en\">Send UBL Invoice.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Invia Fattura UBL.</tebes:InputDescription>	\r\n					\r\n					<tebes:SUT fileIdRef=\"FI-1_document-xml-website-IN-1_TA-1_TP-1\" interaction=\"website\" lg=\"xml\" type=\"document\">		    \r\n					</tebes:SUT>\r\n					\r\n					<tebes:GUI reaction=\"upload\">\r\n						<tebes:GUIDescription lg=\"en\">Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.</tebes:GUIDescription>				\r\n						<tebes:GUIDescription lg=\"it\">Upload dell\'Input con fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.</tebes:GUIDescription>				    				    \r\n					</tebes:GUI>\r\n				</tebes:Input>\r\n			</tebes:Inputs>\r\n		</tebes:TestAction>\r\n		\r\n	</tebes:TestActionList>\r\n\r\n	<!-- \n		The \"tebes:Choreography\" element specifies a choreography among the defined test actions. \n		If this element is empty, the N actions MUST be executed from number 1 to N, sequentially.\n		\n		The choreography XML element is work in progress:\n		a solution to express choreography may be an ebBP-like XML syntax. \n	-->	\r\n	<tebes:Choreography/>\r\n	\r\n</tebes:TestPlan>\r\n'),(2,'C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-2.xml','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<?xml-stylesheet type=\"text/xsl\" href=\"../../../xsl/TeBES_TestPlan.xsl\" ?>\r\n\r\n<!-- \n    Document Description:		Test Plan created for the TeBES Platform \n    Author:              		Cristiano Novelli (X-Lab, Cross-TEC, ENEA)\n    Project:                    Tecnopolo\n-->\r\n<tebes:TestPlan xmlns:tebes=\"http://www.ubl-italia.org/TeBES\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" lg=\"en\">\r\n	\r\n	<tebes:TestPlanHeader>\r\n	<!-- \n		The \"datetime\" elements are related to the creation and last modify datetime of file.\n		The state element can take 2 values: \"draft\" or \"final\" \n	-->\r\n	    <tebes:Id>2</tebes:Id>\r\n	    <tebes:Name>TP-2</tebes:Name>\r\n	    <tebes:Description lg=\"en\">TP-2 Test Plan defined and used for the TeBES platform development.</tebes:Description>\r\n	    <tebes:Description lg=\"it\">Test Plan TP-2 definito e utilizzato per lo sviluppo della piattaforma TeBES.</tebes:Description>\r\n	    <tebes:UserId>0</tebes:UserId>\r\n	    <tebes:CreationDatetime>2013-06-27T18:43:00Z</tebes:CreationDatetime>\r\n	    <tebes:LastUpdateDatetime>2014-02-04T17:15:00Z</tebes:LastUpdateDatetime>\r\n	</tebes:TestPlanHeader>\r\n			\r\n	<tebes:TestActionList>\r\n	\r\n		<tebes:TestAction id=\"TA-1_TP-2\" number=\"1\">\r\n		    		\r\n			<tebes:ActionName>XMLSchema-UBL-T10</tebes:ActionName>\r\n			<tebes:ActionDescription lg=\"en\">UBL Invoice XML Schema Validation.</tebes:ActionDescription>\r\n			<tebes:ActionDescription lg=\"it\">Validazione UBL con XML Schema.</tebes:ActionDescription>\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-001_TC-001_TS-001</tebes:Test>\r\n			<tebes:Inputs>\r\n				<tebes:Input>\r\n					\r\n				    <tebes:Name>IN-1_TA-1_TP-2</tebes:Name>\r\n				    \r\n					<tebes:InputDescription lg=\"en\">Send UBL Invoice.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Invia Fattura UBL.</tebes:InputDescription>	\r\n					\r\n					<tebes:SUT fileIdRef=\"FI-1_document-xml-website-IN-1_TA-1_TP-2\" interaction=\"website\" lg=\"xml\" type=\"document\">		    \r\n					</tebes:SUT>\r\n					\r\n					<tebes:GUI reaction=\"upload\">\r\n						<tebes:GUIDescription lg=\"en\">Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-2.</tebes:GUIDescription>				\r\n						<tebes:GUIDescription lg=\"it\">Upload dell\'Input con fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-2.</tebes:GUIDescription>				    				    \r\n					</tebes:GUI>\r\n					\r\n				</tebes:Input>	\r\n			</tebes:Inputs>	\r\n		</tebes:TestAction>\r\n	\r\n	\r\n		<!-- \n			Each tebes:TestAction MUST have a @number attribute used to enumerate every action.\n			The N actions will be execute from 1 to N, sequentially, \n			excepted different specification expressed through the tebes:TestPlan/tebes:Choreography element\n			\n			Each tebes:Test element MUST have a @type attribute used with one and only one of these values:\n				- \"TestAssertion\" \n				- \"TestCase\" \n				- \"TestSuite\" \n		-->\r\n		<tebes:TestAction id=\"TA-2_TP-2\" number=\"2\">\r\n			\r\n			<!-- \n				The following \"tebes:ActionName\" element can take different values \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then tebes:ActionName element takes taml:testAssertion/@name from Test Assertion\n					(f.e. \"SDI-UBL\")\n				- \"TestCase\" \n					then tebes:ActionName element takes taml:testAssertionSet/@setname from Test Case\n					(f.e. \"Test Case for UBL Invoice Italian INTERCENT-ER Schematron validation\")\n				- \"TestSuite\" \n					then tebes:ActionName element takes Test Suite name folder\n					(f.e. \"TS-005_sdi\")\n			-->\r\n			<tebes:ActionName>SDI-UBL</tebes:ActionName>\r\n			\r\n			<!-- \n				The following \"tebes:ActionDescription\" element can take different values \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then Name element takes taml:testAssertion/taml:description\n				- \"TestCase\" \n					then Name element takes taml:testAssertionSet/@setname (or user input)\n				- \"TestSuite\" then Name element takes Test Suite name folder  (or user input)\n			-->\r\n			<tebes:ActionDescription lg=\"en\">Italian INTERCENT-ER UBL Invoice Schematron Validation.</tebes:ActionDescription>\r\n			<tebes:ActionDescription lg=\"it\">Validazione Fattura UBL italiana INTERCENT-ER con Schematron.</tebes:ActionDescription>\r\n						\r\n\r\n			<!-- \n				The tebes:ActionTarget is the SUT type \n			-->\r\n			<!-- tebes:ActionTarget type=\"xml\" retrieve=\"upload\">document</tebes:ActionTarget -->			\r\n						\r\n			<!-- \n				The tebes:Test element contains the reference to test to execute, \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then tebes:Test element takes taml:testAssertion/@id from Test Assertion\n					(f.e. \"TA-001_TC-001_TS-005\")\n				- \"TestCase\" \n					then tebes:Test element takes taml:testAssertionSet/@setid from Test Case\n					(f.e. \"TC-001_TS-005\")\n				- \"TestSuite\" \n					then tebes:Test element takes Test Suite name folder prefix\n					(f.e. \"TS-005\")\n					\n				The tebes:Test/@location specifies the location of the test:\n				- an XML file absolute path, in the cases of \"TestAssertion\" or \"TestCase\" types;\n				- a folder absolute path, in the case of \"TestSuite\" type\n			-->\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml\" skipPrerequisites=\"false\" type=\"TestAssertion\">TA-001_TC-001_TS-005</tebes:Test>\r\n\r\n			<!-- \n				tebes:Input è un elemento che specifica l\'input da parte del SUT \n				(o il SUT stesso se l\'elemento è uno)\n				su cui sarà eseguita quest\'action.\n				Nell\'action corrente, per esempio, il SUT è la fattura UBL.\n				I seguenti attributi possono assumere i seguenti valori:\n				- type: \"document\", \"transport\", \"process\";		\n				- description: descrizione testuale\n			-->\r\n			<tebes:Inputs>\r\n				<tebes:Input>\r\n					\r\n				    <tebes:Name>IN-1_TA-2_TP-1</tebes:Name>\r\n				    \r\n					<tebes:InputDescription lg=\"en\">Send UBL Invoice.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Invia Fattura UBL.</tebes:InputDescription>	\r\n					\r\n					<tebes:SUT fileIdRef=\"FI-2_document-xml-website-IN-1_TA-2_TP-2\" interaction=\"website\" lg=\"xml\" type=\"document\">		    \r\n					</tebes:SUT>\r\n					\r\n					<tebes:GUI reaction=\"upload\">\r\n						<tebes:GUIDescription lg=\"en\">Upload of Input with fileIdRef = FI-2_document-xml-website-IN-1_TA-2_TP-2.</tebes:GUIDescription>				\r\n						<tebes:GUIDescription lg=\"it\">Upload dell\'Input con fileIdRef = FI-2_document-xml-website-IN-1_TA-2_TP-2.</tebes:GUIDescription>				    				    \r\n					</tebes:GUI>\r\n					\r\n				</tebes:Input>	\r\n			</tebes:Inputs>\r\n		</tebes:TestAction>\r\n		\r\n	</tebes:TestActionList>\r\n\r\n	<!-- \n		The \"tebes:Choreography\" element specifies a choreography among the defined test actions. \n		If this element is empty, the N actions MUST be executed from number 1 to N, sequentially.\n		\n		The choreography XML element is work in progress:\n		a solution to express choreography may be an ebBP-like XML syntax. \n	-->	\r\n	<tebes:Choreography/>\r\n	\r\n</tebes:TestPlan>\r\n'),(3,'C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-80.xml','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<?xml-stylesheet type=\"text/xsl\" href=\"../../../xsl/TeBES_TestPlan.xsl\" ?>\r\n\r\n<!-- \n    Document Description:		Test Plan created for the TeBES Platform \n    Author:              		Cristiano Novelli (X-Lab, Cross-TEC, ENEA)\n    Project:                    Tecnopolo\n-->\r\n<tebes:TestPlan xmlns:tebes=\"http://www.ubl-italia.org/tebes\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" lg=\"en\">\r\n	\r\n	<tebes:TestPlanHeader>\r\n	    <tebes:Id>80</tebes:Id>\r\n	    <tebes:Name>TP-80</tebes:Name>\r\n	    <tebes:State>draft</tebes:State>\r\n	    <tebes:Description lg=\"en\">TP-80 Test Plan is used to test a Web Service Server for the Global Weather Web Service case.</tebes:Description>\r\n	    <tebes:Description lg=\"it\">Il Test Plan TP-80 è usato per il test del trasporto Web Service Server per il caso Global Weather Web Service.</tebes:Description>\r\n	    <tebes:UserId>0</tebes:UserId>\r\n	    <tebes:CreationDatetime>2014-08-21T16:43:00Z</tebes:CreationDatetime>\r\n	    <tebes:LastUpdateDatetime>2014-08-21T16:43:00Z</tebes:LastUpdateDatetime>\r\n	</tebes:TestPlanHeader>\r\n    \r\n	<tebes:TestActionList>\r\n	\r\n		<tebes:TestAction id=\"TA-1_TP-80\" number=\"1\">\r\n			\r\n			<tebes:ActionName>Web Service Server</tebes:ActionName>\r\n			<tebes:ActionDescription lg=\"en\">Test of Server for the Global Weather Web Service.</tebes:ActionDescription>\r\n				\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/WS/TS-006_WS/TC-001_SOAPEnvelope-WS.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-001_TC-001_TS-006</tebes:Test>\r\n			\r\n			<tebes:Inputs>\r\n			    \r\n			    <tebes:Input>		\r\n				    <tebes:Name>CityName</tebes:Name>\r\n				    \r\n					<tebes:InputDescription lg=\"en\">Text field 2 of 2: City.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Campo Testuale 2 di 2: Città.</tebes:InputDescription>	\r\n					\r\n					<tebes:SUT fileIdRef=\"TI-1_transport-soap-webservice-server-IN-1_TA-1_TP-80\" interaction=\"webservice-server\" lg=\"soap\" type=\"transport\">		    \r\n					</tebes:SUT>\r\n					\r\n					<tebes:GUI reaction=\"text\">\r\n						<tebes:GUIDescription lg=\"en\">Insert City (i.g. Bologna) for the WS Request to the TeBES WS Global Weather.</tebes:GUIDescription>				\r\n						<tebes:GUIDescription lg=\"it\">Inserire Città (p.es. Bologna) per la Richiesta al TeBES WS Global Weather.</tebes:GUIDescription>				    				    \r\n					</tebes:GUI>\r\n				</tebes:Input>\r\n				\r\n				<tebes:Input>		\r\n				    <tebes:Name>CountryName</tebes:Name>\r\n				    \r\n					<tebes:InputDescription lg=\"en\">Text field 1 of 2: Contry.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Campo Testuale 1 di 2: Nazione.</tebes:InputDescription>	\r\n					\r\n					<tebes:SUT fileIdRef=\"TI-2_transport-soap-webservice-server-IN-2_TA-1_TP-80\" interaction=\"webservice-server\" lg=\"soap\" type=\"transport\">		    \r\n					</tebes:SUT>\r\n					\r\n					<tebes:GUI reaction=\"text\">\r\n						<tebes:GUIDescription lg=\"en\">Insert Country (i.g. Italy) for the WS Request to the TeBES WS Global Weather.</tebes:GUIDescription>				\r\n						<tebes:GUIDescription lg=\"it\">Inserire Nazione (p.es. Italy) per la Richiesta al TeBES WS Global Weather.</tebes:GUIDescription>				    				    \r\n					</tebes:GUI>\r\n				</tebes:Input>\r\n				\r\n\r\n				\r\n			</tebes:Inputs>			\r\n\r\n		</tebes:TestAction>\r\n		\r\n	</tebes:TestActionList>\r\n\r\n	<!-- \n		The \"tebes:Choreography\" element specifies a choreography among the defined test actions. \n		If this element is empty, the N actions MUST be executed from number 1 to N, sequentially.\n		\n		The choreography XML element is work in progress:\n		a solution to express choreography may be an ebBP-like XML syntax. \n	-->	\r\n	<tebes:Choreography/>\r\n	\r\n</tebes:TestPlan>\r\n'),(4,'C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-81.xml','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<?xml-stylesheet type=\"text/xsl\" href=\"../../../xsl/TeBES_TestPlan.xsl\" ?>\r\n\r\n<!-- \n    Document Description:		Test Plan created for the TeBES Platform \n    Author:              		Cristiano Novelli (X-Lab, Cross-TEC, ENEA)\n    Project:                    Tecnopolo\n-->\r\n<tebes:TestPlan xmlns:tebes=\"http://www.ubl-italia.org/tebes\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" lg=\"en\">\r\n	\r\n	<tebes:TestPlanHeader>\r\n	    <tebes:Id>81</tebes:Id>\r\n	    <tebes:Name>TP-81</tebes:Name>\r\n	    <tebes:State>draft</tebes:State>\r\n	    <tebes:Description lg=\"en\">TP-81 Test Plan is used to test a Web Service Client for the Global Weather Web Service case.</tebes:Description>\r\n	    <tebes:Description lg=\"it\">Il Test Plan TP-81 è usato per il test del trasporto Web Service Client per il caso Global Weather Web Service.</tebes:Description>\r\n	    <tebes:UserId>0</tebes:UserId>\r\n	    <tebes:CreationDatetime>2014-08-21T16:43:00Z</tebes:CreationDatetime>\r\n	    <tebes:LastUpdateDatetime>2014-08-21T16:43:00Z</tebes:LastUpdateDatetime>\r\n	</tebes:TestPlanHeader>\r\n    \r\n	<tebes:TestActionList>\r\n	\r\n		<tebes:TestAction id=\"TA-1_TP-81\" number=\"1\">\r\n			\r\n			<tebes:ActionName>Web Service Client</tebes:ActionName>\r\n			<tebes:ActionDescription lg=\"en\">Test of Client for the Global Weather Web Service.</tebes:ActionDescription>\r\n			<tebes:ActionDescription lg=\"it\">Test Client per il Web Service Global Weather.</tebes:ActionDescription>\r\n			\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/WS/TS-006_WS/TC-001_SOAPEnvelope-WS.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-002_TC-001_TS-006</tebes:Test>		\r\n\r\n		</tebes:TestAction>\r\n		\r\n	</tebes:TestActionList>\r\n\r\n	<!-- \n		The \"tebes:Choreography\" element specifies a choreography among the defined test actions. \n		If this element is empty, the N actions MUST be executed from number 1 to N, sequentially.\n		\n		The choreography XML element is work in progress:\n		a solution to express choreography may be an ebBP-like XML syntax. \n	-->	\r\n	<tebes:Choreography/>\r\n	\r\n</tebes:TestPlan>\r\n'),(5,'C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-91.xml','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<?xml-stylesheet type=\"text/xsl\" href=\"../../../xsl/TeBES_TestPlan.xsl\" ?>\r\n\r\n<!-- \n    Document Description:		Test Plan created for the TeBES Platform \n    Author:              		Cristiano Novelli (X-Lab, Cross-TEC, ENEA)\n    Project:                    Tecnopolo\n-->\r\n<tebes:TestPlan xmlns:tebes=\"http://www.ubl-italia.org/TeBES\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" lg=\"en\">\r\n	\r\n	<tebes:TestPlanHeader>\r\n	<!-- \n		The \"datetime\" elements are related to the creation and last modify datetime of file.\n		The state element can take 2 values: \"draft\" or \"final\" \n	-->\r\n	    <tebes:Id>91</tebes:Id>\r\n	    <tebes:Name>TP-91</tebes:Name>\r\n	    <tebes:State>draft</tebes:State>\r\n	    <tebes:Description lg=\"en\">TP-91 Test Plan is used to test a correct UBL invoice and a wrong UBL invoice against XML Schema.</tebes:Description>\r\n	    <tebes:Description lg=\"it\">Test Plan TP-91 è definito e utilizzato per testare una Fattura UBL corretta e una sbagliata con XML Schema.</tebes:Description>\r\n	    <tebes:UserId>0</tebes:UserId>\r\n	    <tebes:CreationDatetime>2013-06-27T18:43:00Z</tebes:CreationDatetime>\r\n	    <tebes:LastUpdateDatetime>2014-02-04T17:15:00Z</tebes:LastUpdateDatetime>\r\n	</tebes:TestPlanHeader>\r\n    \r\n	<tebes:TestActionList>\r\n	\r\n		<tebes:TestAction id=\"TA-1_TP-91\" number=\"1\">\r\n			\r\n			<tebes:ActionName>Correct XMLSchema-UBL-T10</tebes:ActionName>\r\n			<tebes:ActionDescription lg=\"en\">CORRECT UBL Invoice XML Schema Validation.</tebes:ActionDescription>\r\n			<tebes:ActionDescription lg=\"it\">Validazione di una CORRETTA Fattura UBL con XML Schema.</tebes:ActionDescription>\r\n				\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-001_TC-001_TS-001</tebes:Test>\r\n			\r\n			<tebes:Inputs>\r\n				<tebes:Input>		\r\n				    <tebes:Name>IN-1_TA-1_TP-91</tebes:Name>\r\n				    \r\n					<tebes:InputDescription lg=\"en\">Sending of CORRECT UBL Invoice.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Invio della Fattura UBL CORRETTA.</tebes:InputDescription>	\r\n					\r\n					<tebes:SUT fileIdRef=\"FI-1_document-xml-website-IN-1_TA-1_TP-91\" interaction=\"website\" lg=\"xml\" type=\"document\">		    \r\n					</tebes:SUT>\r\n					\r\n					<tebes:GUI reaction=\"upload\">\r\n						<tebes:GUIDescription lg=\"en\">Upload of CORRECT UBL Invoice.</tebes:GUIDescription>				\r\n						<tebes:GUIDescription lg=\"it\">Upload della Fattura UBL CORRETTA.</tebes:GUIDescription>				    				    \r\n					</tebes:GUI>\r\n				</tebes:Input>\r\n			</tebes:Inputs>			\r\n\r\n		</tebes:TestAction>\r\n	\r\n		<tebes:TestAction id=\"TA-2_TP-91\" number=\"2\">\r\n			\r\n			<tebes:ActionName>Wrong XMLSchema-UBL-T10</tebes:ActionName>\r\n			<tebes:ActionDescription lg=\"en\">WRONG UBL Invoice XML Schema Validation.</tebes:ActionDescription>\r\n			<tebes:ActionDescription lg=\"it\">Validazione di una Fattura UBL SCORRETTA con XML Schema.</tebes:ActionDescription>\r\n\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-001_TC-001_TS-001</tebes:Test>\r\n			\r\n			<tebes:Inputs>			\r\n				<tebes:Input>		\r\n				    <tebes:Name>IN-1_TA-2_TP-91</tebes:Name>\r\n				    \r\n					<tebes:InputDescription lg=\"en\">Sending of WRONG UBL Invoice.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Invio della Fattura UBL SCORRETTA.</tebes:InputDescription>	\r\n					\r\n					<tebes:SUT fileIdRef=\"FI-1_document-xml-website-IN-1_TA-2_TP-91\" interaction=\"website\" lg=\"xml\" type=\"document\">		    \r\n					</tebes:SUT>\r\n					\r\n					<tebes:GUI reaction=\"upload\">\r\n						<tebes:GUIDescription lg=\"en\">Upload of WRONG UBL Invoice.</tebes:GUIDescription>				\r\n						<tebes:GUIDescription lg=\"it\">Upload della Fattura UBL SCORRETTA.</tebes:GUIDescription>				    				    \r\n					</tebes:GUI>\r\n				</tebes:Input>\r\n			</tebes:Inputs>\r\n			\r\n		</tebes:TestAction>\r\n		\r\n	</tebes:TestActionList>\r\n\r\n	<!-- \n		The \"tebes:Choreography\" element specifies a choreography among the defined test actions. \n		If this element is empty, the N actions MUST be executed from number 1 to N, sequentially.\n		\n		The choreography XML element is work in progress:\n		a solution to express choreography may be an ebBP-like XML syntax. \n	-->	\r\n	<tebes:Choreography/>\r\n	\r\n</tebes:TestPlan>\r\n'),(6,'C:/Java/jboss-4.2.3.GA/server/default/TeBES_Artifacts/users/0/testplans/TP-MML_001.xml','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<?xml-stylesheet type=\"text/xsl\" href=\"../../../xsl/TeBES_TestPlan.xsl\" ?>\r\n\r\n<!-- \n    Document Description:		Test Plan created for the TeBES Platform \n    Author:              		Arianna Brutti (X-Lab, Cross-TEC, ENEA)\n    Project:                    Tecnopolo\n-->\r\n<tebes:TestPlan xmlns:tebes=\"http://www.ubl-italia.org/TeBES\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" lg=\"en\">\r\n\r\n	<tebes:TestPlanHeader>\r\n		<!-- \n		The \"datetime\" elements are related to the creation and last modify datetime of file.\n		The state element can take 2 values: \"draft\" or \"final\" \n	-->\r\n		<tebes:Id>MML</tebes:Id>\r\n		<tebes:Name>TP-MML</tebes:Name>\r\n		<tebes:State>draft</tebes:State>\r\n		<tebes:Description lg=\"en\">TP-MML Test Plan is used to test the conformance of the business\r\n			documents exchanged in Moda-ML \'Purchase of fabrics\' activity.</tebes:Description>\r\n		<tebes:Description lg=\"it\">Test Plan TP-MML è utilizzato per testare la conformità dei\r\n			documenti di business usati nell\'attivita\' Moda-ML \'Acquisto\r\n			tessuti\'.</tebes:Description>\r\n		<tebes:UserId>0</tebes:UserId>\r\n	    <tebes:CreationDatetime>2014-08-21T16:43:00Z</tebes:CreationDatetime>\r\n	    <tebes:LastUpdateDatetime>2014-08-21T16:43:00Z</tebes:LastUpdateDatetime>\r\n	</tebes:TestPlanHeader>\r\n\r\n	<tebes:TestActionList>\r\n\r\n		<tebes:TestAction id=\"TA-1_TP-MML\" number=\"1\">\r\n\r\n			<tebes:ActionName>Check TEXOrder-MML</tebes:ActionName>\r\n			<tebes:ActionDescription lg=\"en\">Check if the document is valid against the Textile\r\n				Purchase Order MODA-ML XML Schema.</tebes:ActionDescription>\r\n			<tebes:ActionDescription lg=\"it\">Verifica se il documento è valido rispetto allo Schema\r\n				Moda-ML Ordine di Acquisto Tessuti.</tebes:ActionDescription>\r\n\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/MODAML_2013-1/TS-009_MML_BusinessDocBR/TC-001_TEXOrder-MML-BR.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-001_TC-001_TS-001</tebes:Test>\r\n\r\n			<tebes:Inputs>\r\n				<tebes:Input>\r\n					<tebes:Name>IN-1_TA-1_TP-MML</tebes:Name>\r\n\r\n					<tebes:InputDescription lg=\"en\">Sending of Moda-ML Textile Purchase\r\n						Order.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Invio di un Ordine di Acquisto Tessuti\r\n						Moda-ML.</tebes:InputDescription>\r\n\r\n					<tebes:SUT fileIdRef=\"FI-1_document-xml-website-IN-1_TA-1_TP-MML\" interaction=\"website\" lg=\"xml\" type=\"document\"> </tebes:SUT>\r\n\r\n					<tebes:GUI reaction=\"upload\">\r\n						<tebes:GUIDescription lg=\"en\">Upload of Moda-ML Textile Purchase\r\n							Order.</tebes:GUIDescription>\r\n						<tebes:GUIDescription lg=\"it\">Upload dell\'Ordine di Acquisto Tessuti\r\n							Moda-ML.</tebes:GUIDescription>\r\n					</tebes:GUI>\r\n				</tebes:Input>\r\n			</tebes:Inputs>\r\n\r\n		</tebes:TestAction>\r\n\r\n\r\n		<tebes:TestAction id=\"TA-2_TP-MML\" number=\"2\">\r\n\r\n			<tebes:ActionName>Check TEXOrdResponse-MML</tebes:ActionName>\r\n			<tebes:ActionDescription lg=\"en\">Check if the document is valid against the Textile\r\n				Purchase Order Response MODA-ML XML Schema.</tebes:ActionDescription>\r\n			<tebes:ActionDescription lg=\"it\">Verifica se il documento è valido rispetto allo Schema\r\n				Moda-ML Risposta all\'Ordine di Acquisto Tessuti.</tebes:ActionDescription>\r\n\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/MODAML_2013-1/TS-009_MML_BusinessDocBR/TC-002_TEXOrdResponse-MML-BR.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-001_TC-001_TS-001</tebes:Test>\r\n\r\n			<tebes:Inputs>\r\n				<tebes:Input>\r\n					<tebes:Name>IN-2_TA-2_TP-MML</tebes:Name>\r\n\r\n					<tebes:InputDescription lg=\"en\">Sending of Moda-ML Textile Purchase\r\n						Order Response.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Invio di una Risposta all\'Ordine di Acquisto Tessuti\r\n						Moda-ML.</tebes:InputDescription>\r\n\r\n					<tebes:SUT fileIdRef=\"FI-1_document-xml-website-IN-2_TA-2_TP-MML\" interaction=\"website\" lg=\"xml\" type=\"document\"> </tebes:SUT>\r\n\r\n					<tebes:GUI reaction=\"upload\">\r\n						<tebes:GUIDescription lg=\"en\">Upload of Moda-ML Textile Purchase\r\n							Order Response.</tebes:GUIDescription>\r\n						<tebes:GUIDescription lg=\"it\">Upload della Risposta all\'Ordine di Acquisto Tessuti\r\n							Moda-ML.</tebes:GUIDescription>\r\n					</tebes:GUI>\r\n				</tebes:Input>\r\n			</tebes:Inputs>\r\n\r\n		</tebes:TestAction>\r\n\r\n\r\n		<tebes:TestAction id=\"TA-3_TP-MML\" number=\"3\">\r\n\r\n			<tebes:ActionName>Check TEXOrder-MML</tebes:ActionName>\r\n			<tebes:ActionDescription lg=\"en\">Check if the document is valid against the Textile\r\n				Purchase Order against MODA-ML XML Schema.</tebes:ActionDescription>\r\n			<tebes:ActionDescription lg=\"it\">Verifica se il documento è valido rispetto allo Schema\r\n				Moda-ML Ordine di Acquisto Tessuti.</tebes:ActionDescription>\r\n\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/MODAML_2013-1/TS-009_MML_BusinessDocBR/TC-003_TEXOrdChange-MML-BR.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-001_TC-001_TS-001</tebes:Test>\r\n\r\n			<tebes:Inputs>\r\n				<tebes:Input>\r\n					<tebes:Name>IN-3_TA-3_TP-MML</tebes:Name>\r\n\r\n					<tebes:InputDescription lg=\"en\">Sending of Moda-ML Textile Purchase\r\n						Order Change.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Invio di una Modifica Ordine di Acquisto Tessuti\r\n						Moda-ML.</tebes:InputDescription>\r\n\r\n					<tebes:SUT fileIdRef=\"FI-1_document-xml-website-IN-3_TA-3_TP-MML\" interaction=\"website\" lg=\"xml\" type=\"document\"> </tebes:SUT>\r\n\r\n					<tebes:GUI reaction=\"upload\">\r\n						<tebes:GUIDescription lg=\"en\">Upload of Moda-ML Textile Purchase\r\n							Order Change.</tebes:GUIDescription>\r\n						<tebes:GUIDescription lg=\"it\">Upload della Modifica Ordine di Acquisto Tessuti\r\n							Moda-ML.</tebes:GUIDescription>\r\n					</tebes:GUI>\r\n				</tebes:Input>\r\n			</tebes:Inputs>\r\n\r\n		</tebes:TestAction>\r\n\r\n		<tebes:TestAction id=\"TA-4_TP-MML\" number=\"4\">\r\n\r\n			<tebes:ActionName>Check TEXOrder-MML</tebes:ActionName>\r\n			<tebes:ActionDescription lg=\"en\">Check if the document is valid against the Textile\r\n				Purchase Order Status against MODA-ML XML Schema.</tebes:ActionDescription>\r\n			<tebes:ActionDescription lg=\"it\">Verifica se il documento è valido rispetto allo Schema\r\n				Moda-ML Stato di Avanzamento Ordine di Acquisto Tessuti.</tebes:ActionDescription>\r\n\r\n			<tebes:Test lg=\"taml\" location=\"http://winter.bologna.enea.it/tebes-artifacts/testsuites/MODAML_2013-1/TS-009_MML_BusinessDocBR/TC-004_TEXOrdStatus-MML-BR.xml\" skipPrerequisites=\"true\" type=\"TestAssertion\">TA-001_TC-001_TS-001</tebes:Test>\r\n\r\n			<tebes:Inputs>\r\n				<tebes:Input>\r\n					<tebes:Name>IN-4_TA-4_TP-MML</tebes:Name>\r\n\r\n					<tebes:InputDescription lg=\"en\">Sending of Moda-ML Textile Purchase\r\n						Order Status.</tebes:InputDescription>\r\n					<tebes:InputDescription lg=\"it\">Invio di uno  Stato di Avanzamento Ordine di Acquisto Tessuti\r\n						Moda-ML.</tebes:InputDescription>\r\n\r\n					<tebes:SUT fileIdRef=\"FI-1_document-xml-website-IN-4_TA-4_TP-MML\" interaction=\"website\" lg=\"xml\" type=\"document\"> </tebes:SUT>\r\n\r\n					<tebes:GUI reaction=\"upload\">\r\n						<tebes:GUIDescription lg=\"en\">Upload of Moda-ML Textile Purchase\r\n							Order Status.</tebes:GUIDescription>\r\n						<tebes:GUIDescription lg=\"it\">Upload dello  Stato di Avanzamento Ordine di Acquisto Tessuti\r\n							Moda-ML.</tebes:GUIDescription>\r\n					</tebes:GUI>\r\n				</tebes:Input>\r\n			</tebes:Inputs>\r\n\r\n		</tebes:TestAction>\r\n\r\n	</tebes:TestActionList>\r\n\r\n	<!-- \n		The \"tebes:Choreography\" element specifies a choreography among the defined test actions. \n		If this element is empty, the N actions MUST be executed from number 1 to N, sequentially.\n		\n		The choreography XML element is work in progress:\n		a solution to express choreography may be an ebBP-like XML syntax. \n	-->\r\n	<tebes:Choreography/>\r\n\r\n</tebes:TestPlan>\r\n');
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
  `link` varchar(255) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9B0ED72FD92EA83` (`report_id`),
  CONSTRAINT `FK9B0ED72FD92EA83` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`)
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
-- Table structure for table `testsession`
--

DROP TABLE IF EXISTS `testsession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testsession` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` varchar(255) DEFAULT NULL,
  `localization` varchar(255) DEFAULT NULL,
  `creationDateTime` varchar(255) DEFAULT NULL,
  `lastUpdateDateTime` varchar(255) DEFAULT NULL,
  `messageStore` text,
  `actionMark` int(11) NOT NULL,
  `testPlan_id` bigint(20) DEFAULT NULL,
  `sut_id` bigint(20) DEFAULT NULL,
  `report_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKFBB155C4AC268D63` (`user_id`),
  KEY `FKFBB155C4D92EA83` (`report_id`),
  KEY `FKFBB155C44041EF03` (`testPlan_id`),
  KEY `FKFBB155C4C8782BD1` (`sut_id`),
  CONSTRAINT `FKFBB155C4C8782BD1` FOREIGN KEY (`sut_id`) REFERENCES `sut` (`id`),
  CONSTRAINT `FKFBB155C44041EF03` FOREIGN KEY (`testPlan_id`) REFERENCES `testplan` (`id`),
  CONSTRAINT `FKFBB155C4AC268D63` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKFBB155C4D92EA83` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testsession`
--

LOCK TABLES `testsession` WRITE;
/*!40000 ALTER TABLE `testsession` DISABLE KEYS */;
/*!40000 ALTER TABLE `testsession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `textstore`
--

DROP TABLE IF EXISTS `textstore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `textstore` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `refId` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `session_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC341E7D4C9C715D1` (`session_id`),
  CONSTRAINT `FKC341E7D4C9C715D1` FOREIGN KEY (`session_id`) REFERENCES `testsession` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `textstore`
--

LOCK TABLES `textstore` WRITE;
/*!40000 ALTER TABLE `textstore` DISABLE KEYS */;
/*!40000 ALTER TABLE `textstore` ENABLE KEYS */;
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
  `group_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK36EBCB6FBC983` (`role_id`),
  KEY `FK36EBCB5F12AF1` (`group_id`),
  CONSTRAINT `FK36EBCB5F12AF1` FOREIGN KEY (`group_id`) REFERENCES `usergroup` (`id`),
  CONSTRAINT `FK36EBCB6FBC983` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Cristiano','Novelli','cristiano.novelli@gmail.com','xcristiano',NULL,4);
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
  KEY `FK14C712DFC1CFF2F1` (`testaction_id`),
  CONSTRAINT `FK14C712DFC1CFF2F1` FOREIGN KEY (`testaction_id`) REFERENCES `testaction` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinput`
--

LOCK TABLES `userinput` WRITE;
/*!40000 ALTER TABLE `userinput` DISABLE KEYS */;
INSERT INTO `userinput` VALUES (1,'IN-1_TA-1_TP-1','Send UBL Invoice.','document','xml','website','\0','FI-1_document-xml-website-IN-1_TA-1_TP-1','\0','upload','Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.',1),(2,'IN-2_TA-1_TP-1','Send UBL Invoice.','document','xml','website','\0','FI-2_document-xml-website-IN-2_TA-1_TP-1','\0','upload','Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.',1),(3,'IN-1_TA-2_TP-1','Send UBL Invoice.','document','xml','website','\0','FI-1_document-xml-website-IN-1_TA-1_TP-1','\0','upload','Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-1.',2),(4,'IN-1_TA-1_TP-2','Send UBL Invoice.','document','xml','website','\0','FI-1_document-xml-website-IN-1_TA-1_TP-2','\0','upload','Upload of Input with fileIdRef = FI-1_document-xml-website-IN-1_TA-1_TP-2.',3),(5,'IN-1_TA-2_TP-1','Send UBL Invoice.','document','xml','website','\0','FI-2_document-xml-website-IN-1_TA-2_TP-2','\0','upload','Upload of Input with fileIdRef = FI-2_document-xml-website-IN-1_TA-2_TP-2.',4),(6,'CityName','Text field 2 of 2: City.','transport','soap','webservice-server','\0','TI-1_transport-soap-webservice-server-IN-1_TA-1_TP-80','\0','text','Insert City (i.g. Bologna) for the WS Request to the TeBES WS Global Weather.',5),(7,'CountryName','Text field 2 of 2: City.','transport','soap','webservice-server','\0','TI-2_transport-soap-webservice-server-IN-2_TA-1_TP-80','\0','text','Insert City (i.g. Bologna) for the WS Request to the TeBES WS Global Weather.',5),(8,'IN-1_TA-1_TP-91','Sending of CORRECT UBL Invoice.','document','xml','website','\0','FI-1_document-xml-website-IN-1_TA-1_TP-91','\0','upload','Upload of CORRECT UBL Invoice.',7),(9,'IN-1_TA-2_TP-91','Sending of WRONG UBL Invoice.','document','xml','website','\0','FI-1_document-xml-website-IN-1_TA-2_TP-91','\0','upload','Upload of WRONG UBL Invoice.',8),(10,'IN-1_TA-1_TP-MML','Sending of Moda-ML Textile Purchase\n						Order.','document','xml','website','\0','FI-1_document-xml-website-IN-1_TA-1_TP-MML','\0','upload','Upload of Moda-ML Textile Purchase\n							Order.',9),(11,'IN-2_TA-2_TP-MML','Sending of Moda-ML Textile Purchase\n						Order Response.','document','xml','website','\0','FI-1_document-xml-website-IN-2_TA-2_TP-MML','\0','upload','Upload of Moda-ML Textile Purchase\n							Order Response.',10),(12,'IN-3_TA-3_TP-MML','Sending of Moda-ML Textile Purchase\n						Order Change.','document','xml','website','\0','FI-1_document-xml-website-IN-3_TA-3_TP-MML','\0','upload','Upload of Moda-ML Textile Purchase\n							Order Change.',11),(13,'IN-4_TA-4_TP-MML','Sending of Moda-ML Textile Purchase\n						Order Status.','document','xml','website','\0','FI-1_document-xml-website-IN-4_TA-4_TP-MML','\0','upload','Upload of Moda-ML Textile Purchase\n							Order Status.',12);
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
  KEY `FK7CA12567C9C715D1` (`session_id`),
  CONSTRAINT `FK7CA12567C9C715D1` FOREIGN KEY (`session_id`) REFERENCES `testsession` (`id`)
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

-- Dump completed on 2015-02-20 14:54:35
