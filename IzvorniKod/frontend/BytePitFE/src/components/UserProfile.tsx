import React, { useState, useEffect, useContext } from "react";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { UserContext } from "../context/userContext";
import { Tabs, ConfigProvider } from "antd";
import Competition from "./Competition";
import Problems from "./Problems";
import { fetchData } from "../hooks/usersAPI";
import '../styles/UserProfile.css'
import '../styles/Table.css'
interface UserData {
  korisnickoIme: string;
  ime: string;
  prezime: string;
  email: string;
  uloga: string;
}

interface CompetitionData {
  natjecanjeId: number;
  voditelj: string;
  nazivNatjecanja: string;
  pocetakNatjecanja: string;
  krajNatjecanja: string;
}

interface ProblemData {
  voditelj: UserData;
  nazivZadatka: string;
  tekstZadatka: string;
  zadatakId: BigInteger;
}

const UserProfile: React.FC = () => {
  const [imageData, setImageData] = useState<string | null>(null);
  const { user } = useContext(UserContext); //podaci ulogiranog korisnika
  const { korisnickoIme } = useParams();
  const [userData, setUserData] = useState<UserData | null>(null); //podaci korisnika ciji se profil otvara
  const [competitionsData, setCompetitionsData] = useState<CompetitionData[]>(
    []
  );
  const [problemsData, setProblemsData] = useState<ProblemData[]>([]);

  useEffect(() => {
    const fetchProfilePicture = async () => {
      try {
        if (korisnickoIme !== ''){
          const response = await fetch(`/api/user/image/${korisnickoIme}`);
          const blob = await response.blob();
          const imageUrl = URL.createObjectURL(blob);
          setImageData(imageUrl);
        }
      } catch (error) {
        console.error('Error fetching profile picture:', error);
      }
    };
    fetchProfilePicture();
  }, [korisnickoIme]);

  useEffect(() => {
    fetchData(`/api/user/profile/${korisnickoIme}`, user)
      .then((data: UserData) => setUserData(data))
      .catch((error) => {
        console.error("error fetching user profile data:", error);
      });
  }, [korisnickoIme, user]);

  useEffect(() => {
    fetchData(`/api/natjecanja/get/voditelj/${korisnickoIme}`, user)
      .then((data: CompetitionData[]) => setCompetitionsData(data))
      .catch((error) => {
        console.error("error fetching competition data:", error);
      });
  }, [korisnickoIme, user]); //voditelj - natjecanja / moja natjecanja

  useEffect(() => {
    if (
      korisnickoIme === user.korisnickoIme &&
      userData &&
      userData.uloga === "VODITELJ"
    ) {
      fetchData(`/api/problems/my`, user)
        .then((data: ProblemData[]) => setProblemsData(data))
        .catch((error) => {
          console.error("error fetching problem data:", error);
        });
    } else if (
      korisnickoIme !== user.korisnickoIme &&
      userData &&
      userData.uloga === "VODITELJ"
    ) {
      fetch(`/api/problems/author/${korisnickoIme}`)
        .then((response) => response.json())
        .then((data: ProblemData[]) => setProblemsData(data))
        .catch((error) => {
          console.error("error fetching problem data:", error);
        });
    }
  }, [user, userData, korisnickoIme]); //voditelj - moji zadaci (javni i privatni)

  if (!userData) {
    return <p>dohvaćanje korisnika</p>;
  }

  const renderProblemsTab = () => (
    <div className="problemContainer">
      {problemsData ? (
        <div className="tableWrapper">
          <div className="info-table">
            <table>
              <thead>
                <tr>
                  <th>korisničko ime</th>
                  <th>naziv</th>
                  <th>tekst</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {problemsData.map((problem, index) => (
                  <Problems key={index} problem={problem} />
                ))}
              </tbody>
            </table>
          </div>
        </div>
      ) : (
        <p>greška prilikom dohvaćanja zadataka</p>
      )}
    </div>
  );
  const renderCompetitionsTab = () => (
    <div className="competitionContainer">
      {competitionsData ? (
        <div className="tableWrapper">
          <div className="info-table">
            <table>
              <thead>
                <tr>
                  <th>voditelj</th>
                  <th>naziv</th>
                  <th>početak</th>
                  <th>kraj</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {competitionsData.map((competition, index) => (
                  <Competition key={index} competition={competition} />
                ))}
              </tbody>
            </table>
          </div>
        </div>
      ) : (
        <p>greška prilikom dohvaćanja natjecanja</p>
      )}
    </div>
  );

  return (
    <div className = "profileContainer">
      {userData.korisnickoIme === user.korisnickoIme ? (
        <div className="userProfileContainer">
          <h2>moj profil</h2>
            <div className = "userProfile"> 
              <div className="userImg">
                {imageData ? (
                  <img className="userProfileImg" src={imageData} alt="BytePit unregistered user icon" />
                ) : (
                  <img className="userProfileImg" src="/slike/bytepit-usericon.png" alt="Default icon" />
                )}
              </div>
              <div className="userData">
                <p>{userData.korisnickoIme}</p>
                <p>{userData.ime}&nbsp;{userData.prezime}</p>
                <p>{userData.email}</p>
              </div>
            </div>
          {userData.uloga === "ADMIN" && (
            <Link to="/user/listRequested">odobri voditelje</Link>
          )}

          {userData.uloga === "VODITELJ" && (
            <ConfigProvider
              theme={{
                components: {
                  Tabs: {
                    inkBarColor: "#dd7236",
                    itemActiveColor: "#dd7236",
                    itemSelectedColor: "#dd7236",
                    itemHoverColor: "#dd7236",
                  },
                },
              }}
            >
              <Tabs
                defaultActiveKey="1"
                centered
                items={[
                  {
                    label: <p>moji zadaci</p>,
                    key: "1",
                    children: renderProblemsTab(),
                  },
                  {
                    label: <p>moja natjecanja</p>,
                    key: "2",
                    children: renderCompetitionsTab(),
                  },
                ]}
              />
            </ConfigProvider>
          )}
        </div>
      ) : (
        <div>
          <h2>profil korisnika</h2>
          <div className="userProfileWrapper">
            <div className = "userProfile"> 
              <div className="userImg">
                {imageData ? (
                  
                  <img className="userProfileImg" src={imageData} alt="BytePit unregistered user icon" />
                ) : (
                  <img className="userProfileImg" src="/slike/bytepit-usericon.png" alt="Default icon" />
                )}
                </div>
                <div className="userData">
                  <p>{userData.korisnickoIme}</p>
                  <p>{userData.ime}&nbsp;{userData.prezime}</p>
                  <p>{userData.email}</p>
                </div>
              </div>
          </div>
          {userData.uloga === "VODITELJ" && (
            <ConfigProvider
              theme={{
                components: {
                  Tabs: {
                    inkBarColor: "#dd7236",
                    itemActiveColor: "#dd7236",
                    itemSelectedColor: "#dd7236",
                    itemHoverColor: "#dd7236",
                  },
                },
              }}
            >
              <Tabs
                defaultActiveKey="1"
                centered
                items={[
                  {
                    label: <p>zadaci</p>,
                    key: "1",
                    children: renderProblemsTab(),
                  },
                  {
                    label: <p>natjecanja</p>,
                    key: "2",
                    children: renderCompetitionsTab(),
                  },
                ]}
              />
            </ConfigProvider>
          )}
        </div>
      )}
    </div>
  );
};
export default UserProfile;

