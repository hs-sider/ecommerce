import React, { useState, useEffect } from 'react';
import ItemCard from "./itemCard.component";
import Row from 'react-bootstrap/Row';

export default function Items() {
  const [items, setItems] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const verifyToken = async () => {
      const token = localStorage.getItem('token');
        console.log(token)

        if (!token) {
          setError('Token verification failed');
          return;
        }

        try {
        const response = await fetch(`http://localhost:8080/api/v1/items`);
        const data = await response.json();
        setItems(data);
        console.log(data)
        } catch (error) {
          console.error("Error fetching items:", error);
        }

    };

    verifyToken();
  }, []);

  return (
        <>
          {items.map((item, index) => {
            return <ItemCard item={item} key={index} />;
          })}
          {error && <p style={{ color: 'red' }}>{error}</p>}
        </>
    );
}