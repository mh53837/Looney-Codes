--
-- PostgreSQL database dump
--

-- Dumped from database version 15.5
-- Dumped by pg_dump version 15.5 (Ubuntu 15.5-1.pgdg22.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: bytepit_user
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO bytepit_user;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: korisnik; Type: TABLE; Schema: public; Owner: bytepit_user
--

CREATE TABLE public.korisnik (
    confirmed_email boolean NOT NULL,
    korisnik_id integer NOT NULL,
    vrijeme_registracije timestamp(6) without time zone,
    email character varying(255) NOT NULL,
    fotografija character varying(255),
    ime character varying(255),
    korisnicko_ime character varying(255) NOT NULL,
    lozinka character varying(255),
    prezime character varying(255),
    requested_uloga character varying(255),
    uloga character varying(255),
    CONSTRAINT korisnik_requested_uloga_check CHECK (((requested_uloga)::text = ANY ((ARRAY['ADMIN'::character varying, 'VODITELJ'::character varying, 'NATJECATELJ'::character varying])::text[]))),
    CONSTRAINT korisnik_uloga_check CHECK (((uloga)::text = ANY ((ARRAY['ADMIN'::character varying, 'VODITELJ'::character varying, 'NATJECATELJ'::character varying])::text[])))
);


ALTER TABLE public.korisnik OWNER TO bytepit_user;

--
-- Name: korisnik_seq; Type: SEQUENCE; Schema: public; Owner: bytepit_user
--

CREATE SEQUENCE public.korisnik_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.korisnik_seq OWNER TO bytepit_user;

--
-- Name: nadmetanje_zadaci; Type: TABLE; Schema: public; Owner: bytepit_user
--

CREATE TABLE public.nadmetanje_zadaci (
    nadmetanje_natjecanje_id integer NOT NULL,
    zadaci_zadatak_id integer NOT NULL
);


ALTER TABLE public.nadmetanje_zadaci OWNER TO bytepit_user;

--
-- Name: natjecanje; Type: TABLE; Schema: public; Owner: bytepit_user
--

CREATE TABLE public.natjecanje (
    korisnik_korisnik_id integer,
    natjecanje_id integer NOT NULL,
    kraj_natjecanja timestamp(6) without time zone,
    pocetak_natjecanja timestamp(6) without time zone,
    naziv_natjecanja character varying(255) NOT NULL
);


ALTER TABLE public.natjecanje OWNER TO bytepit_user;

--
-- Name: natjecanje_seq; Type: SEQUENCE; Schema: public; Owner: bytepit_user
--

CREATE SEQUENCE public.natjecanje_seq
    START WITH 101
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.natjecanje_seq OWNER TO bytepit_user;

--
-- Name: pehar; Type: TABLE; Schema: public; Owner: bytepit_user
--

CREATE TABLE public.pehar (
    mjesto integer NOT NULL,
    natjecanje_natjecanje_id integer,
    natjecatelj_korisnik_id integer,
    pehar_id integer NOT NULL,
    slika_pehara character varying(255)
);


ALTER TABLE public.pehar OWNER TO bytepit_user;

--
-- Name: pehar_seq; Type: SEQUENCE; Schema: public; Owner: bytepit_user
--

CREATE SEQUENCE public.pehar_seq
    START WITH 1011
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pehar_seq OWNER TO bytepit_user;

--
-- Name: rjesenje; Type: TABLE; Schema: public; Owner: bytepit_user
--

CREATE TABLE public.rjesenje (
    broj_tocnih_primjera double precision NOT NULL,
    natjecanje_natjecanje_id integer,
    natjecatelj_korisnik_id integer NOT NULL,
    rjesenje_rb integer NOT NULL,
    zadatak_zadatak_id integer NOT NULL,
    vrijeme_odgovora timestamp(6) without time zone,
    programski_kod text
);


ALTER TABLE public.rjesenje OWNER TO bytepit_user;

--
-- Name: testni_primjer; Type: TABLE; Schema: public; Owner: bytepit_user
--

CREATE TABLE public.testni_primjer (
    testni_primjer_rb integer NOT NULL,
    zadatak_zadatak_id integer NOT NULL,
    izlazni_podaci character varying(255),
    ulazni_podaci character varying(255)
);


ALTER TABLE public.testni_primjer OWNER TO bytepit_user;

--
-- Name: virtualno_natjecanje; Type: TABLE; Schema: public; Owner: bytepit_user
--

CREATE TABLE public.virtualno_natjecanje (
    korisnik_korisnik_id integer,
    natjecanje_id integer NOT NULL,
    orginalno_natjecanje_natjecanje_id integer,
    pocetak_natjecanja timestamp(6) without time zone
);


ALTER TABLE public.virtualno_natjecanje OWNER TO bytepit_user;

--
-- Name: virtualno_natjecanje_seq; Type: SEQUENCE; Schema: public; Owner: bytepit_user
--

CREATE SEQUENCE public.virtualno_natjecanje_seq
    START WITH 100001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.virtualno_natjecanje_seq OWNER TO bytepit_user;

--
-- Name: zadatak; Type: TABLE; Schema: public; Owner: bytepit_user
--

CREATE TABLE public.zadatak (
    broj_bodova integer NOT NULL,
    privatni_zadatak boolean NOT NULL,
    voditelj_korisnik_id integer,
    vremensko_ogranicenje integer NOT NULL,
    zadatak_id integer NOT NULL,
    naziv_zadatka character varying(255) NOT NULL,
    tekst_zadatka text,
    tezina_zadatka character varying(255),
    CONSTRAINT zadatak_tezina_zadatka_check CHECK (((tezina_zadatka)::text = ANY ((ARRAY['RECRUIT'::character varying, 'VETERAN'::character varying, 'REALISM'::character varying])::text[])))
);


ALTER TABLE public.zadatak OWNER TO bytepit_user;

--
-- Name: zadatak_seq; Type: SEQUENCE; Schema: public; Owner: bytepit_user
--

CREATE SEQUENCE public.zadatak_seq
    START WITH 1001
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zadatak_seq OWNER TO bytepit_user;

--
-- Data for Name: korisnik; Type: TABLE DATA; Schema: public; Owner: bytepit_user
--

COPY public.korisnik (confirmed_email, korisnik_id, vrijeme_registracije, email, fotografija, ime, korisnicko_ime, lozinka, prezime, requested_uloga, uloga) FROM stdin;
t	12	2024-01-19 09:04:39.415063	lm54195@fer.hr	./src/main/resources/profilneSlike/zutokljunac.png	Zutoooo	zutokljunac	$2a$10$OIrfR3v18.GZ0ljkUIO3Q.CrXKBVQnvAfPzGicfRqbke1b7.7QuAW	Kljunac	VODITELJ	VODITELJ
t	1	2024-01-17 19:49:50.247474	admin@bytepit.hr	./src/main/resources/profilneSlike/placeHolder.png	Admin	admin	$2y$10$Hpi8VX8KT2MyLvdoCQKX9uBprCdU2sDsAt8gx1mrznSPKmrDwHv0W	Administrator	\N	ADMIN
t	2	2024-01-17 19:49:50.247474	spidey@avengers.net	./src/main/resources/profilneSlike/placeHolder.png	Miles	spiderman	$2y$10$TnCTVD9HyIRkTrv1dcfSTuwwBCHnwV07Q9By9GHqshfiqdJltdLt.	Morales	\N	NATJECATELJ
t	3	2024-01-17 19:49:50.247474	hulk@avengers.net	./src/main/resources/profilneSlike/placeHolder.png	Bruce	hulk	$2y$10$EQzrPL8ccKBY7SgXFYcRN.zxPmAhLOHoTgEXhWyHxGWIHYQWEjmXq	Banner	\N	NATJECATELJ
t	4	2024-01-17 19:49:50.247474	penny.alfred@justice.com	./src/main/resources/profilneSlike/placeHolder.png	Alfred	buttler	$2y$10$q7MTDmHKnznsZBRR0Ho9N.TUEh2X4aV04V6QIrDLMY3lKfKc9.oVq	Pennyworth	\N	VODITELJ
t	5	2024-01-17 19:49:50.247474	batman@justice.com	./src/main/resources/profilneSlike/placeHolder.png	Bruce	batman	$2y$10$P4eDgjwT4ykRFt74.fTwV.tIwaoLMMjunlGirBEtmNPsRrIcAcU5m	Bayne	\N	NATJECATELJ
t	6	2024-01-17 19:49:50.247474	nick.fury@shield.gov	./src/main/resources/profilneSlike/placeHolder.png	Nick	eyepatch	$2y$10$p4dWESqApfxgcJ3lqatdV.tY0/li8vPIaNC0TxIeDdEgm0tupjJkq	Fury	\N	VODITELJ
f	7	2024-01-17 19:49:50.247474	miss.minutes@tva.gov	./src/main/resources/profilneSlike/placeHolder.png	Miss	missMinutes	$2y$10$SkXSgI414ejZLRi9nq5bZeZjgYUA6JjRS1mbspjxrmseFEp.UpOb2	Minutes	\N	VODITELJ
t	8	2024-01-17 19:49:58.535672	devasip269@konican.com	./src/main/resources/profilneSlike/captain.jpg	Steve	captain	$2a$10$JWKBRe4wEn.4WMvFYbhPBeOy9mS9gAjbfkzZvOibvMrekF7HoRTGu	Rogers	NATJECATELJ	NATJECATELJ
t	9	2024-01-17 20:14:06.246118	mv54173@fer.hr	./src/main/resources/profilneSlike/marko.jpg	Marko	marko	$2a$10$F46gdA/lubl7H70YVcDLfeza65a.F40uhLl.MWZJWY2ERm1pg45Xm	Varga	NATJECATELJ	NATJECATELJ
t	10	2024-01-18 15:14:34.933091	harowig234@konican.com	./src/main/resources/profilneSlike/test.png	test	test	$2a$10$kIYoxn3sUJFw5FzK/ES8RuopDoy2rX4Dj0KpvOhKyY.I4GzhAyJQG	test	NATJECATELJ	NATJECATELJ
t	11	2024-01-18 16:10:04.285436	vc54146@fer.hr	./src/main/resources/profilneSlike/vedran.jpg	Vedran	vedran	$2a$10$9CTqsybsXbBfxD5Kj2xFbuyMNg62ndMH8I95YWGW4A49ZRtMdziUK	Ćutić	VODITELJ	VODITELJ
\.


--
-- Data for Name: nadmetanje_zadaci; Type: TABLE DATA; Schema: public; Owner: bytepit_user
--

COPY public.nadmetanje_zadaci (nadmetanje_natjecanje_id, zadaci_zadatak_id) FROM stdin;
101	1001
102	1002
106	1002
103	1003
103	1004
102	1005
106	1005
104	1006
102	1007
106	1007
107	1002
107	1007
107	1003
108	1007
108	1005
108	1002
109	1007
109	1005
109	1002
110	1008
111	1007
111	1002
111	1005
112	1001
113	1008
113	1002
113	1007
114	1003
114	1008
114	1005
115	1002
115	1008
115	1007
\.


--
-- Data for Name: natjecanje; Type: TABLE DATA; Schema: public; Owner: bytepit_user
--

COPY public.natjecanje (korisnik_korisnik_id, natjecanje_id, kraj_natjecanja, pocetak_natjecanja, naziv_natjecanja) FROM stdin;
4	101	2024-01-18 19:49:50.247474	2024-01-17 19:49:50.247474	Endgame
6	102	2024-01-17 11:49:50.247474	2024-01-14 19:49:50.247474	Justice League
4	103	2024-01-21 19:49:50.247474	2024-01-19 19:49:50.247474	Secret Wars
6	104	2024-01-18 19:49:50.247474	2024-01-16 19:49:50.247474	Hunger Games
4	110	2024-01-18 18:33:12.724	2024-01-18 18:32:59.514	proba_priv_zad
4	113	2024-01-19 09:11:28.29	2024-01-19 09:07:25.796	natjecanje
\.


--
-- Data for Name: pehar; Type: TABLE DATA; Schema: public; Owner: bytepit_user
--

COPY public.pehar (mjesto, natjecanje_natjecanje_id, natjecatelj_korisnik_id, pehar_id, slika_pehara) FROM stdin;
1	102	3	1021	./src/main/resources/slikePehara/trophyPlaceholder.png
1	103	\N	1031	./src/main/resources/slikePehara/trophyPlaceholder.png
1	104	\N	1041	./src/main/resources/slikePehara/trophyPlaceholder.png
1	110	\N	1042	./src/main/resources/slikePehara/110.jpg
1	101	2	1011	./src/main/resources/slikePehara/trophyPlaceholder.png
2	101	3	1052	./src/main/resources/slikePehara/trophyPlaceholder.png
3	101	4	1053	./src/main/resources/slikePehara/trophyPlaceholder.png
1	113	2	1062	./src/main/resources/slikePehara/113.png
\.


--
-- Data for Name: rjesenje; Type: TABLE DATA; Schema: public; Owner: bytepit_user
--

COPY public.rjesenje (broj_tocnih_primjera, natjecanje_natjecanje_id, natjecatelj_korisnik_id, rjesenje_rb, zadatak_zadatak_id, vrijeme_odgovora, programski_kod) FROM stdin;
1	101	3	1	1005	2024-01-15 19:49:50.247474	#include<bits/stdc++.h>\\n int main() {int s; std::cin >> s; std::cout << s;}
0.5	102	3	2	1005	2024-01-17 09:49:50.247474	#include<bits/stdc++.h>\\n using namespace std;\\n int main() {string s;\\n cin >> s;\\n cout << s;}
0	101	5	3	1005	2024-01-17 03:49:50.247474	\N
1	101	4	4	1005	2024-01-16 19:49:50.247474	#include<bits/stdc++.h>\\n int main() {int s; std::cin >> s; std::cout << s;}
1	105	5	5	1003	2024-01-17 18:49:50.247474	#include<bits/stdc++.h>\\n int main() {int a, b; std::cin >> a >> b; std::cout << a*b
1	102	4	6	1003	2024-01-17 18:49:50.247474	#include<bits/stdc++.h>\\n int main() {int x, y; std::cin >> x >> y; std::cout << x*y
1	\N	5	7	1006	2024-01-17 18:49:50.247474	#include<bits/stdc++.h>\\n int main() {int kune; std::cin >> kune; std::cout << (int)kune/7.5345;}
1	106	5	8	1005	2024-01-17 09:49:50.247474	#include<bits/stdc++.h>\\n using namespace std;\\n int main() {string s;\\n cin >> s;\\n cout << s;}
1	\N	2	0	1003	2024-01-18 13:39:29.438797	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\nint broj1, broj2;\n    cin >> broj1 >> broj2;\n  cout << broj1 * broj2;\n  return 0;\n}
1	\N	10	0	1003	2024-01-18 15:17:40.615093	#include <bits/stdc++.h>\n#include <stdio.h>\nusing namespace std;\nint main() {\n  int a, b;\n  scanf("%d %d", &a, &b);\n  printf("%d", a * b);\n  return 0;\n}
1	108	2	1	1002	2024-01-18 15:30:14.76585	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n\n  int N;\n  \n  scanf("%d", &N);\n  \n  for(int i = 1; i <= N; i++) {\n    printf("%d ", i);\n  }\n\n  return 0;\n}
0	108	2	2	1007	2024-01-18 15:31:58.954055	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n\n  printf("%d", a*a);\n  \n  return 0;\n}
0	108	2	3	1007	2024-01-18 15:32:07.560964	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n\n  printf("%d", a*a);\n  \n  return 0;\n}
0	108	2	4	1007	2024-01-18 15:32:27.757602	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n\n  int a;\n  scanf("%d", &a);\n  printf("%d", a*a);\n  \n  return 0;\n}
1	108	2	5	1005	2024-01-18 15:34:35.781348	#include <bits/stdc++.h>\n#include <string.h>\nusing namespace std;\nint main() {\n\n  string a = "";\n  cin >> a;\n  cout << a;\n  \n  \n  return 0;\n}
1	101	2	6	1001	2024-01-18 15:40:19.448191	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n\n  printf("Hello world");\n\n  return 0;\n}
1	101	2	7	1001	2024-01-18 15:45:06.290596	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n\n  printf("Hello world");\n\n  return 0;\n}
1	109	2	8	1002	2024-01-18 15:47:11.546646	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n\n  int N;\n  scanf("%d", &N);\n  for (int i = 1; i <= N; i++) {\n    printf("%d ", i);\n  }\n\n  return 0;\n}
1	109	2	9	1005	2024-01-18 15:47:52.147086	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n\n  string a;\n  cin >> a;\n  cout << a;\n\n  return 0;\n}
0	109	2	10	1007	2024-01-18 15:48:22.054937	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n\n  int a;\n  scanf("%d", &a);\n\n  printf("%d", (a * a));\n\n  return 0;\n}
0	\N	9	0	1002	2024-01-18 15:53:27.221482	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n  int a;\n  cin >> a;\n  cout << a;\n  return 0;\n}
1	\N	2	11	1005	2024-01-18 16:02:40.847067	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\nstring a;\ncin >> a;\ncout << a;\n  return 0;\n}
1	\N	2	12	1005	2024-01-18 16:07:06.756926	#include <iostream>\n\nint main() {\n  std::string s;\n  std::cin >> s;\n  std::cout << s;\n\n  return 0;\n}\n
1	\N	2	13	1005	2024-01-18 16:14:32.413217	#include <bits/stdc++.h>\n#include <cstdio>\n\nusing namespace std;\nint main() {\n  char test[100];\n  scanf("%s", test);\n  printf("%s", test);\n  \n  return 0;\n}
1	\N	2	14	1005	2024-01-18 16:15:33.766443	#include <bits/stdc++.h>\n#include <cstdio>\n\nusing namespace std;\nint main() {\n  char test[100];\n  scanf("%s", test);\n  printf("%s", test);\n  \n  return 0;\n}\n
1	\N	2	15	1002	2024-01-18 17:08:08.383679	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n  int n;\n  cin >> n;\n  for(int i=1;i < n;i++)\n    cout << i << ' ';\n\n  cout << n;\n\n  return 0;\n}
1	\N	2	16	1008	2024-01-18 17:08:23.087034	#include <bits/stdc++.h>\nusing namespace std;\n\nconst int MAXH = 25;\n\nint n, zeli[MAXH][MAXH];\n\nint main() {\n  cin >> n;\n\n  int najvise = -1;\n  int A, B;\n\n  for (int i = 0; i < n; ++i) {\n    int a, b;\n    cin >> a >> b;\n    zeli[a][b]++;\n    if (zeli[a][b] > najvise) {\n      najvise = zeli[a][b];\n      A = a;\n      B = b;\n    } else if (zeli[a][b] == najvise) {\n      if (b - a < B - A) {\n        A = a;\n        B = b;\n      } else if (b - a == B - A && a < A) {\n        A = a;\n        B = b;\n      }\n    }\n  }\n\n  cout << A << " " << B << endl;\n  return 0;\n}\n
1	101	2	17	1001	2024-01-18 17:19:21.586642	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n  cout << "Hello world";\n  return 0;\n}
1	\N	2	18	1008	2024-01-18 19:21:58.094882	#include <bits/stdc++.h>\nusing namespace std;\n\nconst int MAXH = 25;\n\nint n, zeli[MAXH][MAXH];\n\nint main() {\n  cin >> n;\n\n  int najvise = -1;\n  int A, B;\n\n  for (int i = 0; i < n; ++i) {\n    int a, b;\n    cin >> a >> b;\n    zeli[a][b]++;\n    if (zeli[a][b] > najvise) {\n      najvise = zeli[a][b];\n      A = a;\n      B = b;\n    } else if (zeli[a][b] == najvise) {\n      if (b - a < B - A) {\n        A = a;\n        B = b;\n      } else if (b - a == B - A && a < A) {\n        A = a;\n        B = b;\n      }\n    }\n  }\n\n  cout << A << " " << B << endl;\n  return 0;\n}\n
1	\N	2	19	1005	2024-01-18 19:24:40.297062	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n  string a;\n  cin >> a;\n  cout << a;\n  return 0;\n}
1	\N	2	20	1005	2024-01-18 19:24:56.598694	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\nstring a;\ncin >> a;\ncout << a;\n  return 0;\n}
1	112	3	3	1001	2024-01-18 19:55:11.973475	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n  printf("Hello world");\n  return 0;\n}
1	\N	2	21	1005	2024-01-18 20:07:17.928586	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\nstring a;\ncin >> a;\ncout << a;\n  return 0;\n}
1	\N	2	22	1005	2024-01-18 20:13:12.40075	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\nstring a;\ncin >> a;\ncout << a;\nreturn 0;\n}
1	\N	2	23	1005	2024-01-18 20:14:16.799171	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\nstring a;\ncin >> a;\ncout << a;\nreturn 0;\n}
1	\N	2	24	1008	2024-01-18 22:33:11.196251	#include <bits/stdc++.h>\nusing namespace std;\n\nint counter[24][24];\n\nint main() {\n  int n;\n  cin >> n;\n\n  for(int i=0;i < n;i++) {\n    int a, b;\n    cin >> a >> b;\n    counter[a - 1][b - 1]++;\n  }\n\n  int max_vrijeme = -1;\n  int poc, kraj;\n  for(int i=0;i < 24;i++) {\n    for(int j=i+1;j < 24;j++) {\n      if(counter[i][j] > max_vrijeme) {\n        max_vrijeme = counter[i][j];\n        poc = i;\n        kraj = j;\n      }\n    }\n  }\n\n  cout << poc+1 << ' ' << kraj+1;\n\n  return 0;\n}\n
1	\N	9	1	1007	2024-01-19 08:07:46.541034	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n  int a;\n  cin >> a;\n  cout << a*a;\n  return 0;\n}
0.75	\N	2	25	1007	2024-01-19 08:15:36.134718	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n  int a;\n  cin >> a;\n  cout << a*a;\n  return 0;\n}
0.75	113	2	26	1007	2024-01-19 09:09:35.801901	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\n  int a;\n  cin >> a;\n  cout << a*a;\n  return 0;\n}
1	113	2	27	1008	2024-01-19 09:11:20.600035	#include <bits/stdc++.h>\nusing namespace std;\n\nconst int MAXH = 25;\n\nint n, zeli[MAXH][MAXH];\n\nint main() {\n  cin >> n;\n\n  int najvise = -1;\n  int A, B;\n\n  for (int i = 0; i < n; ++i) {\n    int a, b;\n    cin >> a >> b;\n    zeli[a][b]++;\n    if (zeli[a][b] > najvise) {\n      najvise = zeli[a][b];\n      A = a;\n      B = b;\n    } else if (zeli[a][b] == najvise) {\n      if (b - a < B - A) {\n        A = a;\n        B = b;\n      } else if (b - a == B - A && a < A) {\n        A = a;\n        B = b;\n      }\n    }\n  }\n\n  cout << A << " " << B << endl;\n  return 0;\n}\n
1	\N	2	28	1005	2024-01-19 10:11:18.7418	#include <bits/stdc++.h>\nusing namespace std;\nint main() {\nstring a;\ncin >> a;\ncout << a;\nreturn 0;\n}
\.


