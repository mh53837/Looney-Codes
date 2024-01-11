import React, {useContext, useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import { Table, ConfigProvider } from 'antd';
import '../../styles/Table.css';
import { UserContext } from '../../context/userContext';
import { ThemeContext } from '../../context/themeContext';

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

interface UserData {
  korisnickoIme: string;
  ime: string;
  prezime: string;
  email: string;
  uloga: string;
}

interface ProblemsTabProps {
  problemsData: ProblemData[];
  onUpdate: () => void;
  userData: UserData;
} 

const ProblemUpdateForm = React.lazy(() => import("./ProblemUpdateForm"));
const EvaluationTests = React.lazy(() => import("../Problems/EvaluationTests"));

const ProblemsProfileTab: React.FC<ProblemsTabProps> = ({ problemsData, onUpdate, userData}) => {
  const {user} = useContext(UserContext);
  const [evaluationTestsVisible, setEvaluationTestsVisible] = useState(false);
  const [selectedZadatakId, setSelectedZadatakId] = useState<BigInteger | null>(null);
  
  const {theme} = useContext(ThemeContext);

  const renderTable = (problemData: ProblemData[] | undefined ) => {
    if(!problemData) {
      return;
    }

    return (
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
            ...( (user.uloga === "NATJECATELJ" )   ? [
              {
                title: '',
                dataIndex: 'zadatakId',
                key: 'action',
                className: "th-td",
                render: (zadatakId : BigInteger) => 
                (
                    <Link to={`/problem/${zadatakId}`}>
                      riješi
                    </Link>
                )
              },
            ] :[] ),
            ...( ((user.uloga === "VODITELJ" && user.korisnickoIme === userData.korisnickoIme) || user.uloga === "ADMIN")   ? [
              {
                title: 'status',
                dataIndex: 'privatniZadatak',
                key: 'status',
                className: 'th-td',
                sorter:   (a: { privatniZadatak: boolean }, b: { privatniZadatak: boolean }) =>
                (a.privatniZadatak ? 1 : -1) - (b.privatniZadatak ? 1 : -1),
                render: (privatniZadatak : boolean) => (
                <div>
                  { privatniZadatak && <p>privatni</p> }
                  {!privatniZadatak && <p>javni</p> }
                </div>
                ),
              },
              {
                title: '',
                key: 'edit',
                className: 'th-td',
                render: (data : ProblemData) => (
                  <span>
                    {
                      <React.Suspense fallback={<div>učitavanje...</div>}>
                        <ProblemUpdateForm zadatakId={data.zadatakId ?? 0} onUpdateSuccess={handleUpdateSuccess}/>
                      </React.Suspense>
                    }
                  </span>
                ),
              },
              {
                title: '',
                key: 'tests',
                className: 'th-td',
                render: (data : ProblemData) => (
                  <span>
                  {
                    <React.Suspense fallback={<div>učitavanje...</div>}>
                      <button onClick={() => handleEvaluationTests(data.zadatakId)}>prikaži testove</button>
                    </React.Suspense>
                  }
                </span>
                ),
              }
          ] : [] ),
          ]}
          pagination={false}
          rowKey="zadatakId"
          showSorterTooltip={false}
          style={{ tableLayout: 'fixed' }}
        />
    );
  }
  const handleUpdateSuccess = () => {
    onUpdate();
  };

  const handleEvaluationTests = (zadatakId: BigInteger) => {
    setSelectedZadatakId(zadatakId);
    setEvaluationTestsVisible(true);
  };

  const onCloseEvaluationTests = () => {
    setEvaluationTestsVisible(false);
    setSelectedZadatakId(null);
  };
  useEffect(() => {

  }, [theme.isThemeDark]);

  return (
    <div className="problemContainer">
      {(
        user.korisnickoIme === userData.korisnickoIme &&
        <Link to = "/problems/new">
          <button className="addBtn">novi zadatak</button>
        </Link>
      )}
      
      {problemsData ? (
        problemsData.length === 0 ? ( 
        <div>nema zadataka</div> 
        ) : (
          <div className="tableWrapper">
          <div className="info-table">
            {
              theme.isThemeDark === false ? (
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
                { renderTable(problemsData) }
                </ConfigProvider>
              ) : (
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
                { renderTable(problemsData) }
                </ConfigProvider>
              )
            }
          </div>
        </div>
      )) : (
        <p>greška prilikom dohvaćanja zadataka</p>
      
      )}


      {evaluationTestsVisible && selectedZadatakId && (
        <React.Suspense fallback={<div>učitavanje...</div>}>
          <EvaluationTests
            zadatakId={selectedZadatakId}
            visible={evaluationTestsVisible}
            onClose={onCloseEvaluationTests}
          />
        </React.Suspense>
      )}
    </div>
  );
};

export default ProblemsProfileTab;
