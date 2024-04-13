const instance = 'http://localhost:8084/carts'

export const getCartRequest = (id_cart, token) => {
    return fetch(`${instance}/${id_cart}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    });
}

export const addProductToCartRequest = (id_cart, id_product, quantity, token) => {
    return fetch(`${instance}/addProduct/${id_cart}/${id_product}/${quantity}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    });
}

export const deleteProductToCartRequest = (id_cart, id_product, token) => {
    return fetch(`${instance}/deleteProduct/${id_cart}/${id_product}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    });
}

export const getPurchaseRequest = (id_cart, token) => {
    return fetch(`${instance}/purchase/${id_cart}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    });
}