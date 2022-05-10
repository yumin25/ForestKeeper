import React, { useEffect, useState } from "react";
import { RenderAfterNavermapsLoaded, NaverMap, Marker } from "react-naver-maps";
import "./Home.css";
import { Route, Routes } from "react-router-dom";
import Send from "../../../config/Send";
import axios from "axios";

function NaverMapAPI() {
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
      center={{ lat: 37.554722, lng: 126.970833 }} // 지도 초기 위치
      defaultZoom={13} // 지도 초기 확대 배율
    ></NaverMap>
  );
}

function Trash() {
  axios.defaults.withCredentials = true;
  function getTrash() {
    Send.get(
      `/plogging/trash`,
      {},
      {
        withCredentials: true,
      }
    )
      .then((res) => {
        console.log(res);
      })
      .catch((e) => {
        console.log(e);
      });
  }
  return (
    <>
      <div id="map">
        <button
          onClick={() => getTrash()}
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
          쓰레기통 찾기
        </button>

        <RenderAfterNavermapsLoaded
          ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
          error={<p>Maps Load Error</p>}
          loading={<p>Maps Loading...</p>}
        >
          <NaverMapAPI></NaverMapAPI>
        </RenderAfterNavermapsLoaded>
      </div>
    </>
  );
}
export default Trash;
