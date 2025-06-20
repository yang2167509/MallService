FROM openjdk:8
ENV workdir=/root/images/goods-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8004
CMD java -jar ./target/goods-service-8004-0.0.1-SNAPSHOT.jar --server.port=8004
