import React, {useEffect, useState, useContext} from 'react';
import '../../styles/Table.css';
import { UserContext } from '../../context/userContext';

export interface IUser {
    korisnickoIme:string;
    ime:string;
    prezime:string;
    email: string;
    uloga: string;
}

const User = React.lazy(() => import('./User'));

const UserList: React.FC = () => {
    const [users, setUsers] = useState<IUser[]>([]);
    const {user} = useContext(UserContext);

    useEffect(() => {
        if(user.uloga === "ADMIN"){
            try {
                const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);
                const options = {
                    method: "GET",
                    headers: {
                      Authorization: `Basic ${credentials}`,
                      "Content-Type": "application/json",
                    },
                  };
                fetch('/api/user/allAdmin', options)
                    .then(response => response.json())
                    .then((data: IUser[]) => setUsers(data))
                    .catch(error => console.error('Error fetching users:', error));
            
            } catch (error) {
                console.error('Error:', error);
            }
        }
        else {
            try {
                fetch('/api/user/all')
                    .then(response => response.json())
                    .then((data: IUser[]) => setUsers(data))
                    .catch(error => console.error('Error fetching users:', error));
            
            } catch (error) {
                console.error('Error:', error);
            }
        }
        
    },   
    [user]);

    return (
        <div className="info-table">
            <table>
                <thead>
                <tr>
                    <th>korisničko ime</th>
                    <th>ime</th>
                    <th>prezime</th>
                    <th>email</th>
                    <th>uloga</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {users.map((userList, index) => (
                    <React.Suspense fallback={<div>učitavanje...</div>}>
                        <User key={index} user={userList} />
                    </React.Suspense>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default UserList;
