import{r as a,S as s}from"./index-121ac392.js";const f=({userData:r})=>{const[t,n]=a.useState([]),[c,i]=a.useState(null);return a.useEffect(()=>{(r==null?void 0:r.uloga)==="NATJECATELJ"&&fetch(`/api/trophies/user/${r.korisnickoIme}`).then(e=>e.json()).then(e=>{console.log("Trophy data:",e),n(e)}).catch(e=>{console.error("error fetching trophy data:",e)})},[r]),a.useEffect(()=>{(async()=>{try{if(t.length>0){const o=t.map(async p=>{const l=await(await fetch(`/api/trophies/image/${p.peharId}`)).blob();return URL.createObjectURL(l)}),h=await Promise.all(o);i(h)}}catch(o){console.error("Error fetching trophy pictures:",o)}})()},[t]),s.jsxs("div",{children:[t.length===0&&s.jsx("p",{children:"nema osvojenih pehara"}),t.map((e,o)=>s.jsxs("div",{className:"userTrophy",children:[s.jsx("h4",{children:e.natjecanje.nazivNatjecanja}),s.jsx("p",{children:"|"}),s.jsxs("p",{children:["Mjesto: ",e.mjesto]}),s.jsx("p",{children:"|"}),c&&s.jsx("img",{className:"trophyImg",src:c[o],alt:"slika pehara"})]},e.peharId))]})};export{f as default};
