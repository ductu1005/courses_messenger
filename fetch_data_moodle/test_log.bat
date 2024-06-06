@echo off
setlocal enabledelayedexpansion

REM Lấy ngày và giờ hiện tại
for /f "delims=" %%a in ('wmic OS Get localdatetime ^| find "."') do set datetime=%%a
set "year=!datetime:~0,4!"
set "month=!datetime:~4,2!"
set "day=!datetime:~6,2!"
set "hour=!datetime:~8,2!"
set "minute=!datetime:~10,2!"
set "second=!datetime:~12,2!"

REM Định dạng ngày và giờ
set "formatted_datetime=!year!!month!!day!-!hour!!minute!!second!"

REM Tên file log
set "log_file=E:\Do_an_20020494\fetch_data_moodle\logs\logfile_!formatted_datetime!.log"

REM Ghi log vào file
echo Log content goes here >> %log_file%

REM Chạy ứng dụng Java và ghi log
java -jar "E:\Do_an_20020494\fetch_data_moodle\out\artifacts\fetch_data_moodle_jar\fetch_data_moodle.jar" >> %log_file%

REM Kết thúc
exit /b 0
