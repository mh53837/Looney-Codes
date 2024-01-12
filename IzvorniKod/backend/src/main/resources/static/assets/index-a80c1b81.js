import{o as D,R as J,r,aO as K,C as Q,a2 as U,aP as tt,f as q}from"./index-611d8f01.js";function lt(e){const{sizePopupArrow:a,borderRadiusXS:n,borderRadiusOuter:t}=e,s=a/2,o=0,l=s,i=t*1/Math.sqrt(2),u=s-t*(1-1/Math.sqrt(2)),h=s-n*(1/Math.sqrt(2)),y=t*(Math.sqrt(2)-1)+n*(1/Math.sqrt(2)),x=2*s-h,v=y,b=2*s-i,w=u,C=2*s-o,S=l,m=s*Math.sqrt(2)+t*(Math.sqrt(2)-2),c=t*(Math.sqrt(2)-1),N=`polygon(${c}px 100%, 50% ${c}px, ${2*s-c}px 100%, ${c}px 100%)`,p=`path('M ${o} ${l} A ${t} ${t} 0 0 0 ${i} ${u} L ${h} ${y} A ${n} ${n} 0 0 1 ${x} ${v} L ${b} ${w} A ${t} ${t} 0 0 0 ${C} ${S} Z')`;return{arrowShadowWidth:m,arrowPath:p,arrowPolygon:N}}const it=(e,a,n)=>{const{sizePopupArrow:t,arrowPolygon:s,arrowPath:o,arrowShadowWidth:l,borderRadiusXS:i,calc:u}=e;return{pointerEvents:"none",width:t,height:t,overflow:"hidden","&::before":{position:"absolute",bottom:0,insetInlineStart:0,width:t,height:u(t).div(2).equal(),background:a,clipPath:{_multi_value_:!0,value:[s,o]},content:'""'},"&::after":{content:'""',position:"absolute",width:l,height:l,bottom:0,insetInline:0,margin:"auto",borderRadius:{_skip_check_:!0,value:`0 0 ${D(i)} 0`},transform:"translateY(50%) rotate(-135deg)",boxShadow:n,zIndex:0,background:"transparent"}}};function j(e){return["small","middle","large"].includes(e)}function R(e){return e?typeof e=="number"&&!Number.isNaN(e):!1}const V=J.createContext({latestIndex:0}),et=V.Provider,st=e=>{let{className:a,index:n,children:t,split:s,style:o}=e;const{latestIndex:l}=r.useContext(V);return t==null?null:r.createElement(r.Fragment,null,r.createElement("div",{className:a,style:o},t),n<l&&s&&r.createElement("span",{className:`${a}-split`},s))},ot=st;var nt=globalThis&&globalThis.__rest||function(e,a){var n={};for(var t in e)Object.prototype.hasOwnProperty.call(e,t)&&a.indexOf(t)<0&&(n[t]=e[t]);if(e!=null&&typeof Object.getOwnPropertySymbols=="function")for(var s=0,t=Object.getOwnPropertySymbols(e);s<t.length;s++)a.indexOf(t[s])<0&&Object.prototype.propertyIsEnumerable.call(e,t[s])&&(n[t[s]]=e[t[s]]);return n};const at=r.forwardRef((e,a)=>{var n,t;const{getPrefixCls:s,space:o,direction:l}=r.useContext(Q),{size:i=(o==null?void 0:o.size)||"small",align:u,className:h,rootClassName:y,children:x,direction:v="horizontal",prefixCls:b,split:w,style:C,wrap:S=!1,classNames:m,styles:c}=e,N=nt(e,["size","align","className","rootClassName","children","direction","prefixCls","split","style","wrap","classNames","styles"]),[p,$]=Array.isArray(i)?i:[i,i],A=j($),E=j(p),G=R($),T=R(p),I=U(x,{keepEmpty:!0}),M=u===void 0&&v==="horizontal"?"center":u,d=s("space",b),[H,L,X]=tt(d),F=q(d,o==null?void 0:o.className,L,`${d}-${v}`,{[`${d}-rtl`]:l==="rtl",[`${d}-align-${M}`]:M,[`${d}-gap-row-${$}`]:A,[`${d}-gap-col-${p}`]:E},h,y,X),k=q(`${d}-item`,(n=m==null?void 0:m.item)!==null&&n!==void 0?n:(t=o==null?void 0:o.classNames)===null||t===void 0?void 0:t.item);let P=0;const Y=I.map((f,z)=>{var O,_;f!=null&&(P=z);const B=f&&f.key||`${k}-${z}`;return r.createElement(ot,{className:k,key:B,index:z,split:w,style:(O=c==null?void 0:c.item)!==null&&O!==void 0?O:(_=o==null?void 0:o.styles)===null||_===void 0?void 0:_.item},f)}),Z=r.useMemo(()=>({latestIndex:P}),[P]);if(I.length===0)return null;const g={};return S&&(g.flexWrap="wrap"),!E&&T&&(g.columnGap=p),!A&&G&&(g.rowGap=$),H(r.createElement("div",Object.assign({ref:a,className:F,style:Object.assign(Object.assign(Object.assign({},g),o==null?void 0:o.style),C)},N),r.createElement(et,{value:Z},Y)))}),W=at;W.Compact=K;const ct=W;export{ct as S,lt as a,it as g};