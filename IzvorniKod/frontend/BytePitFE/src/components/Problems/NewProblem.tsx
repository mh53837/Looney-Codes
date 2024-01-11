import React, {useState, ChangeEvent, FormEvent, useContext} from 'react';
import '../../styles/NewProblem.css';
import { Select, InputNumber, message } from "antd";
import { UserContext } from '../../context/userContext';

interface ProblemForm {
    nazivZadatka : string;
    brojBodova: number;
    vremenskoOgranicenje: number;
    tekstZadatka: string;
    privatniZadatak: boolean;
    tezinaZadatka: string;
}

interface NewProbProps{
    handleOk: () => void;
}
const EvaluationTests = React.lazy(() => import("../Problems/EvaluationTests"));

const NewProblem: React.FC<NewProbProps> = ({handleOk}) => { 
    const {user } = useContext(UserContext);
    const [error, setError] = useState<string>('');
    const [poruka, setPoruka] = useState<string>('');
    const [problemForm, setProblemForm] = useState<ProblemForm>({
        nazivZadatka : "",
        brojBodova: 10,
        vremenskoOgranicenje: 3,
        tekstZadatka: "",
        privatniZadatak: true,
        tezinaZadatka: "RECRUIT",
    });

    const [messageApi, contextHolder] = message.useMessage();

    const [evaluationTestsVisible, setEvaluationTestsVisible] = useState(false);
    const [zadatakId, setZadatakId] = useState<BigInteger | null>(null);

    const handleStatusChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setProblemForm({
            ...problemForm,
            privatniZadatak: event.target.value === "true" ? true : false
        });
    };

    const handleBrojBodovaChange = (value: string) => {
        const broj = parseInt(value, 10);
        if (broj == 10){
            setProblemForm({
                ...problemForm,
                tezinaZadatka: "RECRUIT"
            });
        } else if( broj == 20) {
            setProblemForm({
                ...problemForm,
                tezinaZadatka: "VETERAN"
            });
        } else if (broj == 50) {
            setProblemForm({
                ...problemForm,
                tezinaZadatka: "REALISM"
            });
        }
        
      };

    function onChange(event: ChangeEvent<HTMLInputElement>) {
        const { name, value } = event.target;
        setProblemForm((oldForm) => ({ ...oldForm, [name]: value }));
    }

    const onVremOgranicenjeChange = (value: number | null) => {
        if (value !== null) {
            setProblemForm((oldForm) => ({
                ...oldForm,
                vremenskoOgranicenje: value,
            }));
        }

    }

    const onNewProblemCreated = (zadatakId: BigInteger) => {
        setProblemForm({
            nazivZadatka : "",
            brojBodova: 10,
            vremenskoOgranicenje: 3,
            tekstZadatka: "",
            privatniZadatak: true,
            tezinaZadatka: "RECRUIT",
        });
        setPoruka('');
        setError('');
        console.log('new problem id:', zadatakId);
        setZadatakId(zadatakId);
        setEvaluationTestsVisible(true);
    };
    const onCloseEvaluationTests = () => {
        setEvaluationTestsVisible(false);
        setZadatakId(null);
        handleOk();
    };

    const onSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setError('');

        if(problemForm.nazivZadatka === "" || problemForm.tekstZadatka === "" ) {
            setError("unesite potrebne podatke i pokušajte ponovno");
            return
        }

        const jsonPart = {
            nazivZadatka: problemForm.nazivZadatka,
            brojBodova: problemForm.brojBodova,
            vremenskoOgranicenje: problemForm.vremenskoOgranicenje,
            tekstZadatka: problemForm.tekstZadatka,
            privatniZadatak: problemForm.privatniZadatak,
            tezinaZadatka: problemForm.tezinaZadatka

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
            const response = await fetch('/api/problems/new', options);
        
            if (!response.ok) {
              throw new Error(`HTTP error! Status: ${response.status}`);
            }
        
            const data = await response.json();
            messageApi.open({
                type: 'success',
                content: 'uspješno ste kreirali novi zadatak',
              });
            setPoruka('uspješno ste kreirali novi zadatak');
            onNewProblemCreated(data.zadatakId);
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
                        <label>naziv zadatka</label>
                        <input name="nazivZadatka" placeholder='naziv zadatka' onChange={onChange} value={problemForm.nazivZadatka} />
                    </div>
                    <div className="FormRow">
                        <label>tekst zadatka</label>
                        <input className= "tekstZadatka" name="tekstZadatka" placeholder='tekst zadatka' onChange={onChange} value={problemForm.tekstZadatka} />
                    </div>
                    <div className="FormRow" id="brBodova">
                        <label>broj bodova</label>
                        <Select 
                            defaultValue= { "10"  }
                            style={{ width: 120 }}
                            onChange={ handleBrojBodovaChange}
                            options={[
                                { value:"10", label: "10" },
                                { value:"20", label: "20" },
                                { value:"50", label: "50" },
                            ]}
                        />
                    </div>
                    <div className="FormRow" id="vremOgranicenje">
                        <label>vremensko ograničenje</label>
                        <InputNumber<number>
                            min={1} 
                            max={10} 
                            defaultValue={3} 
                            placeholder="vremensko ograničenje"
                            onChange={onVremOgranicenjeChange}
                            controls={true}
                            value={problemForm.vremenskoOgranicenje}
                            step={1}
                        />
                    </div>
                    <div className="FormRow">
                        <div className="privatniZadatak">
                            <label className="radio1">
                                <input
                                    type="radio"
                                    value="true"
                                    checked={problemForm.privatniZadatak === true}
                                    onChange={handleStatusChange}
                                />
                                privatni
                            </label>
                            <label className="radio1">
                                <input
                                    type="radio"
                                    value="false"
                                    checked={problemForm.privatniZadatak === false}
                                    onChange={handleStatusChange}
                                />
                                javni
                            </label>
                        </div>
                    </div>

                    <div className="error">{error}</div>
                    <div className="poruka">{poruka}</div>
                    <button type="submit">stvori zadatak!</button>
                </form>
            </div>

            {evaluationTestsVisible && zadatakId !== null && (
                <React.Suspense fallback={<div>učitavanje...</div>}>
                    <EvaluationTests zadatakId={zadatakId} visible={true} onClose={onCloseEvaluationTests} />
                </React.Suspense>
            )}
        </div>
    )
}

export default NewProblem;