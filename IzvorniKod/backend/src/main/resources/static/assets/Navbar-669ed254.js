import{r as i,U as h,ad as x,ae as j,S as e,ac as t}from"./index-4f9613f9.js";const b=l=>{const{user:s}=i.useContext(h),n=l.onLogout,[r,c]=i.useState(null),m=x(),{theme:o,setTheme:u}=i.useContext(j);return i.useEffect(()=>{(async()=>{try{if(s.korisnickoIme!==""){const d=await(await fetch(`/api/user/image/${s.korisnickoIme}`)).blob(),k=URL.createObjectURL(d);c(k)}}catch(a){console.error("Error fetching profile picture:",a)}})()},[s.korisnickoIme]),i.useEffect(()=>{s.korisnickoIme||c("/slike/bytepit-usericon.png")},[s.korisnickoIme]),i.useEffect(()=>{document.documentElement.setAttribute("data-theme",o.isThemeDark?"dark":"light")},[o.isThemeDark]),e.jsxs("div",{className:"navbar-container",children:[e.jsx("div",{className:"logo-container",children:e.jsx(t,{to:"/",children:e.jsx("img",{src:"/slike/logo-bytepit.png",alt:"BytePit logo"})})}),e.jsxs("div",{className:"navbar-options-container",children:[e.jsx(t,{to:"/",children:e.jsx("button",{children:"kalendar"})}),e.jsx(t,{to:"/user/all",children:e.jsx("button",{children:"korisnici"})}),e.jsx(t,{to:"/problems/all",children:e.jsx("button",{children:"zadaci"})}),e.jsx("button",{onClick:()=>{u({isThemeDark:!o.isThemeDark})},children:o.isThemeDark?"☀️":"🌙"})]}),s.korisnickoIme?e.jsx("div",{className:"loginDiv",children:m.pathname===`/user/profile/${s.korisnickoIme}`?e.jsx("button",{onClick:n,children:"odjavi se!"}):e.jsxs(t,{to:`/user/profile/${s.korisnickoIme}`,children:[r?e.jsx("img",{className:"userIconImg",src:r,alt:"BytePit unregistered user icon"}):e.jsx("img",{className:"userIconImg",src:"/slike/bytepit-usericon.png",alt:"Default icon"}),e.jsxs("p",{className:"korisnikIme",children:[" ",s.korisnickoIme]}),e.jsx("button",{onClick:n,children:"odjavi se!"})]})}):e.jsx("div",{className:"loginDiv",children:e.jsxs(t,{to:"/login",children:[e.jsx("img",{className:"userIconImg",src:"/slike/bytepit-usericon.png",alt:"BytePit unregistered user icon"}),e.jsx("button",{children:"prijavi se!"})]})})]})};export{b as default};
