import React, { ReactElement, useContext, useEffect, useState } from 'react';
import LoadingOverlay from 'react-loading-overlay-nextgen';
import { useParams } from 'react-router-dom';
import '../../styles/ProblemPage.css';
import { UserContext } from '../../context/userContext';
import CodeMirror from '@uiw/react-codemirror';
import { cpp } from '@codemirror/lang-cpp';
import { dracula } from '@uiw/codemirror-theme-dracula';

interface IProblemDetails {
        zadatakId: string;
        nazivZadatka: string;
        brojBodova: number;
        vremenskoOgranicenje: string;
        tekstZadatka: string;
}

const ProblemPage: React.FC = () => {
        const { zadatakId, nadmetanjeId } = useParams();     //id zadatka preuzet iz url-a (opcionalno i id nadmetanja)
        const { user } = useContext(UserContext); //podaci ulogiranog korisnika

        const [problemDetails, setProblemDetails] = useState<IProblemDetails | null>(null); //atributi problema
        const [testResults, setTestResults] = useState<number[]>([]);   //rezultati testova
        const [errorMessage, setErrorMessage] = useState<string | null>(null);  //poruka ukoliko nije ulogiran natjecatelj
        const [isLoading, setLoading] = useState<boolean>(false); // za loading overlay...
        const [code, setCode] = useState<string>(`#include <bits/stdc++.h>
using namespace std;
int main() {

  return 0;
}`);
        // izvuci podatke o zadatku na temelju id-a
        useEffect(() => {
                fetch(`/api/problems/get/${zadatakId}`)
                        .then((response) => response.json())
                        .then((data: IProblemDetails) => setProblemDetails(data))
                        .catch((error) => console.error('Error fetching problem details:', error));

                setTestResults([]);
                setErrorMessage(null);
                if (zadatakId) {
                        let backup = localStorage.getItem(zadatakId);
                        if (backup)
                                setCode(backup);
                }
        }, [zadatakId]);

        if (!problemDetails) {
                return (<div>Zadatak ne postoji ili mu ne možete pristupiti</div>);
        }

        // funkcija za loadanje sadržaja nove datoteke
        const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
                const files = event.target.files;

                if (files && files.length > 0) {
                        const selected = files[0];
                        const reader = new FileReader();

                        reader.onload = (event) => {
                                if (event.target && event.target.result) {
                                        const fileContent = event.target.result as string;
                                        setCode(fileContent);
                                        console.log(fileContent);
                                }
                        };

                        reader.readAsText(selected);
                }
        };

        // pozivanje evaluacije na backendu
        const handleSubmitClick = async () => {
                try {
                        // ako nema fajla, onda citamo sadrzaj editora!
                        if (!code) {
                                setErrorMessage("Greška kod uploadanja!");
                                return;
                        }

                        // konstrukcija JSON-a
                        const solutionData = {
                                korisnickoIme: user.korisnickoIme,
                                zadatakId: problemDetails.zadatakId || '',
                                programskiKod: code?.valueOf(), // salji sadrzaj editora ako nema fajla
                                nadmetanjeId: nadmetanjeId,
                        };

                        // zamijeni navodnike u programskom kodu
                        solutionData.programskiKod = solutionData.programskiKod?.replace(`/"/g`, `\"`);

                        const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);

                        setLoading(true);
                        fetch('/api/solutions/upload', {
                                method: 'POST',
                                headers: {
                                        'Authorization': `Basic ${credentials}`,
                                        'Content-Type': 'application/json',
                                },
                                body: JSON.stringify(solutionData),
                        })
                                // moguće greške
                                .then((response) => {
                                        if (response.ok) {
                                                return response.json();
                                        } else if (response.status === 403) {
                                                // Dodaj poruku o pogrešci kada je status 403
                                                setErrorMessage('Morate biti prijavljeni kao natjecatelj');
                                                throw new Error('Forbidden');
                                        } else {
                                                setErrorMessage('Greška pri uploadu. Molimo pokušajte kasnije.');
                                                throw new Error('Failed to upload solution');
                                        }
                                })
                                // spremi rezultate testiranja ako postoje
                                .then((data) => {
                                        console.log('Response body:', data);
                                        if (data.tests && data.tests.length > 0) {
                                                setTestResults(data.tests);
                                        }
                                })
                                .catch((error) => {
                                        console.error('Error uploading solution:', error);
                                })
                                .finally(() => {
                                        setLoading(false);
                                });
                } catch (error) {
                        console.error('Error uploading solution:', error);
                }
        };


        // upload gumb koji je dostupan samo ulogiranom korisniku
        let uploadButton = null;
        let codeEditor: ReactElement | null = null;
        if (user.uloga === 'NATJECATELJ') {
                uploadButton = (
                        <div className="problem-upload">
                                <input type="file" accept=".cpp" onChange={handleFileChange} />
                                <br />
                                <button disabled={isLoading} style={isLoading ? { opacity: 0.6, cursor: 'not-allowed' } : {}}
                                        onClick={handleSubmitClick}>Provjeri</button>
                        </div>
                );

                codeEditor = (
                        <div className="code-editor">
                                <CodeMirror value={code} onChange={(value, _) => { setCode(value); if (zadatakId) localStorage.setItem(zadatakId, code); }}
                                        height="30rem" theme={dracula} extensions={[cpp()]} />
                        </div>
                );
        }

        // vraćanje stranice
        return (
                <div className="problem-details-container">
                        <h1 className="problem-title">{problemDetails.nazivZadatka}</h1>
                        <div className="problem-info">
                                <p>Broj bodova: {problemDetails.brojBodova}</p>
                                <p>Vremensko ograničenje: {problemDetails.vremenskoOgranicenje}</p>
                        </div>
                        <div className="problem-description">
                                <h2>Tekst zadatka:</h2>
                                <p>{problemDetails.tekstZadatka}</p>
                        </div>
                        {errorMessage && <p className="error-message">{errorMessage}</p>}
                        <br />
                        <LoadingOverlay
                                active={isLoading} spinner text='Provjeravamo rješenje...'
                        >
                                {codeEditor}
                        </LoadingOverlay>
                        {uploadButton}
                        {testResults.length > 0 && (
                                <div className="test-results">
                                        <h2>Rezultati testiranja</h2>
                                        <table>
                                                <thead>
                                                        <tr>
                                                                <th>Redni broj testa</th>
                                                                <th>Status</th>
                                                        </tr>
                                                </thead>
                                                <tbody>
                                                        {testResults.map((status, index) => (
                                                                <tr key={index}>
                                                                        <td>{index + 1}</td>
                                                                        <td>{getStatusMessage(status)}</td>
                                                                </tr>
                                                        ))}
                                                </tbody>
                                        </table>
                                </div>
                        )}
                </div>
        );
};

// Pomoćna funkcija za dobivanje poruke ovisno o statusnom kodu testa
const getStatusMessage = (status: number): string => {
        switch (status) {
                case 3:
                        return 'Prolazi';
                case 4:
                        return 'Greška u ispisu';
                case 5:
                        return 'Prekoračenje vremenskog ograničenja';
                case 6:
                        return 'Pogreška pri kompilaciji';
                default:
                        return 'Nepoznat status';
        }
};

export default ProblemPage;
