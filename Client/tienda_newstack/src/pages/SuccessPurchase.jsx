import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const SuccessPurchase = () => {
  const [purchaseDetails, setPurchaseDetails] = useState(null);

  useEffect(() => {
    const getParamsFromUrl = () => {
      const urlParams = new URLSearchParams(window.location.search);
      const params = {};
      for (const [key, value] of urlParams.entries()) {
        params[key] = value;
      }
      console.log('Parámetros de la URL:', params);
      setPurchaseDetails(params); // Almacenar los parámetros en el estado del componente
    };

    getParamsFromUrl();
  }, []);

  return (
    <div className="min-h-screen bg-gray-800 flex justify-center items-center">
      <div className="bg-white p-8 rounded-lg shadow-md">
        <h1 className="text-3xl font-bold mb-4 text-blue-700">¡Compra exitosa!</h1>
        <p className="text-gray-600 mb-4">Gracias por tu compra.</p>
        {purchaseDetails && (
          <div className="border-t border-gray-300 pt-4">
            <h2 className="text-xl font-bold mb-2">Detalles de la compra:</h2>
            <ul className="text-gray-700">
              <li><strong>Order ID:</strong> {purchaseDetails.collection_id}</li>
              <li><strong>Estado del pago:</strong> {purchaseDetails.collection_status}</li>
              <li><strong>ID de pago:</strong> {purchaseDetails.payment_id}</li>
              <li><strong>Metodo de pago:</strong> {purchaseDetails.payment_type}</li>
            </ul>
            <Link to="/" className="block text-blue-700 mt-4 text-center">Volver al inicio</Link>
          </div>
        )}
      </div>
    </div>
  );
};

export default SuccessPurchase;
