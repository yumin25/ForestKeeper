import { useOutlet, useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import search from "../../../res/img/search.png";
import Bar from "./Bar";
import SearchInput from "./SearchInput";
function ResultItem({ result }) {
  return <>{result.name}</>;
}

function SearchList({
  keyword,
  keywordHandler,
  pageStateHandler,
  mountainCodeHandler,
}) {
  let useParam = useParams(); //여기 안에 keyword, category 존재함

  const [searchList, setSearchList] = useState([
    {
      mountainCode: "1",
      name: "관악산1",
      address: "서울특별시 상도로 47갈 89 아사달빌딩",
    },
    {
      mountainCode: "2",
      name: "관악산2",
      address: "서울특별시 상도로 47갈 89 아사달빌딩",
    },
    {
      mountainCode: "3",
      name: "관악산3",
      address: "서울특별시 상도로 47갈 89 아사달빌딩",
    },
    {
      mountainCode: "4",
      name: "관악산4",
      address: "서울특별시 상도로 47갈 89 아사달빌딩",
    },
  ]);

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

  function getResult() {}

  function goDetail(mountainCode) {
    mountainCodeHandler(mountainCode);
    pageStateHandler("result");
  }
  
  useEffect(() => {
    getResult();
  }, []);

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
          height: "82vh",
          borderTop: "1px solid #EFEFEF",
          borderBottom: "1px solid #EFEFEF",
        }}
      >
        {searchList &&
          searchList.map((result) => (
            //   이거 result.seq 수정해야됨
            <div onClick={() => goDetail(result.mountainCode)}>
              <ResultItem result={result}></ResultItem>
            </div>
          ))}
      </div>
      <Bar></Bar>
    </>
  );
}
export default SearchList;
