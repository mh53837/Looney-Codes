import UserList from './components/UserList.tsx';
import './App.css';
import Home from './components/Home';
import ProblemsList from './components/ProblemsList.tsx';
import Login from './components/Login.tsx';
import { Navbar } from './layout/Navbar.tsx';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from "./components/Register.tsx";
import { useState } from 'react';

const App = () => {
    const [loggedInUser, setLoggedInUser] = useState<string | null>(null);

    const handleLogin = (korisnickoIme: string) => {
        setLoggedInUser(korisnickoIme);
    };

    const handleLogout = () => {
        setLoggedInUser(null);
    };
    
    return (
        <Router>
            <Navbar />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/user/all" element={<UserList />} />
                    <Route path="/problems/all" element={<ProblemsList />} />
                    <Route path="/login" element={
                    <div>
                        {loggedInUser ? (
                            <div>
                                <p>pozdrav, {loggedInUser}!</p>
                                <button onClick={handleLogout}>odjavi se!</button>
                                {/*  */}
                            </div>
                        ) : (
                            <Login onLogin={handleLogin} />
                        )}
                    </div>} />
                    <Route path="/register" element={<Register onRegister={() => console.log('User registered!')} />} />
                </Routes>
        </Router>
    );   
};

export default App;