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

  // distance
  const computeDistance = (startLatCord, startLonCord, endLatCord, endLonCord) => {
    const degreesToRadians = (degrees) => {
      const radians = (degrees * Math.PI) / 180;
      return radians;
    };
    let startLat = degreesToRadians(startLatCord);
    let startLon = degreesToRadians(startLonCord);
    let endLat = degreesToRadians(endLatCord);
    let endLon = degreesToRadians(endLonCord);
    const Radius = 6371;
    let distance = Math.acos(Math.sin(startLat) * Math.sin(endLat) + Math.cos(startLat) * Math.cos(endLat) * Math.cos(startLon - endLon)) * Radius;
    return distance;
  };
  const [allDistance, setAllDistance] = useState([]);

  // gps
  const [trackingPath, setTrackingPath] = useState([]);
  const handleTrackingPath = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        setTrackingPath((currentArray) => [...currentArray, new window.naver.maps.LatLng(position.coords.latitude, position.coords.longitude)]);
        let endLatCord = position.coords.latitude;
        let endLonCord = position.coords.longitude;
        let startLatCord = endLatCord;
        let startLonCord = endLonCord;
        // console.log(startLatCord, startLonCord, endLatCord, endLonCord);
        let dist = computeDistance(startLatCord, startLonCord, endLatCord, endLonCord);
        // console.log(dist);
        setAllDistance((currentArray) => [...currentArray, dist]);
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
    // console.log(allDistance);
    // console.log(trackingPath);
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
        allDistance={allDistance}
      ></PloggingMap>
    </>
  );
}
export default Plogging;
