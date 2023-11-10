import React, {useEffect, useState} from 'react';
import Problems from './Problems';
import { IUser } from './UserList';


export interface IProblems {
    voditelj: IUser;
    nazivZadatka: string;
    tekstZadatka: string;
}

const ProblemsList: React.FC = () => {
    const [problem, setProblems] = useState<IProblems[]>([]);

    useEffect(() => {
        fetch('/api/problems/all')
            .then(response => response.json())
            .then((data: IProblems[]) => setProblems(data))
            .catch(error => console.error('Error fetching problems:', error));
    }, []);

    return (
        <div className="user-info-table">
            <table>
                <thead>
                <tr>
                    <th>korisničko ime</th>
                    <th>naziv</th>
                    <th>tekst</th>
                </tr>
                </thead>
                <tbody>
                {problem.map((problem, index) => (
                    <Problems key={index} problem={problem} />
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default ProblemsList;