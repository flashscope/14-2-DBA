
#백업 실행기
#/home/next/redis/redis_dumper.sh


#!/bin/bash
echo "dump start!"
redis-cli save
sudo mv dump.rdb /var/lib/mysql/redis_dump/dump.rdb
mysql -u root -p881214 popi -e "INSERT INTO REDIS_DUMP(image) VALUES(LOAD_FILE('/var/lib/mysql/redis_dump/dump.rdb'));"
echo "dump end!"


#crontab 추가
#crontab -e
30 4 * * * /home/next/redis/redis_dumper.sh






