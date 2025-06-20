FROM openjdk:8
ENV workdir=/root/images/message-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8009
CMD java -jar ./target/message-service-8009-0.0.1-SNAPSHOT.jar --server.port=8009
