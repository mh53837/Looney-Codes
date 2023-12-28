import React, { useEffect, useState } from 'react';
import '../../styles/Table.css'


export interface IProblems {
    voditelj: string;
    nazivZadatka: string;
    tekstZadatka: string;
    zadatakId: BigInteger;
    brojBodova: number ;
    privatniZadatak: boolean;
}

const Problems = React.lazy(() => import('./Problems'));

const ProblemsList: React.FC = () => {
    const [problem, setProblems] = useState<IProblems[]>([]);
    

    useEffect(() => {
        fetch('/api/problems/all')
            .then(response => response.json())
            .then((data: IProblems[]) => setProblems(data))
            .catch(error => console.error('Error fetching problems:', error));
    }, []);

    return (
        <div className="info-table">
            <table>
                <thead>
                    <tr>
                        <th>korisničko ime</th>
                        <th>naziv</th>
                        <th>tekst</th>
                        <th>broj bodova</th>
                        <th></th>
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
