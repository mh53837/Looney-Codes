import React, { useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { Table, ConfigProvider } from "antd";
import "../../styles/Table.css";
import "../../styles/UserProfile.css";

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

interface CompetitionProfileCalendarProps {
  competitionsData: CompetitionData[];
  onUpdate: () => void;
}
const CompetitionProfileCalendar: React.FC<CompetitionProfileCalendarProps> = ({competitionsData, onUpdate,}) => {
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);

  const handleUpdateSuccess = () => {
    onUpdate();
  };

  const tileContent = ({ date, view }: { date: Date; view: string }) => {
    if (view === 'month') {
      const filteredCompetitions = competitionsData.filter(
        (competition) =>
        new Date(competition.pocetakNatjecanja).toDateString() ===
        date?.toDateString()
      )
        return filteredCompetitions.length > 0 ? (
            <div>
                {filteredCompetitions.map((competition) => (
                    <p key={competition.natjecanjeId} className="naziv-natjecanje">
                        &#9733;
                    </p>
                ))}
            </div>
        ) : null;
    }

    return null;
};

  return (
    <div className="calendar-container">
      <Calendar
        value={selectedDate}
        onChange={(newDate) => setSelectedDate(newDate as Date)}
        tileContent={tileContent}
      />

      <div className="info-table">
        {selectedDate !== null ? (
          <div>
            <p>označeni datum: {selectedDate.toDateString()}</p>
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
                            },
                          },
                        }}
                      >
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
                              sorter: (a, b) =>
                                a.nazivNatjecanja.localeCompare(
                                  b.nazivNatjecanja
                                ),
                            },
                            {
                              title: "početak",
                              dataIndex: "pocetakNatjecanja",
                              key: "pocetakNatjecanja",
                              className: "th-td",
                              render: (date) => new Date(date).toDateString(),
                            },
                            {
                              title: "kraj",
                              dataIndex: "krajNatjecanja",
                              key: "krajNatjecanja",
                              className: "th-td",
                              render: (date) => new Date(date).toDateString(),
                            },
                            {
                              title: "",
                              key: "edit",
                              className: "th-td",
                              render: (data) => (
                                <span>
                                  {
                                    <React.Suspense
                                      fallback={<div>učitavanje...</div>}
                                    >
                                      <CompetitonUpdateForm
                                        natjecanjeId={data.natjecanjeId ?? 0}
                                        onUpdateSuccess={handleUpdateSuccess}
                                      />
                                    </React.Suspense>
                                  }
                                </span>
                              ),
                            },
                          ]}
                          pagination={false}
                          rowKey="natjecanjeId"
                          showSorterTooltip={false}
                          style={{ tableLayout: "fixed" }}
                        />
                      </ConfigProvider>
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
