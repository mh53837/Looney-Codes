import React, { useState, useEffect } from 'react';
/* import { Link } from 'react-router-dom'; */
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
/* import { UserContext } from '../context/userContext';
import { useContext } from 'react'; */

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

    /* const { user } = useContext(UserContext); */

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
                        
{/*             {user.korisnickoIme !== '' ? (      //ovaj dio se moze maknuti posto ce username pisati u navbaru
                <div>
                    <p>Pozdrav, {user.korisnickoIme}!</p>
                    {user.korisnickoIme === 'admin' ? (
                        <Link to="/user/listRequested">odobri voditelje</Link>
                    ) : (<p></p>)}
                </div>
            ) : (
                <div>
                    <p>Pozdrav, nepoznati korisnik!</p>
                </div>
            )} */} 
        

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

        </div>
    );
};

export default Home;
