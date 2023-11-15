import { useEffect} from "react";

export const GetHome = ()=>{
    useEffect(() => {
        fetch('/api')
            .then(response => response.json())
            .catch(error => console.error('Error:', error));
    }, []);
}

