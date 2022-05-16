import React, { useEffect, useState } from "react";
import logo_temp from "../../../../res/img/logo_temp.png";
import first from "../../../../res/img/medal.png";
import second from "../../../../res/img/medal(1).png";
import third from "../../../../res/img/medal(2).png";
import axios from "axios";
import Send from "../../../../config/Send";
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
        <div style={{ marginRight: "3vw" }}>
          {user.imagePath == "" || user.imagePath == null ? (
            <img
              src={logo_temp}
              style={{
                width: "4.5vh",
                height: "4.5vh",
                borderRadius: 100,
              }}
            />
          ) : (
            <img
              src={user.imagePath}
              style={{
                width: "4.5vh",
                height: "4.5vh",
                borderRadius: 100,
              }}
            />
          )}
        </div>
        <div
          className="lowNickname"
          style={{ marginTop: "1vh", fontSize: "1.6vh" }}
        >
          {user.nickname}
        </div>
        {tab == "count" ? (
          <div
            id="result"
            className="topResult"
            style={{
              position: "absolute",
              right: "10vw",
              marginTop: "0.8vh",
              fontSize: "1.8vh",
            }}
          >
            {user.count} 회
          </div>
        ) : (
          <div
            id="result"
            className="topResult"
            style={{ marginTop: "0.8vh", fontSize: "1.8vh" }}
          >
            {user.distance} km
          </div>
        )}
      </div>
    </>
  );
}

function Star({ mountainCode, url }) {
  const [users, setUsers] = useState([
    {
      nickname: "",
      count: 0,
      distance: 0.0,
      imagePath: "",
    },
    {
      nickname: "",
      count: 0,
      distance: 0.0,
      imagePath: "",
    },
    {
      nickname: "",
      count: 0,
      distance: 0.0,
      imagePath: "",
    },
  ]);
  // const LowRanker = users.slice(3, users.length);
  const [LowRanker, setLowRanker] = useState(users.slice(3, users.length));

  console.log(users);
  const [rank, setRank] = useState(2);
  const [rankNum, setRankNum] = useState([4, 5, 6, 7, 8, 9, 10]);
  const [tab, setTab] = useState("count");

  useEffect(() => {
    getRank();
  }, [tab]);

  function getRank() {
    console.log(mountainCode);
    setRank(2);
    Send.get(url + `/mountain/rank/${mountainCode}`, {
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
          width: "80vw",
          marginTop: "2.5vh",
          marginBottom: "1vh",
          marginLeft: "10vw",
          display: "flex",
        }}
      >
        <div id="second" style={{ width: "19vw", marginRight: "10vw" }}>
          <div id="images">
            <div id="profile" style={{ textAlign: "center", marginTop: "6vh" }}>
              {users[1] &&
              users[1].imagePath != "" &&
              users[1].imagePath !== null ? (
                <img src={users[1].imagePath} className="topImage" />
              ) : (
                <img src={logo_temp} className="topImage" />
              )}
            </div>
            <div id="rankImg">
              <img
                src={second}
                style={{
                  top: "36vh",
                  left: "19.5vw",
                  position: "absolute",
                  width: "8.8vw",
                  height: "8.5vw",
                  borderRadius: 100,
                }}
              />
            </div>
          </div>
          <div>
            <div className="topNickname" style={{ fontSize: "1.6vh" }}>
              {users.length >= 2 && users[1].nickname}
            </div>
            {tab == "count"
              ? users.length >= 2 && (
                  <div
                    id="result"
                    className="topResult"
                    style={{ fontSize: "1.8vh" }}
                  >
                    {users[1].count} 회
                  </div>
                )
              : users.length >= 2 && (
                  <div
                    id="result"
                    className="topResult"
                    style={{ fontSize: "1.8vh" }}
                  >
                    {users[1].distance} km
                  </div>
                )}
          </div>
        </div>

        <div id="first" style={{ width: "23vw", marginRight: "10vw" }}>
          <div id="images">
            <div id="profile" style={{ textAlign: "center" }}>
              {users[0] &&
              users[0].imagePath != "" &&
              users[0].imagePath !== null ? (
                <img
                  src={users[0].imagePath}
                  style={{
                    position: "relative",
                    width: "21vw",
                    height: "21vw",
                    borderRadius: 100,
                  }}
                  className="topImage"
                />
              ) : (
                <img
                  src={logo_temp}
                  style={{
                    position: "relative",
                    width: "21vw",
                    height: "21vw",
                    borderRadius: 100,
                  }}
                  className="topImage"
                />
              )}
            </div>
            <div id="rankImg">
              <img
                src={first}
                style={{
                  top: "30vh",
                  left: "50.5vw",
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
            className="topNickname"
            style={{ fontSize: "1.6vh" }}
          >
            {users.length >= 1 && users[0].nickname}
          </div>

          {tab == "count"
            ? users.length >= 1 && (
                <div
                  id="result"
                  className="topResult"
                  style={{ fontSize: "1.8vh" }}
                >
                  {users[0].count} 회
                </div>
              )
            : users.length >= 1 && (
                <div
                  id="result"
                  className="topResult"
                  style={{ fontSize: "1.8vh" }}
                >
                  {users[0].distance} km
                </div>
              )}
        </div>

        <div id="third" style={{ width: "19vw" }}>
          <div id="images">
            <div id="profile" style={{ textAlign: "center", marginTop: "6vh" }}>
              {users[2] &&
              users[2].imagePath != "" &&
              users[2].imagePath != null ? (
                <img src={users[2].imagePath} className="topImage" />
              ) : (
                <img src={logo_temp} className="topImage" />
              )}
            </div>
            <div id="rankImg">
              <img
                src={third}
                style={{
                  top: "36vh",
                  left: "80.5vw",
                  position: "absolute",
                  width: "8.3vw",
                  height: "8vw",
                  borderRadius: 100,
                }}
              />
            </div>
          </div>
          <div className="topNickname" style={{ fontSize: "1.6vh" }}>
            {users.length >= 3 && users[2].nickname}
          </div>
          {tab == "count"
            ? users.length >= 3 && (
                <div
                  id="result"
                  className="topResult"
                  style={{ fontSize: "1.8vh" }}
                >
                  {users[2].count} 회
                </div>
              )
            : users.length >= 3 && (
                <div
                  id="result"
                  className="topResult"
                  style={{ fontSize: "1.8vh" }}
                >
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
          marginTop: "4vh",
          marginLeft: "10vw",
        }}
      >
        <div style={{ display: "flex" }}>
          <div>
            {rankNum &&
              rankNum.map((num) => (
                <div
                  style={{
                    height: "3.5vh",
                    marginBottom: "1.5vh",
                    fontSize: "1.6vh",
                    paddingTop: "1vh",
                    marginRight: "3vw",
                  }}
                >
                  {num}위
                </div>
              ))}
          </div>
          <div>
            {LowRanker &&
              LowRanker.map((user) => (
                <UserItem rank={rank} tab={tab} user={user}></UserItem>
              ))}
          </div>
        </div>
      </div>
    </>
  );
}
export default Star;
