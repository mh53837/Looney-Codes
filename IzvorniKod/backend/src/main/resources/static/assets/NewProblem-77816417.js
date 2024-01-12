import{r as s,d as j,_ as le,f as A,c as ce,X as Ge,R as U,j as We,Y as Ke,g as Ne,Z as z,$ as D,a0 as Ue,l as ze,m as Be,a1 as me,p as Ve,a2 as Xe,E as be,F as Ee,a3 as Ye,a4 as Qe,a5 as qe,a6 as Ze,a7 as Je,a8 as et,a9 as tt,aa as nt,M as ot,ab as Se}from"./index-60b6e0ac.js";var Oe=s.forwardRef(function(e,t){var a=e.prefixCls,n=e.style,o=e.className,r=e.duration,u=r===void 0?4.5:r,S=e.eventKey,c=e.content,m=e.closable,y=e.closeIcon,v=y===void 0?"x":y,p=e.props,g=e.onClick,h=e.onNoticeClose,N=e.times,b=e.hovering,E=s.useState(!1),$=j(E,2),O=$[0],k=$[1],x=b||O,P=function(){h(S)},C=function(i){(i.key==="Enter"||i.code==="Enter"||i.keyCode===Ge.ENTER)&&P()};s.useEffect(function(){if(!x&&u>0){var l=setTimeout(function(){P()},u*1e3);return function(){clearTimeout(l)}}},[u,x,N]);var f="".concat(a,"-notice");return s.createElement("div",le({},p,{ref:t,className:A(f,o,ce({},"".concat(f,"-closable"),m)),style:n,onMouseEnter:function(i){var d;k(!0),p==null||(d=p.onMouseEnter)===null||d===void 0||d.call(p,i)},onMouseLeave:function(i){var d;k(!1),p==null||(d=p.onMouseLeave)===null||d===void 0||d.call(p,i)},onClick:g}),s.createElement("div",{className:"".concat(f,"-content")},c),m&&s.createElement("a",{tabIndex:0,className:"".concat(f,"-close"),onKeyDown:C,onClick:function(i){i.preventDefault(),i.stopPropagation(),P()}},v))}),Pe=U.createContext({}),at=function(t){var a=t.children,n=t.classNames;return U.createElement(Pe.Provider,{value:{classNames:n}},a)},ge=8,pe=3,ye=16,st=function(t){var a={offset:ge,threshold:pe,gap:ye};if(t&&We(t)==="object"){var n,o,r;a.offset=(n=t.offset)!==null&&n!==void 0?n:ge,a.threshold=(o=t.threshold)!==null&&o!==void 0?o:pe,a.gap=(r=t.gap)!==null&&r!==void 0?r:ye}return[!!t,a]},rt=["className","style","classNames","styles"],it=function(t){var a,n=t.configList,o=t.placement,r=t.prefixCls,u=t.className,S=t.style,c=t.motion,m=t.onAllNoticeRemoved,y=t.onNoticeClose,v=t.stack,p=s.useContext(Pe),g=p.classNames,h=s.useRef({}),N=s.useState(null),b=j(N,2),E=b[0],$=b[1],O=s.useState([]),k=j(O,2),x=k[0],P=k[1],C=n.map(function(w){return{config:w,key:String(w.key)}}),f=st(v),l=j(f,2),i=l[0],d=l[1],I=d.offset,F=d.threshold,G=d.gap,H=i&&(x.length>0||C.length<=F),Re=typeof c=="function"?c(o):c;return s.useEffect(function(){i&&x.length>1&&P(function(w){return w.filter(function(_){return C.some(function(V){var ee=V.key;return _===ee})})})},[x,C,i]),s.useEffect(function(){var w;if(i&&h.current[(w=C[C.length-1])===null||w===void 0?void 0:w.key]){var _;$(h.current[(_=C[C.length-1])===null||_===void 0?void 0:_.key])}},[C,i]),U.createElement(Ke,le({key:o,className:A(r,"".concat(r,"-").concat(o),g==null?void 0:g.list,u,(a={},ce(a,"".concat(r,"-stack"),!!i),ce(a,"".concat(r,"-stack-expanded"),H),a)),style:S,keys:C,motionAppear:!0},Re,{onAllRemoved:function(){m(o)}}),function(w,_){var V=w.config,ee=w.className,Me=w.style,Ae=w.index,fe=V,te=fe.key,Fe=fe.times,M=String(te),W=V,je=W.className,_e=W.style,X=W.classNames,Y=W.styles,Te=Ne(W,rt),ne=C.findIndex(function(q){return q.key===M}),Q={};if(i){var K=C.length-1-(ne>-1?ne:Ae-1),de=o==="top"||o==="bottom"?"-50%":"0";if(K>0){var oe,ae,se;Q.height=H?(oe=h.current[M])===null||oe===void 0?void 0:oe.offsetHeight:E==null?void 0:E.offsetHeight;for(var ve=0,re=0;re<K;re++){var ie;ve+=((ie=h.current[C[C.length-1-re].key])===null||ie===void 0?void 0:ie.offsetHeight)+G}var Le=(H?ve:K*I)*(o.startsWith("top")?1:-1),He=!H&&E!==null&&E!==void 0&&E.offsetWidth&&(ae=h.current[M])!==null&&ae!==void 0&&ae.offsetWidth?((E==null?void 0:E.offsetWidth)-I*2*(K<3?K:3))/((se=h.current[M])===null||se===void 0?void 0:se.offsetWidth):1;Q.transform="translate3d(".concat(de,", ").concat(Le,"px, 0) scaleX(").concat(He,")")}else Q.transform="translate3d(".concat(de,", 0, 0)")}return U.createElement("div",{ref:_,className:A("".concat(r,"-notice-wrapper"),ee,X==null?void 0:X.wrapper),style:z(z(z({},Me),Q),Y==null?void 0:Y.wrapper),onMouseEnter:function(){return P(function(T){return T.includes(M)?T:[].concat(D(T),[M])})},onMouseLeave:function(){return P(function(T){return T.filter(function(De){return De!==M})})}},U.createElement(Oe,le({},Te,{ref:function(T){ne>-1?h.current[M]=T:delete h.current[M]},prefixCls:r,classNames:X,styles:Y,className:A(je,g==null?void 0:g.notice),style:_e,times:Fe,key:te,eventKey:te,onNoticeClose:y,hovering:i&&x.length>0})))})},lt=s.forwardRef(function(e,t){var a=e.prefixCls,n=a===void 0?"rc-notification":a,o=e.container,r=e.motion,u=e.maxCount,S=e.className,c=e.style,m=e.onAllRemoved,y=e.stack,v=e.renderNotifications,p=s.useState([]),g=j(p,2),h=g[0],N=g[1],b=function(l){var i,d=h.find(function(I){return I.key===l});d==null||(i=d.onClose)===null||i===void 0||i.call(d),N(function(I){return I.filter(function(F){return F.key!==l})})};s.useImperativeHandle(t,function(){return{open:function(l){N(function(i){var d=D(i),I=d.findIndex(function(H){return H.key===l.key}),F=z({},l);if(I>=0){var G;F.times=(((G=i[I])===null||G===void 0?void 0:G.times)||0)+1,d[I]=F}else F.times=0,d.push(F);return u>0&&d.length>u&&(d=d.slice(-u)),d})},close:function(l){b(l)},destroy:function(){N([])}}});var E=s.useState({}),$=j(E,2),O=$[0],k=$[1];s.useEffect(function(){var f={};h.forEach(function(l){var i=l.placement,d=i===void 0?"topRight":i;d&&(f[d]=f[d]||[],f[d].push(l))}),Object.keys(O).forEach(function(l){f[l]=f[l]||[]}),k(f)},[h]);var x=function(l){k(function(i){var d=z({},i),I=d[l]||[];return I.length||delete d[l],d})},P=s.useRef(!1);if(s.useEffect(function(){Object.keys(O).length>0?P.current=!0:P.current&&(m==null||m(),P.current=!1)},[O]),!o)return null;var C=Object.keys(O);return Ue.createPortal(s.createElement(s.Fragment,null,C.map(function(f){var l=O[f],i=s.createElement(it,{key:f,configList:l,placement:f,prefixCls:n,className:S==null?void 0:S(f),style:c==null?void 0:c(f),motion:r,onNoticeClose:b,onAllNoticeRemoved:x,stack:y});return v?v(i,{prefixCls:n,key:f}):i})),o)}),ct=["getContainer","motion","prefixCls","maxCount","className","style","onAllRemoved","stack","renderNotifications"],ut=function(){return document.body},Ce=0;function ft(){for(var e={},t=arguments.length,a=new Array(t),n=0;n<t;n++)a[n]=arguments[n];return a.forEach(function(o){o&&Object.keys(o).forEach(function(r){var u=o[r];u!==void 0&&(e[r]=u)})}),e}function dt(){var e=arguments.length>0&&arguments[0]!==void 0?arguments[0]:{},t=e.getContainer,a=t===void 0?ut:t,n=e.motion,o=e.prefixCls,r=e.maxCount,u=e.className,S=e.style,c=e.onAllRemoved,m=e.stack,y=e.renderNotifications,v=Ne(e,ct),p=s.useState(),g=j(p,2),h=g[0],N=g[1],b=s.useRef(),E=s.createElement(lt,{container:h,ref:b,prefixCls:o,motion:n,maxCount:r,className:u,style:S,onAllRemoved:c,stack:m,renderNotifications:y}),$=s.useState([]),O=j($,2),k=O[0],x=O[1],P=s.useMemo(function(){return{open:function(f){var l=ft(v,f);(l.key===null||l.key===void 0)&&(l.key="rc-notification-".concat(Ce),Ce+=1),x(function(i){return[].concat(D(i),[{type:"open",config:l}])})},close:function(f){x(function(l){return[].concat(D(l),[{type:"close",key:f}])})},destroy:function(){x(function(f){return[].concat(D(f),[{type:"destroy"}])})}}},[]);return s.useEffect(function(){N(a())}),s.useEffect(function(){b.current&&k.length&&(k.forEach(function(C){switch(C.type){case"open":b.current.open(C.config);break;case"close":b.current.close(C.key);break;case"destroy":b.current.destroy();break}}),x(function(C){return C.filter(function(f){return!k.includes(f)})}))},[k]),[P,E]}const vt=e=>{const{componentCls:t,iconCls:a,boxShadow:n,colorText:o,colorSuccess:r,colorError:u,colorWarning:S,colorInfo:c,fontSizeLG:m,motionEaseInOutCirc:y,motionDurationSlow:v,marginXS:p,paddingXS:g,borderRadiusLG:h,zIndexPopup:N,contentPadding:b,contentBg:E}=e,$=`${t}-notice`,O=new me("MessageMoveIn",{"0%":{padding:0,transform:"translateY(-100%)",opacity:0},"100%":{padding:g,transform:"translateY(0)",opacity:1}}),k=new me("MessageMoveOut",{"0%":{maxHeight:e.height,padding:g,opacity:1},"100%":{maxHeight:0,padding:0,opacity:0}}),x={padding:g,textAlign:"center",[`${t}-custom-content > ${a}`]:{verticalAlign:"text-bottom",marginInlineEnd:p,fontSize:m},[`${$}-content`]:{display:"inline-block",padding:b,background:E,borderRadius:h,boxShadow:n,pointerEvents:"all"},[`${t}-success > ${a}`]:{color:r},[`${t}-error > ${a}`]:{color:u},[`${t}-warning > ${a}`]:{color:S},[`${t}-info > ${a},
      ${t}-loading > ${a}`]:{color:c}};return[{[t]:Object.assign(Object.assign({},Ve(e)),{color:o,position:"fixed",top:p,width:"100%",pointerEvents:"none",zIndex:N,[`${t}-move-up`]:{animationFillMode:"forwards"},[`
        ${t}-move-up-appear,
        ${t}-move-up-enter
      `]:{animationName:O,animationDuration:v,animationPlayState:"paused",animationTimingFunction:y},[`
        ${t}-move-up-appear${t}-move-up-appear-active,
        ${t}-move-up-enter${t}-move-up-enter-active
      `]:{animationPlayState:"running"},[`${t}-move-up-leave`]:{animationName:k,animationDuration:v,animationPlayState:"paused",animationTimingFunction:y},[`${t}-move-up-leave${t}-move-up-leave-active`]:{animationPlayState:"running"},"&-rtl":{direction:"rtl",span:{direction:"rtl"}}})},{[t]:{[`${$}-wrapper`]:Object.assign({},x)}},{[`${t}-notice-pure-panel`]:Object.assign(Object.assign({},x),{padding:0,textAlign:"start"})}]},mt=e=>({zIndexPopup:e.zIndexPopupBase+Xe+10,contentBg:e.colorBgElevated,contentPadding:`${(e.controlHeightLG-e.fontSize*e.lineHeight)/2}px ${e.paddingSM}px`}),ke=ze("Message",e=>{const t=Be(e,{height:150});return[vt(t)]},mt);var gt=globalThis&&globalThis.__rest||function(e,t){var a={};for(var n in e)Object.prototype.hasOwnProperty.call(e,n)&&t.indexOf(n)<0&&(a[n]=e[n]);if(e!=null&&typeof Object.getOwnPropertySymbols=="function")for(var o=0,n=Object.getOwnPropertySymbols(e);o<n.length;o++)t.indexOf(n[o])<0&&Object.prototype.propertyIsEnumerable.call(e,n[o])&&(a[n[o]]=e[n[o]]);return a};const pt={info:s.createElement(Ye,null),success:s.createElement(Qe,null),error:s.createElement(qe,null),warning:s.createElement(Ze,null),loading:s.createElement(Je,null)},$e=e=>{let{prefixCls:t,type:a,icon:n,children:o}=e;return s.createElement("div",{className:A(`${t}-custom-content`,`${t}-${a}`)},n||pt[a],s.createElement("span",null,o))},yt=e=>{const{prefixCls:t,className:a,type:n,icon:o,content:r}=e,u=gt(e,["prefixCls","className","type","icon","content"]),{getPrefixCls:S}=s.useContext(be),c=t||S("message"),m=Ee(c),[y,v,p]=ke(c,m);return y(s.createElement(Oe,Object.assign({},u,{prefixCls:c,className:A(a,v,`${c}-notice-pure-panel`,p,m),eventKey:"pure",duration:null,content:s.createElement($e,{prefixCls:c,type:n,icon:o},r)})))},Ct=yt;function ht(e,t){return{motionName:t??`${e}-move-up`}}function ue(e){let t;const a=new Promise(o=>{t=e(()=>{o(!0)})}),n=()=>{t==null||t()};return n.then=(o,r)=>a.then(o,r),n.promise=a,n}var xt=globalThis&&globalThis.__rest||function(e,t){var a={};for(var n in e)Object.prototype.hasOwnProperty.call(e,n)&&t.indexOf(n)<0&&(a[n]=e[n]);if(e!=null&&typeof Object.getOwnPropertySymbols=="function")for(var o=0,n=Object.getOwnPropertySymbols(e);o<n.length;o++)t.indexOf(n[o])<0&&Object.prototype.propertyIsEnumerable.call(e,n[o])&&(a[n[o]]=e[n[o]]);return a};const Nt=8,bt=3,Et=e=>{let{children:t,prefixCls:a}=e;const n=Ee(a),[o,r,u]=ke(a,n);return o(s.createElement(at,{classNames:{list:A(r,u,n)}},t))},St=(e,t)=>{let{prefixCls:a,key:n}=t;return s.createElement(Et,{prefixCls:a,key:n},e)},Ot=s.forwardRef((e,t)=>{const{top:a,prefixCls:n,getContainer:o,maxCount:r,duration:u=bt,rtl:S,transitionName:c,onAllRemoved:m}=e,{getPrefixCls:y,getPopupContainer:v,message:p}=s.useContext(be),g=n||y("message"),h=()=>({left:"50%",transform:"translateX(-50%)",top:a??Nt}),N=()=>A({[`${g}-rtl`]:S}),b=()=>ht(g,c),E=s.createElement("span",{className:`${g}-close-x`},s.createElement(et,{className:`${g}-close-icon`})),[$,O]=dt({prefixCls:g,style:h,className:N,motion:b,closable:!1,closeIcon:E,duration:u,getContainer:()=>(o==null?void 0:o())||(v==null?void 0:v())||document.body,maxCount:r,onAllRemoved:m,renderNotifications:St});return s.useImperativeHandle(t,()=>Object.assign(Object.assign({},$),{prefixCls:g,message:p})),O});let he=0;function we(e){const t=s.useRef(null);return tt(),[s.useMemo(()=>{const n=c=>{var m;(m=t.current)===null||m===void 0||m.close(c)},o=c=>{if(!t.current){const P=()=>{};return P.then=()=>{},P}const{open:m,prefixCls:y,message:v}=t.current,p=`${y}-notice`,{content:g,icon:h,type:N,key:b,className:E,style:$,onClose:O}=c,k=xt(c,["content","icon","type","key","className","style","onClose"]);let x=b;return x==null&&(he+=1,x=`antd-message-${he}`),ue(P=>(m(Object.assign(Object.assign({},k),{key:x,content:s.createElement($e,{prefixCls:y,type:N,icon:h},g),placement:"top",className:A(N&&`${p}-${N}`,E,v==null?void 0:v.className),style:Object.assign(Object.assign({},v==null?void 0:v.style),$),onClose:()=>{O==null||O(),P()}})),()=>{n(x)}))},u={open:o,destroy:c=>{var m;c!==void 0?n(c):(m=t.current)===null||m===void 0||m.destroy()}};return["info","success","warning","error","loading"].forEach(c=>{const m=(y,v,p)=>{let g;y&&typeof y=="object"&&"content"in y?g=y:g={content:y};let h,N;typeof v=="function"?N=v:(h=v,N=p);const b=Object.assign(Object.assign({onClose:N,duration:h},g),{type:c});return o(b)};u[c]=m}),u},[]),s.createElement(Ot,Object.assign({key:"message-holder"},e,{ref:t}))]}function Pt(e){return we(e)}let R=null,L=e=>e(),B=[],Z={};function xe(){const{prefixCls:e,getContainer:t,duration:a,rtl:n,maxCount:o,top:r}=Z,u=e??Se().getPrefixCls("message"),S=(t==null?void 0:t())||document.body;return{prefixCls:u,getContainer:()=>S,duration:a,rtl:n,maxCount:o,top:r}}const kt=s.forwardRef((e,t)=>{const[a,n]=s.useState(xe),[o,r]=we(a),u=Se(),S=u.getRootPrefixCls(),c=u.getIconPrefixCls(),m=u.getTheme(),y=()=>{n(xe)};return s.useEffect(y,[]),s.useImperativeHandle(t,()=>{const v=Object.assign({},o);return Object.keys(v).forEach(p=>{v[p]=function(){return y(),o[p].apply(o,arguments)}}),{instance:v,sync:y}}),s.createElement(ot,{prefixCls:S,iconPrefixCls:c,theme:m},r)});function J(){if(!R){const e=document.createDocumentFragment(),t={fragment:e};R=t,L(()=>{nt(s.createElement(kt,{ref:a=>{const{instance:n,sync:o}=a||{};Promise.resolve().then(()=>{!t.instance&&n&&(t.instance=n,t.sync=o,J())})}}),e)});return}R.instance&&(B.forEach(e=>{const{type:t,skipped:a}=e;if(!a)switch(t){case"open":{L(()=>{const n=R.instance.open(Object.assign(Object.assign({},Z),e.config));n==null||n.then(e.resolve),e.setCloseFn(n)});break}case"destroy":L(()=>{R==null||R.instance.destroy(e.key)});break;default:L(()=>{var n;const o=(n=R.instance)[t].apply(n,D(e.args));o==null||o.then(e.resolve),e.setCloseFn(o)})}}),B=[])}function $t(e){Z=Object.assign(Object.assign({},Z),e),L(()=>{var t;(t=R==null?void 0:R.sync)===null||t===void 0||t.call(R)})}function wt(e){const t=ue(a=>{let n;const o={type:"open",config:e,resolve:a,setCloseFn:r=>{n=r}};return B.push(o),()=>{n?L(()=>{n()}):o.skipped=!0}});return J(),t}function It(e,t){const a=ue(n=>{let o;const r={type:e,args:t,resolve:n,setCloseFn:u=>{o=u}};return B.push(r),()=>{o?L(()=>{o()}):r.skipped=!0}});return J(),a}function Rt(e){B.push({type:"destroy",key:e}),J()}const Mt=["success","info","warning","error","loading"],At={open:wt,destroy:Rt,config:$t,useMessage:Pt,_InternalPanelDoNotUseOrYouWillBeFired:Ct},Ie=At;Mt.forEach(e=>{Ie[e]=function(){for(var t=arguments.length,a=new Array(t),n=0;n<t;n++)a[n]=arguments[n];return It(e,a)}});const jt=Ie;export{jt as m};