import React, { useState } from "react";
import { RenderAfterNavermapsLoaded, NaverMap } from "react-naver-maps";
import Bar from "./Bar";
import "./Home.css";
import search from "../../../res/img/search.png";
import { Route, Routes } from "react-router-dom";
function NaverMapAPI() {
  return (
    <NaverMap
      mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
      style={{
        width: "100vw", // 네이버지도 가로 길이
        height: "82vh", // 네이버지도 세로 길이
        position: "relative",
        zIndex: 1,
      }}
      defaultCenter={{ lat: 37.554722, lng: 126.970833 }} // 지도 초기 위치
      defaultZoom={13} // 지도 초기 확대 배율
    />
  );
}

function Home() {
  return (
    <>
      <div style={{ height: "7vh" }}>
        <div style={{ display: "flex", marginTop: "3vh", marginBottom: "0.5vh" }}>
          <div>
            <input
              className="input"
              placeholder="산 이름 검색하기"
              style={{
                background: "#EFEFEF",
                width: "77vw",
                height: "5vh",
                borderRadius: 10,
                border: "none",
                paddingLeft: "5vw",
                marginBottom: "1vh",
                marginLeft: "4.5vw",
              }}
            ></input>
          </div>
          <div>
            <img
              style={{
                marginTop: "1.2vh",
                width: "6vw",
                height: "6vw",
                marginLeft: "2vw",
              }}
              src={search}
            />
          </div>
        </div>
      </div>

      <button
        onClick={() => (document.location.href = "/recommend")}
        style={{
          position: "absolute",
          height: "5vh",
          width: "28vw",
          borderRadius: 15,
          border: "none",
          boxShadow: "0px 5px 10px 2px darkgray",
          background: "#37CD8D",
          color: "white",
          marginTop: "1.5vh",
          marginLeft: "5vw",
          zIndex: 2,
        }}
      >
        추천 등산로
      </button>

      <RenderAfterNavermapsLoaded
        ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
        error={<p>Maps Load Error</p>}
        loading={<p>Maps Loading...</p>}
      >
        <NaverMapAPI />
      </RenderAfterNavermapsLoaded>
    </>
  );
}

export default Home;
