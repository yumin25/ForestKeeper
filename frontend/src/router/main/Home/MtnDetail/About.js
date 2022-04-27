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
      <div>{content}</div>
    </div>
  );
}
function QnaItem() {
  return <></>;
}

function About() {
  const nickname = "익명숲지기";
  const content =
    "처음 가보는 산이었는데 역시 괜히 ‘악’산이 아니었습니다...정말 죽음을 맛봤습니다..^^..";
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
              <div style={{ display: "flex" }}>
                <div
                  style={{
                    marginTop: "0.5vh",
                    textAlign: "right",
                    fontSize: "1.5vh",
                    color: "#8E8E92",
                    marginLeft: "65vw",
                    marginBottom: "1vh",
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
              <ReviewItem nickname={nickname} content={content} />
            </>
          ) : (
            <></>
          )}
        </div>
      </div>
    </>
  );
}
export default About;
