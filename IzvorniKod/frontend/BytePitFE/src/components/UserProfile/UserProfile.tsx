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
const UserProfileUpdateForm = React.lazy(() => import("./UserProfileUpdateForm"));
const UserTrophies = React.lazy(() => import("./UserTrophies"));

const UserProfile: React.FC = () => {
  const [imageData, setImageData] = useState<string | null>(null);
  const { user } = useContext(UserContext); //podaci ulogiranog korisnika
  const { korisnickoIme } = useParams();
  const [userData, setUserData] = useState<UserData | null>(null); //podaci korisnika ciji se profil otvara
  const [problemsData, setProblemsData] = useState<ProblemData[]>([]);
  const [competitionsData, setCompetitionsData] = useState<CompetitionData[]>([]);
  const [attempted, setAttempted] = useState<number>(0);
  const [solved, setSolved] = useState<number>(0);

  const handleCompetitionUpdate = () => {
    if (userData?.uloga === "VODITELJ") {
      fetch(`/api/natjecanja/get/voditelj/${korisnickoIme}`)
      .then((response) => response.json() )
      .then((data: CompetitionData[]) => {
        console.log("Competition data:", data);
        setCompetitionsData(data);
      })
      .catch((error) => {
        console.error("error fetching competition data:", error);
      });
    }
  };
  const handleProfileUpdate = () => {
    fetchData(`/api/user/profile/${korisnickoIme}`, user)
      .then((data: UserData) => setUserData(data))
      .catch((error) => {
        console.error("error fetching user profile data:", error);
      });
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
    if(userData?.uloga === "NATJECATELJ"){
      fetch(`/api/problems/get/${korisnickoIme}/allTasks`)
        .then((response) => response.json())
        .then((data) => {setAttempted(data.length), console.log(data) })
        .catch((error) => {
          console.error("error fetching attempted data:", error);
        }
      );

    }
  })
  useEffect(() => {
    if(userData?.uloga === "NATJECATELJ"){
      fetch(`/api/problems/get/${korisnickoIme}/allSolvedTasks`)
        .then((response) => response.json())
        .then((data) => {setSolved(data.length), console.log(data) })
        .catch((error) => {
          console.error("error fetching attempted data:", error);
        }
      );
    } 
  })

  useEffect(() => {
    const fetchProfilePicture = async () => {
      try {
        if (korisnickoIme !== "") {
          const response = await fetch(`/api/user/image/${korisnickoIme}`);
          console.log("img:" ,response);
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
    fetch(`/api/user/profile/${korisnickoIme}`)
    .then((response) => response.json())
    .then((data: UserData) => {
      console.log("User data:", data);
      setUserData(data); })
    .catch((error) => {
      console.error("error fetching user profile data:", error);
    });
  }, [korisnickoIme, user]);

  useEffect(() => {
    if (userData?.uloga === "VODITELJ") {
      fetch(`/api/natjecanja/get/voditelj/${korisnickoIme}`)
        .then((response) => response.json())
        .then((data: CompetitionData[]) => {
          console.log("Competition data:", data);
          setCompetitionsData(data);
        })
        .catch((error) => {
          console.error("error fetching competition data:", error);
        });
    }
  }, [korisnickoIme, userData]);

  useEffect(() => {
    if ( userData && userData.uloga === "VODITELJ") {
      if(user && korisnickoIme === user.korisnickoIme) {
        fetchData(`/api/problems/my`, user)
          .then((data: ProblemData[]) => {
            console.log("Problems data:", data);
            setProblemsData(data)})
          .catch((error) => {
            console.error("error fetching problem data:", error);
        });
      }
      else if (korisnickoIme !== user.korisnickoIme ) {
        fetch(`/api/problems/author/${korisnickoIme}`)
          .then((response) => response.json())
          .then((data: ProblemData[]) => {
            console.log("Problems data:", data);
            setProblemsData(data)})
          .catch((error) => {
            console.error("error fetching problem data:", error);
        });
      }
    }
  }, [user, userData, korisnickoIme]); //voditelj - moji zadaci (javni i privatni)

  if (!userData) {
    return <p>dohvaćanje korisnika</p>;
  }

  const renderUserTrophies = () => (
    <React.Suspense fallback={<div>učitavanje...</div>}>
      <UserTrophies userData = {userData}/>
    </React.Suspense>
  );

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

        {user.uloga === "ADMIN" && (
          <div>
          {
              <React.Suspense fallback={<div>učitavanje...</div>}>
                <UserProfileUpdateForm korisnickoIme={userData.korisnickoIme ?? ""} onUpdateSuccess={handleProfileUpdate}/>
              </React.Suspense>
          }
          </div>
        )}

        {user.uloga === "ADMIN" && userData.uloga === "ADMIN" && (
          <Link to="/user/listRequested"><button>odobri voditelje</button></Link>
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
            <p>broj točno riješenih zadataka: {solved } </p>
            <p>broj isprobanih zadataka: { attempted } </p>
            <p>osvojeni pehari: {renderUserTrophies()} </p>
          </div>
        )}
      </div>
    </div>
  );
};
export default UserProfile;
