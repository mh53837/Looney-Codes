import React, { ChangeEvent, FormEvent, useState } from 'react';
import './Register.css';

interface RegisterProps {
    onRegister: () => void;
}

interface RegisterForm {
    korisnickoIme: string;
    ime: string;
    prezime: string;
    email: string;
    requestedUloga: string;
    lozinka: string;
    confirmLozinka: string;
}

const Register: React.FC<RegisterProps> = (props) => {
    const [registerForm, setRegisterForm] = useState<RegisterForm>({
        korisnickoIme: '',
        ime: '',
        prezime: '',
        email: '',
        requestedUloga: '',
        lozinka: '',
        confirmLozinka: '',
    });
    const [error, setError] = useState<string>('');

    const handleUlogaChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRegisterForm({
            ...registerForm,
            requestedUloga: event.target.value,
        });
    };

    function onChange(event: ChangeEvent<HTMLInputElement>) {
        const { name, value } = event.target;
        setRegisterForm((oldForm) => ({ ...oldForm, [name]: value }));
    }

    function onSubmit(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setError('');

        if (registerForm.lozinka !== registerForm.confirmLozinka) {
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
                        <input name="korisnickoIme" onChange={onChange} value={registerForm.korisnickoIme} />
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
                        <input name="lozinka" type="password" onChange={onChange} value={registerForm.lozinka} />
                    </div>
                    <div className="FormRow">
                        <label>potvrdi lozinku</label>
                        <input name="confirmLozinka" type="password" onChange={onChange} value={registerForm.confirmLozinka} />
                    </div>
                    <div className = "FormRow">
                        <label>uloga</label>
                        <div className = "RoleOptions" >
                            <label className = "RadioLbl">
                                <input
                                    type="radio"
                                    value="VODITELJ"
                                    checked={registerForm.requestedUloga === 'VODITELJ'}
                                    onChange={handleUlogaChange}
                                />
                                voditelj
                            </label>
                            <label className = "RadioLbl">
                                <input 
                                    type="radio"
                                    value="NATJECATELJ"
                                    checked={registerForm.requestedUloga === 'NATJECATELJ'}
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
