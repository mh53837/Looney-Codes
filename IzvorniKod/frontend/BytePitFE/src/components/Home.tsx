import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

interface Natjecanje {
    natjecanjeId: number;
    nazivNatjecanja: string;
    pocetakNatjecanja: string;
    krajNatjecanja: string;
    voditeljId: number;
}

const Home: React.FC = () => {
    const [date, setDate] = useState<Date>(new Date());
    const [natjecanja, setNatjecanja] = useState<Natjecanje[]>([]);
    const [oznacenaNatjecanja, setOznacenaNatjecanja] = useState<Natjecanje[]>([]);

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

    return (
        <div>
            <div className="calendar-container">
                <Calendar value={date} onChange={(newDate) => setDate(newDate as Date)} tileContent={tileContent} />
            </div>
            <div className="selected-date-container">
                Označeni datum: {date.toDateString()}
                <br />
                {oznacenaNatjecanja.length > 0 ? (
                    <div>
                        Označena natjecanja: {oznacenaNatjecanja.map(natjecanje => natjecanje.nazivNatjecanja).join(', ')}
                    </div>
                ) : (
                    <div>Ovog datuma se ne održava natjecanje.</div>
                )}
            </div>

            {/* Dodajte tablicu ispod ovog dijela */}
            <div>
                <h2>Sva natjecanja:</h2>
                <table className="info-table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Naziv natjecanja</th>
                        <th>Početak natjecanja</th>
                        <th>Kraj natjecanja</th>
                        <th>Voditelj ID</th>
                    </tr>
                    </thead>
                    <tbody>
                    {natjecanja.map((natjecanje) => (
                        <tr key={natjecanje.natjecanjeId}>
                            <td>{natjecanje.natjecanjeId}</td>
                            <td>{natjecanje.nazivNatjecanja}</td>
                            <td>{natjecanje.pocetakNatjecanja}</td>
                            <td>{natjecanje.krajNatjecanja}</td>
                            <td>{natjecanje.voditeljId}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Home;
