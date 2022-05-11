import { useNavigate } from "react-router-dom";
import logo from "../../../res/img/logo.png";
import { RenderAfterNavermapsLoaded, NaverMap, Marker, Polyline } from "react-naver-maps";

function RecordDetail() {
  const navigate = useNavigate();
  return (
    <>
      <div style={{ display: "flex", margin: "1vh" }} onClick={() => navigate(-1)}>
        <span className="material-icons-outlined" style={{ fontSize: "2rem", color: "#8E8E92" }}>
          arrow_back_ios
        </span>
      </div>
      {/* 기록 */}
      <div style={{ display: "flex", flexDirection: "column", alignItems: "center", margin: "3vh 1vw 0 1vw", height: "75vh" }}>
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
            }}
          >
            <div style={{ margin: 0, width: "80vw", display: "flex" }}>
              <div style={{ margin: "0 auto", width: "70vw", display: "flex", flexDirection: "column", justifyContent: "space-evenly" }}>
                <div style={{ margin: "1vh 0", fontSize: "10vw", color: "#8ABC9A", fontWeight: "700" }}>2022.04.15</div>
                <div style={{ margin: "1vh 0", fontSize: "8vw", color: "#8ABC9A", fontWeight: "700" }}>아차산</div>
                <div style={{ display: "flex", justifyContent: "space-around", color: "#8E8E92" }}>
                  <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <p style={{ marginBottom: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>거리</p>
                    <p style={{ marginTop: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>87.95km</p>
                  </div>
                  <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <p style={{ marginBottom: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>시간</p>
                    <p style={{ marginTop: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>87:10</p>
                  </div>
                  <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <p style={{ marginBottom: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>경험치</p>
                    <p style={{ marginTop: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>5000exp</p>
                  </div>
                </div>
              </div>
            </div>
            {/* <img src={logo} alt="img" style={{ margin: "auto", height: "65vw", width: "65vw" }} /> */}
            {/* <RenderAfterNavermapsLoaded
              ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
              error={<p>Maps Load Error</p>}
              loading={<p>Maps Loading...</p>}
            >
              <NaverMap
                mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
                style={{
                  width: "65vw", // 네이버지도 가로 길이
                  height: "65vh", // 네이버지도 세로 길이
                  position: "relative",
                  zIndex: 1,
                }}
                center={{ lat: myLocation.latitude, lng: myLocation.longitude }} // 지도 초기 위치
                defaultZoom={13} // 지도 초기 확대 배율
              >
                {myLocation.latitude !== 37.554722 && myLocation.longitude !== 126.970833 && (
                  <Marker key={1} position={new navermaps.LatLng(myLocation.latitude, myLocation.longitude)} />
                )}
                <Polyline path={trackingPath} strokeColor={"red"} strokeStyle={"solid"} strokeOpacity={1} strokeWeight={3} />
              </NaverMap>
            </RenderAfterNavermapsLoaded> */}
          </div>
        </>
      </div>
    </>
  );
}

export default RecordDetail;
