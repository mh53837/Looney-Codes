import React, {useState, ChangeEvent, FormEvent, useContext} from 'react';
import '../../styles/NewProblem.css';
import type { DatePickerProps } from "antd/es/date-picker";
import hrHR from 'antd/lib/locale/hr_HR'; 
import { Dayjs } from "dayjs";
import dayjs from "dayjs";
import 'dayjs/locale/hr';
import { DatePicker, Space, ConfigProvider, message} from "antd";
import { UserContext } from '../../context/userContext';
import AddProblemsToCompetition from './AddProblemsToCompetition';

interface CompetitionForm {
    nazivNatjecanja: string;
    pocetakNatjecanja: string;
    krajNatjecanja: string;
}

interface NewCompProps{
    handleOk: () => void
}
const NewCompetition: React.FC<NewCompProps> = ({handleOk}) => { 
    const today = dayjs();
    const [natjecanjeId, setNatjecanjeId] = useState<number | null>(null);
    const [evaluationTestsVisible, setEvaluationTestsVisible] = useState(false);
    const {user } = useContext(UserContext);
    const [error, setError] = useState<string>('');
    const [poruka, setPoruka] = useState<string>('');
    const [updatedPocetak, setUpdatedPocetak] = useState<Dayjs | null>(null);
    const [updatedKraj, setUpdatedKraj] = useState<Dayjs | null>(null);

    const [messageApi, contextHolder] = message.useMessage();

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

      const onNewCompetitionCreated = (natjecanjeId: number) => {
        setCompetitionForm({
            nazivNatjecanja : "",
            pocetakNatjecanja: "",
            krajNatjecanja: "",
        });
        setPoruka('');
        setError('');
        console.log('new competition id:', natjecanjeId);
        setNatjecanjeId(natjecanjeId);
        setEvaluationTestsVisible(true);
    };
    const onCloseAddProblems = () => {
        setEvaluationTestsVisible(false);
        setNatjecanjeId(null);
        handleOk();
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

    const onSubmit = async (e: FormEvent<HTMLFormElement>) => {
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

        try {
            const response = await fetch('/api/natjecanja/new', options);
        
            if (!response.ok) {
              throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data = await response.json();
            messageApi.open({
                type: 'success',
                content: 'uspješno ste kreirali novo natjecanje',
              });
            setPoruka("uspješno ste kreirali novo natjecanje");
            onNewCompetitionCreated(data.natjecanjeId);
            console.log('Server response:', data);
        } catch (error) {
            console.error('Fetch error:', error);
            setError('Došlo je do pogreške, pokušajte ponovno!');
        }

    }


    return (
        <div className="newProblem-container">
            {contextHolder}
            <div className="NewProblem">
                <form onSubmit={onSubmit}>
                    <div className="FormRow">
                        <label>naziv natjecanja</label>
                        <input name="nazivNatjecanja" placeholder='naziv natjecanja' onChange={onChange} value={competitionForm.nazivNatjecanja} />
                    </div>
                    <ConfigProvider locale={hrHR}>
                    <Space direction="vertical">
                    <div className="FormRowDate" >
                        <label>početak natjecanja </label>
                        
                        <DatePicker
                            showTime
                            placeholder="početak natjecanja"
                            defaultValue={today}
                            value={updatedPocetak}
                            onChange={onPocetakChange}
                            size={"small"}
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
                            size={"small"}
                        />
                        
                    </div>
                    </Space>
                    </ConfigProvider>
                    
                    
                    <div className="error">{error}</div>
                    <div className="poruka">{poruka}</div>
                    <button type="submit">stvori natjecanje!</button>
                </form>
            </div>

            {evaluationTestsVisible && natjecanjeId !== null && (
                <React.Suspense fallback={<div>učitavanje...</div>}>
                    <AddProblemsToCompetition natjecanjeId={natjecanjeId} visible={true} onClose={onCloseAddProblems} />
                </React.Suspense>
            )}
        </div>
    )
}



export default NewCompetition;