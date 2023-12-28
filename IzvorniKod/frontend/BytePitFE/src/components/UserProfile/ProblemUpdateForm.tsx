import React, { useState, useContext, useEffect } from "react";
import { ConfigProvider } from "antd";
import { UserContext } from "../../context/userContext";
import "../../styles/CompetitionUpdateForm.css";

interface ProblemUpdateFormProps {
  zadatakId: number;
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
	const [updatedTekst, setUpdatedTekst] = useState<string>("");/* 
  const [updatedBrojBodova, setUpdatedBrojBodova] = useState<number>(10); */

	useEffect(() => {
    const fetchInitialData = async () => {
      try {
        const fetchResponse = await fetch(`/api/problems/get/${zadatakId}`);
        const data: ProblemData = await fetchResponse.json();
        setProblemData(data);

        setUpdatedNaziv(data.nazivZadatka);
        setUpdatedTekst(data.tekstZadatka);/* 
        setUpdatedBrojBodova(data.brojBodova); */
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


  const HandleEdit = async (zadatakId: number) => {
    try {
      const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);

      const updatedData = {
        zadatakId: zadatakId,
        nazivZadatka: updatedNaziv ? updatedNaziv : problemData?.nazivZadatka,
				tekstZadatka: updatedTekst ? updatedTekst : problemData?.tekstZadatka,
        voditelj: problemData?.voditelj,
        brojBodova: problemData?.brojBodova,
        privatniZadatak: problemData?.privatniZadatak,
        tezinaZadatka: problemData?.tezinaZadatka,
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
 {/*            <input
              className="problemUpdateForm"
              type="text"
              placeholder="broj bodova"
              value={updatedBrojBodova}
              onChange={(e) => setUpdatedBrojBodova(e.target.value)}
            /> */}
          </div>
        </DynamicModal>
        </React.Suspense>
      </ConfigProvider>
    </>
  );
};

export default ProblemUpdateForm;
