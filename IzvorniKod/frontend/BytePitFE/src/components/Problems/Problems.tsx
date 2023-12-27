import React from 'react';
import '../../styles/Table.css';
import { Link } from 'react-router-dom';
interface ProblemsProps {
    problem: {
        voditelj: string;
        nazivZadatka: string;
        tekstZadatka: string;
        zadatakId: BigInteger;
        brojBodova: number ;
        privatniZadatak: boolean;
    };
}

const Problems: React.FC<ProblemsProps> = (props) => {
    const { voditelj, nazivZadatka, tekstZadatka, zadatakId, brojBodova } = props.problem;

    return (
        <tr className="info-table">
            <td>{voditelj}</td>
            <td>{nazivZadatka}</td>
            <td>{tekstZadatka}</td>
            <td>{brojBodova}</td>
            <td>
                <Link to={`/problem/${zadatakId}`}>
                    riješi zadatak
                </Link>
            </td>
        </tr>
    );
};

export default Problems;
