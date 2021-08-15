@echo off
set path=C:\Users\Mamduh\.m2\repository\allure\allure-2.14.0\bin;C:\Program Files\Java\jdk-15.0.2\bin;%path%
allure serve allure-results
pause
exit