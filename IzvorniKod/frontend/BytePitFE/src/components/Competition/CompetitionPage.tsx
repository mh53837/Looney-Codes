import { useParams } from 'react-router-dom';
import ProblemPage from '../Problems/ProblemPage';
import { useEffect, useState, ReactNode } from 'react';
import { Link } from 'react-router-dom';

interface ProblemInfo {
  zadatakId : BigInt;
  zadatakIme : string;
};

interface CompetitionInfo {
  virtualno : boolean;
  zadaci : Array<ProblemInfo>;
}

const CompetitionPage : React.FC = () => {
  const { nadmetanjeId, zadatakId } = useParams();
  const [ info, setInfo ] = useState<CompetitionInfo>();
  const [ zadatakStranica, setZadatakStranica ] = useState<ReactNode | undefined>();

  useEffect(() => {
      if(zadatakId != undefined && nadmetanjeId != undefined)
        setZadatakStranica(<ProblemPage />);
      else
        setZadatakStranica((<h3> Odaberite zadatak. Sretno! </h3>))
    }, [zadatakId]);

  useEffect(() => {
    fetch(`/api/nadmetanja/info/${nadmetanjeId}`)
    .then((data) => data.json())
    .then(data => setInfo(data))
    .catch(() => {
      console.log("Greška!")
    });
  }, [nadmetanjeId]);

  if(info === undefined) 
    return;

  const zadaci = [];
  for(const zadatak of info?.zadaci.values()) {
    console.log("debug: " + zadatak.zadatakIme);
    zadaci.push((
        <Link to={`/natjecanja/rjesi/${nadmetanjeId}/${zadatak.zadatakId}`}>
        <button> {zadatak.zadatakIme} </button>
        </Link>
        ));
  }

  return  (
      <div>
        {zadatakStranica != null && zadatakStranica}
        <span>
          <h3> Lista zadataka... </h3>
          {zadaci}
        </span>
      </div>
  );
}

export default CompetitionPage;
