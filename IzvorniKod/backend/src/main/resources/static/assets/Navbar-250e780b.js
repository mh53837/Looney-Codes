import{r as i,U as h,ac as x,ad as j,S as e,ab as t}from"./index-1bf42253.js";const b=m=>{const{user:s}=i.useContext(h),r=m.onLogout,[n,c]=i.useState(null),u=x(),{theme:o,setTheme:a}=i.useContext(j);return o||a({isThemeDark:!1}),i.useEffect(()=>{(async()=>{try{if(s.korisnickoIme!==""){const k=await(await fetch(`/api/user/image/${s.korisnickoIme}`)).blob(),d=URL.createObjectURL(k);c(d)}}catch(l){console.error("Error fetching profile picture:",l)}})()},[s.korisnickoIme]),i.useEffect(()=>{s.korisnickoIme||c("/slike/bytepit-usericon.png")},[s.korisnickoIme]),i.useEffect(()=>{document.documentElement.setAttribute("data-theme",o.isThemeDark?"dark":"light")},[o.isThemeDark]),e.jsxs("div",{className:"navbar-container",children:[e.jsx("div",{className:"logo-container",children:e.jsx(t,{to:"/",children:e.jsx("img",{src:"/slike/logo-bytepit.png",alt:"BytePit logo"})})}),e.jsxs("div",{className:"navbar-options-container",children:[e.jsx(t,{to:"/",children:e.jsx("button",{children:"kalendar"})}),e.jsx(t,{to:"/user/all",children:e.jsx("button",{children:"korisnici"})}),e.jsx(t,{to:"/problems/all",children:e.jsx("button",{children:"zadaci"})}),e.jsx("button",{onClick:()=>{a({isThemeDark:!o.isThemeDark})},children:o.isThemeDark?"☀️":"🌙"})]}),s.korisnickoIme?e.jsx("div",{className:"loginDiv",children:u.pathname===`/user/profile/${s.korisnickoIme}`?e.jsx("button",{onClick:r,children:"odjavi se"}):e.jsxs(t,{to:`/user/profile/${s.korisnickoIme}`,children:[n?e.jsx("img",{className:"userIconImg",src:n,alt:"BytePit unregistered user icon"}):e.jsx("img",{className:"userIconImg",src:"/slike/bytepit-usericon.png",alt:"Default icon"}),e.jsxs("p",{className:"korisnikIme",children:[" ",s.korisnickoIme]}),e.jsx("button",{onClick:r,children:"odjavi se"})]})}):e.jsx("div",{className:"loginDiv",children:e.jsxs(t,{to:"/login",children:[e.jsx("img",{className:"userIconImg",src:"/slike/bytepit-usericon.png",alt:"BytePit unregistered user icon"}),e.jsx("button",{children:"prijavi se"})]})})]})};export{b as default};
