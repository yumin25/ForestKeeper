import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { RenderAfterNavermapsLoaded, NaverMap, Marker, Polyline } from "react-naver-maps";
import ReactTimerStopwatch from "react-stopwatch-timer";
import location from "../../../res/img/location.png";
import play from "../../../res/img/play.png";
import stop from "../../../res/img/stop.png";
import File from "../../../config/File";
import "./PloggingMap.css";

function MapAPI({ myLocation, trackingPath }) {
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
      <Polyline path={trackingPath} strokeColor={"red"} strokeStyle={"solid"} strokeOpacity={1} strokeWeight={3} />
    </NaverMap>
  );
}
function PloggingMap({ getLocation, myLocation, tracking, stopTracking, trackingPath, allDistance, distTracking, mtCode }) {
  const navigate = useNavigate();
  const reducer = (accumulator, currentValue) => accumulator + currentValue;
  const fromTime = new Date(0, 0, 0, 0, 0, 0, 0);
  const [isOn, setIsOn] = useState(false);
  const startRecording = () => {
    let stopButton = document.getElementById("stop");
    stopButton.style.animation = "stretch 1s both";
  };
  const endRecording = () => {
    let stopButton = document.getElementById("stop");
    stopButton.style.animation = "reverse-stretch 1s both";
  };
  const timeRecord = () => {
    let timeHour = Number(watch().slice(11, 13)) + Number(document.getElementsByClassName("react-stopwatch-timer__table")[0].innerText.slice(0, 2));
    let timeMin = Number(watch().slice(14, 16)) + Number(document.getElementsByClassName("react-stopwatch-timer__table")[0].innerText.slice(3, 5));
    if (timeMin >= 60) {
      timeHour += 1;
      timeMin -= 60;
    }
    timeHour = String(timeHour);
    timeMin = String(timeMin);
    if (timeHour.length < 2) {
      timeHour = "0" + timeHour;
    }
    if (timeMin.length < 2) {
      timeMin = "0" + timeMin;
    }
    const endTime = watch().slice(0, 11) + timeHour + ":" + timeMin;
    return endTime;
  };

  const makeZeroNum = (num, n) => {
    var numStr = "" + num;
    while (true) {
      if (numStr.length >= n) {
        break;
      }
      numStr = "0" + numStr;
    }
    return numStr;
  };

  const watch = () => {
    var date = new Date();
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    var h = date.getHours();
    var min = date.getMinutes();
    // var s = date.getSeconds();

    var result = y + "-" + makeZeroNum(m, 2) + "-" + makeZeroNum(d, 2) + " " + makeZeroNum(h, 2) + ":" + makeZeroNum(min, 2);
    // + ":" + makeZeroNum(s, 2);
    return result;
  };
  // 요청보내기
  const recordPlogging = (e) => {
    const formData = new FormData();
    const data = {
      mountainCode: mtCode,
      startTime: watch(),
      endTime: timeRecord(),
      distance: allDistance.reduce(reducer).toFixed(2),
      coords: trackingPath ? trackingPath : [],
    };
    console.log(data);
    formData.append("dto", new Blob([JSON.stringify(data)], { type: "application/json" }));

    File.post("/plogging", formData)
      .then((res) => {
        console.log(res.data);
        e.preventDefault();
        navigate("/accounts/mypage/recorddetail");
        localStorage.setItem("ploggingId", res.data.ploggingId);
      })
      .catch((e) => {
        console.log(e);
      });
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
          id="play"
          src={play}
          alt=""
          style={{ zIndex: 3, height: "3.5vh", width: "3.5vh", marginTop: "0.5vh", marginLeft: "1.4vw" }}
          onClick={() => {
            startRecording();
            setIsOn(true);
            watch();
            tracking();
            distTracking();
          }}
        />
      </button>
      <button id="stop" className="btn-recording">
        <div style={{ display: "flex", justifyContent: "space-around", alignItems: "center" }}>
          <div>
            <ReactTimerStopwatch
              id="timer"
              isOn={isOn}
              className="react-stopwatch-timer__table"
              watchType="stopwatch"
              fromTime={fromTime}
              style={{ width: "16.5vw !important", height: "12vw !important" }}
            />
          </div>
          {allDistance.length === 0 ? (
            <p style={{ color: "white", fontSize: "5vw", fontWeight: "700", marginTop: 0, marginBottom: 0 }}>0 km</p>
          ) : (
            <p style={{ color: "white", fontSize: "5vw", fontWeight: "700", marginTop: 0, marginBottom: 0 }}>
              {allDistance.reduce(reducer).toFixed(2)} km
            </p>
          )}
          <img
            src={stop}
            alt=""
            style={{ zIndex: 1, height: "3.5vh", width: "3.5vh" }}
            onClick={(e) => {
              endRecording();
              setIsOn(false);
              stopTracking();
              recordPlogging(e);
            }}
          />
        </div>
      </button>
      <RenderAfterNavermapsLoaded
        ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
        error={<p>Maps Load Error</p>}
        loading={<p>Maps Loading...</p>}
      >
        <MapAPI myLocation={myLocation} trackingPath={trackingPath} />
      </RenderAfterNavermapsLoaded>
    </>
  );
}

export default PloggingMap;
