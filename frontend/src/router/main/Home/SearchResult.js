import React, { useEffect, useState } from "react";
import { RenderAfterNavermapsLoaded, NaverMap, Marker } from "react-naver-maps";
import "./Home.css";
import { Route, Routes } from "react-router-dom";

function NaverMapAPI({ myLocation }) {
  const navermaps = window.naver.maps;

  return (
    <NaverMap
      mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
      style={{
        width: "100vw", // 네이버지도 가로 길이
        height: "82vh", // 네이버지도 세로 길이
        position: "relative",
        zIndex: 1,
      }}
      center={{ lat: myLocation.latitude, lng: myLocation.longitude }} // 지도 초기 위치
      defaultZoom={13} // 지도 초기 확대 배율
    >
      {myLocation.latitude !== 37.554722 &&
        myLocation.longitude !== 126.970833 && (
          <Marker
            key={1}
            position={
              new navermaps.LatLng(myLocation.latitude, myLocation.longitude)
            }
          />
        )}
    </NaverMap>
  );
}

function SearchResult({ getLocation, myLocation }) {
  return (
    <>
      <div id="map">
        <button
          onClick={getLocation}
          style={{
            position: "absolute",
            height: "5vh",
            width: "18vw",
            borderRadius: 15,
            border: "none",
            boxShadow: "0px 5px 10px 2px darkgray",
            background: "white",
            color: "#37CD8D",
            marginTop: "1.5vh",
            marginLeft: "5vw",
            zIndex: 2,
          }}
        >
          내 위치
        </button>

        <RenderAfterNavermapsLoaded
          ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
          error={<p>Maps Load Error</p>}
          loading={<p>Maps Loading...</p>}
        >
          <NaverMapAPI myLocation={myLocation}></NaverMapAPI>
        </RenderAfterNavermapsLoaded>
      </div>
    </>
  );
}
export default SearchResult;
