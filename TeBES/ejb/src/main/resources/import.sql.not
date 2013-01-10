-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: tebes
-- ------------------------------------------------------
-- Server version	5.5.29

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
-- Table structure for table `action`
--

DROP TABLE IF EXISTS `action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `actionNumber` int(11) NOT NULL,
  `actionName` varchar(255) DEFAULT NULL,
  `testLanguage` varchar(255) DEFAULT NULL,
  `testType` varchar(255) DEFAULT NULL,
  `testLocation` varchar(255) DEFAULT NULL,
  `testValue` varchar(255) DEFAULT NULL,
  `jumpTurnedON` bit(1) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `workflow_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK74946A566864CDF9` (`workflow_id`),
  CONSTRAINT `FK74946A566864CDF9` FOREIGN KEY (`workflow_id`) REFERENCES `actionworkflow` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action`
--

LOCK TABLES `action` WRITE;
/*!40000 ALTER TABLE `action` DISABLE KEYS */;
INSERT INTO `action` VALUES (1,1,'XMLSchema-UBL-T10','taml','TestAssertion','C:/Java/workspace-juno/TeBES/war/src/main/webapp/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml','TA-001_TC-001_TS-001','','UBL Invoice XML Schema Validation.',1),(2,2,'SDI-UBL','taml','TestAssertion','C:/Java/workspace-juno/TeBES/war/src/main/webapp/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml','TA-001_TC-001_TS-005','\0','Italian INTERCENT-ER UBL Invoice Schematron Validation.',1);
/*!40000 ALTER TABLE `action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actionworkflow`
--

DROP TABLE IF EXISTS `actionworkflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actionworkflow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `commnet` varchar(255) DEFAULT NULL,
  `testPlan_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKCB7DF0354041EF03` (`testPlan_id`),
  CONSTRAINT `FKCB7DF0354041EF03` FOREIGN KEY (`testPlan_id`) REFERENCES `testplan` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actionworkflow`
--

LOCK TABLES `actionworkflow` WRITE;
/*!40000 ALTER TABLE `actionworkflow` DISABLE KEYS */;
INSERT INTO `actionworkflow` VALUES (1,'2 round of update or more',1);
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choreography`
--

LOCK TABLES `choreography` WRITE;
/*!40000 ALTER TABLE `choreography` DISABLE KEYS */;
/*!40000 ALTER TABLE `choreography` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'standard_user','Standard User Role: he can execute a test plan but he can\'t create/edit it',1),(2,'advanced_user','Advanced User Role: he can create/edit/execute a test plan',2),(3,'admin_user','System Administrator Role: he is an advanced user and can add/modify test suites',3),(4,'developer_user','Developer Role: he has whole power and permissions on test bed platform',4);
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
  `DONE_STATE` varchar(255) DEFAULT NULL,
  `SUSPENDED_STATE` varchar(255) DEFAULT NULL,
  `ABORTED_STATE` varchar(255) DEFAULT NULL,
  `WORKING_STATE` varchar(255) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `testPlanId` bigint(20) DEFAULT NULL,
  `sutId` bigint(20) DEFAULT NULL,
  `reportId` bigint(20) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
  `type` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK14232AC268D63` (`user_id`),
  CONSTRAINT `FK14232AC268D63` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sut`
--

LOCK TABLES `sut` WRITE;
/*!40000 ALTER TABLE `sut` DISABLE KEYS */;
INSERT INTO `sut` VALUES (1,'sut1','xmldocument','XML document uploaded by web interface',1);
/*!40000 ALTER TABLE `sut` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taf`
--

DROP TABLE IF EXISTS `taf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taf` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taf`
--

LOCK TABLES `taf` WRITE;
/*!40000 ALTER TABLE `taf` DISABLE KEYS */;
/*!40000 ALTER TABLE `taf` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testplan`
--

DROP TABLE IF EXISTS `testplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testplan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `xml` text,
  `userId` bigint(20) DEFAULT NULL,
  `datetime` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testplan`
--

