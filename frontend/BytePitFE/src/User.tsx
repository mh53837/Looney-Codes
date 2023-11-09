function User(props){
    const {korisnikId, korisnickoIme} = props.user;

    return (
        <p>{korisnikId} {korisnickoIme} </p>
    );
}

export default User;