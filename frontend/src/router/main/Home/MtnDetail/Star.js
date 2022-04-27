import React, { useEffect, useState } from "react";
import temp from "../../../../res/img/temp.png";
import first from "../../../../res/img/medal.png";
import second from "../../../../res/img/medal(1).png";
import third from "../../../../res/img/medal(2).png";
function UserItem({ user }) {
  return (
    <>
      <div
        style={{
          display: "flex",
          height: "4.5vh",
          marginBottom: "1.5vh",
        }}
      >
        <div
          style={{
            marginTop: "0.5vh",
            fontWeight: 700,
            width: "8vw",
            marginRight: "3vw",
            textAlign: "center",
            fontSize: "2vh",
          }}
        >
          {user.rank}
        </div>
        <div style={{ marginRight: "3vw" }}>
          <img
            src={temp}
            style={{
              width: "8vw",
              height: "8vw",
              borderRadius: 100,
            }}
          />
        </div>
        <div
          style={{
            marginTop: "0.5vh",
            fontSize: "1.8vh",
            fontWeight: "400",
            width: "40vw",
          }}
        >
          {user.nickname}
        </div>
        <div
          style={{
            marginTop: "0.5vh",
            marginLeft: "1vw",
            fontSize: "1.8vh",
            fontWeight: "400",
            width: "16vw",
            textAlign: "end",
            color: "#ACACAC",
          }}
        >
          20000회
        </div>
      </div>
    </>
  );
}

function Star() {
  const users = [
    {
      userId: "bc9f-x5ad-2hdz-p6dvt5sk",
      nickname: "ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ",
      rank: 1,
    },
    {
      userId: "bc9f-x5ad-2hdz-p6dvt5sk",
      nickname: "숨1",
      rank: 2,
    },
    {
      userId: "bc9f-x5ad-2hdz-p6dvt5sk",
      nickname: "숨1",
      rank: 3,
    },
    {
      userId: "bc9f-x5ad-2hdz-p6dvt5sk",
      nickname: "숨이이이이이이이이이",
      rank: 4,
    },
    {
      userId: "bc9f-x5ad-2hdz-p6dvt5sk",
      nickname: "숨1",
      rank: 5,
    },
    {
      userId: "bc9f-x5ad-2hdz-p6dvt5sk",
      nickname: "숨1",
      rank: 6,
    },
    {
      userId: "bc9f-x5ad-2hdz-p6dvt5sk",
      nickname: "숨1",
      rank: 7,
    },
    {
      userId: "bc9f-x5ad-2hdz-p6dvt5sk",
      nickname: "숨1",
      rank: 8,
    },
    {
      userId: "bc9f-x5ad-2hdz-p6dvt5sk",
      nickname: "숨1",
      rank: 9,
    },
    {
      userId: "bc9f-x5ad-2hdz-p6dvt5sk",
      nickname: "숨1",
      rank: 10,
    },
  ];

  const LowRanker = users.slice(3, users.length);
  const [tab, setTab] = useState("count");
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
        {tab == "count" ? (
          <>
            <button style={ClickedStyle}>횟수</button>
            <button onClick={() => setTab("distance")} style={UnClickedStyle}>
              거리
            </button>
          </>
        ) : (
          <>
            <button onClick={() => setTab("count")} style={UnClickedStyle}>
              횟수
            </button>
            <button style={ClickedStyle}>거리</button>
          </>
        )}
      </div>

      {/* 25vh */}
      <div
        id="topRanker"
        style={{
          height: "20vh",
          width: "73vw",
          marginTop: "2.5vh",
          marginBottom: "2.5vh",
          marginLeft: "13.5vw",
          display: "flex",
        }}
      >
        <div id="second" style={{ width: "19vw", marginRight: "5.5vw" }}>
          <div id="images">
            <div id="profile" style={{ textAlign: "center", marginTop: "6vh" }}>
              <img
                src={temp}
                style={{
                  position: "relative",
                  width: "17vw",
                  height: "17vw",
                  borderRadius: 100,
                }}
              />
            </div>
            <div id="rankImg">
              <img
                src={second}
                style={{
                  top: "33.5vh",
                  left: "24vw",
                  position: "absolute",
                  width: "8.8vw",
                  height: "8.5vw",
                  borderRadius: 100,
                }}
              />
            </div>
          </div>
          <div
            id="nickname"
            style={{
              fontSize: "1.8vh",
              fontWeight: "bold",
              textAlign: "center",
            }}
          >
            {users[1].nickname}
          </div>
          <div
            id="result"
            style={{
              fontSize: "1.8vh",
              color: "#ACACAC",
              textAlign: "center",
            }}
          >
            200회
          </div>
        </div>

        <div id="first" style={{ width: "23vw", marginRight: "5.5vw" }}>
          <div id="images">
            <div id="profile" style={{ textAlign: "center" }}>
              <img
                src={temp}
                style={{
                  position: "relative",
                  width: "21vw",
                  height: "21vw",
                  borderRadius: 100,
                }}
              />
            </div>
            <div id="rankImg">
              <img
                src={first}
                style={{
                  top: "28vh",
                  left: "52vw",
                  position: "absolute",
                  width: "9.3vw",
                  height: "9vw",
                  borderRadius: 100,
                }}
              />
            </div>
          </div>
          <div
            id="nickname"
            style={{
              fontSize: "1.8vh",
              fontWeight: "bold",
              textAlign: "center",
            }}
          >
            {users[0].nickname}
          </div>
          <div
            id="result"
            style={{
              fontSize: "1.8vh",
              color: "#ACACAC",
              textAlign: "center",
            }}
          >
            200회
          </div>
        </div>

        <div id="third" style={{ width: "19vw" }}>
          <div id="images">
            <div id="profile" style={{ textAlign: "center", marginTop: "6vh" }}>
              <img
                src={temp}
                style={{
                  position: "relative",
                  width: "17vw",
                  height: "17vw",
                  borderRadius: 100,
                }}
              />
            </div>
            <div id="rankImg">
              <img
                src={third}
                style={{
                  top: "33.5vh",
                  left: "77vw",
                  position: "absolute",
                  width: "8.3vw",
                  height: "8vw",
                  borderRadius: 100,
                }}
              />
            </div>
          </div>
          <div
            id="nickname"
            style={{
              fontSize: "1.8vh",
              fontWeight: "bold",
              textAlign: "center",
            }}
          >
            {users[2].nickname}
          </div>
          <div
            id="result"
            style={{ fontSize: "1.8vh", color: "#ACACAC", textAlign: "center" }}
          >
            300회
          </div>
        </div>
      </div>

      {/* 42.5vh */}
      <div
        id="LowRanker"
        style={{
          height: "42.5vh",
          width: "80vw",
          marginLeft: "10vw",
        }}
      >
        {LowRanker &&
          LowRanker.map((user) => <UserItem user={user}></UserItem>)}
      </div>
    </>
  );
}
export default Star;
