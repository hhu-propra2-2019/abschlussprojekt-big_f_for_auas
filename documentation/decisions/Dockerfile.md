Added Dockerfile to create jar files in container and run it on jdk11.
Build with : docker build . -t TAG
run with : docker run -p 8080:8080 -t TAG