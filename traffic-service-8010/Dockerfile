FROM openjdk:8
ENV workdir=/root/images/traffic-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8010
CMD java -jar ./target/traffic-service-8010-0.0.1-SNAPSHOT.jar --server.port=8010
#EXPOSE ${badao_port}
#CMD java -jar traffic-service-8010-0.0.1-SNAPSHOT.jar --server.port=${badao_port}
