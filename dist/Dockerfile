FROM java

ADD . /code

VOLUME /code

WORKDIR /code

CMD ./bin/golden-cache -Dconfig.resource='application.prod.conf'