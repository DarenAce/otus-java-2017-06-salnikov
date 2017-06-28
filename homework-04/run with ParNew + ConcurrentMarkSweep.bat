SET MEMORY=-Xms512m -Xmx512m
SET GC=-XX:+UseParNewGC -XX:+UseConcMarkSweepGC
java %MEMORY% %GC% -jar %1