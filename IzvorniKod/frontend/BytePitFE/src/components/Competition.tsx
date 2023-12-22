import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Table.css'

interface CompetitionProps {
    competition: {
        natjecanjeId: number;
        voditelj: string;
        nazivNatjecanja: string;
        pocetakNatjecanja: string;
        krajNatjecanja: string;
    };

}

const Competition: React.FC<CompetitionProps> = (props) => {
    const {natjecanjeId, voditelj, nazivNatjecanja, pocetakNatjecanja, krajNatjecanja } = props.competition;

    return (
        <tr className="competition-info-header">
        <td>{natjecanjeId}</td>
        <td>{voditelj}</td>
        <td>{nazivNatjecanja}</td>
        <td>{pocetakNatjecanja}</td>
        <td>{krajNatjecanja}</td>
        <td>
            <Link to = {`/user/get/${natjecanjeId}`}>
                pogledaj natjecanje
            </Link>
        </td>
        </tr>
    );
};


export default Competition;
