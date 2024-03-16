import axios from "axios"

// const API_USERS = 'http://localhost:8082/users'

const instance = axios.create({
    baseURL: 'http://localhost:8082/users',
    withCredentials: true
})

export const editProfileRequest = (newData, token) => {
    if(token){
        return instance.put(`/api/editUser`, newData, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
    }
}
export const saveProductFavorite = (id_product, token) => {
    if(token){
        return instance.post(`/api/addFavorite/${id_product}`, null, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
    }
}