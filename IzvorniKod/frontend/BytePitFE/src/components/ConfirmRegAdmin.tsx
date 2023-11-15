import React, {useEffect, useState} from 'react';
import './ConfirmRegAdmin.css';

export interface IUser {
    korisnikId:number;
    korisnickoIme:string;
    ime:string;
    prezime:string;
    email: string
}

const ConfirmRegAdmin: React.FC = () => {
    const [users, setUsers] = useState<IUser[]>([]);

    const handleButtonClick = async (korisnickoIme : string) => {
        console.log(`Button clicked for user: ${korisnickoIme}`);

        try {
            const response = await fetch(`/api/confirmRequest/${korisnickoIme}`);
            if (response.ok) {
              const data = await response.json();
              console.log('Response data:', data);
            } else {
              console.error('Error fetching data:', response.statusText);
            }
          } catch (error) {
            console.error('Error:', error);
          }

    };

    useEffect(() => {
        fetch('/api/user/listRequested')
            .then(response => response.json())
            .then((data: IUser[]) => setUsers(data))
            .catch(error => console.error('Error fetching users:', error));
    }, []);

    return (
        <div className="user-info-table">
            <p>korisnici kojima treba potvrditi ulogu voditelja:</p>
            {users.length === 0 ? (
                <p>trenutno nema korisnika kojima treba treba potvrditi ulogu voditelja!</p>
                ) : (
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
                            <tr key={index}>
                                <td>{user.korisnickoIme}</td>
                                <td>{user.ime}</td>
                                <td>{user.prezime}</td>
                                <td>{user.email}</td>
                                <td className='tdBtnPotvrdi'>
                                <button className='btnPotvrdi' onClick={() => handleButtonClick(user.korisnickoIme)}>potvrdi</button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                )}
            </div>
        );
    };

export default ConfirmRegAdmin;
