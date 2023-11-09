import React from 'react';
import {getHome} from "../hooks/usersAPI";


const Home: React.FC = () => {
    getHome();
    return (
        <div>
            <div>
            <img src = "../slike/logo-bytepit3.png" alt="BytePit logo" />
            </div>

        </div>
    );
};

export default Home;