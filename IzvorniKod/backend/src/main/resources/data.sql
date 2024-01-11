DELETE FROM virtualno_natjecanje;
DELETE FROM rjesenje;
DELETE FROM testni_primjer;
DELETE FROM pehar;
DELETE FROM zadatak;
DELETE FROM natjecanje;
DELETE FROM korisnik;
DROP SEQUENCE korisnik_seq;
DROP SEQUENCE natjecanje_seq;
DROP SEQUENCE pehar_seq;
DROP SEQUENCE zadatak_seq;


CREATE SEQUENCE korisnik_seq
START 1
INCREMENT BY 1;

CREATE SEQUENCE natjecanje_seq
START 101
INCREMENT 1;

CREATE SEQUENCE zadatak_seq
START 1001
INCREMENT 1;


CREATE SEQUENCE pehar_seq
START 1011
INCREMENT 10;


INSERT INTO korisnik (korisnik_id, korisnicko_ime, ime, prezime, email, lozinka, uloga, vrijeme_registracije, confirmed_email, fotografija) 
VALUES
-- sifra za admina: 'adminSifra'
(nextval('korisnik_seq'), 'admin', 'Admin', 'Administrator', 'admin@bytepit.hr', '$2y$10$Hpi8VX8KT2MyLvdoCQKX9uBprCdU2sDsAt8gx1mrznSPKmrDwHv0W', 'ADMIN', CURRENT_TIMESTAMP, true, './src/main/resources/profilneSlike/placeHolder.png'),
-- sifra za spidermana: 'iLoveMJ'
(nextval('korisnik_seq'), 'spiderman', 'Miles', 'Morales', 'spidey@avengers.net', '$2y$10$TnCTVD9HyIRkTrv1dcfSTuwwBCHnwV07Q9By9GHqshfiqdJltdLt.', 'NATJECATELJ', CURRENT_TIMESTAMP, true, './src/main/resources/profilneSlike/placeHolder.png'),
-- sifra za hulka: 'greenMonster'
(nextval('korisnik_seq'), 'hulk', 'Bruce', 'Banner', 'hulk@avengers.net', '$2y$10$EQzrPL8ccKBY7SgXFYcRN.zxPmAhLOHoTgEXhWyHxGWIHYQWEjmXq', 'NATJECATELJ', CURRENT_TIMESTAMP, true, './src/main/resources/profilneSlike/placeHolder.png'),
-- sifra za buttlera: 'robin'
(nextval('korisnik_seq'), 'buttler', 'Alfred', 'Pennyworth', 'penny.alfred@justice.com', '$2y$10$q7MTDmHKnznsZBRR0Ho9N.TUEh2X4aV04V6QIrDLMY3lKfKc9.oVq', 'VODITELJ', CURRENT_TIMESTAMP, true, './src/main/resources/profilneSlike/placeHolder.png'),
-- sifra za batmana: 'harleyQueen'
(nextval('korisnik_seq'), 'batman', 'Bruce', 'Bayne', 'batman@justice.com', '$2y$10$P4eDgjwT4ykRFt74.fTwV.tIwaoLMMjunlGirBEtmNPsRrIcAcU5m', 'NATJECATELJ', CURRENT_TIMESTAMP, true, './src/main/resources/profilneSlike/placeHolder.png'),
-- sifra za furyja: 'catLover', ouch haha
(nextval('korisnik_seq'), 'eyepatch', 'Nick', 'Fury', 'nick.fury@shield.gov', '$2y$10$p4dWESqApfxgcJ3lqatdV.tY0/li8vPIaNC0TxIeDdEgm0tupjJkq', 'VODITELJ', CURRENT_TIMESTAMP, true, './src/main/resources/profilneSlike/placeHolder.png'),
-- sifra za missMinutes: 'kang<3'
(nextval('korisnik_seq'), 'missMinutes', 'Miss', 'Minutes', 'miss.minutes@tva.gov', '$2y$10$SkXSgI414ejZLRi9nq5bZeZjgYUA6JjRS1mbspjxrmseFEp.UpOb2', 'VODITELJ', CURRENT_TIMESTAMP, false, './src/main/resources/profilneSlike/placeHolder.png');


INSERT INTO natjecanje (natjecanje_id, naziv_natjecanja, korisnik_korisnik_id, pocetak_natjecanja, kraj_natjecanja) 
VALUES
(nextval('natjecanje_seq'), 'Endgame', '4', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP+'1 day'::INTERVAL),
(nextval('natjecanje_seq'), 'Justice League', '6', CURRENT_TIMESTAMP-'3 day'::INTERVAL, CURRENT_TIMESTAMP-'8 hour'::INTERVAL),
(nextval('natjecanje_seq'), 'Secret Wars', '4', CURRENT_TIMESTAMP+'2 day'::INTERVAL, CURRENT_TIMESTAMP+'4 day'::INTERVAL),
(nextval('natjecanje_seq'), 'Hunger Games', '6', CURRENT_TIMESTAMP-'1 day'::INTERVAL, CURRENT_TIMESTAMP+'1 day'::INTERVAL),
(nextval('natjecanje_seq'), 'Sprint', '6', CURRENT_TIMESTAMP-'1 day'::INTERVAL, CURRENT_TIMESTAMP+'1 minute'::INTERVAL);

