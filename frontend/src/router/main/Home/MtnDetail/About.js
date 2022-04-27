import React, { useEffect, useState } from "react";
import temp from "../../../../res/img/temp.png";
function About() {
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
  };
  return (
    <>
      <div id="tab" style={{ marginLeft: "9vw", marginRight: "6vw" }}>
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
    </>
  );
}
export default About;
