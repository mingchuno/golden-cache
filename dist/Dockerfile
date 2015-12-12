FROM java

COPY . /code

VOLUME /code

WORKDIR /code

CMD ./bin/golden-cache -Dconfig.resource='application.prod.conf' -Dlogger.resource='logback.xml'