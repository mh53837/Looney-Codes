import React, { useState, useEffect, useContext} from "react";
import { ConfigProvider, Modal } from "antd";
import "../../styles/CompetitionUpdateForm.css";
import {UserContext} from '../../context/userContext';
import { ThemeContext } from "../../context/themeContext";

interface EvaluationTestProps {
    zadatakId: BigInteger;
    visible: boolean;
    onClose: () => void;
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

const NewEvaluationTest = React.lazy(() => import("../Problems/NewEvaluationTest") );
const EvaluationTests: React.FC<EvaluationTestProps> = ({zadatakId, visible, onClose}) => {

    const [testsData, setTestsData] = useState<EvaluationTestsData[] | null>(null);
    const {user} = useContext(UserContext);
    const {theme} = useContext(ThemeContext);

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

    const renderModal = () => {

        return (
            <React.Suspense fallback={<div>učitavanje...</div>}>
            <Modal
            title="testni primjeri" 
            open={open}
            onCancel={handleClose}
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
            </Modal>
            </React.Suspense>
        );
    }
    

    const handleOk = async () => {
        onClose();
        setConfirmLoading(true);
        try {
          setOpen(false);
          setConfirmLoading(false);
        } catch (error) {
          console.error("Error handling edit:", error);
          setConfirmLoading(false);
        }
    };

    const handleClose = () => {
        handleCancel();
        onClose();
    }

    const handleCancel = () => {
        console.log("Clicked cancel button");
        setOpen(false);
        visible=false;
    };
    const showModal = () => {
        if (!open) {
            setOpen(true);
        }
    };

    useEffect(() => {
        if (!open) {
            setOpen(true);
        }
    }, [visible, open]);

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
        {visible && showModal()}
        { !visible && (<button onClick={showModal}>testni primjeri</button>)}
        {
        theme.isThemeDark == false ? (
            <ConfigProvider
                theme={{
                components: {
                    Modal: {
                    contentBg: "#fff",
                    
                    },
                    Button: {
                    colorPrimary: "#dd7230",
                    colorPrimaryHover: "#dd723081",
                    colorPrimaryActive: "#dd723081",
                    },
                }, 
                }}
            >
                { renderModal() }
            </ConfigProvider>
            ) : (
            <ConfigProvider
            theme={{
                components: {
                Modal: {
                    contentBg: "#2A2D34",
                    headerBg: "#2A2D34",
                    footerBg: "#2A2D34",
                    titleColor: "#ECDCC9",
                    colorPrimary: "#ECDCC9",
                    colorText: "#ECDCC9",
                },
                Button: {
                    colorPrimary: "#dd7230",
                    colorPrimaryHover: "#dd723081",
                    colorPrimaryActive: "#dd723081",
                    textHoverBg:"#2A2D34",
                },
                }, 
            }}
            >
            { renderModal() }
            </ConfigProvider>
            )
        }
        </>
    )
}

export default EvaluationTests;