import { Link } from 'react-router-dom';
import './Navbar.css'; // Import the CSS file for styling

export const Navbar = () => {
  return (
    <div className="navbar-container">
      <div className="nav-item">
        <Link to="/">
          <button>home</button>
        </Link>
      </div>
      <div className="nav-item">bok, ja sam nav bar</div>
      <div className="nav-item">
        <Link to="/user/all">
          <button>korisnici</button>
        </Link>
      </div>
    </div>
  );
};
