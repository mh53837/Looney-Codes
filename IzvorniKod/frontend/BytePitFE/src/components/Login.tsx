import React, { ChangeEvent, FormEvent, useState } from 'react';
import { Link } from 'react-router-dom';
import './Login.css';

interface LoginProps {
    onLogin: () => void;
}

interface LoginForm {
    korisnickoIme: string;
    lozinka: string;
}

const Login: React.FC<LoginProps> = (props) => {
    const [loginForm, setLoginForm] = useState<LoginForm>({ korisnickoIme: '', lozinka: '' });
    const [error, setError] = useState<string>('');

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
            if (response.status === 200) {
                console.log("Success!");
                props.onLogin();
            } else {
                setError('Login failed!');
            }
        });
    }

    return (
        <div className="login-container">
            <div className="Login">
                <form onSubmit={onSubmit}>
                    <div className="FormRow">
                        <label>korisničko ime</label>
                        <input name="korisnickoIme" onChange={onChange} value={loginForm.korisnickoIme} />
                    </div>
                    <div className="FormRow">
                        <label>lozinka</label>
                        <input name="lozinka" type="password" onChange={onChange} value={loginForm.lozinka} />
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