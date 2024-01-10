import React, { useState, useContext } from "react";
import { Link } from 'react-router-dom';
import { UserContext } from "../../context/userContext";
import { ThemeContext } from "../../context/themeContext";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { Table, ConfigProvider } from "antd";
import "../../styles/Table.css";
import "../../styles/UserProfile.css";
import AddProblemsToCompetition from "../Competition/AddProblemsToCompetition";

const CompetitonUpdateForm = React.lazy(
  () => import("./CompetitionUpdateForm")
);

interface CompetitionData {
  natjecanjeId: number;
  korisnickoImeVoditelja: string;
  nazivNatjecanja: string;
  pocetakNatjecanja: string;
  krajNatjecanja: string;
}

interface UserData {
  korisnickoIme: string;
  ime: string;
  prezime: string;
  email: string;
  uloga: string;
}


interface CompetitionProfileCalendarProps {
  competitionsData: CompetitionData[];
  onUpdate: () => void;
  userData: UserData;
}
const CompetitionProfileCalendar: React.FC<CompetitionProfileCalendarProps> = ({competitionsData, onUpdate, userData}) => {
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);

  const [addProblemsVisible, setAddProblemsVisible] = useState(false);
  const [selectedCompetitionId, setSelectedCompetitionId] = useState<number | null>(null);
  
  const {user} = useContext(UserContext); //podaci ulogiranog korisnika

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

  const handleUpdateSuccess = () => {
    onUpdate();
  };

  const {theme} = useContext(ThemeContext);

  const handleAddProblems = (natjecanjeId: number) => {
    setSelectedCompetitionId(natjecanjeId);
    setAddProblemsVisible(true);
  };

  const onCloseAddProblems = () => {
    setAddProblemsVisible(false);
    setSelectedCompetitionId(null);
  }

  const tileContent = ({ date, view }: { date: Date; view: string }) => {
    if (view === 'month') {
      const filteredCompetitions = competitionsData.filter(
        (competition: CompetitionData) =>
        new Date(competition.pocetakNatjecanja).toDateString() ===
        date?.toDateString()
      )
        return filteredCompetitions.length > 0 ? (
            <div>
                {filteredCompetitions.map((competition: CompetitionData) => (
                    <p style={{fontSize: 15}} key={competition.natjecanjeId} className="naziv-natjecanje">⭐</p>
                ))}
            </div>
        ) : null;
    }

    return null;
};

const renderTable = (filteredData : CompetitionData[] | undefined) => {
  if (!filteredData) {
    return;
  }
  return (
    <Table
      dataSource={filteredData}
      columns={[
        {
          title: "korisničko ime",
          dataIndex: "korisnickoImeVoditelja",
          key: "korisnickoIme",
          className: "th-td",
        },
        {
          title: "naziv",
          dataIndex: "nazivNatjecanja",
          key: "nazivNatjecanja",
          className: "th-td",
          sorter: (a: CompetitionData, b: CompetitionData) =>
              a.nazivNatjecanja.localeCompare(b.nazivNatjecanja),
        },
        {
          title: "početak",
          dataIndex: "pocetakNatjecanja",
          key: "pocetakNatjecanja",
          className: "th-td",
          render: (date: string) => formatirajDatumVrijeme(date),
        },
        {
          title: "kraj",
          dataIndex: "krajNatjecanja",
          key: "krajNatjecanja",
          className: "th-td",
          render: (date: string) => formatirajDatumVrijeme(date),
        },
        ...( ((user.uloga === "VODITELJ" && user.korisnickoIme === userData.korisnickoIme) || user.uloga === "ADMIN")   ? [
          {
            title: "",
            key: "edit",
            className: "th-td",
            render: (data : CompetitionData) => (
              <span>
                    <React.Suspense
                      fallback={<div>učitavanje...</div>}
                    >
                      <CompetitonUpdateForm
                        natjecanjeId={data.natjecanjeId ?? 0}
                        onUpdateSuccess={handleUpdateSuccess}
                      />
                    </React.Suspense>
              </span>
            ),
          },
          {
            title: "",
            key: "problems",
            className: "th-td",
            render: (data : CompetitionData) => (
              <span>
                    <React.Suspense
                      fallback={<div>učitavanje...</div>}
                    >
                      <button onClick = { () => handleAddProblems(data.natjecanjeId)}>zadaci</button>
                    </React.Suspense>
              </span>
            ),
          },
          ] : []),
      ]}
      pagination={false}
      rowKey="natjecanjeId"
      showSorterTooltip={false}
      style={{ tableLayout: "fixed" }}
    />
  )
}

  return (
    <div className="calendar-container">
      {(
        user.korisnickoIme === userData.korisnickoIme &&
        <Link to = "/natjecanja/new">
          <button className="addBtn">novo natjecanje</button>
        </Link>
      )}
      
      <Calendar
        value={selectedDate}
        onChange={(newDate) => setSelectedDate(newDate as Date)}
        tileContent={tileContent}
        locale="hr-HR"
      />

      <div className="info-table">
        {selectedDate !== null ? (
          <div>
            <p>označeni datum: {formatirajDatumVrijeme(selectedDate.toUTCString())}</p>
            {(() => {
              const filteredData = competitionsData?.filter(
                (competition) =>
                  new Date(competition.pocetakNatjecanja).toDateString() ===
                  selectedDate.toDateString()
              );

              if (filteredData?.length === 0) {
                return <p>na označeni datum nema natjecanja</p>;
              } else {
                return (
                  <div className="tableWrapper">
                    <div className="info-table">
                      {
                        theme.isThemeDark == false ? (
                        <ConfigProvider
                          theme={{
                            
                            components: {
                              Table: {
                                headerBg: "#f4c95de7",
                                rowHoverBg: "#f4c95d52",
                                borderColor: "#00000085",
                                headerFilterHoverBg: "#f4c95de7",
                                headerSortActiveBg: "#f4c95de7",
                                headerSortHoverBg: "#f4c95da9",
                                headerBorderRadius: 0,
                                colorPrimary: "#dd7230",
                                headerSplitColor: "transparent",
                                colorBgContainer: "#fff",
                                colorText:"#000"
                              },
                            },
                          }}
                        > 
                        {renderTable(filteredData)}
                        
                        </ConfigProvider>
                      ):(
                        <ConfigProvider
                          theme={{
                            components: {
                              Table: {
                                headerBg: "#dd7230",
                                rowHoverBg: "#dcdcdc34",
                                borderColor: "#00000085",
                                headerFilterHoverBg: "#dd7230e0",
                                headerSortActiveBg: "#dd7230e0",
                                headerSortHoverBg: "#dd7230",
                                headerBorderRadius: 0,
                                colorPrimary: "#f4c95d",
                                headerSplitColor: "transparent",
                                colorBgContainer: "#2A2D34",
                                colorText:" #ECDCC9",
                              },
                            },
                          }}
                        > 
                        {renderTable(filteredData)}
                        
                        </ConfigProvider>
                      )}
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

      {addProblemsVisible && selectedCompetitionId && (
        <React.Suspense fallback={<div>učitavanje...</div>}>
          <AddProblemsToCompetition
            natjecanjeId={selectedCompetitionId}
            visible={addProblemsVisible}
            onClose={onCloseAddProblems}
          />
        </React.Suspense>
      )}
    </div>
  );
};

export default CompetitionProfileCalendar;
