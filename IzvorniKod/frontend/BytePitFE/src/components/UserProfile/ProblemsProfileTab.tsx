import React from 'react';
import { Link } from 'react-router-dom';
import { Table, ConfigProvider } from 'antd';
import '../../styles/Table.css';

interface ProblemData {
  voditelj: string;
  nazivZadatka: string;
  tekstZadatka: string;
  zadatakId: BigInteger;
  brojBodova: number;
  privatniZadatak: boolean;
  tezinaZadatka: string;
  vremenskoOgranicenje: number;
}

interface ProblemsTabProps {
  problemsData: ProblemData[];
  onUpdate: () => void;
}

const ProblemUpdateForm = React.lazy(() => import("./ProblemUpdateForm"));

const ProblemsProfileTab: React.FC<ProblemsTabProps> = ({ problemsData, onUpdate }) => {

  const handleUpdateSuccess = () => {
    onUpdate();
  };

  return (
    <div className="problemContainer">
      {problemsData ? (
        problemsData.length === 0 ? ( 
        <div>nema zadataka</div> 
        ) : (
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
                    headerSplitColor: "transparent"
                  },
                },
              }}
            >
            <Table
              dataSource={problemsData}
              columns={ [
                {
                  title: 'korisničko ime',
                  dataIndex: 'voditelj',
                  key: 'korisnickoIme',
                  className: "th-td"
                },
                {
                  title: 'naziv',
                  dataIndex: 'nazivZadatka',
                  key: 'nazivZadatka',
                  className: "th-td",
                  sorter: (a, b) => a.nazivZadatka.localeCompare(b.nazivZadatka),
                },
                {
                  title: 'tekst',
                  dataIndex: 'tekstZadatka',
                  key: 'tekstZadatka',
                  className: "th-td"
                },
                {
                  title: 'broj bodova',
                  dataIndex: 'brojBodova',
                  key: 'brojBodova',
                  className: "th-td",
                  sorter: (a, b) => a.brojBodova - b.brojBodova,
                },
                {
                  title: 'vremensko ograničenje',
                  dataIndex: 'vremenskoOgranicenje',
                  key: 'vremenskoOgranicenje',
                  className: "th-td",
                  sorter: (a, b) => a.vremenskoOgranicenje - b.vremenskoOgranicenje,
                },
                {
                  title: '',
                  dataIndex: 'zadatakId',
                  key: 'action',
                  className: "th-td",
                  render: (zadatakId) => <Link to={`/problem/${zadatakId}`}>riješi</Link>,
                },
                {
                  title: '',
                  key: 'edit',
                  className: 'th-td',
                  render: (data) => (
                    <span>
                      {<React.Suspense fallback={<div>učitavanje...</div>}>
                        <ProblemUpdateForm zadatakId={data.zadatakId ?? 0} onUpdateSuccess={handleUpdateSuccess}/>
                      </React.Suspense>}
                    </span>
                  ),
                },
              ]}
              pagination={false}
              rowKey="zadatakId"
              showSorterTooltip={false}
              style={{ tableLayout: 'fixed' }}
            />
            </ConfigProvider>
          </div>
        </div>
      )) : (
        <p>greška prilikom dohvaćanja zadataka</p>
      
      )}
    </div>
  );
};

export default ProblemsProfileTab;
