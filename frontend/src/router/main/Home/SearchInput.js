import search from "../../../res/img/search.png";
import React, { useEffect, useState } from "react";
import axios from "axios";
function SearchInput({ keyword, keywordHandler, onSubmit, goSearch }) {
  return (
    <>
      <div
        id="searchBar"
        style={{
          height: "7vh",
          marginTop: "3.5vh",

          marginBottom: "1vh",
        }}
      >
        <div style={{ display: "flex" }}>
          <div>
            {keyword === "" ? (
              <input
                className="input"
                placeholder="산 이름 검색하기"
                onKeyPress={onSubmit}
                onChange={keywordHandler}
                style={{
                  background: "#EFEFEF",
                  width: "77vw",
                  height: "5vh",
                  borderRadius: 10,
                  border: "none",
                  paddingLeft: "5vw",

                  marginLeft: "4.5vw",
                }}
              ></input>
            ) : (
              <input
                className="input"
                value={keyword}
                onKeyPress={onSubmit}
                onChange={keywordHandler}
                style={{
                  background: "#EFEFEF",
                  width: "77vw",
                  height: "5vh",
                  borderRadius: 10,
                  border: "none",
                  paddingLeft: "5vw",
                  marginLeft: "4.5vw",
                }}
              ></input>
            )}
          </div>
          <div onClick={goSearch}>
            <img
              style={{
                marginTop: "1.2vh",
                width: "6vw",
                height: "6vw",
                marginLeft: "2vw",
              }}
              src={search}
            />
          </div>
        </div>
      </div>
    </>
  );
}
export default SearchInput;
