import React, { useState, useEffect } from "react";
import Map_ser from "../services/Map_ser";

function Map() {
  const [chunks, setChunks] = useState([]);

  useEffect(() => {
    Map_ser.getID_position().then((res) => {
      const newChunks = [];
      for (let i = 0; i < res.length; i += 3) {
        newChunks.push(res.slice(i, i + 3));
      }
      setChunks(newChunks);
    });
  }, []);

  return (
    <div className="Infor">
      <div className="Gate"><button>Gate</button></div>
      <button className={`venue2 ${chunks[0] === "100" ? "active" : ""}`}>
        Venue 2
      </button>
      <button className={`venue1 ${chunks[1] === "010" ? "active" : ""}`}>
        Venue 1
      </button>
      <button className={`venue3 ${chunks[2] === "001" ? "active" : ""}`}>
        Venue 3
      </button>
    </div>
  );
}

export default Map;