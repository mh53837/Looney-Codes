import React from 'react';
import { Link } from 'react-router-dom';
import { UserContext } from '../context/userContext';
import { useContext } from 'react';

const UserProfile: React.FC = () => {

    const { user } = useContext(UserContext);


    return (
        <div>
            {user.korisnickoIme !== '' ? (
                <div>
                    <p>Pozdrav, {user.korisnickoIme}!</p>
                    {user.korisnickoIme === 'admin' ? (
                        <Link to="/user/listRequested">odobri voditelje</Link>
                    ) : (<p></p>)}
                </div>
            ) : (
                 <div> prijavi se kako bi vidio svoj korisnički profil </div>
            ) }

        </div>
    );
};

export default UserProfile;
