import React, { useContext } from 'react';
import '../../styles/Table.css';
import { Link } from 'react-router-dom';
import { UserContext } from '../../context/userContext';
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
    const { voditelj, nazivZadatka, tekstZadatka, zadatakId, brojBodova, privatniZadatak } = props.problem;
    const {user} = useContext( UserContext );
    return (
        <tr className="info-table">
            <td>{voditelj}</td>
            <td>{nazivZadatka}</td>
            <td>{tekstZadatka}</td>
            <td>{brojBodova}</td>
            <td>{ privatniZadatak && <p>privatni</p> }
                {!privatniZadatak && <p>javni</p> }</td>
            {(user.uloga === "NATJECATELJ") &&    
            <td>
                <Link to={`/problem/${zadatakId}`}>
                    riješi zadatak
                </Link>
            </td>
            }
        </tr>
    );
};

export default Problems;
