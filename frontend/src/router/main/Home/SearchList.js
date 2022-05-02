import { useOutlet, useParams } from "react-router-dom";
import React, { useCallback, useEffect, useState } from "react";
import search from "../../../res/img/search.png";
import Bar from "./Bar";
import SearchInput from "./SearchInput";
import axios from "axios";
import { useInView } from "react-intersection-observer";
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

function SearchList({ keyword, keywordHandler }) {
  const url = "https://k6a306.p.ssafy.io/api";
  const [searchList, setSearchList] = useState();

  const [pageNumber, setPageNumber] = useState(1);
  const [loading, setLoading] = useState(false);
  const [ref, inView] = useInView();

  const getItems = useCallback(async () => {
    setLoading(true);
    getResult();
    setLoading(false);
  }, [pageNumber]);

  useEffect(() => {
    getItems();
  }, [getItems]);

  useEffect(() => {
    // 사용자가 마지막 요소를 보고 있고, 로딩 중이 아니라면
    if (inView && !loading) {
      setPageNumber((prevState) => prevState + 1);
    }
  }, [inView, loading]);

  function getResult() {
    axios
      .get(url + `/mountain`, {
        params: {
          keyword: keyword,
          pageNumber: pageNumber,
        },
      })
      .then(function (response) {
        console.log(response);
        setSearchList((prevState) => [...prevState, response.data.searchlist]);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  function goSearch() {
    if (keyword !== "" && keyword != undefined) {
      setPageNumber(1);
      getResult();
    } else {
      alert("검색어를 입력해주세요.");
    }
  }

  const onSubmit = (event) => {
    if (event.key === "Enter") {
      console.log(keyword);
      if (keyword !== "" && keyword !== undefined) {
        setPageNumber(1);
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
          height: "81.9vh",
          borderTop: "1px solid #EFEFEF",
          marginLeft: "5vw",
          width: "90vw",
        }}
      >
        {searchList &&
          searchList.map((result, idx) => (
            <div key={idx}>
              {searchList.length - 1 == idx ? (
                <div
                  className="list-item"
                  ref={ref}
                  onClick={() => goDetail(result.mountainCode)}
                >
                  <ResultItem result={result}></ResultItem>
                </div>
              ) : (
                <div onClick={() => goDetail(result.mountainCode)}>
                  <ResultItem result={result}></ResultItem>
                </div>
              )}
            </div>
          ))}
      </div>
    </>
  );
}
export default SearchList;
