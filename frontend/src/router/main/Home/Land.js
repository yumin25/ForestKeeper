import axios from "axios";
import { useEffect, useState } from "react";
import landing from "../../../res/img/landing.png";

function Land() {
  const [userNum, setUserNum] = useState(0);
  const [dist, setDist] = useState(0);
  const welcome = () => {
    axios.get("https://k6a306.p.ssafy.io/api/plogging/welcome").then((res) => {
      setUserNum(res.data.numberOfUsers);
      setDist(res.data.totalDistance);
    });
  };

  useEffect(() => {
    welcome();
  }, []);
  return (
    <div style={{ margin: "20vh" }}>
      <div
        style={{
          display: "flex",
          alignItems: "center",
          flexDirection: "column",
        }}
      >
        <img style={{ width: "85vw" }} src={landing} alt="logo" />
        <div style={{ margin: "3vh 0 5vh", width: "80vw", display: "flex", flexDirection: "column" }}>
          <div>
            <span style={{ margin: "0", fontSize: "3.5vh", fontWeight: "500", color: "black" }}>지금 </span>
            <span style={{ margin: "0", fontSize: "4vh", fontWeight: "700", color: "black" }}>{userNum}</span>
            <span style={{ margin: "0", fontSize: "3.5vh", fontWeight: "500", color: "black" }}>명이</span>
          </div>
          <div>
            <span style={{ margin: "0", fontSize: "4vh", fontWeight: "700", color: "black" }}>{dist}</span>
            <span style={{ margin: "0", fontSize: "3.5vh", fontWeight: "500", color: "black" }}>km를 걸으며</span>
          </div>
          <div style={{ margin: "0", fontSize: "3.5vh", fontWeight: "500", color: "black" }}>환경을 살리고 있습니다!</div>
        </div>
        <button
          style={{
            backgroundColor: "#37CD8D",
            border: "none",
            borderRadius: "2rem",
            width: "14rem",
            height: "2.5rem",
            color: "white",
            marginBottom: "0.5rem",
          }}
          onClick={() => (document.location.href = `/accounts/login`)}
        >
          함께 하러 가기
        </button>
      </div>
    </div>
  );
}

export default Land;
