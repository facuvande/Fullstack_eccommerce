import axios from "axios"

const instance = axios.create({
    baseURL: 'http://localhost:8083/products',
    withCredentials: true
})

export const getProductByIdRequest = productId => instance.get(`/${productId}`)
// export const loginRequest = user => instance.post(`/auth/login`, user)
// export const verifyTokenRequest = token => {
//     if(token){
//         return instance.post(`/auth/validateToken`, null, {
//             headers: {
//                 'Authorization': `Bearer ${token}`
//             }
//         })
//     }
// }