** How to run this app on VM manually **

Boot up 3 t3.micro instances in AWS with devops-practice ami for mysql,backend,frontend , we will configure in a order of db,backend,frontend

# MySQL

Install MySQL Server 8.0.x

```
dnf install mysql-server -y
```

Start and Enable MySQL Service

```
systemctl enable mysqld
```
```
systemctl start mysqld
```

Next, We need to change the default root password in order to start using the database service. 

```
mysql_secure_installation --set-root-pass LibApp@1
```
# Backend

As this is a spring-boot application , we need to first install java on backend vm

```
sudo yum install -y java-17-openjdk-headless

```

Make sure the installation is done correctly, with the below command

```
java --version
```

Install Maven onto the server, as the build tool for this project is maven

```
sudo yum install -y maven
```
Verify the installation with the below command

```
mvn --version
```

We will now setup the db from backend server like creating db,tables through mysql client.

```
 sudo dnf install -y https://dev.mysql.com/get/mysql80-community-release-el9-1.noarch.rpm
```
```
sudo rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022
```
```
sudo dnf install -y mysql-community-client --nogpgcheck
```

```
mysql -h db.sainathdevops.space -u root -p
```

Enter the password that we have set, i have created a domain and added a DNS record for db server on AWS route53, you can use the ip address directly as well

```
create database librarydb;
```

```
show databases;
```

```
use librarydb;
```

```
mysql -h db.sainathdevops.space -uroot -pExpenseApp@1 < /mysql/scripts/React-Springboot-Add-Tables-Script-1.sql
```
Once the data is loaded, you can install dependencies and run the jar file

```
mvn clean install
```

go to target folder and execute jar

```
java -jar library-app-be-0.0.1-SNAPSHOT.jar
```

# Front end

curl -fsSL https://rpm.nodesource.com/setup_18.x | sudo bash -
sudo yum install -y nodejs
sudo dnf  install nginx -y


