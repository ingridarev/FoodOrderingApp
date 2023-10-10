import React from "react";
import plateImage from '../images/plate.jpg';
import { Image } from "semantic-ui-react";


export function HomePage() {
    return(<div>
        <Image src={plateImage}></Image>
    </div>);
}