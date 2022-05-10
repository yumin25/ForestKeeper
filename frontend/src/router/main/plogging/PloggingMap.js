import { useState } from "react";
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
function PloggingMap({ getLocation, myLocation, tracking, stopTracking, trackingPath, allDistance }) {
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
    const timeValue = document.getElementsByClassName("react-stopwatch-timer__table")[0].innerText;
    console.log(timeValue);
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
    console.log(result);
  };
  // 요청보내기
  const imgFile =
    "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDExMDVfMTg2%2FMDAxNjA0NTczODQzMDUy.6Rfm1wBLiJqX0j_jacirUtOQPuo6kIz89XZ3-JRg0qcg.9Yjpdz3fQJH7F2HIiXQ4u8uncSKvAKjX0-iKRZFWgi4g.JPEG.violet110113%2Fy8hfb1bf61czzvoru310.jpg&type=sc960_832";
  const recordPlogging = () => {
    let formData = new FormData();
    formData.append("image", imgFile);
    // console.log(imgFile.src);

    const data = {
      mountainName: "아차산",
      startTime: "2022-05-04 11:04",
      endTime: "2022-05-04 11:14",
      distance: "1.1",
    };
    formData.append("dto", new Blob([JSON.stringify(data)], { type: "application/json" }));

    File.post("/plogging", formData)
      .then((res) => {
        console.log(res.data);
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
          }}
        />
      </button>
      <button id="stop" className="btn-recording">
        <div style={{ display: "flex", justifyContent: "space-around", alignItems: "center" }}>
          <div>
            <ReactTimerStopwatch id="timer" isOn={isOn} className="react-stopwatch-timer__table" watchType="stopwatch" fromTime={fromTime} />
          </div>
          {allDistance.length === 0 ? (
            <p style={{ color: "white", fontSize: "5vw", fontWeight: "700", marginTop: 0, marginBottom: 0 }}>0 km</p>
          ) : (
            <p style={{ color: "white", fontSize: "5vw", fontWeight: "700", marginTop: 0, marginBottom: 0 }}>{allDistance.reduce(reducer)} km</p>
          )}
          <img
            src={stop}
            alt=""
            style={{ zIndex: 3, height: "3.5vh", width: "3.5vh" }}
            onClick={() => {
              endRecording();
              setIsOn(false);
              timeRecord();
              stopTracking();
              // recordPlogging();
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
