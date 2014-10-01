
#레디스 덤프 구현 배쉬 파일 위치
#/home/next/redis/redis_dumper.sh

#!/bin/bash
echo "dump start!"
redis-cli save
sudo mv dump.rdb /var/lib/mysql/redis_dump/dump.rdb
mysql -u root -p881214 popi -e "INSERT INTO REDIS_DUMP(image) VALUES(LOAD_FILE('/var/lib/mysql/redis_dump/dump.rdb'));"
echo "dump end!"

#crontab 명령
#crontab -e
30 4 * * * /home/next/redis/redis_dumper.sh

# Mysql 테이블 백업용
CREATE TABLE `REDIS_DUMP` (
  `num` int(11) NOT NULL AUTO_INCREMENT,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `image` longblob,
  PRIMARY KEY (`num`)
);