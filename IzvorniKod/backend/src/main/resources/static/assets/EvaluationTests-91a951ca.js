import{R as m,Q as y,r,U as T,ae as b,S as e,M as p,ak as z}from"./index-c885bd5f.js";const A=m.lazy(()=>y(()=>import("./NewEvaluationTest-bbc5e0b4.js"),["assets/NewEvaluationTest-bbc5e0b4.js","assets/index-c885bd5f.js","assets/index-1a6d5c6b.css","assets/NewEvaluationTest-1dc0f828.css"])),$=({zadatakId:c,visible:a,onClose:j})=>{const[t,x]=r.useState(null),{user:s}=r.useContext(T),{theme:C}=r.useContext(b),[l,d]=r.useState(!1),[g,h]=r.useState(!1);r.useEffect(()=>{const i={method:"GET",headers:{Authorization:`Basic ${btoa(`${s.korisnickoIme}:${s.lozinka}`)}`,"Content-Type":"application/json"}};fetch(`/api/problems/get/${c}/tests`,i).then(o=>o.json()).then(o=>{x(o),console.log("test data: ",o)}).catch(o=>console.error("Error fetching problems:",o))},[c,s]);const u=()=>e.jsx(m.Suspense,{fallback:e.jsx("div",{children:"učitavanje..."}),children:e.jsxs(z,{title:"testni primjeri",open:l,onCancel:v,confirmLoading:g,footer:e.jsx("div",{children:e.jsx("button",{onClick:E,children:"spremi promjene"},"ok")}),children:[e.jsx("div",{className:"info-table",children:(t==null?void 0:t.length)==0?e.jsx("div",{children:e.jsx("p",{children:"ovaj zadatak nema testnih primjera"})}):e.jsxs("table",{children:[e.jsx("thead",{children:e.jsxs("tr",{children:[e.jsx("th",{children:"ulaz"}),e.jsx("th",{children:"izlaz"})]})}),e.jsx("tbody",{children:t==null?void 0:t.map((n,i)=>e.jsxs("tr",{className:"info-table",children:[e.jsx("td",{children:n!=null&&n.ulazniPodaci?n.ulazniPodaci:""}),e.jsx("td",{children:n!=null&&n.izlazniPodaci?n.izlazniPodaci:""})]},i))})]})}),e.jsx(m.Suspense,{fallback:e.jsx("div",{children:"učitavanje..."}),children:e.jsx(A,{zadatakId:c,onTestAdded:k})})]})}),E=async()=>{j(),h(!0);try{d(!1),h(!1)}catch(n){console.error("Error handling edit:",n),h(!1)}},v=()=>{P(),j()},P=()=>{console.log("Clicked cancel button"),d(!1),a=!1},f=()=>{l||d(!0)};r.useEffect(()=>{l||d(!0)},[a,l]);const k=()=>{const i={method:"GET",headers:{Authorization:`Basic ${btoa(`${s.korisnickoIme}:${s.lozinka}`)}`,"Content-Type":"application/json"}};fetch(`/api/problems/get/${c}/tests`,i).then(o=>o.json()).then(o=>{x(o),console.log("test data: ",o)}).catch(o=>console.error("Error fetching problems:",o))};return e.jsxs(e.Fragment,{children:[a&&f(),!a&&e.jsx("button",{onClick:f,children:"testni primjeri"}),C.isThemeDark==!1?e.jsx(p,{theme:{components:{Modal:{contentBg:"#fff"},Button:{colorPrimary:"#dd7230",colorPrimaryHover:"#dd723081",colorPrimaryActive:"#dd723081"}}},children:u()}):e.jsx(p,{theme:{components:{Modal:{contentBg:"#2A2D34",headerBg:"#2A2D34",footerBg:"#2A2D34",titleColor:"#ECDCC9",colorPrimary:"#ECDCC9",colorText:"#ECDCC9"},Button:{colorPrimary:"#dd7230",colorPrimaryHover:"#dd723081",colorPrimaryActive:"#dd723081",textHoverBg:"#2A2D34"}}},children:u()})]})};export{$ as default};
