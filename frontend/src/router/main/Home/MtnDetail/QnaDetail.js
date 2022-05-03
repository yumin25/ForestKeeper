import React, { useCallback, useEffect, useState } from "react";
import Bar from "../Bar";
import "../Home.css";
function QnaDetail() {
  function createComment() {}
  function getArticle() {}
  useEffect(() => {
    getArticle();
  }, []);
  const [comments, setComments] = useState([
    {
      nickname: "didii",
      description: "최고 !",
      createTime: "2022-04-18 15:37:33",
    },
    {
      nickname: "didii",
      description: "최고 !",
      createTime: "2022-04-18 15:37:33",
    },
    {
      nickname: "didii",
      description: "최고 !",
      createTime: "2022-04-18 15:37:33",
    },
    {
      nickname: "didii",
      description: "최고 !",
      createTime: "2022-04-18 15:37:33",
    },
  ]);
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
        }}
      >
        <div
          id="header"
          style={{
            position: "relative",
            height: "3.5vh",
            fontSize: "1.8vh",
            marginBottom: "3.5vh",
          }}
        >
          <div style={{ fontWeight: "bolder" }}>익명숲지기</div>
          <div style={{ color: "#ACACAC" }}>04/18 16:30</div>
        </div>
        <div
          id="contents"
          style={{
            position: "relative",
            height: "30vh",
            marginBottom: "2vh",
          }}
        >
          <div
            id="title"
            style={{
              fontSize: "2.2vh",
              fontWeight: "bold",
              marginBottom: "1.5vh",
            }}
          >
            여기 꼭 아이젠 필요할까요??
          </div>
          <div id="content" style={{ fontSize: "1.8vh" }}>
            관악산은 처음 등반해보는데 아이젠이 필요할지 ~~~~~~~~~~~~~~~~~~~~~~
            저쩌고~~~~~~배고파~~~신전 빨리 왕~~~~ 룰루랄라
          </div>
        </div>
        <div id="comments">
          <div
            style={{
              position: "relative",
              fontWeight: "regular",
              fontSize: "2vh",
              marginBottom: "1.5vh",
            }}
          >
            댓글
          </div>
          <div id="comment" style={{ height: "30vh", overflow: "auto" }}>
            {comments &&
              comments.map((comment) => (
                //   10.5vh
                <div
                  style={{
                    fontSize: "1.7vh",
                    borderBottom: "1px solid #8E8E92",
                  }}
                >
                  <div style={{ fontWeight: "bold", marginTop: "1.5vh" }}>
                    익명숲지기
                  </div>
                  <div style={{ fontSize: "1.7vh" }}>{comment.description}</div>
                  <div style={{ color: "#ACACAC", marginBottom: "1.5vh" }}>
                    {comment.createTime}
                  </div>
                </div>
              ))}
          </div>
        </div>
        <div>
          <div>
            <input
              className="input"
              style={{
                position: "relative",
                background: "#EFEFEF",
                border: "none",
                width: "70.3vw",
                height: "4vh",
                marginTop: "2.7vh",
                paddingLeft: "3vw",
                fontSize: "1.8vh",
                paddingRight: "10vw",
              }}
              placeholder="댓글을 작성해보세요"
            ></input>
          </div>
          <div>
            <button
              onClick={createComment}
              style={{
                position: "absolute",
                top: "85vh",
                right: "10vw",
                border: "none",
                background: "none",
                color: "#69696C",
                fontSize: "1.8vh",
              }}
            >
              작성
            </button>
          </div>
        </div>
      </div>
      <Bar></Bar>
    </>
  );
}
export default QnaDetail;
