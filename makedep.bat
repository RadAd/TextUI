@echo off
setlocal

set RAD_INSTALL_DIR=%~dp0\libs
call "%~dp0..\RadConsole\make" install
