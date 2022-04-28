import { useOutlet, useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import search from "../../../res/img/search.png";
import Bar from "./Bar";
import SearchInput from "./SearchInput";
import axios from "axios";
function ResultItem({ result }) {
  return (
    <>
      <div
        style={{
          borderBottom: "1px solid #EFEFEF",
          cursor: "pointer",
        }}
      >
        <div
          style={{
            fontSize: "2vh",
            marginTop: "2vh",
            marginBottom: "0.5vh",
          }}
        >
          {result.name}
        </div>
        <div
          style={{ fontSize: "1.5vh", color: "#8E8E92", marginBottom: "2vh" }}
        >
          {result.address}
        </div>
      </div>
    </>
  );
}

function SearchList({
  keyword,
  keywordHandler,
  pageStateHandler,
  mountainCodeHandler,
}) {
  let useParam = useParams(); //여기 안에 keyword, category 존재함
  const url = "https://k6a306.p.ssafy.io/api";
  const [searchList, setSearchList] = useState();
  // const [searchList, setSearchList] = useState([
  //   {
  //     mountainCode: "1",
  //     name: "관악산1",
  //     address: "서울특별시 상도로 47갈 89 아사달빌딩",
  //   },
  //   {
  //     mountainCode: "2",
  //     name: "관악산2",
  //     address: "서울특별시 상도로 47갈 89 아사달빌딩",
  //   },
  //   {
  //     mountainCode: "3",
  //     name: "관악산3",
  //     address: "서울특별시 상도로 47갈 89 아사달빌딩",
  //   },
  //   {
  //     mountainCode: "4",
  //     name: "관악산4",
  //     address: "서울특별시 상도로 47갈 89 아사달빌딩",
  //   },
  //   {
  //     mountainCode: "4",
  //     name: "관악산4",
  //     address: "서울특별시 상도로 47갈 89 아사달빌딩",
  //   },
  //   {
  //     mountainCode: "4",
  //     name: "관악산4",
  //     address: "서울특별시 상도로 47갈 89 아사달빌딩",
  //   },
  //   {
  //     mountainCode: "4",
  //     name: "관악산4",
  //     address: "서울특별시 상도로 47갈 89 아사달빌딩",
  //   },
  //   {
  //     mountainCode: "4",
  //     name: "관악산4",
  //     address: "서울특별시 상도로 47갈 89 아사달빌딩",
  //   },
  // ]);
  useEffect(() => {
    getResult();
  }, []);

  function getResult() {
    axios
      .get(url + `/mountain`, {
        params: {
          keyword: keyword,
        },
      })
      .then(function (response) {
        console.log(response);
        setSearchList(response.data.searchlist);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  function goSearch() {
    if (keyword !== "" && keyword != undefined) {
      getResult();
    } else {
      alert("검색어를 입력해주세요.");
    }
  }

  const onSubmit = (event) => {
    if (event.key === "Enter") {
      console.log(keyword);
      if (keyword !== "" && keyword !== undefined) {
        getResult();
      } else {
        alert("검색어를 입력해주세요.");
      }
    }
  };

  function goDetail(mountainCode) {
    document.location.href = `/detail/${mountainCode}`;
  }

  return (
    <>
      <SearchInput
        keyword={keyword}
        keywordHandler={keywordHandler}
        onSubmit={onSubmit}
        goSearch={goSearch}
      ></SearchInput>

      <div
        id="List"
        style={{
          // background: "yellow",
          height: "81.9vh",
          borderTop: "1px solid #EFEFEF",
          // borderBottom: "1px solid #EFEFEF",
          marginLeft: "5vw",
          width: "90vw",
        }}
      >
        {searchList &&
          searchList.map((result) => (
            <div onClick={() => goDetail(result.mountainCode)}>
              <ResultItem result={result}></ResultItem>
            </div>
          ))}
      </div>
      {/* <Bar></Bar> */}
    </>
  );
}
export default SearchList;
