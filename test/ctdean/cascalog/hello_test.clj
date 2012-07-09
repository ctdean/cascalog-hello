;;;;
;;;; hello-test - test the frequency count query
;;;;
;;;; Chris Dean

(ns ctdean.cascalog.hello-test
  (:use cascalog.api
        clojure.test
        ctdean.cascalog.hello))

(deftest freq-test
  (let [line-src [["a line of text line many"]
                  ["words line count line text"]]]
    (is (= (??- (freq-count-query line-src))
           '((["count" 1] ["line" 4] ["many" 1] ["of" 1] ["text" 2] ["words" 1]))))))
