import { fontFamily } from "@mui/system";
import React, { useEffect, useState } from "react";
import back from "../../../../res/img/back.png";
import check from "../../../../res/img/check.png";
import checked from "../../../../res/img/checked.png";
import Bar from "../Bar";
import Star from "./Star";
import About from "./About";
import Home from "./Home";

function Detail() {
  const [isVisited, setIsVisited] = useState(false);
  const [tab, setTab] = useState("home");
  const [Info, setInfo] = useState({
    name: "북악산",
    address: "서울특별시  종로구 청운동 ",
    admin: "종로구청",
    tel: "02-2148-1114",
    description:
      "북악산은 서울의 주산으로 경복궁 뒤쪽에... 전망도 빼어나다.\n\n.",
    height: 342.5,
    isFamous: 0,
    famousDescription: "",
    lat: 37.5930556,
    lng: 126.9733333,
  });
  function getMountainInfo() {}
  return (
    <>
      <div style={{ height: "92.5vh" }}>
        <div
          id="header"
          style={{ marginLeft: "6vw", marginRight: "6vw", height: "9vh" }}
        >
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
          id="mountainName"
          style={{
            marginLeft: "6vw",
            marginRight: "6vw",
            height: "5vh",
            display: "flex",
          }}
        >
          <div
            style={{
              marginLeft: "3vw",
              fontSize: "3.5vh",
              fontWeight: "500",
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
                  marginLeft: "2vw",
                  marginTop: "1.5vh",
                  width: "6vw",
                  height: "6vw",
                }}
                src={checked}
              />
            )}
          </div>
        </div>

        <div
          id="address"
          style={{
            marginLeft: "9vw",
            marginRight: "6vw",
            marginBottom: "2.2vh",
            fontSize: "1.8vh",
            color: "#69696C",
          }}
        >
          {Info.address}
        </div>

        <div id="tabs" style={{ height: "74vh" }}>
          {tab == "star" ? (
            <>
              <div
                id="tab"
                style={{
                  marginLeft: "9vw",
                  marginRight: "6vw",
                  fontSize: "2vh",
                  display: "flex",
                  marginBottom: "2vh",
                }}
              >
                <div
                  style={{ marginRight: "2.5vw" }}
                  onClick={() => setTab("home")}
                >
                  Home
                </div>
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
          ) : tab == "about" ? (
            <>
              <div
                id="tab"
                style={{
                  marginLeft: "9vw",
                  marginRight: "6vw",
                  fontSize: "2vh",
                  display: "flex",
                  marginBottom: "2vh",
                }}
              >
                <div
                  style={{ marginRight: "2.5vw" }}
                  onClick={() => setTab("home")}
                >
                  Home
                </div>
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
          ) : (
            <>
              <div
                id="tab"
                style={{
                  marginLeft: "9vw",
                  marginRight: "6vw",
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
                  Home
                </div>
                <div
                  onClick={() => setTab("star")}
                  style={{ marginRight: "2.5vw" }}
                >
                  명예의 전당
                </div>
                <div onClick={() => setTab("home")}>About {Info.name}</div>
              </div>

              <Home lat={Info.lat} lng={Info.lng}></Home>
            </>
          )}
        </div>
      </div>
      <Bar></Bar>
    </>
  );
}
export default Detail;
