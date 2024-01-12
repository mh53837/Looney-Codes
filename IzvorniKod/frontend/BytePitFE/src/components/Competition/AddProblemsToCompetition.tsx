import React, { useContext, useEffect, useState } from "react";
import { UserContext } from "../../context/userContext";
import { ThemeContext } from "../../context/themeContext";
import {Modal, ConfigProvider} from "antd";
import "../../styles/CompetitionUpdateForm.css";

interface UserData {
    korisnickoIme: string;
    ime: string;
    prezime: string;
    email: string;
    uloga: string;
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


interface ProblemsToCompetitionProps{
    natjecanjeId: BigInteger;
    visible: boolean;
    onClose: () => void;
}

const AddProblemsToCompetition : React.FC<ProblemsToCompetitionProps> = ({natjecanjeId, visible, onClose}) => {
    const [competitionProblems, setCompetitionProblems] = useState<ProblemData[]>();
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
        fetch(`/api/natjecanja/get/zadaci/${natjecanjeId}`, options)
        .then(response => response.json())
        .then((data: ProblemData[]) => {setCompetitionProblems(data);
        console.log("test data: ", data)})
        .catch(error => console.error('Error fetching problems:', error));
    }, [natjecanjeId, user] );

    const renderModal = () => {
        return (
            <React.Suspense fallback={<div>učitavanje...</div>}>
            <Modal
            title="zadaci na natjecanju" 
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
                {competitionProblems?.length == 0 ? <div><p>ovao natjecanje nema dodijeljenih zadataka</p></div> :
                <table>
                    <thead>
                        <tr>
                            <th>voditelj</th>
                            <th>naziv</th>
                            <th>tekst</th>
                            <th>težina</th>
                        </tr>
                    </thead>
                    <tbody>
                    {competitionProblems?.map((problem, index) => (
                        <tr className="info-table" key={index}>
                            <td>{problem?.voditelj.korisnickoIme ? problem.voditelj.korisnickoIme  : ""}</td>
                            <td>{problem?.nazivZadatka ? problem.nazivZadatka : ""}</td>
                            <td>{problem?.tekstZadatka ? problem.tekstZadatka : ""}</td>
                            <td>{problem?.brojBodova == 10 ? '⭐' : problem.brojBodova == 20 ? "⭐⭐" : "⭐⭐⭐"}</td>
                        </tr>
                    )) 
                    }
                    </tbody>
                </table>
                }

            </div>
 
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
    
    return (
        <>
        {visible && showModal()}
        { !visible && (<button onClick={showModal}>zadaci</button>)}
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
    );
}

export default AddProblemsToCompetition;