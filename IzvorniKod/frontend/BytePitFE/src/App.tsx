import UserList from './components/UserList.tsx';
import { Link } from 'react-router-dom';
import './App.css';
import Home from './components/Home';
import ProblemsList from './components/ProblemsList.tsx';
import ProblemPage from './components/ProblemPage.tsx';
import Login from './components/Login.tsx';
import ConfirmRegAdmin from './components/ConfirmRegAdmin.tsx';
import ConfirmEmail from './components/ConfirmEmail.tsx';
import Navbar from './layout/Navbar.tsx';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Register from "./components/Register.tsx";
import { useContext, useState, useEffect } from 'react';
import { UserContext } from './context/userContext';
import NotFoundPage from "./components/NotFoundPage.tsx"
import UserProfile from './components/UserProfile.tsx';

const App: React.FC = () => {
    const [redirectToHome, setRedirectToHome] = useState<boolean>(false);

    const { user } = useContext(UserContext);
    const { setUser } = useContext(UserContext);

    const handleLogin = ( korisnik_id: number, korisnickoIme: string, lozinka: string, uloga: string ) => {

        setRedirectToHome(true);

        setUser({korisnik_id, korisnickoIme, lozinka, uloga });

        setTimeout(() => {
            setRedirectToHome(false);
        }, 100);
    };

    const handleLogout = () => {
        setRedirectToHome(true);

        setUser({ korisnik_id: 0, korisnickoIme: '', lozinka: '', uloga: ''});

        setTimeout(() => {
            setRedirectToHome(false);
        }, 100);
    };

    useEffect(() => {
        console.log("User in App component:", user);
      }, [user]);

    return (
        <Router>
            {user && (
            <Navbar /* user={{ korisnikIme: user.korisnickoIme, korisnik_id: user.korisnik_id }} */ onLogout={handleLogout} />
            )}
            {redirectToHome && <Navigate to="/" replace={true} />}
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/user/allAdmin" element={<UserList />} />
                <Route path="/problems/all" element={<ProblemsList />} />
                <Route
                    path="/login"
                    element={
                        <div>
                            {user.korisnickoIme !== '' ? (
                                <div>
                                    <Link to="/"></Link>
                                </div>
                            ) : (
                                <Login onLogin={handleLogin} />
                            )}
                        </div>
                    }
                />
                <Route path="/register" element={
                    <div>
                        <Register onRegister={() => console.log('User registered!')} />
                    </div>
                }

                />
                <Route path="/user/get/:id" element={<UserProfile />} />
                <Route path="/user/confirmEmail/:id" element={<ConfirmEmail />} />
                <Route path="/user/listRequested" element={
                    <div>
                        {user ? (
                            <div>
                                <ConfirmRegAdmin loggedInUser={user.korisnickoIme} loggedInUserPass={user.lozinka} />
                            </div>
                        ) : (
                            <p>moras biti prijavljen kao admin</p>)}
                    </div>
                } />
                <Route path="/problem/:id" element={<ProblemPage />} />
                <Route path="*" element={<NotFoundPage />} />
            </Routes>
        </Router>
    );
};

export default App;
