import{R as l,Q as I,r,U as T,ad as B,S as e,ab as y,aj as P,M as p,ai as D}from"./index-1a8425e1.js";import{A as w}from"./AddProblemsToCompetition-697d7099.js";/* empty css                              */const z=l.lazy(()=>I(()=>import("./CompetitionUpdateForm-77f4157e.js"),["assets/CompetitionUpdateForm-77f4157e.js","assets/index-1a8425e1.js","assets/index-8c872585.css","assets/hr-467cba6b.js","assets/CompetitionUpdateForm-191f03f1.css"])),_=({competitionsData:o,onUpdate:g,userData:c})=>{const[i,f]=r.useState(null),[j,m]=r.useState(!1),[h,u]=r.useState(null),{user:s}=r.useContext(T),d=t=>{const a={day:"2-digit",month:"2-digit",year:"numeric",hour:"2-digit",minute:"2-digit"};return new Date(t).toLocaleString("hr-HR",a)},v=()=>{g()},{theme:x}=r.useContext(B),C=t=>{u(t),m(!0)},N=()=>{m(!1),u(null)},b=({date:t,view:a})=>a==="month"&&o.filter(S=>new Date(S.pocetakNatjecanja).toDateString()===(t==null?void 0:t.toDateString())).length>0?e.jsx("div",{children:e.jsx("p",{style:{fontSize:15},className:"naziv-natjecanje",children:"★"})}):null,k=t=>{if(t)return e.jsx(D,{dataSource:t,columns:[{title:"korisničko ime",dataIndex:"korisnickoImeVoditelja",key:"korisnickoIme",className:"th-td"},{title:"naziv",dataIndex:"nazivNatjecanja",key:"nazivNatjecanja",className:"th-td",sorter:(a,n)=>a.nazivNatjecanja.localeCompare(n.nazivNatjecanja)},{title:"početak",dataIndex:"pocetakNatjecanja",key:"pocetakNatjecanja",className:"th-td",render:a=>d(a),sorter:(a,n)=>new Date(a.pocetakNatjecanja).getTime()-new Date(n.pocetakNatjecanja).getTime()},{title:"kraj",dataIndex:"krajNatjecanja",key:"krajNatjecanja",className:"th-td",render:a=>d(a),sorter:(a,n)=>new Date(a.krajNatjecanja).getTime()-new Date(n.krajNatjecanja).getTime()},...s.uloga==="VODITELJ"&&s.korisnickoIme===c.korisnickoIme||s.uloga==="ADMIN"?[{title:"",key:"edit",className:"th-td",render:a=>e.jsx("span",{children:e.jsx(l.Suspense,{fallback:e.jsx("div",{children:"učitavanje..."}),children:e.jsx(z,{natjecanjeId:a.natjecanjeId??0,onUpdateSuccess:v,theme:x.isThemeDark})})})},{title:"",key:"problems",className:"th-td",render:a=>e.jsx("span",{children:e.jsx(l.Suspense,{fallback:e.jsx("div",{children:"učitavanje..."}),children:e.jsx("button",{onClick:()=>C(a.natjecanjeId),children:"zadaci"})})})}]:[]],pagination:!1,rowKey:"natjecanjeId",showSorterTooltip:!1,style:{tableLayout:"fixed"},scroll:{x:!0}})};return e.jsxs("div",{className:"calendar-container",children:[s.korisnickoIme===c.korisnickoIme&&e.jsx(y,{to:"/natjecanja/new",children:e.jsx("button",{className:"addBtn",children:"novo natjecanje"})}),e.jsx(P,{value:i,onChange:t=>f(t),tileContent:b,locale:"hr-HR"}),e.jsx("div",{className:"info-table",children:i!==null?e.jsxs("div",{children:[e.jsxs("p",{children:["označeni datum: ",d(i.toUTCString())]}),(()=>{const t=o==null?void 0:o.filter(a=>new Date(a.pocetakNatjecanja).toDateString()===i.toDateString());return(t==null?void 0:t.length)===0?e.jsx("p",{children:"na označeni datum nema natjecanja"}):e.jsx("div",{className:"tableWrapper",children:e.jsx("div",{className:"info-table",children:x.isThemeDark==!1?e.jsx(p,{theme:{components:{Table:{cellPaddingBlock:16,cellPaddingInline:6,headerBg:"#f4c95de7",rowHoverBg:"#f4c95d52",borderColor:"#00000085",headerFilterHoverBg:"#f4c95de7",headerSortActiveBg:"#f4c95de7",headerSortHoverBg:"#f4c95da9",headerBorderRadius:0,colorPrimary:"#dd7230",headerSplitColor:"transparent",colorBgContainer:"#fff",colorText:"#000"}}},children:k(t)}):e.jsx(p,{theme:{components:{Table:{cellPaddingBlock:16,cellPaddingInline:6,headerBg:"#dd7230",rowHoverBg:"#dcdcdc34",borderColor:"#00000085",headerFilterHoverBg:"#dd7230e0",headerSortActiveBg:"#dd7230e0",headerSortHoverBg:"#dd7230",headerBorderRadius:0,colorPrimary:"#f4c95d",headerSplitColor:"transparent",colorBgContainer:"#2A2D34",colorText:" #ECDCC9"}}},children:k(t)})})})})()]}):e.jsx("p",{children:"odaberite datum"})}),j&&h&&e.jsx(l.Suspense,{fallback:e.jsx("div",{children:"učitavanje..."}),children:e.jsx(w,{natjecanjeId:h,visible:j,onClose:N})})]})};export{_ as default};