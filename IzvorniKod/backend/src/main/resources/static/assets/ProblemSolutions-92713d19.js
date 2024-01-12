import{af as C,r as s,ae as b,U as S,S as e,ai as $,M as j,aj as k}from"./index-723c806d.js";const y=({zadatakId:i,natjecatelj:d})=>{const{nadmetanjeId:p}=C(),[m,x]=s.useState([]),[B,g]=s.useState(!1),{theme:w}=s.useContext(b),{user:o}=s.useContext(S);s.useEffect(()=>{(async()=>{try{const c={method:"GET",headers:{Authorization:`Basic ${btoa(`${o.korisnickoIme}:${o.lozinka}`)}`}},a=await fetch(`/api/solutions/get/competition/${p}?zadatak=${i}&natjecatelj=${d}`,c);if(!a.ok)throw new Error("Failed to fetch data");const f=await a.json();x(f);const l=await(await fetch(`/api/solutions/solved/${i}`,c)).json();g(l)}catch(r){console.error("Error fetching data:",r)}})()},[p,i,d]);const v=async t=>{try{const c={method:"GET",headers:{Authorization:`Basic ${btoa(`${o.korisnickoIme}:${o.lozinka}`)}`}},a=await fetch(`/api/solutions/code?rbr=${t}&zadatak=${i}&natjecatelj=${d}`,c);if(!a.ok)throw window.alert("Morate riješiti zadatak da biste vidjeli rješenja drugih korisnika!"),new Error("Failed to fetch solution code");let n=(await a.text()).replace(/\\n/g,`
`);n=n.substring(1,n.length-1);const l=new Blob([n],{type:"text/plain"}),h=document.createElement("a");h.href=window.URL.createObjectURL(l),h.download=`solution_${t}.cpp`,h.click()}catch(r){console.error("Error downloading solution code:",r)}},u=B||o.korisnickoIme===d?[{title:"Rješenje",dataIndex:"rBr",key:"rBr"},{title:"Rješenost (%)",dataIndex:"postotakTocnihPrimjera",key:"postotakTocnihPrimjera"},{title:"Preuzimanje",key:"actions",render:t=>e.jsx($,{className:"download",onClick:()=>v(t.rBr),children:e.jsx("span",{children:"Dohvati"})})}]:[{title:"Rješenje",dataIndex:"rBr",key:"rBr"},{title:"Rješenost (%)",dataIndex:"postotakTocnihPrimjera",key:"postotakTocnihPrimjera"}];return e.jsx("div",{className:"tableWrapper",children:e.jsx("div",{className:"info-table",children:w.isThemeDark==!1?e.jsx(j,{theme:{components:{Table:{headerBg:"#f4c95de7",rowHoverBg:"#f4c95d52",borderColor:"#00000085",headerFilterHoverBg:"#f4c95de7",headerSortActiveBg:"#f4c95de7",headerSortHoverBg:"#f4c95da9",headerBorderRadius:0,colorPrimary:"#dd7230",headerSplitColor:"transparent",colorBgContainer:"#fff",colorText:"#000"}}},children:e.jsx(k,{pagination:!1,dataSource:m,columns:u})}):e.jsx(j,{theme:{components:{Table:{headerBg:"#dd7230",rowHoverBg:"#dcdcdc34",borderColor:"#00000085",headerFilterHoverBg:"#dd7230e0",headerSortActiveBg:"#dd7230e0",headerSortHoverBg:"#dd7230",headerBorderRadius:0,colorPrimary:"#f4c95d",headerSplitColor:"transparent",colorBgContainer:"#2A2D34",colorText:" #ECDCC9"}}},children:e.jsx(k,{pagination:!1,dataSource:m,columns:u})})})})};export{y as default};
