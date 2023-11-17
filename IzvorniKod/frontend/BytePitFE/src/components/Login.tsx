import React, { ChangeEvent, FormEvent, useState } from 'react';
import { Link } from 'react-router-dom';
import { useContext } from 'react';
import { UserContext } from '../context/userContext';
import './Login.css';

interface LoginProps {
    onLogin: (korisnickoIme: string, lozinka:string) => void;
}

interface LoginForm {
    korisnickoIme: string;
    lozinka: string;
}

const Login: React.FC<LoginProps> = (props) => {
    const [loginForm, setLoginForm] = useState<LoginForm>({ korisnickoIme: '', lozinka: '' });
    const [error, setError] = useState<string>('');
    const { setUser } = useContext(UserContext)!;

    function onChange(event: ChangeEvent<HTMLInputElement>) {
        const { name, value } = event.target;
        setLoginForm((oldForm) => ({ ...oldForm, [name]: value }));
    }

    function onSubmit(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setError('');

        const credentials = btoa(`${loginForm.korisnickoIme}:${loginForm.lozinka}`);
        const options = {
            method: 'POST',
            headers: {
                  'Authorization': `Basic ${credentials}`,
                  'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginForm),
        };

        fetch('/api/user/login', options).then((response) => {
            e.preventDefault();
            console.log(response.status);
            if (response.status === 200) {
                console.log("Success!");
                setUser({ korisnickoIme: loginForm.korisnickoIme, lozinka: loginForm.lozinka});
                props.onLogin(loginForm.korisnickoIme, loginForm.lozinka);
            } else if (response.status === 401) {
                setError('Neispravno korisničko ime ili lozinka!');
            } else if (response.status === 403) {
                setError('Email adresa nije potvrđena!');
            }  else {
                setError('Greška prilikom prijave, pokušajte ponovno!');
            }
        });
    }

    return (
        <div className="login-container">
            <div className="Login">
                <form onSubmit={onSubmit}>
                    <div className="FormRow">
                        <label>korisničko ime</label>
                        <input name="korisnickoIme" placeholder='korisničko ime' onChange={onChange} value={loginForm.korisnickoIme} />
                    </div>
                    <div className="FormRow">
                        <label>lozinka</label>
                        <input name="lozinka" placeholder='lozinka' type="password" onChange={onChange} value={loginForm.lozinka} />

                    </div>
                    <div className="error">{error}</div>
                    <button type="submit">prijavi se!</button>
                    <Link to="/register">
                        <button type="button">registracija</button>
                    </Link>
                </form>
            </div>
        </div>
    );
};

export default Login;
