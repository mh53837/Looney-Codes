import{r as s,U as j,z as a}from"./index-d62fd9a5.js";const v=({zadatakId:u,onTestAdded:z})=>{const[h,i]=s.useState(""),{user:r}=s.useContext(j),[o,l]=s.useState({ulazniPodaci:"",izlazniPodaci:""}),c=n=>{const{name:d,value:t}=n.target;l(e=>({...e,[d]:t}))},p=()=>{if(i(""),o.ulazniPodaci===""&&o.izlazniPodaci===""){i("unesite potrebne podatke i pokušajte ponovno");return}const n={ulazniPodaci:o.ulazniPodaci,izlazniPodaci:o.izlazniPodaci},t={method:"POST",headers:{Authorization:`Basic ${btoa(`${r.korisnickoIme}:${r.lozinka}`)}`,"Content-Type":"application/json"},body:JSON.stringify(n)};fetch(`/api/problems/get/${u}/addTest`,t).then(e=>{if(!e.ok)throw new Error(`HTTP error! Status: ${e.status}`);return e.json()}).then(e=>{console.log("Server response:",e),z(),l({ulazniPodaci:"",izlazniPodaci:""})}).catch(e=>{console.error("Fetch error:",e),i("Došlo je do pogreške, pokušajte ponovno!")})};return a.jsxs("div",{children:[a.jsx("div",{className:"FormRow",children:a.jsxs("div",{className:"zadatakTestovi",children:[a.jsx("h3",{children:"novi testni primjer:"}),a.jsxs("div",{className:"ulaz",children:[a.jsx("label",{children:"ulaz programa:"}),a.jsx("input",{name:"ulazniPodaci",placeholder:"ulaz",onChange:c,value:o.ulazniPodaci})]}),a.jsxs("div",{className:"izlaz",children:[a.jsx("label",{children:"izlaz programa:"}),a.jsx("input",{name:"izlazniPodaci",placeholder:"izlaz",onChange:c,value:o.izlazniPodaci})]}),a.jsx("button",{onClick:p,children:"dodaj!"})]})}),a.jsx("div",{className:"error",children:h})]})};export{v as default};
