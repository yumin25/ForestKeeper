import { useOutlet, useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import search from "../../../res/img/search.png";
import Bar from "./Bar";
import SearchInput from "./SearchInput";
function ResultItem() {
  return <></>;
}
function Search() {
  let useParam = useParams(); //여기 안에 keyword, category 존재함
  const [keyword, setKeyword] = useState(useParam.keyword);
  const [results, setResults] = useState();
  console.log(useParam.keyword);

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

  function getResult() {}

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

      <div id="List" style={{ height: "82vh" }}>
        {results &&
          results.map((result) => (
            //   이거 result.seq 수정해야됨
            // <div onClick={() => goDetail(result.seq)}>
            <div>
              <ResultItem></ResultItem>
            </div>
          ))}
      </div>
      <Bar></Bar>
    </>
  );
}
export default Search;
