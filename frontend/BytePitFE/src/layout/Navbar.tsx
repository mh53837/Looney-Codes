import { Link } from 'react-router-dom';
import './Navbar.css'; // Import the CSS file for styling

export const Navbar = () => {
  return (
    <div className="navbar-container">
      <div className="nav-item">
        <Link to="/">
          <button>home</button>
        </Link>
        <Link to="/user/all">
          <button>korisnici</button>
        </Link>
        <Link to="/problems/all">
          <button>zadaci</button>
        </Link>
      </div>
    </div>
  );
};
