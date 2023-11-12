import React from 'react';
import { Link } from 'react-router-dom';
import {getHome} from "../hooks/usersAPI";


const Home: React.FC = () => {
    getHome();
    return (
        <div>
            <div>
            <img src = "../slike/logo-bytepit3.png" alt="BytePit logo" />
            </div>
            <Link to="/login">
                <button>Login</button>
            </Link>
            <Link to="/register">
                <button>Register</button>
            </Link>
        </div>
    );
};

export default Home;