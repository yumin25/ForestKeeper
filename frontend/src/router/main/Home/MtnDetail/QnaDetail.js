import React, { useCallback, useEffect, useState } from "react";
import Bar from "../Bar";
function QnaDetail() {
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
          width: "83.4vw",
          marginRight: "8.3vw",
          height: "84vh",
          marginTop: "8.5vh",
        }}
      >
        <div
          id="header"
          style={{ height: "3.5vh", fontSize: "1.8vh", marginBottom: "4.5vh" }}
        >
          <div style={{ fontWeight: "bolder" }}>익명숲지기</div>
          <div style={{ color: "#ACACAC" }}>04/18 16:30</div>
        </div>
        <div id="contents" style={{ height: "32vh", marginBottom: "5.5vh" }}>
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
          <div id="content" style={{ fontSize: "1.5vh" }}>
            관악산은 처음 등반해보는데 아이젠이 필요할지 ~~~~~~~~~~~~~~~~~~~~~~
            저쩌고~~~~~~배고파~~~신전 빨리 왕~~~~ 룰루랄라
          </div>
        </div>
        <div id="comments">
          <div
            style={{
              fontWeight: "regular",
              fontSize: "2vh",
              marginBottom: "2vh",
            }}
          >
            댓글
          </div>
          <div id="comment">
            {comments &&
              comments.map((comment) => (
                //   10.5vh
                <div
                  style={{
                    fontSize: "1.7vh",
                    borderBottom: "1px solid #8E8E92",
                  }}
                >
                  <div style={{ fontWeight: "bold", marginTop: "2.2vh" }}>
                    익명숲지기
                  </div>
                  <div style={{ fontSize: "1.7vh" }}>{comment.description}</div>
                  <div style={{ color: "#ACACAC", marginBottom: "2.2vh" }}>
                    {comment.createTime}
                  </div>
                </div>
              ))}
          </div>
        </div>
      </div>
      <Bar></Bar>
    </>
  );
}
export default QnaDetail;
