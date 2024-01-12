import{r as n,U as w,ae as D,S as t,M as B,R as M,ak as N,T as $}from"./index-723c806d.js";const H=({zadatakId:d,onUpdateSuccess:z})=>{const[e,T]=n.useState(null),{user:k}=n.useContext(w),{theme:A}=n.useContext(D),[P,l]=n.useState(!1),[S,i]=n.useState(!1),[c,f]=n.useState(""),[u,j]=n.useState(""),[x,r]=n.useState(""),[g,C]=n.useState(0),[p,h]=n.useState(!0),m=a=>{console.log(a.target.value),a.target.value==="true"?h(!0):a.target.value==="false"&&h(!1)},b=a=>{const o=parseInt(a,10);o==10?r("RECRUIT"):o==20?r("VETERAN"):o==50&&r("REALISM"),C(o)};n.useEffect(()=>{(async()=>{try{const s=await(await fetch(`/api/problems/get/${d}`)).json();T(s),f(s.nazivZadatka),j(s.tekstZadatka),C(s.brojBodova),r(s.tezinaZadatka),h(s.privatniZadatak)}catch(o){console.error("Error fetching initial data:",o)}})()},[d]);const y=()=>t.jsx(M.Suspense,{fallback:t.jsx("div",{children:"učitavanje..."}),children:t.jsx(N,{title:"uredi zadatak",open:P,onOk:Z,confirmLoading:S,onCancel:O,cancelText:"odustani",okText:"spremi promjene",okButtonProps:{style:{color:"#2A2D34"}},cancelButtonProps:{style:{color:"#2A2D34"}},children:t.jsxs("div",{className:"updateFormContainer",children:[t.jsx("p",{children:"naziv:"}),t.jsx("input",{className:"problemUpdateForm",type:"text",placeholder:"naziv zadatka",value:c,onChange:a=>f(a.target.value)}),t.jsx("p",{children:"tekst:"}),t.jsx("input",{className:"problemUpdateForm",type:"text",placeholder:"tekst zadatka",value:u,onChange:a=>j(a.target.value)}),t.jsx("p",{children:"težina (broj bodova): "}),t.jsx($,{defaultValue:(e==null?void 0:e.brojBodova.toString())||"10",style:{width:160},onChange:b,options:[{value:"10",label:"★ (10 bodova)"},{value:"20",label:"★★ (20 bodova)"},{value:"50",label:"★★★ (50 bodova)"}]}),t.jsx("p",{children:"status zadatka:"}),t.jsxs("label",{children:[t.jsx("input",{type:"radio",value:"true",checked:p===!0,onChange:m}),"privatni"]}),t.jsxs("label",{children:[t.jsx("input",{type:"radio",value:"false",checked:p===!1,onChange:m}),"javni"]})]})})}),E=()=>{l(!0)},Z=async()=>{i(!0);try{await R(d),l(!1),i(!1),z()}catch(a){console.error("Error handling edit:",a),i(!1)}},O=()=>{console.log("Clicked cancel button"),l(!1)},R=async a=>{try{const o=btoa(`${k.korisnickoIme}:${k.lozinka}`),s={zadatakId:a,nazivZadatka:c||(e==null?void 0:e.nazivZadatka),tekstZadatka:u||(e==null?void 0:e.tekstZadatka),voditelj:e==null?void 0:e.voditelj,brojBodova:g||(e==null?void 0:e.brojBodova),privatniZadatak:p,tezinaZadatka:x||(e==null?void 0:e.tezinaZadatka),vremenskoOgranicenje:e==null?void 0:e.vremenskoOgranicenje};console.log("updated data:",s);const v=await fetch(`/api/problems/update/${a}`,{method:"POST",headers:{Authorization:`Basic ${o}`,"Content-Type":"application/json"},body:JSON.stringify(s)});if(v.ok){console.log(`successfully updated zadatak data for: ${a}`);const U=await v.json();console.log("API Response:",U)}else console.error("failed to update zadatak data:",v.statusText)}catch(o){console.error("error updating zadatak data:",o)}};return t.jsxs(t.Fragment,{children:[t.jsx("button",{onClick:E,children:"uredi"}),A.isThemeDark==!1?t.jsx(B,{theme:{components:{Modal:{contentBg:"#fff"},Button:{colorPrimary:"#dd7230",colorPrimaryHover:"#dd723081",colorPrimaryActive:"#dd723081"}}},children:y()}):t.jsx(B,{theme:{components:{Modal:{contentBg:"#2A2D34",headerBg:"#2A2D34",footerBg:"#2A2D34",titleColor:"#ECDCC9",colorPrimary:"#2A2D34",colorPrimaryText:"#2A2D34",colorText:"#ECDCC9"},Button:{colorPrimary:"#dd7230e0",colorPrimaryHover:"#ECDCC9",colorPrimaryActive:"#2A2D34",colorLinkHover:"#000",textHoverBg:"#2A2D34",colorText:"#2A2D34",colorPrimaryText:"#2A2D34"}}},children:y()})]})};export{H as default};
