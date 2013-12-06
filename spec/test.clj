(ns test
  (:require [speclj.core :refer :all]
            [test-ring.core :refer :all]
            ))

(describe "numeric?"
          (it "returns true on '123'"
              (should-be numeric? "123"))
          (it "returns false on '123abc'"
              (should-not-be numeric? "123abc"))
          (it "returns false on 'abc'"
              (should-not-be numeric? "abcd")))
               
(describe "Truth"

  (it "is true"
    (should true))

  (it "is not false"
    (should-not false)))

(run-specs)
