import React, { useEffect, useState } from "react";
import { RenderAfterNavermapsLoaded, NaverMap, Marker } from "react-naver-maps";
import { Route, Routes } from "react-router-dom";

function NaverMapAPI({ lat, lng }) {
  const navermaps = window.naver.maps;

  return (
    <NaverMap
      mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
      style={{
        width: "100vw", // 네이버지도 가로 길이
        height: "55.5vh", // 네이버지도 세로 길이
        position: "relative",
        zIndex: 1,
      }}
      center={{ lat: lat, lng: lng }} // 지도 초기 위치
      defaultZoom={13} // 지도 초기 확대 배율
    >
      <Marker key={1} position={new navermaps.LatLng(lat, lng)} />
    </NaverMap>
  );
}

function Home({ lat, lng }) {
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
            <div style={{ marginRight: "5vw" }}>명</div>
            <div> km</div>
          </div>
        </div>
      </div>
      <div id="map">
        <RenderAfterNavermapsLoaded
          ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
          error={<p>Maps Load Error</p>}
          loading={<p>Maps Loading...</p>}
        >
          <NaverMapAPI lat={lat} lng={lng}></NaverMapAPI>
        </RenderAfterNavermapsLoaded>
      </div>
    </>
  );
}
export default Home;
