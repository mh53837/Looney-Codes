import React, { useState, useContext, useEffect } from "react";
import { ConfigProvider, Select } from "antd";
import { UserContext } from "../../context/userContext";
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

const DynamicModal = React.lazy(() => import("antd/lib/modal"));

const ProblemUpdateForm: React.FC<ProblemUpdateFormProps> = ({ zadatakId, onUpdateSuccess }) => {
  const [problemData, setProblemData] = useState<ProblemData | null>(null);
  const { user } = useContext(UserContext);

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
          title="uredi zadatak"
          open={open}
          onOk={handleOk}
          confirmLoading={confirmLoading}
          onCancel={handleCancel}
          cancelText="odustani"
          okText="spremi promjene"
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
            <p>broj bodova: </p>
            <Select 
              defaultValue= {problemData?.brojBodova.toString() || "10"  }
              style={{ width: 120 }}
              onChange={ handleBrojBodovaChange}
              options={[
                { value:"10", label: "10" },
                { value:"20", label: "20" },
                { value:"50", label: "50" },
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
            

           
          </div>
        </DynamicModal>
        </React.Suspense>
      </ConfigProvider>
    </>
  );
};

export default ProblemUpdateForm;
