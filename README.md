II sistema deve permettere l'emissione dei biglietti, sia da distributori automatici che da rivenditori autorizzati,

oltre che Ilemissione di abbonamenti settimanali e mensili di tipo nominativo ad utenti dotati di tessera.

La tessera ha validitå annuale e deve essere rinnovata alla scadenza.

Ogni biglietto ed abbonamento é identificato da un codice univoco.

Deve essere possibile tenere traccia del numero di biglietti e/o abbonamenti emessi in un dato periodo di tempo in totale e per punto di emissione.

I distributori automatici possono essere attivi o fuori servizio.l

Occorre inoltre permettere la verifica rapida della validitå di un abbonamento in base al numero di tessera dell'utente controllato.

Documenti di viaggio

- id: UUID
- data_emissione
- distributore_id

Abbonamenti

- tessera_id
- data_scadenza: calcolato data_emissione + 7 = scadenza -> query scadenza < now()
- tipo [SETTIMANALE, MENSILE]

Biglietti - data_vidimazione - veicolo_id

Distributori
id: UUID
tipo: [AUTOMATICI, RIVENDITORI]
attivo:

Utenti
id: UUID (tessera)
nome:
cognome:
data_inizio:
data_scadenza: calcolato -> data_inizio + 12

Quando un biglietto viene vidimato su un
mezzo, esso deve essere annullato e deve essere possibile acquisire il numero di biglietti vidimati su un
particolare mezzo o in totale in un periodo di tempo.

II sistema deve inoltre prevedere la gestione del parco mezzi. I mezzi possono essere tram o autobus ed avere
una certa capienza in base al tipo di mezzo. Ogni mezzo pub essere in servizio o in manutenzione ed occorre
tenere traccia dei periodi di servizio o manutenzione di ogni mezzo.

Veicoli

- id: UUID
- tipo:[TRAM,AUTOBUS]
- capienza 50 o 60 settato in base al tipo passato al costruttore
- tratta_id
- stato: servizio o manutenzione ??????

Manutenzioni - id:UUID - data_inizio: - data_fine: - veicolo_id

Ogni mezzo in servizio puö essere assegnato ad una tratta, che é caratterizzata da una zona di partenza, un
capolinea ed un tempo medio di percorrenza. Occorre tenere traccia del numero di volte che un mezzo percorre
una tappa e del tempo effettivo di percorrenza di ogni tratta.

Tratte - id: UUID - partenza: - capolinea: - t_percorrenza_medio: calcolato in base alle percorrenze

Percorrenze - id: UUID - veicolo_id: - tratta_id: - ora_partenza - ora_arrivo - t_percorrenza_effettivo: calcolato ora_arrivo - ora_partenza# BE-U1-BW

II sistema deve permettere l'emissione dei biglietti, sia da distributori automatici che da rivenditori autorizzati,
oltre che Ilemissione di abbonamenti settimanali e mensili di tipo nominativo ad utenti dotati di tessera. La tesser
ha validitå annuale e deve essere rinnovata alla scadenza. Ogni biglietto ed abbonamento é identificato da un
codice univoco. Deve essere possibile tenere traccia del numero di biglietti e/o abbonamenti emessi in un dato
periodo di tempo in totale e per punto di emissione. I distributori automatici possono essere attivi o fuori servizio.l
Occorre inoltre permettere la verifica rapida della validitå di un abbonamento in base al numero di tessera
dell'utente controllato.

II sistema deve inoltre prevedere la gestione del parco mezzi. I mezzi possono essere tram o autobus ed avere
una certa capienza in base al tipo di mezzo. Ogni mezzo pub essere in servizio o in manutenzione ed occorre
tenere traccia dei periodi di servizio o manutenzione di ogni mezzo.

Quando un biglietto viene vidimato su un
mezzo, esso deve essere annullato e deve essere possibile acquisire il numero di biglietti vidimati su un
particolare mezzo o in totale in un periodo di tempo.

Veicoli

- id: UUID
- tipo:[TRAM,AUTOBUS]
- capienza
- tratta

Manutenzioni - id:UUID - data_inizio: - data_fine: - veicolo

Ogni mezzo in servizio puö essere assegnato ad una tratta, che é caratterizzata da una zona di partenza, un
capolinea ed un tempo medio di percorrenza. Occorre tenere traccia del numero di volte che un mezzo percorre
una tappa e del tempo effettivo di percorrenza di ogni tratta.

Tratte - id: UUID - partenza: - capolinea: - t_percorrenza_medio: calcolato

Percorrenze - veicolo_id: - tratta_id: - ora_partenza - ora_arrivo - t_percorrenza_effettivo: ora_arrivo - ora_partenza
