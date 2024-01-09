import React from 'react';
import '../../styles/Table.css';
import { Link } from 'react-router-dom';
interface ProblemsProps {
    problem: {
        voditelj: string;
        nazivZadatka: string;
        tekstZadatka: string;
        zadatakId: BigInteger;
        brojBodova: number;
        privatniZadatak: boolean;
    };
}

const Problems: React.FC<ProblemsProps> = (props) => {
    const { voditelj, nazivZadatka, tekstZadatka, zadatakId, brojBodova, privatniZadatak } = props.problem;
    return (
        <tr className="info-table">
            <td>{voditelj}</td>
            <td>
                <Link to={`/problem/${zadatakId}`}>
                    {nazivZadatka}
                </Link>
            </td>
            <td><span>{tekstZadatka}</span></td>
            <td>{brojBodova}</td>
            <td>{privatniZadatak && <p>privatni</p>}
                {!privatniZadatak && <p>javni</p>}</td>
        </tr>
    );
};

export default Problems;
