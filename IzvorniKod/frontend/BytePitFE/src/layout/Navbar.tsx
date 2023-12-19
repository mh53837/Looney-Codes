import { Link, useLocation } from 'react-router-dom';
import './Navbar.css';
import React from 'react';
import { useEffect, useState, useContext } from 'react';
import { UserContext } from '../context/userContext';


interface NavbarProps {
/*   user:{
    korisnikIme: string | null;
    korisnik_id: number;
  }; */
    onLogout: () => void;
  }

const Navbar: React.FC<NavbarProps> = (props) => {
  const { user } = useContext(UserContext); //podaci ulogiranog korisnika/* 
    /* const { korisnikIme, korisnik_id } = props.user; */ 
    const onLogout = props.onLogout;
    const [imageData, setImageData] = useState<string | null>(null);
    const location = useLocation();

    console.log(location.pathname); 
    console.log(user.korisnik_id); 
    console.log(user.uloga); 
  
    useEffect(() => {
      const fetchProfilePicture = async () => {
        try {
          if (user.korisnickoIme !== ''){
            const response = await fetch(`/api/user/image/${user.korisnickoIme}`);
            const blob = await response.blob();
            const imageUrl = URL.createObjectURL(blob);
            setImageData(imageUrl);
          }
        } catch (error) {
          console.error('Error fetching profile picture:', error);
        }
      };
      fetchProfilePicture();
    }, [user.korisnickoIme, user.korisnik_id]);
  
    useEffect(() => {
      if (!user.korisnickoIme) {
        setImageData("/slike/bytepit-usericon.png");
      }
    }, [user.korisnickoIme, user.korisnik_id]
    );

    return (

    <div className="navbar-container">
      <div className="logo-container">
          <Link to="/">
            <img src="/slike/logo-bytepit3.png" alt="BytePit logo" />
          </Link>
      </div>
        <div className="navbar-options-container">
          <div className="nav-item">
              <Link to="/">
                <button>kalendar</button>
              </Link>
              <Link to="/user/allAdmin">
                <button>korisnici</button>
              </Link>
              <Link to="/problems/all">
                <button>zadaci</button>
              </Link>
            </div>
        </div>
    
        {user.korisnickoIme ? (
        <div className="loginDiv">
          {location.pathname === `/user/get/${user.korisnik_id}` ? ( //ako je navbar na profilu korisnika nek se prikaze odjavi
            <button onClick={onLogout}>odjavi se!</button>
            ) : ( //inace se prikazuje njegov username
            <Link to = {`/user/get/${user.korisnik_id}`}>
              {imageData ? (
                <img className="userIconImg" src={imageData} alt="BytePit unregistered user icon" />
              ) : (
                <img className="userIconImg" src="/slike/bytepit-usericon.png" alt="Default icon" />
              )}
              <p className = "korisnikIme"> { user.korisnickoIme }</p>
              <button onClick={onLogout}>odjavi se!</button>
            </Link>
            )}
        </div>
        ) : (
        <div className="loginDiv">
          <Link to="/login">
            <img className="userIconImg" src="/slike/bytepit-usericon.png" alt="BytePit unregistered user icon" /> 
            <button>prijavi se!</button>
          </Link>
        </div>
      )}

    </div>
  );
};

export default Navbar;