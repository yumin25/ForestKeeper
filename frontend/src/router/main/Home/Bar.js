import React, { useState } from "react";
import { Route, Routes } from "react-router-dom";
import home from "../../../res/img/home.png";
import home_clicked from "../../../res/img/home_clicked.png";
import user from "../../../res/img/user.png";
import user_clicked from "../../../res/img/user_clicked.png";
import hiking from "../../../res/img/hiking.png";
import hiking_clicked from "../../../res/img/hiking_clicked.png";
import { NavLink } from "react-router-dom";
function NavBar() {
  const [now, setNow] = useState("HOME");
  function goHome() {
    setNow("HOME");
  }
  function goHiking() {
    setNow("HIKING");
  }
  function goUser() {
    setNow("USER");
  }
  return (
    <div>
      <div style={{ display: "flex", height: "8vh" }}>
        <div id="home" style={{ width: "33vw" }} onClick={() => goHome()}>
          {now == "HOME" ? (
            <NavLink to={"/"}>
              <img
                style={{
                  marginTop: "2vh",
                  marginLeft: "13vw",
                  width: "7vw",
                  height: "3.5vh",
                }}
                src={home_clicked}
              />
            </NavLink>
          ) : (
            <NavLink to={"/"}>
              <img
                style={{
                  marginTop: "2vh",
                  marginLeft: "13vw",
                  width: "7vw",
                  height: "3.5vh",
                }}
                src={home}
              />
            </NavLink>
          )}
        </div>
        <div id="hiking" style={{ width: "33vw" }} onClick={() => goHiking()}>
          {now == "HIKING" ? (
            <NavLink to="/plogging">
              <img
                style={{
                  marginTop: "2vh",
                  marginLeft: "13vw",
                  width: "7vw",
                  height: "3.5vh",
                }}
                src={hiking_clicked}
              />
            </NavLink>
          ) : (
            <NavLink to="/plogging">
              <img
                style={{
                  marginTop: "2vh",
                  marginLeft: "13vw",
                  width: "7vw",
                  height: "3.5vh",
                }}
                src={hiking}
              />
            </NavLink>
          )}
        </div>
        <div id="user" style={{ width: "33vw" }} onClick={() => goUser()}>
          {now == "USER" ? (
            <NavLink to="/user">
              <img
                style={{
                  marginTop: "2vh",
                  marginLeft: "13vw",
                  width: "7vw",
                  height: "3.5vh",
                }}
                src={user_clicked}
              />
            </NavLink>
          ) : (
            <NavLink to="/user">
              <img
                style={{
                  marginTop: "2vh",
                  marginLeft: "13vw",
                  width: "7vw",
                  height: "3.5vh",
                }}
                src={user}
              />
            </NavLink>
          )}
        </div>
      </div>
    </div>
  );
}
export default NavBar;
