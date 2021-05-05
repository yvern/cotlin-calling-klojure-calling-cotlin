# lib.klo

A small example on having a Clojure library that can be used as dependency and called from a Kotlin project

## Usage

Check out the `:jar` alias on [`deps.edn`](https://github.com/yvern/cotlin-calling-klojure-calling-cotlin/blob/main/lib.klo/deps.edn):

```clojure
  :jar {:replace-deps {com.github.seancorfield/depstar {:mvn/version "2.0.211"}}
        :exec-fn hf.depstar/jar
        :exec-args {:jar "lib.klo.jar"
                    :sync-pom true
                    :aot true
                    :compile-ns :all
                    :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}
```

these are not required for this setup to work, but since we dont really need some clojure features, we can trade them of for an insignificant slither of performance and unnoticeable jar size reduction

* `:aot true`

    we don't really need to compile our source code inside the other app, we can do it aot

* `:compile-ns :all`

    usually for (clojure) libs we dont do aot at all, but since we want to deal with them as simple jvm classes, this forces all namespaces to be aot

* `:jvm-opts ["-Dclojure.compiler.direct-linking=true"]`

    we dont actually need the whole var lookup dynamicity features, so we can leave it out

Build a deployable jar of this library:

    $ clojure -X:jar

This will update the generated `pom.xml` file to keep the dependencies synchronized with
your `deps.edn` file. Install it locally (requires the `pom.xml` file):

    $ clojure -X:install

The last command will also display the maven coordinate for this library:

```clojure
{net.clojars.cckc/lib.klo {:mvn/version "0.1.0-SNAPSHOT"}}
```

You can use it from Gradle (kotlin DSL) as:

```kotlin
implementation("net.clojars.cckc:lib.klo:0.1.0-SNAPSHOT")
```