import React, { useEffect, useState } from "react";
import temp from "../../../../res/img/temp.png";
import first from "../../../../res/img/medal.png";
import second from "../../../../res/img/medal(1).png";
import third from "../../../../res/img/medal(2).png";
import axios from "axios";
import "./Star.css";
function UserItem({ user, tab }) {
  return (
    <>
      <div
        style={{
          display: "flex",
          height: "4.5vh",
          marginBottom: "1.5vh",
        }}
      >
        <div className="lowRank">{user.rank}</div>
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
        <div className="lowNickname">{user.nickname}</div>
        {tab == "count" ? (
          <div id="result" className="topResult">
            {user.count} 회
          </div>
        ) : (
          <div id="result" className="topResult">
            {user.distance} km
          </div>
        )}
      </div>
    </>
  );
}

function Star({ url }) {
  const [users, setUsers] = useState([
    {
      rank: 1,
      nickname: "1위",
      count: 1,
      distance: 0.0,
    },
    {
      rank: 2,
      nickname: "2위",
      count: 2,
      distance: 0.0,
    },
    {
      rank: 3,
      nickname: "3위",
      count: 3,
      distance: 0.0,
    },
  ]);
  const LowRanker = users.slice(3, users.length);
  const [tab, setTab] = useState("count");

  useEffect(() => {
    getRank();
  }, [tab]);

  function getRank() {
    axios
      .get(url + `/api/mountain/rank`, {
        params: {
          by: tab,
        },
      })
      .then(function (response) {
        console.log(response.data);
        setUsers(response.data.mountainRankResponseDTOList);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

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
              <img src={temp} className="topImage" />
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
          <div id="nickname" className="topNickname">
            {users.length >= 2 && users[1].nickname}
          </div>
          {tab == "count"
            ? users.length >= 2 && (
                <div id="result" className="topResult">
                  {users[1].count} 회
                </div>
              )
            : users.length >= 2 && (
                <div id="result" className="topResult">
                  {users[1].distance} km
                </div>
              )}
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
          <div id="nickname" className="topNicknamw">
            {users.length >= 1 && users[0].nickname}
          </div>

          {tab == "count"
            ? users.length >= 1 && (
                <div id="result" className="topResult">
                  {users[0].count} 회
                </div>
              )
            : users.length >= 1 && (
                <div id="result" className="topResult">
                  {users[0].distance} km
                </div>
              )}
        </div>

        <div id="third" style={{ width: "19vw" }}>
          <div id="images">
            <div id="profile" style={{ textAlign: "center", marginTop: "6vh" }}>
              <img src={temp} className="topImage" />
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
          <div className="topNickname">
            {users.length >= 3 && users[2].nickname}
          </div>
          {tab == "count"
            ? users.length >= 3 && (
                <div id="result" className="topResult">
                  {users[2].count} 회
                </div>
              )
            : users.length >= 3 && (
                <div id="result" className="topResult">
                  {users[2].distance} km
                </div>
              )}
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
          LowRanker.map((user) => <UserItem tab={tab} user={user}></UserItem>)}
      </div>
    </>
  );
}
export default Star;
