import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  Container,
  Card,
  Button,
  Image,
  Input,
  Grid,
  Table,
  Message,
  Icon,
} from "semantic-ui-react";
import drinksImage from "../images/drinks.jpg";
import { Link } from "react-router-dom";
import breakfastImage from "../images/breakfast.jpg";
import desertsImage from "../images/deserts.jpg";
import dinnerImage from "../images/dinner.jpg";
import burgerImage from "../images/burger.jpg";
import foodImage from "../images/food.jpg";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewMeals() {
  const params = useParams();
  const [menu, setMenu] = useState({});
  const [meals, setMeals] = useState([]);
  const [orderItems, setOrderItems] = useState([]);
  const [quantity, setQuantity] = useState("");
  const [unconfirmedOrders, setUnconfirmedOrders] = useState([]);

  const getImageForMenu = (menu) => {
    switch (menu.title.toLowerCase()) {
      case "breakfast":
        return breakfastImage;
      case "deserts":
        return desertsImage;
      case "dinner":
        return dinnerImage;
      case "drinks":
        return drinksImage;
      case "burgers":
        return burgerImage;
      default:
        return foodImage;
    }
  };

  const fetchUnconfirmedOrders = async () => {
    fetch("/api/v1/foodOrders/unconfirmed")
      .then((response) => response.json())
      .then((data) => setUnconfirmedOrders(data))
      .catch((error) =>
        console.error("Error fetching unconfirmed orders:", error)
      );
    console.log("UNCONFIRMED ITEMS: ", unconfirmedOrders);
  };

  useEffect(() => {
    fetchUnconfirmedOrders();
  }, []);

  const fetchMenu = async () => {
    fetch(`/api/v1/menus/${params.id}`)
    .then((response) => response.json())
    .then((data) => {
      setMenu(data);
      setMeals(data.meals);
    })
    .catch((error) => console.error("Error fetching menu:", error));
  }

  useEffect(() => {
    if (params.id) {
      fetchMenu();
      // fetch(`/api/v1/menus/${params.id}`)
      //   .then((response) => response.json())
      //   .then((data) => {
      //     setMenu(data);
      //     setMeals(data.meals);
      //   })
      //   .catch((error) => console.error("Error fetching menu:", error));

      if (unconfirmedOrders.length > 0) {
        const unconfOrder = unconfirmedOrders[0];
        fetch(`/api/v1/orderItems/foodOrder/${unconfOrder.id}`)
          .then((response) => response.json())
          .then((data) => setOrderItems(data))
          .catch((error) =>
            console.error("Error fetching order items:", error)
          );
      }
    }
  }, [params.id, unconfirmedOrders]);

  const handleAddOrderItem = async (mealId) => {
    const selectedMeal = meals.find((meal) => meal.id === mealId);

    if (selectedMeal && unconfirmedOrders.length > 0) {
      const unconfOrder = unconfirmedOrders[0];

      const newQuantity = quantity[mealId] !== "" ? quantity[mealId] : 0;

      try {
        const createOrderItemResponse = await fetch(
          `/api/v1/orderItems/${unconfOrder.id}/${mealId}/${newQuantity}/newOrderItem`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        if (createOrderItemResponse.ok) {
          fetch("/api/v1/foodOrders/unconfirmed")
            .then((response) => response.json())
            .then((data) => setUnconfirmedOrders(data))
            .catch((error) =>
              console.error("Error fetching unconfirmed orders:", error)
            );
          setQuantity("");
        } else {
          console.error(
            "Failed to create order item:",
            createOrderItemResponse.status
          );
        }
      } catch (error) {
        console.error("Error adding order item:", error);
      }
    } else {
      console.error("Selected meal is undefined or no unconfirmed orders.");
    }
  };

  const handleQuantityChange = (mealId, e) => {
    const newQuantity = {
      ...quantity,
      [mealId]: e.target.value !== "" ? parseInt(e.target.value) : "",
    };
    setQuantity(newQuantity);
  };

  const renderMeals = () => {
    return meals.map((meal) => (
      <Card key={meal.id} style={{ width: "200px" }}>
        <Image src={getImageForMenu(menu)} wrapped ui={false} />
        <Card.Content>
          <Card.Header style={{ textAlign: "center" }}>
            {meal.title}
          </Card.Header>
        </Card.Content>
        <Card.Content extra>
          <div style={{ display: "flex", alignItems: "center" }}>
            <Input
              placeholder="Quantity"
              style={{
                marginRight: "10px",
                width: "90px",
                color: "grey",
                backgroundColor: "transparent",
                border: "1px solid grey",
                borderRadius: "5px",
              }}
              value={quantity[meal.id] || ""}
              onChange={(e) => handleQuantityChange(meal.id, e)}
            />
            <Button
              style={{
                color: "grey",
                backgroundColor: "transparent",
                border: "1px solid grey",
              }}
              onClick={() => handleAddOrderItem(meal.id)}
            >
              ADD
            </Button>
          </div>
        </Card.Content>
      </Card>
    ));
  };

  const handleConfirmOrder = async (orderId) => {
    try {
      const response = await fetch(`/api/v1/foodOrders/${orderId}/confirm`, {
        method: "PUT",
      });

      if (response.ok) {
        // Handle successful confirmation
        console.log("Order confirmed successfully!");
      } else {
        // Handle error response
        console.error("Error confirming order:", response.statusText);
      }
    } catch (error) {
      console.error("An error occurred while confirming the order:", error);
    }
    fetchUnconfirmedOrders();
    setOrderItems([]);
  };

  return (
    <Container className="meals mt-3">
      <Grid columns={2} divided>
        <Grid.Row>
          <Grid.Column width={12}>
            <Button
              className="create-button mb-2"
              style={{
                color: "grey",
                backgroundColor: "transparent",
                border: "1px solid grey",
              }}
              name="create"
              active={false}
              as={Link}
              to={`/meals/create/${params.id}`}
            >
              Add new meal
            </Button>
            <Card.Group>{renderMeals()}</Card.Group>
          </Grid.Column>
          <Grid.Column width={4}>
            {unconfirmedOrders.map((order) => (
              <Message key={order.id}>
                <b>Order ID: {order.id}</b>
                <br></br>
                {order.createdDate}
              </Message>
            ))}

            <Table celled>
              <Table.Header>
                <Table.Row>
                  <Table.HeaderCell>Meal</Table.HeaderCell>
                  <Table.HeaderCell>Quantity</Table.HeaderCell>
                  <Table.HeaderCell>Action</Table.HeaderCell>
                </Table.Row>
              </Table.Header>
              <Table.Body>
                {orderItems.map((item, index) => {
                  console.log("Item:", item);
                  return (
                    <Table.Row key={index}>
                      <Table.Cell>{item.meal.title}</Table.Cell>
                      <Table.Cell>{item.quantity}</Table.Cell>
                      <Table.Cell>
                        <Button
                          basic
                          style={{
                            color: "grey",
                            backgroundColor: "transparent",
                            border: "1px solid grey",
                          }}
                        >
                          <Icon name="trash" size="small" />
                        </Button>
                      </Table.Cell>
                    </Table.Row>
                  );
                })}
              </Table.Body>
            </Table>
            <Button
              className="mt-2 menu-button"
              style={{
                color: "pink",
                backgroundColor: "transparent",
                border: "1px solid pink",
              }}
              onClick={() => handleConfirmOrder(unconfirmedOrders[0].id)}
            >
              Confirm order
            </Button>
          </Grid.Column>
        </Grid.Row>
      </Grid>
    </Container>
  );
}
