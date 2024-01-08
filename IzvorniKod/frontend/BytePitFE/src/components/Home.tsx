import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

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
    const [trenutnaNatjecanja, setTrenutnaNatjecanja] = useState<Natjecanje[]>([]);
    const [selectedTable, setSelectedTable] = useState<'nadolazeca' | 'prosla' | 'trenutna'>('trenutna');

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

    useEffect(() => {
        const fetchTrenutnaNatjecanja = async () => {
            try {
                const response = await fetch('/api/natjecanja/trenutna');
                const data = await response.json();
                setTrenutnaNatjecanja(data);
            } catch (error) {
                console.error('Greška prilikom dohvaćanja trenutnih natjecanja:', error);
            }
        };

        fetchTrenutnaNatjecanja();
    }, []);

    const tileContent = ({ date, view }: { date: Date; view: string }) => {
        if (view === 'month') {
            const natjecanjaZaDatum = natjecanja.filter(
                (natjecanje) =>
                    date >= new Date(natjecanje.pocetakNatjecanja) &&
                    date <= new Date(natjecanje.krajNatjecanja)
            );

            return natjecanjaZaDatum.length > 0 ? (
                <div>
                    {natjecanjaZaDatum.map((natjecanje) => (
                        <p key={natjecanje.natjecanjeId} className="naziv-natjecanje">
                            {natjecanje.nazivNatjecanja}
                        </p>
                    ))}
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
        return new Date(datumVrijeme).toLocaleString('en-GB', options);
    };

    const proslaNatjecanja = natjecanja.filter(
        (natjecanje) => date > new Date(natjecanje.krajNatjecanja)
    );

    return (
        <div>
            <div className="calendar-container">
                <Calendar value={date} onChange={(newDate) => setDate(newDate as Date)} tileContent={tileContent} />
            </div>
            <div className="selected-date-container">
                Označeni datum: {date.toLocaleDateString()} {date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}
                <br />
                {oznacenaNatjecanja.length > 0 ? (
                    <div>
                        Označena natjecanja: {oznacenaNatjecanja.map(natjecanje => natjecanje.nazivNatjecanja).join(', ')}
                    </div>
                ) : (
                    <div>Ovog datuma se ne održava natjecanje.</div>
                )}
            </div>

            <div className="table-switch-buttons">
                <button className={"tablica-natjecanje-button"} onClick={() => setSelectedTable('nadolazeca')}>Nadolazeća natjecanja</button>
                <button className={"tablica-natjecanje-button"} onClick={() => setSelectedTable('prosla')}>Prošla natjecanja</button>
                <button className={"tablica-natjecanje-button"} onClick={() => setSelectedTable('trenutna')}>Trenutna natjecanja</button>
            </div>

            {selectedTable === 'trenutna' && (
                <div className="tablica-trenutna-natjecanja">
                    <table className="info-table">
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
                        {trenutnaNatjecanja.map((natjecanje) => (
                            <tr key={natjecanje.natjecanjeId}>
                                <td>{natjecanje.natjecanjeId}</td>
                                <td>{natjecanje.nazivNatjecanja}</td>
                                <td>{formatirajDatumVrijeme(natjecanje.pocetakNatjecanja)}</td>
                                <td>{formatirajDatumVrijeme(natjecanje.krajNatjecanja)}</td>
                                <td>{natjecanje.korisnickoImeVoditelja}</td>
                                <td>Pokreni natjecanje</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}

            {selectedTable === 'nadolazeca' && (
                <div className="tablica-natjecanja">
                    <table className="info-table">
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
                        {natjecanja.map((natjecanje) => (
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
                <div className="tablica-prosla-natjecanja">
                    <table className="info-table">
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
                        {proslaNatjecanja.map((natjecanje) => (
                            <tr key={natjecanje.natjecanjeId}>
                                <td>{natjecanje.natjecanjeId}</td>
                                <td>{natjecanje.nazivNatjecanja}</td>
                                <td>{formatirajDatumVrijeme(natjecanje.pocetakNatjecanja)}</td>
                                <td>{formatirajDatumVrijeme(natjecanje.krajNatjecanja)}</td>
                                <td>{natjecanje.korisnickoImeVoditelja}</td>
                                <td>Pokreni natjecanje</td>
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
