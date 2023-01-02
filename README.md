# FINAL WORKSHOP
Contains all SQL, Mongo and PYP workshops (final task maybe incomplete but queries are up)
**TODO >>> UP REDIS**

## Setup for Railway Deployment
Variables in application properties are not sent to railway, any variables set there during local run are to be set again in railway environment variables.

### Railway
**Code**
Ensure pom.xml java version 18
Put Dockerfile on root of project
Generate new snapshot file in target folder
```
mvn package
```
Replace snapshot name in dockerfile

**Website**
Create new empty service
Provision new empty, mySQL and mongo service
Take connection details for all services and proceed with steps below for mySQL and MongoDB and return here

***RETURN TO THIS POINT AFTER SETTING UP MYSQL AND MONGO***
Application Properties for Local Run --> not pushed to Railway

```
spring.datasource.url=jdbc:mysql://root:ngPzRebiBHWSJB0Kv5Aq@containers-us-west-27.railway.app:7589/railway
spring.datasource.username=root 
```

Environment Variables for Railway
```
SPRING_DATASOURCE_URL = jdbc:mysql://root:asdasdasdasd@containers-us-west-1.railway.app:1234/railway
SPRING_DATASOURCE_USERNAME = root
SPRING_DATA_MONGODB_URI = mongodb://mongo:asdasdadadasd@containers-us-west-1.railway.app:1234/<database>?authSource=admin 
```

**Local Terminal**
Push to Railway
```
railway login
railway link 
<Choose service>
railway up
```

**Website**
Check deployment status
Generate domain
Test application online
Debug with deployment logs on Railway

### mySQL
**Sample mySQL connection details from Railway**
mysql://root:asdadsadadsad@containers-us-west-1.railway.app:1234/railway
mysql://<user>:<password>@<host>:<port>/<database>

**Setup schemas/databases on Railway mySQL and upload data**
```
cd <directory with .sql files>
<enter connection string from Railway mySQL, sample below>
mysql -h<host> -u<user> -p<password> --port <port number> --protocol=TCP <database/schema, default is railway>
CREATE SCHEMA IF NOT EXISTS `<database name>`;
USE <DATABASE>
Source .sql files
create user '<username>'@'<host>' identified by '<password>' --> use % for host for remote logins
Grant all privileges on <database>.<table> to '<username>'@'<host>'
Flush privileges
Select user from mysql.user; //check for successful creation
```
**Create another user other than root**
```
create user '<username>'@'<host>' identified by '<password>' --> use % for host for remote logins
Grant all privileges on <database>.<table> to '<username>'@'<host>'
Flush privileges
Select user from mysql.user; //check for successful creation
```

**Test connection with Workbench**
Take connection details of host and port from Railway as seen from above sample


### MongoDB
**Sample mongo connection details from Railway**
mongodb://mongo:asdasdasdasd@containers-us-west-1.railway.app:1234
mongodb://<user>:<password>@<host>:<port>

**Setup schemas/databases on Railway mySQL and upload data**
```
cd <directory with data files>
mongoimport  mongodb://mongo:<host>:<port>/<DB NAME>?authSource=admin -c<collection> --<type of importing data> <doc path>
```

**Test connection with Studio3T**
Take connection details of host and port from Railway as seen from above sample

**RETURN ABOVE FOR CONTINUATION WITH SETTING UP WITH RAILWAY**