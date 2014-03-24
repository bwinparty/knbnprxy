#curl -X GET "http://localhost:8585/rest/epics" > epics.json
#curl -X POST --data-binary @batikLogo.svg "http://localhost:8585/rest/compile?context=test&format=pdf" > batik.pdf
#curl -X POST --data-binary @batikLogo.svg "http://localhost:8585/rest/compile?context=test&format=png&width=250&height=250&scale=1" > batik.png
#curl -X POST --data-binary @batikLogo.svg "http://localhost:8585/rest/compile?context=test&format=svg" > batik.svg
#curl -X GET "http://localhost:8585/data/notes"
#curl -X POST -d "{'field1':'value1','field2':123}" "http://localhost:8585/data/notes"
#curl -X DELETE "http://localhost:8585/data/notes?id=532ef33b30044056f615c89c"
#curl -X PUT -d "{'field1':'value2','field2':321,'field3':'new'}" "http://localhost:8585/data/notes?id=532ef33b30044056f615c89c"
