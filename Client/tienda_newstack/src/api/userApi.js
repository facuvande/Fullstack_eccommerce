const instance = 'http://localhost:8082/users';

export const getUsersRequest = (token) => {
    if(token){
        return fetch(`${instance}/api`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        });
    }
}

export const createPaymentRequest = (id_payment, token) => {
    if(token){
        return fetch(`${instance}/api/addPayment/${id_payment}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        });
    }
}


export const editProfileRequest = (newData, token) => {
    if (token) {
        return fetch(`${instance}/api/editUser`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(newData)
        });
    }
};

export const saveProductFavorite = (id_product, token) => {
    if (token) {
        return fetch(`${instance}/api/addFavorite/${id_product}`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    }
};

export const deleteProductFavorite = (id_product, token) => {
    if (token) {
        return fetch(`${instance}/api/deleteFavorite/${id_product}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    }
};