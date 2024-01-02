import React, {useState, ChangeEvent, FormEvent, useContext} from 'react';
import '../../styles/NewProblem.css';
import type { DatePickerProps } from "antd/es/date-picker";
import { Dayjs } from "dayjs";
import dayjs from "dayjs";
import { DatePicker, Space} from "antd";
import { UserContext } from '../../context/userContext';

interface NewCompetitionProps {
    onNewCompetitionCreated: () => void;
}

interface CompetitionForm {
    nazivNatjecanja: string;
    pocetakNatjecanja: string;
    krajNatjecanja: string;
}

const NewCompetition: React.FC<NewCompetitionProps> = (props) => { 
    const today = dayjs();
    const {user } = useContext(UserContext);
    const [error, setError] = useState<string>('');
    const [poruka, setPoruka] = useState<string>('');
    const [updatedPocetak, setUpdatedPocetak] = useState<Dayjs | null>(null);
    const [updatedKraj, setUpdatedKraj] = useState<Dayjs | null>(null);
    const [competitionForm, setCompetitionForm] = useState<CompetitionForm>({
        nazivNatjecanja : "",
        pocetakNatjecanja: "",
        krajNatjecanja: "",
    });

    function onChange(event: ChangeEvent<HTMLInputElement>) {
        const { name, value } = event.target;
        setCompetitionForm((oldForm) => ({ ...oldForm, [name]: value }));
    }

    const onPocetakChange: DatePickerProps["onChange"] = (date, dateString) => {
        console.log(date, dateString);
        setUpdatedPocetak(date);
        if(date){
            setCompetitionForm((oldForm) => ({
                ...oldForm,
                pocetakNatjecanja: date.toISOString(),
            }));
            console.log(competitionForm.pocetakNatjecanja);
        }

      };
    
      const onKrajChange: DatePickerProps["onChange"] = (date, dateString) => {
        console.log(date, dateString);
        setUpdatedKraj(date);
        if(date){
            setCompetitionForm((oldForm) => ({
                ...oldForm,
                krajNatjecanja: date.toISOString(),
                
            }));
            console.log(competitionForm.krajNatjecanja);
        }
      };

    function onSubmit(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setError('');

        if(competitionForm.nazivNatjecanja === "" || competitionForm.pocetakNatjecanja === "" || competitionForm.krajNatjecanja === "" ) {
            setError("unesite potrebne podatke i pokušajte ponovno");
            return
        }

        const jsonPart = {
            nazivNatjecanja: competitionForm.nazivNatjecanja,
            pocetakNatjecanja: competitionForm.pocetakNatjecanja,
            krajNatjecanja: competitionForm.krajNatjecanja,
            korisnickoImeVoditelja: user.korisnickoIme

        }
        const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);
        const options = {
            method: 'POST',
            headers: {
                Authorization: `Basic ${credentials}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(jsonPart),
        };

        fetch('/api/natjecanja/new', options).then((response) => {
            if (!response.ok) {
              throw new Error(`HTTP error! Status: ${response.status}`);
            }
            setPoruka("uspješno ste kreirali novo natjecanje");
            props.onNewCompetitionCreated();
            return response.json();
          })
          .then((data) => {
            console.log('Server response:', data);
          })
          .catch((error) => {
            console.error('Fetch error:', error);
            setError('Došlo je do pogreške, pokušajte ponovno!');
          });
    }


    return (
        <div className="newProblem-container">
            <div className="NewProblem">
                <form onSubmit={onSubmit}>
                    <div className="FormRow">
                        <label>naziv natjecanja</label>
                        <input name="nazivNatjecanja" placeholder='naziv natjecanja' onChange={onChange} value={competitionForm.nazivNatjecanja} />
                    </div>
                    <Space direction="vertical">
                    <div className="FormRowDate" >
                        <label>početak natjecanja </label>
                        <DatePicker
                            showTime
                            placeholder="početak natjecanja"
                            defaultValue={today}
                            value={updatedPocetak}
                            onChange={onPocetakChange}
                        />
                    </div>
                    <div className="FormRowDate">
                        <label> kraj natjecanja</label>
                        <DatePicker
                            showTime
                            placeholder="kraj natjecanja"
                            defaultValue={today}
                            value={updatedKraj}
                            onChange={onKrajChange}
                        />
                    </div>
                    </Space>
                    
                    
                    <div className="error">{error}</div>
                    <div className="poruka">{poruka}</div>
                    <button type="submit">stvori natjecanje!</button>
                </form>
            </div>
        </div>
    )
}



export default NewCompetition;