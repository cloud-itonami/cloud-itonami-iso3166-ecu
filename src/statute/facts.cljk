(ns statute.facts
  "General-law compliance catalog for Ecuador (ECU). Like
  cloud-itonami-iso3166-ury/-cri/-pan, this repo had no
  `marketentry.facts` implementation yet (blueprint-only) -- this is
  the FIRST code-bearing content in this repo, self-contained with its
  own deps.edn. Mirrors
  cloud-itonami-iso3166-jpn/-usa/-esp/-swe/-nor/-dnk/-fin/-prt/-bel/-bra/-mex/-chl/-arg/-zaf/-col/-ury/-cri/-pan's
  `statute.facts` (ADR-2607141700, cloud-itonami-compliance-fact-federation).

  Every entry cites an OFFICIAL gob.ec (Ecuador's own official
  government portal, 'Guía Oficial de Trámites y Servicios') URL --
  never fabricated. A law not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries."
  {"ECU"
   [{:statute/id "ecu.ley-companias-ro312"
     :statute/title "Ley de Compañías"
     :statute/jurisdiction "ECU"
     :statute/kind :law
     :statute/law-number "Registro Oficial N.º 312"
     :statute/url "https://www.gob.ec/regulaciones/ley-companias"
     :statute/url-provenance :official-gob-ec
     :statute/enacted-date "1999-11-05"
     :statute/retrieved-at "2026-07-16"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "ecu.ley-organica-proteccion-datos-personales-ro459"
     :statute/title "Ley Orgánica de Protección de Datos Personales"
     :statute/jurisdiction "ECU"
     :statute/kind :law
     :statute/law-number "Registro Oficial N.º 459"
     :statute/url "https://www.gob.ec/regulaciones/ley-organica-proteccion-datos-personales"
     :statute/url-provenance :official-gob-ec
     :statute/enacted-date "2021-05-26"
     :statute/retrieved-at "2026-07-16"
     :statute/topic #{:data-protection :privacy}}
    {:statute/id "ecu.codigo-trabajo-ro167"
     :statute/title "Código de Trabajo"
     :statute/jurisdiction "ECU"
     :statute/kind :law
     :statute/law-number "Registro Oficial N.º 167"
     :statute/url "https://www.gob.ec/regulaciones/codigo-trabajo"
     :statute/url-provenance :official-gob-ec
     :statute/enacted-date "2005-12-16"
     :statute/retrieved-at "2026-07-16"
     :statute/topic #{:labor :employment}}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-ecu statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "ECU")) " ECU statutes seeded with an "
                 "official gob.ec citation. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
