(ns culture.facts
  "Country-level regional-culture catalog for Ecuador (ECU) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"ECU"
   [{:culture/id "ecu.dish.encebollado"
     :culture/name "Encebollado"
     :culture/country "ECU"
     :culture/kind :dish
     :culture/summary "Onion-dressed fish stew from Ecuador, where it is regarded as a national dish, particularly popular in the coastal regions."
     :culture/url "https://en.wikipedia.org/wiki/Encebollado"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "ecu.dish.fanesca"
     :culture/name "Fanesca"
     :culture/country "ECU"
     :culture/kind :dish
     :culture/summary "Soup traditionally prepared only on Good Friday and eaten by households and communities in Ecuador as a Holy Week tradition."
     :culture/url "https://en.wikipedia.org/wiki/Fanesca"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "ecu.dish.hornado"
     :culture/name "Hornado"
     :culture/country "ECU"
     :culture/kind :dish
     :culture/summary "Whole roast pig of Ecuadorian cuisine, commonly served in highland markets with traditional side dishes."
     :culture/url "https://en.wikipedia.org/wiki/Hornado"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "ecu.dish.llapingacho"
     :culture/name "Llapingacho"
     :culture/country "ECU"
     :culture/kind :dish
     :culture/summary "Fried potato pancakes stuffed with cheese that originated in Ambato, Ecuador, typically served with pork, avocado and salad."
     :culture/url "https://en.wikipedia.org/wiki/Llapingacho"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "ecu.beverage.colada-morada"
     :culture/name "Colada morada"
     :culture/country "ECU"
     :culture/kind :beverage
     :culture/summary "Thick purple beverage prepared with typical fruits of Ecuador, spices and corn flour, traditionally consumed on 2 November for All Souls' Day / Day of the Dead."
     :culture/url "https://en.wikipedia.org/wiki/Colada_morada"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "ecu.craft.panama-hat"
     :culture/name "Panama hat"
     :culture/country "ECU"
     :culture/kind :craft
     :culture/summary "Traditional brimmed straw hat of Ecuadorian origin, also called a toquilla straw hat; the art of weaving it was added to the UNESCO Intangible Cultural Heritage Lists in 2012."
     :culture/url "https://en.wikipedia.org/wiki/Panama_hat"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "ecu.festival.mama-negra"
     :culture/name "Mama Negra"
     :culture/country "ECU"
     :culture/kind :festival
     :culture/summary "Traditional festival held twice a year in Latacunga, Cotopaxi Province, Ecuador, honouring the Virgin of Mercy with parades blending indigenous, Spanish and African elements."
     :culture/url "https://en.wikipedia.org/wiki/Mama_Negra"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "ecu.heritage.quito"
     :culture/name "Historic centre of Quito"
     :culture/name-local "Quito"
     :culture/country "ECU"
     :culture/kind :heritage
     :culture/summary "Capital of Ecuador whose historic centre was designated a UNESCO World Heritage Site in 1978, among the first sites so declared."
     :culture/url "https://en.wikipedia.org/wiki/Quito"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "ecu.heritage.galapagos"
     :culture/name "Galápagos Islands"
     :culture/name-local "Islas Galápagos"
     :culture/country "ECU"
     :culture/kind :heritage
     :culture/summary "Archipelago forming the Galápagos Province of the Republic of Ecuador, designated a UNESCO World Heritage Site in 1978."
     :culture/url "https://en.wikipedia.org/wiki/Gal%C3%A1pagos_Islands"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

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
      :note (str "cloud-itonami-iso3166-ecu culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "ECU"))
                 " ECU entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
