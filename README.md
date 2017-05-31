# Feedback System project
## Project requirements
1. PostgreSQL.
2. TomEE (Apache) WebProfile.
3. Hibernate ORM.
4. (Optional) IntelliJ IDEA
10. (Optional) pgAdmin 3 or 4.

## Starting up project
0. In pgAdmin change password of user `postgres` to `testpass`  
1. Open pgAdmin 4
2. Create database `feedbackdb`
3. Open Feedback System project tree in IntelliJ IDEA
4. Find `feedback_schema.sql` under `src/main/db`
5. Right-click `Run 'feedback_schema.sql'...`
4. Find `sample_data.sql` under `src/main/db`
5. Right-click `Run 'sample_data.sql'...`

## Application Server configuration

### TomEE (Apache)
Short version:
1. Download WebProfile, ZIP from: http://tomee.apache.org/downloads.html
2. Unzip
4. In IntelliJ IDEA: register "TomEE Server" -> local:
    * Press "Fix", choose "exploded war" as artifact
5. Run the server, project should start successfully.

Long version: [TomEE and IntelliJ IDEA](http://tomee.apache.org/tomee-and-intellij.html).

### Hibernate ORM
Short version:
1. Download final version of Hibernate ORM, ZIP from: http://hibernate.org/orm/downloads/
2. Copy contents from `hibernate-release-5.2.9.Final.zip/lib/required` to the `<tomee>/lib` directory.
3. Add the following lines to the `<tomee>/conf/system.properties` file:
    * `javax.persistence.provider = org.hibernate.jpa.HibernatePersistenceProvider
tomee.jpa.factory.lazy=true`

Long version: [Hibernate User Guide](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html).