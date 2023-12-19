import React from 'react';
import { Link } from 'react-router-dom';
import './User.css';
interface UserProps {
    user: {
        korisnik_id: number;
        korisnickoIme: string;
        ime: string;
        prezime: string;
        email: string;
    };

}

const User: React.FC<UserProps> = (props) => {
    const {korisnik_id, korisnickoIme, ime, prezime, email } = props.user;

    return (
        <tr className="user-info-header">
        <td>{korisnickoIme}</td>
        <td>{ime}</td>
        <td>{prezime}</td>
        <td>{email}</td>
        <td>
            <Link to = {`/user/get/${korisnik_id}`}>
                pogledaj profil
            </Link>
        </td>
        </tr>
    );
};


export default User;
