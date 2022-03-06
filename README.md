### Setup
install SBT

### Run
In a terminal, open the source directory and run:
````
sbt run 
````
On the first run, it will take some time. The app will be ready when this text appears:

```

[info] p.c.s.AkkaHttpServer - Listening for HTTP on /0:0:0:0:0:0:0:0:9000

(Server started, use Enter to stop and go back to the console...)
```

Once that text appears, then the following URL will pull back weather data http:127.0.0.1:9000/weather?lat=latitude&long=longitude