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
  korisnickoImeNatjecatelja: string;
  natjecanjeId: number;
  natjecanjeNaziv: string | null;
  mjesto: number;
  slikaPehara: string;
}


const UserTrophies: React.FC<TrophyProps> = ({ userData }) => {
  const [trophyData, setTrophyData] = useState<TrophyData[]>([]);
  const [imageData, setImageData] = useState<string[] | null>(null);
  const [loading, setLoading] = useState(true);


  useEffect(() => {
    if (userData?.uloga === "NATJECATELJ") {
      fetch(`/api/trophies/user/${userData.korisnickoIme}`)
        .then((response) => response.json())
        .then((data: TrophyData[]) => {
          setTrophyData(data);
        })
        .catch((error) => {
          console.error("error fetching trophy data:", error);
        });
    }
  }, [userData]);

  useEffect(() => {
    const fetchCompetitionNames = async () => {
      try {
        if (trophyData.length > 0) {
          const competitionPromises = trophyData.map(async (trophy) => {
            const response = await fetch(`/api/natjecanja/get/${trophy.natjecanjeId}`);
            const data = await response.json();
            return data.nazivNatjecanja; // Return only the competition name
          });
  
          const competitionNames = await Promise.all(competitionPromises);
          
          setTrophyData((prevTrophyData) =>
            prevTrophyData.map((trophy, index) => ({
              ...trophy,
              natjecanjeNaziv: competitionNames[index],
            }))
          );
        }
      } catch (error) {
        console.error("Error fetching competition names:", error);
      }
    };
  
    fetchCompetitionNames();
  }, [trophyData]);
  

  useEffect(() => {
    const fetchTrophyPictures = async () => {
      try {
        if (trophyData.length > 0) {
          const imagePromises = trophyData.map(async (trophy) => {
            const response = await fetch(`/api/trophies/image/${trophy.peharId}`);
            if (response.ok) {
              const blob = await response.blob();
              return URL.createObjectURL(blob);
            } else {
              return '/slike/trophyPlaceholder.png';
            }
          });
          const imageUrls = await Promise.all(imagePromises);
          setImageData(imageUrls);
          setLoading(false);
        }
      } catch (error) {
        console.error("Error fetching trophy pictures:", error);
        setLoading(false);
      }
    };
  
    if (loading) {
      fetchTrophyPictures();
    }
  }, [trophyData, loading]);
  

  return (
    <div>
      {trophyData.length === 0 && (<p>nema osvojenih pehara</p>)}
      {trophyData.map((trophy, index) => (
        <div key={trophy.peharId} className="userTrophy">
          <h4>{trophy.natjecanjeNaziv}</h4>
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