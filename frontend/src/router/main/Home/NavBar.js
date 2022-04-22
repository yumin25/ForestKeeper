import React, { useState } from "react";
import home from "../../../res/img/home.png";
import home_clicked from "../../../res/img/home_clicked.png";
import user from "../../../res/img/user.png";
import user_clicked from "../../../res/img/user_clicked.png";
import hiking from "../../../res/img/hiking.png";
import hiking_clicked from "../../../res/img/hiking_clicked.png";

function NavBar() {
  const [now, setNow] = useState("HOME");
  function goHome() {
    setNow("HOME");
    document.location.href = "/";
  }
  function goHiking() {
    setNow("HIKING");
    document.location.href = "/";
  }
  function goUser() {
    setNow("USER");
    document.location.href = "/login";
  }
  return (
    <div style={{ display: "flex" }}>
      <div id="home" style={{ width: "33vw" }} onClick={() => goHome()}>
        {now == "HOME" ? (
          <img
            style={{
              marginTop: "2vh",
              marginLeft: "13vw",
              width: "7vw",
              height: "3.5vh",
            }}
            src={home_clicked}
          />
        ) : (
          <img
            style={{
              marginTop: "2vh",
              marginLeft: "13vw",
              width: "7vw",
              height: "3.5vh",
            }}
            src={home}
          />
        )}
      </div>
      <div
        id="hiking"
        style={{ width: "33vw", height: "12vh" }}
        onClick={() => goHiking()}
      >
        {now == "HIKING" ? (
          <img
            style={{
              marginTop: "2vh",
              marginLeft: "13vw",
              width: "7vw",
              height: "3.5vh",
            }}
            src={hiking_clicked}
          />
        ) : (
          <img
            style={{
              marginTop: "2vh",
              marginLeft: "13vw",
              width: "7vw",
              height: "3.5vh",
            }}
            src={hiking}
          />
        )}
      </div>
      <div
        id="user"
        style={{ width: "33vw", height: "12vh" }}
        onClick={() => goUser()}
      >
        {now == "USER" ? (
          <img
            style={{
              marginTop: "2vh",
              marginLeft: "13vw",
              width: "7vw",
              height: "3.5vh",
            }}
            src={user_clicked}
          />
        ) : (
          <img
            style={{
              marginTop: "2vh",
              marginLeft: "13vw",
              width: "7vw",
              height: "3.5vh",
            }}
            src={user}
          />
        )}
      </div>
    </div>
  );
}
export default NavBar;
