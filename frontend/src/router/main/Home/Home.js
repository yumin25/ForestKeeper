import React, { useEffect, useState } from "react";
import { RenderAfterNavermapsLoaded, NaverMap, Marker } from "react-naver-maps";
import Bar from "./Bar";
import "./Home.css";
import search from "../../../res/img/search.png";
import { Route, Routes } from "react-router-dom";
import SearchInput from "./SearchInput";
import Map from "./Map";
import SearchList from "./SearchList";
import SearchResult from "./SearchResult";

function Home() {
  const [pageState, setPageState] = useState("home");
  const [keyword, setKeyword] = useState("");
  const [myLocation, setMyLocation] = useState({
    latitude: 37.554722,
    longitude: 126.970833,
  });
  const [mountainCode, setMountainCode] = useState();
  function getLocation() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        console.log(position);
        setMyLocation({
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        });
      });
    } else {
      window.alert("현재위치를 알수 없습니다.");
    }
  }

  function goSearch() {
    if (keyword !== "" && keyword != undefined) {
      setPageState("searchList");
      //document.location.href = `/search/${keyword}`;
    } else {
      alert("검색어를 입력해주세요.");
    }
  }

  function keywordHandler(event) {
    setKeyword(event.target.value);
  }

  function pageStateHandler(param) {
    setPageState(param);
  }
  function mountainCodeHandler(param) {
    setMountainCode(param);
  }

  const onSubmit = (event) => {
    if (event.key === "Enter") {
      console.log(keyword);
      if (keyword !== "" && keyword !== undefined) {
        setPageState("searchList");
      } else {
        alert("검색어를 입력해주세요.");
      }
    }
  };

  return (
    <>
      {pageState == "home" ? (
        <>
          <SearchInput
            keyword={keyword}
            keywordHandler={keywordHandler}
            onSubmit={onSubmit}
            goSearch={goSearch}
          ></SearchInput>
          <Map getLocation={getLocation} myLocation={myLocation}></Map>
        </>
      ) : pageState == "searchList" ? (
        <SearchList
          keyword={keyword}
          keywordHandler={keywordHandler}
          pageStateHandler={pageStateHandler}
          mountainCodeHandler={mountainCodeHandler}
        ></SearchList>
      ) : (
        <>
          <SearchInput
            keyword={keyword}
            keywordHandler={keywordHandler}
            onSubmit={onSubmit}
            goSearch={goSearch}
          ></SearchInput>
          <SearchResult
            getLocation={getLocation}
            myLocation={myLocation}
          ></SearchResult>
        </>
      )}
    </>
  );
}

export default Home;
