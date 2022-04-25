import React, { useEffect, useState } from "react";

function Star() {
  const [tab, setTab] = useState("count");
  return (
    <>
      <div id="tab">
        {tab == "count" ? (
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
              횟수
            </button>
            <button
              onClick={() => setTab("distance")}
              style={{
                borderRadius: 3,
                width: "20vw",
                height: "3vh",
                background: "#DAEDD7",
                color: "white",
                border: "none",
              }}
            >
              거리
            </button>
          </>
        ) : (
          <>
            <button
              onClick={() => setTab("count")}
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
              횟수
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
              거리
            </button>
          </>
        )}
      </div>
    </>
  );
}
export default Star;
