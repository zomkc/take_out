import{m as a}from"./axios.7f6bf870.js";function o(r){return a.post("/api/order/submit",{...r})}function e(r){return a.get("/api/order/userPage",{params:{...r}})}function n(r){return a.post("/api/order/again",{...r})}export{n as a,o as b,e as o};