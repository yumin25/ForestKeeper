import React, { useEffect, useState, useRef } from "react";
import { RenderAfterNavermapsLoaded, NaverMap, Marker } from "react-naver-maps";
import "./Home.css";
import { Route, Routes } from "react-router-dom";
import Send from "../../../config/Send";
function NaverMapAPI({ trashList }) {
  var HOME_PATH = window.HOME_PATH || ".";
  const navermaps = window.naver.maps;
  const [lat, setLat] = useState(37.554722);
  const [long, setLong] = useState(126.970833);
  var map = new navermaps.Map("map", {
    center: new navermaps.LatLng(lat, long),
    zoom: 13,
    mapTypeId: navermaps.MapTypeId.NORMAL,
  });

  var bounds = map.getBounds(),
    southWest = bounds.getSW(),
    northEast = bounds.getNE(),
    lngSpan = northEast.lng() - southWest.lng(),
    latSpan = northEast.lat() - southWest.lat();
  useEffect(() => {
    displayMarker();
  }, [lat, long]);

  useEffect(() => {
    setLat(map.getCenter()._lat);
    setLong(map.getCenter()._lng);
  }, [map.getCenter()]);

  function displayMarker() {
    var position = new navermaps.LatLng(
      southWest.lat() + latSpan * Math.random(),
      southWest.lng() + lngSpan * Math.random()
    );
    let num = 0;
    for (var i = 0; i < trashList.length; i++) {
      if (
        trashList[i].latitude >= map.getBounds()._min._lat &&
        trashList[i].latitude <= map.getBounds()._max._lat &&
        trashList[i].longitude >= map.getBounds()._min._lng &&
        trashList[i].longitude <= map.getBounds()._max._lng
      ) {
        num += 1;
        var marker = new navermaps.Marker({
          position: new navermaps.LatLng(
            trashList[i].latitude,
            trashList[i].longitude
          ),
          map: map,
        });
      }
    }
    console.log(num);
  }

  return (
    <div>
      <button
        onClick={displayMarker}
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
          marginLeft: "70vw",
          zIndex: 2,
        }}
      >
        쓰레기통 찾기
      </button>
      <div id="map" style={{ width: "100vw", height: "100vh" }} />
    </div>
  );
}

function Trash() {
  const [trashList, setTrashList] = useState([]);
  useEffect(() => {
    getTrash();
  }, []);

  function getTrash() {
    Send.get(
      `/plogging/trash`,
      {},
      {
        withCredentials: true,
      }
    )
      .then((res) => {
        setTrashList(res.data.list);
        console.log(res);
      })
      .catch((e) => {
        console.log(e);
      });
  }

  function getBounds() {}
  return (
    <>
      <div id="map">
        <RenderAfterNavermapsLoaded
          ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
          error={<p>Maps Load Error</p>}
          loading={<p>Maps Loading...</p>}
        >
          <NaverMapAPI trashList={trashList}></NaverMapAPI>
        </RenderAfterNavermapsLoaded>
      </div>
    </>
  );
}
export default Trash;
