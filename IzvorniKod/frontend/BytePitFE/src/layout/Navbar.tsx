import { Link } from 'react-router-dom';
import './Navbar.css'; // Import the CSS file for styling

export const Navbar = () => {
  return (
    <div className="navbar-container">
      <div className="logo-container">
          <Link to="/">
              <img src="../slike/logo-bytepit3.png" alt="BytePit logo" />
          </Link>
      </div>
        <div className="navbar-options-container">
            <div className="nav-item">
                <Link to="/">
                    <button>kalendar</button>
                </Link>
                <Link to="/user/all">
                    <button>korisnici</button>
                </Link>
                <Link to="/problems/all">
                    <button>zadaci</button>
                </Link>
            </div>
        </div>
    

        <div className="loginDiv">
        <Link to="/login">
            <img className = "userIconImg" src="../slike/bytepit-usericon.png" alt="BytePit unregistered user icon" />
            <button>prijavi se!</button>
        </Link>
        </div>
    </div>
  );
};
