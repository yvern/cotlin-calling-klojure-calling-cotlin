lib.klo.jar:
	cd lib.klo && clj -X:jar

local-install: lib.klo.jar
	cd lib.klo && clj -X:install

run: local-install
	./gradlew run

assemble: local-install
	./gradlew assemble

image:
	docker build -t ckc .
