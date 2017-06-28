SET MEMORY=-Xms512m -Xmx512m
SET GC=-XX:+UseParallelGC -XX:+UseParallelOldGC
java %MEMORY% %GC% -jar %1