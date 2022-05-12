import React, { useEffect, useState } from "react";
import { RenderAfterNavermapsLoaded, NaverMap, Marker } from "react-naver-maps";
import Bar from "./Bar";
import "./Home.css";
import search from "../../../res/img/search.png";
import { Route, Routes } from "react-router-dom";
import SearchInput from "./SearchInput";
import Map from "./Map";
import SearchList from "./SearchList";
import Recommend from "./Recommend";
import Send from "../../../config/Send";

function Home() {
  const [pageState, setPageState] = useState("home");
  const [keyword, setKeyword] = useState("");
  const [myLocation, setMyLocation] = useState({
    latitude: 37.554722,
    longitude: 126.970833,
  });
  const [mountainCode, setMountainCode] = useState();
  const [nearMountain, setNearMountain] = useState([]); // 근처 산 리스트
  const [avgMountain, setAvgMountain] = useState([]); // 방문한 평균높이 산 리스트

  useEffect(() => {

    // 근처 산 리스트 요청
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        Send.get("/mountain/recommend", {
          params: {
            by: "distance",
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          },
        }).then(({ data }) => {
          console.log(data.recommendResponseDTOList);
          setNearMountain(data.recommendResponseDTOList)
        });
      });
    } else {
      window.alert("현재위치를 알수 없습니다.");
    }

    // 비슷한 높이 산 요청
    Send.get("/mountain/recommend", {
      params: {
        by: "height",
      },
    }).then(({ data }) => {
      console.log(data.recommendResponseDTOList);
      setAvgMountain(data.recommendResponseDTOList)
    }).catch(err=>{

    });
  }, []);

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
         
         {/* 추천 산 카드 확인 */}
          {/* 
          <Recommend title="근처 산" recommendList={nearMountain} near={true}/>  
          <Recommend title="방문했던 평균 높이 산" recommendList={avgMountain} near={false}/> 
          */}
       
          <Map getLocation={getLocation} myLocation={myLocation}></Map>
        </>
      ) : (
        <SearchList
          keyword={keyword}
          keywordHandler={keywordHandler}
          pageStateHandler={pageStateHandler}
          mountainCodeHandler={mountainCodeHandler}
        ></SearchList>
      )}
    </>
  );
}

export default Home;
