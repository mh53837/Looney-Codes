import{r as n,U,S as e,M as p,R as I,ak as M,V as R}from"./index-4f9613f9.js";import{a as x,d as C,D as y}from"./hr-a8f44c14.js";const $=({natjecanjeId:s,onUpdateSuccess:v,theme:P})=>{const{user:h}=n.useContext(U),[t,A]=n.useState(null),[S,c]=n.useState(!1),[D,l]=n.useState(!1),[i,f]=n.useState(""),[d,m]=n.useState(null),[j,k]=n.useState(null);n.useEffect(()=>{(async()=>{try{const r=await(await fetch(`/api/natjecanja/get/${s}`)).json();A(r),f(r.nazivNatjecanja),m(C(r.pocetakNatjecanja)),k(C(r.krajNatjecanja))}catch(o){console.error("Error fetching initial data:",o)}})()},[s]);const g=()=>e.jsx(p,{locale:x,children:e.jsx(I.Suspense,{fallback:e.jsx("div",{children:"učitavanje..."}),children:e.jsx(M,{title:"uredi natjecanje",open:S,onOk:T,confirmLoading:D,onCancel:B,cancelText:"odustani",okText:"spremi promjene",okButtonProps:{style:{color:"#2A2D34"}},cancelButtonProps:{style:{color:"#2A2D34"}},children:e.jsxs("div",{className:"updateFormContainer",children:[e.jsx("input",{className:"competitionUpdateForm",type:"text",placeholder:"naziv natjecanja",value:i,onChange:a=>f(a.target.value)}),e.jsxs(R,{direction:"vertical",children:[e.jsx(y,{showTime:!0,placeholder:"početak natjecanja",value:d,onChange:E}),e.jsx(y,{showTime:!0,placeholder:"kraj natjecanja",value:j,onChange:w})]})]})})})}),N=()=>{c(!0)},T=async()=>{l(!0);try{await z(s),c(!1),l(!1),v()}catch(a){console.error("Error handling edit:",a),l(!1)}},B=()=>{console.log("Clicked cancel button"),c(!1)},E=(a,o)=>{console.log(a,o),m(a)},w=(a,o)=>{console.log(a,o),k(a)},z=async a=>{try{const o=btoa(`${h.korisnickoIme}:${h.lozinka}`),r={natjecanjeId:a,nazivNatjecanja:i||(t==null?void 0:t.nazivNatjecanja),pocetakNatjecanja:d?d.toISOString():t==null?void 0:t.pocetakNatjecanja,krajNatjecanja:j?j.toISOString():t==null?void 0:t.krajNatjecanja,korisnickoImeVoditelja:t==null?void 0:t.korisnickoImeVoditelja};console.log("updated data:",r);const u=await fetch("/api/natjecanja/update",{method:"POST",headers:{Authorization:`Basic ${o}`,"Content-Type":"application/json"},body:JSON.stringify(r)});if(u.ok){console.log(`successfully updated competition data for: ${a}`);const O=await u.json();console.log("API Response:",O)}else console.error("failed to update competition data:",u.statusText)}catch(o){console.error("error updating competition data:",o)}};return e.jsxs(e.Fragment,{children:[e.jsx("button",{onClick:N,children:"uredi"}),P===!1?e.jsx(p,{locale:x,theme:{components:{Modal:{contentBg:"#fff"},Button:{colorPrimary:"#dd7230",colorPrimaryHover:"#dd723081",colorPrimaryActive:"#dd723081"}}},children:g()}):e.jsx(p,{theme:{components:{Modal:{contentBg:"#2A2D34",headerBg:"#2A2D34",footerBg:"#2A2D34",titleColor:"#ECDCC9",colorPrimary:"#2A2D34",colorPrimaryText:"#2A2D34",colorText:"#ECDCC9"},Button:{colorPrimary:"#dd7230e0",colorPrimaryHover:"#ECDCC9",colorPrimaryActive:"#2A2D34",colorLinkHover:"#000",textHoverBg:"#2A2D34",colorText:"#2A2D34",colorPrimaryText:"#2A2D34"}}},children:g()})]})};export{$ as default};
