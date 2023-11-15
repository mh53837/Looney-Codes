import React, { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

interface HomeProps {
    loggedInUser: string | null;
}

const Home: React.FC<HomeProps> = ({ loggedInUser }) => {
    const [date, setDate] = useState<Date>(new Date());

    return (
        <div>
            {loggedInUser ? (
                <div>
                    <p>Pozdrav, {loggedInUser}!</p>
                    {/* Additional content for logged-in users */}
                </div>
            ) : (
                <div>
                    <p>Pozdrav, nepoznati korisnik!</p>
                </div>
            )}

            <div className="calendar-container">
                <Calendar value={date} onChange={(newDate) => setDate(newDate as Date)} />
            </div>
            <div className="selected-date-container">
                Selected date: {date.toDateString()}
            </div>

        </div>
    );
};

export default Home;

