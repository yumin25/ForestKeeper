import React, { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import {
  RenderAfterNavermapsLoaded,
  NaverMap,
  Marker,
  Polyline,
} from "react-naver-maps";
import ReactTimerStopwatch from "react-stopwatch-timer";
import location from "../../../res/img/location.png";
import play from "../../../res/img/play.png";
import stop from "../../../res/img/stop.png";
import File from "../../../config/File";
import "./PloggingMap.css";
let stopwatchtimer;
function MapAPI({ myLocation, trackingPath, trashList, isOn }) {
  const TrashStyle = {
    position: "absolute",
    border: "0.1px solid #8ABC9A",
    borderRadius: 15,
    background: "#8ABC9A",
    color: "white",
    borderRadius: 15,
    fontSize: "1.7vh",
    left: "70vw",
    top: "3vh",
    height: "5vh",
    width: "28vw",
    zIndex: 2,
  };

  const navermaps = window.naver.maps;
  const [lat, setLat] = useState(37.579722);
  const [long, setLong] = useState(126.976033);
  const [isClicked, setIsClicked] = useState(false);
  let dddd = 11;
  let count = 0;
  var map = new navermaps.Map("map", {
    center: new navermaps.LatLng(lat, long),
    zoom: 15,
    mapTypeId: navermaps.MapTypeId.NORMAL,
  });

  var bounds = map.getBounds(),
    southWest = bounds.getSW(),
    northEast = bounds.getNE(),
    lngSpan = northEast.lng() - southWest.lng(),
    latSpan = northEast.lat() - southWest.lat();

  useEffect(() => {
    if (isClicked == false) {
      setIsClicked(true);
    } else {
      setIsClicked(false);
    }
  }, [count]);

  useEffect(() => {
    setLat(myLocation.latitude);
    setLong(myLocation.longitude);
  }, [myLocation]);

  useEffect(() => {
    if (isOn == false) {
      setLat(map.getCenter()._lat);
      setLong(map.getCenter()._lng);
    }
  }, [map.getCenter()]);

  // function displayMarker() {
  //   count++;
  //   console.log(count);
  //   var position = new navermaps.LatLng(southWest.lat() + latSpan * Math.random(), southWest.lng() + lngSpan * Math.random());
  //   let num = 0;

  //   if (count != 0 && count % 2 == 0) {
  //     var map1 = new navermaps.Map("map", {
  //       center: new navermaps.LatLng(lat, long),
  //       zoom: 15,
  //       mapTypeId: navermaps.MapTypeId.NORMAL,
  //     });
  //     map = new navermaps.Map("map", {
  //       center: new navermaps.LatLng(lat, long),
  //       zoom: 15,
  //       mapTypeId: navermaps.MapTypeId.NORMAL,
  //     });
  //   } else {
  //     for (var i = 0; i < trashList.length; i++) {
  //       if (
  //         trashList[i].latitude >= map.getBounds()._min._lat &&
  //         trashList[i].latitude <= map.getBounds()._max._lat &&
  //         trashList[i].longitude >= map.getBounds()._min._lng &&
  //         trashList[i].longitude <= map.getBounds()._max._lng
  //       ) {
  //         num += 1;
  //         var marker = new navermaps.Marker({
  //           position: new navermaps.LatLng(trashList[i].latitude, trashList[i].longitude),
  //           map: map,
  //         });
  //       }
  //     }
  //   }

  //   console.log(num);
  // }

  return (
    <div>
      {/* <button style={TrashStyle} onClick={() => displayMarker()}>
        쓰레기통 찾기
      </button> */}

      {/* {isOn === true ? ( */}
      <NaverMap
        mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
        style={{
          width: "100vw", // 네이버지도 가로 길이
          height: "92.5vh", // 네이버지도 세로 길이
          position: "relative",
          zIndex: 1,
        }}
        center={{ lat: myLocation.latitude, lng: myLocation.longitude }} // 지도 초기 위치
        defaultZoom={15} // 지도 초기 확대 배율
      >
        {myLocation.latitude !== 37.554722 &&
          myLocation.longitude !== 126.970833 && (
            <Marker
              key={1}
              position={
                new navermaps.LatLng(myLocation.latitude, myLocation.longitude)
              }
            />
          )}
        <Polyline
          path={trackingPath}
          strokeColor={"red"}
          strokeStyle={"solid"}
          strokeOpacity={1}
          strokeWeight={3}
        />
      </NaverMap>
      {/* ) : (
        <div
          id="map"
          style={{
            // zIndex: 1,
            width: "50vw",
            height: "92.5vh",
          }}
        />
      )} */}
    </div>
  );
}

function PloggingMap({
  getLocation,
  myLocation,
  tracking,
  stopTracking,
  trackingPath,
  allDistance,
  distTracking,
  mtCode,
  trashList,
}) {
  const navigate = useNavigate();
  const reducer = (accumulator, currentValue) => accumulator + currentValue;
  const [isOn, setIsOn] = useState(false);
  const [currentTime, setCurrentTime] = useState("00:00:00");
  const stopwatch = () => {
    let time = 0;
    stopwatchtimer = setInterval(function () {
      time++;
      var hour = 0;
      var min = 0;
      var sec = 0;

      min = Math.floor(time / 60);
      hour = Math.floor(min / 60);
      sec = time % 60;
      min = min % 60;

      var th = hour;
      var tm = min;
      var ts = sec;
      if (th < 10) {
        th = "0" + hour;
      }
      if (tm < 10) {
        tm = "0" + min;
      }
      if (ts < 10) {
        ts = "0" + sec;
      }
      setCurrentTime(th + ":" + tm + ":" + ts);
    }, 1000);
  };
  const quitStopwatch = () => {
    clearInterval(stopwatchtimer);
  };

  // 애니메이션적용
  const startRecording = () => {
    let stopButton = document.getElementById("stop");
    stopButton.style.animation = "stretch 1s both";
  };
  const endRecording = () => {
    let stopButton = document.getElementById("stop");
    stopButton.style.animation = "reverse-stretch 1s both";
  };

  // 시작시간
  const watch = () => {
    var date = new Date();
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    var h = date.getHours();
    var min = date.getMinutes();
    // var s = date.getSeconds();

    var result =
      y +
      "-" +
      makeZeroNum(m, 2) +
      "-" +
      makeZeroNum(d, 2) +
      " " +
      makeZeroNum(h, 2) +
      ":" +
      makeZeroNum(min, 2);
    // + ":" + makeZeroNum(s, 2);
    return result;
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

  // 끝시간
  const timeRecord = () => {
    let timeHour =
      Number(watch().slice(11, 13)) + Number(currentTime.slice(0, 2));
    let timeMin =
      Number(watch().slice(14, 16)) + Number(currentTime.slice(3, 5));

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

  // 요청보내기
  const recordPlogging = (e) => {
    clearInterval(stopwatchtimer);
    const formData = new FormData();
    const data = {
      mountainCode: mtCode,
      startTime: watch(),
      endTime: timeRecord(),
      distance: allDistance.reduce(reducer).toFixed(2),
      coords: trackingPath ? trackingPath : [],
    };
    formData.append(
      "dto",
      new Blob([JSON.stringify(data)], { type: "application/json" })
    );

    File.post("/plogging", formData)
      .then((res) => {
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
          style={{
            zIndex: 3,
            height: "4vh",
            width: "4vh",
            marginTop: "0.4vh",
            marginLeft: "0.1vw",
          }}
        />
      </button>
      <button id="start" className="btn-start">
        <img
          id="play"
          src={play}
          alt=""
          style={{
            zIndex: 3,
            height: "3.5vh",
            width: "3.5vh",
            marginTop: "0.5vh",
            marginLeft: "1.4vw",
          }}
          onClick={() => {
            startRecording();
            setIsOn(true);
            watch();
            tracking();
            distTracking();
            stopwatch();
          }}
        />
      </button>
      <button id="stop" className="btn-recording">
        <div
          style={{
            display: "flex",
            justifyContent: "space-around",
            alignItems: "center",
          }}
        >
          <p
            style={{
              color: "white",
              fontSize: "5vw",
              fontWeight: "700",
              marginTop: 0,
              marginBottom: 0,
            }}
          >
            {currentTime}
          </p>
          {allDistance.length === 0 ? (
            <p
              style={{
                color: "white",
                fontSize: "5vw",
                fontWeight: "700",
                marginTop: 0,
                marginBottom: 0,
              }}
            >
              0.00 km
            </p>
          ) : (
            <p
              style={{
                color: "white",
                fontSize: "5vw",
                fontWeight: "700",
                marginTop: 0,
                marginBottom: 0,
              }}
            >
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
              quitStopwatch();
            }}
          />
        </div>
      </button>
      <div id="map">
        <RenderAfterNavermapsLoaded
          ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
          error={<p>Maps Load Error</p>}
          loading={<p>Maps Loading...</p>}
        >
          <MapAPI
            myLocation={myLocation}
            trackingPath={trackingPath}
            trashList={trashList}
            isOn={isOn}
          />
        </RenderAfterNavermapsLoaded>
      </div>
    </>
  );
}

export default PloggingMap;
