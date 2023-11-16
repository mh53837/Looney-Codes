import UserList from './components/UserList.tsx';
import './App.css';
import Home from './components/Home';
import ProblemsList from './components/ProblemsList.tsx';
import Login from './components/Login.tsx';
import ConfirmRegAdmin from './components/ConfirmRegAdmin.tsx';
import ConfirmEmail from './components/ConfirmEmail.tsx';
import { Navbar } from './layout/Navbar.tsx';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Register from "./components/Register.tsx";
import { useState } from 'react';

const App: React.FC = () => {
    const [loggedInUser, setLoggedInUser] = useState<string | null>(null);
    const [loggedInUserPass, setLoggedInUserPass] = useState<string | null>(null);
    const [redirectToHome, setRedirectToHome] = useState<boolean>(false);


    const handleLogin = (korisnickoIme: string, lozinka:string) => {
        setLoggedInUser(korisnickoIme);
        setLoggedInUserPass(lozinka);

        setRedirectToHome(true);

        setTimeout(() => {
            setRedirectToHome(false);
        }, 100);
    };

    const handleLogout = () => {
        setLoggedInUser(null);
        setLoggedInUserPass(null);
        setRedirectToHome(true);

        setTimeout(() => {
            setRedirectToHome(false);
        }, 100);
    };

    
    
    return (
        <Router>
            <Navbar loggedInUser={loggedInUser} onLogout={handleLogout}/>
            {redirectToHome && <Navigate to="/" replace={true} />} 
                <Routes>
                <Route path="/" element={<Home loggedInUser={loggedInUser} />} />
                    <Route path="/user/all" element={<UserList />} />
                    <Route path="/problems/all" element={<ProblemsList />} />
                    <Route
                        path="/login"
                        element={
                            <div>
                            {loggedInUser ? (
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
                        {loggedInUser ? (
                            <div>
                            <ConfirmRegAdmin loggedInUser={loggedInUser} loggedInUserPass = {loggedInUserPass}/>
                            </div>
                        ) : (
                            <p>moras biti prijavljen kao admin</p>)}
                        </div>
                    }/>
                                        
                </Routes>
        </Router>
    );   
};

export default App;