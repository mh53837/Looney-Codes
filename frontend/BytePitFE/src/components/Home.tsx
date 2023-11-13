import React from 'react';
import { Link } from 'react-router-dom';
import {getHome} from "../hooks/usersAPI";


const Home: React.FC = () => {
    getHome();
    return (
        <div>
            <Link to="/login">
                <button>prijavi se!</button>
            </Link>
            <Link to="/register">
                <button>registriraj se!</button>
            </Link>
        </div>
    );
};

export default Home;