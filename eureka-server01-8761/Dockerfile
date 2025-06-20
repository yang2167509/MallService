FROM openjdk:8
ENV workdir=/root/images/eureka-server01
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8761
CMD java -jar ./target/eureka-server01-8761-0.0.1-SNAPSHOT.jar --server.port=8761