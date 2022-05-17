import React, { useCallback, useEffect, useState } from "react";
import Bar from "../Bar";
import "../Home.css";
import Send from "../../../../config/Send";
import { connect } from "react-redux";
import x from "../../../../res/img/x.png";
function TeamDetail({ userSlice }) {
  const [detail, setDetail] = useState({
    message: "매칭 글조회에 성공했습니다.",
    statusCode: 200,
    id: "b9a6e9ce-2ea5-45f0-a769-1adb8146e7da",
    nickname: "숲지기산지기문지기",
    title: "조회 테스트e3221",
    createTime: "2022-05-03T01:55:28.07625",
    ploggingDate: "2022-05-02",
    total: 6,
    participant: 1,
    mountainName: "국지산",
    mountainCode: "427500701",
    content: "ㅍㅍㅁㅇ",
    views: 1,
    closed: false,
  });
  const matchingId = window.localStorage.getItem("matchingId");
  useEffect(() => {
    getArticle();
  }, []);

  const btnStyle = {
    width: "25vw",
    height: "5vh",
    background: "#37CD76",
    color: "white",
    borderRadius: 15,
    border: "none",
    marginLeft: "29vw",
  };

  function closeMatching() {
    Send.patch(`/match/close`, { matchingId: matchingId })
      .then((res) => {
        console.log(res);
        getArticle();
      })
      .catch((e) => {
        console.log(e);
      });
  }
  function getArticle() {
    Send.get(`/match/${matchingId}`)
      .then((res) => {
        console.log(res);
        setDetail(res.data);
      })
      .catch((e) => {
        console.log(e);
      });
  }
  console.log(userSlice);
  return (
    <>
      {/* 92.5vh */}
      <div
        style={{
          marginLeft: "8.3vw",
          width: "83.3vw",
          marginRight: "8.3vw",
          height: "84vh",
          marginTop: "8.5vh",
          //   background: "red",
        }}
      >
        <div style={{ fontSize: "2.2vh", marginLeft: "2vw" }}>
          <b>{detail.nickname}</b>
        </div>
        <div
          style={{
            color: "#ACACAC",
            fontSize: "2vh",
            marginLeft: "2vw",
            marginBottom: "3vh",
          }}
        >
          {detail.createTime.substr(0, 10) +
            " " +
            detail.createTime.substr(11, 8)}
        </div>
        <div
          id="banner"
          style={{
            width: "83.3vw",
            height: "9vh",
            background: "#EAF9E6",
            borderRadius: 10,
            marginBottom: "4.8vh",
          }}
        >
          <div
            style={{
              display: "flex",
              fontSize: "2.8vh",
              color: "#8ABC9A",
              fontWeight: "bold",
              paddingTop: "2.2vh",
              paddingLeft: "5vw",
            }}
          >
            <div style={{ width: "55vw" }}>{detail.ploggingDate}</div>
            <div>
              {detail.participant}/{detail.total} 명
            </div>
          </div>
        </div>
        <div
          style={{
            fontSize: "2.2vh",
            color: "#515153",
            marginBottom: "1.2vh",
            marginLeft: "2vw",
          }}
        >
          <b>{detail.title}</b>
        </div>
        <div style={{ marginLeft: "2vw", marginBottom: "7vh" }}>
          {detail.content}
        </div>
        {userSlice.userNickname == detail.nickname ? (
          detail.closed == false ? (
            <button style={btnStyle} onClick={() => closeMatching()}>
              마감하기
            </button>
          ) : (
            <button style={btnStyle}>마감완료</button>
          )
        ) : (
          <button style={btnStyle}></button>
        )}
      </div>
      <Bar></Bar>
    </>
  );
}
function mapStateToProps(state) {
  return { userSlice: state.user };
}

export default connect(mapStateToProps)(TeamDetail);
