all: build

build:
	sbt clean dist

clean:
	rm -rf target/