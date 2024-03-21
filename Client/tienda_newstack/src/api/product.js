const instance = 'http://localhost:8083/products'

export const getProducts = () => {
    return fetch(instance, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        }
    });
}

export const getProductByIdRequest = productId => {
    return fetch(`${instance}/${productId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }
    });
    
}
export const getFavoriteProductsRequest = (token) => {
    if(token){
        return fetch(`${instance}/productsFavorites`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
        });
    }
} 

export const editProductRequest = (product, token) => {
    if(token){
        return fetch(`${instance}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(product)
        });
    }
}

export const deleteProductRequest = (id_product, token) => {
    if(token){
        return fetch(`${instance}/${id_product}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
    }
}

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