FROM openjdk:8
ENV workdir=/root/images/member-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8008
CMD java -jar ./target/member-service-8008-0.0.1-SNAPSHOT.jar --server.port=8008
