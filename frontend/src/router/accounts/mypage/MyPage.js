import { useState } from "react";
import { Link } from "react-router-dom";
import { connect } from "react-redux";
import logo from "../../../res/img/logo.png";

function MyPage({ userSlice }) {
  const [menu, setMenu] = useState("1");
  const menuHandlerOne = (e) => {
    setMenu("1");
  };
  const menuHandlerTwo = (e) => {
    setMenu("2");
  };
  return (
    <>
      <div style={{ display: "flex", flexDirection: "row-reverse", margin: "1vh", height: "5vh" }}>
        <Link to="/accounts/mypage/settings" style={{ textDecoration: "none" }}>
          <span className="material-icons-outlined" style={{ fontSize: "2rem", color: "#8E8E92" }}>
            settings
          </span>
        </Link>
      </div>
      <div style={{ height: "85.5vh" }}>
        {/* 개인정보 */}
        <div style={{ display: "flex", justifyContent: "center" }}>
          <div style={{ display: "flex", width: "80vw" }}>
            <img
              style={{
                width: "22vw",
                maxWidth: "22vw",
                objectFit: "cover",
                borderRadius: "50%",
                border: "solid",
                borderWidth: "1px",
                borderColor: "#8E8E92",
              }}
              src={logo}
              alt="logo"
            />
            <div style={{ margin: "auto 0", marginLeft: "1rem" }}>
              <div style={{ display: "flex", alignItems: "center", marginBottom: "3vw" }}>
                <p style={{ fontSize: "4.5vw", margin: "0 2vw 0 0", color: "#8E8E92" }}>{userSlice.userNickname}</p>
                <button
                  style={{
                    backgroundColor: "#B8DEB3",
                    border: "none",
                    borderRadius: "50%",
                    width: "6vw",
                    height: "6vw",
                    color: "white",
                    fontSize: "4vw",
                    padding: "auto",
                  }}
                >
                  <span className="material-icons-outlined" style={{ fontSize: "3vw" }}>
                    edit
                  </span>
                </button>
              </div>
              <p style={{ fontSize: "4.5vw", margin: 0, color: "#8E8E92" }}>{userSlice.userEmail}</p>
            </div>
          </div>
        </div>
        {/* 활동요약 */}
        <div style={{ display: "flex", justifyContent: "space-evenly", color: "#8E8E92" }}>
          <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <p style={{ marginBottom: 0, fontSize: "4vw" }}>거리</p>
            <p style={{ marginTop: 0, fontSize: "4vw" }}>87.95km</p>
          </div>
          <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <p style={{ marginBottom: 0, fontSize: "4vw" }}>시간</p>
            <p style={{ marginTop: 0, fontSize: "4vw" }}>87:10</p>
          </div>
          <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <p style={{ marginBottom: 0, fontSize: "4vw" }}>경험치</p>
            <p style={{ marginTop: 0, fontSize: "4vw" }}>5000exp</p>
          </div>
        </div>
        {/* 활동메뉴 */}
        <div style={{ display: "flex", marginTop: "1vh" }}>
          <div
            style={{
              padding: "1vh 0",
              width: "50vw",
              minheight: "5vh",
              textAlign: "center",
              fontSize: "4.5vw",
              borderBottom: "solid",
              borderColor: menu === "1" ? "#37CD8D" : "#8E8E92",
              borderWidth: "4px",
            }}
            onClick={menuHandlerOne}
          >
            활동 내역
          </div>
          <div
            style={{
              padding: "1vh 0",
              width: "50vw",
              minheight: "5vh",
              textAlign: "center",
              fontSize: "4.5vw",
              borderBottom: "solid",
              borderColor: menu === "2" ? "#37CD8D" : "#8E8E92",
              borderWidth: "4px",
            }}
            onClick={menuHandlerTwo}
          >
            등산 기록
          </div>
        </div>
        {/* 활동기록 */}
        <div style={{ display: "flex", flexDirection: "column", alignItems: "center", margin: "2vh 1vw 0 1vw", height: "50vh" }}>
          {menu === "1" ? (
            <>
              <Link to="/accounts/mypage/recorddetail" style={{ textDecoration: "none" }}>
                <div
                  style={{
                    marginBottom: "2vh",
                    width: "80vw",
                    height: "12vh",
                    minHeight: "100px",
                    backgroundColor: "#EAF9E6",
                    borderRadius: "10px",
                    display: "flex",
                    alignItems: "center",
                  }}
                >
                  <div style={{ margin: "auto", width: "70vw", display: "flex" }}>
                    <img src={logo} alt="img" style={{ margin: "auto", height: "80px", width: "80px" }} />
                    <div style={{ margin: "0 auto", width: "45vw", display: "flex", flexDirection: "column", justifyContent: "space-evenly" }}>
                      <p style={{ margin: 0, fontSize: "3vh", color: "#8ABC9A", fontWeight: "700" }}>2022.04.15</p>
                      <div style={{ display: "flex", justifyContent: "space-between" }}>
                        <div style={{ margin: 0, fontSize: "2vh", fontWeight: "700", color: "#8E8E92" }}>아차산</div>
                        <div style={{ margin: 0, fontSize: "2vh", fontWeight: "700", color: "#8E8E92" }}>7.72 km</div>
                        <div style={{ margin: 0, fontSize: "2vh", fontWeight: "700", color: "#8E8E92" }}>42 : 15</div>
                      </div>
                    </div>
                  </div>
                </div>
              </Link>
            </>
          ) : (
            <>
              <div
                style={{
                  marginBottom: "2vh",
                  width: "80vw",
                  height: "100px",
                  backgroundColor: "#EAF9E6",
                  borderRadius: "10px",
                  display: "flex",
                  alignItems: "center",
                }}
              >
                <div style={{ margin: "auto", width: "70vw", display: "flex" }}>
                  <div style={{ margin: "0 auto", width: "45vw" }}>
                    <p style={{ margin: 0, fontSize: "4vh", color: "#8ABC9A", fontWeight: "700", textAlign: "center" }}>관악산</p>
                  </div>
                </div>
              </div>
            </>
          )}
        </div>
      </div>
    </>
  );
}

function mapStateToProps(state) {
  return { userSlice: state.user };
}

export default connect(mapStateToProps)(MyPage);
