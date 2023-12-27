import React, { useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import Competition from "../Competition";
import { IUser } from "../User/UserList";
import "../../styles/Table.css";
import "../../styles/UserProfile.css";

interface CompetitionData {
  natjecanjeId: number;
  voditelj: IUser;
  nazivNatjecanja: string;
  pocetakNatjecanja: string;
  krajNatjecanja: string;
}

const CompetitionProfileCalendar: React.FC<{competitionsData: CompetitionData[]; 
}> = ({ competitionsData }) => {
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);

  return (
    <div className="calendar-container">
      <Calendar
        value={selectedDate}
        onChange={(newDate) => setSelectedDate(newDate as Date)}
      />

      <div className="info-table">
        {selectedDate !== null ? (
          <div>
            <p>označeni datum: {selectedDate.toDateString()}</p>
            {(() => {
              const filteredData = competitionsData.filter(
                (competition) =>
                  new Date(competition.pocetakNatjecanja).toDateString() ===
                  selectedDate.toDateString()
              );
              if (filteredData.length === 0) {
                return <p>na označeni datum nema natjecanja</p>;
              } else {
                return (
                  <div className="tableWrapper">
                    <div className="info-table">
                      <table>
                        <thead>
                          <tr>
                            <th>voditelj</th>
                            <th>naziv</th>
                            <th>početak</th>
                            <th>kraj</th>
                            <th></th>
                          </tr>
                        </thead>
                        <tbody>
                          {filteredData.map((competition, index) => (
                            <Competition
                              key={index}
                              competition={competition}
                            />
                          ))}
                        </tbody>
                      </table>
                    </div>
                  </div>
                );
              }
            })()}
          </div>
        ) : (
          <p>odaberite datum</p>
        )}
      </div>
    </div>
  );
};

export default CompetitionProfileCalendar;
