(ns clojure.core.typed.test.runtime-infer.qualified-keys
  {:lang :core.typed
   :core.typed {:features #{:runtime-infer}}
   }
  (:require [clojure.core.typed :as t]
            [clojure.spec.alpha :as s]
            [clojure.test :refer :all]
            [clojure.walk :as walk]
            [clojure.pprint :refer [pprint]]))

;; Start: Generated by clojure.core.typed - DO NOT EDIT
(defmulti op-multi-spec :op)
(defmethod op-multi-spec :fn [_] (s/keys :req-un [::context ::fn ::op]))
(defmethod
  op-multi-spec
  :lambda
  [_]
  (s/keys :req-un [::body ::context ::op]))
(defmethod
  op-multi-spec
  :val
  [_]
  (s/keys :req-un [::op ::val] :opt-un [::context]))
(s/def ::val any?)
(s/def ::context any?)
(s/def ::op any?)
(s/def ::body any?)
(s/def ::fn any?)
(s/def ::Op (s/multi-spec op-multi-spec :op))
(s/fdef op-unq :args (s/cat) :ret ::Op)
(s/fdef single :args (s/cat) :ret any?)
;; End: Generated by clojure.core.typed - DO NOT EDIT
(defn unqualified []
  {:foo {:foo {:foo nil}}}
  )
(defn qualified []
  {::foo {::foo {::foo nil}}}
  )

(defn qualified2 []
  (rand-nth
    [{::foo2 {::foo nil}}
     {::foo2 {::foo {::foo1 :a}}}
     {::foo2 {::foo {::foo1 :b}}}
     {::foo2 {::foo {::foo1 :c}}}
     ])
  )

(defn same1 []
  {:blah 1
   :flag 2
   :opt1 2
   :opt2 3})

(defn same2 []
  {:blah 1
   :flag 2
   :opt1 2
   :opt3 3})

(defn single []
  {:a 1})

(defn op-unq []
  (rand-nth
    [{:op :fn 
      :context :expression
      :fn {:op :lambda 
           :context :statement
           :body {:op :val
                  :context :statement
                  :val 1}}}
     {:op :val
      :val 1}
     ])
  )

;(qualified)
;(doall (repeatedly 100 qualified2))
;(unqualified)
;(same1)
;(same2)
(single)
(doall (repeatedly 100 (comp #(walk/postwalk identity %) op-unq)))