--
-- Data for Name: testni_primjer; Type: TABLE DATA; Schema: public; Owner: bytepit_user
--

COPY public.testni_primjer (testni_primjer_rb, zadatak_zadatak_id, izlazni_podaci, ulazni_podaci) FROM stdin;
1	1001	Hello world	\N
1	1002	1 2 3 4 5	5
1	1003	10	5 2
1	1004	1 1 2	3
1	1005	3	3
2	1005	jeka	jeka
1	1006	10000	75345
2	1002	1 2	2
1	1007	100	10
2	1007	4	2
3	1007	1	-1
2	1004	1 1 2 3	4
2	1001	Hello world	
1	1008	8 16	3 8 16 9 17 8 16
2	1008	11 18	10 11 18 11 18 8 15 10 17 11 18 11 18 10 17 11 18 10 17 9 16
3	1008	8 15	10 10 17 8 15 9 16 10 17 9 16 10 17 11 18 8 15 8 15 9 16
4	1007	29.16	5.4
\.


--
-- Data for Name: virtualno_natjecanje; Type: TABLE DATA; Schema: public; Owner: bytepit_user
--

COPY public.virtualno_natjecanje (korisnik_korisnik_id, natjecanje_id, orginalno_natjecanje_natjecanje_id, pocetak_natjecanja) FROM stdin;
2	105	\N	2024-01-17 18:49:50.247474
5	106	102	2024-01-17 19:48:50.247474
10	107	\N	2024-01-18 15:18:43.352
2	108	102	2024-01-18 15:28:08.746
2	109	102	2024-01-18 15:46:17.846
2	111	102	2024-01-18 19:25:55.304
3	112	101	2024-01-18 19:54:43.699
3	114	\N	2024-01-19 09:14:44.803
3	115	113	2024-01-19 09:15:15.398
\.


