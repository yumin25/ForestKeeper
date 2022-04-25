import React, { useEffect, useState } from "react";
function About() {
  const [tab, setTab] = useState("review");
  return (
    <>
      <div id="tab">
        {tab == "review" ? (
          <>
            <button
              style={{
                borderRadius: 3,
                width: "20vw",
                height: "3vh",
                background: "#A2CA9D",
                color: "white",
                border: "none",
                marginRight: "1vw",
              }}
            >
              등산후기
            </button>
            <button
              onClick={() => setTab("qna")}
              style={{
                borderRadius: 3,
                width: "20vw",
                height: "3vh",
                background: "#DAEDD7",
                color: "white",
                border: "none",
              }}
            >
              Q&A
            </button>
          </>
        ) : (
          <>
            <button
              onClick={() => setTab("review")}
              style={{
                borderRadius: 3,
                width: "20vw",
                height: "3vh",
                background: "#DAEDD7",
                color: "white",
                border: "none",
                marginRight: "1vw",
              }}
            >
              등산후기
            </button>
            <button
              style={{
                borderRadius: 3,
                width: "20vw",
                height: "3vh",
                background: "#A2CA9D",
                color: "white",
                border: "none",
              }}
            >
              Q&A
            </button>
          </>
        )}
      </div>
    </>
  );
}
export default About;
