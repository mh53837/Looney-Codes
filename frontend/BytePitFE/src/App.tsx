import UserList from './components/UserList.tsx';
import './App.css';
import Home from './components/Home';
import ProblemsList from './components/ProblemsList.tsx';
import { Navbar } from './layout/Navbar.tsx';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

const App = () => {
    return (
        <Router>
            <Navbar />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/user/all" element={<UserList />} />
                    <Route path="/problems/all" element={<ProblemsList />} />
                </Routes>
        </Router>
    );   
};

export default App;