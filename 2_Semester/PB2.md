# Podatkovne baze II

1. kolkvij 6 teden
    kolokvij je zadnji termin predavanji

Oba dva morate narediti 50%

Optimizacija (2-3 teden)

NoSQL (4-6 teden)

BC (7-10 teden)

- DDL → CREATE, ALTER, DROP
- DML → SELECT, JOIN, INSERT, UPDATE
- TCL → START, COMMIT, ROLLBACK

Conceptualni Model → ER Diagram (Struktura)

Logični Model →  ER Diagram (Podatkovni tipi)

Fizični Model → Ustvarjanje tabele

Optimizacija:

- Pravilno modeliranje

- Pravilno pisanje SQL

- Indeksiranje

-De normalizacija

### Indeksiranje

Vsak PRIMARY KEY ali FOREIGN KEY

Če database dosti išče recimo ime oz n

#TODO look up Binary search sql engine

## Predavanje 2

1. Development machine - localhost

2. Production - Na enem serveru koda in podatkovna baza **ne uporabljati root**

3. Dedicated machine - 1 Server podatkovna baza drug server koda. **ne uporabljati root**.

Laptop, in oba serverja sta del LAN networka

En user je application A user (Server kjer je koda je application A), ki dobi access do relevantne podatkovne baze. Ko damo GRANT PRIVILAGES **ne sme** biti IDENTIFIED BY % ta znak pomeni da lahko nekdo iz katerega koli IP addressa lahko poveze na nas server (seveda mora vedeti username in password BRUTE FORCE ATTACK). Namesto tega naredimo identified by ip address.

Če imamo kakšno drugo aplikacijo naprimer eno Java eno .net (C#) lahko uporabimo isti server za hosting 2 podatkovni bazi.

SQL je standard ampak vsak ponudnik ima svoje posebnosti 90% je the same.

### Ne strukturne podatkovne zahteve

Zadrževanje:

- kako dolgo je treba hraniti podatke
- ali jih je potrebno brisati?

Backup - kopija vseh podatkov v production bazi

Arhiv - vsi podatki ki se zbrisejo iz podatkovne baze se podatki dajo v arhiv (ponavadi po nekem casovnem intervalu "*Horizontal slicing*" torej arhiviramo row's after a certain time)

Volumen:

- pričakovano število vrstic na posamezni tabeli na začetku
- pričakovano število vrstic na posamezni tabeli po določenem času

Razporožljivost:

- ali je dostopnost do podatko konstanto zahtevana
- kako dolgo in kako pogosto so lahko podatki nedostopni (vzdrževanje, replikacija)

Svežina:

- kako sveži podatki morajo biti za tiste, ki poizvedujejo po teh (posodabljanje, poizvedovanje)

Varnost:

- kdo naj bi imel dostop do PB

### Fizično modeliranje (NA KOLOKVIJU !!!!)

TRANSACTION

študent -> boni -> ponudnik -> transaction

SELECT UPDATE INSERT DELETE

SUID MATRIKA:

| Poraba bonov | SELECT | UPDATE | INSERT | DELETE |
| ----------- |--------|--------|--------|--------|
| Študent       | true |--------|--------|--------|
| Boni       | true |--------|--------|--------|
| Ponudnik       | true |--------|--------|--------|
| Transaction       |--------|--------|true |--------|

| Reg študentov | SELECT | UPDATE | INSERT | DELETE |
| ----------- |--------|--------|--------|--------|
| Študent       | true |--------|true|--------|
| Boni       | --------|--------|true|--------|
| Ponudnik       |-------- |--------|--------|--------|
| Transaction       |--------|--------|-------- |--------|

Metki nakažemo 50$

0. `START TRANSACTION`
1. `SELECT metka`
2. `SELECT balance FROM račun WHERE jaz`
3. `UPDATE balance  -50 WHERE jaz`
4. `UPDATE balance FROM račun WHERE Metka`
5. `INSERT INTO transaction`
6. `COMMIT TRANSACTION`

S tem ko mi naredimo START TRANSACTION si računalnik začne delat spremembe v RAM memory in potem obstajajo neki rollbacki.

Recimo da moremo narediti aplikacijo za bone:
Začnemo z use case diagramom kjer lahko preprosto vidim koliko uporabnikov bo imela posamezna transakcij. Nato lahko nastavim indexe za optimizacijo.

Nato naredimo še sequence diagram in deployment diagram **Look it up**

Uporaba omejitve CHECK alternativa TRIGGER
