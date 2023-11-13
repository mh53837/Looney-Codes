import React, { ChangeEvent, FormEvent, useState } from 'react';
import { Link } from 'react-router-dom';
import './Login.css';

interface LoginProps {
    onLogin: () => void;
}

interface LoginForm {
    username: string;
    password: string;
}

const Login: React.FC<LoginProps> = (props) => {
    const [loginForm, setLoginForm] = useState<LoginForm>({ username: '', password: '' });
    const [error, setError] = useState<string>('');

    function onChange(event: ChangeEvent<HTMLInputElement>) {
        const { name, value } = event.target;
        setLoginForm((oldForm) => ({ ...oldForm, [name]: value }));
    }

    function onSubmit(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setError('');

        const credentials = btoa(`${loginForm.username}:${loginForm.password}`);
        const options = {
            method: 'POST',
            headers: {
                  'Authorization': `Basic ${credentials}`,
                  'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginForm),
        };

        // ruta uopce nije bitna, jedino je bitno da nas server prihvaca!
        fetch('/api/user/', options).then((response) => {
            if (response.status === 401) {
                setError('Login failed');
            } else {
                console.log('Success!');
                props.onLogin();
            }
        });
    }

    return (
        <div className="login-container">
            <div className="Login">
                <form onSubmit={onSubmit}>
                    <div className="FormRow">
                        <label>korisničko ime</label>
                        <input name="username" onChange={onChange} value={loginForm.username} />
                    </div>
                    <div className="FormRow">
                        <label>lozinka</label>
                        <input name="password" type="password" onChange={onChange} value={loginForm.password} />
                    </div>
                    <div className="error">{error}</div>
                    <button type="submit">prijavi se!</button>
                    <Link to="/register">
                        <button type="button">prijava</button>
                    </Link>
                </form>
            </div>
        </div>
    );
};

export default Login;
