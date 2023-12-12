import React from "react";
import { Link } from "react-router-dom";
import BannerImage from "../assets/automated_parking.jpg";
import "../styles/Home.css";

function Home() {
  
  return (
    <div className="home" style={{ backgroundImage: `url(${BannerImage})` }}>
      <div className="headerContainer">
        <h1> AUTOMATED PARKING </h1>
        {/* <p> </p> */}
        <Link to="/map">
          <button> OVERVIEW DIAGRAM </button>
        </Link>
      </div>
    </div>
  );
}

export default Home;