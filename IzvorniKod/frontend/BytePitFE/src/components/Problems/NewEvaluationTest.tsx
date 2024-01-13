import React, { useState, useContext, ChangeEvent } from "react";
import { UserContext } from "../../context/userContext";
import "../../styles/NewEvaluationTest.css";


interface NewEvaluationProps {
  zadatakId: BigInteger;
  onTestAdded: () => void;
}

interface EvaluationTest{
    ulazniPodaci: string;
    izlazniPodaci: string;
}
  
const NewEvaluationTest: React.FC<NewEvaluationProps> = ({zadatakId, onTestAdded}) => {
    const [error, setError] = useState<string>('');
    const { user } = useContext(UserContext);

    const [evaluationTest, setEvaluationTest] = useState<EvaluationTest>({
        ulazniPodaci:"",
        izlazniPodaci:"",
    })
    const onTestChange = (event: ChangeEvent<HTMLInputElement>) => {
        const { name, value } = event.target;
        setEvaluationTest((oldTest) => ({ ...oldTest, [name]: value }));
    }

    const  onTestSubmit = () => {
        setError('');
        if(evaluationTest.ulazniPodaci === "" && evaluationTest.izlazniPodaci === "" ) {
            setError("unesite potrebne podatke i pokušajte ponovno");
            return
        }
        const jsonPart = {
            ulazniPodaci: evaluationTest.ulazniPodaci,
            izlazniPodaci: evaluationTest.izlazniPodaci,
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
    
        fetch(`/api/problems/get/${zadatakId}/addTest`, options).then((response) => {
            if (!response.ok) {
              throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
          })
          .then((data) => {
            console.log('Server response:', data);
            onTestAdded();
            setEvaluationTest({ulazniPodaci:"",
            izlazniPodaci:"",});
          })
          .catch((error) => {
            console.error('Fetch error:', error);
            setError('Došlo je do pogreške, pokušajte ponovno!');
          });
    }
    return (
      <div>

        <div className="FormRow">
          <div className = "zadatakTestovi">
            <h3>novi testni primjer:</h3>
            <div className = "ulaz"> 
              <label>ulaz programa:</label>
              <input name = "ulazniPodaci" placeholder ="ulaz" onChange={onTestChange} value={evaluationTest.ulazniPodaci} />
            </div>
            <div className = "izlaz"> 
            <label>izlaz programa:</label>
              <input name = "izlazniPodaci" placeholder ="izlaz" onChange={onTestChange} value={evaluationTest.izlazniPodaci} />
            </div>
            <button onClick={onTestSubmit}>dodaj!</button>
          </div>
        </div>
        <div className="error">{error}</div>

      </div>
    )
}

export default NewEvaluationTest;