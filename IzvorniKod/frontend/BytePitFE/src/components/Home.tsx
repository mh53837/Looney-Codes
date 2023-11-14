import React from 'react';
import { Link } from 'react-router-dom';
import {GetHome} from "../hooks/usersAPI";


const Home: React.FC = () => {
    GetHome();
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