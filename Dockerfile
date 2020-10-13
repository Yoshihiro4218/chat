FROM openjdk:8u242-jre-slim
# Set the env variable
ENV SPRING_BOOT_HOME=/opt/spring-boot \
    LANG=ja_JP.UTF-8
USER root
RUN rm -f /etc/localtime && \
    ln -s /usr/share/zoneinfo/Asia/Tokyo /etc/localtime && \
    mkdir -p /opt/spring-boot && chmod 755 /opt/spring-boot && \
    useradd -s /sbin/nologin -g 0 -m spring-boot && \
    chown -R spring-boot:0 ${SPRING_BOOT_HOME} && \
    chmod -R g+rw ${SPRING_BOOT_HOME}
USER spring-boot
WORKDIR /opt/spring-boot
EXPOSE 8080
ADD ./target/chat.jar /opt/spring-boot/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/spring-boot/chat.jar"]