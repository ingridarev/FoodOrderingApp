import React, { useState, useContext, history } from "react";
import { useHref, useNavigate, NavLink } from "react-router-dom";
import { AuthContext } from '../AuthContext';

import {
  Button,
  Form,
  Grid,
  Segment,
  Message,
  Container,
  Header,
  Image,
} from "semantic-ui-react";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function CreateUserPage() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [user, setUser] = useState("");
  const [role, setRole] = useState("");
  const navigate = useNavigate();
  const { appState, setAppState } = useContext(AuthContext);

  const [state, setState] = useState({
    userName: "",
    password: "",
    loading: false,
  });

  const listUrl = useHref("/login");

  const handleUserNameChange = (event) => {
    setUserName(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const createUser = () => {
    fetch("/api/v1/users/user/create", {
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        userName: userName,
        password: password,
      }),
    })
      .then((response) => applyResult(response))
      .catch((error) => {
        console.error("Error creating user:", error);
      });
  };
  

  const applyResult = (response) => {
    if (response.status === 404) {
      console.error("Endpoint not found");
      setError("Endpoint not found");  // Set the error message to be displayed
      return;
    }
  
    response.json().then((data) => {
      if (response.ok) {
        console.log("User created successfully:", data);
        // Redirect to another page or perform any other action
      } else {
        console.error("Failed to create user:", data.message);
        setError(data.message);  // Set the error message to be displayed
      }
    });
  };
  
  

  return (
    <Container>
      <Grid
        textAlign="center"
        style={{ height: "100vh" }}
        verticalAlign="middle"
      >
        <Grid.Column style={{ maxWidth: 450 }}>
          <Form size="large">
            <Form.Input >Susikurkite savo vartotoją</Form.Input>
            <Segment stacked>
              <Form.Input
                fluid
                icon="user"
                iconPosition="left"
                placeholder="Vartotojo vardas"
                value={userName}
                onChange={handleUserNameChange}
              />
              <Form.Input
                fluid
                icon="lock"
                iconPosition="left"
                placeholder="Slaptažodis"
                type="password"
                value={password}
                onChange={handlePasswordChange}
              />
              <Button
                text-color="white"
                color="black"
                fluid
                size="large"
                onClick={createUser}
              >
                Sukurti vartotoją
              </Button>
            </Segment>
          </Form>
        </Grid.Column>
      </Grid>
    </Container>
  );
}