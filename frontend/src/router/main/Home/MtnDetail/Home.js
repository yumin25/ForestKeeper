import React, { useEffect, useState } from "react";
import { RenderAfterNavermapsLoaded, NaverMap, Marker } from "react-naver-maps";
import { Route, Routes } from "react-router-dom";
import axios from "axios";
function NaverMapAPI({ lat, lng, handleMenu }) {
  const navermaps = window.naver.maps;

  return (
    <>
      <button
        onClick={() => handleMenu("teamList")}
        style={{
          position: "absolute",
          zIndex: 2,
          background: "#37CD8D",
          color: "white",
          width: "54vw",
          height: "5vh",
          border: "none",
          borderRadius: 15,
          marginLeft: "23vw",
          top: "85vh",
        }}
      >
        함께 플로깅할 팀원 구하기
      </button>
      <NaverMap
        mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
        style={{
          width: "88vw", // 네이버지도 가로 길이
          height: "58.5vh", // 네이버지도 세로 길이
          position: "relative",
          marginLeft: "6vw",
          zIndex: 1,
        }}
        center={{ lat: lat, lng: lng }} // 지도 초기 위치
        defaultZoom={13} // 지도 초기 확대 배율
      >
        <Marker key={1} position={new navermaps.LatLng(lat, lng)} />
      </NaverMap>
    </>
  );
}

function Home({ visiter, distance, lat, lng, handleMenu }) {
  return (
    <>
      {/* 15vh */}
      <div
        id="banner"
        style={{
          height: "8vh",
          background: "#EAF9E6",
          borderRadius: 15,
          marginLeft: "6vw",
          marginRight: "6vw",
          marginBottom: "2vh",
          paddingTop: "2vh",
          paddingLeft: "5vw",
          paddingRight: "5vw",
        }}
      >
        <div id="text" style={{ color: "#8ABC9A" }}>
          <div style={{ fontSize: "1.5vh" }}>누적 방문 횟수/거리</div>
          <div
            style={{ display: "flex", fontSize: "3.5vh", fontWeight: "bold" }}
          >
            <div style={{ marginRight: "5vw" }}>{visiter}명</div>
            <div> {distance}km</div>
          </div>
        </div>
      </div>
      <div id="map">
        <RenderAfterNavermapsLoaded
          ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
          error={<p>Maps Load Error</p>}
          loading={<p>Maps Loading...</p>}
        >
          <NaverMapAPI
            lat={lat}
            lng={lng}
            handleMenu={handleMenu}
          ></NaverMapAPI>
        </RenderAfterNavermapsLoaded>
      </div>
    </>
  );
}
export default Home;
