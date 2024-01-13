import React, { useContext, useEffect, useState } from "react";
import { UserContext } from "../../context/userContext";
import { ThemeContext } from "../../context/themeContext";
import {Modal, ConfigProvider} from "antd";
import "../../styles/CompetitionUpdateForm.css";

interface ProblemData {
    voditelj: string;
    nazivZadatka: string;
    tekstZadatka: string;
    zadatakId: BigInteger;
    brojBodova: number;
    privatniZadatak: boolean;
    tezinaZadatka: string;
    vremenskoOgranicenje: number;
}

interface ProblemsToCompetitionProps{
    natjecanjeId: number;
    visible: boolean;
    onClose: () => void;
}

const AddProblemsToCompetition : React.FC<ProblemsToCompetitionProps> = ({natjecanjeId, visible, onClose}) => {
    const [competitionProblems, setCompetitionProblems] = useState<ProblemData[]>([]);

    const [problemsToAdd, setProblemsToAdd] = useState<ProblemData[]>([]);

    const {user} = useContext(UserContext);
    const {theme} = useContext(ThemeContext);

    const [open, setOpen] = useState(false);
    const [confirmLoading, setConfirmLoading] = useState(false);
    console.log("dodaj zadatke tema: ", theme);
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

    useEffect( () => {
        const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);
            const options = {
            method: "GET",
            headers: {
                Authorization: `Basic ${credentials}`,
                "Content-Type": "application/json",
            },
            };
        fetch(`/api/problems/my`, options)
        .then(response => response.json())
        .then((data: ProblemData[]) => { 
            const filteredProblemsToAdd = data.filter(problem =>
                !competitionProblems.some(compProblem => compProblem.zadatakId === problem.zadatakId)
            );
            setProblemsToAdd(filteredProblemsToAdd);
            console.log("test data: ", data)
        })
        .catch(error => console.error('Error fetching problems:', error));
    }, [user, competitionProblems] );

    const handleAddProblem = async (zadatakId: BigInteger) => {
        try {
          const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);
          const response = await fetch(
            `/api/natjecanja/add/zadatak/${natjecanjeId}/${zadatakId}`,
            {
              method: "POST",
              headers: {
                Authorization: `Basic ${credentials}`,
                "Content-Type": "application/json",
              },
            }
          );
          if (response.ok) {
            const problemToAdd = problemsToAdd.find(p => p.zadatakId === zadatakId);
            if (problemToAdd && !competitionProblems.some(p => p.zadatakId === zadatakId)) {
              setCompetitionProblems([...competitionProblems, problemToAdd]);
              setProblemsToAdd(problemsToAdd.filter(p => p.zadatakId !== zadatakId));
              console.log("Successfully added problem to competition");
            } else {
              console.log("Problem is already in competitionProblems or not found in problemsToAdd");
            }
          } else {
            console.error("Failed to add problem to competition:", response.statusText);
          }
        } catch (error) {
          console.error("Error adding problem to competition:", error);
        }
      };
    
      const handleRemoveProblem = async (zadatakId: BigInteger) => {
        try {
          const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);
          const response = await fetch(
            `/api/natjecanja/remove/zadatak/${natjecanjeId}/${zadatakId}`,
            {
              method: "POST",
              headers: {
                Authorization: `Basic ${credentials}`,
                "Content-Type": "application/json",
              },
            }
          );
          if (response.ok) {
            setCompetitionProblems(competitionProblems.filter(p => p.zadatakId !== zadatakId));
            const removedProblem = competitionProblems.find(p => p.zadatakId === zadatakId);
            if (removedProblem) {
              setProblemsToAdd([...problemsToAdd, removedProblem]);
            }
            console.log("Successfully removed problem from competition");
          } else {
            console.error("Failed to remove problem from competition:", response.statusText);
          }
        } catch (error) {
          console.error("Error removing problem from competition:", error);
        }
      };

    const renderModal = () => {
        return (
            <React.Suspense fallback={<div>učitavanje...</div>}>
            <Modal
            width={900}
            title="zadaci na natjecanju" 
            open={open}
            onCancel={handleClose}
            okText={"spremi promjene"}
            confirmLoading={confirmLoading}
            footer={        
            <div>
                <button key="ok" onClick={handleOk}>
                    spremi promjene
                </button>
            </div>
            }
            >
            <div className="info-table">
                {competitionProblems?.length == 0 ? <div><p>ovo natjecanje nema dodijeljenih zadataka</p></div> :
                <table>
                    <thead>
                        <tr>
                            <th>naziv</th>
                            <th>vremensko ograničenje</th>
                            <th>težina</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    {competitionProblems?.map((problem, index) => (
                        <tr className="info-table" key={index}>
                            <td>{problem?.nazivZadatka ? problem.nazivZadatka : ""}</td>
                            <td>{problem?.vremenskoOgranicenje ? problem.vremenskoOgranicenje : ""}</td>
                            <td>{problem?.brojBodova == 10 ? "★" : problem.brojBodova == 20 ? "★★" : "★★★"}</td>
                            <td><button style={{ fontSize: 18, margin: 2 }}onClick= { () => handleRemoveProblem(problem.zadatakId)}>-</button></td>
                        </tr>
                    )) 
                    }
                    </tbody>
                </table>
                }

                <h4>odaberi zadatake:</h4>
                {problemsToAdd.length == 0 ? (
                    <p>svi zadaci su već dio natjecanja</p>
                ) : ( 
                <div className="info-table">
                    <table>
                        <thead>
                            <tr>
                                <th>naziv</th>
                                <th>vremensko ograničenje</th>
                                <th>težina</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {problemsToAdd?.map((problem, index) => (
                            <tr className="info-table" key={index}>
                                <td>{problem?.nazivZadatka ? problem.nazivZadatka : ""}</td>
                                <td>{problem?.vremenskoOgranicenje ? problem.vremenskoOgranicenje : ""}</td>
                                <td>{problem?.brojBodova == 10 ? '★' : problem.brojBodova == 20 ? "★★" : "★★★"}</td>
                                <td><button style={{ fontSize: 16 }} onClick = { () => handleAddProblem(problem.zadatakId)}>+</button></td>
                            </tr>
                            ))}
                        </tbody>
                    </table>
                </div>)}
            </div>
 
            </Modal>
            </React.Suspense>
        );
    }
    

    const handleOk = async () => {
        setConfirmLoading(true);
        try {
          setOpen(false);
          setConfirmLoading(false);
          onClose();
          
          
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
          {!visible && <button onClick={showModal}>zadaci</button>}
          {theme.isThemeDark === false ? (
            <>
              {console.log("light theme applied")}
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
                {renderModal()}
              </ConfigProvider>
            </>
          ) : (
            <>
              {console.log("dark theme applied")}
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
                      textHoverBg: "#2A2D34",
                    },
                  },
                }}
              >
                {renderModal()}
              </ConfigProvider>
            </>
          )}
        </>
      );
}      

export default AddProblemsToCompetition;