--
-- Data for Name: zadatak; Type: TABLE DATA; Schema: public; Owner: bytepit_user
--

COPY public.zadatak (broj_bodova, privatni_zadatak, voditelj_korisnik_id, vremensko_ogranicenje, zadatak_id, naziv_zadatka, tekst_zadatka, tezina_zadatka) FROM stdin;
10	f	4	3	1008	Tvrtka	Mihaela je nedavno osnovala tvrtku koja se bavi proizvodnjom odjeće. Zaposlila je N radnika i sada trebaodabrati radno vrijeme svoje tvrtke. Svakog radnika je upitala koje mu radno vrijeme najviše odgovara.Radnici su joj odgovorili u obliku: “Želim raditi svaki dan od A sati do B sati.”Kako bi udovoljila što većem broju radnika, Mihaela će za radno vrijeme tvrtke odabrati ono koje senajviše puta pojavljuje kao željeno radno vrijeme. Ako takvih vremena ima više, odabrat će ono najkraće,a ako i dalje ima više takvih odabrat će ono koje počinje najranije.Budući da Mihaela trenutno ima puno posla s državnom administracijom, pomozite joj i napišite programkoji će ispisati radno vrijeme koje treba odabrati.Ulazni podaciU prvom je retku prirodan broj N (1 ≤ N ≤ 10), broj radnika.U i-tom od sljedećih N redaka su dva cijela broja Ai i Bi (0 ≤ Ai < Bi < 24), željeno radno vrijeme i-togradnika.Izlazni podaciIspišite traženo radno vrijeme.	RECRUIT
50	f	6	1	1006	Konvertor	Za zadani iznos u kunama ispiši ekvivalentni iznos u eurima zaokružen na cijeli broj	REALISM
10	f	4	1	1001	Hello world	Napiši program koji ispisuje Hello world.	RECRUIT
10	f	6	1	1003	Množilica	Napiši program koji množi dva upisana cijela broja.	RECRUIT
50	f	4	2	1004	Fibbonaci	Ispiši prvih N brojeva iz Fibbonacijevog niza	REALISM
20	f	6	1	1005	Jeka	Ispiši ono što je korisnik unio.	VETERAN
20	f	4	1	1002	Brojilica	Napiši program koji ispisuje sve prirodne brojeve do broja N.	VETERAN
10	f	4	1	1007	Kvadrat	Ispiši površinu kvadarata stranice a.	RECRUIT
\.


