import{r as t,U as J,ae as I,S as e,M as A,R as M,ak as H}from"./index-121ac392.js";const q=({korisnickoIme:f,onUpdateSuccess:D})=>{const[r,b]=t.useState(null),{user:p}=t.useContext(J),{theme:m}=t.useContext(I),[P,x]=t.useState(!1),[S,u]=t.useState(!1),[g,k]=t.useState(""),[j,v]=t.useState(""),[C,y]=t.useState(""),[o,l]=t.useState(""),[z,i]=t.useState(""),[h,U]=t.useState(""),[N,c]=t.useState(""),L=a=>{U(a.target.value)};t.useEffect(()=>{},[m.isThemeDark]);const w=()=>{if(c(""),o===""&&z==="")return l(""),i(""),!0;if(o!==z)return l(""),i(""),c("Lozinke se ne podudaraju!"),!1;const a=8,n=/[A-Z]/.test(o),s=/[a-z]/.test(o),E=/\d/.test(o),d=/[!@#$%^&*(),.?":{}|<>]/.test(o);return o.length<a?(l(""),i(""),c("Lozinka mora sadržavati barem 8 znakova!"),!1):!n||!s||!E||!d?(l(""),i(""),c("Lozinka mora sadržavati barem jedno veliko slovo, interpunkcijski znak i broj!"),!1):!0};t.useEffect(()=>{(async()=>{try{const s=await(await fetch(`/api/user/profile/${f}`)).json();b(s),k(s.ime),v(s.prezime),y(s.email),U(s.uloga)}catch(n){console.error("Error fetching initial data:",n)}})()},[f]);const R=()=>{x(!0)},F=async()=>{u(!0);try{w()?(c(""),await B(f),x(!1),u(!1),D()):u(!1)}catch(a){console.error("Error handling edit:",a),u(!1)}},$=()=>{console.log("Clicked cancel button"),x(!1),c(""),k((r==null?void 0:r.ime)||""),v((r==null?void 0:r.prezime)||""),y((r==null?void 0:r.email)||""),l(""),i(""),U("")},B=async a=>{try{console.log(`credentials:${p.korisnickoIme} : ${p.lozinka}`);const n=btoa(`${p.korisnickoIme}:${p.lozinka}`),s={...g!==""&&{ime:g},...j!==""&&{prezime:j},...C!==""&&{email:C},...h!==""&&{requestedUloga:h},...o!==""&&{lozinka:o}},E={method:"POST",headers:{Authorization:`Basic ${n}`,"Content-Type":"application/json"},body:JSON.stringify(s)};console.log("updated data:",s);const d=await fetch(`/api/user/update/${a}`,E);if(d.ok){console.log(`successfully updated korisnik data for: ${a}`);const O=await d.json();console.log("API Response:",O)}else console.error("failed to update korisnik data:",d.statusText)}catch(n){console.error("error updating korisnik data:",n)}},T=()=>(console.log("tema: ",m,m.isThemeDark),e.jsx(M.Suspense,{fallback:e.jsx("div",{children:"učitavanje..."}),children:e.jsx(H,{title:"uredi korisničke podatke",open:P,onOk:F,confirmLoading:S,onCancel:$,cancelText:"odustani",okText:"spremi promjene",children:e.jsxs("div",{className:"updateFormContainer",children:[e.jsx("p",{children:"ime:"}),e.jsx("input",{className:"problemUpdateForm",type:"text",placeholder:"ime",value:g,onChange:a=>k(a.target.value)}),e.jsx("p",{children:"prezime:"}),e.jsx("input",{className:"problemUpdateForm",type:"text",placeholder:"prezime",value:j,onChange:a=>v(a.target.value)}),e.jsx("p",{children:"email:"}),e.jsx("input",{className:"problemUpdateForm",type:"text",placeholder:"email",value:C,onChange:a=>y(a.target.value)}),e.jsx("p",{children:"nova lozinka:"}),e.jsx("input",{className:"problemUpdateForm",type:"password",placeholder:"nova lozinka",value:o,onChange:a=>l(a.target.value)}),e.jsx("p",{children:"potvrdi novu lozinku:"}),e.jsx("input",{className:"problemUpdateForm",type:"password",placeholder:"nova lozinka",value:z,onChange:a=>i(a.target.value)}),e.jsxs("div",{className:"FormRow",children:[e.jsx("p",{children:"uloga: "}),e.jsxs("div",{className:"RoleOptions",children:[e.jsxs("label",{className:"RadioLbl",children:[e.jsx("input",{type:"radio",value:"VODITELJ",checked:h==="VODITELJ",onChange:L}),"voditelj"]}),e.jsxs("label",{className:"RadioLbl",children:[e.jsx("input",{type:"radio",value:"NATJECATELJ",checked:h==="NATJECATELJ",onChange:L}),"natjecatelj"]})]})]}),e.jsx("div",{className:"error",children:N})]})})}));return e.jsxs(e.Fragment,{children:[e.jsx("button",{onClick:R,children:"uredi profil"}),m.isThemeDark===!1?e.jsx(A,{theme:{components:{Modal:{contentBg:"#fff"},Button:{colorPrimary:"#dd7230",colorPrimaryHover:"#dd723081",colorPrimaryActive:"#dd723081"}}},children:T()}):e.jsx(A,{theme:{components:{Modal:{contentBg:"#2A2D34",headerBg:"#2A2D34",footerBg:"#2A2D34",titleColor:"#ECDCC9",colorPrimary:"#2A2D34",colorPrimaryText:"#2A2D34",colorText:"#ECDCC9"},Button:{colorPrimary:"#dd7230e0",colorPrimaryHover:"#ECDCC9",colorPrimaryActive:"#2A2D34",colorLinkHover:"#000",textHoverBg:"#2A2D34",colorText:"#2A2D34",colorPrimaryText:"#2A2D34"}}},children:T()})]})};export{q as default};
