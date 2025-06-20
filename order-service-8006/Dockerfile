FROM openjdk:8
ENV workdir=/root/images/order-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8006
CMD java -jar ./target/order-service-8006-0.0.1-SNAPSHOT.jar --server.port=8006
