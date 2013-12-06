(ns test-framework
  (:require [speclj.core :refer :all]
            [test-ring.framework :refer :all]
            ))

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
  (it "splits /abc/ to [abc]"
    (should=
      ["abc"]
      (split-uri "/abc/")))
  )

(describe "wrap-prepend-method"
  (it "prepends the method"
    (should=
      ((wrap-prepend-method :matchee) {:split-uri ["a" "b" "c"]
                                       :request-method :get})
      [:get "a" "b" "c"])
    )
  )

(describe "wrap-split-uri"
  (it "splits"
    (should=
      ((wrap-split-uri :split-uri) {:uri "/a/b/c"})
      ["a" "b" "c"])
    )
  )

