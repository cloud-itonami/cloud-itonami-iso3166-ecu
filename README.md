# cloud-itonami-iso3166-ecu

Open ISO 3166 Blueprint for **ECU**: Ecuador -- **`:implemented`**.

This repository designs **and implements** a forkable OSS business for
an independent public-sector market-entry consultant: an already-
incorporated operator (e.g. a `cloud-itonami-cofog-{code}`,
`cloud-itonami-isco-{code}`, `cloud-itonami-unspsc-{segment}` or
`cloud-itonami-{ISIC}` blueprint fork) gets a **MarketEntry-LLM**
Compliance Advisor + independent **Market-Entry Compliance Governor**
to navigate public-procurement registration, local business/tax
registration, and local-content rules in Ecuador, so the operator
can win and service a government contract without hiring a full in-house
compliance department.

## Checks

Six checks, in priority order, evaluated by `marketentry.governor` on
every `MarketEntry-LLM` proposal. All six are HARD violations a human
approver cannot override; double-actuation guards are counted
separately. The confidence/actuation gate (item 6) is SOFT -- but see
Actuation below, `:filing/draft`/`:filing/submit` never auto-commit
regardless.

| # | Check | Grounds | Source |
|---|---|---|---|
| 1 | **Spec-basis** -- a `:jurisdiction/assess`/`:filing/draft`/`:filing/submit` proposal must cite an official source, never an invented one | `marketentry.facts/spec-basis` | gob.ec/sercop, gob.ec/scvs, gob.ec/sri, compraspublicas.gob.ec |
| 2 | **Evidence incomplete** -- for draft/submit, the jurisdiction's full required-evidence checklist must be on file: (a) SCVS company-registration record (incorporation / capital-increase / legal-representative-change / statute-reform authorization under the Ley de Compañías), (b) SRI RUC tax-registration record, (c) SOCE provider-registration record on the applicable track (national-provider / foreign-provider / private-sector-contractor) | `marketentry.facts/required-evidence-satisfied?` | SCVS; SRI; SERCOP (LOSNCP) / SOCE |
| 3 | **SCVS legal-rep-change unapproved** (flagship) -- for submit, INDEPENDENTLY verify `:legal-rep-change-approved?` when the engagement declares `:requires-legal-rep-change? true` | `marketentry.governor/legal-rep-change-unapproved-violations` | Superintendencia de Compañías, Valores y Seguros (SCVS) -- approves company legal-representative changes as part of its oversight of company organization/activity/dissolution/liquidation, per the Ley de Compañías |
| 4 | **Engagement fee mismatch** -- for submit, independently recompute `claimed-fee = base-fee + monthly-rate x monitoring-months` | `marketentry.registry/engagement-fee-matches-claim?` | ground-truth recompute (fleet-standard discipline) |
| 5 | **RUC unverified** -- for submit, INDEPENDENTLY check `:ruc-verified?` when the engagement declares `:requires-ruc? true` | `marketentry.governor/ruc-unverified-violations` | SRI RUC (Registro Único de Contribuyentes) -- issued by the Servicio de Rentas Internas, autonomous public institution est. 2 Dec 1997 |
| 6 | **Confidence floor / actuation gate** (SOFT) -- LLM confidence below 0.6, or the op is `:filing/draft`/`:filing/submit` -> escalate to human | `marketentry.governor/check` | this vertical's own Trust Controls (`docs/business-model.md`) |

Two further double-actuation guards (`already-drafted`,
`already-submitted`) refuse to draft or submit the SAME engagement
twice, enforced off dedicated `:drafted?`/`:submitted?` booleans, never
a `:status` value.

**Structural note carried in `marketentry.facts`, not a governor
check:** unlike the US federal split (GSA operates SAM.gov as a
platform distinct from the FAR-Council-owned regulation), Ecuador's
SERCOP is SIMULTANEOUSLY the procurement regulator (LOSNCP) AND the
SOCE e-procurement platform operator. `marketentry.facts/
platform-operator-spec-basis` exposes this so no proposal ever invents
a separate "Ecuador procurement platform operator" entity distinct
from SERCOP; see `test/marketentry/facts_test.kotoba`'s
`sercop-is-simultaneously-regulator-and-platform-operator`.

SOCE also runs SEPARATE registration tracks for national providers,
foreign providers, and private-sector contractors -- `marketentry.facts`
deliberately preserves this three-track distinction as its own
required-evidence item rather than collapsing it into one generic
"supplier registration" line.

## Actuation

**Drafting a real SOCE registration package and submitting a real SOCE
registration are never autonomous, at any phase, by construction.** Two
independent layers enforce this:

- `marketentry.governor`'s `high-stakes` set
  (`#{:actuation/draft-filing :actuation/submit-filing}`) always
  escalates, regardless of confidence.
- `marketentry.phase`'s phase table (`phase 0` through `phase 3`)
  never puts `:filing/draft` or `:filing/submit` in any phase's
  `:auto` set -- see `marketentry.phase`'s own docstring and
  `test/marketentry/phase_test.kotoba`'s `filing-submit-never-auto`, plus
  `test/marketentry/governor_contract_test.kotoba`'s
  `filing-draft-and-submit-never-auto-commit`.

The actor may intake an engagement, assess a jurisdiction and draft a
recommendation; a human market-entry operator is always the one who
actually files a draft or a submission. Grounded directly in this
blueprint's own [`docs/business-model.md`](docs/business-model.md) and
`marketentry.governor`'s own namespace docstring, which names this
vertical's Trust Controls verbatim: "any actual portal registration or
filing submission requires Market-Entry Compliance Governor clearance
and always escalates to human sign-off"; "a false or fabricated
regulatory-requirement claim is a HARD hold". `:filing/draft` and
`:filing/submit` apply SEQUENTIALLY to the SAME engagement record
(draft first, submit later) -- matching every sibling
`market-entry-compliance-governor` actor's own sequential shape.

