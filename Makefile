docs:
	cd lib.klo && clj -M:sidenotes && mv docs ./..

lib.klo/lib.klo.jar:
	cd lib.klo && clj -X:jar

local-install: lib.klo/lib.klo.jar
	cd lib.klo && clj -X:install

run: local-install
	./gradlew run

assemble: local-install
	./gradlew assemble

image:
	docker build -t ckc .

clean:
	@rm -rf docs
	@rm lib.klo/lib.klo.jar