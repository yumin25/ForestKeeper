import axios from "axios";
import { useEffect, useState } from "react";
import shortlogo from "../res/img/logo_temp.png";
import landing from "../res/img/landing-removebg.png";

function Download() {
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
    <div style={{ fontFamily: "'Noto Sans KR', sans-serif" }}>
      <div style={{ position: "sticky", top: "0", height: "10vh", backgroundColor: "white" }}>
        <div style={{ margin: "auto 10vw", height: "10vh", display: "flex", justifyContent: "space-between" }}>
          <div style={{ margin: "auto 0", display: "flex", justifyContent: "space-around" }}>
            <img src={shortlogo} alt="" style={{ height: "7vh" }} />
            <div style={{ margin: "auto 0", fontSize: "3vh", fontWeight: "700" }}>Forest Keeper</div>
          </div>
          <div style={{ margin: "auto 0", display: "flex", justifyContent: "space-around", width: "15vw" }}>
            <div>DOWNLOAD</div>
            <div>DOCS</div>
          </div>
        </div>
      </div>
      <div style={{ padding: "2vh 5vw", height: "50vh", backgroundColor: "#B8DEB3", display: "flex" }}>
        <div style={{ margin: "7vh 5vh", width: "50vw" }}>
          <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <div style={{ margin: "0", fontSize: "5vh", fontWeight: "700", color: "black" }}>등산과 플로깅의 만남</div>
            <div style={{ margin: "0", fontSize: "5vh", fontWeight: "700", color: "black" }}>Forest Keeper</div>
            <button
              style={{
                backgroundColor: "#37CD8D",
                border: "none",
                borderRadius: "2rem",
                width: "15vw",
                height: "5vh",
                color: "white",
                marginTop: "2vh",
              }}
              onClick={() => (document.location.href = `/accounts/login`)}
            >
              <div style={{ margin: "0", fontSize: "3vh", fontWeight: "700", color: "white" }}>동행하기</div>
            </button>
            <div style={{ marginTop: "10vh" }}>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>지금 </span>
              <span style={{ margin: "0", fontSize: "3vh", fontWeight: "700", color: "black" }}>{userNum}</span>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>명이 </span>
              <span style={{ margin: "0", fontSize: "3vh", fontWeight: "700", color: "black" }}>{dist}</span>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>km를 걸으며 </span>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>환경을 살리고 있습니다!</span>
            </div>
          </div>
        </div>
        <img src={landing} alt="" style={{ float: "right" }} />
      </div>
      <div style={{ height: "40vh", backgroundColor: "white" }}></div>
      <div style={{ height: "40vh", backgroundColor: "lightgrey" }}></div>
      <div style={{ height: "40vh", backgroundColor: "white" }}></div>
      <div style={{ height: "20vh", backgroundColor: "black" }}></div>
    </div>
  );
}
export default Download;
