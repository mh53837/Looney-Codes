import{r as m,S as e}from"./index-723c806d.js";const z=u=>{const[a,c]=m.useState({korisnickoIme:"",ime:"",prezime:"",email:"",requestedUloga:"",lozinka:"",confirmLozinka:"",slika:null}),[j,r]=m.useState(""),[k,h]=m.useState(""),d=i=>{c({...a,requestedUloga:i.target.value})};function n(i){const{name:o,value:s}=i.target;c(l=>({...l,[o]:s}))}function g(i){if(i.target.files&&i.target.files.length>0){const o=i.target.files[0];c(s=>({...s,slika:o}))}}function x(i){const s=/[A-Z]/.test(i),l=/[a-z]/.test(i),p=/\d/.test(i),t=/[!@#$%^&*(),.?":{}|<>]/.test(i);return i.length<8?"Lozinka mora sadržavati barem 8 znakova!":!s||!l||!p||!t?"Lozinka mora sadržavati barem jedno veliko slovo, interpunkcijski znak i broj!":null}function v(i){i.preventDefault(),r("");const o=x(a.lozinka);if(o){r(o);return}if(a.lozinka!==a.confirmLozinka){r("Lozinke se ne podudaraju!");return}const s=new FormData,l={korisnickoIme:a.korisnickoIme,ime:a.ime,prezime:a.prezime,email:a.email,requestedUloga:a.requestedUloga,lozinka:a.lozinka};if(a.slika)s.append("image",a.slika,a.slika.name);else{r("Dodajte sliku!");return}s.append("userData",new Blob([JSON.stringify(l)],{type:"application/json"}),"userData.json"),fetch("/api/user/register",{method:"POST",body:s}).then(t=>{t.status===401?r("Došlo je do pogreške, pokušajte ponovno!"):t.status===400?r("Korisničko ime ili email je zauzet, pokušajte ponovno!"):(h("Provjerite e-mail kako biste potvrdili registraciju!"),u.onRegister())})}return e.jsx("div",{className:"register-container",children:e.jsx("div",{className:"Register",children:e.jsxs("form",{onSubmit:v,children:[e.jsxs("div",{className:"FormRow",children:[e.jsx("label",{children:"korisničko ime"}),e.jsx("input",{name:"korisnickoIme",placeholder:"korisničko ime",onChange:n,value:a.korisnickoIme})]}),e.jsxs("div",{className:"FormRow",children:[e.jsx("label",{children:"ime"}),e.jsx("input",{name:"ime",placeholder:"ime",onChange:n,value:a.ime})]}),e.jsxs("div",{className:"FormRow",children:[e.jsx("label",{children:"prezime"}),e.jsx("input",{name:"prezime",placeholder:"prezime",onChange:n,value:a.prezime})]}),e.jsxs("div",{className:"FormRow",children:[e.jsx("label",{children:"email"}),e.jsx("input",{name:"email",placeholder:"email",onChange:n,value:a.email})]}),e.jsxs("div",{className:"FormRow",children:[e.jsx("label",{children:"lozinka"}),e.jsx("input",{name:"lozinka",placeholder:"lozinka",type:"password",onChange:n,value:a.lozinka})]}),e.jsxs("div",{className:"FormRow",children:[e.jsx("label",{children:"potvrdi lozinku"}),e.jsx("input",{name:"confirmLozinka",placeholder:"lozinka",type:"password",onChange:n,value:a.confirmLozinka})]}),e.jsxs("div",{className:"FormRow",children:[e.jsx("label",{children:"slika profila"}),e.jsx("input",{name:"slika",type:"file",onChange:g,accept:".jpg, .jpeg, .png"})]}),e.jsxs("div",{className:"FormRow",children:[e.jsx("label",{children:"uloga"}),e.jsxs("div",{className:"RoleOptions",children:[e.jsxs("label",{className:"RadioLbl",children:[e.jsx("input",{type:"radio",value:"VODITELJ",checked:a.requestedUloga==="VODITELJ",onChange:d}),"voditelj"]}),e.jsxs("label",{className:"RadioLbl",children:[e.jsx("input",{type:"radio",value:"NATJECATELJ",checked:a.requestedUloga==="NATJECATELJ",onChange:d}),"natjecatelj"]})]})]}),e.jsx("div",{className:"error",children:j}),e.jsx("div",{className:"poruka",children:k}),e.jsx("button",{type:"submit",children:"registriraj se!"})]})})})};export{z as default};
