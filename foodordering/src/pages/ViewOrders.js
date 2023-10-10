import React, { useState, useEffect } from "react";
import { Card, Icon, Button } from "semantic-ui-react";

export function ViewOrders() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await fetch("/api/v1/foodOrders");
        if (response.ok) {
          const data = await response.json();
          setOrders(data);
          console.log("DATA: ", data);
        } else {
          console.error("Error fetching orders:", response.statusText);
        }
      } catch (error) {
        console.error("Error fetching orders:", error);
      }
    };

    fetchOrders();
  }, []);

  return (
    <div>
      <h2>Orders List</h2>
      <Card.Group>
        {orders.map((order) => (
          <Card key={order.id}>
            <Card.Content>
              <Card.Header>Order ID: {order.id}</Card.Header>
              <Card.Meta>Created Date: {order.createdDate}</Card.Meta>
              <Card.Description>
                <strong>Total Amount: ${order.totalAmount}</strong>
                <br />
                <strong>Order Items:</strong>
                <ul>
                  {order.orderItems.map((item) => (
                    <li key={item.id}>
                      {item.quantity}x {item.meal.title}
                    </li>
                  ))}
                </ul>
              </Card.Description>
            </Card.Content>
            <Card.Content extra>
              {order.confirmed ? "Confirmed" : "Not Confirmed"}
            </Card.Content>
          </Card>
        ))}
      </Card.Group>
    </div>
  );
}
