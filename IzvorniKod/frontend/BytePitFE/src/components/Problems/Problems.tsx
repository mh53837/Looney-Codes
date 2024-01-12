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
    const { voditelj, nazivZadatka, tekstZadatka, zadatakId, brojBodova } = props.problem;
    return (
        <tr className="info-table">
            <td>
                <Link to={`/user/profile/${voditelj}`}>
                    {voditelj}
                </Link></td>
            <td>
                <Link to={`/problem/${zadatakId}`}>
                    {nazivZadatka}
                </Link>
            </td>
            <td><span>{tekstZadatka}</span></td>
            <td>{brojBodova == 10 ? '⭐' : brojBodova == 20 ? "⭐⭐" : "⭐⭐⭐"}</td>
        </tr>
    );
};

export default Problems;
