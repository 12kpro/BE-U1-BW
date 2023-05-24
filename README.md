II sistema deve permettere l'emissione dei biglietti, sia da distributori automatici che da rivenditori autorizzati,

oltre che Ilemissione di abbonamenti settimanali e mensili di tipo nominativo ad utenti dotati di tessera.

La tessera ha validitå annuale e deve essere rinnovata alla scadenza. 

```
select *, (datainizio + 365) < now()  as scaduta from utenti u where (datainizio + 365) < now() ;
```

Ogni biglietto ed abbonamento é identificato da un codice univoco.

Deve essere possibile tenere traccia del numero di biglietti e/o abbonamenti emessi in un dato periodo di tempo in totale e per punto di emissione.

```
select tipo_documento,distributoreid_id , count(*)  as numero
from documenti_viaggio
where dataemissione  BETWEEN '2023-04-01' AND '2023-05-31'
group by tipo_documento, distributoreid_id
```

I distributori automatici possono essere attivi o fuori servizio.l

Occorre inoltre permettere la verifica rapida della validitå di un abbonamento in base al numero di tessera dell'utente controllato.

```
select * from (select *,
    CASE
        WHEN dv.tipo = 'SETTIMANALE' THEN dv.dataemissione + 7 < now()
        WHEN dv.tipo = 'MENSILE' THEN dv.dataemissione + 30 < now()
    END AS scadenza
from documenti_viaggio dv join utenti u on dv.tesseraid_id = u.id
) s
where scadenza
```

Quando un biglietto viene vidimato su un
mezzo, esso deve essere annullato e deve essere possibile acquisire il numero di biglietti vidimati su un
particolare mezzo o in totale in un periodo di tempo.
Conteggio per veiocolo

```
select count(*) as totale  from documenti_viaggio dv where datavidimazione is not null and dv.veicoloid_id = '79df7151-ee6d-41fa-a9f4-d694fe9a7f45'
```

Conteggio per periodo

```
select count(*) as totale  from documenti_viaggio dv where datavidimazione is not null and dv.datavidimazione  BETWEEN '2023-04-01' AND '2023-04-30'
```

II sistema deve inoltre prevedere la gestione del parco mezzi. I mezzi possono essere tram o autobus ed avere
una certa capienza in base al tipo di mezzo. Ogni mezzo pub essere in servizio o in manutenzione ed occorre
tenere traccia dei periodi di servizio o manutenzione di ogni mezzo.

Ogni mezzo in servizio puö essere assegnato ad una tratta, che é caratterizzata da una zona di partenza, un
capolinea ed un tempo medio di percorrenza. Occorre tenere traccia del numero di volte che un mezzo percorre
una tappa e del tempo effettivo di percorrenza di ogni tratta.

numero percorrenze per tratta per veicolo

```
select trattaid_id,veicoloid_id , count(trattaid_id) as numero from percorrenze p
where oraarrivo is not null
group by trattaid_id, veicoloid_id
```

tempo di percorrenza tratta per veicolo:

```
select trattaid_id ,veicoloid_id , sum(EXTRACT(EPOCH FROM (oraarrivo - orapartenza)))  as percorrenza from percorrenze p
where oraarrivo is not null
group by trattaid_id, veicoloid_id
```

tempo di percorrenza medio per tratta

```
select trattaid_id, sum(EXTRACT(EPOCH FROM (oraarrivo - orapartenza)))/count(trattaid_id)  as percorrenzamedia from percorrenze p
where oraarrivo is not null
group by trattaid_id
```
Esempio metodo e query con parametro/i:

```
   public List<Prestito>  findByTesseraUtente(String t) {
        Query q = em.createQuery("SELECT p FROM Prestito p JOIN Utente u on p.utente=u.id WHERE u.numeroTessera = :t");
        q.setParameter("t", UUID.fromString(t));
        return q.getResultList();
    }
```    

TODO:
- [ ] Testare metodi delle query
- [ ] Testare metodi delete
- [ ] Fare Prompt