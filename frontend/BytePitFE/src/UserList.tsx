import App from "./App";
import React from "react";
import User from './User';
function UserList() {
    const [users, setUsers] = React.useState([]);

    React.useEffect(() => {
        fetch('/api/user/all')
            .then(data => data.json())
            .then(users => setUsers(users))
    }, []);

    return (
        <div className='User'> {
            users.map(user =>
                <User key = {user.korisnikId} user = {user}/>
            )
        }
        </div>
    );
}

export default UserList