--
-- Name: korisnik_seq; Type: SEQUENCE SET; Schema: public; Owner: bytepit_user
--

SELECT pg_catalog.setval('public.korisnik_seq', 12, true);


--
-- Name: natjecanje_seq; Type: SEQUENCE SET; Schema: public; Owner: bytepit_user
--

SELECT pg_catalog.setval('public.natjecanje_seq', 115, true);


--
-- Name: pehar_seq; Type: SEQUENCE SET; Schema: public; Owner: bytepit_user
--

SELECT pg_catalog.setval('public.pehar_seq', 1071, true);


--
-- Name: virtualno_natjecanje_seq; Type: SEQUENCE SET; Schema: public; Owner: bytepit_user
--

SELECT pg_catalog.setval('public.virtualno_natjecanje_seq', 100002, true);


--
-- Name: zadatak_seq; Type: SEQUENCE SET; Schema: public; Owner: bytepit_user
--

SELECT pg_catalog.setval('public.zadatak_seq', 1008, true);


--
-- Name: korisnik korisnik_email_key; Type: CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.korisnik
    ADD CONSTRAINT korisnik_email_key UNIQUE (email);


--
-- Name: korisnik korisnik_korisnicko_ime_key; Type: CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.korisnik
    ADD CONSTRAINT korisnik_korisnicko_ime_key UNIQUE (korisnicko_ime);


