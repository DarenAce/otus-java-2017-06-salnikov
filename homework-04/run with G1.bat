SET MEMORY=-Xms512m -Xmx512m
SET GC=-XX:+UseG1GC
java %MEMORY% %GC% -jar %1