@echo off
rem dsa -- run the DSA practice system from any directory (cmd / PowerShell).
rem
rem Setup: copy this file (and the plain "dsa" script) into a directory on
rem your PATH, e.g. C:\Users\you\.local\bin, then set DSA_HOME to wherever
rem you cloned this repo, e.g.:
rem   setx DSA_HOME "C:\path\to\dsa-practice-kit"
rem (open a new terminal afterwards so the env var takes effect)
if not defined DSA_HOME (
    echo dsa: DSA_HOME is not set
    echo      setx DSA_HOME "C:\path\to\dsa-practice-kit" and open a new terminal
    exit /b 1
)
if not exist "%DSA_HOME%\dsa.py" (
    echo dsa: cannot find dsa.py under "%DSA_HOME%"
    echo      check that DSA_HOME points at your dsa-practice-kit checkout
    exit /b 1
)
python "%DSA_HOME%\dsa.py" %*
exit /b %ERRORLEVEL%
