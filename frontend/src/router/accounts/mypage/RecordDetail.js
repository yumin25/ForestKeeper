import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { connect } from "react-redux";
import Send from "../../../config/Send";
import logo from "../../../res/img/logo.png";
import { RenderAfterNavermapsLoaded, NaverMap, Marker, Polyline } from "react-naver-maps";

function RecordDetail() {
  const navigate = useNavigate();
  const [detail, setDetail] = useState({});
  const [centerX, setCenterX] = useState();
  const [centerY, setCenterY] = useState();
  const getDetail = (ploggingId) => {
    Send.get(`/plogging/${ploggingId}`, {
      params: {
        ploggingId: localStorage.getItem("ploggingId"),
      },
    })
      .then((res) => {
        setDetail(res.data);
        setCenterY(res.data.coords[parseInt((res.data.coords.length - 1) / 2)].y);
        setCenterX(res.data.coords[parseInt((res.data.coords.length - 1) / 2)].x);
      })
      .catch((e) => {
        console.log(e);
      });
  };
  const delId = (e) => {
    e.preventDefault();
    localStorage.removeItem("ploggingId");
  };
  useEffect(() => {
    getDetail(localStorage.getItem("ploggingId"));
  }, []);
  return (
    <>
      <div
        style={{ display: "flex", margin: "1vh" }}
        onClick={(e) => {
          navigate(-1);
          delId(e);
        }}
      >
        <span className="material-icons-outlined" style={{ fontSize: "2rem", color: "#8E8E92" }}>
          arrow_back_ios
        </span>
        <span style={{ fontSize: "7vw", fontWeight: "700", color: "#8E8E92", marginLeft: "3vw" }}>플로깅 기록</span>
      </div>
      {/* 기록 */}
      <div style={{ display: "flex", flexDirection: "column", alignItems: "center", margin: "7vh 1vw 0 1vw", height: "75vh" }}>
        <>
          <div
            style={{
              marginBottom: "2vh",
              width: "80vw",
              height: "75vh",
              backgroundColor: "#EAF9E6",
              borderRadius: "10px",
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
              paddingBottom: "5vh",
            }}
          >
            <div style={{ margin: 0, width: "80vw", display: "flex" }}>
              <div style={{ margin: "0 auto", width: "70vw", display: "flex", flexDirection: "column", justifyContent: "space-evenly" }}>
                <div style={{ margin: "1vh 0", fontSize: "10vw", color: "#8ABC9A", fontWeight: "700" }}>{detail.date}</div>
                <div style={{ margin: "1vh 0", fontSize: "8vw", color: "#8ABC9A", fontWeight: "700" }}>{detail.mountainName}</div>
                <div style={{ display: "flex", justifyContent: "space-around", color: "#8E8E92" }}>
                  <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <p style={{ marginBottom: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>거리</p>
                    <p style={{ marginTop: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>{detail.distance}km</p>
                  </div>
                  <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <p style={{ marginBottom: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>시간</p>
                    <p style={{ marginTop: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>{detail.time}</p>
                  </div>
                  <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <p style={{ marginBottom: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>경험치</p>
                    <p style={{ marginTop: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>{detail.exp}exp</p>
                  </div>
                </div>
              </div>
            </div>
            {/* <img src={logo} alt="img" style={{ margin: "auto", height: "65vw", width: "65vw" }} /> */}
            <RenderAfterNavermapsLoaded
              ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
              error={<p>Maps Load Error</p>}
              loading={<p>Maps Loading...</p>}
            >
              <NaverMap
                mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
                style={{
                  width: "65vw", // 네이버지도 가로 길이
                  height: "45vh", // 네이버지도 세로 길이
                  position: "relative",
                  zIndex: 1,
                }}
                center={{ lat: centerY, lng: centerX }} // 지도 초기 위치
                defaultZoom={13} // 지도 초기 확대 배율
                draggable={false}
                scrollWheel={false}
              >
                <Polyline path={detail.coords} strokeColor={"red"} strokeStyle={"solid"} strokeOpacity={1} strokeWeight={3} />
              </NaverMap>
            </RenderAfterNavermapsLoaded>
          </div>
        </>
      </div>
    </>
  );
}

function mapStateToProps(state) {
  return { userSlice: state.user };
}

export default connect(mapStateToProps)(RecordDetail);
