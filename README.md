**How to run this app on VM manually **

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
