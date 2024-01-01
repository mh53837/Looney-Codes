import React, { useState, useContext, useEffect } from "react";
import { ConfigProvider } from "antd";
import { UserContext } from "../../context/userContext";
import "../../styles/CompetitionUpdateForm.css";

interface UserProfileUpdateFormProps {
  korisnickoIme: string;
  onUpdateSuccess: () => void;
}
interface UserData {
  ime: string;
  prezime: string;
  email: string;
  fotografija: string;
}

const DynamicModal = React.lazy(() => import("antd/lib/modal"));

const UserProfileUpdateForm: React.FC<UserProfileUpdateFormProps> = ({ korisnickoIme, onUpdateSuccess }) => {
  const [userData, setUserData] = useState<UserData | null>(null);
  const { user } = useContext(UserContext); //ulogirani korisnik

  const [open, setOpen] = useState(false);
  const [confirmLoading, setConfirmLoading] = useState(false);

  const [updatedIme, setUpdatedIme] = useState<string>("");
  const [updatedPrezime, setUpdatedPrezime] = useState<string>("");
  const [updatedEmail, setUpdatedEmail] = useState<string>("");
  const [updatedLozinka, setUpdatedLozinka] = useState<string>(""); 
  const [confirmUpdatedLozinka, setConfirmUpdatedLozinka] = useState<string>(""); 
  const [updatedFotografija, setUpdatedFotografija] = useState<string>("");
  const [error, setError] = useState<string>("");

  const handleUpdatedLozinka = () => {
    setError("");
    if( updatedLozinka === "" && confirmUpdatedLozinka === ""){
      setUpdatedLozinka("");
      setConfirmUpdatedLozinka("");
      return true;
    }
    else if (updatedLozinka !== confirmUpdatedLozinka) {
      setUpdatedLozinka("");
      setConfirmUpdatedLozinka("");
      setError('Lozinke se ne podudaraju!');
        return false;
    }

    const minLength = 8;
    const hasUpperCase = /[A-Z]/.test(updatedLozinka);
    const hasLowerCase = /[a-z]/.test(updatedLozinka);
    const hasDigit = /\d/.test(updatedLozinka);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(updatedLozinka);
    if (updatedLozinka.length < minLength) {
      setUpdatedLozinka("");
      setConfirmUpdatedLozinka("");
      setError("Lozinka mora sadržavati barem 8 znakova!");
      return false;
    } else if (!hasUpperCase || !hasLowerCase || !hasDigit || !hasSpecialChar) {
      setUpdatedLozinka("");
      setConfirmUpdatedLozinka("");
      setError("Lozinka mora sadržavati barem jedno veliko slovo, interpunkcijski znak i broj!");
      return false;
    }
    return true;
  }

	useEffect(() => {
    const fetchInitialData = async () => {
      try {
        const fetchResponse = await fetch(`/api/user/profile/${korisnickoIme}`);
        const data: UserData = await fetchResponse.json();
        setUserData(data);

        setUpdatedIme(data.ime);
        setUpdatedPrezime(data.prezime);
        setUpdatedEmail(data.email);
        setUpdatedFotografija(data.fotografija);
      } catch (error) {
        console.error("Error fetching initial data:", error);
      }
    };

    fetchInitialData();
  }, [korisnickoIme]);

  const showModal = () => {
    setOpen(true);
  };

  const handleOk = async () => {
    setConfirmLoading(true);
    try {
      if(handleUpdatedLozinka())  {
        setError("");
        await HandleEdit(korisnickoIme);
        setOpen(false);
        setConfirmLoading(false);
        onUpdateSuccess();
      }
      else {
        setConfirmLoading(false);
      }
      
    } catch (error) {
      console.error("Error handling edit:", error);
      setConfirmLoading(false);
    }
  };

  const handleCancel = () => {
    console.log("Clicked cancel button");
    setOpen(false);
    setError("");
  
    setUpdatedIme(userData?.ime || "");
    setUpdatedPrezime(userData?.prezime || "");
    setUpdatedEmail(userData?.email || "");
    setUpdatedLozinka("");
    setConfirmUpdatedLozinka("");
    setUpdatedFotografija(userData?.fotografija || "");
  };


  const HandleEdit = async (korisnickoIme: string) => {

    try {
      console.log(`credentials:${user.korisnickoIme} : ${user.lozinka}`);
      const credentials = btoa(`${user.korisnickoIme}:${user.lozinka}`);
      const updatedData = {
        ime: updatedIme ? updatedIme : userData?.ime,
        prezime: updatedPrezime ? updatedPrezime : userData?.prezime,
        email: updatedEmail ? updatedEmail : userData?.email,
        fotografija: updatedFotografija ? updatedFotografija : userData?.fotografija,
        ...(updatedLozinka !== "" && { lozinka: updatedLozinka })
      }
      
      console.log("updated data:", updatedData);

      const response = await fetch(`/api/user/update/${korisnickoIme}`, {
        method: "POST",
        headers: {
          Authorization: `Basic ${credentials}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedData),
      });
      if (response.ok) {
        console.log(
          `successfully updated korisnik data for: ${korisnickoIme}`
        );
        const responseData = await response.json();
        console.log("API Response:", responseData);
      } else {
        console.error(
          "failed to update korisnik data:",
          response.statusText
        );
      }
    } catch (error) {
      console.error("error updating korisnik data:", error);
    }
  };

  return (
    <>
      <button onClick={showModal}>uredi korisničke podatke</button>
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
          title="uredi korisničke podatke"
          open={open}
          onOk={handleOk}
          confirmLoading={confirmLoading}
          onCancel={handleCancel}
          cancelText="odustani"
          okText="spremi promjene"
        >
          <div className="updateFormContainer">
						<p>ime:</p>
            <input
              className="problemUpdateForm"
              type="text"
              placeholder="ime"
              value={updatedIme}
              onChange={(e) => setUpdatedIme(e.target.value)}
            />
						<p>prezime:</p>
						<input
              className="problemUpdateForm"
              type="text"
              placeholder="prezime"
              value={updatedPrezime}
              onChange={(e) => setUpdatedPrezime(e.target.value)}
            />
            <p>email:</p>
						<input
              className="problemUpdateForm"
              type="text"
              placeholder="email"
              value={updatedEmail}
              onChange={(e) => setUpdatedEmail(e.target.value)}
            />
            <p>nova lozinka:</p>
						<input
              className="problemUpdateForm"
              type="password"
              placeholder="nova lozinka"
              value={updatedLozinka}
              onChange={(e) => setUpdatedLozinka(e.target.value)}
            />
            <p>potvrdi novu lozinku:</p>
						<input
              className="problemUpdateForm"
              type="password"
              placeholder="nova lozinka"
              value={confirmUpdatedLozinka}
              onChange={(e) => setConfirmUpdatedLozinka(e.target.value)}
            />
            <div className="error">{error}</div>

          </div>
        </DynamicModal>
        </React.Suspense>
      </ConfigProvider>
    </>
  );
};

export default UserProfileUpdateForm;
