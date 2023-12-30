import React, { useState, useEffect, useContext } from "react";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { UserContext } from "../../context/userContext";
import { Tabs, ConfigProvider } from "antd";
import { fetchData } from "../../hooks/usersAPI";
import UserProfileHeader from "./UserProfileHeader";
import "../../styles/UserProfile.css";
import "../../styles/Table.css";

interface UserData {
  korisnickoIme: string;
  ime: string;
  prezime: string;
  email: string;
  uloga: string;
}

interface CompetitionData {
  natjecanjeId: number;
  korisnickoImeVoditelja: string;
  nazivNatjecanja: string;
  pocetakNatjecanja: string;
  krajNatjecanja: string;
}

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
const ProblemsProfileTab = React.lazy(() => import("./ProblemsProfileTab"));
const CompetitionProfileCalendar = React.lazy(() => import("./CompetitionProfileCalendar"));

const UserProfile: React.FC = () => {
  const [imageData, setImageData] = useState<string | null>(null);
  const { user } = useContext(UserContext); //podaci ulogiranog korisnika
  const { korisnickoIme } = useParams();
  const [userData, setUserData] = useState<UserData | null>(null); //podaci korisnika ciji se profil otvara
  const [problemsData, setProblemsData] = useState<ProblemData[]>([]);
  const [competitionsData, setCompetitionsData] = useState<CompetitionData[]>([]);

  const handleCompetitionUpdate = () => {
    if (userData?.uloga === "VODITELJ") {
      fetchData(`/api/natjecanja/get/voditelj/${korisnickoIme}`, user)
        .then((data: CompetitionData[]) => {
          console.log("Competition data:", data);
          setCompetitionsData(data);
        })
        .catch((error) => {
          console.error("error fetching competition data:", error);
        });
    }
  };

  const handleProblemUpdate = () => {
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
  }

  useEffect(() => {
    const fetchProfilePicture = async () => {
      try {
        if (korisnickoIme !== "") {
          const response = await fetch(`/api/user/image/${korisnickoIme}`);
          const blob = await response.blob();
          const imageUrl = URL.createObjectURL(blob);
          setImageData(imageUrl);
        }
      } catch (error) {
        console.error("Error fetching profile picture:", error);
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
    if (userData?.uloga === "VODITELJ") {
      fetchData(`/api/natjecanja/get/voditelj/${korisnickoIme}`, user)
        .then((data: CompetitionData[]) => {
          console.log("Competition data:", data);
          setCompetitionsData(data);
        })
        .catch((error) => {
          console.error("error fetching competition data:", error);
        });
    }
  }, [korisnickoIme, user, userData]); //voditelj - natjecanja / moja natjecanja

  useEffect(() => {
    if (
      korisnickoIme === user.korisnickoIme &&
      userData &&
      userData.uloga === "VODITELJ"
    ) {
      fetchData(`/api/problems/my`, user)
        .then((data: ProblemData[]) => {
          console.log("Problems data:", data);
          setProblemsData(data)})
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
        .then((data: ProblemData[]) => {
          console.log("Problems data:", data);
          setProblemsData(data)})
        .catch((error) => {
          console.error("error fetching problem data:", error);
        });
    }
  }, [user, userData, korisnickoIme]); //voditelj - moji zadaci (javni i privatni)

  if (!userData) {
    return <p>dohvaćanje korisnika</p>;
  }

  const renderProblemsTab = () => (
    <React.Suspense fallback={<div>učitavanje...</div>}>
      <ProblemsProfileTab problemsData={problemsData} onUpdate={handleProblemUpdate} userData = {userData}/>
    </React.Suspense>
  );
  const renderCompetitionsCalendar = () => {
    if (!competitionsData) {
      return <div>Loading...</div>;
    }
    return (
      <React.Suspense fallback={<div>Loading...</div>}>
        <CompetitionProfileCalendar
          competitionsData={competitionsData}
          onUpdate={handleCompetitionUpdate}
          userData={userData}
        />
      </React.Suspense>
    );
  };

  return (
    <div className="profileContainer">
      <div className="userProfileContainer">
        <h2>
          {userData.korisnickoIme === user.korisnickoIme
            ? "moj profil"
            : "profil korisnika"}
        </h2>
        <UserProfileHeader imageData={imageData} userData={userData} />

        {user.uloga === "ADMIN" && userData.uloga === "ADMIN" && (
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
                  label: <p> {userData.korisnickoIme === user.korisnickoIme ? "moji zadaci" : "zadaci"} </p>,
                  key: "1",
                  children: renderProblemsTab(),
                },
                {
                  label: <p>{userData.korisnickoIme === user.korisnickoIme ? "moja natjecanja" : "natjecanja"}</p>,
                  key: "2",
                  children: renderCompetitionsCalendar(),
                },
              ]}
            />
          </ConfigProvider>
        )}

        {userData.uloga === "NATJECATELJ" && (
          <div>
            <p>broj točno riješenih zadataka: </p>
            <p>broj isprobanih zadataka: </p>
            <p>osvojeni pehari:</p>
          </div>
        )}
      </div>
    </div>
  );
};
export default UserProfile;
