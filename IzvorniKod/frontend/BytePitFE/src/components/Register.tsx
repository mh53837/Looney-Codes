import React, { ChangeEvent, FormEvent, useState } from 'react';
import '../styles/Register.css';

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
    slika: File | null;
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
        slika: null,
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

    function onSlikaChange(event: ChangeEvent<HTMLInputElement>) {
        if (event.target.files && event.target.files.length > 0) {
            const slika = event.target.files[0];
            setRegisterForm((oldForm) => ({ ...oldForm, slika }));
        }
    }

    function provjeriLozinku(lozinka: string): string | null {
        const minLength = 8;
        const hasUpperCase = /[A-Z]/.test(lozinka);
        const hasLowerCase = /[a-z]/.test(lozinka);
        const hasDigit = /\d/.test(lozinka);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(lozinka);

        if (lozinka.length < minLength) {
            return "Lozinka mora sadržavati barem 8 znakova!";
        } else if (!hasUpperCase || !hasLowerCase || !hasDigit || !hasSpecialChar) {
            return "Lozinka mora sadržavati barem jedno veliko slovo, interpunkcijski znak i broj!"
        }

        return null;
    }

    function onSubmit(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setError('');

        const passError = provjeriLozinku(registerForm.lozinka);
        if (passError) {
            setError(passError);
            return;
        }

        if (registerForm.lozinka !== registerForm.confirmLozinka) {
            setError('Lozinke se ne podudaraju!');
            return;
        }

        const formData = new FormData();
        const jsonPart = {
            korisnickoIme: registerForm.korisnickoIme,
            ime: registerForm.ime,
            prezime: registerForm.prezime,
            email: registerForm.email,
            requestedUloga: registerForm.requestedUloga,
            lozinka: registerForm.lozinka,
        };

        if (registerForm.slika) {
            formData.append('image', registerForm.slika, registerForm.slika.name);
        } else {
            setError("Dodajte sliku!")
            return
        }

        formData.append('userData', new Blob([JSON.stringify(jsonPart)], { type: 'application/json' }), 'userData.json');



        const options = {
            method: 'POST',
            body: formData,
        };

        fetch('/api/user/register', options).then((response) => {
            if (response.status === 401) {
                setError('Došlo je do pogreške, pokušajte ponovno!');
            } else if (response.status === 400) {
                setError('Korisničko ime ili email je zauzet, pokušajte ponovno!');
            } else {
                setPoruka('Provjerite e-mail kako biste potvrdili registraciju!');
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
                    <div className="FormRow">
                        <label>Slika profila</label>
                        <input name="slika" type="file" onChange={onSlikaChange} accept=".jpg, .jpeg, .png" />
                    </div>
                    <div className="FormRow">
                        <label>uloga</label>
                        <div className="RoleOptions">
                            <label className="RadioLbl">
                                <input
                                    type="radio"
                                    value="VODITELJ"
                                    checked={registerForm.requestedUloga === 'VODITELJ'}
                                    onChange={handleUlogaChange}
                                />
                                voditelj
                            </label>
                            <label className="RadioLbl">
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
