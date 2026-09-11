(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest ecu-has-spec-basis
  (let [sb (facts/spec-basis "ECU")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (= 3 (count (:required-evidence sb))) "SCVS + SRI + SOCE, three distinct required-evidence items")
    (is (some? (facts/rep-spec-basis "ECU")))
    (is (some? (facts/corporate-number-spec-basis "ECU")))
    (is (some? (facts/platform-operator-spec-basis "ECU")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "ECU")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "ECU" all)))
    (is (not (facts/required-evidence-satisfied? "ECU" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["ECU" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 1 (:covered c)))
    (is (= ["ECU"] (:covered-jurisdictions c)))
    (is (= ["ATL" "USA"] (:missing-jurisdictions c)))))

(deftest sercop-is-simultaneously-regulator-and-platform-operator
  (testing "structural note -- never invent a separate Ecuador procurement platform operator distinct from SERCOP"
    (let [note (facts/platform-operator-spec-basis "ECU")]
      (is (some? note))
      (is (re-find #"(?i)SERCOP" (:platform-operator-note note)))
      (is (re-find #"(?i)simultaneously" (:platform-operator-note note))))))

(deftest rep-spec-basis-cites-scvs
  (let [rep (facts/rep-spec-basis "ECU")]
    (is (re-find #"(?i)SCVS|Superintendencia" (:rep-owner-authority rep)))
    (is (= "https://www.gob.ec/scvs" (:rep-provenance rep)))))

(deftest corporate-number-spec-basis-cites-sri
  (let [cn (facts/corporate-number-spec-basis "ECU")]
    (is (re-find #"(?i)SRI|Servicio de Rentas" (:corporate-number-owner-authority cn)))
    (is (= "https://www.gob.ec/sri" (:corporate-number-provenance cn)))))
