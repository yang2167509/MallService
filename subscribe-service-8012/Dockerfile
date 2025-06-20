FROM openjdk:8
ENV workdir=/root/images/subscribe-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8012
CMD java -jar ./target/subscribe-service-8012-0.0.1-SNAPSHOT.jar --server.port=8012
