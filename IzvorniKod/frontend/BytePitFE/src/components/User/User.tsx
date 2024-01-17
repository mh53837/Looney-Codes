import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import '../../styles/Table.css';
import { UserContext } from '../../context/userContext';
interface UserProps {
    user: {
        korisnickoIme: string;
        ime: string;
        prezime: string;
        email: string;
        uloga: string;
    };
}
const UserProfileUpdateForm = React.lazy(() => import("../../components/UserProfile/UserProfileUpdateForm"));
const User: React.FC<UserProps> = (props) => {
    const { korisnickoIme, ime, prezime, email, uloga } = props.user;
    const {user} = useContext(UserContext);

    const handleProfileUpdate = () => { };
                  
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

            {user.uloga === "ADMIN" && (
            <td>
                <UserProfileUpdateForm
                    korisnickoIme={korisnickoIme ?? ""}
                    onUpdateSuccess={handleProfileUpdate}
                />
            </td>
            )}
        </tr>
    );
};


export default User;
