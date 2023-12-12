import UserList from './components/UserList.tsx';
import './App.css';
import Home from './components/Home';
import ProblemsList from './components/ProblemsList.tsx';
import ProblemPage from './components/ProblemPage.tsx';
import Login from './components/Login.tsx';
import ConfirmRegAdmin from './components/ConfirmRegAdmin.tsx';
import ConfirmEmail from './components/ConfirmEmail.tsx';
import { Navbar } from './layout/Navbar.tsx';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Register from "./components/Register.tsx";
import { useContext, useState } from 'react';
import { UserContext } from './context/userContext';
import NotFoundPage from "./components/NotFoundPage.tsx"

const App: React.FC = () => {
    const [redirectToHome, setRedirectToHome] = useState<boolean>(false);

    const { user } = useContext(UserContext);
    const { setUser } = useContext(UserContext);


    const handleLogin = (korisnickoIme: string, lozinka: string) => {

        setRedirectToHome(true);

        setUser({ korisnickoIme, lozinka });

        setTimeout(() => {
            setRedirectToHome(false);
        }, 100);
    };

    const handleLogout = () => {
        setRedirectToHome(true);

        setUser({ korisnickoIme: '', lozinka: '' });

        setTimeout(() => {
            setRedirectToHome(false);
        }, 100);
    };



    return (
        <Router>
            <Navbar loggedInUser={user.korisnickoIme} onLogout={handleLogout} />
            {redirectToHome && <Navigate to="/" replace={true} />}
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/user/all" element={<UserList />} />
                <Route path="/problems/all" element={<ProblemsList />} />
                <Route
                    path="/login"
                    element={
                        <div>
                            {user.korisnickoIme !== '' ? (
                                <div>
                                    <button onClick={handleLogout}>odjavi se!</button>
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
