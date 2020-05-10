#!/bin/sh

echo "CREATE DATABASE IF NOT EXISTS \`chat_localtest\` ;" | "${mysql[@]}"
echo "GRANT ALL ON \`chat_localtest\`.* TO '"$MYSQL_USER"'@'%' ;" | "${mysql[@]}"
echo 'FLUSH PRIVILEGES ;' | "${mysql[@]}"