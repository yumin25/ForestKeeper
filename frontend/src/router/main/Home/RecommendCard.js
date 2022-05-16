import React from "react";
import FmdGoodIcon from "@mui/icons-material/FmdGood";
import mtn_icon from "../../../res/img/mtn_icon.png";
const RecommendCard = ({ mountain, near }) => {
  return (
    <div
      style={{
        borderRadius: "15px",
        background: "#EAF9E6",
        paddingTop: "1.5vh",
        paddingBottom: "1.5vh",
        paddingLeft: "6vw",
        height: "10vh",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-between",
      }}
    >
      <div>
        <div
          style={{
            fontSize: "2.5vh",
            color: "rgb(138, 188, 154)",
            marginBottom: "3%",
          }}
        >
          <strong>{mountain.name}</strong>{" "}
        </div>
        <div
          style={{
            display: "flex",
            fontSize: "2vh",
            color: "rgb(142, 142, 146)",
          }}
        >
          <img style={{ height: "2vh", width: "2vh" }} src={mtn_icon} />
          <div style={{ fontSize: "1.8vh" }}>
            {mountain.address.split(" ")[0] +
              " " +
              mountain.address.split(" ")[1]}{" "}
          </div>
        </div>
      </div>

      <div style={{ fontSize: "1.8vh", color: "rgb(142, 142, 146)" }}>
        {near ? `거리: ${mountain.value} km` : <>높이: {mountain.value} m</>}
      </div>
    </div>
  );
};

export default RecommendCard;
