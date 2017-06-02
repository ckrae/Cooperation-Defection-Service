REM	curl test script for las2peer service

REM test authentication with test user alice

curl -v -X GET "http://localhost:8082/cdService/simulation/1/parameters"  --user alice:pwalice



PAUSE

REM more curl commandlines...
PAUSE

