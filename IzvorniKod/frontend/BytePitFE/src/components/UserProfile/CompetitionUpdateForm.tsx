import React, { useState, useContext, useEffect } from "react";
import { DatePicker, Space, ConfigProvider, Modal } from "antd";
import type { DatePickerProps } from "antd/es/date-picker";
import hrHR from 'antd/lib/locale/hr_HR'; 
import { Dayjs } from "dayjs";
import dayjs from "dayjs";
import 'dayjs/locale/hr';
import { UserContext } from "../../context/userContext";
import "../../styles/CompetitionUpdateForm.css";

interface CompetitionUpdateFormProps {
  natjecanjeId: number;
  onUpdateSuccess: () => void;
  theme: boolean;
}
interface CompetitionData {
  natjecanjeId: number;
  korisnickoImeVoditelja: string;
  nazivNatjecanja: string;
  pocetakNatjecanja: string;
  krajNatjecanja: string;
}


const CompetitonUpdateForm: React.FC<CompetitionUpdateFormProps> = ({natjecanjeId, onUpdateSuccess, theme}) => {
  const { user } = useContext(UserContext);
  const [competitionData, setCompetitionData] = useState<CompetitionData | null>(null);
  const [open, setOpen] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);

  const [updatedNaziv, setUpdatedNaziv] = useState<string>("");
  const [updatedPocetak, setUpdatedPocetak] = useState<Dayjs | null>(null);
  const [updatedKraj, setUpdatedKraj] = useState<Dayjs | null>(null);

  useEffect(() => {
    const fetchInitialData = async () => {
      try {
        const fetchResponse = await fetch(
          `/api/natjecanja/get/${natjecanjeId}`
        );
        const data: CompetitionData = await fetchResponse.json();
        setCompetitionData(data);

        setUpdatedNaziv(data.nazivNatjecanja);
        setUpdatedPocetak(dayjs(data.pocetakNatjecanja));
        setUpdatedKraj(dayjs(data.krajNatjecanja));
      } catch (error) {
        console.error("Error fetching initial data:", error);
      }
    };

    fetchInitialData();
  }, [natjecanjeId]);

  const renderModal = () => {

    return (
      <ConfigProvider locale={hrHR}>
      <React.Suspense fallback={<div>učitavanje...</div>}>
      <Modal
        title="uredi natjecanje"
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
          <input
            className="competitionUpdateForm"
            type="text"
            placeholder="naziv natjecanja"
            value={updatedNaziv}
            onChange={(e) => setUpdatedNaziv(e.target.value)}
          />

          <Space direction="vertical">
            
            <DatePicker
              showTime
              placeholder="početak natjecanja"
              value={updatedPocetak}
              onChange={onPocetakChange}
            />

            <DatePicker
              showTime
              placeholder="kraj natjecanja"
              value={updatedKraj}
              onChange={onKrajChange}
            />
            
          </Space>
        </div>
      </Modal>
      </React.Suspense>
      </ConfigProvider>
    );
  }

  const showModal = () => {
    setOpen(true);
  };

  const handleOk = async () => {
    setConfirmLoading(true);

    try {
      await HandleEdit(natjecanjeId);
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

  const onPocetakChange: DatePickerProps["onChange"] = (date, dateString) => {
    console.log(date, dateString);
    setUpdatedPocetak(date);
  };

  const onKrajChange: DatePickerProps["onChange"] = (date, dateString) => {
    console.log(date, dateString);
    setUpdatedKraj(date);
  };

  const HandleEdit = async (natjecanjeId: number) => {
    try {
      const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);

      const updatedData = {
        natjecanjeId: natjecanjeId,
        nazivNatjecanja: updatedNaziv
          ? updatedNaziv
          : competitionData?.nazivNatjecanja,
        pocetakNatjecanja: updatedPocetak
          ? updatedPocetak.toISOString()
          : competitionData?.pocetakNatjecanja,
        krajNatjecanja: updatedKraj
          ? updatedKraj.toISOString()
          : competitionData?.krajNatjecanja,
        korisnickoImeVoditelja: competitionData?.korisnickoImeVoditelja,
      } as CompetitionData;
      console.log("updated data:", updatedData);

      const response = await fetch("/api/natjecanja/update", {
        method: "POST",
        headers: {
          Authorization: `Basic ${credentials}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedData),
      });
      if (response.ok) {
        console.log(
          `successfully updated competition data for: ${natjecanjeId}`
        );
        const responseData = await response.json();
        console.log("API Response:", responseData);
      } else {
        console.error(
          "failed to update competition data:",
          response.statusText
        );
      }
    } catch (error) {
      console.error("error updating competition data:", error);
    }
  };
  return (
    <>
      <button onClick={showModal}>uredi</button>
      {
        theme === false ? (
          <ConfigProvider
            locale={hrHR}
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

export default CompetitonUpdateForm;
