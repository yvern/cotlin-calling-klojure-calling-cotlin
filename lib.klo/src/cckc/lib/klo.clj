(ns cckc.lib.klo
  (:require [clojure.pprint :refer [print-table]])
  (:import (java.util List Map)
           (java.util.function Function))
  (:gen-class
   :methods [^{:static true}
             [funky [java.util.List java.util.function.Function]
              java.util.Map]
             
             ^{:static true}
             [printer [java.util.List]
              java.lang.String]]))

(set! *warn-on-reflection* true)

(defn- top-chars [^Function f url]
  (->> url
       slurp
       (.apply ^Function f)
       frequencies
       (sort-by val)
       reverse
       (into [] (take 10))))

(defn -funky ^Map [^List i ^Function f]
  (let [top-chars' (partial top-chars f)]
    (into {} (map (juxt identity top-chars')) i)))

(defn -printer ^String [^List counts]
  (->> counts
       (mapv (partial zipmap ['char 'count]))
       print-table
       with-out-str))
