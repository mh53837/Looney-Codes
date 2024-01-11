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
  const [imageData, setImageData] = useState<string[] | null>(null);


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

  useEffect(() => {
    const fetchTrophyPictures = async () => {
      try {
        if (trophyData.length > 0) {
          const imagePromises = trophyData.map(async (trophy) => {
            const response = await fetch(`/api/trophies/image/${trophy.peharId}`);
            const blob = await response.blob();
            return URL.createObjectURL(blob);
          });
  
          const imageUrls = await Promise.all(imagePromises);
          setImageData(imageUrls);
        }
      } catch (error) {
        console.error("Error fetching trophy pictures:", error);
      }
    };
  
    fetchTrophyPictures();
  }, [trophyData]);
  

  return (
    <div>
      {trophyData.length === 0 && (<p>nema osvojenih pehara</p>)}
      {trophyData.map((trophy, index) => (
        <div key={trophy.peharId} className="userTrophy">
          <h4>{trophy.natjecanje.nazivNatjecanja}</h4>
          <p>|</p>
          <p>Mjesto: {trophy.mjesto}</p>
          <p>|</p>
          {imageData && (<img className='trophyImg' src={imageData[index]} alt={`slika pehara`} /> )}
        </div>
      ))}
    </div>
  );
};

export default UserTrophies; 