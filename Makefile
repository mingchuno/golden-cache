all: build

build:
	sbt clean test dist

clean:
	rm -rf target/