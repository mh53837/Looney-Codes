import React, {useEffect, useState, useContext} from 'react';
import User from './User';
import './User.css';
import { UserContext } from '../context/userContext';

export interface IUser {
    korisnik_id: number;
    korisnickoIme:string;
    ime:string;
    prezime:string;
    email: string
}

const UserList: React.FC = () => {
    const [users, setUsers] = useState<IUser[]>([]);
    const {user} = useContext(UserContext);

    useEffect(() => {
        const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);
        try {
            const options = {
                method: 'GET',
                headers: {
                    'Authorization': `Basic ${credentials}`,
                    'Content-Type': 'application/json'
                },
            };
            fetch('/api/user/allAdmin', options)
                .then(response => response.json())
                .then((data: IUser[]) => setUsers(data))
                .catch(error => console.error('Error fetching users:', error));
        
        } catch (error) {
            console.error('Error:', error);
        }
    },   
    [user.korisnickoIme, user.lozinka]);

    return (
        <div className="user-info-table">
            <table>
                <thead>
                <tr>
                    <th>korisničko ime</th>
                    <th>ime</th>
                    <th>prezime</th>
                    <th>email</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {users.map((user, index) => (
                    <User key={index} user={user} />
                ))}
                </tbody>
            </table>
            </div>
    );
};

export default UserList;
