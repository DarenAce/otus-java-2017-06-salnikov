SET MEMORY=-Xms512m -Xmx512m
SET GC=-XX:+UseSerialGC
java %MEMORY% %GC% -jar %1