import UserList from './components/UserList.tsx';
import './App.css';
import Home from './components/Home';
import ProblemsList from './components/ProblemsList.tsx';
import Login from './components/Login.tsx';
import { Navbar } from './layout/Navbar.tsx';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from "./components/Register.tsx";

const App = () => {
    return (
        <Router>
            <Navbar />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/user/all" element={<UserList />} />
                    <Route path="/problems/all" element={<ProblemsList />} />
                    <Route path="/login" element={<Login onLogin={() => console.log('User logged in!')} />} />
                    <Route path="/register" element={<Register onRegister={() => console.log('User registered!')} />} />

                </Routes>
        </Router>
    );   
};

export default App;