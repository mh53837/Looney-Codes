import{r as o,U as A,z as e,x as E,R as V}from"./index-d62fd9a5.js";import{m as O}from"./NewProblem-d1f55048.js";import{d as U,a as $,D as h}from"./hr-1d671007.js";import{A as K}from"./AddProblemsToCompetition-add71484.js";import{S as H}from"./index-3b6b477c.js";import"./index-bd4635f9.js";/* empty css                              */const Q=({handleOk:N})=>{const p=U(),[d,u]=o.useState(null),[x,m]=o.useState(!1),{user:j}=o.useContext(A),[f,c]=o.useState(""),[g,k]=o.useState(""),[S,b]=o.useState(null),[C,w]=o.useState(null),[z,P]=O.useMessage(),[t,i]=o.useState({nazivNatjecanja:"",pocetakNatjecanja:"",krajNatjecanja:""});function y(a){const{name:n,value:s}=a.target;i(l=>({...l,[n]:s}))}const T=(a,n)=>{console.log(a,n),b(a),a&&(i(s=>({...s,pocetakNatjecanja:a.toISOString()})),console.log(t.pocetakNatjecanja))},I=a=>{i({nazivNatjecanja:"",pocetakNatjecanja:"",krajNatjecanja:""}),k(""),c(""),console.log("new competition id:",a),u(a),m(!0)},D=()=>{m(!1),u(null),N()},F=(a,n)=>{console.log(a,n),w(a),a&&(i(s=>({...s,krajNatjecanja:a.toISOString()})),console.log(t.krajNatjecanja))},R=async a=>{if(a.preventDefault(),c(""),t.nazivNatjecanja===""||t.pocetakNatjecanja===""||t.krajNatjecanja===""){c("unesite potrebne podatke i pokušajte ponovno");return}const n={nazivNatjecanja:t.nazivNatjecanja,pocetakNatjecanja:t.pocetakNatjecanja,krajNatjecanja:t.krajNatjecanja,korisnickoImeVoditelja:j.korisnickoIme},l={method:"POST",headers:{Authorization:`Basic ${btoa(`${j.korisnickoIme}:${j.lozinka}`)}`,"Content-Type":"application/json"},body:JSON.stringify(n)};try{const r=await fetch("/api/natjecanja/new",l);if(!r.ok)throw new Error(`HTTP error! Status: ${r.status}`);const v=await r.json();z.open({type:"success",content:"uspješno ste kreirali novo natjecanje"}),k("uspješno ste kreirali novo natjecanje"),I(v.natjecanjeId),console.log("Server response:",v)}catch(r){console.error("Fetch error:",r),c("Došlo je do pogreške, pokušajte ponovno!")}};return e.jsxs("div",{className:"newProblem-container",children:[P,e.jsx("div",{className:"NewProblem",children:e.jsxs("form",{onSubmit:R,children:[e.jsxs("div",{className:"FormRow",children:[e.jsx("label",{children:"naziv natjecanja"}),e.jsx("input",{name:"nazivNatjecanja",placeholder:"naziv natjecanja",onChange:y,value:t.nazivNatjecanja})]}),e.jsx(E,{locale:$,children:e.jsxs(H,{direction:"vertical",children:[e.jsxs("div",{className:"FormRowDate",children:[e.jsx("label",{children:"početak natjecanja "}),e.jsx(h,{showTime:!0,placeholder:"početak natjecanja",defaultValue:p,value:S,onChange:T,size:"small"})]}),e.jsxs("div",{className:"FormRowDate",children:[e.jsx("label",{children:" kraj natjecanja"}),e.jsx(h,{showTime:!0,placeholder:"kraj natjecanja",defaultValue:p,value:C,onChange:F,size:"small"})]})]})}),e.jsx("div",{className:"error",children:f}),e.jsx("div",{className:"poruka",children:g}),e.jsx("button",{type:"submit",children:"stvori natjecanje!"})]})}),x&&d!==null&&e.jsx(V.Suspense,{fallback:e.jsx("div",{children:"učitavanje..."}),children:e.jsx(K,{natjecanjeId:d,visible:!0,onClose:D})})]})};export{Q as default};
