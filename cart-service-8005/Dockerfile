FROM openjdk:8
ENV workdir=/root/images/cart-service
COPY . ${workdir}
WORKDIR ${workdir}
EXPOSE 8005
CMD java -jar ./target/cart-service-8005-0.0.1-SNAPSHOT.jar --server.port=8005
