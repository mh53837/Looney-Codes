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
    const pocetakDatum = new Date(pocetakNatjecanja);

    const krajDatum = new Date(krajNatjecanja);

    return (
        <tr className="info-table">
            <td>{voditelj}</td>
            <td>{nazivNatjecanja}</td>
            <td>{pocetakDatum.toDateString()}</td>
            <td>{krajDatum.toDateString()}</td>
            <td>
                <Link to = {`/user/get/${natjecanjeId}`}>
                    pogledaj natjecanje
                </Link>
            </td>
        </tr>
    );
};


export default Competition;
