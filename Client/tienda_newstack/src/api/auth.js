import axios from "axios"

// const API_USERS = 'http://localhost:8082/users'

const instance = axios.create({
    baseURL: 'http://localhost:8082/users',
    withCredentials: true
})

export const registerRequest = user => instance.post(`/auth/register`, user)
export const loginRequest = user => instance.post(`/auth/login`, user)
export const editProfileRequest = (newData, token) => {
    if(token){
        return instance.put(`/api/editUser`, newData, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
    }
}
export const verifyTokenRequest = token => {
    if(token){
        return instance.post(`/auth/validateToken`, null, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
    }
}