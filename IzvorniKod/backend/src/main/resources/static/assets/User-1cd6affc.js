import{R as c,Q as l,r as x,U as j,S as e,ac as h}from"./index-4f9613f9.js";const m=c.lazy(()=>l(()=>import("./UserProfileUpdateForm-fd5aca57.js"),["assets/UserProfileUpdateForm-fd5aca57.js","assets/index-4f9613f9.js","assets/index-bb3e8cee.css"])),p=t=>{const{korisnickoIme:s,ime:i,prezime:o,email:d,uloga:r}=t.user,{user:n}=x.useContext(j),a=()=>{};return e.jsxs("tr",{className:"user-info-header",children:[e.jsx("td",{children:e.jsx(h,{to:`/user/profile/${s}`,children:s})}),e.jsx("td",{children:i}),e.jsx("td",{children:o}),e.jsx("td",{children:d}),r==="ADMIN"&&e.jsx("td",{children:"admin"}),r==="VODITELJ"&&e.jsx("td",{children:"voditelj"}),r==="NATJECATELJ"&&e.jsx("td",{children:"natjecatelj"}),n.uloga==="ADMIN"&&e.jsx("td",{children:e.jsx(m,{korisnickoIme:s??"",onUpdateSuccess:a})})]})};export{p as default};