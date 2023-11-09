import { useEffect, useState} from "react";

export const getHome = ()=>{
    useEffect(() => {
        fetch('/api')
            .then(response => response.json())
            .catch(error => console.error('Error:', error));
    }, []);
}

