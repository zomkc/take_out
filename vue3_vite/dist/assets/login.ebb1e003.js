import{m as i}from"./axios.7f6bf870.js";function n(s){return i.post("/api/user/login",{...s})}function o(s){return i.post("/api/user/password",{...s})}function r(s){return i.post("/api/user/sendMsg",{...s})}function p(){return i.post("/api/user/loginout")}function e(s){return i.post("/api/user/edit",{...s})}function u(s){return i.post("/api/user/edituser",{...s})}export{p as a,u as b,e,n as l,o as p,r as s};
