import { useState, useContext } from "react";
import { useParams, useHref } from "react-router-dom";
import { Button, Form, Grid } from "semantic-ui-react";
import { AuthContext } from "../AuthContext";

const JSON_HEADERS = {
  "Content-Type": "application/json",
};

export function AddMeal() {
  const { menuId } = useParams(); // Update to menuId
  const { appState } = useContext(AuthContext);

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");

  const listUrl = useHref(`/menus/${menuId}`); // Update URL
  const [hide, setHide] = useState(false);

  const createMeal = () => {
    fetch(`/api/v1/meals/${menuId}`, { // Update fetch URL
      method: "POST",
      headers: JSON_HEADERS,
      body: JSON.stringify({
        title,
        description,
        menuId: Number(menuId), // Update to menuId and convert to a number
      }),
    })
      .then(applyResult)
      .then(() => (window.location = listUrl));
  };

  const applyResult = (result) => {
    const clear = () => {
      setHide(true);
    };
    if (result.ok) {
      clear();
    } else {
      window.alert("Nepavyko sukurt: " + result.status);
    }
  };
  return (
    <div>
      <Grid columns={3}>
        <Grid.Column width={5}></Grid.Column>
        <Grid.Column width={6}>
          <Form>
            <Form.Field>
              <label>Meal title:</label>
              <input
                placeholder="Meal title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              />
            </Form.Field>
            <Form.Field>
              <label>Meal description:</label>
              <input
                placeholder="Meal description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </Form.Field>
            <Button
              type="submit"
              primary
              style={{
                color: "grey",
                backgroundColor: "transparent",
                border: "1px solid grey",
              }}
              onClick={(e) => createMeal(e)}
            >
              Finish
            </Button>
          </Form>
        </Grid.Column>
        <Grid.Column width={5}></Grid.Column>
      </Grid>
    </div>
  );
}
