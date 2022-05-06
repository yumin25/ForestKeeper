import React, { useCallback, useEffect, useState } from "react";
import Bar from "../Bar";
import { useParams } from "react-router-dom";
import axios from "axios";
import Send from "../../../../config/Send";
import "../Home.css";
function Write() {
  const url = "https://k6a306.p.ssafy.io/api";
  const [reviewTab, setReviewTab] = useState(false);
  const [qnaTab, setQnaTab] = useState(false);
  const [title, setTitle] = useState();
  const [content, setContent] = useState();
  const [communityCode, setCommunityCode] = useState();
  let useParam = useParams();

  const ClickedStyle = {
    border: "0.1px solid #8ABC9A",
    background: "#8ABC9A",
    color: "white",
    borderRadius: 15,
    fontSize: "1.7vh",
    height: "4vh",
    width: "18vw",
  };
  const UnClickedStyle = {
    border: "0.1px solid #8E8E92",
    background: "white",
    color: "#8E8E92",
    borderRadius: 15,
    fontSize: "1.7vh",
    height: "4vh",
    width: "18vw",
  };

  function handleReviewTab() {
    if (reviewTab == false) {
      setReviewTab(true);
      setQnaTab(false);
      setCommunityCode("REVIEW");
    } else {
      setReviewTab(false);
    }
  }
  function handleQnaTab() {
    if (qnaTab == false) {
      setQnaTab(true);
      setReviewTab(false);
      setCommunityCode("QNA");
    } else {
      setQnaTab(false);
    }
  }

  const onTitleHandler = (event) => {
    setTitle(event.currentTarget.value);
  };
  const onContentHandler = (event) => {
    setContent(event.currentTarget.value);
  };

  function register() {
    const data = {
      mountainId: useParam.mountainCode,
      communityCode: communityCode,
      title: title,
      description: content,
    };

    Send.post(`/community`, JSON.stringify(data))
      .then((res) => {
        console.log(res);
        if (res.code === 201) {
          alert("글이 등록되었습니다.");
        }
      })
      .catch((e) => {
        console.log(e);
      });
  }

  return (
    <>
      <div
        style={{
          width: "81.8vw",
          marginLeft: "9.1vw",
        }}
      >
        <div
          style={{
            width: "81.8vw",
            height: "3.4vh",
            display: "flex",
            marginTop: "7vh",
            marginBottom: "1vh",
            color: "#8ABC9A",
            fontSize: "1.7vh",
          }}
        >
          <div style={{ marginTop: "0.5vh" }}>글 종류를 선택해주세요</div>
          <div style={{ position: "absolute", right: "14.1vw" }}>
            <button
              onClick={register}
              style={{
                background: "#37CD8D",
                color: "white",
                border: "none",
                borderRadius: 5,
                width: "14vw",
                height: "3.4vh",
                fontSize: "1.7vh",
              }}
            >
              등록
            </button>
          </div>
        </div>
        <div style={{ display: "flex", marginBottom: "2.8vh" }}>
          <div style={{ marginRight: "2vw" }}>
            {reviewTab ? (
              <button style={ClickedStyle} onClick={handleReviewTab}>
                산 후기
              </button>
            ) : (
              <button style={UnClickedStyle} onClick={handleReviewTab}>
                산 후기
              </button>
            )}
          </div>

          {qnaTab ? (
            <button style={ClickedStyle} onClick={handleQnaTab}>
              Q&A
            </button>
          ) : (
            <button style={UnClickedStyle} onClick={handleQnaTab}>
              Q&A
            </button>
          )}
        </div>

        <textarea
          class="textarea"
          placeholder="제목을 입력해주세요."
          onChange={onTitleHandler}
          style={{
            height: "3.5vh",
            borderBottom: "1px solid #C4C4C4",
            // paddingBottom: "1vh",
            width: "80vw",
            fontSize: "2.1vh",
          }}
        ></textarea>
        <textarea
          class="textarea"
          rows="1"
          maxlength="5000"
          placeholder="내용을 입력하세요."
          onChange={onContentHandler}
          style={{
            marginTop: "2vh",
            height: "66.5vh",
            overflow: "auto",
            width: "80vw",
            fontSize: "2.1vh",
          }}
        ></textarea>
      </div>
      <Bar></Bar>
    </>
  );
}

export default Write;
