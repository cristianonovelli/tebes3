Setup TeBES in Eclipse Development Environment:

1. SVN Checkout from location:
https://tebes3.googlecode.com/svn/trunk/

2. Import TeBES 4-modules project as Existing Maven Project

3. Adjust *.properties and *-ds.xml files from
TeBES/ejb/src/main/resources

4. Create MySQL account and database as specified in *-ds.xml file

5. Set environment variable JBOSS_HOME
(i.e. JBOSS_HOME=C:\Java\jboss-4.2.3.GA)

6. Check build-package-deployment through the maven statement:
mvn clean install -DskipTests

7. Check JUnit Tests through one of following maven statements:
mvn clean install
mvn clean install -Pdev-create
mvn clean install -Pdev-update
mvn clean install -Pprod
