FROM ingvarjackal/alpine-oraclejre9
VOLUME /tmp
ADD target/inservice-2.0-SNAPSHOT-jar-with-dependencies.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
RUN apk add --no-cache openssl
ENV DOCKERIZE_VERSION v0.4.0
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-alpine-linux-amd64-$DOCKERIZE_VERSION.tar.gz
CMD [ "sh", "-c", "$COMMANDS java $JAVA_OPTS -Xms$JAVAMEM -Xmx$JAVAMEM -Djava.security.egd=file:/dev/./urandom -Dlog4j.configuration=$LOG4JCONF -jar /app.jar" ]