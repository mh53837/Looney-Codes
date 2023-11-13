import React from 'react';
import {GetHome} from "../hooks/usersAPI";


const Home: React.FC = () => {
    GetHome();
    return (
        <div>
            bok!
            <br />
            *kalendar*
        </div>
    );
};

export default Home;