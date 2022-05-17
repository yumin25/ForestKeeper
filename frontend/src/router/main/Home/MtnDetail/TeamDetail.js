import React, { useCallback, useEffect, useState } from "react";
import Bar from "../Bar";
import "../Home.css";
import Send from "../../../../config/Send";
import { connect } from "react-redux";
import x from "../../../../res/img/x.png";
function TeamDetail({ userSlice }) {
  const [detail, setDetail] = useState({
    message: "",
    statusCode: "",
    id: "",
    nickname: "",
    title: "",
    createTime: "",
    ploggingDate: "",
    total: 0,
    participant: 0,
    mountainName: "",
    mountainCode: "",
    content: "",
    views: 0,
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
  const clickdeBtnStyle = {
    width: "25vw",
    height: "5vh",
    background: "#FF7760",
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
            <button style={clickdeBtnStyle}>마감완료</button>
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
