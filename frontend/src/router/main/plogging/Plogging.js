import React, { useState, useEffect } from "react";
import PloggingMap from "./PloggingMap";
import Send from "../../../config/Send";
let tracker;
let distanceTracker;
function Plogging() {
  const [trashList, setTrashList] = useState([]);
  useEffect(() => {
    getTrash();
  }, []);

  function getTrash() {
    Send.get(
      `/plogging/trash`,
      {},
      {
        withCredentials: true,
      }
    )
      .then((res) => {
        setTrashList(res.data.list);
        console.log(res);
      })
      .catch((e) => {
        console.log(e);
      });
  }

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
  const computeDistance = (
    startLatCord,
    startLonCord,
    endLatCord,
    endLonCord
  ) => {
    const degreesToRadians = (degrees) => {
      const radians = (degrees * Math.PI) / 180;
      return radians;
    };
    let startLat = degreesToRadians(startLatCord);
    let startLon = degreesToRadians(startLonCord);
    let endLat = degreesToRadians(endLatCord);
    let endLon = degreesToRadians(endLonCord);
    const Radius = 6371;

    let distance =
      Math.acos(
        Math.sin(startLat) * Math.sin(endLat) +
          Math.cos(startLat) * Math.cos(endLat) * Math.cos(startLon - endLon)
      ) * Radius;
    return (
      Math.floor(distance) +
      (Math.round(distance * 100) - Math.floor(distance) * 100) / 100
    );
  };
  const [allDistance, setAllDistance] = useState([]);

  // gps
  const [trackingPath, setTrackingPath] = useState([]);
  const handleTrackingPath = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        setTrackingPath((currentArray) => [
          ...currentArray,
          new window.naver.maps.LatLng(
            position.coords.latitude,
            position.coords.longitude
          ),
        ]);
      });
    }
  };
  const handleAllDistance = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        var endLatCord = position.coords.latitude;
        var endLonCord = position.coords.longitude;

        var startLatCord =
          trackingPath.length > 0 ? trackingPath[trackingPath.length - 2].y : 1;
        var startLonCord =
          trackingPath.length > 0 ? trackingPath[trackingPath.length - 2].x : 2;
        let dist = computeDistance(
          startLatCord,
          startLonCord,
          endLatCord,
          endLonCord
        );

        if (dist < 1) {
          setAllDistance((currentArray) => [...currentArray, dist]);
        }
        // console.log(startLatCord, startLonCord, endLatCord, endLonCord);
        // console.log(trackingPath[trackingPath.length - 1]);
        // console.log(dist);
        // console.log(allDistance);
      });
    }
  };
  const tracking = () => {
    tracker = setInterval(function () {
      getLocation();
      handleTrackingPath();
    }, 2000);
  };
  const distTracking = () => {
    handleAllDistance();
  };
  const stopTracking = () => {
    clearInterval(tracker);
    clearInterval(distanceTracker);
    // console.log(allDistance);
    // console.log(trackingPath);
    // console.log(trackingPath[trackingPath.length - 1].y, trackingPath[trackingPath.length - 1].x);
  };

  useEffect(() => {
    distTracking();
  }, [trackingPath]);

  return (
    <>
      <PloggingMap
        getLocation={getLocation}
        myLocation={myLocation}
        trackingPath={trackingPath}
        tracking={tracking}
        distTracking={distTracking}
        stopTracking={stopTracking}
        allDistance={allDistance}
        trashList={trashList}
      ></PloggingMap>
    </>
  );
}
export default Plogging;
