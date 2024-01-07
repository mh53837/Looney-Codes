import React, { useState, useEffect, useContext} from "react";
import { ConfigProvider } from "antd";
import "../../styles/CompetitionUpdateForm.css";
import {UserContext} from '../../context/userContext';


interface EvaluationTestProps {
    zadatakId: BigInteger;
}

interface ProblemData {
    voditelj: UserData;
    nazivZadatka: string;
    tekstZadatka: string;
    zadatakId: BigInteger;
    brojBodova: number;
    privatniZadatak: boolean;
    tezinaZadatka: string;
    vremenskoOgranicenje: number;
}
  interface UserData {
    korisnickoIme: string;
    ime: string;
    prezime: string;
    email: string;
    uloga: string;
}

interface EvaluationTestData {
    testniPrimjerRb: number, 
    zadatak: ProblemData,
} 
interface EvaluationTestsData{
    testniPrimjerId: EvaluationTestData;
    ulazniPodaci: string | null;
    izlazniPodaci: string | null;
}

const DynamicModal = React.lazy(() => import("antd/lib/modal"));
const NewEvaluationTest = React.lazy(() => import("../Problems/NewEvaluationTest") );
const EvaluationTests: React.FC<EvaluationTestProps> = ({zadatakId}) => {

    const [testsData, setTestsData] = useState<EvaluationTestsData[] | null>(null);
    const {user} = useContext(UserContext);

    const [open, setOpen] = useState(false);
    const [confirmLoading, setConfirmLoading] = useState(false);

    useEffect( () => {
        const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);
            const options = {
            method: "GET",
            headers: {
                Authorization: `Basic ${credentials}`,
                "Content-Type": "application/json",
            },
            };
        fetch(`/api/problems/get/${zadatakId}/tests`, options)
        .then(response => response.json())
        .then((data: EvaluationTestsData[]) => {setTestsData(data);
        console.log("test data: ", data)})
        .catch(error => console.error('Error fetching problems:', error));
    }, [zadatakId, user] );
    

    const handleOk = async () => {
        setConfirmLoading(true);
        try {
          setOpen(false);
          setConfirmLoading(false);
        } catch (error) {
          console.error("Error handling edit:", error);
          setConfirmLoading(false);
        }
    };

    const handleCancel = () => {
        console.log("Clicked cancel button");
        setOpen(false);
    };
    const showModal = () => {
        setOpen(true);
    };

    const handleTestAdded = () => {
        const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);
        const options = {
            method: "GET",
            headers: {
                Authorization: `Basic ${credentials}`,
                "Content-Type": "application/json",
            },
        };
        fetch(`/api/problems/get/${zadatakId}/tests`, options)
        .then(response => response.json())
        .then((data: EvaluationTestsData[]) => {setTestsData(data);
        console.log("test data: ", data)})
        .catch(error => console.error('Error fetching problems:', error));
    };

    return(
        <>
        <button onClick={showModal}>testni primjeri</button>
        <ConfigProvider
            theme={{
            components: {
                Modal: {},
                Button: {
                colorPrimary: "#dd7230",
                colorPrimaryHover: "#dd723081",
                colorPrimaryActive: "#dd723081"
                },
            },
            }}
        >
            <React.Suspense fallback={<div>učitavanje...</div>}>
            <DynamicModal
            title="testni primjeri"
            open={open}
            onCancel={handleCancel}
            confirmLoading={confirmLoading}
            footer={        
            <div>
                <button key="ok" onClick={handleOk}>
                    ok
                </button>
            </div>
            }
            >

            <div className="info-table">
                {testsData?.length == 0 ? <div><p>ovaj zadatak nema testnih primjera</p></div> :
                <table>
                    <thead>
                        <tr>
                            <th>ulaz</th>
                            <th>izlaz</th>
                        </tr>
                    </thead>
                    <tbody>
                    {testsData?.map((test, index) => (
                        <tr className="info-table" key={index}>
                            <td>{test?.ulazniPodaci ? test.ulazniPodaci : ""}</td>
                            <td>{test?.izlazniPodaci ? test.izlazniPodaci : ""}</td>
                        </tr>
                    )) 
                    }
                    </tbody>
                </table>
                }

            </div>
            
 
            <React.Suspense fallback={<div>učitavanje...</div>}>
                <NewEvaluationTest zadatakId={zadatakId} onTestAdded={handleTestAdded} /> 
            </React.Suspense> 

            
            </DynamicModal>
            </React.Suspense>
        </ConfigProvider>
        </>
    )
}

export default EvaluationTests;