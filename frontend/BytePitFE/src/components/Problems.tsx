import React from 'react';
import './Problems.css';
import { IUser } from './UserList';
interface ProblemsProps {
    problem: {
        voditelj: IUser;
        nazivZadatka: string;
        tekstZadatka: string;
    };
}

const Problems: React.FC<ProblemsProps> = (props) => {
    const { voditelj, nazivZadatka, tekstZadatka } = props.problem;

    return (
        <tr className="user-info-header">
        <td>{voditelj?.korisnickoIme}</td>
        <td>{nazivZadatka }</td>
        <td>{tekstZadatka}</td>
        </tr>
    );
};

export default Problems;
