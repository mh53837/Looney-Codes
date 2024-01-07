import React, {useEffect, useState} from 'react';

interface TrophyProps {
    userData: UserData;
}

interface UserData {
    korisnickoIme: string;
    ime: string;
    prezime: string;
    email: string;
    uloga: string;
}

interface TrophyData {
    peharId: number;
    natjecatelj: UserData;
    natjecanje: CompetitionData;
    mjesto: number;
    slikaPehara: string;
}

interface CompetitionData {
    natjecanjeId: number;
    korisnickoImeVoditelja: string;
    nazivNatjecanja: string;
    pocetakNatjecanja: string;
    krajNatjecanja: string;
  }


const UserTrophies: React.FC<TrophyProps> = ({userData}) => {
    const [trophyData, setTrophyData] = useState<TrophyData[]>([]);

    useEffect(() => {
        if (userData?.uloga === "NATJECATELJ") {
        fetch(`/api/trophies/user/${userData.korisnickoIme}`)
            .then((response) => response.json())
            .then((data: TrophyData[]) => {
            console.log("Trophy data:", data);
            setTrophyData(data);
            })
            .catch((error) => {
            console.error("error fetching trophy data:", error);
            });
        }
    }, [userData]);

    return (
        <div>
      {trophyData.map((trophy) => (

        <div key={trophy.peharId}>
          <h3>{trophy.natjecanje.nazivNatjecanja}</h3>
          <p>Mjesto: {trophy.mjesto}</p>
          <img src={trophy.slikaPehara} alt={`slika pehara`} />
        </div>
      ))}
    </div>
    )
};

export default UserTrophies; 