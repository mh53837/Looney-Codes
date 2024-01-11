import React, { useEffect, useState } from 'react';

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


const UserTrophies: React.FC<TrophyProps> = ({ userData }) => {
  const [trophyData, setTrophyData] = useState<TrophyData[]>([]);
  const [imageData, setImageData] = useState<string[]>([]);

  useEffect(() => {
    if (userData?.uloga === "NATJECATELJ") {
      fetch(`/api/trophies/user/${userData.korisnickoIme}`)
        .then((response) => response.json())
        .then((data: TrophyData[]) => {
          console.log("Trophy data:", data);

          
          const imageUrls = data.map((trophy) => {
            const blob = new Blob([trophy.slikaPehara], { type: 'image/jpeg' });
            return URL.createObjectURL(blob);
          });

          setTrophyData(data);
          setImageData(imageUrls);
        })
        .catch((error) => {
          console.error("error fetching trophy data:", error);
        });
    }
  }, [userData]);

  return (
    <div>
      {trophyData.map((trophy, index) => (
        <div key={trophy.peharId} className="userTrophy">
          <h4>{trophy.natjecanje.nazivNatjecanja}</h4>
          <p>|</p>
          <p>Mjesto: {trophy.mjesto}</p>
          <p>|</p>
          <img src={imageData[index]} alt={`slika pehara`} />
        </div>
      ))}
    </div>
  );
};

export default UserTrophies; 