(ns test
  (:require [speclj.core :refer :all]
            [test-ring.core :refer :all]))

(describe "wrap-split-uri"
  (it "splits"
    (should=
      ((wrap-split-uri :split-uri) {:uri "/a/b/c"})
      ["a" "b" "c"])
    )
  )

(describe "split-uri"
  (it "splits /a/b/c to a, b, c"
    (should=
      ["a" "b" "c"]
      (split-uri "/a/b/c"))
    )
  (it "splits /a/b to a, b"
    (should=
      ["a" "b"]
      (split-uri "/a/b"))
    )
  (it "splits / to an empty list"
    (should=
      []
      (split-uri "/"))
    )
  )

(describe "Truth"

  (it "is true"
    (should true))

  (it "is not false"
    (should-not false)))

(run-specs)
