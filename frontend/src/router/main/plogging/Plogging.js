import React, { useState } from "react";
import PloggingMap from "./PloggingMap";

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

  // useEffect(() => {
  //   getLocation();
  // }, [myLocation]);

  return (
    <>
      <PloggingMap getLocation={getLocation} myLocation={myLocation}></PloggingMap>
    </>
  );
}
export default Plogging;
