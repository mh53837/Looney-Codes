import React, { ChangeEvent, FormEvent, useState } from 'react';
import './Register.css';

interface RegisterProps {
    onRegister: () => void;
}

interface RegisterForm {
    username: string;
    ime: string;
    prezime: string;
    email: string;
    uloga: string;
    password: string;
    confirmPassword: string;
}

const Register: React.FC<RegisterProps> = (props) => {
    const [registerForm, setRegisterForm] = useState<RegisterForm>({
        username: '',
        ime: '',
        prezime: '',
        email: '',
        uloga: '',
        password: '',
        confirmPassword: '',
    });
    const [error, setError] = useState<string>('');

    const handleUlogaChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRegisterForm({
            ...registerForm,
            uloga: event.target.value,
        });
    };

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

        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(registerForm),
        };

        fetch('/api/user/register', options).then((response) => {
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
                        <label>korisničko ime</label>
                        <input name="username" onChange={onChange} value={registerForm.username} />
                    </div>
                    <div className="FormRow">
                        <label>ime</label>
                        <input name="ime" onChange={onChange} value={registerForm.ime} />
                    </div>
                    <div className="FormRow">
                        <label>prezime</label>
                        <input name="prezime" onChange={onChange} value={registerForm.prezime} />
                    </div>
                    <div className="FormRow">
                        <label>email</label>
                        <input name="email" onChange={onChange} value={registerForm.email} />
                    </div>

                    <div className="FormRow">
                        <label>lozinka</label>
                        <input name="password" type="password" onChange={onChange} value={registerForm.password} />
                    </div>
                    <div className="FormRow">
                        <label>potvrdi lozinku</label>
                        <input name="confirmPassword" type="password" onChange={onChange} value={registerForm.confirmPassword} />
                    </div>
                    <div className = "FormRow">
                        <label>uloga</label>
                        <div className = "RoleOptions" >
                            <label className = "RadioLbl">
                                <input
                                    type="radio"
                                    value="voditelj"
                                    checked={registerForm.uloga === 'voditelj'}
                                    onChange={handleUlogaChange}
                                />
                                voditelj
                            </label>
                            <label className = "RadioLbl">
                                <input 
                                    type="radio"
                                    value="natjecatelj"
                                    checked={registerForm.uloga === 'natjecatelj'}
                                    onChange={handleUlogaChange}
                                />
                                natjecatelj
                            </label>
                        </div>
                    </div>

                    <div className="error">{error}</div>
                    <button type="submit">registriraj se!</button>
                </form>
            </div>
        </div>
    );
};

export default Register;
