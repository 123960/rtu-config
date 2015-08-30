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
  [d-path config-name config-desc]
  (spit (str d-path "/installation.xml") (s/replace (s/replace install-base-file #"<!--CONFIG-NAME-->" config-name) #"<!--CONFIG-DESC-->" config-desc)))

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
  (doseq [file (file-list input-path)] 
    (copy-file (str input-path "/" file) (str output-path (config file)))))

(defn -main
  "Creates a config ready-to-up to ACM based on a config downloaded from ACM replacing the name of the files with the correct name"
  [& args]
  (let [config-path (first args) config-name (second args) config-desc (get args 2)]
    (if (and config-path (and config-name config-desc))
      (if (create-dir "./rtu-configproperties/")
        (if (install-file "./rtu-configproperties/" config-name config-desc)
          (println "something strange happened")
          (rtu-config-files config-path "./rtu-configproperties/"))
        (println "cannot create rtu-configproperties directory, maybe it already exist"))
      (println "Creates a config ready-to-up to ACM based on a config downloaded from ACM replacing the name of the files with the correct name
                Args expected: [config-path config-name config-desc]
                  config-path = Path to directory with the configuration downloaded from ACM
                  config-name = Name of the configuration in the installation.xml file (tag <ns3:name>)
                  config-desc = Description of the configuration in the installation.xml file (tag <ns3:description>)"))))