
FROM openjdk:8
ENV workdir=/root/images/ad-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8002
CMD java -jar ./target/ad-service-8002-0.0.1-SNAPSHOT.jar --server.port=8002
