import React, {useState, useEffect} from 'react';
import { useParams } from 'react-router-dom';


const ConfirmEmail:React.FC = () => { 
    const { id } = useParams();    
    const [hasFetched, setHasFetched] = useState(false);
    const [errorPoruka, setErrorPoruka] = useState<string | null>(null);
    const [hasReceivedResponse, setHasReceivedResponse] = useState(false);


    useEffect( () => {
        const fetchUserData = async () => {
            try {
                fetch(`/api/user/confirmEmail/${id}`).then((response) => {
                    if(!hasReceivedResponse && !hasFetched) {
                        if (response.status === 200) {
                            console.log("Success!");
                            setErrorPoruka('uspješno si potvrdio svoju email adresu!');
                        } else if (response.status === 404) {
                            setErrorPoruka('korisnik ne postoji!');
                        } else if (response.status === 400) {
                            setErrorPoruka('uspješno si potvrdio svoju email adresu!');
                        } else {
                            setErrorPoruka(`Unexpected status: ${response.status}`);
                        }
                        setHasReceivedResponse(true);
                    }
                })

               
            }
            catch (error) {
                console.log(error);
            }
            finally {
                setHasFetched(true);
            }    
        };

        if(!hasFetched && !hasReceivedResponse){
            fetchUserData();
        } 


    }, [id,  hasFetched, hasReceivedResponse, setErrorPoruka]
    );
    return (
        <div>
            <p>{errorPoruka}</p>
        </div>
    );

};
export default ConfirmEmail;
