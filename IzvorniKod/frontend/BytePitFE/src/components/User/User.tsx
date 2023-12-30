import React from 'react';
import { Link } from 'react-router-dom';
import '../../styles/Table.css';
interface UserProps {
    user: {
        korisnickoIme: string;
        ime: string;
        prezime: string;
        email: string;
    };
}

const User: React.FC<UserProps> = (props) => {
    const {korisnickoIme, ime, prezime, email } = props.user;

    return (
        <tr className="user-info-header">
        <td>{korisnickoIme}</td>
        <td>{ime}</td>
        <td>{prezime}</td>
        <td>{email}</td>
        <td>
            <Link to = {`/user/profile/${korisnickoIme}`}>
                pogledaj profil
            </Link>
        </td>
        </tr>
    );
};


export default User;
