import React, { useState, useContext, history } from "react";
import { useHref, useNavigate, NavLink } from "react-router-dom";
import AuthContext from "../AuthContext";
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

export function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [user, setUser] = useState("");
  const [role, setRole] = useState("");
  const navigate = useNavigate();
  const { appState, setAppState } = useContext(AuthContext);

  const [state, setState] = useState({
    username: "",
    password: "",
    loading: false,
  });

  const listUrl = useHref("/menus");

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleSubmit = (event) => {
    if (!appState.isAuthenticated) {
      setAppState({ type: "LOADING", value: true });
      event.preventDefault();

      const credentials = {
        username: username,
        password: password,
      };

      fetch("api/v1/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          // 'Accept': 'application/json'
        },
        body: JSON.stringify(credentials),
      })
        .then(applyResult)
        .catch((error) => {
          setError("An error occurred. Please try again later.");
        });
    } else {
      alert("Jau prisijungta");
      // setAppState({ type: "AUTHENTICATED", value: true })
    }
  };

  const applyResult = (result) => {
    const clear = () => {
      clearForm();
    };
    if (result.ok) {
      setAppState({ type: "AUTHENTICATED", value: true });
      navigate("/menus");
    } else {
      setError("Login failed. Please try again.");
    }
  };
  const clearForm = () => {
    setState({
      ...state,
      user: "",
      password: null,
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
            <Segment stacked>
              <Form.Input
                fluid
                icon="user pink"
                iconPosition="left"
                placeholder="Vartotojo vardas"
                value={username}
                onChange={handleUsernameChange}
              />
              <Form.Input
                fluid
                icon="lock pink"
                iconPosition="left"
                placeholder="Slaptažodis"
                type="password"
                value={password}
                onChange={handlePasswordChange}
              />
              <Button
                text-color="white"
                color="pink"
                fluid
                size="large"
                onClick={handleSubmit}
              >
                Prisijunkite
              </Button>
              <Button
                className="mt-2 "
                style={{
                  color: "grey",
                  backgroundColor: "transparent",
                  border: "1px solid grey",
                }}
                fluid
                size="large"
                as={NavLink}
                exact
                to={"/users/user/create"}
              >
                Create User
              </Button>
            </Segment>
          </Form>
        </Grid.Column>
      </Grid>
    </Container>
  );
}