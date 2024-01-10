import { useParams } from 'react-router-dom';
import { useEffect, useState, useMemo, lazy } from 'react';
import { Link } from 'react-router-dom';
import CountDown from 'react-countdown';
import { Navigate } from "react-router-dom";
import '../../styles/CompetitionPage.css';


interface ProblemInfo {
  zadatakId: BigInt;
  zadatakIme: string;
};

interface CompetitionInfo {
  ime: string;
  virtualno: boolean;
  krajNatjecanja: Date;
  zadaci: Array<ProblemInfo>;
}

const ProblemPage = lazy(() => import('../Problems/ProblemPage.tsx'));

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

        {zadatakId != undefined && <span><Link to={`/natjecanja/rjesi/${nadmetanjeId}`}>
          <h2>{info.ime}</h2>
        </Link></span>}

        {info.krajNatjecanja && <CountDown date={info.krajNatjecanja} className='countDown'><Navigate to={`/natjecanja/rezultati/${nadmetanjeId}`} replace={true} /></CountDown>}
      </div>
      {zadatakId === undefined && <h1>{info.ime}</h1>}
      {zadatakStranica != null && zadatakStranica}
      <div className="problemMenu">
        <h4> Odaberite zadatak: </h4>
        {zadaci}
      </div>
    </div>
  );
}

export default CompetitionPage;