--
-- Name: korisnik korisnik_pkey; Type: CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.korisnik
    ADD CONSTRAINT korisnik_pkey PRIMARY KEY (korisnik_id);


--
-- Name: nadmetanje_zadaci nadmetanje_zadaci_pkey; Type: CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.nadmetanje_zadaci
    ADD CONSTRAINT nadmetanje_zadaci_pkey PRIMARY KEY (nadmetanje_natjecanje_id, zadaci_zadatak_id);


--
-- Name: natjecanje natjecanje_pkey; Type: CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.natjecanje
    ADD CONSTRAINT natjecanje_pkey PRIMARY KEY (natjecanje_id);


--
-- Name: pehar pehar_pkey; Type: CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.pehar
    ADD CONSTRAINT pehar_pkey PRIMARY KEY (pehar_id);


--
-- Name: rjesenje rjesenje_pkey; Type: CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.rjesenje
    ADD CONSTRAINT rjesenje_pkey PRIMARY KEY (natjecatelj_korisnik_id, rjesenje_rb, zadatak_zadatak_id);


--
-- Name: testni_primjer testni_primjer_pkey; Type: CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.testni_primjer
    ADD CONSTRAINT testni_primjer_pkey PRIMARY KEY (testni_primjer_rb, zadatak_zadatak_id);


