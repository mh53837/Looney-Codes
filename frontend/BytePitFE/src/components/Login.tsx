import React, { ChangeEvent, FormEvent, useState } from 'react';
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

        const body = `username=${loginForm.username}&password=${loginForm.password}`;
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: body,
        };

        fetch('/api/login', options).then((response) => {
            if (response.status === 401) {
                setError('Login failed');
            } else {
                props.onLogin();
            }
        });
    }

    return (
        <div className="login-container">
            <div className="Login">
                <form onSubmit={onSubmit}>
                    <div className="FormRow">
                        <label>Username</label>
                        <input name="username" onChange={onChange} value={loginForm.username} />
                    </div>
                    <div className="FormRow">
                        <label>Password</label>
                        <input name="password" type="password" onChange={onChange} value={loginForm.password} />
                    </div>
                    <div className="error">{error}</div>
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>
    );
};

export default Login;
