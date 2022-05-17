import React, { useCallback, useEffect, useState } from "react";
import Bar from "../Bar";
import "../Home.css";
import Send from "../../../../config/Send";
import { connect } from "react-redux";
import x from "../../../../res/img/x.png";
import axios from "axios";
function TeamDetail({ userSlice }) {
  const [isParticipated, setIsParticipated] = useState(false);
  const [detail, setDetail] = useState({
    message: "",
    statusCode: "",
    id: "",
    nickname: "",
    title: "",
    createTime: "",
    ploggingDate: "",
    total: 0,
    participants: [],
    mountainName: "",
    mountainCode: "",
    content: "",
    views: 0,
    closed: false,
  });
  console.log(isParticipated);
  const matchingId = window.localStorage.getItem("matchingId");
  useEffect(() => {
    getArticle();
  }, []);
  useEffect(() => {
    confirmParticipation();
  }, [detail]);

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
        confirmParticipation();
      })
      .catch((e) => {
        console.log(e);
      });
  }

  function confirmParticipation() {
    console.log(detail.participants);
    console.log(userSlice);
    for (let i = 0; i < detail.participants.length; i++) {
      if (detail.participants[i].nickname == userSlice.userNickname) {
        setIsParticipated(true);
      }
    }
  }

  function apply() {
    const data = {
      matchingId: matchingId,
    };
    Send.post(`/match/join`, data)
      .then((res) => {
        console.log(res);
        getArticle();
      })
      .catch((e) => {
        console.log(e);
      });
  }

  function cancel() {
    Send.delete(`/match/cancel/${matchingId}`)
      .then((res) => {
        console.log(res);
        getArticle();
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
        <div>
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
        </div>

        <div
          id="banner"
          style={{
            width: "78.3vw",
            height: "15vh",
            background: "#EAF9E6",
            borderRadius: 10,
            marginBottom: "4.8vh",
            color: "#8ABC9A",
            paddingTop: "2.1vh",
            paddingLeft: "5vw",
            fontSize: "2vh",
          }}
        >
          <div style={{ display: "flex", marginBottom: "1.8vh" }}>
            <div style={{ marginRight: "2vw", marginTop: "0.3vh" }}>
              플로깅할 산 :{" "}
            </div>
            <div style={{ fontSize: "2.3vh" }}>
              <b>{detail.mountainName}</b>{" "}
            </div>
          </div>
          <div style={{ display: "flex", marginBottom: "1.8vh" }}>
            <div style={{ marginRight: "2vw", marginTop: "0.3vh" }}>
              일자 :{" "}
            </div>
            <div style={{ fontSize: "2.3vh" }}>
              <b>{detail.ploggingDate}</b>{" "}
            </div>
          </div>
          <div style={{ display: "flex" }}>
            <div style={{ marginRight: "2vw", marginTop: "0.3vh" }}>
              인원현황 :{" "}
            </div>
            <div style={{ fontSize: "2.3vh" }}>
              <b>
                {detail.participants.length}/{detail.total} 명
              </b>
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

        {detail.close == true ? (
          <button style={clickdeBtnStyle}>마감완료</button>
        ) : userSlice.userNickname == detail.nickname ? (
          <button style={btnStyle} onClick={() => closeMatching()}>
            마감하기
          </button>
        ) : isParticipated == false ? (
          <button style={btnStyle} onClick={() => apply()}>
            신청하기
          </button>
        ) : (
          <button style={clickdeBtnStyle} onClick={() => cancel()}>
            신청취소
          </button>
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
