import{r as t,U as h,S as o,ac as p}from"./index-60b6e0ac.js";const x=c=>{const[i,k]=t.useState({korisnickoIme:"",lozinka:""}),[u,n]=t.useState(""),{setUser:m}=t.useContext(h);function a(r){const{name:l,value:s}=r.target;k(e=>({...e,[l]:s}))}function d(r){r.preventDefault(),n("");const s={method:"POST",headers:{Authorization:`Basic ${btoa(`${i.korisnickoIme}:${i.lozinka}`)}`,"Content-Type":"application/json"},body:JSON.stringify(i)};fetch("/api/user/login",s).then(e=>{if(r.preventDefault(),console.log(e.status),e.status===200)return console.log("Success!"),e.json();throw e.status===401?(n("Neispravno korisničko ime ili lozinka!"),new Error("Bad request")):e.status===403?(n("Email adresa nije potvrđena!"),new Error("Forbidden")):(n("Greška prilikom prijave, pokušajte ponovno!"),new Error("Error"))}).then(e=>{c.onLogin(i.korisnickoIme,i.lozinka,e.uloga),m({korisnickoIme:i.korisnickoIme,lozinka:i.lozinka,uloga:e.uloga})}).catch(e=>{console.error(e)})}return o.jsx("div",{className:"login-container",children:o.jsx("div",{className:"Login",children:o.jsxs("form",{onSubmit:d,children:[o.jsxs("div",{className:"FormRow",children:[o.jsx("label",{children:"korisničko ime"}),o.jsx("input",{name:"korisnickoIme",placeholder:"korisničko ime",onChange:a,value:i.korisnickoIme})]}),o.jsxs("div",{className:"FormRow",children:[o.jsx("label",{children:"lozinka"}),o.jsx("input",{name:"lozinka",placeholder:"lozinka",type:"password",onChange:a,value:i.lozinka})]}),o.jsx("div",{className:"error",children:u}),o.jsx("button",{type:"submit",children:"prijavi se!"}),o.jsx(p,{to:"/register",children:o.jsx("button",{type:"button",children:"registracija"})})]})})})};export{x as default};
