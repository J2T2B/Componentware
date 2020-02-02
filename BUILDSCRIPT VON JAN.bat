@echo off
call gradle build
cd ./frontend
call npm run build
cd ..
xcopy "./frontend/build" "./server/src/main/webapp" /S /I /Y
pause