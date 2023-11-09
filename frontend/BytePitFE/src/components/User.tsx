import React from 'react';
import './User.css';
interface UserProps {
    user: {
        korisnickoIme: string;
        ime: string;
        prezime: string;
        email: string;
    };

}

const User: React.FC<UserProps> = (props) => {
    const { korisnickoIme, ime, prezime, email } = props.user;

    return (
        <tr className="user-info-header">
        <td>{korisnickoIme}</td>
        <td>{ime}</td>
        <td>{prezime}</td>
        <td>{email}</td>
        </tr>
    );
};


export default User;
