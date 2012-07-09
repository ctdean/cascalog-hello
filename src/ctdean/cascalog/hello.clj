;;;;
;;;; hello world for cascalog on Amazon EMR
;;;;
;;;; Chris Dean

(ns ctdean.cascalog.hello
  (:use cascalog.api
        cascalog.util)
  (:require [cascalog.ops :as ops]
            [clojure.string :as str]))

(defmapcatop split-words
  "Split a string into a seq of words where a word is defined as a
   letter folled by one or more letters or digits."
  [line]
  (map str/lower-case
       (re-seq #"[a-zA-Z]\w+" line)))

(defn freq-count-query
  "A query that returns the word frequency count"
  [tap]
  (<- [?word ?count]
      (tap ?line)
      (split-words ?line :> ?word)
      (ops/count ?count)))

;; Write out the frequency count for the text input.
(defmain WritePoemFreq [in-path out-path]
  (let [in (hfs-textline in-path)
        out (hfs-textline out-path)]
    (?- out (freq-count-query in))))