--
-- Name: virtualno_natjecanje virtualno_natjecanje_pkey; Type: CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.virtualno_natjecanje
    ADD CONSTRAINT virtualno_natjecanje_pkey PRIMARY KEY (natjecanje_id);


--
-- Name: zadatak zadatak_pkey; Type: CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.zadatak
    ADD CONSTRAINT zadatak_pkey PRIMARY KEY (zadatak_id);


--
-- Name: rjesenje fk1fe34vilk51ysfm223lfjtmom; Type: FK CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.rjesenje
    ADD CONSTRAINT fk1fe34vilk51ysfm223lfjtmom FOREIGN KEY (natjecatelj_korisnik_id) REFERENCES public.korisnik(korisnik_id);


--
-- Name: pehar fk22oeo11iaou8f6nuryj8i2qny; Type: FK CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.pehar
    ADD CONSTRAINT fk22oeo11iaou8f6nuryj8i2qny FOREIGN KEY (natjecatelj_korisnik_id) REFERENCES public.korisnik(korisnik_id);


--
-- Name: nadmetanje_zadaci fk3foayeexm0y7p5es954dt0ftp; Type: FK CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.nadmetanje_zadaci
    ADD CONSTRAINT fk3foayeexm0y7p5es954dt0ftp FOREIGN KEY (zadaci_zadatak_id) REFERENCES public.zadatak(zadatak_id);


