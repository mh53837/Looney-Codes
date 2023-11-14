import React, {useEffect, useState} from 'react';
import User from './User';
import './User.css'

export interface IUser {
    korisnikId:number;
    korisnickoIme:string;
    ime:string;
    prezime:string;
    email: string
}

const UserList: React.FC = () => {
    const [users, setUsers] = useState<IUser[]>([]);

    useEffect(() => {
        fetch('/api/user/all')
            .then(response => response.json())
            .then((data: IUser[]) => setUsers(data))
            .catch(error => console.error('Error fetching users:', error));
    }, []);

    return (
        <div className="user-info-table">
            <table>
                <thead>
                <tr>
                    <th>korisničko ime</th>
                    <th>ime</th>
                    <th>prezime</th>
                    <th>email</th>
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