LOCK TABLES `testplan` WRITE;
/*!40000 ALTER TABLE `testplan` DISABLE KEYS */;
INSERT INTO `testplan` VALUES (1,'<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<tebes:TestPlan xmlns:tebes=\"http://www.ubl-italia.org/TeBES\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" datetime=\"2012-06-13T18:43:00\" id=\"1\" state=\"draft\" userID=\"1\" userSUT=\"xmlupload\" xsi:schemaLocation=\"http://www.ubl-italia.org/TeBES/xmlschemas/TAML http://www.ubl-italia.org/TeBES/xmlschemas/TAML/testAssertionMarkupLanguage.xsd\">\r\n	\r\n	<tebes:TestActionList>\r\n	\r\n		<tebes:TestAction number=\"1\">\r\n			\r\n			<tebes:ActionName>XMLSchema-UBL-T10</tebes:ActionName>\r\n			<tebes:ActionDescription>UBL Invoice XML Schema Validation.</tebes:ActionDescription>\r\n			<tebes:Test jumpPrerequisites=\"true\" lg=\"taml\" location=\"http://www.ubl-italia.org/TeBES/testsuites/UBL/TS-001_UBL/TC-001_XMLSchema-UBL-T10.xml\" type=\"TestAssertion\">TA-001_TC-001_TS-001</tebes:Test>\r\n			\r\n		</tebes:TestAction>\r\n	\r\n	\r\n		<!-- \n			Each tebes:TestAction MUST have a @number attribute used to enumerate every action.\n			The N actions will be execute from 1 to N, sequentially, \n			excepted different specification expressed through the tebes:TestPlan/tebes:Choreography element\n			\n			Each tebes:TestAction MUST have a @type attribute used with one and only one of these values:\n				- \"TestAssertion\" \n				- \"TestCase\" \n				- \"TestSuite\" \n		-->\r\n		<tebes:TestAction number=\"2\">\r\n			\r\n			<!-- \n				The following \"tebes:ActionName\" element can take different values \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then tebes:ActionName element takes taml:testAssertion/@name from Test Assertion\n					(f.e. \"SDI-UBL\")\n				- \"TestCase\" \n					then tebes:ActionName element takes taml:testAssertionSet/@setname from Test Case\n					(f.e. \"Test Case for UBL Invoice Italian INTERCENT-ER Schematron validation\")\n				- \"TestSuite\" \n					then tebes:ActionName element takes Test Suite name folder\n					(f.e. \"TS-005_sdi\")\n			-->\r\n			<tebes:ActionName>SDI-UBL</tebes:ActionName>\r\n			\r\n			<!-- \n				The following \"tebes:ActionDescription\" element can take different values \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then Name element takes taml:testAssertion/taml:description\n				- \"TestCase\" \n					then Name element takes taml:testAssertionSet/@setname (or user input)\n				- \"TestSuite\" then Name element takes Test Suite name folder  (or user input)\n			-->\r\n			<tebes:ActionDescription>Italian INTERCENT-ER UBL Invoice Schematron Validation.</tebes:ActionDescription>\r\n			\r\n\r\n			<!-- \n				The tebes:ActionTarget is the SUT type \n			-->\r\n			<!-- tebes:ActionTarget type=\"xml\" retrieve=\"upload\">document</tebes:ActionTarget -->			\r\n						\r\n			<!-- \n				The tebes:Test element contains the reference to test to execute, \n				dependent on previous tebes:TestAction/@type attribute:\n				- \"TestAssertion\" \n					then tebes:Test element takes taml:testAssertion/@id from Test Assertion\n					(f.e. \"TA-001_TC-001_TS-005\")\n				- \"TestCase\" \n					then tebes:Test element takes taml:testAssertionSet/@setid from Test Case\n					(f.e. \"TC-001_TS-005\")\n				- \"TestSuite\" \n					then tebes:Test element takes Test Suite name folder prefix\n					(f.e. \"TS-005\")\n					\n				The tebes:Test/@location specifies the location of the test:\n				- an XML file absolute path, in the cases of \"TestAssertion\" or \"TestCase\" types;\n				- a folder absolute path, in the case of \"TestSuite\" type\n			-->\r\n			<tebes:Test jumpPrerequisites=\"false\" lg=\"taml\" location=\"http://www.ubl-italia.org/TeBES/testsuites/UBL/TS-005_sdi/TC-001_SDI-UBL-T10.xml\" type=\"TestAssertion\">TA-001_TC-001_TS-005</tebes:Test>\r\n			\r\n		</tebes:TestAction>\r\n		\r\n\r\n\r\n		<!-- tebes:TestAction number=\"3\">\n			\n			<tebes:ActionName>TC-001_TS-002</tebes:ActionName>\n			<tebes:ActionDescription>Test Case for UBL Invoice BII Schematron validation.</tebes:ActionDescription>\n			<tebes:Test \n				lg=\"taml\" \n				type=\"TestCase\" \n				jumpPrerequisites=\"true\"\n				location=\"http://www.ubl-italia.org/TeBES/testsuites/UBL/TS-002_bii/TC-001_BII-UBL-T10.xml\">TC-001_TS-002</tebes:Test>\n			\n		</tebes:TestAction -->\r\n		\r\n	</tebes:TestActionList>\r\n\r\n			<!-- \n				The \"tebes:Choreography\" element specifies a choreography among the defined test actions. \n				If this element is empty, the N actions MUST be executed from number 1 to N, sequentially.\n				\n				The choreography XML element is work in progress:\n				a solution to express choreography may be an ebBP-like XML syntax. \n			-->	\r\n	<tebes:Choreography/>\r\n	\r\n</tebes:TestPlan>\r\n',1,'2012-06-13T18:43:00','updated','C:/Java/workspace-juno/TeBES/war/src/main/webapp/users/1/testplans/TP-1.xml');
/*!40000 ALTER TABLE `testplan` ENABLE KEYS */;
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
  PRIMARY KEY (`id`),
  KEY `FK285FEB6FBC983` (`role_id`),
  CONSTRAINT `FK285FEB6FBC983` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Cristiano','Novelli','cristiano.novelli@enea.it','xcristiano',4);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-01-06 11:21:06
