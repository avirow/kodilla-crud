call runcrud.bat
if "ERRORLEVEL" == "0" goto openWebsite
goto fail

:openWebsite
start Google Chrome --new-window http://localhost:8080/crud/task/getTasks
echo Everything is OK
goto end

:fail
echo.
echo There were errors
echo Cannot open file: "runcrud.bat"

:end
echo.
echo Work is finish
