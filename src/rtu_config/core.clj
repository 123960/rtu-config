(ns rtu-config.core
  (:gen-class)
  (:require [clojure.edn :as edn])
  (:require [clojure.java.io :as io])
  (:require [clojure.string  :as s]))

(def config
  (:file-map (edn/read-string (slurp "resources/config.edn"))))

(def install-base-file
  (slurp "resources/installation.xml"))

(defn install-file
  [config-name config-desc]
  (s/replace (s/replace install-base-file #"<CONFIG-NAME>" config-name) #"<CONFIG-DESC>" config-desc))

(defn create-dir
  [d-path]
  (.mkdirs (io/file d-path)))

(defn copy-file [source-path dest-path]
  (io/copy (io/file source-path) (io/file dest-path))) 

(defn file-list
  [config-path]
  (for [file (filter #(.isFile %) (file-seq (io/file config-path)))](.getName file)))

(defn rtu-config-files
  [input-path output-path]
  (for [file (file-list input-path)]
      (copy-file (str input-path "/" file) (str output-path (config file)))))

(defn -main
  "Creates a config ready-to-up to ACM based on a config downloaded from ACM replacing the name of the files with the correct name"
  [config-path config-name config-desc & args]
  (if (create-dir "./rtu-configproperties/")
    (rtu-config-files config-path "./rtu-configproperties/")
    (rtu-config-files config-path "./rtu-configproperties/")))

 ;;#(install-file config-name config-desc)