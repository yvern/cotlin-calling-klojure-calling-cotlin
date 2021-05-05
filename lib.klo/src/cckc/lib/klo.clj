(ns cckc.lib.klo
  "`:gen-class` form ensures this namespace outputs a class that can be used from Kotlin (or any jvm language)
   the value after `:methods` is to be a vec of public methods and their signatures

   unfortunately, we need to provide the full path/name of the types/interfaces/classes,
   even while importing them, maybe because of aliasing and making sure we get the right ones.
   
   the `:name` key makes the generated class have a name other than the current namespace.

   the method parameters are to be in the vector, the second element, and lastly the return type.
   no return value requires the type java.lang.Void
   
   the name of exported methods should match functions in the current namespace (after the '-'),
   and this is how they are seen from Java
   
   `gen-class` allows for a 'prefix' to be used, that would be placed before the '-' on the function names,
   by default it is empty (''). personally i would disencourage to use it,
   as we all recognize `-main` as mapping to `public static void main`, so as static methods on our class"
  (:require [clojure.pprint :refer [print-table]])
  (:import (java.util List Map)
           (java.util.function Function))
  (:gen-class
   :name cckc.lib.Klo
   :methods [^{:static true}
             [funky [java.util.List java.util.function.Function]
              java.util.Map]
             
             ^{:static true}
             [printer [java.util.List]
              java.lang.String]]))

;; Here we would like to be aware of reflection,
;; which can degrade performance.
;; Type hints are here to stop that
(set! *warn-on-reflection* true)

(defn top-chars
  "Given a closure filling the Java Function interface and an url,
   reads the content of the url, 
   
   and applies (via `.apply`) the given closure,
   builds a histogram and returns the top 10 results"
  [^Function f url]
  (->> url
       slurp
       (.apply ^Function f)
       frequencies
       (sort-by val)
       reverse
       (into [] (take 10))))

(defn -funky
  "Receives a Java List of urls and a closure filling the Java Function interface,
   
   returns a Java Map of given urls to the top 10 char count ranking, in descending order"
  ^Map [^List i ^Function f]
  (let [top-chars' (partial top-chars f)]
    (into {} (map (juxt identity top-chars')) i)))

(defn -printer
  "Given a Java List with the tuples of chars and their counts,
   
   and returns a String containing a table ranking of those"
  ^String [^List counts]
  (->> counts
       (mapv (partial zipmap ['char 'count]))
       print-table
       with-out-str))
