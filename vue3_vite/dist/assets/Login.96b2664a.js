import{u as P,r as k,i as U,g as b,a as E}from"./axios.7f6bf870.js";import{E as q}from"./el-button.bf2fb6ab.js";import{E as R}from"./el-input.4b2907ce.js";import{E as _}from"./el-message.21579b22.js";import{s as z,l as D,p as H}from"./login.ebb1e003.js";import{r as T,a as Z,c as K,t as Y,h as y,w as S,b as W,d as x,v as G,T as J,n as F,i as j,e as O,f as Q,g as X,_ as $,o as d,j as g,k as m,l as ee,m as V,p as C,q as p,s as I}from"./index.93b7fa5a.js";import{E as oe}from"./index.2e452faa.js";import{u as te}from"./index.560cc0f0.js";function se(t){let e;const s=P("loading"),l=T(!1),o=Z({...t,originalPosition:"",originalOverflow:"",visible:!1});function n(r){o.text=r}function a(){const r=o.parent;if(!r.vLoadingAddClassList){let f=r.getAttribute("loading-number");f=Number.parseInt(f)-1,f?r.setAttribute("loading-number",f.toString()):(k(r,s.bm("parent","relative")),r.removeAttribute("loading-number")),k(r,s.bm("parent","hidden"))}c(),h.unmount()}function c(){var r,f;(f=(r=u.$el)==null?void 0:r.parentNode)==null||f.removeChild(u.$el)}function v(){var r;t.beforeClose&&!t.beforeClose()||(l.value=!0,clearTimeout(e),e=window.setTimeout(i,400),o.visible=!1,(r=t.closed)==null||r.call(t))}function i(){if(!l.value)return;const r=o.parent;l.value=!1,r.vLoadingAddClassList=void 0,a()}const h=K({name:"ElLoading",setup(){return()=>{const r=o.spinner||o.svg,f=y("svg",{class:"circular",viewBox:o.svgViewBox?o.svgViewBox:"0 0 50 50",...r?{innerHTML:r}:{}},[y("circle",{class:"path",cx:"25",cy:"25",r:"20",fill:"none"})]),M=o.text?y("p",{class:s.b("text")},[o.text]):void 0;return y(J,{name:s.b("fade"),onAfterLeave:i},{default:S(()=>[W(x("div",{style:{backgroundColor:o.background||""},class:[s.b("mask"),o.customClass,o.fullscreen?"is-fullscreen":""]},[y("div",{class:s.b("spinner")},[f,M])]),[[G,o.visible]])])})}}}),u=h.mount(document.createElement("div"));return{...Y(o),setText:n,removeElLoadingChild:c,close:v,handleAfterLeave:i,vm:u,get $el(){return u.$el}}}let w;const ne=function(t={}){if(!U)return;const e=le(t);if(e.fullscreen&&w)return w;const s=se({...e,closed:()=>{var o;(o=e.closed)==null||o.call(e),e.fullscreen&&(w=void 0)}});re(e,e.parent,s),N(e,e.parent,s),e.parent.vLoadingAddClassList=()=>N(e,e.parent,s);let l=e.parent.getAttribute("loading-number");return l?l=`${Number.parseInt(l)+1}`:l="1",e.parent.setAttribute("loading-number",l),e.parent.appendChild(s.$el),F(()=>s.visible.value=e.visible),e.fullscreen&&(w=s),s},le=t=>{var e,s,l,o;let n;return j(t.target)?n=(e=document.querySelector(t.target))!=null?e:document.body:n=t.target||document.body,{parent:n===document.body||t.body?document.body:n,background:t.background||"",svg:t.svg||"",svgViewBox:t.svgViewBox||"",spinner:t.spinner||!1,text:t.text||"",fullscreen:n===document.body&&((s=t.fullscreen)!=null?s:!0),lock:(l=t.lock)!=null?l:!1,customClass:t.customClass||"",visible:(o=t.visible)!=null?o:!0,target:n}},re=async(t,e,s)=>{const{nextZIndex:l}=te(),o={};if(t.fullscreen)s.originalPosition.value=b(document.body,"position"),s.originalOverflow.value=b(document.body,"overflow"),o.zIndex=l();else if(t.parent===document.body){s.originalPosition.value=b(document.body,"position"),await F();for(const n of["top","left"]){const a=n==="top"?"scrollTop":"scrollLeft";o[n]=`${t.target.getBoundingClientRect()[n]+document.body[a]+document.documentElement[a]-Number.parseInt(b(document.body,`margin-${n}`),10)}px`}for(const n of["height","width"])o[n]=`${t.target.getBoundingClientRect()[n]}px`}else s.originalPosition.value=b(e,"position");for(const[n,a]of Object.entries(o))s.$el.style[n]=a},N=(t,e,s)=>{const l=P("loading");["absolute","fixed","sticky"].includes(s.originalPosition.value)?k(e,l.bm("parent","relative")):E(e,l.bm("parent","relative")),t.fullscreen&&t.lock?E(e,l.bm("parent","hidden")):k(e,l.bm("parent","hidden"))},A=Symbol("ElLoading"),B=(t,e)=>{var s,l,o,n;const a=e.instance,c=u=>O(e.value)?e.value[u]:void 0,v=u=>{const r=j(u)&&(a==null?void 0:a[u])||u;return r&&T(r)},i=u=>v(c(u)||t.getAttribute(`element-loading-${X(u)}`)),L=(s=c("fullscreen"))!=null?s:e.modifiers.fullscreen,h={text:i("text"),svg:i("svg"),svgViewBox:i("svgViewBox"),spinner:i("spinner"),background:i("background"),customClass:i("customClass"),fullscreen:L,target:(l=c("target"))!=null?l:L?void 0:t,body:(o=c("body"))!=null?o:e.modifiers.body,lock:(n=c("lock"))!=null?n:e.modifiers.lock};t[A]={options:h,instance:ne(h)}},ie=(t,e)=>{for(const s of Object.keys(e))Q(e[s])&&(e[s].value=t[s])},ae={mounted(t,e){e.value&&B(t,e)},updated(t,e){const s=t[A];e.oldValue!==e.value&&(e.value&&!e.oldValue?B(t,e):e.value&&e.oldValue?O(e.value)&&ie(e.value,s.options):s==null||s.instance.close())},unmounted(t){var e;(e=t[A])==null||e.instance.close()}};const ue={components:{ElConfigProvider:oe},data(){return{loginWay:!0,form:{phone:"19980841590",code:"",password:"123"},msgFlag:!1,loading:!1,codeShow:!0,count:"",timer:null}},methods:{getCode(){this.form.code="";const t=/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,e=60;t.test(this.form.phone)?(this.count=e,this.codeShow=!1,this.timer=setInterval(()=>{this.count>0&&this.count<=e?this.count--:(this.codeShow=!0,clearInterval(this.timer),this.timer=null)},1e3),this.msgFlag=!1,z({phone:this.form.phone})):this.msgFlag=!0},async btnLogin(){if(this.form.phone&&this.form.code){this.loading=!0;const t=await D({phone:this.form.phone,code:this.form.code});this.loading=!1,t.data.code===1?(sessionStorage.setItem("userPhone",this.form.phone),window.requestAnimationFrame(()=>{this.$router.push("/home")})):_.error("\u767B\u5F55\u5931\u8D25")}else _.error("\u8BF7\u8F93\u5165\u624B\u673A\u53F7\u6216\u9A8C\u8BC1\u7801")},async passwordbtnLogin(){if(/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/.test(this.form.phone)){if(this.form.phone&&this.form.password){this.loading=!0;const e=await H({phone:this.form.phone,password:this.form.password});this.loading=!1,e.data.code===1?(sessionStorage.setItem("userPhone",this.form.phone),window.requestAnimationFrame(()=>{this.$router.push("/home")})):_.error("\u767B\u5F55\u5931\u8D25")}else _.error({message:"\u8BF7\u8F93\u5165\u624B\u673A\u53F7\u6216\u5BC6\u7801"});this.msgFlag=!1}else this.msgFlag=!0}}},ce={id:"login"},de=p("div",{class:"divHead"},"\u767B\u5F55",-1),me={key:0,class:"divContainer"},fe={key:1,style:{color:"#bbbbbb"},class:"count"},ge={key:1,class:"divContainer"},pe={key:2,class:"divMsg"},ve=p("div",{class:"login_hint"},[p("span",null," \u6E29\u99A8\u63D0\u793A\uFF1A\u672A\u6CE8\u518C\u5E10\u53F7\u7684\u624B\u673A\u53F7\uFF0C\u767B\u5F55\u65F6\u5C06\u81EA\u52A8\u6CE8\u518C\uFF0C\u4E14\u4EE3\u8868\u5DF2\u540C\u610F "),p("a",{href:"javascript:;"},"\u300A\u7528\u6237\u670D\u52A1\u534F\u8BAE\u300B")],-1),he={class:"password"};function be(t,e,s,l,o,n){const a=R,c=q,v=ae;return W((d(),g("div",ce,[de,o.loginWay?(d(),g("div",me,[x(a,{placeholder:"  \u8BF7\u8F93\u5165\u624B\u673A\u53F7\u7801",modelValue:o.form.phone,"onUpdate:modelValue":e[0]||(e[0]=i=>o.form.phone=i),maxlength:"20"},null,8,["modelValue"]),x(a,{placeholder:" \u8BF7\u8F93\u5165\u9A8C\u8BC1\u7801",modelValue:o.form.code,"onUpdate:modelValue":e[1]||(e[1]=i=>o.form.code=i),maxlength:"20"},null,8,["modelValue"]),o.codeShow?(d(),g("span",{key:0,style:{color:"#ff0000"},onClick:e[2]||(e[2]=(...i)=>n.getCode&&n.getCode(...i))},"\u83B7\u53D6\u9A8C\u8BC1\u7801")):m("",!0),o.codeShow?m("",!0):(d(),g("span",fe,ee(o.count)+"\u79D2\u540E\u91CD\u8BD5",1))])):m("",!0),o.loginWay?m("",!0):(d(),g("div",ge,[x(a,{placeholder:"  \u8BF7\u8F93\u5165\u624B\u673A\u53F7\u7801",modelValue:o.form.phone,"onUpdate:modelValue":e[3]||(e[3]=i=>o.form.phone=i),maxlength:"20"},null,8,["modelValue"]),o.loginWay?m("",!0):(d(),V(a,{key:0,placeholder:" \u8BF7\u8F93\u5165\u5BC6\u7801",type:"password",modelValue:o.form.password,"onUpdate:modelValue":e[4]||(e[4]=i=>o.form.password=i),maxlength:"20"},null,8,["modelValue"]))])),o.msgFlag?(d(),g("div",pe,"\u624B\u673A\u53F7\u8F93\u5165\u4E0D\u6B63\u786E\uFF0C\u8BF7\u91CD\u65B0\u8F93\u5165")):m("",!0),o.loginWay?(d(),V(c,{key:3,type:"primary",class:C({btnSubmit:!0,btnNoPhone:!o.form.phone,btnPhone:o.form.phone}),onClick:n.btnLogin},{default:S(()=>[I(" \u767B\u5F55 ")]),_:1},8,["class","onClick"])):m("",!0),o.loginWay?m("",!0):(d(),V(c,{key:4,type:"primary",class:C({btnSubmit:!0,btnNoPhone:!o.form.phone,btnPhone:o.form.phone}),onClick:n.passwordbtnLogin},{default:S(()=>[I(" \u5BC6\u7801\u767B\u5F55 ")]),_:1},8,["class","onClick"])),ve,p("div",he,[o.loginWay?(d(),g("span",{key:0,class:C({on:!o.loginWay})},[p("a",{href:"javascript:;",onClick:e[5]||(e[5]=i=>o.loginWay=!1)},"\u5BC6\u7801\u767B\u5F55")],2)):m("",!0),o.loginWay?m("",!0):(d(),g("span",{key:1,class:C({on:o.loginWay})},[p("a",{href:"javascript:;",onClick:e[6]||(e[6]=i=>o.loginWay=!0)},"\u77ED\u4FE1\u767B\u5F55")],2))])])),[[v,o.loading]])}const Se=$(ue,[["render",be]]);export{Se as default};
