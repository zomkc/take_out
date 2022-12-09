import{i as F,m as K,n as B,t as T,o as V,p as G,q as U,s as W,v as D,x as J,y as H,b as M,c as q,e as y,z as X,w as Y}from"./base.c25e0513.js";import{u as m,r as P,G as p,z as S,H as g,B as $,I as Z,J as k}from"./index.7da13a9a.js";function A(e){var t;const n=U(e);return(t=n==null?void 0:n.$el)!=null?t:n}const L=F?window:void 0;function ge(...e){let t,n,s,a;if(K(e[0])||Array.isArray(e[0])?([n,s,a]=e,t=L):[t,n,s,a]=e,!t)return B;Array.isArray(n)||(n=[n]),Array.isArray(s)||(s=[s]);const r=[],o=()=>{r.forEach(l=>l()),r.length=0},c=(l,u,i)=>(l.addEventListener(u,i,a),()=>l.removeEventListener(u,i,a)),v=m(()=>A(t),l=>{o(),l&&r.push(...n.flatMap(u=>s.map(i=>c(l,u,i))))},{immediate:!0,flush:"post"}),f=()=>{v(),o()};return T(f),f}function ee(e,t=!1){const n=P(),s=()=>n.value=Boolean(e());return s(),V(s,t),n}function te(e){return JSON.parse(JSON.stringify(e))}const I=typeof globalThis<"u"?globalThis:typeof window<"u"?window:typeof global<"u"?global:typeof self<"u"?self:{},E="__vueuse_ssr_handlers__";I[E]=I[E]||{};I[E];var h=Object.getOwnPropertySymbols,ne=Object.prototype.hasOwnProperty,se=Object.prototype.propertyIsEnumerable,oe=(e,t)=>{var n={};for(var s in e)ne.call(e,s)&&t.indexOf(s)<0&&(n[s]=e[s]);if(e!=null&&h)for(var s of h(e))t.indexOf(s)<0&&se.call(e,s)&&(n[s]=e[s]);return n};function Ie(e,t,n={}){const s=n,{window:a=L}=s,r=oe(s,["window"]);let o;const c=ee(()=>a&&"ResizeObserver"in a),v=()=>{o&&(o.disconnect(),o=void 0)},f=m(()=>A(e),u=>{v(),c.value&&a&&u&&(o=new ResizeObserver(t),o.observe(u,r))},{immediate:!0,flush:"post"}),l=()=>{v(),f()};return T(l),{isSupported:c,stop:l}}var z;(function(e){e.UP="UP",e.RIGHT="RIGHT",e.DOWN="DOWN",e.LEFT="LEFT",e.NONE="NONE"})(z||(z={}));var ae=Object.defineProperty,j=Object.getOwnPropertySymbols,re=Object.prototype.hasOwnProperty,ie=Object.prototype.propertyIsEnumerable,N=(e,t,n)=>t in e?ae(e,t,{enumerable:!0,configurable:!0,writable:!0,value:n}):e[t]=n,le=(e,t)=>{for(var n in t||(t={}))re.call(t,n)&&N(e,n,t[n]);if(j)for(var n of j(t))ie.call(t,n)&&N(e,n,t[n]);return e};const ue={easeInSine:[.12,0,.39,0],easeOutSine:[.61,1,.88,1],easeInOutSine:[.37,0,.63,1],easeInQuad:[.11,0,.5,0],easeOutQuad:[.5,1,.89,1],easeInOutQuad:[.45,0,.55,1],easeInCubic:[.32,0,.67,0],easeOutCubic:[.33,1,.68,1],easeInOutCubic:[.65,0,.35,1],easeInQuart:[.5,0,.75,0],easeOutQuart:[.25,1,.5,1],easeInOutQuart:[.76,0,.24,1],easeInQuint:[.64,0,.78,0],easeOutQuint:[.22,1,.36,1],easeInOutQuint:[.83,0,.17,1],easeInExpo:[.7,0,.84,0],easeOutExpo:[.16,1,.3,1],easeInOutExpo:[.87,0,.13,1],easeInCirc:[.55,0,1,.45],easeOutCirc:[0,.55,.45,1],easeInOutCirc:[.85,0,.15,1],easeInBack:[.36,0,.66,-.56],easeOutBack:[.34,1.56,.64,1],easeInOutBack:[.68,-.6,.32,1.6]};le({linear:G},ue);function Ee(e,t,n,s={}){var a,r,o;const{clone:c=!1,passive:v=!1,eventName:f,deep:l=!1,defaultValue:u}=s,i=S(),w=n||(i==null?void 0:i.emit)||((a=i==null?void 0:i.$emit)==null?void 0:a.bind(i))||((o=(r=i==null?void 0:i.proxy)==null?void 0:r.$emit)==null?void 0:o.bind(i==null?void 0:i.proxy));let b=f;t||(t="modelValue"),b=f||b||`update:${t.toString()}`;const C=d=>c?W(c)?c(d):te(d):d,x=()=>D(e[t])?C(e[t]):u;if(v){const d=x(),_=P(d);return m(()=>e[t],O=>_.value=C(O)),m(_,O=>{(O!==e[t]||l)&&w(b,O)},{deep:l}),_}else return p({get(){return x()},set(d){w(b,d)}})}const ce=["","default","small","large"],Pe=e=>e,fe=["class","style"],de=/^on[A-Z]/,Se=(e={})=>{const{excludeListeners:t=!1,excludeKeys:n}=e,s=p(()=>((n==null?void 0:n.value)||[]).concat(fe)),a=S();return a?p(()=>{var r;return J(Object.entries((r=a.proxy)==null?void 0:r.$attrs).filter(([o])=>!s.value.includes(o)&&!(t&&de.test(o))))}):p(()=>({}))},Q=Symbol("formContextKey"),pe=Symbol("formItemContextKey"),R=e=>{const t=S();return p(()=>{var n,s;return(s=((n=t.proxy)==null?void 0:n.$props)[e])!=null?s:void 0})},ve=H({type:String,values:ce,required:!1}),we=(e,t={})=>{const n=P(void 0),s=t.prop?n:R("size"),a=t.global?n:M("size"),r=t.form?{size:void 0}:g(Q,void 0),o=t.formItem?{size:void 0}:g(pe,void 0);return p(()=>s.value||$(e)||(o==null?void 0:o.size)||(r==null?void 0:r.size)||a.value||"")},Ce=e=>{const t=R("disabled"),n=g(Q,void 0);return p(()=>t.value||$(e)||(n==null?void 0:n.disabled)||!1)},me={},be=q({a11y:{type:Boolean,default:!0},locale:{type:y(Object)},size:ve,button:{type:y(Object)},experimentalFeatures:{type:y(Object)},keyboardNavigation:{type:Boolean,default:!0},message:{type:y(Object)},zIndex:Number,namespace:{type:String,default:"el"}}),Oe=Z({name:"ElConfigProvider",props:be,setup(e,{slots:t}){m(()=>e.message,s=>{Object.assign(me,s!=null?s:{})},{immediate:!0,deep:!0});const n=X(e);return()=>k(t,"default",{config:n==null?void 0:n.value})}}),xe=Y(Oe);export{xe as E,Ce as a,ve as b,pe as c,Se as d,Ie as e,Q as f,ge as g,me as h,ce as i,Ee as j,Pe as m,we as u};