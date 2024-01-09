import React, { useContext, useEffect, useState } from 'react';
import '../../styles/Table.css'
import { UserContext } from '../../context/userContext';


export interface IProblems {
    voditelj: string;
    nazivZadatka: string;
    tekstZadatka: string;
    zadatakId: BigInteger;
    brojBodova: number;
    privatniZadatak: boolean;
}

const Problems = React.lazy(() => import('./Problems'));

const ProblemsList: React.FC = () => {
    const [problem, setProblems] = useState<IProblems[]>([]);
    const { user } = useContext(UserContext);

    useEffect(() => {
        if (user && user.uloga === "ADMIN") {
            const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);
            const options = {
                method: "GET",
                headers: {
                    Authorization: `Basic ${credentials}`,
                    "Content-Type": "application/json",
                },
            };
            fetch('/api/problems/adminView', options)
                .then(response => response.json())
                .then((data: IProblems[]) => setProblems(data))
                .catch(error => console.error('Error fetching problems:', error));
        } else {
            fetch('/api/problems/all')
                .then(response => response.json())
                .then((data: IProblems[]) => setProblems(data))
                .catch(error => console.error('Error fetching problems:', error));
        }
    }, [user]);

    return (
        <div className="info-table">
            <table>
                <thead>
                    <tr>
                        <th>voditelj</th>
                        <th>naziv</th>
                        <th>tekst</th>
                        <th>broj bodova</th>
                        <th>status</th>
                    </tr>
                </thead>
                <tbody>
                    {problem.map((problem, index) => (
                        <React.Suspense fallback={<div>učitavanje...</div>}>
                            <Problems key={index} problem={problem} />
                        </React.Suspense>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ProblemsList;
