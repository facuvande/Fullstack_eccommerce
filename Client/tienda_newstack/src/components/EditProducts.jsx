import React from 'react'
import { Navbar } from './Navbar'
import { FooterNav } from './FooterNav'
import { useAuth } from '../context/AuthContext'
import { Navigate } from 'react-router-dom'
import { useEffect } from 'react'
import { addProductRequest, deleteProductRequest, editProductRequest, getProducts } from '../api/product'
import { useState } from 'react'
import { EditProductsModal } from './EditProductsModal'
import Cookies from 'js-cookie'
import Swal from 'sweetalert2'
import { AddProductsModal } from './AddProductsModal'

export const EditProducts = () => {

    const { user } = useAuth();
    const [products, setProducts] = useState([])
    const [productToEdit, setProductToEdit] = useState(null)
    const [modal, setModal] = useState(false);
    const [addProductModal, setAddProductModal] = useState(false);

    useEffect(() => {
        getProducts().then(response => response.json()).then(data => setProducts(data))
    }, [])

    const toggleEditModal = (product) => {
        setModal(!modal);
        setProductToEdit(product);
    }

    const toggleAddProductModal = () => {
        setAddProductModal(!addProductModal);
    }

    const handleEditProduct = async (updatedProduct) => {
        const cookies = Cookies.get();
        const res = await editProductRequest(updatedProduct, cookies.token);
        if (res.ok) {
            // Actualiza la lista de productos después de la edición
            getProducts().then(response => response.json()).then(data => setProducts(data));
            toggleEditModal();
        } else {
            console.error('Error al editar el producto');
        }
    };

    const handleAddProduct = async (product) => {
        const cookies = Cookies.get();
        const res = await addProductRequest(product, cookies.token);
        if (res.ok) {
            // Actualiza la lista de productos después de agregar
            getProducts().then(response => response.json()).then(data => setProducts(data));
            toggleAddProductModal();
        } else {
            console.error('Error al agregar el producto');
        }
    };

    const deleteProduct = (product) => {
        const cookies = Cookies.get();
        
        Swal.fire({
            title: "Estas seguro que desea eliminar el Producto?",
            text: "Este cambio es irreversible!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Confirmar"
        }).then(async (result) => {
            if (result.isConfirmed) {
                const res = await deleteProductRequest(product.id_product, cookies.token);
                if (res.ok) {
                    // Actualiza la lista de productos después de la eliminación
                    getProducts().then(response => response.json()).then(data => setProducts(data));
                    Swal.fire({
                        title: "Eliminado!",
                        text: "Producto eliminado.",
                        icon: "success"
                    });
                } else {
                    console.error('Error al eliminar el producto');
                }
            }
        });

    }

    if(user.rol[0].name !== 'ADMIN') return <Navigate to='/'/>

    return (
        <>
            <Navbar/>
            <div className="relative overflow-x-auto mt-48">
                <h2 className='text-center mb-5 font-bold text-4xl'>Lista de productos</h2>
                <table className="w-9/12 m-auto text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                    <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" className="px-6 py-3">
                                Nombre
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Descripcion
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Marca
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Precio
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Stock
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Opciones
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            products.map(product => (
                                <tr key={product.id_product} className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 text-ce">
                                    <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                        {product.name}
                                    </th>
                                    <td className="px-6 py-4">
                                        {product.description}
                                    </td>
                                    <td className="px-6 py-4">
                                        {product.brand}
                                    </td>
                                    <td className="px-6 py-4">
                                        ${product.price}
                                    </td>
                                    <td className="px-6 py-4">
                                        {product.stock}
                                    </td>
                                    <td className="px-6 py-4 flex gap-2">
                                        <svg  xmlns="http://www.w3.org/2000/svg" onClick={() => toggleEditModal(product)}  width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  strokeWidth="2"  strokeLinecap="round"  strokeLinejoin="round"  className='text-blue-400'><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M7 7h-1a2 2 0 0 0 -2 2v9a2 2 0 0 0 2 2h9a2 2 0 0 0 2 -2v-1" /><path d="M20.385 6.585a2.1 2.1 0 0 0 -2.97 -2.97l-8.415 8.385v3h3l8.385 -8.415z" /><path d="M16 5l3 3" /></svg>
                                        <svg  xmlns="http://www.w3.org/2000/svg" onClick={() => deleteProduct(product)} width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  strokeWidth="2"  strokeLinecap="round"  strokeLinejoin="round"  className='text-red-500'><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 7l16 0" /><path d="M10 11l0 6" /><path d="M14 11l0 6" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>
                                    </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
            {
                modal ? <EditProductsModal toggleEditModal={toggleEditModal} product={productToEdit} onSubmit={handleEditProduct}/> : null
            }
            

            <div data-dial-init className="fixed end-6 bottom-6 group">
                <div id="speed-dial-menu-default" className="flex flex-col items-center mb-12 space-y-2">
                <button type="button" onClick={toggleAddProductModal} data-dial-toggle="speed-dial-menu-default" aria-controls="speed-dial-menu-default" aria-expanded="false" className="flex items-center justify-center text-white bg-blue-700 rounded-full w-14 h-14 hover:bg-blue-800 dark:bg-blue-600 dark:hover:bg-blue-700 focus:ring-4 focus:ring-blue-300 focus:outline-none dark:focus:ring-blue-800">
                    <svg className="w-5 h-5 transition-transform group-hover:rotate-45" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 18 18">
                        <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 1v16M1 9h16"/>
                    </svg>
                    <span className="sr-only">Open actions menu</span>
                </button>
                </div>
            </div>

            {
                addProductModal ? <AddProductsModal toggleAddProductModal={toggleAddProductModal} handleAddProduct={handleAddProduct}/> : null
            }

            <FooterNav/>
        </>
    )
}
