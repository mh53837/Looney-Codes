import{r as a,S as e}from"./index-23e25728.js";const l=n=>{},p=({loggedInUser:n,loggedInUserPass:i})=>{const[s,c]=a.useState([]),h=async t=>{console.log(`Button clicked for user: ${t}`);const o=btoa(`${n}:${i}`);try{const r={method:"POST",headers:{Authorization:`Basic ${o}`,"Content-Type":"application/json"}};fetch(`/api/user/confirmRequest/${t}`,r).then(d=>{d.status===200?(console.log("okej!"),c(j=>j.filter(m=>m.korisnickoIme!==t)),l(s.length)):console.log("nije okej!")})}catch(r){console.error("Error:",r)}};return a.useEffect(()=>{const t=btoa(`${n}:${i}`);try{const o={method:"GET",headers:{Authorization:`Basic ${t}`,"Content-Type":"application/json"}};fetch("/api/user/listRequested",o).then(r=>r.json()).then(r=>{c(r),l(r.length)}).catch(r=>console.error("Error fetching users:",r))}catch(o){console.error("Error:",o)}},[n,i]),e.jsx("div",{className:"tableWrapper",children:e.jsxs("div",{className:"info-table",children:[e.jsx("p",{children:"korisnici kojima treba odobriti zatraženu ulogu:"}),s.length===0?e.jsx("p",{children:"trenutno nema korisnika kojima treba odobriti zatraženu ulogu"}):e.jsxs("table",{children:[e.jsx("thead",{children:e.jsxs("tr",{children:[e.jsx("th",{children:"korisničko ime"}),e.jsx("th",{children:"ime"}),e.jsx("th",{children:"prezime"}),e.jsx("th",{children:"email"}),e.jsx("th",{})]})}),e.jsx("tbody",{children:s.map((t,o)=>e.jsxs("tr",{children:[e.jsx("td",{children:t.korisnickoIme}),e.jsx("td",{children:t.ime}),e.jsx("td",{children:t.prezime}),e.jsx("td",{children:t.email}),e.jsx("td",{className:"tdBtnPotvrdi",children:e.jsx("button",{className:"btnPotvrdi",onClick:()=>h(t.korisnickoIme),children:"potvrdi"})})]},o))})]})]})})};export{p as default};
