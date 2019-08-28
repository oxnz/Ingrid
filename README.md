# Ingrid


## Run

```shell
curl localhost:8080
curl -XPOST -H "Content-Type: application/json" \
    -d '{"cat":"CHECKIN", "ref":"1234", "state": "CA", "city": "SF"}' \
    localhost:8080/tx/record
curl localhost:8080/records
curl localhost:8080/results
curl -XPOST localhost:8080/actuator/shutdown
```
