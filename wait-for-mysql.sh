#!/bin/bash

until (echo > /dev/tcp/mysql/3306) 2> /dev/null
do
 echo "MySQL is unavailable - sleeping"
 sleep 15
done

echo "MySQL is up - executing command"
exec $@