--
-- Name: testni_primjer fk8gj42bn0mn17ur17torroh6p3; Type: FK CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.testni_primjer
    ADD CONSTRAINT fk8gj42bn0mn17ur17torroh6p3 FOREIGN KEY (zadatak_zadatak_id) REFERENCES public.zadatak(zadatak_id);


--
-- Name: virtualno_natjecanje fk_abfjhy0v5hghdv42vsibig1m0; Type: FK CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.virtualno_natjecanje
    ADD CONSTRAINT fk_abfjhy0v5hghdv42vsibig1m0 FOREIGN KEY (korisnik_korisnik_id) REFERENCES public.korisnik(korisnik_id);


--
-- Name: natjecanje fk_i63gu1ni9vxliimxdjw4ikktu; Type: FK CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.natjecanje
    ADD CONSTRAINT fk_i63gu1ni9vxliimxdjw4ikktu FOREIGN KEY (korisnik_korisnik_id) REFERENCES public.korisnik(korisnik_id);


--
-- Name: virtualno_natjecanje fkbvfkh7oqxfjfbwptj3s404wrr; Type: FK CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.virtualno_natjecanje
    ADD CONSTRAINT fkbvfkh7oqxfjfbwptj3s404wrr FOREIGN KEY (orginalno_natjecanje_natjecanje_id) REFERENCES public.natjecanje(natjecanje_id);


