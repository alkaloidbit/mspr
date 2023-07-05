#!/bin/bash

mysql -u root -pRvTC6F8D < ./sql/install_db.sql
echo './install_db: Db reinit.'
mysql -u root -pRvTC6F8D < ./sql/fixtures.sql
echo './fixtures.sql: Db populated with fixtures data.'

