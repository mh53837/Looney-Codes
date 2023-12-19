import React, { useState, useEffect, useContext } from "react";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { UserContext } from '../context/userContext';
import { Tabs, ConfigProvider } from 'antd';

//ne radi jer se id s backenda vraca ko undefined
//radi kad se rucno unese broj id korisnika u rutu
//zbog backenda pristup profilima ima samo admin

interface UserData {
    korisnickoIme: string;
    Ime: string;
    prezime: string;
    email: string;
    uloga: string;
  }

const UserProfile: React.FC = () => {
  const { user } = useContext(UserContext); //podaci ulogiranog korisnika 
  const { id } = useParams();
  const [userData, setUserData] = useState<UserData | null>(null); //podaci korisnika ciji se profil otvara

  
  useEffect(() => {
    
    const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);
    try {
      const options = {
        method: 'GET',
        headers: {
          'Authorization': `Basic ${credentials}`,
          'Content-Type': 'application/json'
        },
      };
    fetch(`/api/user/get/${id}`, options)
      .then((response) => response.json())
      .then((data:UserData) => setUserData(data))
      .catch((error) => {
        console.error("error fetching user data:", error);
      });
    } catch (error) {
      console.error('Error:', error);
    }

  }, [id, user.korisnickoIme, user.lozinka]);

  if (!userData) {
    return <p>korisnik ne postoji ili mu se ne može pristupiti</p>;
  }

  return (
    <div>
      <p>{userData.korisnickoIme}</p> 
      <p>{userData.Ime}</p> 
      <p>{userData.prezime}</p> 
      <p>{userData.email}</p> 
      {(userData.korisnickoIme === user.korisnickoIme && userData.uloga === 'ADMIN') ? //ako korisnik pristupa svom profilu i ako je korisnik admin
      (
        <div>
          <Link to="/user/listRequested">odobri voditelje</Link>
        </div>
      ) : ( //ako korisnik pristupa tudjem profilu
      <div>
      </div>
      )}

      {userData.uloga === 'NATJECATELJ' ? ( //profil natjecatelja
      //prikaz statistike natjecatelja
        <div> 
            
        </div>
      ) : (
        <div> </div>
      ) }
      {userData.uloga === 'VODITELJ' ? ( //profil voditelj
      //prikaz zadataka, prikaz natjecatelja
        <div> 
          <ConfigProvider
            theme={{
              components: {
                Tabs: {
                  inkBarColor: '#dd7236',
                  itemActiveColor:  '#dd7236',
                  itemSelectedColor: '#dd7236',
                  itemHoverColor:'#dd7236',
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
                key: '1',
                children: (
                  <div>
                    <p>* tablica zadataka *</p>
                  </div>
                ),
              },
              {
                label:<p>moja natjecanja</p>,
                key: '2',
                children: (
                  <div>
                    <p>* tablica natjecanja *</p>
                  </div>
                ),
              },
            ]}
          />
          </ConfigProvider>
          
        </div>
      ) : (
        <div> </div>
      ) }
      
    </div>
  );
};

export default UserProfile;
