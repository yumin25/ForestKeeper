import React, { useEffect, useState } from "react";
import temp from "../../../../res/img/temp.png";
import edit from "../../../../res/img/edit.png";
function ReviewItem({ nickname, content }) {
  return (
    <div
      style={{
        marginTop: "1.5vh",
        // background: "red",
        height: "8vh",
        borderBottom: "1px solid #CDCDCD",
        fontSize: "1.5vh",
      }}
    >
      <div style={{ fontWeight: "bold", marginBottom: "0.5vh" }}>
        {nickname}
      </div>
      <div style={{ color: "#69696C" }}>{content}</div>
    </div>
  );
}
function QnaItem({ nickname, content, title }) {
  return (
    <>
      <div
        style={{
          // height: "9vh",
          borderBottom: "1px solid #CDCDCD",
          fontSize: "1.5vh",
        }}
      >
        <div
          style={{
            marginTop: "1.5vh",
            fontWeight: "bold",
            marginBottom: "0.5vh",
          }}
        >
          {nickname}
        </div>
        <div style={{ marginBottom: "0.5vh" }}>{title}</div>
        <div style={{ color: "#8E8E92", marginBottom: "1.5vh" }}>{content}</div>
      </div>
    </>
  );
}

function WriteItem() {
  return (
    <>
      <div style={{ display: "flex" }}>
        <div
          style={{
            marginTop: "0.5vh",
            textAlign: "right",
            fontSize: "1.5vh",
            color: "#8E8E92",
            marginLeft: "65vw",
          }}
        >
          글쓰기
        </div>
        <div>
          <img
            style={{
              marginLeft: "2vw",
              width: "2vh",
              height: "2vh",
            }}
            src={edit}
          />
        </div>
      </div>
    </>
  );
}

function About() {
  const nickname = "익명숲지기";
  const title = "여기 꼭 아이젠 필요할까요??";
  const content = "제가 어쩌고 저쩌고요 이러쿵 저러쿵 배고프다";
  const [tab, setTab] = useState("review");
  const ClickedStyle = {
    borderRadius: 3,
    width: "20vw",
    height: "3vh",
    background: "#A2CA9D",
    color: "white",
    border: "none",
    marginRight: "1vw",
  };
  const UnClickedStyle = {
    borderRadius: 3,
    width: "20vw",
    height: "3vh",
    background: "#DAEDD7",
    color: "white",
    border: "none",
    marginRight: "1vw",
  };
  return (
    <>
      <div style={{ marginLeft: "9vw", marginRight: "9vw" }}>
        <div id="tab" style={{ marginBottom: "1vh" }}>
          {tab == "review" ? (
            <>
              <button style={ClickedStyle}>등산후기</button>
              <button onClick={() => setTab("qna")} style={UnClickedStyle}>
                Q&A
              </button>
            </>
          ) : (
            <>
              <button onClick={() => setTab("review")} style={UnClickedStyle}>
                등산후기
              </button>
              <button style={ClickedStyle}>Q&A</button>
            </>
          )}
        </div>
        <div id="List" style={{ height: "62vh" }}>
          {tab == "review" ? (
            <>
              <WriteItem />
              <ReviewItem nickname={nickname} content={content} />
            </>
          ) : (
            <>
              <WriteItem />
              <QnaItem
                nickname={nickname}
                content={content}
                title={title}
              ></QnaItem>
            </>
          )}
        </div>
      </div>
    </>
  );
}
export default About;
