FROM openjdk:8
LABEL name = "fronted-service-8001"
ENV workdir=/root/images/fronted-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8001
CMD java -jar ./target/fronted-service-8001-0.0.1-SNAPSHOT.jar --server.port=8001

