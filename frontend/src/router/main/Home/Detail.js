import { fontFamily } from "@mui/system";
import React, { useEffect, useState } from "react";
import back from "../../../res/img/back.png";
import check from "../../../res/img/check.png";
import checked from "../../../res/img/checked.png";
import Bar from "./Bar";
import Star from "./Star";
import About from "./About";

function Detail() {
  const [isVisited, setIsVisited] = useState(false);
  const [tab, setTab] = useState("star");
  const [Info, setInfo] = useState({
    name: "관악산",
    howMany: "589",
    distance: "454225",
  });
  function getMountainInfo() {}
  return (
    <>
      <div style={{ height: "92.5vh", marginLeft: "6vw", marginRight: "6vw" }}>
        <div id="header" style={{ height: "8vh" }}>
          <div>
            <img
              style={{
                marginTop: "4vh",
                width: "5vw",
                height: "5vw",
              }}
              src={back}
            />
          </div>
        </div>

        <div
          style={{
            height: "6vh",
            display: "flex",
            marginBottom: "1vh",
          }}
        >
          <div
            id="mountainName"
            style={{
              marginLeft: "3vw",
              marginBottom: "2.5vh",
              fontSize: "3.5vh",
              fontWeight: "light",
            }}
          >
            {Info.name}
          </div>

          <div>
            {isVisited == false ? (
              <img
                style={{
                  marginLeft: "2vw",
                  marginTop: "1.5vh",
                  width: "6vw",
                  height: "6vw",
                }}
                src={check}
              />
            ) : (
              <img
                style={{
                  width: "6vw",
                  height: "6vw",
                }}
                src={checked}
              />
            )}
          </div>
        </div>

        {/* 15vh */}
        <div
          id="banner"
          style={{
            height: "8vh",
            background: "#EAF9E6",
            borderRadius: 15,
            marginBottom: "4vh",
            paddingTop: "2vh",
            paddingLeft: "5vw",
            paddingRight: "5vw",
          }}
        >
          <div id="text" style={{ color: "#8ABC9A" }}>
            <div style={{ fontSize: "1.5vh" }}>누적 방문자/거리</div>
            <div
              style={{ display: "flex", fontSize: "3.5vh", fontWeight: "bold" }}
            >
              <div style={{ marginRight: "5vw" }}>{Info.howMany} 명</div>
              <div>{Info.distance} km</div>
            </div>
          </div>
        </div>

        <div id="tabs" style={{ marginLeft: "2vw", height: "60vh" }}>
          {tab == "star" ? (
            <>
              <div
                id="tab"
                style={{
                  fontSize: "2vh",
                  display: "flex",
                  marginBottom: "1.5vh",
                }}
              >
                <div
                  style={{
                    fontWeight: 900,
                    color: "#002831",
                    marginRight: "2.5vw",
                  }}
                >
                  명예의 전당
                </div>
                <div onClick={() => setTab("about")}>About {Info.name}</div>
              </div>
              <Star></Star>
            </>
          ) : (
            <>
              <div
                id="tab"
                style={{
                  fontSize: "2vh",
                  display: "flex",
                  marginBottom: "1.5vh",
                }}
              >
                <div
                  onClick={() => setTab("star")}
                  style={{ marginRight: "2.5vw" }}
                >
                  명예의 전당
                </div>
                <div
                  style={{
                    fontWeight: 900,
                    color: "#002831",
                  }}
                >
                  About {Info.name}
                </div>
              </div>
              <About></About>
            </>
          )}
        </div>
      </div>
      <Bar></Bar>
    </>
  );
}
export default Detail;
