import React, { useState, useContext, useEffect } from "react";
import { ConfigProvider, Select, Modal } from "antd";
import { UserContext } from "../../context/userContext";
import { ThemeContext } from "../../context/themeContext";
import "../../styles/CompetitionUpdateForm.css";

interface ProblemUpdateFormProps {
  zadatakId: BigInteger;
  onUpdateSuccess: () => void;
}
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

/* const NewEvaluationTest = React.lazy(() => import("../Problems/NewEvaluationTest") ); */

const ProblemUpdateForm: React.FC<ProblemUpdateFormProps> = ({ zadatakId, onUpdateSuccess }) => {
  const [problemData, setProblemData] = useState<ProblemData | null>(null);
  const { user } = useContext(UserContext);
  const { theme } = useContext(ThemeContext);
  const [open, setOpen] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);

  const [updatedNaziv, setUpdatedNaziv] = useState<string>("");
	const [updatedTekst, setUpdatedTekst] = useState<string>("");
  const [updatedTezinaZadatka, setUpdatedTezinaZadatka] = useState<string>("");
  const [updatedBrojBodova, setUpdatedBrojBodova] = useState<number>(0);

  const [selectedOption, setSelectedOption] = useState<boolean>(true);

  const handlePrivateOptionChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    console.log(event.target.value);
    if(event.target.value === 'true')
      setSelectedOption(true);
    else if (event.target.value === 'false')
      setSelectedOption(false);
  };

  const handleBrojBodovaChange = (value: string) => {
    const broj = parseInt(value, 10);
    if (broj == 10){
      setUpdatedTezinaZadatka("RECRUIT")
    } else if( broj == 20) {
      setUpdatedTezinaZadatka("VETERAN")
    } else if (broj == 50) {
      setUpdatedTezinaZadatka("REALISM")
    }
    setUpdatedBrojBodova(broj);
  }

	useEffect(() => {
    const fetchInitialData = async () => {
      try {
        const fetchResponse = await fetch(`/api/problems/get/${zadatakId}`);
        const data: ProblemData = await fetchResponse.json();
        setProblemData(data);

        setUpdatedNaziv(data.nazivZadatka);
        setUpdatedTekst(data.tekstZadatka);
        setUpdatedBrojBodova(data.brojBodova);
        setUpdatedTezinaZadatka(data.tezinaZadatka);
        setSelectedOption(data.privatniZadatak);
      } catch (error) {
        console.error("Error fetching initial data:", error);
      }
    };

    fetchInitialData();
  }, [zadatakId]);

  const renderModal = () => {
    return (
      <React.Suspense fallback={<div>učitavanje...</div>}>
      <Modal
        title="uredi zadatak"
        open={open}
        onOk={handleOk}
        confirmLoading={confirmLoading}
        onCancel={handleCancel}
        cancelText="odustani"
        okText="spremi promjene"
        okButtonProps={{ style: { color: "#2A2D34" } }}
        cancelButtonProps={{ style: { color: "#2A2D34" } }}
      >
        <div className="updateFormContainer">
          <p>naziv:</p>
          <input
            className="problemUpdateForm"
            type="text"
            placeholder="naziv zadatka"
            value={updatedNaziv}
            onChange={(e) => setUpdatedNaziv(e.target.value)}
          />
          <p>tekst:</p>
          <input
            className="problemUpdateForm"
            type="text"
            placeholder="tekst zadatka"
            value={updatedTekst}
            onChange={(e) => setUpdatedTekst(e.target.value)}
          />
          <p>težina (broj bodova): </p>
          <Select 
            defaultValue= {problemData?.brojBodova.toString() || "10"  }
            style={{ width: 160 }}
            onChange={ handleBrojBodovaChange}
            options={[
              { value:"10", label: "★ (10 bodova)" },
              { value:"20", label: "★★ (20 bodova)" },
              { value:"50", label: "★★★ (50 bodova)" },
            ]}
          />
          <p>status zadatka:</p>
          <label>
            <input
              type="radio"
              value="true"
              checked={selectedOption === true}
              onChange={handlePrivateOptionChange}
            />
            privatni
        </label>

        <label>
          <input
            type="radio"
            value="false"
            checked={selectedOption === false}
            onChange={handlePrivateOptionChange}
          />
          javni
        </label>

{/*           <React.Suspense fallback={<div>učitavanje...</div>}>
          <NewEvaluationTest zadatakId={zadatakId} /> 
        </React.Suspense> */}

        </div>
      </Modal>
      </React.Suspense>
    );
  }

  const showModal = () => {
    setOpen(true);
  };

  const handleOk = async () => {
    setConfirmLoading(true);

    try {
      await HandleEdit(zadatakId);
      setOpen(false);
      setConfirmLoading(false);
      onUpdateSuccess();
    } catch (error) {
      console.error("Error handling edit:", error);
      setConfirmLoading(false);
    }
  };

  const handleCancel = () => {
    console.log("Clicked cancel button");
    setOpen(false);
  };

  const HandleEdit = async (zadatakId: BigInteger) => {
    try {
      const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);

      const updatedData = {
        zadatakId: zadatakId,
        nazivZadatka: updatedNaziv ? updatedNaziv : problemData?.nazivZadatka,
				tekstZadatka: updatedTekst ? updatedTekst : problemData?.tekstZadatka,
        voditelj: problemData?.voditelj,
        brojBodova: updatedBrojBodova ? updatedBrojBodova : problemData?.brojBodova,
        privatniZadatak: selectedOption,
        tezinaZadatka: updatedTezinaZadatka ? updatedTezinaZadatka : problemData?.tezinaZadatka,
        vremenskoOgranicenje: problemData?.vremenskoOgranicenje,
      };
      console.log("updated data:", updatedData);
      
      const response = await fetch(`/api/problems/update/${zadatakId}`, {
        method: "POST",
        headers: {
          Authorization: `Basic ${credentials}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedData),
      });
      if (response.ok) {
        console.log(
          `successfully updated zadatak data for: ${zadatakId}`
        );
        const responseData = await response.json();
        console.log("API Response:", responseData);
      } else {
        console.error(
          "failed to update zadatak data:",
          response.statusText
        );
      }
    } catch (error) {
      console.error("error updating zadatak data:", error);
    }
  };

  return (
    <>
      <button onClick={showModal}>uredi</button>
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
                colorPrimary: "#2A2D34",
                colorPrimaryText: "#2A2D34",
                colorText: "#ECDCC9",
              },
              Button: {
                colorPrimary: "#dd7230e0",
                colorPrimaryHover: "#ECDCC9",
                colorPrimaryActive: "#2A2D34",
                colorLinkHover: "#000",
                
                textHoverBg:"#2A2D34",
                colorText: "#2A2D34",
                colorPrimaryText: "#2A2D34",
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
};

export default ProblemUpdateForm;
