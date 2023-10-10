import { useState, useEffect, useContext } from "react";
import { Link, NavLink } from "react-router-dom";
import {
  Card,
  Container,
  Button,
  Table,
  Image,
  Grid,
  Message,
} from "semantic-ui-react";
import { AuthContext } from "../AuthContext";
import drinksImage from "../images/drinks.jpg";

import breakfastImage from "../images/breakfast.jpg";
import desertsImage from "../images/deserts.jpg";
import dinnerImage from "../images/dinner.jpg";
import burgerImage from "../images/burger.jpg";
import foodImage from "../images/food.jpg";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function ViewMenus() {
  const { appState, setAppState } = useContext(AuthContext);

  const [menus, setMenus] = useState([]);
  const [open, setOpen] = useState(false);
  const [totalAmount, setTotalAmount] = useState("");
  const [orderItems, setOrderItems] = useState([]);
  const [unconfirmedOrders, setUnconfirmedOrders] = useState([]);
  const [createdOrderId, setCreatedOrderId] = useState(null);
  const [selectedOrderItems, setSelectedOrderItems] = useState([]);

  const fetchMenus = async () => {
    fetch("/api/v1/menus")
      .then((response) => response.json())
      .then((jsonResponse) => setMenus(jsonResponse));
  };
 
  const fetchUnconfirmedOrders = async () => {
    fetch("/api/v1/foodOrders/unconfirmed")
    .then((response) => response.json())
    .then((data) => setUnconfirmedOrders(data))
    .catch((error) =>
      console.error("Error fetching unconfirmed orders:", error)
    );
};


  const handleCreateNewOrderButton = () => {
    const currentDate = new Date();

    const updatedOrderItems = orderItems.map((item) => ({
      ...item,
      meal: {
        id: item.meal.id,
        title: item.meal.title,
        description: item.meal.description
      }
    }));

    const newOrder = {
      createdDate: currentDate.toISOString(),
      totalAmount,
      orderItems: updatedOrderItems,
      is_confirmed: false,
    };

    fetch("/api/v1/foodOrders", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify(newOrder),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log("Food order created successfully!", data);
        setCreatedOrderId(data.id); 
        setUnconfirmedOrders((prevUnconfirmedOrders) => [
          ...prevUnconfirmedOrders,
          data,
        ]);
      })
      .catch((error) => {
        console.error("Error creating order:", error);
      });
  };

  useEffect(() => {
    fetchMenus();
    fetchUnconfirmedOrders();
  }, []);

  const deleteMenus = async (id) => {
    fetch(`/api/v1/menus/` + id, {
      method: "DELETE",
    })
      .then((response) => {
        if (response.ok) {
          const updatedMenus = menus.filter((menu) => menu.id !== id);
          setMenus(updatedMenus);
        }
      })
      .catch((error) => {
        console.error("Error deleting menus:", error);
      });
  };

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

  useEffect(() => {
    if (unconfirmedOrders.length > 0) {
      const firstUnconfirmedOrder = unconfirmedOrders[0];
      setSelectedOrderItems(firstUnconfirmedOrder.orderItems);
    }
  }, [unconfirmedOrders]);

  return (
    <Container className="menus mt-3" textAlign="center">
      <Grid columns={2} divided>
        <Grid.Row>
          <Grid.Column width={12}>
            <Button
              className="create-button mb-4"
              style={{
                color: "grey",
                backgroundColor: "transparent",
                border: "1px solid grey",
              }}
              name="create"
              active={false}
              as={Link}
              to="/menus/create"
            >
              Add new menu
            </Button>

            <Button
              className="create-button mb-4"
              style={{
                color: "grey",
                backgroundColor: "transparent",
                border: "1px solid grey",
              }}
              name="create"
              active={false}
              as={Link}
              to="/menus"
              onClick={handleCreateNewOrderButton}
            >
              Create New Order
            </Button>

            <Card.Group centered>
              {menus.map((menu) => (
                <Card className="card" key={menu.id}>
                  <Card.Content className="centered">
                    <Image src={getImageForMenu(menu)} />
                    <Card.Header className="m-2" textAlign="center">
                      {menu.title}
                    </Card.Header>
                    <Card.Description>{menu.description} </Card.Description>
                    <Card.Description className="buttons-container">
                      <Button
                        className="mt-2 menu-button"
                        as={NavLink}
                        exact
                        menuId={menu.Id}
                        to={`/menus/${menu.id}`}
                        style={{
                          color: "grey",
                          backgroundColor: "transparent",
                          border: "1px solid grey",
                        }}
                      >
                        Open
                      </Button>

                      <Button
                        className="mt-2 menu-button"
                        style={{
                          color: "grey",
                          backgroundColor: "transparent",
                          border: "1px solid grey",
                        }}
                      >
                        Update
                      </Button>
                      <Button
                        onClick={() => setOpen(menu.id)}
                        className="mt-2 trash"
                        style={{
                          color: "grey",
                          backgroundColor: "transparent",
                          border: "1px solid grey",
                        }}
                      >
                        Delete
                        {/* <Icon name="trash" size="small" /> */}
                      </Button>
                    </Card.Description>
                  </Card.Content>
                </Card>
              ))}
            </Card.Group>
          </Grid.Column>

          <Grid.Column width={4}>
            {unconfirmedOrders.map((order) => (
              <Message key={order.id}>
                <b>Order ID: {order.id}</b>
                <br />
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
                {selectedOrderItems.map((orderItem) => (
                  <Table.Row key={orderItem.id}>
                    <Table.Cell>{orderItem.meal.title}</Table.Cell>
                    <Table.Cell>{orderItem.quantity}</Table.Cell>
                    <Table.Cell>Action</Table.Cell>
                  </Table.Row>
                ))}
              </Table.Body>
            </Table>
          </Grid.Column>
        </Grid.Row>
      </Grid>
    </Container>
  );
}
