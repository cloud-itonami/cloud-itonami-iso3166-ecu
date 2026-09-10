(ns marketentry.facts
  "Ecuador market-entry catalog.

  Every field traces to an independently-confirmed official source
  (WebFetch-verified this session, 2026-07-22/23):

    - Company oversight: Superintendencia de Compañías, Valores y
      Seguros (SCVS, https://www.gob.ec/scvs) -- the technical organism
      with administrative/economic autonomy that oversees company
      organization, activity, dissolution and liquidation, and approves
      capital increases, legal-representative changes, mergers/
      divisions, and statute reforms. Legal basis is the Ley de
      Compañías -- this catalog deliberately does NOT cite a specific
      Registro Oficial number for that law (not independently verified
      this session). NOTE: `supercias.gob.ec` (the agency's own domain)
      was unreachable this session -- `gob.ec/scvs` is the confirmed
      mirror; do not cite `supercias.gob.ec` as provenance.
    - Tax registration: Servicio de Rentas Internas (SRI,
      https://www.gob.ec/sri) -- the autonomous public institution
      established 2 December 1997 that administers internal taxes and
      coordinates with the Ministerio de Economía y Finanzas. RUC
      (Registro Único de Contribuyentes) is the taxpayer-registry
      identifier SRI issues; a legal entity needs one to register on
      SOCE. Also see
      https://es.wikipedia.org/wiki/Servicio_de_Rentas_Internas.
    - Public procurement: Servicio Nacional de Contratación Pública
      (SERCOP, https://www.gob.ec/sercop) -- self-described as the
      governing (rector), technical, regulatory, and autonomous entity
      of public contracting. Legal basis is the LOSNCP (Ley Orgánica
      del Sistema Nacional de Contratación Pública).
    - E-procurement portal: Sistema Oficial de Contratación Pública del
      Ecuador (SOCE, https://www.compraspublicas.gob.ec/
      ProcesoContratacion/compras/) -- operated by SERCOP since 2008
      (Acuerdo No. 012-2019). SOCE has SEPARATE registration tracks for
      national providers, foreign providers, and private-sector
      contractors -- this catalog deliberately preserves that
      three-track distinction rather than collapsing it into one
      generic 'supplier registration' required-evidence item.

  STRUCTURAL NOTE (do not contradict in code that reads this catalog):
  unlike the US federal split (GSA operates SAM.gov as a platform
  distinct from the FAR-Council-owned regulation), in Ecuador SERCOP is
  SIMULTANEOUSLY the regulator AND the platform operator. There is no
  separate 'Ecuador procurement platform operator' entity distinct from
  SERCOP -- never invent one.

  Explicitly NOT claimed here (fabrication traps this catalog avoids,
  per this session's research constraints): no foreign-investor /
  foreign-investment-registry fact (e.g. a Banco Central del Ecuador
  investment registry -- two direct-URL lookups both failed this
  session), no specific Ley de Compañías Registro Oficial number, no
  conflation of SCVS with the separate municipal-level Registro
  Mercantil or the notarial incorporation step -- only SCVS and SRI were
  verified as directly germane to a spec-basis fact.

  A jurisdiction not in `catalog` has NO spec-basis, full stop --
  extend `catalog`, never invent an owner-authority/legal-basis/URL.")

(def catalog
  {"ECU"
   {:name "Ecuador"
    :owner-authority "Servicio Nacional de Contratación Pública (SERCOP)"
    :legal-basis "Ley Orgánica del Sistema Nacional de Contratación Pública (LOSNCP)"
    :national-spec "Sistema Oficial de Contratación Pública del Ecuador (SOCE) -- e-procurement portal operated by SERCOP since 2008 (Acuerdo No. 012-2019); separate registration tracks for national providers, foreign providers, and private-sector contractors"
    :provenance "https://www.gob.ec/sercop ; https://www.compraspublicas.gob.ec/ProcesoContratacion/compras/"
    :required-evidence ["SCVS company-registration record (incorporation / capital-increase / legal-representative-change / statute-reform authorization under the Ley de Compañías)"
                         "SRI RUC (Registro Único de Contribuyentes) tax-registration record"
                         "SOCE provider-registration record (national-provider, foreign-provider, or private-sector-contractor track, per the engagement's own category)"]
    ;; legal-representative-change sub-schema -- mirrors the AGO
    ;; template's `:rep-*` triple, grounded in SCVS's approval
    ;; authority over company legal-representative changes (fact #1
    ;; above). This is the flagship check for this vertical: SCVS must
    ;; actually clear a legal-representative change before a filing
    ;; that depends on it is submitted.
    :rep-owner-authority "Superintendencia de Compañías, Valores y Seguros (SCVS)"
    :rep-legal-basis "Ley de Compañías -- SCVS approves company legal-representative changes as part of its oversight of company organization, activity, dissolution and liquidation"
    :rep-provenance "https://www.gob.ec/scvs"
    ;; corporate tax-id sub-schema -- mirrors the AGO template's
    ;; `:corporate-number-*` triple.
    :corporate-number-owner-authority "Servicio de Rentas Internas (SRI)"
    :corporate-number-legal-basis "RUC (Registro Único de Contribuyentes) -- issued by SRI, the autonomous public institution (est. 2 December 1997) administering internal taxes"
    :corporate-number-provenance "https://www.gob.ec/sri"
    ;; SERCOP-is-also-the-platform-operator structural note -- exposed
    ;; so no proposal ever invents a separate platform-operator entity
    ;; distinct from SERCOP (see namespace docstring).
    :platform-operator-note "SERCOP is simultaneously the procurement regulator (LOSNCP) AND the SOCE e-procurement platform operator -- no separate platform-operator body exists, unlike the US GSA/FAR-Council split"
    :platform-operator-provenance "https://www.gob.ec/sercop"}})

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
      :note "R0 catalog seed"})))

(defn required-evidence-satisfied? [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (= (count required-evidence) (count (filter (set submitted) required-evidence)))))

(defn evidence-checklist [iso3] (:required-evidence (spec-basis iso3) []))

(defn rep-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))

(defn corporate-number-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority :corporate-number-legal-basis :corporate-number-provenance]))))

(defn platform-operator-spec-basis
  "The SERCOP-is-also-the-platform-operator structural fact -- exposed
  so callers never invent a separate Ecuador procurement platform
  operator distinct from SERCOP."
  [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:platform-operator-note sb)
      (select-keys sb [:platform-operator-note :platform-operator-provenance]))))
