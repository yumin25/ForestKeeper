import { RenderAfterNavermapsLoaded, NaverMap, Marker, Polyline } from "react-naver-maps";
import location from "../../../res/img/location.png";
import play from "../../../res/img/play.png";
import stop from "../../../res/img/stop.png";
import "./PloggingMap.css";

function MapAPI({ myLocation }) {
  const navermaps = window.naver.maps;

  return (
    <NaverMap
      mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
      style={{
        width: "100vw", // 네이버지도 가로 길이
        height: "92.5vh", // 네이버지도 세로 길이
        position: "relative",
        zIndex: 1,
      }}
      center={{ lat: myLocation.latitude, lng: myLocation.longitude }} // 지도 초기 위치
      defaultZoom={13} // 지도 초기 확대 배율
    >
      {myLocation.latitude !== 37.554722 && myLocation.longitude !== 126.970833 && (
        <Marker key={1} position={new navermaps.LatLng(myLocation.latitude, myLocation.longitude)} />
      )}
      {/* <Polyline
        path={[
          new navermaps.LatLng(37.560620929135716, 127.0936195755005),
          new navermaps.LatLng(37.560620929135716, 127.10353302001953),
          new navermaps.LatLng(37.5556921307849, 127.09452079772949),
          new navermaps.LatLng(37.56321310838941, 127.09814714431763),
          new navermaps.LatLng(37.555760351656545, 127.10299657821654),
          new navermaps.LatLng(37.560620929135716, 127.0936195755005),
        ]}
        // clickable // 사용자 인터랙션을 받기 위해 clickable을 true로 설정합니다.
        strokeColor={"red"}
        strokeStyle={"solid"}
        strokeOpacity={0.8}
        strokeWeight={3}
      /> */}
    </NaverMap>
  );
}

function PloggingMap({ getLocation, myLocation }) {
  const startRecording = () => {
    let stopButton = document.getElementById("stop");
    stopButton.style.animation = "stretch 1s both";
  };
  const endRecording = () => {
    let stopButton = document.getElementById("stop");
    stopButton.style.animation = "reverse-stretch 1s both";
  };
  return (
    <>
      <button
        style={{
          position: "absolute",
          height: "12vw",
          width: "12vw",
          borderRadius: 30,
          border: "none",
          boxShadow: "0px 5px 10px 2px darkgray",
          background: "white",
          color: "#37CD8D",
          marginTop: "83vh",
          marginLeft: "3vw",
          zIndex: 3,
        }}
      >
        <img
          onClick={getLocation}
          src={location}
          alt=""
          style={{ zIndex: 3, height: "4vh", width: "4vh", marginTop: "0.4vh", marginLeft: "0.1vw" }}
        />
      </button>
      <button id="start" className="btn-start">
        <img
          src={play}
          alt=""
          style={{ zIndex: 3, height: "3.5vh", width: "3.5vh", marginTop: "0.5vh", marginLeft: "1.4vw" }}
          onClick={startRecording}
        />
      </button>
      <button id="stop" className="btn-recording">
        <div style={{ display: "flex", justifyContent: "space-around" }}>
          <p style={{ color: "white", fontSize: "5vw", fontWeight: "700", marginTop: "0.5vh", marginBottom: 0 }}>1:15:11</p>
          <p style={{ color: "white", fontSize: "5vw", fontWeight: "700", marginTop: "0.5vh", marginBottom: 0 }}>11.5km</p>
          <img src={stop} alt="" style={{ zIndex: 3, height: "3.5vh", width: "3.5vh" }} onClick={endRecording} />
        </div>
      </button>
      <RenderAfterNavermapsLoaded
        ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
        error={<p>Maps Load Error</p>}
        loading={<p>Maps Loading...</p>}
      >
        <MapAPI myLocation={myLocation} />
      </RenderAfterNavermapsLoaded>
    </>
  );
}

export default PloggingMap;
