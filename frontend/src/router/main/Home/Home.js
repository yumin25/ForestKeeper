import React, { useState } from "react";
import { RenderAfterNavermapsLoaded, NaverMap } from "react-naver-maps";
import NavBar from "./NavBar";
import "./Home.css";
import search from "../../../res/img/search.png";
function NaverMapAPI() {
  return (
    <NaverMap
      mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
      style={{
        width: "100vw", // 네이버지도 가로 길이
        height: "81vh", // 네이버지도 세로 길이
      }}
      defaultCenter={{ lat: 37.554722, lng: 126.970833 }} // 지도 초기 위치
      defaultZoom={13} // 지도 초기 확대 배율
    />
  );
}

function Home() {
  return (
    <>
      <div>
        <div
          className="top"
          style={{
            position: "relative",
            width: "100vw",
            height: "11vh",
          }}
        ></div>
        <input
          className="input"
          placeholder="산 이름 검색하기"
          style={{
            position: "absolute",
            background: "#EFEFEF",
            width: "90vw",
            height: "5vh",
            left: "5vw",
            right: "5vw",
            top: "4vh",
            borderRadius: 10,
            border: "none",
            paddingLeft: "5vw",
            zIndex: 1,
          }}
        ></input>
        <img
          style={{
            posision: "absolute",
            zIndex: 2,
            width: "6vw",
            height: "6vw",
          }}
          src={search}
        />
      </div>

      <RenderAfterNavermapsLoaded
        ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
        error={<p>Maps Load Error</p>}
        loading={<p>Maps Loading...</p>}
      >
        <NaverMapAPI />
      </RenderAfterNavermapsLoaded>
      <div className="nav" style={{ width: "100vw", height: "8vh" }}>
        <NavBar></NavBar>
      </div>
    </>
  );
}

export default Home;
