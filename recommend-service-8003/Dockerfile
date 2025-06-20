FROM openjdk:8
ENV workdir=/root/images/recommend-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8003
CMD java -jar ./target/recommend-service-8003-0.0.1-SNAPSHOT.jar --server.port=8003
