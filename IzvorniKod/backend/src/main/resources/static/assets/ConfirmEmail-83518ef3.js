import{af as l,r as t,S as i}from"./index-60b6e0ac.js";const h=()=>{const{id:r}=l(),[a,c]=t.useState(!1),[u,s]=t.useState(null),[o,n]=t.useState(!1);return t.useEffect(()=>{!a&&!o&&(async()=>{try{fetch(`/api/user/confirmEmail/${r}`).then(e=>{!o&&!a&&(e.status===200?(console.log("Success!"),s("uspješno si potvrdio svoju email adresu!")):e.status===404?s("korisnik ne postoji!"):e.status===400?s("uspješno si potvrdio svoju email adresu!"):s(`Unexpected status: ${e.status}`),n(!0))})}catch(e){console.log(e)}finally{c(!0)}})()},[r,a,o,s]),i.jsx("div",{children:i.jsx("p",{children:u})})};export{h as default};
