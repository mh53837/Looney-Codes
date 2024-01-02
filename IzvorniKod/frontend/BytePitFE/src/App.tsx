import React, {Suspense} from 'react';
import UserList from './components/User/UserList.tsx';
import { Link } from 'react-router-dom';
import './styles/App.css';
import Home from './components/Home';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useContext, useState } from 'react';
import { UserContext } from './context/userContext';

const NewProblem = React.lazy(() => import('./components/Problems/NewProblem.tsx'));
const NewCompetition = React.lazy(() => import('./components/Competition/NewCompetiton.tsx'));
const NotFoundPage = React.lazy(() => import('./components/NotFoundPage.tsx'));
const UserProfile = React.lazy(() => import('./components/UserProfile/UserProfile.tsx'));
const Register = React.lazy(() => import('./components/Register.tsx'));
const Navbar = React.lazy(() => import('./layout/Navbar.tsx'));
const ConfirmEmail = React.lazy(() => import('./components/ConfirmEmail.tsx'));
const ConfirmRegAdmin = React.lazy(() => import('./components/ConfirmRegAdmin.tsx'));
const Login = React.lazy(() => import('./components/Login.tsx'));
const ProblemPage = React.lazy(() => import('./components/Problems/ProblemPage.tsx'));
const ProblemsList = React.lazy(() => import('./components/Problems/ProblemsList.tsx'));

const App: React.FC = () => {
    const [redirectToHome, setRedirectToHome] = useState<boolean>(false);

    const { user } = useContext(UserContext);
    const { setUser } = useContext(UserContext);

    const handleLogin = (korisnickoIme: string, lozinka: string, uloga: string ) => {
        setRedirectToHome(true);
        setUser({korisnickoIme, lozinka, uloga });
        setTimeout(() => {
            setRedirectToHome(false);
        }, 100);
    };

    const handleLogout = () => {
        setRedirectToHome(true);
        setUser({korisnickoIme: '', lozinka: '', uloga: ''});
        setTimeout(() => {
            setRedirectToHome(false);
        }, 100);
        
    };

    return (
        <Router>
            {user && (
            <Navbar onLogout={handleLogout} />
            )}
            {redirectToHome && <Navigate to="/" replace={true} />}
            <Suspense fallback={<div>Loading...</div>}>
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
                <Route path="/user/profile/:korisnickoIme" element={<UserProfile />} />
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
                <Route path="/problems/new" element={<NewProblem onNewProblemCreated={() => console.log('kreiran je novi zadatak!')} />} />
                <Route path="/natjecanja/new" element={<NewCompetition onNewCompetitionCreated={() => console.log('kreirano je novo natjecanje!')} />} />
                <Route path="*" element={<NotFoundPage />} />
            </Routes>
            </Suspense>
        </Router>
    );
};

export default App;
