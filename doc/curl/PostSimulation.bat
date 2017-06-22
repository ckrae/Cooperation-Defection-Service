REM	curl test script for las2peer service

REM test authentication with test user alice

curl -v -H "Content-Type: application/json"  -X POST -d "{\"graphId\":2,\"dynamic\":\"Moran\",\"dynamicValues\":[],\"payoffValues\":[1.0,2.0,3.1,0.0],\"iterations\":20}" "http://localhost:8082/cdService/simulation"  --user alice:pwalice



PAUSE

REM more curl commandlines...
PAUSE

