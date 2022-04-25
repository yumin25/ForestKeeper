import React, { useState } from "react";
import { RenderAfterNavermapsLoaded, NaverMap } from "react-naver-maps";
import Bar from "./Bar";
import "./Home.css";
import search from "../../../res/img/search.png";
import { Route, Routes } from "react-router-dom";
import SearchInput from "./SearchInput";
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
  const [keyword, setKeyword] = useState("");

  function goSearch() {
    if (keyword !== "" && keyword != undefined) {
      document.location.href = `/search/${keyword}`;
    } else {
      alert("검색어를 입력해주세요.");
    }
  }
  function keywordHandler(event) {
    setKeyword(event.target.value);
  }
  const onSubmit = (event) => {
    if (event.key === "Enter") {
      console.log(keyword);
      if (keyword !== "" && keyword !== undefined) {
        document.location.href = `/search/${keyword}`;
      } else {
        alert("검색어를 입력해주세요.");
      }
    }
  };

  return (
    <>
      <SearchInput
        keyword={keyword}
        keywordHandler={keywordHandler}
        onSubmit={onSubmit}
        goSearch={goSearch}
      ></SearchInput>

      <div id="map">
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
      </div>
    </>
  );
}

export default Home;
