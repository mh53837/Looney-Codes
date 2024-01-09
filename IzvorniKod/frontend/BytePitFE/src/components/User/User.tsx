import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/Table.css';
interface UserProps {
    user: {
        korisnickoIme: string;
        ime: string;
        prezime: string;
        email: string;
        uloga: string;
    };
}

const User: React.FC<UserProps> = (props) => {
    const { korisnickoIme, ime, prezime, email, uloga } = props.user;

    return (
        <tr className="user-info-header">
            <td>
                <Link to={`/user/profile/${korisnickoIme}`}>
                    {korisnickoIme}
                </Link></td>
            <td>{ime}</td>
            <td>{prezime}</td>
            <td>{email}</td>
            {(uloga === "ADMIN" && <td>admin</td>)}
            {(uloga === "VODITELJ" && <td>voditelj</td>)}
            {(uloga === "NATJECATELJ" && <td>natjecatelj</td>)}
        </tr>
    );
};


export default User;