--
-- Name: zadatak fkdatngv968nwbicc0dsqex5v09; Type: FK CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.zadatak
    ADD CONSTRAINT fkdatngv968nwbicc0dsqex5v09 FOREIGN KEY (voditelj_korisnik_id) REFERENCES public.korisnik(korisnik_id);


--
-- Name: rjesenje fkn2ckr9kbfo23wb9mnrprp0kgm; Type: FK CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.rjesenje
    ADD CONSTRAINT fkn2ckr9kbfo23wb9mnrprp0kgm FOREIGN KEY (zadatak_zadatak_id) REFERENCES public.zadatak(zadatak_id);


--
-- Name: pehar fkqds6j8rwtg1jre2rxxn0p0l5m; Type: FK CONSTRAINT; Schema: public; Owner: bytepit_user
--

ALTER TABLE ONLY public.pehar
    ADD CONSTRAINT fkqds6j8rwtg1jre2rxxn0p0l5m FOREIGN KEY (natjecanje_natjecanje_id) REFERENCES public.natjecanje(natjecanje_id);


--
-- Name: DEFAULT PRIVILEGES FOR SEQUENCES; Type: DEFAULT ACL; Schema: -; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT ALL ON SEQUENCES  TO bytepit_user;


--
-- Name: DEFAULT PRIVILEGES FOR TYPES; Type: DEFAULT ACL; Schema: -; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT ALL ON TYPES  TO bytepit_user;


--
-- Name: DEFAULT PRIVILEGES FOR FUNCTIONS; Type: DEFAULT ACL; Schema: -; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT ALL ON FUNCTIONS  TO bytepit_user;


--
-- Name: DEFAULT PRIVILEGES FOR TABLES; Type: DEFAULT ACL; Schema: -; Owner: postgres
--

ALTER DEFAULT PRIVILEGES FOR ROLE postgres GRANT ALL ON TABLES  TO bytepit_user;


--
-- PostgreSQL database dump complete
--

