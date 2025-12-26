FROM mysql:8.0
RUN adduser -r lib_user
ENV MYSQL_ROOT_PASSWORD=LibApp@1 \
    MYSQL_USER=lib_user \
    MYSQL_PASSWORD=LibApp@1 \
    MYSQL_DATABASE=transactions
RUN chown -R lib_user:lib_user /var/lib/mysql /var/run/mysqld
COPY scripts/*.sql /docker-entrypoint-initdb.d/