## No robotics premise — digital/data service exemption

Market-entry and procurement-compliance navigation is a pure data/software
service with no physical-domain work (portal registration, document
checklists, regulatory-change monitoring) — the same exemption class as
`cloud-itonami-6310` (HR SaaS replacement) and `cloud-itonami-gtin-*`.
`blueprint.edn` sets `:itonami.blueprint/robotics false` and
`:required-technologies` lists only real capabilities (`:identity`,
`:forms`, `:dmn`, `:bpmn`, `:audit-ledger`), no `:robotics`.

## Core Contract

```text
operator intake + prior filing history
        |
        v
Compliance Advisor -> Market-Entry Compliance Governor -> filing draft, or human sign-off
        |
        v
gated portal registration / filing submission + audit ledger
```

No automated proposal can submit a portal registration or filing the
governor refuses, suppress a compliance record, or claim a legal/tax
conclusion the governor has not cleared. `:filing/submit` is never in any
phase's `:auto` set — it always requires human sign-off (mirrors
`cloud-itonami-M6910`'s `filing-submit-never-auto-at-any-phase`
invariant).

## What this is NOT

- **Not the government of Ecuador.** See
  [`docs/business-model.md`](docs/business-model.md) for the boundary with
  `com-etzhayyim-ooyake` (read-only civic mirror), `matsurigoto` (sovereign
  statecraft), `com-etzhayyim-toritsugi` (individual citizen concierge),
  `legal-entity.etzhayyim.com` (read-only data aggregation), and
  `cloud-itonami-M6910` (company incorporation — a different regulatory
  phase this blueprint assumes is already complete).
- **Not legal or tax advice.** Every regulatory claim must cite the
  official source and route final filings to Ecuadorian-licensed counsel
  or a registered agent where the law requires licensed representation.

## Capability layer

Resolves via [`kotoba-lang/iso3166`](https://github.com/kotoba-lang/iso3166)
(ISO 3166 `ECU`). Required capabilities:

- :identity
- :forms
- :dmn
- :bpmn
- :audit-ledger

See [`docs/business-model.md`](docs/business-model.md) and
[`docs/operator-guide.md`](docs/operator-guide.md).

## Run

```bash
clojure -M:dev:run     # walk a clean intake -> assess -> draft -> submit lifecycle, plus HARD-hold scenarios
clojure -M:dev:test    # governor contract · phase invariants · store parity · registry conformance · facts coverage
clojure -M:lint        # clj-kondo (errors fail; CI mirrors this)
```

## License

AGPL-3.0-or-later.

## Market-entry / statute catalogs

Governed public-sector market-entry compliance actor, same architecture
as the other `cloud-itonami-iso3166-*` siblings:

- `src/marketentry/{facts,governor,phase,sim,operation,registry,store,
  marketentryllm}.cljc` -- the actor. `facts.cljc` cites exactly 4
  independently WebFetch-verified official sources this session: SCVS
  (Superintendencia de Compañías, Valores y Seguros,
  https://www.gob.ec/scvs -- company oversight, including approval of
  legal-representative changes; `supercias.gob.ec` was unreachable this
  session and is deliberately NOT cited), SRI (Servicio de Rentas
  Internas, https://www.gob.ec/sri -- RUC tax registration), SERCOP
  (Servicio Nacional de Contratación Pública,
  https://www.gob.ec/sercop -- procurement regulator under the LOSNCP),
  and SOCE (Sistema Oficial de Contratación Pública del Ecuador,
  https://www.compraspublicas.gob.ec/ProcesoContratacion/compras/ --
  the e-procurement portal SERCOP has operated since 2008). No
  foreign-investment-registry claim is made (two direct-URL lookups for
  a Banco Central del Ecuador investment registry both failed this
  session) and no specific Ley de Compañías Registro Oficial number is
  cited (not independently verified). `governor.cljc`'s flagship check
  independently verifies that a declared SCVS legal-representative
  change has actually been approved before a `:filing/submit` proceeds
  -- a check shape genuinely different from siblings whose flagship
  check is an unconditional resident-representative requirement or a
  sector-conditional constitutional restriction: this one is grounded
  in a specific regulator's own approval authority over ONE corporate
  event (legal-representative change), independently re-verified rather
  than trusted from the engagement's own claim (see the namespace
  docstrings and `test/marketentry/governor_contract_test.kotoba`'s
  `legal-rep-change-unapproved-is-held-and-unoverridable`).
- `src/statute/facts.kotoba` -- general-law catalog (pre-existing, not
  modified by this Wave): Ley de Compañías (R.O. 312), Ley Orgánica de
  Protección de Datos Personales (R.O. 459), and Código de Trabajo
  (R.O. 167), all cited via official gob.ec.

Every marketentry citation is WebFetch-verified against an official
source this session (gob.ec/scvs, gob.ec/sri, gob.ec/sercop,
compraspublicas.gob.ec) or traced to the verified-facts brief this Wave
was built from; `supercias.gob.ec` was unreachable this session and is
named explicitly rather than guessed at -- see `marketentry.facts`'s
own docstring for the full honest disclosure of which citation is a
live-verified URL vs. a deliberately-avoided unreachable one.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Ecuador:

- `src/culture/facts.kotoba` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
