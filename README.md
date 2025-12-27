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

We will now setup the db 

