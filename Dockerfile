FROM clojure as build

RUN apt update && apt install make -y

WORKDIR /base

COPY . .

RUN make assemble

FROM openjdk:11-jre-slim

WORKDIR /base

COPY --from=build /base/app/build/distributions/app.tar .

RUN tar -xf app.tar

ENTRYPOINT app/bin/app