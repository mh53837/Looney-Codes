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

interface TrophyData{
    natjecanjeId: number;
    mjesto: number;
    slikaPehara: File | null;
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

    const [trophyData, setTrophyData] = useState<TrophyData>({
        natjecanjeId: 0,
        mjesto:0,
        slikaPehara: null,
    })

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

    function onSlikaChange(event: ChangeEvent<HTMLInputElement>) {
        if (event.target.files && event.target.files.length > 0) {
            const slikaPehara = event.target.files[0];
            setTrophyData((oldForm) => ({ ...oldForm, slikaPehara }));
        }
    }

    const postTrophy = (natjecanjeId: number) => {
        if(trophyData.slikaPehara){
            const formData = new FormData();
            const jsonPart = {
                natjecanjeId: natjecanjeId,
                mjesto:0,
            };
            formData.append('image', trophyData.slikaPehara, trophyData.slikaPehara.name);
            
            formData.append('peharData', new Blob([JSON.stringify(jsonPart)], { type: 'application/json' }), 'peharData.json');
            
            const options = {
                method: 'POST',
                body: formData,
            };
    
            fetch('/api/trophies/add', options).then((response) => {
                if (!response.ok) {
                    console.log(response);
                    console.log("pogreška!");
                    setError('Došlo je do pogreške, pokušajte ponovno!');
                }
                else console.log("uspjesno prenesena slika pehara!");
            });
        }


    }

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
            postTrophy(data.natjecanjeId);
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

                    <div className="FormRow">
                        <label>slika pehara</label>
                        <input name="slikaPehara" type="file" onChange={onSlikaChange} accept=".jpg, .jpeg, .png" />
                    </div>
                    
                    
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