import React from 'react';
import '../styles/Table.css';
import { IUser } from './UserList';
import { Link } from 'react-router-dom';
interface ProblemsProps {
    problem: {
        voditelj: IUser;
        nazivZadatka: string;
        tekstZadatka: string;
        zadatakId: BigInteger;
    };
}

const Problems: React.FC<ProblemsProps> = (props) => {
    const { voditelj, nazivZadatka, tekstZadatka, zadatakId } = props.problem;

    return (
        <tr className="problem-info-header">
            <td>{voditelj?.korisnickoIme}</td>
            <td>{nazivZadatka}</td>
            <td>{tekstZadatka}</td>
            <td>
                <Link to={`/problem/${zadatakId}`}>
                    Riješi
                </Link>
            </td>
        </tr>
    );
};

export default Problems;
