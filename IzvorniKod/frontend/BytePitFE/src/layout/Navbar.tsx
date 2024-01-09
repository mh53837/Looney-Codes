import { Link, useLocation } from "react-router-dom";
import "../styles/Navbar.css";
import React from "react";
import { useEffect, useState, useContext } from "react";
import { UserContext } from "../context/userContext";

interface NavbarProps {
  onLogout: () => void;
}

const Navbar: React.FC<NavbarProps> = (props) => {
  const { user } = useContext(UserContext); //podaci ulogiranog korisnika/*
  const onLogout = props.onLogout;
  const [imageData, setImageData] = useState<string | null>(null);
  const location = useLocation();
  const [isDark, setIsDark] = useState(false);

  useEffect(() => {
    const fetchProfilePicture = async () => {
      try {
        if (user.korisnickoIme !== "") {
          const response = await fetch(`/api/user/image/${user.korisnickoIme}`);
          const blob = await response.blob();
          const imageUrl = URL.createObjectURL(blob);
          setImageData(imageUrl);
        }
      } catch (error) {
        console.error("Error fetching profile picture:", error);
      }
    };
    fetchProfilePicture();
  }, [user.korisnickoIme]);

  useEffect(() => {
    if (!user.korisnickoIme) {
      setImageData("/slike/bytepit-usericon.png");
    }
  }, [user.korisnickoIme]);

  useEffect(() => {
    document.documentElement.setAttribute("data-theme", isDark ? "dark" : "light");
  }, [isDark]);

  return (
    <div className="navbar-container">
      <div className="logo-container">
        <Link to="/">
          <img src="/slike/logo-bytepit.png" alt="BytePit logo" />
        </Link>
      </div>
      <div className="navbar-options-container">
        <Link to="/">
          <button>kalendar</button>
        </Link>
        <Link to="/user/all">
          <button>korisnici</button>
        </Link>
        <Link to="/problems/all">
          <button>zadaci</button>
        </Link>

        <button onClick={() => setIsDark(!isDark)}>
          {isDark ? "☀️" : "🌙"}
        </button>
      </div>

      {user.korisnickoIme ? (
        <div className="loginDiv">
          {location.pathname === `/user/profile/${user.korisnickoIme}` ? ( //ako je navbar na profilu korisnika nek se prikaze odjavi
            <button onClick={onLogout}>odjavi se!</button>
          ) : (
            //inace se prikazuje njegov username
            <Link to={`/user/profile/${user.korisnickoIme}`}>
              {imageData ? (
                <img
                  className="userIconImg"
                  src={imageData}
                  alt="BytePit unregistered user icon"
                />
              ) : (
                <img
                  className="userIconImg"
                  src="/slike/bytepit-usericon.png"
                  alt="Default icon"
                />
              )}
              <p className="korisnikIme"> {user.korisnickoIme}</p>
              <button onClick={onLogout}>odjavi se!</button>
            </Link>
          )}
        </div>
      ) : (
        <div className="loginDiv">
          <Link to="/login">
            <img
              className="userIconImg"
              src="/slike/bytepit-usericon.png"
              alt="BytePit unregistered user icon"
            />
            <button>prijavi se!</button>
          </Link>
        </div>
      )}
    </div>

  );
};

export default Navbar;
