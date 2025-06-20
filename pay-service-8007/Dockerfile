FROM openjdk:8
ENV workdir=/root/images/pay-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8007
CMD java -jar ./target/pay-service-8007-0.0.1-SNAPSHOT.jar --server.port=8007
