import React from "react";
import FmdGoodIcon from "@mui/icons-material/FmdGood";

const RecommendCard = ({ mountain, near }) => {
  return (
    <div
      style={{
        borderRadius: "15px",
        background: "#EAF9E6",
        padding: "15px",
        height: "100px",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
      }}
    >
      <div>
        <div style={{ fontSize: "1.5em", color:"rgb(138, 188, 154)", marginBottom:"3%" }}>
          <strong>{mountain.name}</strong>{" "}
        </div>
        <div style={{ display: "flex", fontSize: "0.9em", color:"rgb(142, 142, 146)" }}>
          <FmdGoodIcon fontSize="small" />
          <div>
            {mountain.address.split(" ")[0] +
              " " +
              mountain.address.split(" ")[1]}{" "}
          </div>
        </div>
      </div>

      <div style={{  fontSize: "0.8em", color:"rgb(142, 142, 146)" }}>
        {near ? `거리: ${mountain.value} km` : <>높이: {mountain.value} m</>}
      </div>
    </div>
  );
};

export default RecommendCard;
