import React, {useEffect, useState} from 'react';
import './ConfirmRegAdmin.css';

export interface IUser {
    korisnikId:number;
    korisnickoIme:string;
    ime:string;
    prezime:string;
    email: string;
    uloga: string;
}

interface ConfirmRegAdminProps {
    loggedInUser: string | null;
    loggedInUserPass : string | null;
}

const ConfirmRegAdmin: React.FC<ConfirmRegAdminProps> = ({loggedInUser, loggedInUserPass}) => {
    const [users, setUsers] = useState<IUser[]>([]);


    const handleButtonClick = async (korisnickoIme : string) => {
        console.log(`Button clicked for user: ${korisnickoIme}`);

        const credentials = btoa(`${loggedInUser}:${loggedInUserPass}`);
        try {
            const options = {
                method: 'POST',
                headers: {
                      'Authorization': `Basic ${credentials}`,
                      'Content-Type': 'application/json'
                },
            };
    
            fetch(`/api/user/confirmRequest/${korisnickoIme}`, options).then((response) => {
                if (response.status === 200) {
                    console.log("okej!");
                    setUsers(prevUsers => prevUsers.filter(user => user.korisnickoIme !== korisnickoIme));

                } else {
                    console.log("nije okej!");
                }
            });

          } catch (error) {
            console.error('Error:', error);
          }

    };

    useEffect(() => {
        const credentials = btoa(`${loggedInUser}:${loggedInUserPass}`);
        try {
            const options = {
                method: 'GET',
                headers: {
                      'Authorization': `Basic ${credentials}`,
                      'Content-Type': 'application/json'
                },
            };
        fetch('/api/user/listRequested', options)
            .then(response => response.json())
            .then((data: IUser[]) => setUsers(data))
            .catch(error => console.error('Error fetching users:', error));

        } catch (error) {
            console.error('Error:', error);
          }
    }, [loggedInUser, loggedInUserPass]);

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
