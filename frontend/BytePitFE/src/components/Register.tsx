import React, { ChangeEvent, FormEvent, useState } from 'react';
import './Register.css';

interface RegisterProps {
    onRegister: () => void;
}

interface RegisterForm {
    username: string;
    password: string;
    confirmPassword: string;
}

const Register: React.FC<RegisterProps> = (props) => {
    const [registerForm, setRegisterForm] = useState<RegisterForm>({
        username: '',
        password: '',
        confirmPassword: '',
    });
    const [error, setError] = useState<string>('');

    function onChange(event: ChangeEvent<HTMLInputElement>) {
        const { name, value } = event.target;
        setRegisterForm((oldForm) => ({ ...oldForm, [name]: value }));
    }

    function onSubmit(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setError('');

        if (registerForm.password !== registerForm.confirmPassword) {
            setError('Passwords do not match');
            return;
        }

        const body = `username=${registerForm.username}&password=${registerForm.password}`;
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: body,
        };

        fetch('/api/register', options).then((response) => {
            if (response.status === 401) {
                setError('Registration failed');
            } else {
                props.onRegister();
            }
        });
    }

    return (
        <div className="register-container">
            <div className="Register">
                <form onSubmit={onSubmit}>
                    <div className="FormRow">
                        <label>Username</label>
                        <input name="username" onChange={onChange} value={registerForm.username} />
                    </div>
                    <div className="FormRow">
                        <label>Password</label>
                        <input name="password" type="password" onChange={onChange} value={registerForm.password} />
                    </div>
                    <div className="FormRow">
                        <label>Confirm Password</label>
                        <input name="confirmPassword" type="password" onChange={onChange} value={registerForm.confirmPassword} />
                    </div>
                    <div className="error">{error}</div>
                    <button type="submit">Register</button>
                </form>
            </div>
        </div>
    );
};

export default Register;
