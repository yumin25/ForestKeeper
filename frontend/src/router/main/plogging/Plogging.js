import React, { useState } from "react";
import PloggingMap from "./PloggingMap";
let tracker;
function Plogging() {
  const [myLocation, setMyLocation] = useState({
    latitude: 37.554722,
    longitude: 126.970833,
  });

  const getLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        setMyLocation({
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        });
      });
    } else {
      window.alert("현재위치를 알수 없습니다.");
    }
  };

  // gps
  const [trackingPath, setTrackingPath] = useState([]);
  const handleTrackingPath = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        setTrackingPath((currentArray) => [...currentArray, new window.naver.maps.LatLng(position.coords.latitude, position.coords.longitude)]);
      });
    }
  };
  const tracking = () => {
    tracker = setInterval(function () {
      getLocation();
      handleTrackingPath();
    }, 1000);
  };
  const stopTracking = () => {
    clearInterval(tracker);
    console.log(trackingPath);
  };

  // useEffect(() => {
  //   getLocation();
  // }, [myLocation]);

  return (
    <>
      <PloggingMap
        getLocation={getLocation}
        myLocation={myLocation}
        trackingPath={trackingPath}
        tracking={tracking}
        stopTracking={stopTracking}
      ></PloggingMap>
    </>
  );
}
export default Plogging;
