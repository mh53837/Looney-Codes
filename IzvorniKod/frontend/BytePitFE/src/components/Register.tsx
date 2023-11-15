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
    const [poruka, setPoruka] = useState<string>('');


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
            setError('lozinke se ne podudaraju!');
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
                setError('došlo je do pogreške, pokušaj ponovno!');
            }  else if (response.status === 400) {
                setError('korisničko ime je zauzeto, pokušaj ponovno!');
            }
            
            else {
                setPoruka('provjeri mail kako bi potvrdio registraciju!')
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
                        <input name="korisnickoIme" placeholder='korisničko ime' onChange={onChange} value={registerForm.korisnickoIme} />
                    </div>
                    <div className="FormRow">
                        <label>ime</label>
                        <input name="ime" placeholder='ime' onChange={onChange} value={registerForm.ime} />
                    </div>
                    <div className="FormRow">
                        <label>prezime</label>
                        <input name="prezime" placeholder='prezime' onChange={onChange} value={registerForm.prezime} />
                    </div>
                    <div className="FormRow">
                        <label>email</label>
                        <input name="email" placeholder='email' onChange={onChange} value={registerForm.email} />
                    </div>

                    <div className="FormRow">
                        <label>lozinka</label>
                        <input name="lozinka" placeholder='lozinka' type="password" onChange={onChange} value={registerForm.lozinka} />
                    </div>
                    <div className="FormRow">
                        <label>potvrdi lozinku</label>
                        <input name="confirmLozinka" placeholder='lozinka' type="password" onChange={onChange} value={registerForm.confirmLozinka} />
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
                    <div className="poruka">{poruka}</div>
                    <button type="submit">registriraj se!</button>
                </form>
            </div>
        </div>
    );
};

export default Register;
