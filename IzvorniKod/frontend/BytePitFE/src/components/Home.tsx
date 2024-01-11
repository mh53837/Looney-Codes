import React, { useState, useEffect, useContext } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import { Link } from 'react-router-dom';
import { UserContext } from '../context/userContext';

interface Natjecanje {
    natjecanjeId: number;
    nazivNatjecanja: string;
    pocetakNatjecanja: string;
    krajNatjecanja: string;
    korisnickoImeVoditelja: string;
}

const Home: React.FC = () => {
    const [date, setDate] = useState<Date>(new Date());
    const [natjecanja, setNatjecanja] = useState<Natjecanje[]>([]);
    const [oznacenaNatjecanja, setOznacenaNatjecanja] = useState<Natjecanje[]>([]);
    const [selectedTable, setSelectedTable] = useState<'nadolazeca' | 'prosla' | 'trenutna'>('trenutna');
    const [nadolazeca, setUpcomingData] = useState<Natjecanje[]>([]);
    const [trenutna, setOngoingData] = useState<Natjecanje[]>([]);
    const [zavrsena, setFinishedData] = useState<Natjecanje[]>([]);
    const {user} = useContext(UserContext);

    useEffect(() => {
        fetch(`/api/natjecanja/get/finished`)
            .then(response => response.json())
            .then(data => setFinishedData(data))
            .catch(error => console.error('Error fetching competitions:', error));

        fetch(`/api/natjecanja/get/ongoing`)
            .then(response => response.json())
            .then(data => setOngoingData(data))
            .catch(error => console.error('Error fetching competitions:', error));

        fetch(`/api/natjecanja/get/upcoming`)
            .then(response => response.json())
            .then(data => setUpcomingData(data))
            .catch(error => console.error('Error fetching competitions:', error));
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch('/api/natjecanja/all');
                const data = await response.json();
                setNatjecanja(data);
            } catch (error) {
                console.error('Greška prilikom dohvaćanja natjecanja:', error);
            }
        };

        fetchData();
    }, []);

    useEffect(() => {
        const natjecanjaZaDatum = natjecanja.filter(
            (natjecanje) =>
                date >= new Date(natjecanje.pocetakNatjecanja) && date <= new Date(natjecanje.krajNatjecanja)
        );
        setOznacenaNatjecanja(natjecanjaZaDatum);
    }, [date, natjecanja]);


    const tileContent = ({ date, view }: { date: Date; view: string }) => {
        if (view === 'month') {
            const natjecanjaZaDatum = natjecanja.filter(
                (natjecanje) =>
                    date >= new Date(natjecanje.pocetakNatjecanja) &&
                    date <= new Date(natjecanje.krajNatjecanja)
            );

            return natjecanjaZaDatum.length > 0 ? (
                <div>
                    <p className="naziv-natjecanje">
                        &#9733;
                    </p>
                </div>
            ) : null;
        }

        return null;
    };

    const formatirajDatumVrijeme = (datumVrijeme: string) => {
        const options: Intl.DateTimeFormatOptions = {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit',
        };
        return new Date(datumVrijeme).toLocaleString('hr-HR', options);
    };





    return (
        <div>
            {(
                user.uloga === "VODITELJ" &&
                <Link to="/natjecanja/new">
                <button className="addBtn">novo natjecanje</button>
                </Link>
            )}
            <div className="calendar-container">
                <Calendar value={date} locale='hr-HR' onChange={(newDate) => setDate(newDate as Date)} tileContent={tileContent} />
            </div>
            <div className="selected-date-container">
                Označeni datum:
                <br />
                {formatirajDatumVrijeme(date.toUTCString())}
                <br />
                {oznacenaNatjecanja.length > 0 ? (
                    <div className='oznacenaNatjecanja'>
                        {oznacenaNatjecanja.map(natjecanje => natjecanje.nazivNatjecanja).join(' | ')}
                    </div>
                ) : (
                    <div>Ovog datuma se ne održava natjecanje.</div>
                )}
            </div>

            <div className="table-switch-buttons">
                <button className={"tablica-natjecanje-button"} onClick={() => {
                    setSelectedTable('prosla');
                    const element = document.getElementById("tablica");
                    if (element != null)
                        element.scrollIntoView({ behavior: 'smooth' });
                }}>Prošla natjecanja</button>
                <button className={"tablica-natjecanje-button"} onClick={() => {
                    setSelectedTable('trenutna');
                    const element = document.getElementById("tablica");
                    if (element != null)
                        element.scrollIntoView({ behavior: 'smooth' });
                }}>Trenutna natjecanja</button>
                <button className={"tablica-natjecanje-button"} onClick={() => {
                    setSelectedTable('nadolazeca');
                    const element = document.getElementById("tablica");
                    if (element != null)
                        element.scrollIntoView({ behavior: 'smooth' });
                }}>Nadolazeća natjecanja</button>

                {/*<button className={"generiraj-natjecanje-button"} onClick={handleGenerirajNatjecanje}>*/}
                {/*    Generiraj natjecanje*/}
                {/*</button>*/}
            </div>

            {selectedTable === 'trenutna' && (
                <div className="info-table">
                    <table id="tablica">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Naziv natjecanja</th>
                                <th>Početak natjecanja</th>
                                <th>Kraj natjecanja</th>
                                <th>Voditelj</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {trenutna.map((natjecanje) => (
                                <tr key={natjecanje.natjecanjeId}>
                                    <td>{natjecanje.natjecanjeId}</td>
                                    <td>{natjecanje.nazivNatjecanja}</td>
                                    <td>{formatirajDatumVrijeme(natjecanje.pocetakNatjecanja)}</td>
                                    <td>{formatirajDatumVrijeme(natjecanje.krajNatjecanja)}</td>
                                    <td>{natjecanje.korisnickoImeVoditelja}</td>
                                    <td>
                                        <Link to={`/natjecanja/rjesi/${natjecanje.natjecanjeId}/`}>
                                            Pokreni natjecanje
                                        </Link>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

            {selectedTable === 'nadolazeca' && (
                <div className="info-table">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Naziv natjecanja</th>
                                <th>Početak natjecanja</th>
                                <th>Kraj natjecanja</th>
                                <th>Voditelj</th>
                            </tr>
                        </thead>
                        <tbody>
                            {nadolazeca.map((natjecanje) => (
                                <tr key={natjecanje.natjecanjeId}>
                                    <td>{natjecanje.natjecanjeId}</td>
                                    <td>{natjecanje.nazivNatjecanja}</td>
                                    <td>{formatirajDatumVrijeme(natjecanje.pocetakNatjecanja)}</td>
                                    <td>{formatirajDatumVrijeme(natjecanje.krajNatjecanja)}</td>
                                    <td>{natjecanje.korisnickoImeVoditelja}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

            {selectedTable === 'prosla' && (
                <div className="info-table">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Naziv natjecanja</th>
                                <th>Početak natjecanja</th>
                                <th>Kraj natjecanja</th>
                                <th>Voditelj</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {zavrsena.map((natjecanje) => (
                                <tr key={natjecanje.natjecanjeId}>
                                    <td>{natjecanje.natjecanjeId}</td>
                                    <td>
                                        <Link to={`/natjecanja/rezultati/${natjecanje.natjecanjeId}/`}>
                                            {natjecanje.nazivNatjecanja}
                                        </Link>
                                    </td>
                                    <td>{formatirajDatumVrijeme(natjecanje.pocetakNatjecanja)}</td>
                                    <td>{formatirajDatumVrijeme(natjecanje.krajNatjecanja)}</td>
                                    <td>{natjecanje.korisnickoImeVoditelja}</td>
                                    <td>
                                        <Link to={`/natjecanja/rjesi/${natjecanje.natjecanjeId}/`}>
                                            Pokreni natjecanje
                                        </Link>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}

        </div>
    );
};

export default Home;
