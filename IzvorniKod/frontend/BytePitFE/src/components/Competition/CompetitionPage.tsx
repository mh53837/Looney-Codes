import { useParams } from 'react-router-dom';
import ProblemPage from '../Problems/ProblemPage';
import { useEffect, useState, useMemo } from 'react';
import { Link } from 'react-router-dom';
import CountDown from 'react-countdown';
//import Home from '../Home';
import { Navigate } from "react-router-dom";
import '../../styles/CompetitionPage.css';


interface ProblemInfo {
  zadatakId: BigInt;
  zadatakIme: string;
};

interface CompetitionInfo {
  virtualno: boolean;
  krajNatjecanja: Date;
  zadaci: Array<ProblemInfo>;
}

const CompetitionPage: React.FC = () => {
  const { nadmetanjeId, zadatakId } = useParams();
  const [info, setInfo] = useState<CompetitionInfo>();
  const zadatakStranica = useMemo(() => {
    if (zadatakId != undefined && nadmetanjeId != undefined)
      return (<ProblemPage />);
    else
      return ((<h3> Odaberite zadatak. Sretno! </h3>))
  }, [zadatakId]);

  useEffect(() => {
    fetch(`/api/nadmetanja/info/${nadmetanjeId}`)
      .then((data) => data.json())
      .then(data => setInfo(data))
      .catch(() => {
        return (<h3> Greška: natjecanje ne postoji! </h3>);
      })
  }, [nadmetanjeId]);

  if (info === undefined)
    return (<h3> Greška: natjecanje ne postoji! </h3>);

  const zadaci = [];
  for (const zadatak of info?.zadaci.values()) {
    console.log("debug: " + zadatak.zadatakIme);
    zadaci.push((

      <Link to={`/natjecanja/rjesi/${nadmetanjeId}/${zadatak.zadatakId}`}>
        {zadatak.zadatakIme}
      </Link>


    ));
  }





  return (
    <div className='competitionPageWrapper'>
      <div className='competitionHeader'>
        {zadatakId != undefined && <Link to={`/natjecanja/rjesi/${nadmetanjeId}`}>
          ⮈
        </Link>}
        {info.krajNatjecanja && <CountDown date={info.krajNatjecanja} className='countDown'><Navigate to="/" replace={true} /></CountDown>}
      </div>
      {zadatakStranica != null && zadatakStranica}
      <div className="problemMenu">
        <h4> Odaberite zadatak: </h4>
        {zadaci}
      </div>
    </div>
  );
}

export default CompetitionPage;
