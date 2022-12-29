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

Application Properties for Local Run --> not pushed to Railway

```
spring.datasource.url=jdbc:mysql://root:ngPzRebiBHWSJB0Kv5Aq@containers-us-west-27.railway.app:7589/railway
spring.datasource.username=root 
```

Environment Variables for Railway
```
SPRING_DATASOURCE_URL=jdbc:mysql://root:asdasdasdasd@containers-us-west-1.railway.app:1234/railway
SPRING_DATASOURCE_USERNAME=root
SPRING_DATA_MONGODB_URI=mongodb://mongo:asdasdadadasd@containers-us-west-1.railway.app:1234/<database>?authSource=admin 
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

**Setup schemas/databases on Railway mySQL and upload data**
```
cd <directory with data files>
mongoimport  mongodb://mongo:<host>:<port>/<DB NAME>?authSource=admin -c<collection> --<type of importing data> <doc path>
```

**Test connection with Studio3T**
Take connection details of host and port from Railway as seen from above sample









## MyWind

MyWind is a MySQL version of the Microsoft Access 2010 *Northwind* sample database.

The Northwind database is an excellent tutorial schema for a 
small-business ERP, with customers, orders, inventory, purchasing, 
suppliers, shipping, employees, and single-entry accounting. However,
I wanted to experiment with the schema using [MySQL](http://www.mysql.com). 

The Northwind sample database provided with Microsoft Access is a tutorial schema for managing small business customers, orders, inventory, purchasing, suppliers, shipping, and employees. My problem was I wanted to experiment with the Northwind schema using [MySQL](http://www.mysql.com).

## WARNING

* northwind-02 (northwind-02.mwb and northwind-02-sales-orders-and-shipping.png) are a work in process and presumably related to improving the ERD model where it relates to sales orders and shipping, but the specifics have now been lost in time. If anyone sorts this out, I would sincerely appreciate a pull request (creating an issue would be just as appreciated). Thanks.

## Files

* Model:
    * northwind.mwb (MySQL Workbench v6.2)
* EER Diagram:
    * northwind-erd.pdf
    * northwind-erd.png
* Structure:
    * northwind.sql
    * northwind-default-current-timestamp.sql (uses DEFAULT CURRENT TIMESTAMP, requires MySQL 5.6.5+)
* Data:
    * northwind-data.sql

## Creating MyWind

* Created Northwind.aacdb using MS Access 2010 (File > New > Sample Templates > Northwind > SaveAs).
* Created basic SQL equivalent of Northwind schema for MySQL using using BullZip ["Access to MySQL"](http://www.bullzip.com).[1]
* Replaced CamelCase identifier names with lower_case_with_underscore identifier names.
* Replaced " " (space) and "/" (forward slash) characters in identifiers with _underscores_.
* Renamed table primary keys "id".
* Renamed table foreign keys "xxx_id" (e.g. "inventory_id").
* Changed record-create and modify-date columns to type DATETIME (to avoid the 1997 - 2038 UTC date range restriction of TIMESTAMP, and also other limitations).
* Added foreign key relationships and created ERD using MySQL Workbench.
* Imported SQL into MySQL Workbench
    * Added foreign key relationships visually
    * Exported EER Diagrams

----
 [1]: BullZip *Access to MySQL* version 5.1.242. *Access to MySQL* "...may be used free of charge for non-commercial purposes.", http://www.bullzip.com, accessed 2014-01-08.
