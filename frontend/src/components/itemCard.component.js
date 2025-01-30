import React from "react";
import Button from 'react-bootstrap/Button'
import Card from "react-bootstrap/Card";

export default function ItemCard({item}) {

  return (
    <div>
      <Card border="primary" style={{ width: "15rem" }}>
        <Card.Header>
          {item.description}
        </Card.Header>
        <Card.Body>
          <Card.Img variant="top" src={item.image} />
          <Card.Title>Price: {item.price}$</Card.Title>
          <Card.Text>Stock: {item.stock}</Card.Text>
          <Button variant="primary">Add</Button>
        </Card.Body>
      </Card>
      <br />
    </div>
  );
}
