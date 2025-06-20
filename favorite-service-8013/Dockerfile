FROM openjdk:8
ENV workdir=/root/images/favorite-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8013
CMD java -jar ./target/favorite-service-8013-0.0.1-SNAPSHOT.jar --server.port=8013
