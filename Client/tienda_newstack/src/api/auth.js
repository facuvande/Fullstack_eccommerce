import axios from 'axios'

const API_USERS = 'http://localhost:8082/users'

export const registerRequest = user => axios.post(`${API_USERS}/auth/register`, user)