INSERT INTO pehar (pehar_id, mjesto, natjecanje_natjecanje_id, natjecatelj_korisnik_id, slika_pehara)
VALUES
(nextval('pehar_seq'), 1, 101, NULL, './src/main/resources/trophyPlaceholder.png'),
(nextval('pehar_seq'), 1, 102, 3, './src/main/resources/trophyPlaceholder.png'),
(nextval('pehar_seq'), 1, 103, NULL, './src/main/resources/trophyPlaceholder.png'),
(nextval('pehar_seq'), 1, 104, NULL, './src/main/resources/trophyPlaceholder.png');



INSERT INTO zadatak (zadatak_id, naziv_zadatka, tekst_zadatka, broj_bodova, vremensko_ogranicenje, voditelj_korisnik_id, privatni_zadatak, tezina_zadatka)
VALUES
    (nextval('zadatak_seq'), 'Hello world', 'Napiši program koji ispisuje Hello world.', 10, 1, 4, true, 'RECRUIT'),
    (nextval('zadatak_seq'), 'Brojilica', 'Napiši program koji ispisuje sve prirodne brojeve do broja N.', 20, 1, 4, true, 'VETERAN'),
    (nextval('zadatak_seq'), 'Množilica', 'Napiši program koji množi dva upisana cijela broja.', 10, 1, 6, false, 'RECRUIT'),
    (nextval('zadatak_seq'), 'Fibbonaci', 'Ispiši prvih N brojeva iz Fibbonacijevog niza', 50, 2, 4, false, 'REALISM'),
    (nextval('zadatak_seq'), 'Jeka', 'Ispiši ono što je korisnik unio.', 20, 1, 6, false, 'VETERAN'),
    (nextval('zadatak_seq'), 'Konvertor', 'Za zadani iznos u kunama ispiši ekvivalentni iznos u eurima zaokružen na cijeli broj', 50, 1, 6, true, 'REALISM'),
    (nextval('zadatak_seq'), 'Kvadrat', 'Ispiši površinu kvadarata stranice a.', 50, 1, 4, true, 'REALISM');

INSERT INTO virtualno_natjecanje (natjecanje_id, korisnik_korisnik_id, orginalno_natjecanje_natjecanje_id, pocetak_natjecanja) 
VALUES
(nextval('natjecanje_seq'), 2, NULL, CURRENT_TIMESTAMP-'1 hour'::INTERVAL),
(nextval('natjecanje_seq'), 5, 102, CURRENT_TIMESTAMP-'1 minute'::INTERVAL);


ALTER SEQUENCE zadatak_seq RESTART WITH 1001;
INSERT INTO nadmetanje_zadaci (zadaci_zadatak_id, nadmetanje_natjecanje_id )
    VALUES
    (nextval('zadatak_seq'), 101),
    (nextval('zadatak_seq'), 102),
    (currval('zadatak_seq'), 106),
    (nextval('zadatak_seq'), 103),
    (nextval('zadatak_seq'), 103),
    (nextval('zadatak_seq'), 102),
    (currval('zadatak_seq'), 106),
    (nextval('zadatak_seq'), 104),
    (nextval('zadatak_seq'), 102),
    (currval('zadatak_seq'), 106),
    (1003, 105),
    (1005, 105),
    (1006, 105);




INSERT INTO rjesenje (rjesenje_rb, zadatak_zadatak_id, natjecatelj_korisnik_id, programski_kod, vrijeme_odgovora, broj_tocnih_primjera, natjecanje_natjecanje_id)
VALUES
(1, 1005, 3, '#include<bits/stdc++.h>\n int main() {int s; std::cin >> s; std::cout << s;}', CURRENT_TIMESTAMP-'2 day'::INTERVAL, 1, 102),
(2, 1005, 3, '#include<bits/stdc++.h>\n using namespace std;\n int main() {string s;\n cin >> s;\n cout << s;}', CURRENT_TIMESTAMP-'10 hour'::INTERVAL, 0.5, 102),
-- syntax error (prazno rjesenje) -> ne prolaz
(3, 1005, 5, null, CURRENT_TIMESTAMP-'16 hour'::INTERVAL, 0, 102),
(4, 1005, 4, '#include<bits/stdc++.h>\n int main() {int s; std::cin >> s; std::cout << s;}', CURRENT_TIMESTAMP-'1 day'::INTERVAL, 1, 101),
-- syntax error -> ne prolazi
(5, 1003, 5, '#include<bits/stdc++.h>\n int main() {int a, b; std::cin >> a >> b; std::cout << a*b', CURRENT_TIMESTAMP-'1 hour'::INTERVAL, 1, 105),
-- syntax error -> ne prolazi
(6, 1003, 4, '#include<bits/stdc++.h>\n int main() {int x, y; std::cin >> x >> y; std::cout << x*y', CURRENT_TIMESTAMP-'1 hour'::INTERVAL, 1, 102),
(7, 1006, 5, '#include<bits/stdc++.h>\n int main() {int kune; std::cin >> kune; std::cout << (int)kune/7.5345;}', CURRENT_TIMESTAMP-'1 hour'::INTERVAL, 1, NULL),
(8, 1005, 5, '#include<bits/stdc++.h>\n using namespace std;\n int main() {string s;\n cin >> s;\n cout << s;}', CURRENT_TIMESTAMP-'10 hour'::INTERVAL, 1, 106);



INSERT INTO testni_primjer (testni_primjer_rb, zadatak_zadatak_id, ulazni_podaci, izlazni_podaci) 
VALUES
(1, 1001, null, 'Hello world'),
(1, 1002, '5', '1 2 3 4 5'),
(1, 1003, '5 2', '10'),
(1, 1004, '3', '1 1 2'),
(1, 1005, '3', '3'),
(2, 1005, 'jeka', 'jeka'),
(1, 1006, '75345', '10000');



