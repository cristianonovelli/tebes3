TeBES setup into the Eclipse Development Environment:

1. SVN Checkout from location:
https://tebes3.googlecode.com/svn/trunk/

2. Import TeBES 4-modules project as Existing Maven Project

3. Adjust files in TeBES\ejb\src\main\resources
tebes.properties 
tebes-ds.xml  
log4j.xml 

4. Create MySQL account and database as specified in *-ds.xml file

5. Set environment variable JBOSS_HOME
(i.e. JBOSS_HOME=C:\Java\jboss-4.2.3.GA)
and set security module (see at the end of this file)

6. Check build-package-deployment through the maven statement:
mvn clean install -DskipTests

7. Check JUnit Tests through one of following maven statements:
mvn clean install
mvn clean install -Pdev-create -DskipTests
mvn clean install -Pdev-create
mvn clean install -Pdev-update
mvn clean install -Pprod
mvn clean -Pdev-create -Dit.test=SessionManagerImplITCase verify
mvn clean install -Pdev-create -Dit.test=SessionManagerImplITCase verify
mvn clean install -Pprod -Dit.test=SessionManagerImplITCase verify

8. To prepare database, import in MySQL the file ejb\config\tebes_import.sql:
mysql -u root -proot7 -h localhost tebes < tebes_import.sql

9. URL APPLICAZIONE WEB
http://localhost:8080/TeBES-war/index.jsf


- CONFIGURAZIONE DEL MODULO DI SICUREZZA IN JBOSS
Per configurare JAAS in JBOSS occorre inserire i seguenti tag xml all'interno del tag <policy>, nel file login-config.xml 
presente nella cartella JBOSS_HOME/server/default/conf

<application-policy name="tebes_policy">
        <authentication>
            <login-module code="org.jboss.security.auth.spi.DatabaseServerLoginModule" flag="required">
             <module-option name = "dsJndiName">java:/TeBESDS</module-option>
                <module-option name="unauthenticatedIdentity">guest</module-option>
                <module-option name="principalsQuery">SELECT password FROM User WHERE eMail=?</module-option>
                <module-option name="rolesQuery">SELECT name, 'Roles' FROM Role WHERE id = (SELECT role_id FROM User WHERE eMail=?)</module-option>
            </login-module>
        </authentication>
</application-policy>


- DUMP
Per creare il dump: 
1. avviare i test con la chiamata 
mvn clean install -Pdev-create -Dit.test=SessionManagerImplITCase verify
2. sospendere arresto Jboss con ctrl-canc prima dell'arresto
3. esportare db da console con:
mysqldump -u root -p[password] tebes > tebes_dev_import.sql 
4. per salvare solo i dati senza le create delle tabelle:
mysqldump -u root -p[password] --no-create-info --complete-insert tebes > tebes_data_only.sql

