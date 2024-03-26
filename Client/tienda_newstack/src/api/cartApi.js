const instance = 'http://localhost:8084/carts'

// export const getCartRequest = () => {
//     return fetch(instance, {
//         method: 'GET',
//         headers: {
//             'Content-Type': 'application/json',
//         }
//     });
// }

export const addProductToCartRequest = (id_cart, id_product, quantity, token) => {
    return fetch(`${instance}/addProduct/${id_cart}/${id_product}/${quantity}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    });
}
