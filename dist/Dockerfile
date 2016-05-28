FROM java:8-jre

COPY . /code

WORKDIR /code

CMD ./bin/golden-cache -Dconfig.resource='application.prod.conf' -Dlogger.resource='logback.xml'