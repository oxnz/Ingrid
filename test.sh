#!/bin/sh

baseURL='localhost:8080/tx'
recordURL="$baseURL/record"
state='never-land'
city='lost-city'
curl -X POST -H 'Content-Type: application/json' "$recordURL" -d @- <<EOF
{
	"cat":"USER",
	"ref":1234,
	"state":"$state",
	"city":"$city"
}
EOF
echo "RECORDS"
curl localhost:8080/tx/records
echo "RESULTS"
curl localhost:8080/tx/results
